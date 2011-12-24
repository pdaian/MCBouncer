package com.mcbouncer.bukkit;

import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Thread which runs Bukkit commands in
 * the background. This prevents a website
 * error from bringing down the entire game,
 * as well as preventing long-running commands
 * from interfering with play.
 * 
 */
public class BukkitCommandThread extends Thread {

    public MCBouncer controller;
    public CommandSender sender;
    public Command command;
    public String commandLabel;
    public String[] args;

    public BukkitCommandThread(MCBouncer controller, CommandSender sender, Command command, String commandLabel, String[] args) {
        this.controller = controller;
        this.sender = sender;
        this.command = command;
        this.commandLabel = commandLabel;
        this.args = args;
    }

    /**
     * Runs the command. If the onCommand method returns false,
     * it will show an error message in the form of a usage line.
     * 
     */
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
    
    /**
     * Handles the incoming Bukkit command data,
     * transforms it into generic data that MCBouncer
     * can read, and sends it to the controller.
     * 
     * @return 
     */
    public boolean onCommand() {
        
        String[] split = new String[args.length + 1];
        System.arraycopy(args, 0, split, 1, args.length);
        split[0] = "/" + command.getName();

        LocalPlayer newSender = new BukkitCommandSender(controller, sender);
        if( sender instanceof Player ) {
            newSender = new BukkitPlayer(controller, (Player) sender);
        }
        
        controller.handleCommand(newSender, split);

        return true;
    }
}