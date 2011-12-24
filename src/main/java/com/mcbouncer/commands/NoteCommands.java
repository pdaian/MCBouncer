package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.commands.events.AddNoteEvent;
import com.mcbouncer.commands.events.NoteAddedEvent;
import com.mcbouncer.commands.events.NoteRemovedEvent;
import com.mcbouncer.commands.events.RemoveNoteEvent;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import net.lahwran.fevents.MCBEventHandler;

public class NoteCommands extends CommandContainer {

    public NoteCommands(MCBouncer controller) {
        super(controller);
    }

    @Command(aliases = {"addnote"},
    usage = "<username> <text>",
    desc = "Add a note to a username",
    min = 1,
    max = -1)
    @CommandPermissions("mcbouncer.mod")
    public void addnote(CommandContext args, LocalPlayer sender) throws CommandException {

        //TODO: Global notes

        String toNote = controller.getPlugin().getPlayerName(args.getString(0));
        String note = args.getJoinedStrings(1);

        AddNoteEvent addNoteEvent = new AddNoteEvent(toNote, sender, note);
        MCBEventHandler.callEvent(addNoteEvent);

        if (addNoteEvent.isCancelled()) {
            return;
        }

        toNote = addNoteEvent.getUser();
        sender = addNoteEvent.getIssuer();
        note = addNoteEvent.getNote();

        boolean result = MCBouncerUtil.addNote(toNote, sender.getName(), note);

        if (result) {
            controller.getLogger().info(sender.getName() + " added note to " + toNote + " - " + note);
        }

        NoteAddedEvent noteAddedEvent = new NoteAddedEvent(toNote, sender, note, result, ((result == false) ? "" : MCBouncerAPI.getError()));
        MCBEventHandler.callEvent(noteAddedEvent);

        if (result) {
            sender.sendMessage(ChatColor.GREEN + "Note added to " + toNote + " successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + MCBouncerAPI.getError());
        }

    }

    @Command(aliases = {"removenote", "delnote"},
    usage = "<note id>",
    desc = "Remove a note ID",
    min = 1,
    max = 1)
    @CommandPermissions("mcbouncer.mod")
    public void removenote(CommandContext args, LocalPlayer sender) throws CommandException {

        Integer toRemove = args.getInteger(0);

        RemoveNoteEvent removeNoteEvent = new RemoveNoteEvent(sender, toRemove);
        MCBEventHandler.callEvent(removeNoteEvent);

        if (removeNoteEvent.isCancelled()) {
            return;
        }

        toRemove = removeNoteEvent.getNoteId();

        boolean result = MCBouncerUtil.removeNote(toRemove, "admin");

        if (result) {
            controller.getLogger().info(sender.getName() + " removed note ID " + toRemove);
        }

        NoteRemovedEvent noteRemovedEvent = new NoteRemovedEvent(sender, toRemove, result, ((result == false) ? "" : MCBouncerAPI.getError()));
        MCBEventHandler.callEvent(noteRemovedEvent);

        if (result) {
            sender.sendMessage(ChatColor.GREEN + "Note removed successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + MCBouncerAPI.getError());
        }

    }
}
