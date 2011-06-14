package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.ChatColor;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;

public class UnmuteCommand extends BaseCommand {

    public UnmuteCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {

        if( !MCBValidators.UserValidator(args) ) return false;
        
        if(this.isPlayerOnline(args[0])) {
            
            String name = this.getPlayerName(args[0]);
            
            if (!parent.muted.contains(name)) {
                this.sendMessageToSender(ChatColor.RED + "Player is not muted.");
            } else {
                parent.muted.remove(name);
                this.sendMessageToMods(ChatColor.GREEN + name + " was unmuted");
                MCBouncer.log.info(this.getSenderName() + " unmuted " + name);
            }

        }

        return true;

    }
}
