package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {

    public static PermissionHandler permissionHandler;

    @Override
    public void onDisable() {
        System.out.println("Closing MCBouncer!");
    }

    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        final MCBPlayerListener pl = new MCBPlayerListener(this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Priority.High, this);
        setupPermissions();
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                this.getServer().getLogger().info("Permission system not detected, defaulting to OP");
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String senderName = "console";
        if (sender instanceof Player) {
            if (!this.permissionHandler.has((Player) sender, "mcbouncer.mod")) {
                return false;
            }
            senderName = ((Player) sender).getName();

        }
        if (command.getName().equalsIgnoreCase("ban")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.GREEN + "You must specify a user. Type /ban for more info.");
                return false;
            }
            String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : ""); // Stuff goes here
            if (args.length == 1) {
            }
            MCBouncerUtil.addBan(args[0], senderName, reason);

        } else if (command.getName().equalsIgnoreCase("unban")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.GREEN+"You must specify a user, and only one arg. Type /unban for more info.");
                return false;
            }
            MCBouncerUtil.removeBan(args[0]);
        }
        else
            return false;
        return true;
    }
}
