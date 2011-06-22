package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.config.MCBConfiguration;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        
        if( this.senderHasPermission("mcbouncer.admin") ) {
            MCBConfiguration.load(parent.getDataFolder());
            this.sendMessageToSender(ChatColor.GRAY + "MCBouncer configuration loaded");
        }
        
        return true;

    }
    
}