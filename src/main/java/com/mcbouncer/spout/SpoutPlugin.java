package com.mcbouncer.spout;

import com.mcbouncer.LocalServer;

public class SpoutPlugin implements LocalServer {

    public boolean isPlayerOnline(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPlayerName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIPAddress(String ipOrName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void kickPlayer(String name, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void kickPlayerWithIP(String name, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageMods(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void broadcastMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
