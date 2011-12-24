package com.mcbouncer.bukkit;

import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.LocalPlugin;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.bukkit.listeners.BukkitPlayerListener;
import com.mcbouncer.util.NetUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin implements LocalPlugin {

    protected MCBouncer controller;
    
    public void onDisable() {
        controller.getLogger().info("Plugin disabled (version " + MCBouncer.getVersion() + ")");
    }

    public void onEnable() {
        
        LocalConfiguration config = new BukkitConfiguration(this.getDataFolder());
        controller = new MCBouncer(this, config);
        
        controller.getLogger().info("Plugin enabled. (version " + MCBouncer.getVersion() + ")");
        controller.getLogger().debug("Debug mode enabled!");
        
        setupListeners();
    }
    
    protected void setupListeners() {
        BukkitPlayerListener pl = new BukkitPlayerListener(controller);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_PRELOGIN, pl, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, pl, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, pl, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, pl, Event.Priority.Highest, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BukkitCommandThread r = new BukkitCommandThread(controller, sender, command, label, args);
        r.start();
        return true;
    }

    public boolean isPlayerOnline(String name) {
        return this.getServer().getPlayer(name) != null;
    }

    public String getPlayerName(String name) {
        Player player = this.getServer().getPlayer(name);
        if( player == null ) {
            return name;
        }
        return player.getName();
    }

    public String getIPAddress(String ipOrName) {
        if( NetUtils.isIPAddress(ipOrName) ) {
            return ipOrName;
        }
        
        Player player = this.getServer().getPlayer(ipOrName);
        if( player == null ) {
            return "0.0.0.0";
        }
        return player.getAddress().getAddress().getHostAddress();
    }

    public void kickPlayer(String name, String reason) {
        Player player = this.getServer().getPlayer(name);
        if( player != null ) {
            player.kickPlayer(reason);
        }
    }

    public void kickPlayerWithIP(String ip, String reason) {
        for( Player player : this.getServer().getOnlinePlayers() ) {
            if( player.getAddress().getAddress().getHostAddress().equals(ip) ) {
                player.kickPlayer(reason);
            }
        }
    }

    public void messageMods(String string) {
        for( Player player : this.getServer().getOnlinePlayers() ) {
            if( player.hasPermission("mcbouncer.mod") ) {
                player.sendMessage(string);
            }
        }
    }

    public void broadcastMessage(String message) {
        for( Player player : this.getServer().getOnlinePlayers() ) {
            player.sendMessage(message);
        }
    }
    
}
