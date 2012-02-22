package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Internal generic incoming block place event.
 * 
 * Cancelling this event will prevent the block
 * placement.
 * 
 */
public class BlockPlaceEvent extends MCBEvent<BlockPlaceEvent> implements Cancellable {

    protected MCBouncer controller;
    protected String user;
    public static final MCBHandlerList<BlockPlaceEvent> handlers = new MCBHandlerList<BlockPlaceEvent>();
    
    public BlockPlaceEvent(MCBouncer controller, String user) {
        this.controller = controller;
        this.user = user;
    }
    
    @Override
    protected MCBHandlerList<BlockPlaceEvent> getHandlers() {
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
        return "BlockPlaceEvent";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
