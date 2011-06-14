package com.mcbouncer.plugin;

import com.mcbouncer.plugin.command.*;
import com.mcbouncer.util.MCBouncerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
     * ArrayList of all muted players
     */
    public ArrayList<String> muted = new ArrayList<String>();
    
    public HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();

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

        this.commands.put("ban", new BanCommand(this));
        this.commands.put("banip", new BanipCommand(this));
        this.commands.put("unban", new UnbanCommand(this));
        this.commands.put("unbanip", new UnbanipCommand(this));
        this.commands.put("kick", new KickCommand(this));
        this.commands.put("lookup", new LookupCommand(this));
        this.commands.put("mcb-lookup", new McbLookupCommand(this));
        this.commands.put("mute", new MuteCommand(this));
        this.commands.put("unmute", new UnmuteCommand(this));

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
            if (!this.onCommand()) {

                if (command.getUsage().length() > 0) {
                    for (String line : command.getUsage().replace("<command>", commandLabel).split("\n")) {
                        sender.sendMessage(line);
                    }
                }

            }


        }

        public boolean onCommand() {

            if (sender instanceof Player) {
                if (!parent.hasPermission((Player) sender, "mcbouncer.mod")) {
                    return false;
                }
            }

            String commandName = command.getName().toLowerCase();

            //What's this hackery? /command arg  arg2 threw an error. Strip unnecessary spaces
            List<String> temp_list = new LinkedList<String>();
            temp_list.addAll(Arrays.asList(args));
            while (temp_list.contains("")) {
                temp_list.remove("");
            }
            args = temp_list.toArray(new String[0]);

            try {
                if (!parent.commands.containsKey(commandName)) {

                    return false;
                }

                BaseCommand commandClass = parent.commands.get(commandName);
                commandClass.setArgs(args);
                commandClass.setParent(parent);
                commandClass.setSender(sender);
                return commandClass.runCommand();

            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }

        }
    }

}
