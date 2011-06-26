package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.ChatColor;
import java.util.LinkedHashMap;
import org.bukkit.plugin.PluginDescriptionFile;

public class HelpCommand extends BaseCommand {

    public HelpCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        PluginDescriptionFile pdf = parent.getDescription();
        this.sendMessageToSender(ChatColor.GRAY + "MCBouncer commands");
        for( Object commandO : ( (LinkedHashMap<Object, Object>) pdf.getCommands() ).keySet() ) {
            String command = commandO.toString();
            LinkedHashMap data = (LinkedHashMap) ( (LinkedHashMap<Object, Object>) pdf.getCommands() ).get(commandO);
            this.sendMessageToSender(ChatColor.GRAY + "  " + data.get("usage").toString().replace("<command>", command) + " - " + data.get("description") );
        }
        return true;
    }
}