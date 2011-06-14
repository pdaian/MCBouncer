package com.mcbouncer.plugin.command;

import com.mcbouncer.plugin.ChatColor;
import com.mcbouncer.plugin.MCBValidators;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class LookupCommand extends BaseCommand {

    public LookupCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (!MCBValidators.UserValidator(args)) {
            return false;
        }
        ArrayList<HashMap<String, Object>> result = MCBouncerUtil.getBans(args[0]);
        this.sendMessageToSender(ChatColor.AQUA + args[0] + " has " + result.size() + " ban" + MCBouncerUtil.plural(result.size(), "", "s") + ".");
        for (int i = 0; i < result.size(); i++) {
            this.sendMessageToSender(ChatColor.GREEN + "" + (i + 1) + ": " + result.get(i).get("server") + " (" + result.get(i).get("issuer") + ") [" + result.get(i).get("reason") + "]");
        }
        return true;
    }
}
