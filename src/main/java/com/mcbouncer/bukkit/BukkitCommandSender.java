package com.mcbouncer.bukkit;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Implementation of the LocalPlayer interface. Used
 * when a console user runs a command. This instance
 * is what is sent to the command methods.
 * 
 */
public class BukkitCommandSender implements LocalPlayer {

    protected MCBouncer controller;
    protected CommandSender sender;

    public BukkitCommandSender(MCBouncer controller, CommandSender sender) {
        this.controller = controller;
        this.sender = sender;
    }

    public String getName() {
        return "CONSOLE";
    }

    public boolean hasPermission(String permission) {
        return true;
    }

    public void sendMessage(String message) {
        controller.getLogger().info(ChatColor.stripColor(message));
    }
}
