package com.mcbouncer.api;

import com.mcbouncer.util.node.MapNode;

/**
 * Stores data about a user ban that was
 * retrieved from the MCBouncer website.
 * 
 */
public class UserBan {

    protected String username;
    protected String issuer;
    protected String server;
    protected String reason;
    protected String time;

    public UserBan(MapNode node) {
        username = node.getString("username");
        issuer = node.getString("issuer");
        server = node.getString("server");
        reason = node.getString("reason");
        time = node.getString("time");
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
