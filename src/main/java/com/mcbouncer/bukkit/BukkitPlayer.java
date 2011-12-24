package com.mcbouncer.bukkit;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import org.bukkit.entity.Player;

/**
 * Implementation of the LocalPlayer interface. This is
 * used when a command is called from in game. It is then
 * sent to the command controllers.
 * 
 */
public class BukkitPlayer implements LocalPlayer {

    protected MCBouncer controller;
    protected Player player;

    public BukkitPlayer(MCBouncer controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
