package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBouncer extends JavaPlugin {

    /**
     * Permissions class
     */
    public PermissionHandler permissionHandler;
    /**
     * Logging class, provides debugging
     */
    public static final MCBLogger log = new MCBLogger();
    /**
     * Command handler class
     */
    private MCBCommands commandHandler;
    /**
     * ArrayList of all muted players
     */
    public ArrayList<Player> muted = new ArrayList<Player>();
    /**
     * Listener handling
     */
    private MCBListeners listeners;

    /**
     * Simply outputs a message when disabled
     */
    @Override
    public void onDisable() {
        log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");
    }

    /**
     * Performs awesome loading action
     */
    @Override
    public void onEnable() {

        MCBouncerConfig.load(this.getDataFolder());
        setupPermissions();

        this.listeners = MCBListeners.load(this);
        this.commandHandler = MCBCommands.load(this);

        log.info("MCBouncer successfully initiated");
        log.debug("Debug mode enabled!");
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
        return this.commandHandler.onCommand(sender, command, commandLabel, args);
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
