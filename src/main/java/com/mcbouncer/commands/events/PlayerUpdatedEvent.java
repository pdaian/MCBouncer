package com.mcbouncer.commands.events;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * TODO: Implement this.
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
