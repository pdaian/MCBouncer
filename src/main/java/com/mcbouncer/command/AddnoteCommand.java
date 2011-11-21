package com.mcbouncer.command;

import com.mcbouncer.event.AddNoteEvent;
import com.mcbouncer.event.MCBEventHandler;
import com.mcbouncer.event.NoteAddedEvent;
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
        if (!this.senderHasPermission("mcbouncer.mod.addnote")) {
            return true;
        }
        
        if (args.length < 2) {
            return false;
        }
        String playerName = this.getPlayerNameFromArgs(args[0]);
        String sender = this.getSenderName();
        if (playerName == null) {
            playerName = args[0];
        }

        String note = MCBouncerUtil.getReasonOrDefault(args, MCBouncerUtil.implodeWithoutFirstElement(args, " "), "");
        
        AddNoteEvent addNoteEvent = new AddNoteEvent(playerName, sender, note);
        MCBEventHandler.getInstance().dispatch(addNoteEvent);
        
        if (addNoteEvent.isCancelled()) return true;
        
        sender = addNoteEvent.getIssuer();
        playerName = addNoteEvent.getUser();
        note = addNoteEvent.getNote();
        
        boolean result = MCBouncerUtil.addNote(playerName, sender, note);
        
        NoteAddedEvent noteAddedEvent = new NoteAddedEvent(playerName, sender, note, result, ((result==false)?"":MCBouncerAPI.getError()));
        MCBEventHandler.getInstance().dispatch(noteAddedEvent);
        
        if (result)
            MCBouncer.log.info(sender + " added note to " + playerName + " - " + note);
        
        if (noteAddedEvent.isCancelled()) return true;
        
        if (result) {
            this.sendMessageToSender(ChatColor.GREEN + "Note added to " + playerName + " successfully.");
        } else {
            this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
        }

        return true;
    }
}
