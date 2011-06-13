package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.plugin.validator.UserValidator;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class McbLookupCommand implements CommandExecutor {

    private MCBouncer parent;

    public McbLookupCommand(MCBouncer parent) {
        MCBValidators.getInstance().registerValidator("mcb-lookup", new UserValidator(this, parent));
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        JSONObject result = MCBouncerUtil.getMCBLookup(args[0]);
        if (result == null) {
            return true;
        }

        sender.sendMessage(ChatColor.AQUA + args[0] + " has " + result.get("ban_num") + " ban" + MCBouncerUtil.plural(Integer.parseInt(String.valueOf(result.get("ban_num"))), "", "s") + ".");

        for (String s : new String[]{"ban_reasons_global", "ban_reasons_local"}) {
            for (String reason : MCBouncerUtil.JSONtoString((JSONArray) result.get(s))) {
                int i = 0;
                sender.sendMessage((s.equals("ban_reasons_global") ? ChatColor.RED + "[G] " : ChatColor.AQUA + "[L] ") + ChatColor.GREEN + i + ": " + reason);
                i++;
            }
        }
        return true;

    }
}
