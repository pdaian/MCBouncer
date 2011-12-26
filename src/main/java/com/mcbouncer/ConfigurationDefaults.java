package com.mcbouncer;

public enum ConfigurationDefaults {

    DEBUG(false),
    BANSDISALLOW(-1),
    APIKEY(""),
    SHOWMESSAGES(false),
    DEFAULTBAN("Banned for rule violation."),
    DEFAULTKICK("Kicked by an admin."),
    WEBSITE("http://www.mcbouncer.com");
    protected int intVal;
    protected String strVal;
    protected boolean boolVal;

    private ConfigurationDefaults(boolean boolVal) {
        this.boolVal = boolVal;
    }

    private ConfigurationDefaults(String strVal) {
        this.strVal = strVal;
    }

    private ConfigurationDefaults(int intVal) {
        this.intVal = intVal;
    }

    public boolean getBoolVal() {
        return boolVal;
    }

    public int getIntVal() {
        return intVal;
    }

    public String getStrVal() {
        return strVal;
    }
}
