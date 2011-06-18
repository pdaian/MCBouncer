package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;

public class RemovenoteCommand extends BaseCommand {

    public RemovenoteCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        try {
            if (MCBouncerUtil.removeNote(Integer.valueOf(args[0]), this.getSenderName())) {
                MCBouncer.log.info(this.getSenderName() + " removed note ID " + args[0]);
                this.sendMessageToSender(ChatColor.GREEN + "Note removed successfully.");
            } else {
                this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}