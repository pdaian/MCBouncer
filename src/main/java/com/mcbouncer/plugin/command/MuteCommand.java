package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.ChatColor;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;

public class MuteCommand extends BaseCommand {

    public MuteCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {

        if( !MCBValidators.UserValidator(args) ) return false;

        if(this.isPlayerOnline(args[0])) {
            
            String name = this.getPlayerName(args[0]);

            if (parent.muted.contains(name)) {
                this.sendMessageToSender(ChatColor.GREEN + "Player is already muted.");
            } else {
                parent.muted.add(name);
                this.sendMessageToMods(ChatColor.RED + name + " was muted");
            }

        }

        return true;

    }
}
