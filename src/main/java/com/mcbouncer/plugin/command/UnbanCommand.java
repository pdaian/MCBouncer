package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.ChatColor;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;

public class UnbanCommand extends BaseCommand {

    public UnbanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {

        if( !MCBValidators.UserValidator(args) ) return false;
        
        if (MCBouncerUtil.removeBan(args[0])) {
            this.sendMessageToSender(ChatColor.GREEN + "User unbanned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;

    }
}