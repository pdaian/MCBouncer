package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBLogger;
import com.mcbouncer.command.*;
//import com.mcbouncer.plugin.BouncerPlugin;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.config.MCBConfiguration;
import java.net.UnknownHostException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.net.InetAddress;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {
    
    public final static String version = "1.0";

    public PermissionHandler permissionHandler;
    public static final MCBLogger log = new MCBLogger();
    public HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();

    @Override
    public void onDisable() {
        log.info("Plugin disabled. (version " + MCBouncer.version + ")");
    }

    @Override
    public void onEnable() {
        
        setupConfiguration();
        setupPermissions();
        setupListeners();
        setupCommands();
        
        try {            
            MCBouncerAPI.website = "http://" + InetAddress.getByName("www.mcbouncer.com").getHostAddress();
        } catch (UnknownHostException ex) {
            MCBouncerAPI.website = "http://mcbouncer.com";
        }
        log.debug("Website for MCBouncer: " + MCBouncerAPI.website );

        log.info("Plugin enabled. (version " + MCBouncer.version + ")");
        log.debug("Debug mode enabled!");
        
        if( MCBConfiguration.isDebugMode() ) {
            MCBouncerAPI.website = "http://mcb.thezomg.com";
        }
    }

    protected void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                log.debug("Permissions found, using that for Permissions.");
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                log.info("Permissions plugin not detected, defaulting to ops.txt.");
            }
        }
    }
    
    protected void setupListeners() {
        MCBPlayerListener pl = new MCBPlayerListener(this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_PRELOGIN, pl, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, pl, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, pl, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, pl, Event.Priority.Highest, this);
    }
    
    protected void setupCommands() {
        this.commands.put("ban", new BanCommand(this));
        this.commands.put("banip", new BanipCommand(this));
        this.commands.put("unban", new UnbanCommand(this));
        this.commands.put("unbanip", new UnbanipCommand(this));
        this.commands.put("kick", new KickCommand(this));
        this.commands.put("lookup", new LookupCommand(this));
        this.commands.put("mcbouncer", new MCBCommand(this));
        this.commands.put("addnote", new AddnoteCommand(this));
        this.commands.put("removenote", new RemovenoteCommand(this));
    }
    
    public void setupConfiguration() {
        new MCBConfiguration().load(this.getDataFolder());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        MCBCommandThread r = new MCBCommandThread(this, sender, command, commandLabel, args);
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

}
