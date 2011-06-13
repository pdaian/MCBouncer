package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.plugin.validator.UserValidator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    private MCBouncer parent;

    public UnmuteCommand(MCBouncer parent) {
        MCBValidators.getInstance().registerValidator("unmute", new UserValidator(this, parent));
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        Player player = parent.getServer().getPlayer(args[0]);

        if (player != null) {

            if (!parent.muted.contains(player)) {
                sender.sendMessage(ChatColor.GREEN + "Player is not muted.");
            } else {
                parent.muted.remove(player);
                parent.messageMods(ChatColor.RED + player.getName() + " was unmuted");
            }

        }

        return true;

    }
}
