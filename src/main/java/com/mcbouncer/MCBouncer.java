package com.mcbouncer;

import com.mcbouncer.api.MCBouncerAPI;
import com.mcbouncer.commands.BanCommands;
import com.mcbouncer.commands.GeneralCommands;
import com.mcbouncer.commands.MCBouncerCommands;
import com.mcbouncer.commands.NoteCommands;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.exception.CommandPermissionsException;
import com.mcbouncer.exception.CommandUsageException;
import com.mcbouncer.exception.MissingNestedCommandException;
import com.mcbouncer.exception.UnhandledCommandException;
import com.mcbouncer.exception.WrappedCommandException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBLogger;
import com.mcbouncer.util.commands.CommandManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Core controller class. Stores many of the important data
 * that is passed around throughout this plugin. It is designed
 * to be server-independent, so all the data in here is not tied
 * to any specific implementation.
 * 
 * TODO: Document everything!
 * TODO: Implement the remaining unused configuration nodes
 * 
 */
public class MCBouncer {

    protected final MCBLogger logger;
    protected LocalServer server;
    protected CommandManager commandManager;
    protected LocalConfiguration configuration;
    protected MCBouncerAPI api;
    protected static String version;
    protected List<String> currentlyLoggingIn = new ArrayList<String>();
    protected String lastKickedUser = "";

    static {
        getVersion();
    }

    public MCBouncer(LocalServer server, LocalConfiguration configuration) {
        this.logger = new MCBLogger(this);

        this.server = server;
        this.configuration = configuration;

        commandManager = new CommandManager(this);
        commandManager.register(BanCommands.class);
        commandManager.register(GeneralCommands.class);
        commandManager.register(MCBouncerCommands.class);
        commandManager.register(NoteCommands.class);

        this.api = new MCBouncerAPI(this);

    }

    /**
     * Takes in a LocalPlayer and the entire command, string-split by a space.
     * Runs the command, and handles any errors that may occur. 
     * 
     * @param player
     * @param split
     * @return 
     * 
     * @author sk89q
     */
    public boolean handleCommand(LocalPlayer player, String[] split) {
        try {
            split[0] = split[0].substring(1);

            // No command found!
            if (!commandManager.hasCommand(split[0])) {
                return false;
            }

            try {
                commandManager.execute(split, player, player);
            } catch (CommandPermissionsException e) {
                player.sendMessage(ChatColor.RED + "You don't have permission to do this.");
            } catch (MissingNestedCommandException e) {
                player.sendMessage(ChatColor.RED + e.getUsage());
            } catch (CommandUsageException e) {
                player.sendMessage(ChatColor.RED + e.getMessage());
                player.sendMessage(ChatColor.RED + e.getUsage());
            } catch (UnhandledCommandException e) {
                return false;
            } catch (WrappedCommandException e) {
                throw e.getCause();
            } catch (CommandException e) {
                player.sendMessage(ChatColor.RED + e.getMessage());
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Number expected; string given.");
            }
        } catch (Throwable e) {
            player.sendMessage(ChatColor.RED + "Please report this error: [See console]");
            player.sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Returns the logging interface
     * 
     * @return 
     */
    public MCBLogger getLogger() {
        return logger;
    }

    /**
     * Returns the server controller instance
     * @return 
     */
    public LocalServer getServer() {
        return server;
    }

    /**
     * Returns the command management controller
     * @return 
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Returns the configuration controller
     * 
     * @return 
     */
    public LocalConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Returns a list of users who are currently logging in.
     * This is used to prevent people spamming login packets
     * in order to DDoS the server.
     * 
     * @return 
     */
    public List<String> getCurrentlyLoggingIn() {
        return currentlyLoggingIn;
    }

    /**
     * Returns the name of the user that was last kicked. This
     * is used to thread the login without showing lots of dual
     * login/out spam messages;
     * 
     * @return 
     */
    public String getLastKickedUser() {
        return lastKickedUser;
    }

    /**
     * Sets the name of the last kicked user
     * 
     * @param lastKickedUser 
     */
    public void setLastKickedUser(String lastKickedUser) {
        this.lastKickedUser = lastKickedUser;
    }

    /**
     * Returns the local API interface. Contains methods to
     * interact with the MCBouncer website.
     * 
     * @return 
     */
    public MCBouncerAPI getAPI() {
        return api;
    }

    /**
     * Returns the version of MCBouncer. It uses the data in
     * META-INF to get the version. (It specifically uses the
     * version in the Implementation-Version tag)
     * 
     * If, for some reason, META-INF is missing, it will 
     * simply return "(unknown)".
     * 
     * @return 
     * 
     * @author sk89q
     */
    public static String getVersion() {
        if (version != null) {
            return version;
        }

        Package p = MCBouncer.class.getPackage();

        if (p == null) {
            p = Package.getPackage("com.mcbouncer");
        }

        if (p == null) {
            version = "(unknown)";
        } else {
            version = p.getImplementationVersion();

            if (version == null) {
                version = "(unknown)";
            }
        }

        return version;
    }
}
