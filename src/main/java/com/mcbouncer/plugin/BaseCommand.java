package com.mcbouncer.plugin;

import com.mcbouncer.command.ICommand;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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
			senderName = ((Player) sender).getName();
                        if (senderName.startsWith("�a")) {
                            senderName = ChatColor.stripColor((Player) sender).getName());
                        }
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
                return ( this.sender instanceof ConsoleCommandSender ) || parent.hasPermission( (Player) this.sender, permission);
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
