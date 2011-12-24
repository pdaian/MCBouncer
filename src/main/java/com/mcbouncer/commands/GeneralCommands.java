package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.api.IPBan;
import com.mcbouncer.api.UserBan;
import com.mcbouncer.api.UserNote;
import com.mcbouncer.commands.events.PlayerKickEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.NetUtils;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import com.mcbouncer.util.commands.NestedCommand;
import java.util.List;
import net.lahwran.fevents.MCBEventHandler;

public class GeneralCommands extends CommandContainer {

    public GeneralCommands(MCBouncer controller) {
        super(controller);
    }

    @Command(aliases = {"lookup"},
    usage = "<username/ip>",
    desc = "Gets info about a username.",
    min = 1,
    max = 1)
    @CommandPermissions(value={"mcbouncer.mod", "mcbouncer.command.lookup"})
    public void lookup(CommandContext args, LocalPlayer sender) throws CommandException {

        try {
            if (!NetUtils.isIPAddress(args.getString(0))) {
                String username = controller.getPlugin().getPlayerName(args.getString(0));

                List<UserBan> bans = controller.getAPI().getBans(username);
                List<UserNote> notes = controller.getAPI().getNotes(username);

                String ip = controller.getPlugin().getIPAddress(username);
                List<IPBan> ipbans = controller.getAPI().getIPBans(ip);

                sender.sendMessage(ChatColor.AQUA + username + " has " + bans.size() + " ban" + (bans.size() == 1 ? "" : "s") + " and " + notes.size() + " note" + (notes.size() == 1 ? "" : "s"));

                for (int i = 0; i < bans.size(); i++) {
                    sender.sendMessage(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).getServer() + " (" + bans.get(i).getIssuer() + ") [" + bans.get(i).getReason() + "]");
                }
                for (int i = 0; i < ipbans.size(); i++) {
                    sender.sendMessage(ChatColor.GREEN + "IP Ban #" + (i + 1) + ": " + ip + " - " + bans.get(i).getServer() + " (" + bans.get(i).getIssuer() + ") [" + bans.get(i).getReason() + "]");
                }
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).isGlobal()) {
                        sender.sendMessage(ChatColor.GREEN + "Note #" + notes.get(i).getNoteID().toString() + " - GLOBAL: " + notes.get(i).getServer() + " (" + notes.get(i).getIssuer() + ") [" + notes.get(i).getNote() + "]");
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "Note #" + notes.get(i).getNoteID().toString() + ": " + notes.get(i).getServer() + " (" + notes.get(i).getIssuer() + ") [" + notes.get(i).getNote() + "]");
                    }
                }
            } else {
                List<IPBan> bans = controller.getAPI().getIPBans(args.getString(0));
                sender.sendMessage(ChatColor.AQUA + args.getString(0) + " has " + bans.size() + " ban" + (bans.size() == 1 ? "" : "s"));
                for (int i = 0; i < bans.size(); i++) {
                    sender.sendMessage(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).getServer() + " (" + bans.get(i).getIssuer() + ") [" + bans.get(i).getReason() + "]");
                }
            }
        } catch (NetworkException ex) {
            controller.getLogger().severe("Network error!", ex);
            sender.sendMessage(ChatColor.RED + "Unexpected network error! Report to the admins.");
        } catch (APIException ex) {
            controller.getLogger().severe("API error!", ex);
            sender.sendMessage(ChatColor.RED + "Unexpected API error! Report to the admins.");
        }

    }

    @Command(aliases = {"kick", "boot"},
    usage = "<username> [reason]",
    desc = "Kicks a username",
    min = 1,
    max = 2)
    @CommandPermissions(value={"mcbouncer.mod", "mcbouncer.command.kick"})
    public void kick(CommandContext args, LocalPlayer sender) throws CommandException {

        String toKick = controller.getPlugin().getPlayerName(args.getString(0));
        String reason = controller.getConfiguration().getDefaultKickReason();

        if (args.argsLength() > 1) {
            reason = args.getJoinedStrings(1);
        }

        if (controller.getPlugin().isPlayerOnline(toKick)) {
            throw new CommandException(ChatColor.RED + toKick + " is not online");
        }

        PlayerKickEvent playerKickEvent = new PlayerKickEvent(toKick, sender, reason);
        MCBEventHandler.callEvent(playerKickEvent);

        if (playerKickEvent.isCancelled()) {
            return;
        }

        toKick = playerKickEvent.getUser();
        sender = playerKickEvent.getIssuer();
        reason = playerKickEvent.getReason();

        controller.getPlugin().kickPlayer(toKick, "Banned: " + reason);
        controller.getLogger().info(sender.getName() + " kicked " + toKick + " - " + reason);
        sender.sendMessage(ChatColor.GREEN + "User " + toKick + " kicked successfully.");

    }

    @Command(aliases = {"mcbouncer", "mcb"},
    desc = "MCBouncer commands")
    @NestedCommand(MCBouncerCommands.class)
    public void mcb(CommandContext args, LocalPlayer sender) throws CommandException {
    }
}
