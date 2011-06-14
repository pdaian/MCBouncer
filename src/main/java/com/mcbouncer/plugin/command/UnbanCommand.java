package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
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
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if( !MCBValidators.UserValidator(args) ) return false;
        
        if (MCBouncerUtil.removeBan(args[0])) {
            sender.sendMessage(ChatColor.GREEN + "User unbanned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;

    }
}