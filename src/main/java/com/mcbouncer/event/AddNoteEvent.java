package com.mcbouncer.event;

public class AddNoteEvent extends MCBEvent {

    private String user;
    private String issuer;
    private String note;
    
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
    public Type getType() {
        return Type.ADD_NOTE;
    }

}
