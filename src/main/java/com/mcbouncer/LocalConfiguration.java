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

    public abstract String getAPIKey();

    public abstract boolean isDebugMode();

    public abstract String getDefaultKickReason();

    public abstract String getDefaultReason();

    public abstract int getNumBansDisallow();

    public abstract boolean isShowBanMessages();

    public abstract String getWebsite();
}
