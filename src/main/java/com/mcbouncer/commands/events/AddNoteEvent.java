package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

public class AddNoteEvent extends MCBEvent<AddNoteEvent> implements Cancellable {

    private String user;
    private LocalPlayer issuer;
    private String note;
    public static final MCBHandlerList<AddNoteEvent> handlers = new MCBHandlerList<AddNoteEvent>();

    public AddNoteEvent(String user, LocalPlayer issuer, String note) {
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

    @Override
    protected MCBHandlerList<AddNoteEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "AddNote";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
