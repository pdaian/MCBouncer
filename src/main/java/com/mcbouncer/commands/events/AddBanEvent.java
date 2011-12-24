package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.util.BanType;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBHandlerList;
import net.lahwran.fevents.MCBEvent;

/**
 * Called before a ban is added. 
 * 
 * Cancelling this event cancels the addition
 * of the ban, and the user will be allowed to
 * roam free. 
 * 
 * Any fields modified here will be the variables
 * that get acted upon. 
 * 
 */
public class AddBanEvent extends MCBEvent<AddBanEvent> implements Cancellable {

    private String user;
    private LocalPlayer issuer;
    private String reason;
    private BanType banType;
    public static final MCBHandlerList<AddBanEvent> handlers = new MCBHandlerList<AddBanEvent>();

    public AddBanEvent(BanType banType, String user, LocalPlayer issuer, String reason) {
        this.banType = banType;
        this.user = user;
        this.issuer = issuer;
        this.reason = reason;
    }

    public String getUser() {
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

    public void setBanType(BanType banType) {
        this.banType = banType;
    }
    
    public BanType getBanType() {
        return banType;
    }

    @Override
    protected MCBHandlerList<AddBanEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "AddBan";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
