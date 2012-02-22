package com.mcbouncer.event;

import com.mcbouncer.MCBouncer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Internal generic incoming block break event.
 * 
 * Cancelling this event will prevent the block
 * break.
 * 
 */
public class BlockBreakEvent extends MCBEvent<BlockBreakEvent> implements Cancellable {

    protected MCBouncer controller;
    protected String user;
    public static final MCBHandlerList<BlockBreakEvent> handlers = new MCBHandlerList<BlockBreakEvent>();
    
    public BlockBreakEvent(MCBouncer controller, String user) {
        this.controller = controller;
        this.user = user;
    }
    
    @Override
    protected MCBHandlerList<BlockBreakEvent> getHandlers() {
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
        return "BlockBreakEvent";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
