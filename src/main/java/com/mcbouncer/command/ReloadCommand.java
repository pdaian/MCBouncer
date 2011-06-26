package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.ChatColor;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        
        if( this.senderHasPermission("mcbouncer.admin") ) {
            new MockPlugin().doConf();
            this.sendMessageToSender(ChatColor.GRAY + "MCBouncer configuration loaded");
        }
        
        return true;

    }
    
    protected class MockPlugin extends MCBouncer {
        public void doConf() {
            this.setupConfiguration();
        }
    }
    
}