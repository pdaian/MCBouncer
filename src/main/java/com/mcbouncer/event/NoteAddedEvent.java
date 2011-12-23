package com.mcbouncer.event;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class NoteAddedEvent extends MCBEvent<NoteAddedEvent> {

    String user;
    String issuer;
    String note;
    boolean success;
    String error;
    public static final MCBHandlerList<NoteAddedEvent> handlers = new MCBHandlerList<NoteAddedEvent>();

    public NoteAddedEvent(String user, String issuer, String note, boolean success, String error) {
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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
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
    protected MCBHandlerList<NoteAddedEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "NoteAdded";
    }
}
