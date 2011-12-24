package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Called after a note is removed. 
 * 
 * This event cannot be cancelled.
 * 
 */
public class NoteRemovedEvent extends MCBEvent<NoteRemovedEvent> {

    private LocalPlayer issuer;
    private Integer noteId;
    private boolean success;
    private String error;
    public static final MCBHandlerList<NoteRemovedEvent> handlers = new MCBHandlerList<NoteRemovedEvent>();

    public NoteRemovedEvent(LocalPlayer issuer, Integer noteId, boolean success, String error) {
        this.issuer = issuer;
        this.noteId = noteId;
        this.success = success;
        this.error = error;
    }

    public LocalPlayer getIssuer() {
        return issuer;
    }

    public void setIssuer(LocalPlayer issuer) {
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
