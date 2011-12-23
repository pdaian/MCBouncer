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

public class UnbanipCommand extends BaseCommand {

    public UnbanipCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        String player = args[0];
        String theSender = this.getSenderName();
        
        if (!MCBouncerUtil.isIPAddress(player)) {
            this.sendMessageToSender(ChatColor.RED + "Not a valid player or IP.");
            return true;
        }
        
        RemoveBanEvent removeBanEvent = new RemoveBanEvent(BanType.IP, theSender, player);
        MCBEventHandler.callEvent(removeBanEvent);
        
        if (removeBanEvent.isCancelled()) return true;
        player = removeBanEvent.getUser();
        
        boolean result = MCBouncerUtil.removeIPBan(player);
        
        if (result)
            MCBouncerPlugin.log.info(this.getSenderName() + " unbanned " + player);
        
        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.IP, theSender, player, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.callEvent(banRemovedEvent);
        
        if (banRemovedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "IP unbanned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}
