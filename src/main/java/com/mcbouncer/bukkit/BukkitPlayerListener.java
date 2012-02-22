package com.mcbouncer.bukkit;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.ChatEvent;
import com.mcbouncer.event.CommandEvent;
import com.mcbouncer.event.JoinEvent;
import com.mcbouncer.event.KickEvent;
import com.mcbouncer.event.LoginEvent;
import net.lahwran.fevents.MCBEventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
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

    /**
     * Bukkit PreLogin event, calls the internal LoginEvent.
     * 
     * If the event is cancelled, it means that the user
     * is already trying to log in, and this event should
     * be disallowed.
     * 
     * @param event 
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        LoginEvent newEvent = new LoginEvent(controller, event.getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.disallow(Result.KICK_OTHER, "Unexpected error, try again in a few minutes.");
        }

    }

    /**
     * Bukkit Command event, calls the internal CommandEvent.
     * 
     * This basically checks if a user is still trying to log in,
     * and if so, to disallow the event. This is to prevent users
     * from spamming chat in the time that it takes to do a lookup.
     * 
     * @param event 
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer() instanceof Player) {
            CommandEvent newEvent = new CommandEvent(controller, event.getPlayer().getName());
            MCBEventHandler.callEvent(newEvent);

            if (newEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Bukkit Chat event, calls the internal ChatEvent.
     * 
     * This basically checks if a user is still trying to log in,
     * and if so, to disallow the event. This is to prevent users
     * from spamming chat in the time that it takes to do a lookup.
     * 
     * @param event 
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.getPlayer() instanceof Player) {
            ChatEvent newEvent = new ChatEvent(controller, event.getPlayer().getName());
            MCBEventHandler.callEvent(newEvent);

            if (newEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
    
    /**
     * Bukkit Block Break event, calls the internal BlockBreakEvent.
     * 
     * This basically checks if a user is still trying to log in,
     * and if so, to disallow the event. This is to prevent users
     * from removing blocks in the time that it takes to do a lookup.
     * 
     * @param event 
     */
    @EventHandler(priority= EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer() instanceof Player) {
            com.mcbouncer.event.BlockBreakEvent newEvent = new com.mcbouncer.event.BlockBreakEvent(controller, event.getPlayer().getName());
            MCBEventHandler.callEvent(newEvent);
            
            if (newEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
    
    /**
     * Bukkit Block Place event, calls the internal BlockPlaceEvent.
     * 
     * This basically checks if a user is still trying to log in,
     * and if so, to disallow the event. This is to prevent users
     * from placing blocks in the time that it takes to do a lookup.
     * 
     * @param event 
     */
    @EventHandler(priority= EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer() instanceof Player) {
            com.mcbouncer.event.BlockPlaceEvent newEvent = new com.mcbouncer.event.BlockPlaceEvent(controller, event.getPlayer().getName());
            MCBEventHandler.callEvent(newEvent);
            
            if (newEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Bukkit Kick event, calls the internal KickEvent.
     * 
     * This is part of the threading logic. If the event
     * is cancelled, this means that the leave message
     * should be cancelled. This will kick the user
     * without showing it to other players.
     * 
     * @param event 
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent event) {
        KickEvent newEvent = new KickEvent(controller, event.getPlayer().getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setLeaveMessage(null);
        }
    }

    /**
     * Bukkit Join event, calls the internal JoinEvent.
     * 
     * This is part of the threading logic. The join
     * message is always cancelled, to prevent a join
     * message for banned users while a lookup is in
     * progress. If they are not banned, it should do
     * a global broadcast of a join message to make up
     * for the missing join message earlier.
     * 
     * @param event 
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        String ip = event.getPlayer().getAddress().getAddress().getHostAddress();

        Thread r = new PlayerJoinThread(name, ip, event.getJoinMessage());
        r.start();
        event.setJoinMessage(null);
    }

    /**
     * Thread class to connect to the MCBouncer server
     * in a thread. This lets users into the server quicker,
     * and prevents banned users from spamming the server.
     */
    public class PlayerJoinThread extends Thread {

        protected String player;
        protected String ip;
        protected String message;

        public PlayerJoinThread(String player, String ip, String message) {
            this.player = player;
            this.ip = ip;
            this.message = message;
        }

        @Override
        public void run() {
            JoinEvent newEvent = new JoinEvent(controller, player, ip, message);
            MCBEventHandler.callEvent(newEvent);
        }
    }
}