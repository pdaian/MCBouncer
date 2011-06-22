package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerUtil;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCBCommandThread extends Thread {

    public MCBouncer parent;
    public CommandSender sender;
    public Command command;
    public String commandLabel;
    public String[] args;

    public MCBCommandThread(MCBouncer parent, CommandSender sender, Command command, String commandLabel, String[] args) {
        this.parent = parent;
        this.sender = sender;
        this.command = command;
        this.commandLabel = commandLabel;
        this.args = args;
    }

    @Override
    public void run() {
        if (!this.onCommand()) {
            if (command.getUsage().length() > 0) {
                for (String line : command.getUsage().replace("<command>", commandLabel).split("\n")) {
                    sender.sendMessage(line);
                }
            }
        }
    }

    public boolean onCommand() {
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
            if (!parent.commands.containsKey(commandName)) {
                return false;
            }
            
            BaseCommand commandClass = parent.commands.get(commandName);
            commandClass.setArgs(args);
            commandClass.setParent(parent);
            commandClass.setSender(sender);
            
            MCBouncer.log.debug("Received command from " + commandClass.getSenderName() + " - /" + commandName + " " + MCBouncerUtil.implode(args, " ") );
            
            return commandClass.runCommand();
            
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}