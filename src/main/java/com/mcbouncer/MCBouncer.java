package com.mcbouncer;

import com.mcbouncer.util.MCBLogger;
import com.mcbouncer.util.commands.CommandManager;

public class MCBouncer {
    
    protected MCBLogger logger;
    protected LocalPlugin plugin;
    protected CommandManager commandManager;
    protected LocalConfiguration configuration;

    public MCBLogger getLogger() {
        return logger;
    }

    public LocalPlugin getPlugin() {
        return plugin;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public LocalConfiguration getConfiguration() {
        return configuration;
    }
    
    public static String getVersion() {
        return null;
    }
    
}
