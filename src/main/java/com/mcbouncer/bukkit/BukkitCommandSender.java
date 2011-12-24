package com.mcbouncer.bukkit;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import org.bukkit.command.CommandSender;

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
        controller.getLogger().info(message);
    }
}
