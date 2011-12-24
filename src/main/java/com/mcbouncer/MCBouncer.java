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
import com.mcbouncer.http.Transport;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBLogger;
import com.mcbouncer.util.commands.CommandManager;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MCBouncer {

    protected final MCBLogger logger;
    protected LocalPlugin plugin;
    protected CommandManager commandManager;
    protected LocalConfiguration configuration;
    protected MCBouncerAPI api;
    protected static String version;
    protected List<String> currentlyLoggingIn = new ArrayList<String>();
    protected String lastKickedUser = "";

    static {
        getVersion();
    }

    public MCBouncer(LocalPlugin plugin, LocalConfiguration configuration) {
        this.logger = new MCBLogger(this);

        this.plugin = plugin;
        this.configuration = configuration;

        commandManager = new CommandManager(this);
        commandManager.register(BanCommands.class);
        commandManager.register(GeneralCommands.class);
        commandManager.register(MCBouncerCommands.class);
        commandManager.register(NoteCommands.class);

    }

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

    public MCBLogger getLogger() {
        return logger;
    }

    public LocalPlugin getPlugin() {
        return plugin;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public LocalConfiguration getConfiguration() {
        return configuration;
    }

    public Transport getTransport() {
        return new Transport(this);
    }

    public List<String> getCurrentlyLoggingIn() {
        return currentlyLoggingIn;
    }

    public String getLastKickedUser() {
        return lastKickedUser;
    }

    public void setLastKickedUser(String lastKickedUser) {
        this.lastKickedUser = lastKickedUser;
    }

    public MCBouncerAPI getAPI() {
        return api;
    }

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
