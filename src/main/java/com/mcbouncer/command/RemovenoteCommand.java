package com.mcbouncer.command;

import com.mcbouncer.event.MCBEventHandler;
import com.mcbouncer.event.NoteRemovedEvent;
import com.mcbouncer.event.RemoveNoteEvent;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;

public class RemovenoteCommand extends BaseCommand {

    public RemovenoteCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (!this.senderHasPermission("mcbouncer.removenote")) {
            return true;
        }
        
        if (args.length != 1) {
            return false;
        }
        try {
            
            String sender = this.getSenderName();
            Integer noteId = Integer.valueOf(args[0]);
            RemoveNoteEvent removeNoteEvent = new RemoveNoteEvent(sender, noteId);
            
            if (removeNoteEvent.isCancelled()) return true;
            
            sender = removeNoteEvent.getIssuer();
            
            boolean result = MCBouncerUtil.removeNote(noteId, sender);
            
            if (result)
                MCBouncer.log.info(sender + " removed note ID " + args[0]);
            
            NoteRemovedEvent noteRemovedEvent = new NoteRemovedEvent(sender, noteId, result, ((result==false)?"":MCBouncerAPI.getError()));
            MCBEventHandler.getInstance().dispatch(noteRemovedEvent);
            
            if (result) {
                this.sendMessageToSender(ChatColor.GREEN + "Note removed successfully.");
            } else {
                this.sendMessageToSender(ChatColor.RED + MCBouncerAPI.getError());
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
