package com.mcbouncer.util.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.commands.CommandContainer;
import com.mcbouncer.exception.CommandUsageException;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.exception.CommandPermissionsException;
import com.mcbouncer.exception.MissingNestedCommandException;
import com.mcbouncer.exception.UnhandledCommandException;
import com.mcbouncer.exception.WrappedCommandException;
import com.mcbouncer.util.MiscUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manager for handling commands. Modified from sk89q's CommandsManager class in WorldEdit.
 * 
 * Uses annotations to denote commands
 * 
 */
public class CommandManager {

    /**
     * Main MCBouncer controller instance
     */
    protected MCBouncer controller;
    /**
     * Mapping of commands (including aliases) with a description. Root
     * commands are stored under a key of null, whereas child commands are
     * cached under their respective {@link Method}. The child map has
     * the key of the command name (one for each alias) with the
     * method.
     */
    protected Map<Method, Map<String, Method>> commands = new HashMap<Method, Map<String, Method>>();
    /**
     * Used to store the instances associated with a method.
     */
    protected Map<Method, Object> instances = new HashMap<Method, Object>();
    /**
     * Mapping of commands (not including aliases) with a description. This
     * is only for top level commands.
     */
    protected Map<String, String> descriptions = new HashMap<String, String>();
    /**
     * Mapping of commands (not including aliases) with a description. This
     * is only for top level commands.
     */
    protected Map<String, String> helpMessages = new HashMap<String, String>();

    public CommandManager(MCBouncer controller) {
        this.controller = controller;
    }

    /**
     * Register a CommandContainer class
     * @param clazz
     * @return 
     */
    public List<Command> register(Class<? extends CommandContainer> clazz) {
        return registerMethods(clazz, null);
    }

    /**
     * Register the methods of a class. This will automatically construct
     * instances as necessary.
     *
     * @param cls
     * @param parent
     * @return Commands Registered
     */
    protected List<Command> registerMethods(Class<? extends CommandContainer> cls, Method parent) {
        try {
            Constructor<? extends CommandContainer> cons = cls.getConstructor(MCBouncer.class);
            CommandContainer obj = cons.newInstance(controller);
            return registerMethods(obj, parent);
        } catch (Exception e) {
            controller.getLogger().severe("Failed to register commands", e);
        }
        return null;
    }

    /**
     * Register the methods of a CommandContainer instance.
     * 
     * @param cls
     * @param parent
     */
    protected List<Command> registerMethods(CommandContainer classInst, Method parent) {
        Map<String, Method> commMap;
        List<Command> registered = new ArrayList<Command>();

        // Make a new hash map to cache the commands for this class
        // as looking up methods via reflection is fairly slow
        if (commands.containsKey(parent)) {
            commMap = commands.get(parent);
        } else {
            commMap = new HashMap<String, Method>();
            commands.put(parent, commMap);
        }

        for (Method method : classInst.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Command.class)) {
                continue;
            }

            // We want to be able invoke with an instance
            if (!Modifier.isStatic(method.getModifiers())) {
                instances.put(method, classInst);
            }

            Command command = method.getAnnotation(Command.class);

            // Cache the aliases too
            for (String alias : command.aliases()) {
                commMap.put(alias, method);
            }

            // Build a list of commands and their usage details, at least for
            // root level commands
            if (parent == null) {
                final String commandName = command.aliases()[0];
                final String desc = command.desc();

                final String usage = command.usage();
                if (usage.length() == 0) {
                    descriptions.put(commandName, desc);
                } else {
                    descriptions.put(commandName, usage + " - " + desc);
                }

                String help = command.help();
                if (help.length() == 0) {
                    help = desc;
                }

                final CharSequence arguments = getArguments(command);
                for (String alias : command.aliases()) {
                    final String helpMessage = "/" + alias + " " + arguments + "\n\n" + help;
                    final String key = alias.replaceAll("/", "");
                    String previous = helpMessages.put(key, helpMessage);

                    if (previous != null && !previous.replaceAll("^/[^ ]+ ", "").equals(helpMessage.replaceAll("^/[^ ]+ ", ""))) {
                        helpMessages.put(key, previous + "\n\n" + helpMessage);
                    }
                }

            }

