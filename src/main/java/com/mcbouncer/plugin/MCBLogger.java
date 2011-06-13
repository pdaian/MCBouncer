package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerConfig;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a debug method, and prefixes all log messages with [MCBouncer]
 */
public class MCBLogger {

    public static final Logger logger = Logger.getLogger("Minecraft");
    
    public void info( String s ) {
	logger.log(Level.INFO, "[MCBouncer] " + s);
    }
    
    public void debug( String s ) {
	if( MCBouncerConfig.isDebugMode() ) {
	    logger.log(Level.INFO, "[MCBouncer DEBUG] " + s);
	}
    }
    
    public void severe( String s ) {
	logger.log(Level.SEVERE, "[MCBouncer] " + s);
    }
    
    public void warning( String s ) {
	logger.log(Level.WARNING, "[MCBouncer] " + s);
    }
    
}