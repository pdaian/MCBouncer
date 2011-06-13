package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

public class McbLookupCommand implements CommandExecutor {

    private MCBouncer parent;

    public McbLookupCommand(MCBouncer parent) {
        //MCBValidatorHandler.getInstance().registerValidator( "ban", new BanValidator(this, parent) );    
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.GREEN + "You must specify a user, and only one arg.");
            return false;
        }
        JSONObject result = MCBouncerUtil.getMCBLookup(args[0]);
        sender.sendMessage(ChatColor.AQUA + args[0] + " has " + result.get("ban_num") + " ban" + (result.get("ban_num") != "1" ? "s" : "") + ".");
        for (String s : new String[]{"ban_reasons_global", "ban_reasons_local"}) {
            for (String reason : (String[]) result.get(s)) {
                int i = 0;
                sender.sendMessage(ChatColor.GREEN + (s.equals("ban_reasons_global") ? "[G] " : "[L] ") + "" + i + ": " + reason);
                i++;
            }
        }   
        return true;

    }
}
