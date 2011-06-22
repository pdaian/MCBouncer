package com.mcbouncer.command;

import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;

public class AddnoteCommand extends BaseCommand {

    public AddnoteCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length < 2) {
            return false;
        }
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String note = MCBouncerUtil.getReasonOrDefault(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), "");
        boolean result = MCBouncerUtil.addNote(playerName, this.getSenderName(), note);
        if (result) {
            MCBouncer.log.info(this.getSenderName() + " added note to " + playerName + " - " + note);
            this.sendMessageToSender(ChatColor.GREEN + "Note added successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }
        return true;
    }
}