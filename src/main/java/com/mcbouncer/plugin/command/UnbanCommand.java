package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {

    private MCBouncer parent;
    
    public UnbanCommand(MCBouncer parent) {
        //MCBValidatorHandler.getInstance().registerValidator( "ban", new BanValidator(this, parent) );    
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.GREEN + "You must specify a user.");
            return false;
        }
        
        sender.sendMessage(ChatColor.GREEN + (MCBouncerUtil.removeBan(args[0]) ? "User unbanned successfully." : MCBouncerAPI.getError()));
        
        return true;

    }
}