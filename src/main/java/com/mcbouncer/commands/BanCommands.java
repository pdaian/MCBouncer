package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.commands.events.AddBanEvent;
import com.mcbouncer.commands.events.BanAddedEvent;
import com.mcbouncer.commands.events.BanRemovedEvent;
import com.mcbouncer.commands.events.RemoveBanEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.util.BanType;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.NetUtils;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;
import net.lahwran.fevents.MCBEventHandler;

public class BanCommands extends CommandContainer {

    public BanCommands(MCBouncer controller) {
        super(controller);
    }

    @Command(aliases = {"ban"},
    usage = "<username> [reason]",
    desc = "Ban a username",
    min = 1,
    max = -1)
    @CommandPermissions(value={"mcbouncer.mod", "mcbouncer.command.ban"})
    public void ban(CommandContext args, LocalPlayer sender) throws CommandException {

        String toBan = controller.getPlugin().getPlayerName(args.getString(0));
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

        controller.getPlugin().kickPlayer(toBan, "Banned: " + reason);

        boolean success = false;
        String error = "";
        try {
            if (controller.getAPI().addBan(sender.getName(), toBan, reason)) {
                controller.getLogger().info(sender.getName() + " banning " + toBan + " - " + reason);
                success = true;
            }
            else {
                error = "Unknown error";
            }
        } catch (NetworkException ex) {
            controller.getLogger().severe("Network error!", ex);
            error = ex.getMessage();
        } catch (APIException ex) {
            controller.getLogger().severe("API error!", ex);
            error = ex.getMessage();
        }

        BanAddedEvent banAddedEvent = new BanAddedEvent(BanType.USER, toBan, sender, reason, success, error);
        MCBEventHandler.callEvent(banAddedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "User " + toBan + " banned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"unban"},
    usage = "<username>",
    desc = "Unban a username",
    min = 1,
    max = 1)
    @CommandPermissions(value={"mcbouncer.mod", "mcbouncer.command.unban"})
    public void unban(CommandContext args, LocalPlayer sender) throws CommandException {

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
            if (controller.getAPI().removeBan(toUnban)) {
                controller.getLogger().info(sender.getName() + " unbanned " + toUnban);
                success = true;
            }
            else {
                error = "Unknown error";
            }
        } catch (NetworkException ex) {
            controller.getLogger().severe("Network error!", ex);
            error = ex.getMessage();
        } catch (APIException ex) {
            controller.getLogger().severe("API error!", ex);
            error = ex.getMessage();
        }

        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.USER, sender, toUnban, success, error);
        MCBEventHandler.callEvent(banRemovedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "User unbanned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }

    @Command(aliases = {"banip"},
    usage = "<ip/username> [reason]",
    desc = "Ban an IP.",
    min = 1,
    max = -1)
    @CommandPermissions(value={"mcbouncer.mod", "mcbouncer.command.banip"})
    public void banIP(CommandContext args, LocalPlayer sender) throws CommandException {

        String toBanIP = controller.getPlugin().getIPAddress(args.getString(0));
        String toBanUser = controller.getPlugin().getPlayerName(args.getString(0));
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

        if (toBanUser.length() != 0) {
            controller.getPlugin().kickPlayer(toBanUser, "Banned: " + reason);
        } else {
            controller.getPlugin().kickPlayerWithIP(toBanIP, "Banned: " + reason);
        }

        boolean success = false;
        String error = "";
        try {
            if (controller.getAPI().addIPBan(sender.getName(), toBanIP, reason)) {
                controller.getLogger().info(sender.getName() + " banning " + toBanIP + " - " + reason);
                success = true;
            }
            else {
                error = "Unknown error";
            }
        } catch (NetworkException ex) {
            controller.getLogger().severe("Network error!", ex);
            error = ex.getMessage();
        } catch (APIException ex) {
            controller.getLogger().severe("API error!", ex);
            error = ex.getMessage();
        }

        BanAddedEvent banAddedEvent = new BanAddedEvent(BanType.IP, toBanIP, sender, reason, success, error);
        MCBEventHandler.callEvent(banAddedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "IP " + toBanIP + " banned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error    );
        }

    }

    @Command(aliases = {"unbanip"},
    usage = "<ip>",
    desc = "Unban an IP",
    min = 1,
    max = 1)
    @CommandPermissions(value={"mcbouncer.mod", "mcbouncer.command.unbanip"})
    public void unbanip(CommandContext args, LocalPlayer sender) throws CommandException {

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
            if (controller.getAPI().removeIPBan(toUnban)) {
                controller.getLogger().info(sender.getName() + " unbanned " + toUnban);
                success = true;
            }
            else {
                error = "Unknown error";
            }
        } catch (NetworkException ex) {
            controller.getLogger().severe("Network error!", ex);
            error = ex.getMessage();
        } catch (APIException ex) {
            controller.getLogger().severe("API error!", ex);
            error = ex.getMessage();
        }

        BanRemovedEvent banRemovedEvent = new BanRemovedEvent(BanType.IP, sender, toUnban, success, error);
        MCBEventHandler.callEvent(banRemovedEvent);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "IP unbanned successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + error);
        }

    }
}
