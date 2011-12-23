package com.mcbouncer.event;

import com.mcbouncer.util.BanType;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class RemoveBanEvent extends MCBEvent<RemoveBanEvent> {

    private String issuer;
    private String user;
    private BanType banType;
    public static final MCBHandlerList<RemoveBanEvent> handlers = new MCBHandlerList<RemoveBanEvent>();

    public RemoveBanEvent(BanType banType, String issuer, String user) {
        this.banType = banType;
        this.issuer = issuer;
        this.user = user;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
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
}
