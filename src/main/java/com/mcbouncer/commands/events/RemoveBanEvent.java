package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.util.BanType;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class RemoveBanEvent extends MCBEvent<RemoveBanEvent> implements Cancellable {

    private LocalPlayer issuer;
    private String user;
    private BanType banType;
    public static final MCBHandlerList<RemoveBanEvent> handlers = new MCBHandlerList<RemoveBanEvent>();

    public RemoveBanEvent(BanType banType, LocalPlayer issuer, String user) {
        this.banType = banType;
        this.issuer = issuer;
        this.user = user;
    }

    public LocalPlayer getIssuer() {
        return issuer;
    }

    public void setIssuer(LocalPlayer issuer) {
        this.issuer = issuer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BanType getBanType() {
        return banType;
    }

    public void setBanType(BanType banType) {
        this.banType = banType;
    }

    @Override
    protected MCBHandlerList<RemoveBanEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "RemoveBan";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