            // Add the command to the registered command list for return
            registered.add(command);

            // Look for nested commands -- if there are any, those have
            // to be cached too so that they can be quickly looked
            // up when processing commands
            if (method.isAnnotationPresent(NestedCommand.class)) {
                NestedCommand nestedCmd = method.getAnnotation(NestedCommand.class);

                for (Class<? extends CommandContainer> nestedCls : nestedCmd.value()) {
                    this.registerMethods(nestedCls, method);
                }
            }
        }
        return registered;
    }

    /**
     * Checks to see whether there is a command named such at the root level.
     * This will check aliases as well.
     * 
     * @param command
     * @return
     */
    public boolean hasCommand(String command) {
        return commands.get(null).containsKey(command.toLowerCase());
    }

    /**
     * Get a list of command descriptions. This is only for root commands.
     * 
     * @return
     */
    public Map<String, String> getCommands() {
        return descriptions;
    }

    public Map<Method, Map<String, Method>> getMethods() {
        return commands;
    }

    /**
     * Get a map from command name to help message. This is only for root commands.
     * 
     * @return
     */
    public Map<String, String> getHelpMessages() {
        return helpMessages;
    }

    /**
     * Get the usage string for a command.
     * 
     * @param args
     * @param level
     * @param cmd
     * @return
     */
    protected String getUsage(String[] args, int level, Command cmd) {
        final StringBuilder command = new StringBuilder();

        command.append('/');

        for (int i = 0; i <= level; ++i) {
            command.append(args[i]);
            command.append(' ');
        }
        command.append(getArguments(cmd));

        final String help = cmd.help();
        if (!help.isEmpty()) {
            command.append("\n\n");
            command.append(help);
        }

        return command.toString();
    }

    protected CharSequence getArguments(Command cmd) {
        final String flags = cmd.flags();

        final StringBuilder command2 = new StringBuilder();
        if (flags.length() > 0) {
            String flagString = flags.replaceAll(".:", "");
            if (flagString.length() > 0) {
                command2.append("[-");
                for (int i = 0; i < flagString.length(); ++i) {
                    command2.append(flagString.charAt(i));
                }
                command2.append("] ");
            }
        }

        command2.append(cmd.usage());

        return command2;
    }

    /**
     * Get the usage string for a nested command.
     * 
     * @param args
     * @param level
     * @param method
     * @param player
     * @return
     * @throws CommandException
     */
    protected String getNestedUsage(String[] args, int level, Method method, LocalPlayer player) throws CommandException {

        StringBuilder command = new StringBuilder();

        command.append("/");

        for (int i = 0; i <= level; ++i) {
            command.append(args[i]).append(" ");
        }

        Map<String, Method> map = commands.get(method);
        boolean found = false;

        command.append("<");

        Set<String> allowedCommands = new HashSet<String>();

        for (Map.Entry<String, Method> entry : map.entrySet()) {
            Method childMethod = entry.getValue();
            found = true;

            if (hasPermission(childMethod, player)) {
                Command childCmd = childMethod.getAnnotation(Command.class);

                allowedCommands.add(childCmd.aliases()[0]);
            }
        }

        if (allowedCommands.size() > 0) {
            command.append(MiscUtils.join(allowedCommands.toArray(new String[0]), "|"));
        } else {
            if (!found) {
                command.append("?");
            } else {
                //command.append("action");
                throw new CommandPermissionsException();
            }
        }

        command.append(">");

        return command.toString();
    }

    /**
     * Attempt to execute a command. This version takes a separate command
     * name (for the root command) and then a list of following arguments.
     * 
     * @param command command to run
     * @param args arguments
     * @param player command source
     * @param methodArgs method arguments
     * @throws CommandException 
     */
    public void execute(String command, String[] args, LocalPlayer player, Object... methodArgs) throws CommandException {

        String[] newArgs = new String[args.length + 1];
        System.arraycopy(args, 0, newArgs, 1, args.length);
        newArgs[0] = command;
        Object[] newMethodArgs = new Object[methodArgs.length + 1];
        System.arraycopy(methodArgs, 0, newMethodArgs, 1, methodArgs.length);

        executeMethod(null, newArgs, player, newMethodArgs, 0);
    }

    /**
     * Attempt to execute a command.
     * 
     * @param args
     * @param player
     * @param methodArgs
     * @throws CommandException 
     */
    public void execute(String[] args, LocalPlayer player, Object... methodArgs) throws CommandException {

        Object[] newMethodArgs = new Object[methodArgs.length + 1];
        System.arraycopy(methodArgs, 0, newMethodArgs, 1, methodArgs.length);
        executeMethod(null, args, player, newMethodArgs, 0);
    }

    /**
     * Attempt to execute a command.
     * 
     * @param parent
     * @param args
     * @param player
     * @param methodArgs
     * @param level
     * @throws CommandException 
     */
    protected void executeMethod(Method parent, String[] args, LocalPlayer player, Object[] methodArgs, int level) throws CommandException {

        String cmdName = args[level];

        Map<String, Method> map = commands.get(parent);
        Method method = map.get(cmdName.toLowerCase());

        if (method == null) {
            if (parent == null) { // Root
                throw new UnhandledCommandException();
            } else {
                throw new MissingNestedCommandException("Unknown command: " + cmdName,
                        getNestedUsage(args, level - 1, parent, player));
            }
        }

        checkPermission(player, method);

        int argsCount = args.length - 1 - level;

        if (method.isAnnotationPresent(NestedCommand.class)) {
            if (argsCount == 0) {
                throw new MissingNestedCommandException("Sub-command required.",
                        getNestedUsage(args, level, method, player));
            } else {
                executeMethod(method, args, player, methodArgs, level + 1);
            }
        } else if (method.isAnnotationPresent(CommandAlias.class)) {
            CommandAlias aCmd = method.getAnnotation(CommandAlias.class);
            executeMethod(parent, aCmd.value(), player, methodArgs, level);
        } else {
            Command cmd = method.getAnnotation(Command.class);

            String[] newArgs = new String[args.length - level];
            System.arraycopy(args, level, newArgs, 0, args.length - level);

            final Set<Character> valueFlags = new HashSet<Character>();

            char[] flags = cmd.flags().toCharArray();
            Set<Character> newFlags = new HashSet<Character>();
            for (int i = 0; i < flags.length; ++i) {
                if (flags.length > i + 1 && flags[i + 1] == ':') {
                    valueFlags.add(flags[i]);
                    ++i;
                }
                newFlags.add(flags[i]);
            }

            CommandContext context = new CommandContext(newArgs, valueFlags);

            if (context.argsLength() < cmd.min()) {
                throw new CommandUsageException("Too few arguments.", getUsage(args, level, cmd));
            }

            if (cmd.max() != -1 && context.argsLength() > cmd.max()) {
                throw new CommandUsageException("Too many arguments.", getUsage(args, level, cmd));
            }

            for (char flag : context.getFlags()) {
                if (!newFlags.contains(flag)) {
                    throw new CommandUsageException("Unknown flag: " + flag, getUsage(args, level, cmd));
                }
            }

            methodArgs[0] = context;

            Object instance = instances.get(method);

            try {
                method.invoke(instance, methodArgs);
            } catch (IllegalArgumentException e) {
                controller.getLogger().severe("Failed to execute command", e);
            } catch (IllegalAccessException e) {
                controller.getLogger().severe("Failed to execute command", e);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof CommandException) {
                    throw (CommandException) e.getCause();
                }

                throw new WrappedCommandException(e.getCause());
            }
        }
    }

    protected void checkPermission(LocalPlayer player, Method method) throws CommandException {
        if (!hasPermission(method, player)) {
            throw new CommandPermissionsException();
        }
    }

    /**
     * Returns whether a player has access to a command.
     * 
     * @param method
     * @param player
     * @return
     */
    protected boolean hasPermission(Method method, LocalPlayer player) {
        CommandPermissions perms = method.getAnnotation(CommandPermissions.class);
        if (perms == null) {
            return true;
        }

        for (String perm : perms.value()) {
            if (player.hasPermission(perm)) {
                return true;
            }
        }

        return false;
    }
}
