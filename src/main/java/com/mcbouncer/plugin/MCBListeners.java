package com.mcbouncer.plugin;

import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class MCBListeners {

    /**
     * Main plugin class
     */
    private MCBouncer parent;
    
    /**
     * List of all event listeners
     */
    private HashMap<String, Listener> listeners = new HashMap<String, Listener>();
    
    public MCBListeners(MCBouncer parent) {
	this.parent = parent;
    }

    /**
     * Initialize the listeners and connect them to events
     */
    public static MCBListeners load(MCBouncer parent) {
	MCBouncer.log.debug("Loading events and listeners");
	
	MCBListeners listener = new MCBListeners(parent);
	
	listener.registerListener( "player", new MCBPlayerListener(parent) );
        
        listener.registerEvent(Event.Type.PLAYER_JOIN, "player", Event.Priority.High);
        listener.registerEvent(Event.Type.PLAYER_CHAT, "player", Event.Priority.High);
        listener.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, "player", Event.Priority.High);

	return listener;
    }
    
    /**
     * Add a new listener
     */
    public void registerListener( String name, Listener listener ) {
	this.listeners.put(name, listener);
    }
    
    /**
     * Add a new event
     */
    public void registerEvent( Event.Type type, String listener, Event.Priority priority ) {
	parent.getServer().getPluginManager().registerEvent(type, listeners.get(listener), priority, parent);
    }
    

}