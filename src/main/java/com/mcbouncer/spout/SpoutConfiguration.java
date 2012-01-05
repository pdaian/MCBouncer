package com.mcbouncer.spout;

import com.mcbouncer.bukkit.BukkitConfiguration;
import java.io.File;

/**
 * Everything Bukkit config does is not-Bukkit specific. Cool.
 * 
 */
public class SpoutConfiguration extends BukkitConfiguration {

    public SpoutConfiguration(File dataFolder) {
        super(dataFolder);
    }

    
}
