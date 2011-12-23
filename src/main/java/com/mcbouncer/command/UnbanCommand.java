package com.mcbouncer.command;

import com.mcbouncer.event.BanRemovedEvent;
import com.mcbouncer.event.RemoveBanEvent;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.BanType;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import net.lahwran.fevents.MCBEventHandler;

public class UnbanCommand extends BaseCommand {

    public UnbanCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        
        String theSender = this.getSenderName();
        String user = args[0];
        
        RemoveBanEvent removeBanEvent = new RemoveBanEvent(BanType.USER, theSender, user);
        MCBEventHandler.callEvent(removeBanEvent);
        
        if (removeBanEvent.isCancelled()) return true;
        
        theSender = removeBanEvent.getIssuer();
        user = removeBanEvent.getUser();
        
        boolean result = MCBouncerUtil.removeBan(user); 
        
        if (result)
            MCBouncerPlugin.log.info(this.getSenderName() + " unbanned " + args[0]);
        
        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.USER, theSender, user, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.callEvent(banRemovedEvent);
        
        if (banRemovedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "User unbanned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}