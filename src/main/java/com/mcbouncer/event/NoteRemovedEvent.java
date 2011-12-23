package com.mcbouncer.event;

public class NoteRemovedEvent extends MCBEvent {

    private String issuer;
    private Integer noteId;
    private boolean success;
    private String error;
    
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
    public MCBEventType getType() {
        return MCBEventType.NOTE_REMOVED;
    }

}
