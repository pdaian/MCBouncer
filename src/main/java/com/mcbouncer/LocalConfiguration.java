package com.mcbouncer;

import com.mcbouncer.util.ConfigDefaults;
import com.mcbouncer.util.node.MapNode;

/**
 * Base configuration class. Implementations of this class
 * should use the load() method to fill in all the fields.
 * 
 */
public abstract class LocalConfiguration {

    protected boolean debugMode = ConfigDefaults.DEBUG.getBoolVal();
    protected int numBansDisallow = ConfigDefaults.BANSDISALLOW.getIntVal();
    protected String apiKey = ConfigDefaults.APIKEY.getStrVal();
    protected boolean showBanMessages = ConfigDefaults.SHOWMESSAGES.getBoolVal();
    protected String defaultReason = ConfigDefaults.DEFAULTBAN.getStrVal();
    protected String defaultKickMessage = ConfigDefaults.DEFAULTKICK.getStrVal();
    protected String website = ConfigDefaults.WEBSITE.getStrVal();
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

    public String getWebsite() {
        return website;
    }
}
