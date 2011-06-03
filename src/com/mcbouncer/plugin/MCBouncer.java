package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {

    public static PermissionHandler permissionHandler;

    @Override
    public void onDisable() {
        System.out.println("Closing MCBouncer!");
    }

    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        final MCBPlayerListener pl = new MCBPlayerListener(this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Priority.High, this);
        setupPermissions();
        MCBouncerConfig.load(this.getDataFolder());
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                this.getServer().getLogger().info("Permission system not detected, defaulting to OP");
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String senderName = "console";
        if (sender instanceof Player) { 
            if (!this.permissionHandler.has((Player) sender, "mcbouncer.mod")) {
                return false;
            }
            senderName = ((Player) sender).getName();
        }
        if (command.getName().equalsIgnoreCase("ban")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.GREEN + "You must specify a user.");
                return false;
            }
            args[0] = (String) (getServer().matchPlayer(args[0]).size() > 0 ? getServer().matchPlayer(args[0]).get(0) : args[0]);
            String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : this.join(args, " "));
            sender.sendMessage(ChatColor.GREEN+(MCBouncerUtil.addBan(args[0], senderName, reason) ? "User banned successfully." : MCBouncerAPI.getError()));
            Player p = this.getServer().getPlayer(args[0]);
            if (p != null) {
                p.kickPlayer(reason);
            }
       
        } else if (command.getName().equalsIgnoreCase("unban")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.GREEN+"You must specify a user, and only one arg.");
                return false;
            }
            sender.sendMessage(ChatColor.GREEN+(MCBouncerUtil.removeBan(args[0]) ? "User unbanned successfully." : MCBouncerAPI.getError()));
        }
        else if (command.getName().equalsIgnoreCase("lookup")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.GREEN+"You must specify a user, and only one arg.");
                return false;
            }
            ArrayList<HashMap<String, Object>> result = MCBouncerUtil.getBans(args[0]);
            sender.sendMessage(ChatColor.AQUA+args[0]+" has "+result.size()+" ban"+(result.size() != 1 ? "s" : "")+".");
            for (int i = 0; i < result.size(); i++) {
                sender.sendMessage(ChatColor.GREEN + "" + (i + 1) + ": " + result.get(i).get("server") + " (" + result.get(i).get("issuer") + ") [" + result.get(i).get("reason") + "]");
            }
        } else if (command.getName().equalsIgnoreCase("banip")) { // IN PROGRESS AND MESSY - NOT DONE
            String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : this.join(args, " "));
            Pattern p = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
            boolean matches = p.matcher(args[0]).matches();
        if (!matches) {
            args[0] = (String) (getServer().matchPlayer(args[0]).size() > 0 ? getServer().matchPlayer(args[0]).get(0) : "");
        }
        if (args[0].isEmpty()) {
            sender.sendMessage(ChatColor.GREEN+"Not a valid player or IP.");
            return false;
        }
        sender.sendMessage(ChatColor.GREEN+(MCBouncerUtil.addIPBan(args[0]) ? "IP banned successfully." : MCBouncerAPI.getError()));
        if (!matches) {
            getServer().getPlayer(args[0]).kickPlayer(reason);
        }
 }
        
        else
            return false;
        return true;
    }
    // Stolen from Google

    public static String join(String[] s, String delimiter) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 1; i < s.length; i++) {
            buffer.append(s[i]).append(delimiter);
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

}
