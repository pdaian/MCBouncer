package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {

    public static PermissionHandler permissionHandler;
    public static final MCBLogger log = new MCBLogger();
    private MCBCommands commandHandler;

    @Override
    public void onDisable() {
        log.info("Closing MCBouncer!");
    }

    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        final MCBPlayerListener pl = new MCBPlayerListener(this);
        
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Priority.High, this);
        setupPermissions();
        MCBouncerConfig.load(this.getDataFolder());
        this.commandHandler = MCBCommands.load(this);
        
        log.info("MCBouncer successfully initiated");
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (MCBouncer.permissionHandler == null) {
            if (permissionsPlugin != null) {
                MCBouncer.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                log.info("Permission system not detected, defaulting to OP");
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return this.commandHandler.onCommand(sender, command, commandLabel, args);
    }
    
    
    public boolean permission( Player player, String permission ) {
        if (this.permissionHandler == null ) {
            return player.isOp();
        }
        else {
            return this.permissionHandler.has(player, permission);
        }
    }

}
