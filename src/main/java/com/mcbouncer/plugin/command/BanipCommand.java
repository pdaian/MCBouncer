package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.plugin.ChatColor;

public class BanipCommand extends BaseCommand {

    public BanipCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if( !MCBValidators.UserAndReasonValidator(args) ) return false;
        String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBouncerConfig.getDefaultReason());
        String player = this.getIPFromArgs(args[0], reason);
        if (player.isEmpty()) {
            this.sendMessageToSender(ChatColor.GREEN + "Not a valid player or IP.");
            return true;
        }
        boolean result = MCBouncerUtil.addIPBan(player, this.getSenderName(), reason);
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "IP banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;

    }
}
