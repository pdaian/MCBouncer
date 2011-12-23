package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

public class VersionCommand extends BaseCommand {

    public VersionCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        PluginDescriptionFile pdf = parent.getDescription();
        this.sendMessageToSender(ChatColor.GRAY + String.format("You are running %s version %s", pdf.getName(), pdf.getVersion()));
        return true;
    }
}