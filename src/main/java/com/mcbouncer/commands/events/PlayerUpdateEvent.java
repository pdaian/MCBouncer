package com.mcbouncer.commands.events;

import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

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
