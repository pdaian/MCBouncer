package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.MCBValidators;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.config.MCBConfiguration;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;

public class BanipCommand extends BaseCommand {

    public BanipCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (!MCBValidators.UserAndReasonValidator(args)) {
            return false;
        }
        String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBConfiguration.getDefaultReason());
        String player = this.getIPFromArgs(args[0], reason);
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
        boolean result = MCBouncerUtil.addIPBan(player, this.getSenderName(), reason);
        if (result) {
            MCBouncer.log.info(this.getSenderName() + " banning " + player + " - " + reason);
            this.sendMessageToSender(ChatColor.GREEN + "IP banned successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}
