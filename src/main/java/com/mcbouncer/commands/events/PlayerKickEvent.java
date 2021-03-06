package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBHandlerList;
import net.lahwran.fevents.MCBEvent;

/**
 * Called before a player is kicked. 
 * 
 * Cancelling this event cancels the kick, and
 * the user will be allowed to roam free. 
 * 
 * Any fields modified here will be the variables
 * that get acted upon. 
 * 
 */
public class PlayerKickEvent extends MCBEvent<PlayerKickEvent> implements Cancellable {

    private String user;
    private LocalPlayer issuer;
    private String reason;
    public static final MCBHandlerList<PlayerKickEvent> handlers = new MCBHandlerList<PlayerKickEvent>();

    public PlayerKickEvent(String user, LocalPlayer issuer, String reason) {
        this.user = user;
        this.issuer = issuer;
        this.reason = reason;
    }

    public String getPlayer() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalPlayer getIssuer() {
        return issuer;
    }

    public void setIssuer(LocalPlayer issuer) {
        this.issuer = issuer;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    protected MCBHandlerList<PlayerKickEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "PlayerKick";
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
