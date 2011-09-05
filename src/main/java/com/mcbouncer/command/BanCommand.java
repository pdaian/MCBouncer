package com.mcbouncer.command;

import com.mcbouncer.event.AddBanEvent;
import com.mcbouncer.event.BanAddedEvent;
import com.mcbouncer.util.BanType;
import com.mcbouncer.event.MCBEventHandler;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.config.MCBConfiguration;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;

public class BanCommand extends BaseCommand {

    public BanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length < 1) {
            return false;
        }
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String sender = this.getSenderName();
        String reason = MCBouncerUtil.getReasonOrDefault(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBConfiguration.getDefaultReason());

        AddBanEvent addBanEvent = new AddBanEvent(BanType.USER, playerName, sender, reason);
        MCBEventHandler.getInstance().dispatch(addBanEvent);
        
        if (addBanEvent.isCancelled()) return true;
        
        playerName = addBanEvent.getUser();
        sender = addBanEvent.getIssuer();
        reason = addBanEvent.getReason();
        
        if (playerName != null) {
            this.kickPlayer(playerName, "Banned: " + reason);
        } else {
            playerName = args[0];
        }

        boolean result = MCBouncerUtil.addBan(playerName, sender, reason);
        
        if (result)
            MCBouncer.log.info(sender + " banning " + playerName + " - " + reason);
        
        BanAddedEvent banAddedEvent = new BanAddedEvent(BanType.USER, playerName, sender, reason, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.getInstance().dispatch(banAddedEvent);
        
        if (banAddedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "User " + playerName + " banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;
    }
}