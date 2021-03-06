package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Called after a note is added. 
 * 
 * This event cannot be cancelled.
 * 
 */
public class NoteAddedEvent extends MCBEvent<NoteAddedEvent> {

    protected String user;
    protected LocalPlayer issuer;
    protected String note;
    protected boolean success;
    protected String error;
    public static final MCBHandlerList<NoteAddedEvent> handlers = new MCBHandlerList<NoteAddedEvent>();

    public NoteAddedEvent(String user, LocalPlayer issuer, String note, boolean success, String error) {
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
    protected MCBHandlerList<NoteAddedEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "NoteAdded";
    }
}
