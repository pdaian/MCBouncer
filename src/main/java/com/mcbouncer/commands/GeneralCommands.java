package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.PlayerKickEvent;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.plugin.MCBouncerPlugin;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBouncerUtil;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import com.mcbouncer.util.commands.NestedCommand;
import com.mcbouncer.util.config.MCBConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
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
    @CommandPermissions("mcbouncer.mod")
    public void lookup(CommandContext args, LocalPlayer sender) throws CommandException {

        if (!MCBouncerUtil.isIPAddress(args.getString(0))) {
            String username = controller.getPlugin().getPlayerName(args.getString(0));

            ArrayList<HashMap<String, Object>> bans = MCBouncerUtil.getBans(username);
            ArrayList<HashMap<String, Object>> notes = MCBouncerUtil.getNotes(username);

            String ip = controller.getPlugin().getIPAddress(username);
            ArrayList<HashMap<String, Object>> ipbans = MCBouncerUtil.getIPBans(ip);

            sender.sendMessage(ChatColor.AQUA + username + " has " + bans.size() + " ban" + (bans.size() == 1 ? "" : "s") + " and " + notes.size() + " note" + (notes.size() == 1 ? "" : "s"));
            for (int i = 0; i < bans.size(); i++) {
                sender.sendMessage(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
            }
            for (int i = 0; i < ipbans.size(); i++) {
                sender.sendMessage(ChatColor.GREEN + "IP Ban #" + (i + 1) + ": " + ip + " - " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
            }
            for (int i = 0; i < notes.size(); i++) {
                if ((Boolean) notes.get(i).get("global")) {
                    sender.sendMessage(ChatColor.GREEN + "Note #" + notes.get(i).get("noteid") + " - GLOBAL: " + notes.get(i).get("server") + " (" + notes.get(i).get("issuer") + ") [" + notes.get(i).get("note") + "]");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "Note #" + notes.get(i).get("noteid") + ": " + notes.get(i).get("server") + " (" + notes.get(i).get("issuer") + ") [" + notes.get(i).get("note") + "]");
                }
            }
        } else {
            ArrayList<HashMap<String, Object>> bans = MCBouncerUtil.getIPBans(args.getString(0));
            sender.sendMessage(ChatColor.AQUA + args.getString(0) + " has " + bans.size() + " ban" + (bans.size() == 1 ? "" : "s"));
            for (int i = 0; i < bans.size(); i++) {
                sender.sendMessage(ChatColor.GREEN + "Ban #" + (i + 1) + ": " + bans.get(i).get("server") + " (" + bans.get(i).get("issuer") + ") [" + bans.get(i).get("reason") + "]");
            }
        }

    }

    @Command(aliases = {"kick", "boot"},
    usage = "<username> [reason]",
    desc = "Kicks a username",
    min = 1,
    max = 2)
    @CommandPermissions("mcbouncer.mod")
    public void kick(CommandContext args, LocalPlayer sender) throws CommandException {

        String toKick = controller.getPlugin().getPlayerName(args.getString(0));
        String reason = MCBConfiguration.getDefaultKickReason();

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
        MCBouncerPlugin.log.info(sender.getName() + " kicked " + toKick + " - " + reason);
        sender.sendMessage(ChatColor.GREEN + "User " + toKick + " kicked successfully.");

    }

    @Command(aliases = {"mcbouncer", "mcb"},
    desc = "MCBouncer commands")
    @NestedCommand(MCBouncerCommands.class)
    public void mcb(CommandContext args, LocalPlayer sender) throws CommandException {
    }
}
