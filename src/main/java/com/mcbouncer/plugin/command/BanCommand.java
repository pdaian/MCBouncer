package com.mcbouncer.plugin.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.MCBValidators;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBConfiguration;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;

public class BanCommand extends BaseCommand {

    public BanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (!MCBValidators.UserAndReasonValidator(args)) {
            return false;
        }
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBConfiguration.getDefaultReason());
        boolean result = MCBouncerUtil.addBan(playerName, this.getSenderName(), reason);
        if (result) {
            MCBouncer.log.info(this.getSenderName() + " banning " + playerName + " - " + reason);
            this.sendMessageToSender(ChatColor.GREEN + "User banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        this.kickPlayer(playerName, reason);
        return true;
    }
}