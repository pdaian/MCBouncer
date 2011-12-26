package com.mcbouncer.commands;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.exception.CommandException;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MiscUtils;
import com.mcbouncer.util.commands.Command;
import com.mcbouncer.util.commands.CommandContext;
import com.mcbouncer.util.commands.CommandPermissions;

/**
 * Contains subcommands of the /mcbouncer command. 
 * 
 */
public class MCBouncerCommands extends CommandContainer {

    public MCBouncerCommands(MCBouncer controller) {
        super(controller);
    }

    /**
     * From WorldEdit
     */
    @Command(aliases = {"help"},
    usage = "[<command>]",
    desc = "Displays help for the given command or lists all commands.",
    min = 0,
    max = 1)
    public void help(CommandContext args, LocalPlayer player) throws CommandException {

        if (args.argsLength() == 0) {
            player.sendMessage(ChatColor.GRAY + "Commands: " + MiscUtils.join(controller.getCommandManager().getCommands().keySet().toArray(new String[0]), ", "));
            return;
        }

        String command = args.getString(0).replaceAll("/", "");

        String helpMessage = controller.getCommandManager().getHelpMessages().get(command);
        if (helpMessage == null) {
            throw new CommandException("Unknown command '" + command + "'.");
        }

        player.sendMessage(ChatColor.GRAY + helpMessage);
    }
    
    @Command(aliases = {"reload"},
    desc = "Reloads MCBouncer configuration")
    @CommandPermissions(value={"mcbouncer.admin", "mcbouncer.command.reload"})
    public void reload(CommandContext args, LocalPlayer sender) throws CommandException {

        controller.getConfiguration().load();
        sender.sendMessage(ChatColor.GREEN + "Configuration loaded successfully.");

    }
    
    @Command(aliases = {"version", "ver"},
    desc = "Gets the current MCBouncer version")
    public void version(CommandContext args, LocalPlayer sender) throws CommandException {
        sender.sendMessage(ChatColor.GRAY + "Current MCBouncer version: " + MCBouncer.getVersion());
    }
}
