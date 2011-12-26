package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.commands.events.AddGlobalNoteEvent;
import com.mcbouncer.commands.events.AddNoteEvent;
import com.mcbouncer.commands.events.GlobalNoteAddedEvent;
import com.mcbouncer.commands.events.NoteAddedEvent;
import com.mcbouncer.commands.events.NoteRemovedEvent;
import com.mcbouncer.commands.events.RemoveNoteEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.BouncerException;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import net.lahwran.fevents.MCBEventHandler;

/**
 * Contains note-related commands.
 * 
 */
public class NoteCommands extends CommandContainer {

    public NoteCommands(MCBouncer controller) {
        super(controller);
    }

    @Command(aliases = {"addnote"},
    usage = "<username> <text>",
    desc = "Add a note to a username",
    min = 2,
    max = -1)
    @CommandPermissions(value = {"mcbouncer.mod", "mcbouncer.command.addnote"})
    public void addnote(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        String toNote = controller.getServer().getPlayerName(args.getString(0));
        String note = args.getJoinedStrings(1);

        AddNoteEvent addNoteEvent = new AddNoteEvent(toNote, sender, note);
        MCBEventHandler.callEvent(addNoteEvent);

        if (addNoteEvent.isCancelled()) {
            return;
        }

        toNote = addNoteEvent.getUser();
        sender = addNoteEvent.getIssuer();
        note = addNoteEvent.getNote();

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().addNote(sender.getName(), toNote, note);
            controller.getLogger().info(sender.getName() + " added note to " + toNote + " - " + note);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        NoteAddedEvent noteAddedEvent = new NoteAddedEvent(toNote, sender, note, success, error);
        MCBEventHandler.callEvent(noteAddedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "Note added to " + toNote + " successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"addgnote", "addglobalnote"},
    usage = "<username> <text>",
    desc = "Add a global note to a username",
    min = 2,
    max = -1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.command.addnote.global"})
    public void addgnote(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        String toNote = controller.getServer().getPlayerName(args.getString(0));
        String note = args.getJoinedStrings(1);

        AddGlobalNoteEvent addNoteEvent = new AddGlobalNoteEvent(toNote, sender, note);
        MCBEventHandler.callEvent(addNoteEvent);

        if (addNoteEvent.isCancelled()) {
            return;
        }

        toNote = addNoteEvent.getUser();
        sender = addNoteEvent.getIssuer();
        note = addNoteEvent.getNote();

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().addGlobalNote(sender.getName(), toNote, note);
            controller.getLogger().info(sender.getName() + " added global note to " + toNote + " - " + note);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        GlobalNoteAddedEvent noteAddedEvent = new GlobalNoteAddedEvent(toNote, sender, note, success, error);
        MCBEventHandler.callEvent(noteAddedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "Global note added to " + toNote + " successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"removenote", "delnote"},
    usage = "<note id>",
    desc = "Remove a note ID",
    min = 1,
    max = 1)
    @CommandPermissions(value = {"mcbouncer.mod", "mcbouncer.command.removenote"})
    public void removenote(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        Integer toRemove = args.getInteger(0);

        RemoveNoteEvent removeNoteEvent = new RemoveNoteEvent(sender, toRemove);
        MCBEventHandler.callEvent(removeNoteEvent);

        if (removeNoteEvent.isCancelled()) {
            return;
        }

        toRemove = removeNoteEvent.getNoteId();

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().removeNote(toRemove, sender.getName());
            controller.getLogger().info(sender.getName() + " removed note ID " + toRemove);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        NoteRemovedEvent noteRemovedEvent = new NoteRemovedEvent(sender, toRemove, success, error);
        MCBEventHandler.callEvent(noteRemovedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "Note removed successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }
}
