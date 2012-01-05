package com.mcbouncer.spout;

import com.mcbouncer.MCBouncer;
import org.getspout.api.event.EventHandler;
import org.getspout.api.event.Listener;
import org.getspout.api.event.Order;
import org.getspout.api.event.player.PlayerChatEvent;
import org.getspout.api.event.player.PlayerJoinEvent;
import org.getspout.api.event.player.PlayerKickEvent;
import org.getspout.api.event.player.PlayerPreLoginEvent;
import org.getspout.api.event.server.PreCommandEvent;

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
