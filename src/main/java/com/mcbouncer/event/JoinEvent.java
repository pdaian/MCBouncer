package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class JoinEvent extends MCBEvent<JoinEvent> {
    
    protected MCBouncer controller;
    protected String user;
    protected String ip;
    protected String message;
    public static final MCBHandlerList<JoinEvent> handlers = new MCBHandlerList<JoinEvent>();

    public JoinEvent(MCBouncer controller, String user, String ip, String message) {
        this.controller = controller;
        this.user = user;
        this.ip = ip;
        this.message = message;
    }

    public MCBouncer getController() {
        return controller;
    }

    public String getMessage() {
        return message;
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
