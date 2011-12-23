package com.mcbouncer.event;

import com.mcbouncer.util.BanType;

public class RemoveBanEvent extends MCBEvent {

    private String issuer;
    private String user;
    private BanType banType;
    
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
    public MCBEventType getType() {
        return MCBEventType.REMOVE_BAN;
    }

}
