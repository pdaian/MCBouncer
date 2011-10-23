package com.mcbouncer.plugin;

import java.util.Arrays;

import com.mcbouncer.command.ICommand;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public abstract class BaseCommand implements ICommand {

    protected MCBouncer parent;
    protected String[] args;
    protected CommandSender sender;

    public String getPlayerNameFromArgs(String arg) {
        Player player = parent.getServer().getPlayer(arg);
        if (player != null) {
            return player.getName();
        } else {
            return null;
        }
    }

    public String getPlayerName(String name) {
        if (parent.getServer().getPlayer(name) != null) {
            return parent.getServer().getPlayer(name).getName();
        }
        return null;
    }

    public boolean isPlayerOnline(String name) {
        return (parent.getServer().getPlayer(name) != null);
    }

    public String getSenderName() {
        String senderName = "console";
        if (sender instanceof Player) {
            senderName = ChatColor.stripColor(((Player) sender).getName());
        }
        return senderName;
    }

    public String getIPFromArgs(String arg, String kickReason) {
        String player = args[0];
        if (!MCBouncerUtil.isIPAddress(player)) {
            Player playerInst = parent.getServer().getPlayer(player);
            if (playerInst != null) {
                player = playerInst.getAddress().getAddress().getHostAddress();
            } else {
                player = "";
            }
        }
        return player;
    }

    public void sendMessageToSender(String message) {
        sender.sendMessage(message);
    }

    public void sendMessageToMods(String message) {
        parent.messageMods(message);
    }

    public void kickPlayer(String player, String reason) {
        Player p = parent.getServer().getPlayer(player);
        if (p != null) {
            p.kickPlayer(reason);
        }
    }

    public void kickPlayerWithIP(String ip, String reason) {
        for (Player player : parent.getServer().getOnlinePlayers()) {
            if (player.getAddress().getAddress().getHostAddress().equals(ip)) {
                player.kickPlayer(reason);
            }
        }
    }

    public boolean senderHasPermission(String permission) {
        if (this.sender instanceof ConsoleCommandSender) {
            return true;
        }
        
        Player player = (Player) this.sender;
        if (parent.hasPermission(player, permission)) {
            return true;
        }
        
        /* Let's keep things compatable with 'mcbouncer.(mod|admin)' */
        
        String[] mperms = {
            "mcbouncer.addnote", "mcbouncer.ban",        "mcbouncer.banip",
            "mcbouncer.help",    "mcbouncer.kick",       "mcbouncer.lookup",
            "mcbouncer.msg.mod", "mcbouncer.removenote", "mcbouncer.unban",
            "mcbouncer.unbanip", "mcbouncer.version"};
        
        int i = Arrays.binarySearch(mperms, permission);
        if (parent.hasPermission(player, "mcbouncer.mod") && (i >= 0)) {
            return true;
        }
        
        /* Let's just make it an array now for furture expansion */
        String[] aperms = {"mcbouncer.reload"};
        
        i = Arrays.binarySearch(aperms, permission);
        if (parent.hasPermission(player, "mcbouncer.admin") && (i >= 0)) {
            return true;
        }
        
        sendMessageToSender(ChatColor.RED + "You do not have permission for that!");
        return false;
    }

    public String getPlayerIP(String playerName) {
        Player player = parent.getServer().getPlayer(playerName);
        if (player != null) {
            return player.getAddress().getAddress().getHostAddress();
        }
        return "";
    }

    public abstract boolean runCommand();

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setParent(MCBouncer parent) {
        this.parent = parent;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }
}
