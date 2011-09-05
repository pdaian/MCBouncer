package com.mcbouncer.command;

import com.mcbouncer.event.AddBanEvent;
import com.mcbouncer.event.BanAddedEvent;
import com.mcbouncer.event.MCBEventHandler;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.config.MCBConfiguration;
import com.mcbouncer.util.BanType;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;

public class BanipCommand extends BaseCommand {

    public BanipCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length < 1) {
            return false;
        }
        String reason = MCBouncerUtil.getReasonOrDefault(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBConfiguration.getDefaultReason());
        String player = this.getIPFromArgs(args[0], reason);
        String sender = this.getSenderName();
        
        AddBanEvent addBanEvent = new AddBanEvent(BanType.IP, player, sender, reason);
        MCBEventHandler.getInstance().dispatch(addBanEvent);
        
        if (addBanEvent.isCancelled()) return true;
        
        sender = addBanEvent.getIssuer();
        player = addBanEvent.getUser();
        reason = addBanEvent.getReason();
        
        if (!MCBouncerUtil.isIPAddress(args[0])) {
            if (this.isPlayerOnline(args[0])) {
                this.kickPlayer(this.getPlayerName(args[0]), "Banned: " + reason);
            }
        } else {
            this.kickPlayerWithIP(args[0], "Banned: " + reason);
        }
        if (player.isEmpty()) {
            this.sendMessageToSender(ChatColor.GREEN + "Not a valid player or IP.");
            return true;
        }
        boolean result = MCBouncerUtil.addIPBan(player, sender, reason);
        
        if (result)
            MCBouncer.log.info(sender + " banning " + player + " - " + reason);
        
        BanAddedEvent banAddedEvent = new BanAddedEvent(BanType.USER, player, sender, reason, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.getInstance().dispatch(banAddedEvent);
        
        if (banAddedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "IP banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}
