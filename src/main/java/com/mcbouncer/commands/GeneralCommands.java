package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.api.IPBan;
import com.mcbouncer.api.UserBan;
import com.mcbouncer.api.UserNote;
import com.mcbouncer.commands.events.LookupEvent;
import com.mcbouncer.commands.events.PlayerKickEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.BouncerException;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.NetUtils;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import com.mcbouncer.util.commands.NestedCommand;
import java.util.ArrayList;
import java.util.List;
import net.lahwran.fevents.MCBEventHandler;

/**
 * Contains general commands that don't
 * really fit into any other category.
 * 
 */
public class GeneralCommands extends CommandContainer {

    public GeneralCommands(MCBouncer controller) {
        super(controller);
    }

    @Command(aliases = {"lookup"},
    usage = "<username/ip>",
    desc = "Gets info about a username.",
    min = 1,
    max = 1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.mod", "mcbouncer.command.lookup"})
    public void lookup(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        try {
            if (!NetUtils.isIPAddress(args.getString(0))) {
                String username = controller.getServer().getPlayerName(args.getString(0));
                String ip = controller.getServer().getIPAddress(username);

                LookupEvent lookupEvent = new LookupEvent(sender, username);
                MCBEventHandler.callEvent(lookupEvent);

                if (lookupEvent.isCancelled()) {
                    return;
                }
                username = lookupEvent.getPlayer();

                List<UserBan> bans = controller.getAPI().getBans(username);
                List<UserNote> notes = controller.getAPI().getNotes(username);

                List<IPBan> ipbans = new ArrayList<IPBan>();
                if (ip.length() != 0 && !controller.getConfiguration().isIPFunctionsDisabled()) {
                    ipbans = controller.getAPI().getIPBans(ip);
                }

                sender.sendMessage(ChatColor.AQUA + username + " has " + bans.size() + " ban" + (bans.size() == 1 ? "" : "s") + " and " + notes.size() + " note" + (notes.size() == 1 ? "" : "s"));

                for (int i = 0; i < bans.size(); i++) {
                    sender.sendMessage(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).getServer() + " (" + bans.get(i).getIssuer() + ") [" + bans.get(i).getReason() + "]");
                }
                
                for (int i = 0; i < ipbans.size(); i++) {
                    sender.sendMessage(ChatColor.GREEN + "IP Ban #" + (i + 1) + ": " + ip + " - " + ipbans.get(i).getServer() + " (" + ipbans.get(i).getIssuer() + ") [" + ipbans.get(i).getReason() + "]");
                }
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).isGlobal()) {
                        sender.sendMessage(ChatColor.GREEN + "Note #" + notes.get(i).getNoteID().toString() + " - GLOBAL: " + notes.get(i).getServer() + " (" + notes.get(i).getIssuer() + ") [" + notes.get(i).getNote() + "]");
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "Note #" + notes.get(i).getNoteID().toString() + ": " + notes.get(i).getServer() + " (" + notes.get(i).getIssuer() + ") [" + notes.get(i).getNote() + "]");
                    }
                }
            } else {
                if (controller.getConfiguration().isIPFunctionsDisabled()) {
                    sender.sendMessage(ChatColor.RED + "IP Functions Disabled.");
                    return;
                }
                LookupEvent lookupEvent = new LookupEvent(sender, args.getString(0));
                MCBEventHandler.callEvent(lookupEvent);

                if (lookupEvent.isCancelled()) {
                    return;
                }
                String ip = lookupEvent.getPlayer();

                List<IPBan> bans = controller.getAPI().getIPBans(ip);
                sender.sendMessage(ChatColor.AQUA + args.getString(0) + " has " + bans.size() + " ban" + (bans.size() == 1 ? "" : "s"));
                for (int i = 0; i < bans.size(); i++) {
                    sender.sendMessage(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).getServer() + " (" + bans.get(i).getIssuer() + ") [" + bans.get(i).getReason() + "]");
                }
            }
        } catch (APIException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

    }

    @Command(aliases = {"kick", "boot"},
    usage = "<username> [reason]",
    desc = "Kicks a username",
    min = 1,
    max = -1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.mod", "mcbouncer.command.kick"})
    public void kick(CommandContext args, LocalPlayer sender) throws CommandException {

        String toKick = controller.getServer().getPlayerName(args.getString(0));
        String reason = controller.getConfiguration().getDefaultKickReason();

        if (args.argsLength() > 1) {
            reason = args.getJoinedStrings(1);
        }

        PlayerKickEvent playerKickEvent = new PlayerKickEvent(toKick, sender, reason);
        MCBEventHandler.callEvent(playerKickEvent);

        if (playerKickEvent.isCancelled()) {
            return;
        }

        toKick = playerKickEvent.getPlayer();
        sender = playerKickEvent.getIssuer();
        reason = playerKickEvent.getReason();

        if (!controller.getServer().isPlayerOnline(toKick)) {
            throw new CommandException(ChatColor.RED + toKick + " is not online");
        }

        controller.getServer().kickPlayer(toKick, "Kicked: " + reason);
        controller.getLogger().info(sender.getName() + " kicked " + toKick + " - " + reason);
        controller.getServer().messageMods(ChatColor.GREEN + toKick + " has been kicked by " + sender.getName() + ". (" + reason + ")");

    }

    @Command(aliases = {"mcbouncer", "mcb"},
    desc = "MCBouncer commands")
    @NestedCommand(MCBouncerCommands.class)
    public void mcb(CommandContext args, LocalPlayer sender) throws CommandException {
    }
}
