package com.mcbouncer.command;

import com.mcbouncer.event.AddNoteEvent;
import com.mcbouncer.event.NoteAddedEvent;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.ChatColor;
import net.lahwran.fevents.MCBEventHandler;

public class AddnoteCommand extends BaseCommand {

    public AddnoteCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length < 2) {
            return false;
        }
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String theSender = this.getSenderName();
        if (playerName == null) {
            playerName = args[0];
        }

        String note = MCBouncerUtil.getReasonOrDefault(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), "");
        
        AddNoteEvent addNoteEvent = new AddNoteEvent(playerName, theSender, note);
        MCBEventHandler.callEvent(addNoteEvent);
        
        if (addNoteEvent.isCancelled()) return true;
        
        theSender = addNoteEvent.getIssuer();
        playerName = addNoteEvent.getUser();
        note = addNoteEvent.getNote();
        
        boolean result = MCBouncerUtil.addNote(playerName, theSender, note);
        
        NoteAddedEvent noteAddedEvent = new NoteAddedEvent(playerName, theSender, note, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.callEvent(noteAddedEvent);
        
        if (result)
            MCBouncerPlugin.log.info(theSender + " added note to " + playerName + " - " + note);
        
        if (noteAddedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "Note added to " + playerName + " successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;
    }
}