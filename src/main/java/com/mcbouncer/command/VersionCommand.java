package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

public class VersionCommand extends BaseCommand {

    public VersionCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        PluginDescriptionFile pdf = parent.getDescription();
        
        this.sendMessageToSender( ChatColor.GRAY + String.format("You are running %s version %s", pdf.getName(), pdf.getVersion() ) );
        
        return true;

    }
    
}