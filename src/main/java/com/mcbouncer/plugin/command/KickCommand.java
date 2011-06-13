package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KickCommand implements CommandExecutor {

    private MCBouncer parent;

    public KickCommand(MCBouncer parent) {
        //MCBValidatorHandler.getInstance().registerValidator( "ban", new BanValidator(this, parent) );    
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.GREEN + "You must specify a user");
            return false;
        }

        if (parent.getServer().matchPlayer(args[0]).size() > 0) {
            String reason = (args.length > 1 ? MCBouncerUtil.implode(args, " ") : MCBouncerConfig.getDefaultKickMessage());
            MCBouncerUtil.appropriateNotify(ChatColor.RED + parent.getServer().matchPlayer(args[0]).get(0).getName() + " was kicked for " + reason);
            parent.getServer().matchPlayer(args[0]).get(0).kickPlayer(reason);
            return true;
        }
        sender.sendMessage(ChatColor.GREEN + "No such player.");

        return true;

    }
}
