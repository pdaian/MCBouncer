package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.plugin.validator.UserAndReasonValidator;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    private MCBouncer parent;

    public KickCommand(MCBouncer parent) {
        MCBValidators.getInstance().registerValidator("kick", new UserAndReasonValidator(this, parent));
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        Player player = parent.getServer().getPlayer(args[0]);

        if (player != null) {
            String reason = MCBouncerUtil.getDefaultReason(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), MCBouncerConfig.getDefaultKickMessage());
            parent.messageMods(ChatColor.RED + player.getName() + " was kicked for " + reason);
            player.kickPlayer("Kicked: " + reason);
            return true;
        }
        sender.sendMessage(ChatColor.RED + "No such player.");

        return true;

    }
}
