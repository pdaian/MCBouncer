package com.mcbouncer.bukkit.listeners;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.ChatEvent;
import com.mcbouncer.event.CommandEvent;
import com.mcbouncer.event.JoinEvent;
import com.mcbouncer.event.KickEvent;
import com.mcbouncer.event.LoginEvent;
import net.lahwran.fevents.MCBEventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

public class BukkitPlayerListener extends PlayerListener {

    protected MCBouncer controller;

    public BukkitPlayerListener(MCBouncer controller) {
        this.controller = controller;
    }

    @Override
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        LoginEvent newEvent = new LoginEvent(controller, event.getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.disallow(Result.KICK_OTHER, "Unexpected error, try again in a few minutes.");
        }

    }

    @Override
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer() instanceof Player) {
            CommandEvent newEvent = new CommandEvent(controller, event.getPlayer().getName());
            MCBEventHandler.callEvent(newEvent);

            if (newEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.getPlayer() instanceof Player) {
            ChatEvent newEvent = new ChatEvent(controller, event.getPlayer().getName());
            MCBEventHandler.callEvent(newEvent);

            if (newEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) {
        KickEvent newEvent = new KickEvent(controller, event.getPlayer().getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setLeaveMessage(null);
        }
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        String ip = event.getPlayer().getAddress().getAddress().getHostAddress();
        
        Thread r = new PlayerJoinThread(name, ip, event.getJoinMessage());
        r.start();
        event.setJoinMessage(null);
    }

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