package com.mcbouncer.command;

import com.mcbouncer.event.NoteRemovedEvent;
import com.mcbouncer.event.RemoveNoteEvent;
import com.mcbouncer.plugin.BaseCommand;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import net.lahwran.fevents.MCBEventHandler;

public class RemovenoteCommand extends BaseCommand {

    public RemovenoteCommand(MCBouncerPlugin parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        
        try {
            
            String theSender = this.getSenderName();
            Integer noteId = Integer.valueOf(args[0]);
            RemoveNoteEvent removeNoteEvent = new RemoveNoteEvent(theSender, noteId);
            MCBEventHandler.callEvent(removeNoteEvent);
            
            if (removeNoteEvent.isCancelled()) return true;
            
            theSender = removeNoteEvent.getIssuer();
            
            boolean result = MCBouncerUtil.removeNote(noteId, theSender);
            
            if (result)
                MCBouncerPlugin.log.info(theSender + " removed note ID " + args[0]);
            
            NoteRemovedEvent noteRemovedEvent = new NoteRemovedEvent(theSender, noteId, result, ((result==false)?"":MCBouncerAPI.getError()));
            MCBEventHandler.callEvent(noteRemovedEvent);
            
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