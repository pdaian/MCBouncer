package com.mcbouncer.spout;

import com.mcbouncer.LocalServer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.NetUtils;
import org.getspout.api.player.Player;
import org.getspout.api.plugin.CommonPlugin;

/**
 * Implementation of the LocalServer interface. It
 * is the main plugin class for Spout. It extends
 * CommonPlugin, and is set ___TODO____
 * 
 * It registers the configuration, sets the main controller,
 * registers the pseudo-listeners, interprets commands,
 * as well as implementing many of the server-specific 
 * methods that need to be called during the execution of
 * this plugin.
 * 
 */
public class SpoutServer extends CommonPlugin implements LocalServer {

    protected MCBouncer controller;

    @Override
    public void onDisable() {
        controller.getLogger().info("Plugin disabled (version " + MCBouncer.getVersion() + ")");
    }

    @Override
    public void onEnable() {

        /*LocalConfiguration config = new SpoutConfiguration(this.getDataFolder());
        config.load();
        
        controller = new MCBouncer(this, config);

        controller.getLogger().info("Plugin enabled. (version " + MCBouncer.getVersion() + ")");
        controller.getLogger().debug("Debug mode enabled!");

        this.getGame().getEventManager().registerEvents(new SpoutListener(controller), this);
*/
    }

    //@Override
    public boolean onCommand(/*CommandSender sender, Command command, String label, String[] args*/) {
        /*BukkitCommandThread r = new BukkitCommandThread(controller, sender, command, label, args);
        r.start();
        return true;*/
        throw new UnsupportedOperationException("No commands in Spout yet!"); //TODO
    }

    public boolean isPlayerOnline(String name) {
        return this.getGame().getPlayer(name, false) != null;
    }

    public String getPlayerName(String name) {
        Player player = this.getGame().getPlayer(name, false);
        if (player == null) {
            return name;
        }
        return player.getName();
    }

    public String getIPAddress(String ipOrName) {
        if (NetUtils.isIPAddress(ipOrName)) {
            return ipOrName;
        }

        Player player = this.getGame().getPlayer(ipOrName, false);
        if (player == null) {
            return "";
        }
        return player.getSession().getAddress().getAddress().getHostAddress();
    }

    public void kickPlayer(String name, String reason) {
        Player player = this.getGame().getPlayer(name, false);
        if (player != null) {
            player.getSession().disconnect(reason);
        }
    }

    public void kickPlayerWithIP(String ip, String reason) {
        for (Player player : this.getGame().getOnlinePlayers()) {
            if (player.getSession().getAddress().getAddress().getHostAddress().equals(ip)) {
                player.getSession().disconnect(reason);
            }
        }
    }

    public void messageMods(String string) {
        /*for (Player player : this.getGame().getOnlinePlayers()) {
            if (player.("mcbouncer.mod")) {
                player.sendMessage(string);
            }
        }*/
        throw new UnsupportedOperationException("No permissions in Spout yet!");
    }

    public void broadcastMessage(String message) {
        for (Player player : this.getGame().getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
    
}
