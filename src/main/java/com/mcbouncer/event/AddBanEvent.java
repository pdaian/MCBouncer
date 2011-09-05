package com.mcbouncer.event;

import com.mcbouncer.util.BanType;

public class AddBanEvent extends MCBEvent {
    
    private String user;
    private String issuer;
    private String reason;
    
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

    private BanType banType;
    
    public BanType getBanType()
    {
        return banType;
    }
    
    @Override
    public Type getType() {
        return Type.ADD_BAN;
    }

}
