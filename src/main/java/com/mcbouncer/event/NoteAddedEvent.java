package com.mcbouncer.event;

public class NoteAddedEvent extends MCBEvent {

    String user;
    String issuer;
    String note;
    boolean success;
    String error;
    
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
    public Type getType() {
        return Type.NOTE_ADDED;
    }

}
