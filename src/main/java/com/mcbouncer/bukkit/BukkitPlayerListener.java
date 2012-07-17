package com.mcbouncer.bukkit;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.JoinEvent;
import net.lahwran.fevents.MCBEventHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

/**
 * Main listener class for Bukkit. Very little logic is handled
 * here. The purpose of this class is to connect Bukkit events
 * to generic internal kick, login, etc. events. 
 * 
 */
public class BukkitPlayerListener implements Listener {

    protected MCBouncer controller;

    public BukkitPlayerListener(MCBouncer controller) {
        this.controller = controller;
    }
    
    @EventHandler(priority= EventPriority.HIGHEST)
    public void onPlayerPreLoginAsync(AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        String ip = event.getAddress().getHostAddress();
        
        JoinEvent joinEvent = new JoinEvent(controller, name, ip);
        MCBEventHandler.callEvent(joinEvent);
        
        if (joinEvent.isCancelled()) {
            event.disallow(Result.KICK_OTHER, "Unexpected error, try again in a few minutes.");
        }
    }
}