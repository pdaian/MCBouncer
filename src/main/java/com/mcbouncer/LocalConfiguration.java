package com.mcbouncer;

import com.mcbouncer.util.node.MapNode;

/**
 * Base configuration class. Implementations of this class
 * should use the load() method to fill in all the fields.
 * 
 */
public abstract class LocalConfiguration {

    protected boolean debugMode = ConfigurationDefaults.DEBUG.boolVal;
    protected int numBansDisallow = ConfigurationDefaults.BANSDISALLOW.intVal;
    protected String apiKey = ConfigurationDefaults.APIKEY.strVal;
    protected boolean showBanMessages = ConfigurationDefaults.SHOWMESSAGES.boolVal;
    protected String defaultReason = ConfigurationDefaults.DEFAULTBAN.strVal;
    protected String defaultKickMessage = ConfigurationDefaults.DEFAULTKICK.strVal;
    protected String website = ConfigurationDefaults.WEBSITE.strVal;
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
