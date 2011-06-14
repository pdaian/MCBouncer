package com.mcbouncer.plugin;

import com.mcbouncer.plugin.command.*;
import com.mcbouncer.util.MCBouncerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {

    /**
     * Permissions class
     */
    public PermissionHandler permissionHandler;
    /**
     * Logging class, provides debugging
     */
    public static final MCBLogger log = new MCBLogger();
    /**
     * Command handler class
     */
    private MCBCommands commandHandler;
    /**
     * ArrayList of all muted players
     */
    public ArrayList<Player> muted = new ArrayList<Player>();

    /**
     * Simply outputs a message when disabled
     */
    @Override
    public void onDisable() {
        log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");
    }

    /**
     * Performs awesome loading action
     */
    @Override
    public void onEnable() {

        MCBouncerConfig.load(this.getDataFolder());
        setupPermissions();

        MCBPlayerListener pl = new MCBPlayerListener(this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Event.Priority.High, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, pl, Event.Priority.High, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, pl, Event.Priority.High, this);

        this.commandHandler = new MCBCommands(this);
        this.commandHandler.registerCommand("ban", new BanCommand(this));
        this.commandHandler.registerCommand("banip", new BanipCommand(this));
        this.commandHandler.registerCommand("unban", new UnbanCommand(this));
        this.commandHandler.registerCommand("unbanip", new UnbanipCommand(this));
        this.commandHandler.registerCommand("kick", new KickCommand(this));
        this.commandHandler.registerCommand("lookup", new LookupCommand(this));
        this.commandHandler.registerCommand("mcb-lookup", new McbLookupCommand(this));
        this.commandHandler.registerCommand("mute", new MuteCommand(this));
        this.commandHandler.registerCommand("unmute", new UnmuteCommand(this));

        log.info("MCBouncer successfully initiated");
        log.debug("Debug mode enabled!");
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                log.info("Permission system not detected, defaulting to OP");
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        CommandThread r = new CommandThread(this, sender, command, commandLabel, args);
        r.start();
        return true;
    }

    public boolean hasPermission(Player player, String permission) {
        if (this.permissionHandler == null) {
            return player.isOp();
        } else {
            return this.permissionHandler.has(player, permission);
        }
    }

    public void messageMods(String message) {
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (this.hasPermission(player, "mcbouncer.mod")) {
                player.sendMessage(message);
            }
        }
    }

    public class CommandThread extends Thread {

        MCBouncer parent;
        CommandSender sender;
        Command command;
        String commandLabel;
        String[] args;

        public CommandThread(MCBouncer parent, CommandSender sender, Command command, String commandLabel, String[] args) {
            this.parent = parent;
            this.sender = sender;
            this.command = command;
            this.commandLabel = commandLabel;
            this.args = args;
        }

        @Override
        public void run() {
            if (!parent.commandHandler.onCommand(sender, command, commandLabel, args)) {

                if (command.getUsage().length() > 0) {
                    for (String line : command.getUsage().replace("<command>", commandLabel).split("\n")) {
                        sender.sendMessage(line);
                    }
                }

            }


        }
    }
}
