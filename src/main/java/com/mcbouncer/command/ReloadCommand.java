package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.ChatColor;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        
        if( this.senderHasPermission("mcbouncer.admin") ) {
            parent.setupConfiguration();
            this.sendMessageToSender(ChatColor.GRAY + "MCBouncer configuration loaded");
        }
        
        return true;

    }
    
}