package com.mcbouncer.util;

public class BanProvider {
    private String playerName;
    private String server;
    private String banningAdmin;

    public BanProvider(String playerName, String server, String banningAdmin) {
        this.playerName = playerName;
        this.server = server;
        this.banningAdmin = banningAdmin;
    }

    public String getBanningAdmin() {
        return banningAdmin;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getServer() {
        return server;
    }

    
    
}
