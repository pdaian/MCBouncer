package com.mcbouncer;

public interface LocalPlugin {
    
    public boolean isPlayerOnline(String name);
    public String getPlayerName(String name);
    public String getIPAddress(String ipOrName);
    public void kickPlayer(String name, String reason);
    public void kickPlayerWithIP(String name, String reason);
    
}
