package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBCommands;
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
        //MCBValidatorHandler.getInstance().registerValidator( "ban", new BanValidator(this, parent) );    
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.GREEN + "You must specify a user.");
            return false;
        }
        
        args[0] = (parent.getServer().matchPlayer(args[0]).size() > 0 ? parent.getServer().matchPlayer(args[0]).get(0).getName() : args[0]);
        String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : MCBouncerUtil.implode(args, " "));
        sender.sendMessage(ChatColor.GREEN + (MCBouncerUtil.addBan(args[0], MCBCommands.getSenderName(sender), reason) ? "User banned successfully." : MCBouncerAPI.getError()));
        Player p = parent.getServer().getPlayer(args[0]);
        if (p != null) {
            p.kickPlayer(reason);
        }
        
        return true;

    }
}