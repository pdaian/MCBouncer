package com.mcbouncer.event;

public class RemoveNoteEvent extends MCBEvent {

    private String issuer;
    private Integer noteId;
    
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
    public Type getType() {
        return Type.REMOVE_NOTE;
    }

}
