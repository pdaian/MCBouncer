package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MCBouncerCommands extends CommandContainer {

    public MCBouncerCommands(MCBouncer controller) {
        super(controller);
    }

    /**
     * From WorldEdit
     */
    @Command(aliases = {"help"},
    usage = "[<command>]",
    desc = "Displays help for the given command or lists all commands.",
    min = 0,
    max = -1)
    public void help(CommandContext args, LocalPlayer player) throws CommandException {

        if (args.argsLength() == 0) {
            SortedSet<String> commands = new TreeSet<String>(new Comparator<String>() {

                public int compare(String o1, String o2) {
                    final int ret = o1.replaceAll("/", "").compareToIgnoreCase(o2.replaceAll("/", ""));
                    if (ret == 0) {
                        return o1.compareToIgnoreCase(o2);
                    }
                    return ret;
                }
            });
            commands.addAll(controller.getCommandManager().getCommands().keySet());

            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String command : commands) {
                if (!first) {
                    sb.append(", ");
                }

                sb.append('/');
                sb.append(command);
                first = false;
            }

            player.sendMessage(sb.toString());

            return;
        }

        String command = args.getJoinedStrings(0).replaceAll("/", "");

        String helpMessage = controller.getCommandManager().getHelpMessages().get(command);
        if (helpMessage == null) {
            throw new CommandException("Unknown command '" + command + "'.");
        }

        player.sendMessage(helpMessage);
    }
    
    @Command(aliases = {"reload"},
    desc = "Reloads MCBouncer configuration")
    @CommandPermissions("mcbouncer.admin")
    public void reload(CommandContext args, LocalPlayer sender) throws CommandException {

        controller.getConfiguration().load();
        sender.sendMessage(ChatColor.GREEN + "Configuration loaded successfully.");

    }
    
    @Command(aliases = {"version", "ver"},
    desc = "Gets the current MCBouncer version")
    public void version(CommandContext args, LocalPlayer sender) throws CommandException {
        sender.sendMessage(ChatColor.GRAY + "Current MCBouncer version: " + MCBouncer.getVersion());
    }
}
