package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.commands.events.AddBanEvent;
import com.mcbouncer.commands.events.BanAddedEvent;
import com.mcbouncer.commands.events.BanRemovedEvent;
import com.mcbouncer.commands.events.RemoveBanEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.BouncerException;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.util.BanType;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.NetUtils;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import net.lahwran.fevents.MCBEventHandler;

/**
 * Container for ban-related commands.
 * 
 */
public class BanCommands extends CommandContainer {

    public BanCommands(MCBouncer controller) {
        super(controller);
    }

    @Command(aliases = {"ban"},
    usage = "<username> [reason]",
    desc = "Ban a username",
    min = 1,
    max = -1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.mod", "mcbouncer.command.ban"})
    public void ban(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        String toBan = controller.getServer().getPlayerName(args.getString(0));
        String reason = controller.getConfiguration().getDefaultReason();

        if (args.argsLength() > 1) {
            reason = args.getJoinedStrings(1);
        }

        AddBanEvent addBanEvent = new AddBanEvent(BanType.USER, toBan, sender, reason);
        MCBEventHandler.callEvent(addBanEvent);

        if (addBanEvent.isCancelled()) {
            return;
        }

        toBan = addBanEvent.getUser();
        sender = addBanEvent.getIssuer();
        reason = addBanEvent.getReason();

        controller.getServer().kickPlayer(toBan, "Banned: " + reason);

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().addBan(sender.getName(), toBan, reason);
            controller.getLogger().info(sender.getName() + " banning " + toBan + " - " + reason);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        BanAddedEvent banAddedEvent = new BanAddedEvent(BanType.USER, toBan, sender, reason, success, error);
        MCBEventHandler.callEvent(banAddedEvent);

        if (success) {
            String message = ChatColor.GREEN + "User " + toBan + " has been banned by " + sender.getName() + ". (" + reason + ")";
            if (controller.getConfiguration().isShowBanMessages()) {
                controller.getServer().broadcastMessage(message);
            } else {
                controller.getServer().messageMods(message);
            }
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"unban"},
    usage = "<username>",
    desc = "Unban a username",
    min = 1,
    max = 1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.mod", "mcbouncer.command.unban"})
    public void unban(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        String toUnban = args.getString(0);

        RemoveBanEvent removeBanEvent = new RemoveBanEvent(BanType.USER, sender, toUnban);
        MCBEventHandler.callEvent(removeBanEvent);

        if (removeBanEvent.isCancelled()) {
            return;
        }

        toUnban = removeBanEvent.getUser();
        sender = removeBanEvent.getIssuer();

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().removeBan(toUnban);
            controller.getLogger().info(sender.getName() + " unbanned " + toUnban);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.USER, sender, toUnban, success, error);
        MCBEventHandler.callEvent(banRemovedEvent);

        if (success) {
            controller.getServer().messageMods(ChatColor.GREEN + toUnban + " has been unbanned by " + sender.getName() + ".");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"banip"},
    usage = "<ip/username> [reason]",
    desc = "Ban an IP.",
    min = 1,
    max = -1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.mod", "mcbouncer.command.banip"})
    public void banIP(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        String toBanIP = controller.getServer().getIPAddress(args.getString(0));
        String toBanUser = controller.getServer().getPlayerName(args.getString(0));
        String reason = controller.getConfiguration().getDefaultReason();

        if (toBanIP.length() == 0) {
            throw new CommandException("No IP address found for that user.");
        }

        if (args.argsLength() > 1) {
            reason = args.getJoinedStrings(1);
        }

        AddBanEvent addBanEvent = new AddBanEvent(BanType.IP, toBanIP, sender, reason);
        MCBEventHandler.callEvent(addBanEvent);

        if (addBanEvent.isCancelled()) {
            return;
        }

        toBanIP = addBanEvent.getUser();
        sender = addBanEvent.getIssuer();
        reason = addBanEvent.getReason();

        if (!toBanUser.equals(toBanIP)) {
            controller.getServer().kickPlayer(toBanUser, "Banned: " + reason);
        } else {
            controller.getServer().kickPlayerWithIP(toBanIP, "Banned: " + reason);
        }

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().addIPBan(sender.getName(), toBanIP, reason);
            controller.getLogger().info(sender.getName() + " banning " + toBanIP + " - " + reason);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        BanAddedEvent banAddedEvent = new BanAddedEvent(BanType.IP, toBanIP, sender, reason, success, error);
        MCBEventHandler.callEvent(banAddedEvent);

        if (success) {
            controller.getServer().messageMods(ChatColor.GREEN + toBanIP + " has been banned by " + sender.getName() + ". (" + reason + ")");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"unbanip"},
    usage = "<ip>",
    desc = "Unban an IP",
    min = 1,
    max = 1)
    @CommandPermissions(value = {"mcbouncer.admin", "mcbouncer.mod", "mcbouncer.command.unbanip"})
    public void unbanip(CommandContext args, LocalPlayer sender) throws CommandException, BouncerException {

        String toUnban = args.getString(0);

        if (!NetUtils.isIPAddress(toUnban)) {
            throw new CommandException("Invalid IP address given.");
        }

        RemoveBanEvent removeBanEvent = new RemoveBanEvent(BanType.IP, sender, toUnban);
        MCBEventHandler.callEvent(removeBanEvent);

        if (removeBanEvent.isCancelled()) {
            return;
        }

        toUnban = removeBanEvent.getUser();
        sender = removeBanEvent.getIssuer();

        boolean success = false;
        String error = "";

        try {
            controller.getAPI().removeIPBan(toUnban);
            controller.getLogger().info(sender.getName() + " unbanned " + toUnban);
            success = true;
        } catch (APIException e) {
            error = e.getMessage();
        }

        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.IP, sender, toUnban, success, error);
        MCBEventHandler.callEvent(banRemovedEvent);

        if (success) {
            controller.getServer().messageMods(ChatColor.GREEN + toUnban + " has been unbanned by " + sender.getName() + ".");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }
}
