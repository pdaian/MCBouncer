package com.mcbouncer.event;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class AddNoteEvent extends MCBEvent<AddNoteEvent> {

    private String user;
    private String issuer;
    private String note;
    public static final MCBHandlerList<AddNoteEvent> handlers = new MCBHandlerList<AddNoteEvent>();

    public AddNoteEvent(String user, String issuer, String note) {
        this.user = user;
        this.issuer = issuer;
        this.note = note;
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

    @Override
    protected MCBHandlerList<AddNoteEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "AddNote";
    }
}
