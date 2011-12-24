package com.mcbouncer;

import com.mcbouncer.util.node.MapNode;

/**
 * Base configuration class. Implementations of this class
 * should use the load() method to fill in all the fields.
 * 
 */
public abstract class LocalConfiguration {

    protected boolean debugMode = false;
    protected int numBansDisallow = 10;
    protected String apiKey = "";
    protected boolean showBanMessages = true;
    protected String defaultReason = "Banned for rule violation.";
    protected String defaultKickMessage = "Kicked by an admin.";
    protected String website = "http://www.mcbouncer.com";
    protected MapNode conf;

    public abstract void load();

    public MapNode getConfig() {
        return conf;
    }

    public abstract String getAPIKey();

    public abstract boolean isDebugMode();

    public abstract String getDefaultKickReason();

    public abstract String getDefaultReason();

    public abstract int getNumBansDisallow();

    public abstract boolean isShowBanMessages();

    public abstract String getWebsite();
}
