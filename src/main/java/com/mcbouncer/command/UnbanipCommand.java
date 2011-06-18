package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBValidators;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;

public class UnbanipCommand extends BaseCommand {

    public UnbanipCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        String player = args[0];
        if (!MCBouncerUtil.isIPAddress(player)) {
            this.sendMessageToSender(ChatColor.RED + "Not a valid player or IP.");
            return true;
        }
        boolean result = MCBouncerUtil.removeIPBan(player);
        if (result) {
            MCBouncer.log.info(this.getSenderName() + " unbanned " + player);
            this.sendMessageToSender(ChatColor.GREEN + "IP unbanned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}
