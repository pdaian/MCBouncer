package com.mcbouncer.plugin;

import com.mcbouncer.plugin.command.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCBCommands implements CommandExecutor {

    private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
    private MCBouncer parent;

    public MCBCommands(MCBouncer parent) {
        this.parent = parent;
    }

    /**
     * Registers all the commands
     */
    public static MCBCommands load(MCBouncer parent) {

        MCBouncer.log.debug("Loading command handlers");
        MCBCommands handler = new MCBCommands(parent);

        handler.registerCommand("ban", new BanCommand(parent));
        handler.registerCommand("banip", new BanipCommand(parent));
        handler.registerCommand("unban", new UnbanCommand(parent));
        handler.registerCommand("unbanip", new UnbanipCommand(parent));
        handler.registerCommand("kick", new KickCommand(parent));
        handler.registerCommand("lookup", new LookupCommand(parent));
        handler.registerCommand("mcb-lookup", new McbLookupCommand(parent));
        handler.registerCommand("mute", new MuteCommand(parent));
        handler.registerCommand("unmute", new UnmuteCommand(parent));

        return handler;
    }

    public void registerCommand(String name, CommandExecutor command) {
        commands.put(name, command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (sender instanceof Player) {
            if (!parent.hasPermission((Player) sender, "mcbouncer.mod")) {
                return false;
            }
        }

        String commandName = command.getName().toLowerCase();

        //What's this hackery? /command arg  arg2 threw an error. Strip unnecessary spaces
        List<String> temp_list = new LinkedList<String>();
        temp_list.addAll(Arrays.asList(args));
        while (temp_list.contains("")) {
            temp_list.remove("");
        }
        args = temp_list.toArray(new String[0]);

        try {
            if (!commands.containsKey(commandName)) {

                return false;
            }

            return commands.get(commandName).onCommand(sender, command, commandLabel, args);

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }

    public static String getSenderName(CommandSender sender) {
        String senderName = "console";
        if (sender instanceof Player) {
            senderName = ((Player) sender).getName();
        }
        return senderName;
    }
}
