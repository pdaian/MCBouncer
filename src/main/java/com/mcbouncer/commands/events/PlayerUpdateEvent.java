package com.mcbouncer.commands.events;

import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Called before a user's login is sent to the website. 
 * 
 * Cancelling this event cancels the connection to
 * the website, so the login will not be registered.
 * 
 * Any fields modified here will be the variables
 * that get acted upon. 
 * 
 */
public class PlayerUpdateEvent extends MCBEvent<PlayerUpdateEvent> implements Cancellable {

    public static final MCBHandlerList<PlayerUpdateEvent> handlers = new MCBHandlerList<PlayerUpdateEvent>();

    @Override
    protected MCBHandlerList<PlayerUpdateEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "PlayerUpdate";
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
