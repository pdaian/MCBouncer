package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.ChatColor;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;

public class KickCommand extends BaseCommand {

    public KickCommand(MCBouncer parent) {
        this.parent = parent;
    }
    
    public boolean runCommand() {
        if (!MCBValidators.UserAndReasonValidator(args)) {
            return false;
        }
        if (this.isPlayerOnline(args[0])) {
            String name = this.getPlayerName(args[0]);
            String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBouncerConfig.getDefaultKickMessage());
            this.sendMessageToMods(ChatColor.RED + name + " was kicked for " + reason);
            this.kickPlayer(name, "Kicked: " + reason);
            return true;
        }
        this.sendMessageToSender(ChatColor.RED + "No such player.");
        return true;
    }
}
