package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBLogger;
import com.mcbouncer.command.*;
import com.mcbouncer.plugin.BouncerPlugin;
import com.mcbouncer.util.config.MCBConfiguration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {
    
    public final static String version = "1.0beta";

    public PermissionHandler permissionHandler;
    public static final MCBLogger log = new MCBLogger();
    public HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();

    @Override
    public void onDisable() {
        log.info("Plugin disabled. (version " + MCBouncer.version + ")");
    }

    @Override
    public void onEnable() {
        
        MCBConfiguration.load(this.getDataFolder());
        setupPermissions();
        setupListeners();
        setupCommands();

        for (String s : MCBConfiguration.getPlugins()) {
            File file = new File(this.getDataFolder() + s);
            try {
                URL jarfile = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");
                URLClassLoader cl = URLClassLoader.newInstance(new URL[]{jarfile});
                Class<BouncerPlugin> loadedClass = cl.loadClass("BouncerPlugin");
                loadedClass.
            } catch (Exception ex) {
                Logger.getLogger(MCBouncer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        log.info("Plugin enabled. (version " + MCBouncer.version + ")");
        log.debug("Debug mode enabled!");
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                log.debug("Permissions found, using that for Permissions.");
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                log.info("Permissions plugin not detected, defaulting to ops.txt.");
            }
        }
    }
    
    private void setupListeners() {
        MCBPlayerListener pl = new MCBPlayerListener(this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, pl, Event.Priority.Highest, this);
    }
    
    private void setupCommands() {
        this.commands.put("ban", new BanCommand(this));
        this.commands.put("banip", new BanipCommand(this));
        this.commands.put("unban", new UnbanCommand(this));
        this.commands.put("unbanip", new UnbanipCommand(this));
        this.commands.put("kick", new KickCommand(this));
        this.commands.put("lookup", new LookupCommand(this));
        this.commands.put("mcbouncer", new MCBCommand(this));
        this.commands.put("addnote", new AddnoteCommand(this));
        this.commands.put("removenote", new RemovenoteCommand(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        MCBCommandThread r = new MCBCommandThread(this, sender, command, commandLabel, args);
        r.start();
        return true;
    }

    public boolean hasPermission(Player player, String permission) {
        if (this.permissionHandler == null) {
            return player.isOp();
        } else {
            return this.permissionHandler.has(player, permission);
        }
    }

    public void messageMods(String message) {
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (this.hasPermission(player, "mcbouncer.mod")) {
                player.sendMessage(message);
            }
        }
    }    
}
