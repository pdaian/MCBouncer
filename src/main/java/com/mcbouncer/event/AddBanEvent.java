package com.mcbouncer.event;

import com.mcbouncer.util.BanType;
import net.lahwran.fevents.MCBHandlerList;
import net.lahwran.fevents.MCBEvent;

public class AddBanEvent extends MCBEvent<AddBanEvent> {

    private String user;
    private String issuer;
    private String reason;
    private BanType banType;
    public static final MCBHandlerList<AddBanEvent> handlers = new MCBHandlerList<AddBanEvent>();

    public AddBanEvent(BanType banType, String user, String issuer, String reason) {
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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
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
}
