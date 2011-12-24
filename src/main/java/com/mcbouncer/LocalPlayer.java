package com.mcbouncer;

/**
 * Base player interface. Contains player actions
 * that may vary from server to server. 
 * 
 */
public interface LocalPlayer {
    
    /**
     * Gets the name of the player
     * 
     * @return 
     */
    public String getName();
    
    /**
     * Returns whether or not the given user has
     * a certain permission node.
     * 
     * @param permission
     * @return 
     */
    public boolean hasPermission(String permission);
    
    /**
     * Sends a message to this user
     * 
     * @param message 
     */
    public void sendMessage(String message);
    
}
