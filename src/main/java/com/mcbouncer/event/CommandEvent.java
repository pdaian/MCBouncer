package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class CommandEvent extends MCBEvent<CommandEvent> implements Cancellable {

    protected MCBouncer controller;
    protected String user;
    public static final MCBHandlerList<CommandEvent> handlers = new MCBHandlerList<CommandEvent>();

    public CommandEvent(MCBouncer controller, String user) {
        this.controller = controller;
        this.user = user;
    }

    @Override
    protected MCBHandlerList<CommandEvent> getHandlers() {
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
        return "CommandEvent";
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
