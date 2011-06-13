package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    private MCBouncer parent;

    public MuteCommand(MCBouncer parent) {
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
            
            Player player = parent.getServer().matchPlayer(args[0]).get(0);
            if( parent.muted.contains(player) ) {
                sender.sendMessage(ChatColor.GREEN + "Player is already muted.");
            }
            else {
                parent.muted.add(player);
                MCBouncerUtil.appropriateNotify(ChatColor.RED + player.getName() + " was muted");
            }
            
        }
        
        return true;

    }
}
