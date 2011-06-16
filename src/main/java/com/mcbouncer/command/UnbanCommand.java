package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBValidators;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;

public class UnbanCommand extends BaseCommand {

    public UnbanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (!MCBValidators.UserValidator(args)) {
            return false;
        }
        if (MCBouncerUtil.removeBan(args[0])) {
            MCBouncer.log.info(this.getSenderName() + " unbanned " + args[0]);
            this.sendMessageToSender(ChatColor.GREEN + "User unbanned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}