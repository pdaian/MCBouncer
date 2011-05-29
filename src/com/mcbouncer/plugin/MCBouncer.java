package com.mcbouncer.plugin;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

import org.bukkit.entity.Player;
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
        final MCBPlayerListener pl = new MCBPlayerListener();
        pm.registerEvent(Event.Type.PLAYER_QUIT, pl, Priority.High, this);
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
        return true;
    }
}
