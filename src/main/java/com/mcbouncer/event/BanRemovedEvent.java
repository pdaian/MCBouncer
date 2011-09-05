package com.mcbouncer.event;

import com.mcbouncer.util.BanType;

public class BanRemovedEvent extends MCBEvent {

    private BanType banType;
    private String issuer;
    private String user;
    private boolean success;
    private String error;
    
    public BanRemovedEvent(BanType banType, String issuer, String user, boolean success, String error) {
        this.banType = banType;
        this.issuer = issuer;
        this.user = user;
        this.success = success;
        this.error = error;
    }
    
    public BanType getBanType() {
        return banType;
    }



    public void setBanType(BanType banType) {
        this.banType = banType;
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



    public boolean isSuccess() {
        return success;
    }



    public void setSuccess(boolean success) {
        this.success = success;
    }



    public String getError() {
        return error;
    }



    public void setError(String error) {
        this.error = error;
    }



    @Override
    public Type getType() {
        return Type.BAN_REMOVED;
    }

}
