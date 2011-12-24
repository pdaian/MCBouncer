package com.mcbouncer.commands.events;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Called after a player is update on the website. 
 * 
 * This event cannot be cancelled.
 * 
 */
public class PlayerUpdatedEvent extends MCBEvent<PlayerUpdatedEvent> {

    public static final MCBHandlerList<PlayerUpdatedEvent> handlers = new MCBHandlerList<PlayerUpdatedEvent>();

    @Override
    protected MCBHandlerList<PlayerUpdatedEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "PlayerUpdated";
    }

}
