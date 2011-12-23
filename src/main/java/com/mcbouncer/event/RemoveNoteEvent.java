package com.mcbouncer.event;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class RemoveNoteEvent extends MCBEvent<RemoveNoteEvent> {

    private String issuer;
    private Integer noteId;
    public static final MCBHandlerList<RemoveNoteEvent> handlers = new MCBHandlerList<RemoveNoteEvent>();

    public RemoveNoteEvent(String issuer, Integer noteId) {
        this.issuer = issuer;
        this.noteId = noteId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    @Override
    protected MCBHandlerList<RemoveNoteEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "RemoveNote";
    }
}
