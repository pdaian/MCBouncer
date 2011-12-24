package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class LoginEvent extends MCBEvent<LoginEvent> implements Cancellable {
    
    protected MCBouncer controller;
    protected String user;
    public static final MCBHandlerList<LoginEvent> handlers = new MCBHandlerList<LoginEvent>();

    public LoginEvent(MCBouncer controller, String user) {
        this.controller = controller;
        this.user = user;
    }

    public MCBouncer getController() {
        return controller;
    }

    public String getUser() {
        return user;
    }

    @Override
    protected MCBHandlerList<LoginEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "LoginEvent";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
