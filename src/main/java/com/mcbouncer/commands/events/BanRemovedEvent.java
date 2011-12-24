package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.util.BanType;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class BanRemovedEvent extends MCBEvent<BanRemovedEvent> {

    private BanType banType;
    private LocalPlayer issuer;
    private String user;
    private boolean success;
    private String error;
    public static final MCBHandlerList<BanRemovedEvent> handlers = new MCBHandlerList<BanRemovedEvent>();

    public BanRemovedEvent(BanType banType, LocalPlayer issuer, String user, boolean success, String error) {
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
    protected MCBHandlerList<BanRemovedEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "BanRemoved";
    }
}
