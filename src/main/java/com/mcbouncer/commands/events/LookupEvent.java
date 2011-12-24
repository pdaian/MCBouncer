package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class LookupEvent extends MCBEvent<LookupEvent> implements Cancellable {

    private LocalPlayer issuer;
    private String player;
    public static final MCBHandlerList<LookupEvent> handlers = new MCBHandlerList<LookupEvent>();

    public LookupEvent(LocalPlayer issuer, String player) {
        this.issuer = issuer;
        this.player = player;
    }

    public LocalPlayer getIssuer() {
        return issuer;
    }

    public void setIssuer(LocalPlayer issuer) {
        this.issuer = issuer;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    protected MCBHandlerList<LookupEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "Lookup";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
