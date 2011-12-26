package com.mcbouncer;

/**
 * Base server interface. Implementations of this
 * class are typically the base of the plugin for
 * each server mod. For example, Bukkit's version
 * of this would extend JavaPlugin.
 * 
 */
public interface LocalServer {

    /**
     * Whether or not the player with the given name is online.
     * 
     * @param name
     * @return 
     */
    public boolean isPlayerOnline(String name);

    /**
     * Returns the full player name if the player is online.
     * If not, just returns the given argument
     * 
     * @param name
     * @return 
     */
    public String getPlayerName(String name);

    /**
     * Gets the IP address of the given username. 
     * If the user is not online or the given username
     * is an IP, it just returns a blank string.
     * 
     * @param ipOrName
     * @return 
     */
    public String getIPAddress(String ipOrName);

    /**
     * Kicks the player with the given name from the
     * server with the given reason.
     * 
     * If the player is offline, it fails silently.
     * 
     * @param name
     * @param reason 
     */
    public void kickPlayer(String name, String reason);

    /**
     * Kicks all players that are connected to the given
     * IP address from the server with the given reason.
     * 
     * @param ip
     * @param reason 
     */
    public void kickPlayerWithIP(String ip, String reason);

    /**
     * Sends a message to all the currently online players
     * that have the "mcbouncer.mod" permission.
     * 
     * @param string 
     */
    public void messageMods(String string);

    /**
     * Sends a message to all the players on the server.
     * 
     * @param message 
     */
    public void broadcastMessage(String message);
}
