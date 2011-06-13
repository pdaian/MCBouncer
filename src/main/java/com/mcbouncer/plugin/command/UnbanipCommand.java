package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.plugin.validator.UserValidator;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanipCommand implements CommandExecutor {

    private MCBouncer parent;

    public UnbanipCommand(MCBouncer parent) {
        MCBValidators.getInstance().registerValidator("unbanip", new UserValidator(this, parent));
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        String player = args[0];

        if (!MCBouncerUtil.isIPAddress(player)) {
            sender.sendMessage(ChatColor.GREEN + "Not a valid player or IP.");
            return true;
        }

        boolean result = MCBouncerUtil.removeIPBan(player);
        if (result) {
            sender.sendMessage(ChatColor.GREEN + "IP unbanned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;

    }
}
