package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.MCBouncerUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class LookupCommand extends BaseCommand {

    public LookupCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        
        if( !MCBouncerUtil.isIPAddress(args[0]) ) {
            ArrayList<HashMap<String, Object>> bans = MCBouncerUtil.getBans(args[0]);
            ArrayList<HashMap<String, Object>> notes = MCBouncerUtil.getNotes(args[0]);
            String ip = this.getPlayerIP(args[0]);
            ArrayList<HashMap<String, Object>> ipbans = MCBouncerUtil.getIPBans(ip);
            
            this.sendMessageToSender(ChatColor.AQUA + args[0] + " has " + bans.size() + " ban" + ( bans.size() == 1 ? "" : "s" ) + " and " + notes.size() + " note" + ( notes.size() == 1 ? "" : "s" ));
            for (int i = 0; i < bans.size(); i++) {
                this.sendMessageToSender(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
            }
            for (int i = 0; i < ipbans.size(); i++) {
                this.sendMessageToSender(ChatColor.GREEN + "IP Ban #" + (i + 1) + ": " + ip + " - " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
            }
            for (int i = 0; i < notes.size(); i++) {
                if( (Boolean) notes.get(i).get("global") ) {
                    this.sendMessageToSender(ChatColor.GREEN + "Note #" + notes.get(i).get("noteid") + " - GLOBAL: " + notes.get(i).get("server") + " (" + notes.get(i).get("issuer") + ") [" + notes.get(i).get("note") + "]");
                }
                else {
                    this.sendMessageToSender(ChatColor.GREEN + "Note #" + notes.get(i).get("noteid") + ": " + notes.get(i).get("server") + " (" + notes.get(i).get("issuer") + ") [" + notes.get(i).get("note") + "]");
                }
            }
        }
        else {
            ArrayList<HashMap<String, Object>> bans = MCBouncerUtil.getIPBans(args[0]);
            this.sendMessageToSender(ChatColor.AQUA + args[0] + " has " + bans.size() + " ban" + ( bans.size() == 1 ? "" : "s" ));
            for (int i = 0; i < bans.size(); i++) {
                this.sendMessageToSender(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
            }
        }
        return true;
    }
}