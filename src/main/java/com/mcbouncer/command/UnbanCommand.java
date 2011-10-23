package com.mcbouncer.command;

import com.mcbouncer.event.BanRemovedEvent;
import com.mcbouncer.event.MCBEventHandler;
import com.mcbouncer.event.RemoveBanEvent;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.BanType;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;

public class UnbanCommand extends BaseCommand {

    public UnbanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (!this.senderHasPermission("mcbouncer.unban")) {
            return true;
        }
        
        if (args.length != 1) {
            return false;
        }
        String sender = this.getSenderName();
        String user = args[0];
        
        RemoveBanEvent removeBanEvent = new RemoveBanEvent(BanType.USER, sender, user);
        MCBEventHandler.getInstance().dispatch(removeBanEvent);
        
        if (removeBanEvent.isCancelled()) return true;
        
        sender = removeBanEvent.getIssuer();
        user = removeBanEvent.getUser();
        
        boolean result = MCBouncerUtil.removeBan(user); 
        
        if (result)
            MCBouncer.log.info(this.getSenderName() + " unbanned " + args[0]);
        
        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.USER, sender, user, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.getInstance().dispatch(banRemovedEvent);
        
        if (banRemovedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "User unbanned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}
