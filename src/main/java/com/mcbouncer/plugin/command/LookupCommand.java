package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerUtil;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LookupCommand implements CommandExecutor {

    private MCBouncer parent;

    public LookupCommand(MCBouncer parent) {
        //MCBValidatorHandler.getInstance().registerValidator( "ban", new BanValidator(this, parent) );    
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.GREEN + "You must specify a user, and only one arg.");
            return false;
        }
        
        ArrayList<HashMap<String, Object>> result = MCBouncerUtil.getBans(args[0]);
        System.out.println(sender);
        System.out.println(result);
        sender.sendMessage(ChatColor.AQUA + args[0] + " has " + result.size() + " ban" + (result.size() != 1 ? "s" : "") + ".");
        for (int i = 0; i < result.size(); i++) {
            sender.sendMessage(ChatColor.GREEN + "" + (i + 1) + ": " + result.get(i).get("server") + " (" + result.get(i).get("issuer") + ") [" + result.get(i).get("reason") + "]");
        }
        return true;

    }
}
