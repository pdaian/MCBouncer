package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerAPI;
import com.mcbouncer.util.MCBouncerConfig;
import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

public class MCBouncer extends JavaPlugin {

    public static PermissionHandler permissionHandler;
    public static final MCBLogger log = new MCBLogger();

    @Override
    public void onDisable() {
        log.info("Closing MCBouncer!");
    }

    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        final MCBPlayerListener pl = new MCBPlayerListener(this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Priority.High, this);
        setupPermissions();
        MCBouncerConfig.load(this.getDataFolder());
        
        log.info("MCBouncer successfully initiated");
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                log.info("Permission system not detected, defaulting to OP");
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
            args[0] = (getServer().matchPlayer(args[0]).size() > 0 ? getServer().matchPlayer(args[0]).get(0).getName() : args[0]);
            String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : MCBouncerUtil.implode(args, " "));
            sender.sendMessage(ChatColor.GREEN + (MCBouncerUtil.addBan(args[0], senderName, reason) ? "User banned successfully." : MCBouncerAPI.getError()));
            Player p = this.getServer().getPlayer(args[0]);
            if (p != null) {
                p.kickPlayer(reason);
            }

        } else if (command.getName().equalsIgnoreCase("unban")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.GREEN + "You must specify a user, and only one arg.");
                return false;
            }
            sender.sendMessage(ChatColor.GREEN + (MCBouncerUtil.removeBan(args[0]) ? "User unbanned successfully." : MCBouncerAPI.getError()));
        } else if (command.getName().equalsIgnoreCase("kick")) {
            if (getServer().matchPlayer(args[0]).size() > 0) {
                String reason = (args.length > 1 ? MCBouncerUtil.implode(args, " ") : MCBouncerConfig.getDefaultKickMessage());
                MCBouncerUtil.appropriateNotify(ChatColor.RED + getServer().matchPlayer(args[0]).get(0).getName() + " was kicked for " + reason);
                getServer().matchPlayer(args[0]).get(0).kickPlayer(reason);
            }
            sender.sendMessage(ChatColor.GREEN + "No such player.");
        } else if (command.getName().equalsIgnoreCase("lookup")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.GREEN + "You must specify a user, and only one arg.");
                return false;
            }
            ArrayList<HashMap<String, Object>> result = MCBouncerUtil.getBans(args[0]);
            sender.sendMessage(ChatColor.AQUA + args[0] + " has " + result.size() + " ban" + (result.size() != 1 ? "s" : "") + ".");
            for (int i = 0; i < result.size(); i++) {
                sender.sendMessage(ChatColor.GREEN + "" + (i + 1) + ": " + result.get(i).get("server") + " (" + result.get(i).get("issuer") + ") [" + result.get(i).get("reason") + "]");
            }
        } else if (command.getName().equalsIgnoreCase("banip")) { // IN PROGRESS AND MESSY - NOT DONE
            String reason = (args.length == 1 ? MCBouncerConfig.getDefaultReason() : MCBouncerUtil.implode(args, " "));
            Pattern p = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
            boolean matches = p.matcher(args[0]).matches();
            if (!matches) {
                args[0] = (getServer().matchPlayer(args[0]).size() > 0 ? getServer().matchPlayer(args[0]).get(0).getAddress().getAddress().getHostAddress() : "");
                getServer().matchPlayer(args[0]).get(0).kickPlayer(reason);
            }
            if (args[0].isEmpty()) {
                sender.sendMessage(ChatColor.GREEN + "Not a valid player or IP.");
                return false;
            }
            sender.sendMessage(ChatColor.GREEN + (MCBouncerUtil.addIPBan(args[0], senderName, reason) ? "IP banned successfully." : MCBouncerAPI.getError()));
        } else if (command.getName().equalsIgnoreCase("mcb-lookup")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.GREEN + "You must specify a user, and only one arg.");
                return false;
            }
            JSONObject result = MCBouncerUtil.getMCBLookup(args[0]);
            sender.sendMessage(ChatColor.AQUA + args[0] + " has " + result.get("ban_num") + " ban" + (result.get("ban_num") != "1" ? "s" : "") + ".");
            for (String s : new String[]{"ban_reasons_global", "ban_reasons_local"}) {
                for (String reason : (String[]) result.get(s)) {
                    int i = 0;
                    sender.sendMessage(ChatColor.GREEN + (s.equals("ban_reasons_global") ? "[G] " : "[L] ") + "" + i + ": " + reason);
                    i++;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
