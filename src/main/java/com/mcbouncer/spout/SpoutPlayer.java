package com.mcbouncer.spout;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import org.spout.api.player.Player;

public class SpoutPlayer implements LocalPlayer {
    
    protected MCBouncer controller;
    protected Player player;

    public SpoutPlayer(MCBouncer controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
