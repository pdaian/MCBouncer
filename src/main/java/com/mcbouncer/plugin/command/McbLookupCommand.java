package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.ChatColor;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class McbLookupCommand extends BaseCommand {

    public McbLookupCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if( !MCBValidators.UserValidator(args) ) return false;
        JSONObject result = MCBouncerUtil.getMCBLookup(args[0]);
        if (result == null) {
            return true;
        }
        this.sendMessageToSender(ChatColor.AQUA + args[0] + " has " + result.get("ban_num") + " ban" + MCBouncerUtil.plural(Integer.parseInt(String.valueOf(result.get("ban_num"))), "", "s") + ".");
        for (String s : new String[]{"ban_reasons_global", "ban_reasons_local"}) {
            for (String reason : MCBouncerUtil.JSONtoString((JSONArray) result.get(s))) {
                int i = 0;
                this.sendMessageToSender((s.equals("ban_reasons_global") ? ChatColor.RED + "[G] " : ChatColor.AQUA + "[L] ") + ChatColor.GREEN + i + ": " + reason);
                i++;
            }
        }
        return true;

    }
}
