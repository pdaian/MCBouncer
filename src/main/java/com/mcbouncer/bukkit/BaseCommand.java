package com.mcbouncer.bukkit;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public abstract class BaseCommand {

    protected MCBouncer parent;
    protected String[] args;
    protected CommandSender sender;

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setParent(MCBouncer parent) {
        this.parent = parent;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }
    
    protected String getPlayerNameFromArgs(String arg) {
        Player player = parent.getServer().getPlayer(arg);
        if(player != null ) {
            return player.getName();
        }
        else {
            return arg;
        }
    }
    
    protected String getIPFromArgs(String arg, String kickReason) {
        String player = args[0];
        if (!MCBouncerUtil.isIPAddress(player)) {
            Player playerInst = parent.getServer().getPlayer(player);
            if (playerInst != null) {
                player = playerInst.getAddress().getAddress().getHostAddress();
                playerInst.kickPlayer(kickReason);
            } else {
                player = "";
            }
        }
        return player;
    }
    
    protected String getSenderName() {
        String senderName = "console";
        if (sender instanceof Player) {
            senderName = ((Player) sender).getName();
        }
        return senderName;
    }
    
    protected void sendMessageToSender(String message) {
        sender.sendMessage(message);
    }
    
    public void sendMessageToMods(String message) {
        parent.messageMods(message);
    }
    
    protected void kickPlayer(String player, String reason) {
        Player p = parent.getServer().getPlayer(player);
        if (p != null) {
            p.kickPlayer(reason);
        }
    }
    
    protected boolean isPlayerOnline(String name) {
        return (parent.getServer().getPlayer(name) != null);
    }
    
    protected String getPlayerName(String name) {
        if(parent.getServer().getPlayer(name) != null) {
            return parent.getServer().getPlayer(name).getName();
        }
        return "";
    }
    
    public abstract boolean runCommand();
    
}
