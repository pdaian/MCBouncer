package com.mcbouncer.plugin.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBValidators;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBConfiguration;
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
            String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBConfiguration.getDefaultKickMessage());
            
            this.sendMessageToMods(ChatColor.RED + name + " was kicked for " + reason);
            MCBouncer.log.info(this.getSenderName() + " kicked " + name + " - " + reason);
            
            this.kickPlayer(name, "Kicked: " + reason);
            return true;
        }
        this.sendMessageToSender(ChatColor.RED + "No such player.");
        return true;
    }
}
