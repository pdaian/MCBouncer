package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBouncerUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class LookupCommand extends BaseCommand {

    public LookupCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        ArrayList<HashMap<String, Object>> bans = MCBouncerUtil.getBans(args[0]);
        ArrayList<HashMap<String, Object>> notes = MCBouncerUtil.getNotes(args[0]);
        this.sendMessageToSender(ChatColor.AQUA + args[0] + " has " + bans.size() + " ban" + MCBouncerUtil.plural(bans.size(), "", "s") + ", " + notes.size() + "note" + MCBouncerUtil.plural(notes.size(), "", "s"));
        for (int i = 0; i < bans.size(); i++) {
            this.sendMessageToSender(ChatColor.GREEN + "|BAN| " + (i + 1) + ": " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
        }
        for (int i = 0; i < notes.size(); i++) {
            this.sendMessageToSender(ChatColor.GREEN + "|NOTE| " + (i + 1) + ": " + notes.get(i).get("server") + " (" + notes.get(i).get("issuer") + ") [" + notes.get(i).get("reason") + "]");
        }        
        return true;
    }
}
