package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.config.MCBConfiguration;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;

public class BanCommand extends BaseCommand {

    public BanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length < 1) {
            return false;
        }
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String reason = MCBouncerUtil.getReasonOrDefault(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBConfiguration.getDefaultReason());
        boolean result = MCBouncerUtil.addBan(playerName, this.getSenderName(), reason);
        if (result) {
            MCBouncer.log.info(this.getSenderName() + " banning " + playerName + " - " + reason);
            this.sendMessageToSender(ChatColor.GREEN + "User banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        this.kickPlayer(playerName, "Banned: " + reason);
        return true;
    }
}