package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBCommands;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BanipCommand implements CommandExecutor {

    private MCBouncer parent;

    public BanipCommand(MCBouncer parent) {
        //MCBValidatorHandler.getInstance().registerValidator( "ban", new BanValidator(this, parent) );    
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : MCBouncerUtil.implode(args, " "));
        Pattern p = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
        boolean matches = p.matcher(args[0]).matches();
        if (!matches) {
            args[0] = (parent.getServer().matchPlayer(args[0]).size() > 0 ? parent.getServer().matchPlayer(args[0]).get(0).getAddress().getAddress().getHostAddress() : "");
            parent.getServer().matchPlayer(args[0]).get(0).kickPlayer(reason);
        }
        if (args[0].isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "Not a valid player or IP.");
            return false;
        }
        sender.sendMessage(ChatColor.GREEN + (MCBouncerUtil.addIPBan(args[0], MCBCommands.getSenderName(sender), reason) ? "IP banned successfully." : MCBouncerAPI.getError()));
        return true;

    }
}
