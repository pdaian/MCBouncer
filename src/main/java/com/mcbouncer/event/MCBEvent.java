package com.mcbouncer.event;

public abstract class MCBEvent {

    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public abstract MCBEventType getType();

}