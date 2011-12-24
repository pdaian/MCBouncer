package com.mcbouncer.api;

import com.mcbouncer.util.node.MapNode;

/**
 * Stores data about a note that was
 * retrieved from the MCBouncer website.
 * 
 */
public class UserNote {

    protected Integer noteId;
    protected String username;
    protected String issuer;
    protected String server;
    protected String note;
    protected String time;
    protected Boolean global;

    public UserNote(MapNode node) {
        noteId = node.getInteger("noteid");
        username = node.getString("username");
        issuer = node.getString("issuer");
        server = node.getString("server");
        note = node.getString("note");
        time = node.getString("time");
        global = node.getBoolean("global", false);
    }

    public Boolean isGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
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

    public Integer getNoteID() {
        return noteId;
    }

    public void setNoteID(Integer noteId) {
        this.noteId = noteId;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
