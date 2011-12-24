package com.mcbouncer.util;

import com.mcbouncer.MCBouncer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main logger controller, adds debug logging. If
 * the configuration file says debug: true, then debug
 * messages will be shown.
 * 
 */
public class MCBLogger {

    protected MCBouncer controller;
    protected static final Logger logger = Logger.getLogger("Minecraft");

    public MCBLogger(MCBouncer controller) {
        this.controller = controller;
    }

    public void info(String s) {
        logger.log(Level.INFO, "[MCBouncer] " + s);
    }

    public void debug(String s) {
        if (controller.getConfiguration() != null && controller.getConfiguration().isDebugMode()) {
            logger.log(Level.INFO, "[MCBouncer DEBUG] " + s);
        }
    }

    public void severe(String s) {
        logger.log(Level.SEVERE, "[MCBouncer] " + s);
    }

    public void warning(String s) {
        logger.log(Level.WARNING, "[MCBouncer] " + s);
    }

    public void info(String s, Throwable e) {
        logger.log(Level.INFO, "[MCBouncer] " + s, e);
    }

    public void debug(String s, Throwable e) {
        if (controller.getConfiguration() != null && controller.getConfiguration().isDebugMode()) {
            logger.log(Level.INFO, "[MCBouncer DEBUG] " + s, e);
        }
    }

    public void severe(String s, Throwable e) {
        logger.log(Level.SEVERE, "[MCBouncer] " + s, e);
    }

    public void warning(String s, Throwable e) {
        logger.log(Level.WARNING, "[MCBouncer] " + s, e);
    }
}