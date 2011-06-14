package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBCommands;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

    private MCBouncer parent;

    public BanCommand(MCBouncer parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if( !MCBValidators.UserAndReasonValidator(args) ) return false;
        
        String playerName = (parent.getServer().matchPlayer(args[0]).size() > 0 ? parent.getServer().matchPlayer(args[0]).get(0).getName() : args[0]);

        String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBouncerConfig.getDefaultReason());

        boolean result = MCBouncerUtil.addBan(playerName, MCBCommands.getSenderName(sender), reason);

        if (result) {
            sender.sendMessage(ChatColor.GREEN + "User banned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + MCBouncerAPI.getError());
        }

        Player p = parent.getServer().getPlayer(playerName);
        if (p != null) {
            p.getWorld().strikeLightningEffect(p.getLocation());
            p.kickPlayer(reason);
        }

        return true;

    }
}
