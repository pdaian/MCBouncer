package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class ChatEvent extends MCBEvent<ChatEvent> implements Cancellable {

    protected MCBouncer controller;
    protected String user;
    public static final MCBHandlerList<ChatEvent> handlers = new MCBHandlerList<ChatEvent>();

    public ChatEvent(MCBouncer controller, String user) {
        this.controller = controller;
        this.user = user;
    }

    @Override
    protected MCBHandlerList<ChatEvent> getHandlers() {
        return handlers;
    }

    public MCBouncer getController() {
        return controller;
    }

    public String getUser() {
        return user;
    }

    @Override
    protected String getEventName() {
        return "ChatEvent";
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}