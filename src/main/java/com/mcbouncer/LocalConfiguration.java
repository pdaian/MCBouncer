package com.mcbouncer;

import com.mcbouncer.util.node.MapNode;

public abstract class LocalConfiguration {
    
    protected boolean debugMode = false;
    protected int numBansDisallow = 10;
    protected String apiKey = "";
    protected boolean showBanMessages = true;
    protected String defaultReason = "Banned for rule violation.";
    protected String defaultKickMessage = "Kicked by an admin.";
    protected MapNode conf;
    
    public abstract void load();

    public MapNode getConfig() {
        return conf;
    }

    public String getAPIKey() {
        return apiKey;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public String getDefaultKickReason() {
        return defaultKickMessage;
    }

    public String getDefaultReason() {
        return defaultReason;
    }

    public int getNumBansDisallow() {
        return numBansDisallow;
    }

    public boolean isShowBanMessages() {
        return showBanMessages;
    }
}
