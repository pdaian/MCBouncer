package com.mcbouncer.bukkit;

import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.LocalPlugin;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.bukkit.listeners.BukkitPlayerListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPlayerName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIPAddress(String ipOrName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void kickPlayer(String name, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void kickPlayerWithIP(String name, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageMods(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void broadcastMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
