package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.plugin.ChatColor;

public class BanCommand extends BaseCommand {

    public BanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if( !MCBValidators.UserAndReasonValidator(args) ) return false;
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBouncerConfig.getDefaultReason());
        boolean result = MCBouncerUtil.addBan(playerName, this.getSenderName(), reason);
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "User banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        this.kickPlayer(playerName, reason);
        return true;
    }
}