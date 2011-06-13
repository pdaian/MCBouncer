package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBCommands;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.plugin.validator.UserAndReasonValidator;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BanipCommand implements CommandExecutor {
    
    private MCBouncer parent;

    public BanipCommand(MCBouncer parent) {
        MCBValidators.getInstance().registerValidator("banip", new UserAndReasonValidator(this, parent));
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBouncerConfig.getDefaultReason() );
        
        String player = args[0];
        
        if (!MCBouncerUtil.isIPAddress(player)) {
            if( parent.getServer().matchPlayer(args[0]).size() > 0 ) {
                player = parent.getServer().matchPlayer(args[0]).get(0).getAddress().getAddress().getHostAddress();
                parent.getServer().matchPlayer(args[0]).get(0).kickPlayer(reason);
            }
            else {
                player = "";
            }
        }
        
        if (player.isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "Not a valid player or IP.");
            return true;
        }

        boolean result = MCBouncerUtil.addIPBan(player, MCBCommands.getSenderName(sender), reason);
        if (result) {
            sender.sendMessage(ChatColor.GREEN + "IP banned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;

    }

}
