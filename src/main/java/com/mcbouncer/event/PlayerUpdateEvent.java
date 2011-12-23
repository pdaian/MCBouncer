package com.mcbouncer.event;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class PlayerUpdateEvent extends MCBEvent<PlayerUpdateEvent> {

    public static final MCBHandlerList<PlayerUpdateEvent> handlers = new MCBHandlerList<PlayerUpdateEvent>();

    @Override
    protected MCBHandlerList<PlayerUpdateEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "PlayerUpdate";
    }
}
