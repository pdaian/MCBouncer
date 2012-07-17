package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Internal generic game join event.
 * 
 * This is called after the user has logged
 * in, but before it has joined that game.
 * 
 * This event cannot be cancelled.
 * 
 */
public class JoinEvent extends MCBEvent<JoinEvent> {

    protected MCBouncer controller;
    protected String user;
    protected String ip;
    public static final MCBHandlerList<JoinEvent> handlers = new MCBHandlerList<JoinEvent>();

    public JoinEvent(MCBouncer controller, String user, String ip) {
        this.controller = controller;
        this.user = user;
        this.ip = ip;
    }

    public MCBouncer getController() {
        return controller;
    }

    public String getUser() {
        return user;
    }

    public String getIP() {
        return ip;
    }

    @Override
    protected MCBHandlerList<JoinEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "JoinEvent";
    }
}
