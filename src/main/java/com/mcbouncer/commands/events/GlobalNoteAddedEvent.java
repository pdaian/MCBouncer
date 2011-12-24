package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class GlobalNoteAddedEvent extends MCBEvent<GlobalNoteAddedEvent> {

    protected String user;
    protected LocalPlayer issuer;
    protected String note;
    protected boolean success;
    protected String error;
    public static final MCBHandlerList<GlobalNoteAddedEvent> handlers = new MCBHandlerList<GlobalNoteAddedEvent>();

    public GlobalNoteAddedEvent(String user, LocalPlayer issuer, String note, boolean success, String error) {
        this.user = user;
        this.issuer = issuer;
        this.note = note;
        this.success = success;
        this.error = error;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
    protected MCBHandlerList<GlobalNoteAddedEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "GlobalNoteAdded";
    }
}
