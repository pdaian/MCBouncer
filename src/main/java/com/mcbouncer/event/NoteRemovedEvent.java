package com.mcbouncer.event;

import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class NoteRemovedEvent extends MCBEvent<NoteRemovedEvent> {

    private String issuer;
    private Integer noteId;
    private boolean success;
    private String error;
    public static final MCBHandlerList<NoteRemovedEvent> handlers = new MCBHandlerList<NoteRemovedEvent>();

    public NoteRemovedEvent(String issuer, Integer noteId, boolean success, String error) {
        this.issuer = issuer;
        this.noteId = noteId;
        this.success = success;
        this.error = error;
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
    protected MCBHandlerList<NoteRemovedEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "NoteRemoved";
    }
}
