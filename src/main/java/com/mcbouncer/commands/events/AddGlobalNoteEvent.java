package com.mcbouncer.commands.events;

import com.mcbouncer.LocalPlayer;
import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.MCBEvent;
import net.lahwran.fevents.MCBHandlerList;

/**
 * Called before a global is added. 
 * 
 * Cancelling this event cancels the addition
 * of the note, and the user will not have a
 * note attached.
 * 
 * Any fields modified here will be the variables
 * that get acted upon. 
 * 
 */
public class AddGlobalNoteEvent extends MCBEvent<AddGlobalNoteEvent> implements Cancellable {

    private String user;
    private LocalPlayer issuer;
    private String note;
    public static final MCBHandlerList<AddGlobalNoteEvent> handlers = new MCBHandlerList<AddGlobalNoteEvent>();

    public AddGlobalNoteEvent(String user, LocalPlayer issuer, String note) {
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
    protected MCBHandlerList<AddGlobalNoteEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected String getEventName() {
        return "AddGlobalNote";
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
