package com.mcbouncer.util;

public enum ConfigDefaults {

    DEBUG(false),
    BANSDISALLOW(-1),
    APIKEY(""),
    SHOWMESSAGES(false),
    DEFAULTBAN("Banned for rule violation."),
    DEFAULTKICK("Kicked by an admin."),
    WEBSITE("http://www.mcbouncer.com"),
    DISABLEIPFUNCTIONS(false);
    protected int intVal;
    protected String strVal;
    protected boolean boolVal;

    private ConfigDefaults(boolean boolVal) {
        this.boolVal = boolVal;
    }

    private ConfigDefaults(String strVal) {
        this.strVal = strVal;
    }

    private ConfigDefaults(int intVal) {
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
