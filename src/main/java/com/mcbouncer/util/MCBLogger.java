package com.mcbouncer.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MCBLogger {

    public static final Logger logger = Logger.getLogger("Minecraft");

    public void info(String s) {
        logger.log(Level.INFO, "[MCBouncer] " + s);
    }

    public void debug(String s) {
        if (MCBConfiguration.isDebugMode()) {
            logger.log(Level.INFO, "[MCBouncer DEBUG] " + s);
        }
    }

    public void severe(String s) {
        logger.log(Level.SEVERE, "[MCBouncer] " + s);
    }

    public void warning(String s) {
        logger.log(Level.WARNING, "[MCBouncer] " + s);
    }
}