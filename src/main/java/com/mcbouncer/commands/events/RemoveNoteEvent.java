package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class RemoveNoteEvent extends MCBEvent<RemoveNoteEvent> implements Cancellable {

    private LocalPlayer issuer;
    private Integer noteId;
    public static final MCBHandlerList<RemoveNoteEvent> handlers = new MCBHandlerList<RemoveNoteEvent>();

    public RemoveNoteEvent(LocalPlayer issuer, Integer noteId) {
        this.issuer = issuer;
        this.noteId = noteId;
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

    @Override
    protected MCBHandlerList<RemoveNoteEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "RemoveNote";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
