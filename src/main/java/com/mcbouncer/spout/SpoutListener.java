package com.mcbouncer.spout;

import com.mcbouncer.MCBouncer;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerChatEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.event.player.PlayerKickEvent;
import org.spout.api.event.player.PlayerPreLoginEvent;
import org.spout.api.event.server.PreCommandEvent;

public class SpoutListener implements Listener {
    
    protected MCBouncer controller;

    public SpoutListener(MCBouncer controller) {
        this.controller = controller;
    }

    @EventHandler(event = PlayerChatEvent.class, order = Order.EARLIEST)
    public void onPlayerChat(PlayerChatEvent event) {
    }

    @EventHandler(event = PlayerJoinEvent.class, order = Order.LATEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
    }
    
    @EventHandler(event = PlayerPreLoginEvent.class, order = Order.EARLIEST)
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        /*LoginEvent newEvent = new LoginEvent(controller, event.getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.disallow(Result.KICK_OTHER, "Unexpected error, try again in a few minutes.");
        }*/
    }
    
    @EventHandler(event = PreCommandEvent.class, order = Order.LATEST)
    public void onPreCommand(PreCommandEvent event) {
    }
    
    @EventHandler(event = PlayerKickEvent.class, order = Order.LATEST)
    public void onPlayerKick(PlayerKickEvent event) {
    }
}
