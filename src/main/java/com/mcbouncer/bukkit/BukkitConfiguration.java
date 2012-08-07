package com.mcbouncer.bukkit;

import com.mcbouncer.util.ConfigDefaults;
import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.node.YAMLFileNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of the LocalConfiguration interface. 
 * It uses YAML to load in a config.yml file, but it
 * extends MapNode as well, so standard node accessors
 * can be used on it.
 * 
 * @author sk89q
 */
public class BukkitConfiguration extends LocalConfiguration {

    protected File dataFolder;

    public BukkitConfiguration(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    /**
     * Loads the config.yml file. If it doesn't
     * exist, it copies the file in the defaults/
     * folder in the jar to the right location
     * 
     */
    @Override
    public void load() {

        dataFolder.mkdirs();
        File file = new File(dataFolder, "config.yml");

        if (!file.exists()) {
            InputStream input = MCBouncer.class.getResourceAsStream("/defaults/config.yml");
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(file);
                    byte[] buf = new byte[8192];
                    int length = 0;
                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (IOException e) {
                    }

                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }

        }

        YAMLFileNode node = new YAMLFileNode(file);
        node.load();
        this.conf = node;

        debugMode = conf.getBoolean("debug", ConfigDefaults.DEBUG.getBoolVal());
        apiKey = conf.getString("apiKey", ConfigDefaults.APIKEY.getStrVal());
        numBansDisallow = conf.getInteger("numBansDisallow", ConfigDefaults.BANSDISALLOW.getIntVal());
        showBanMessages = conf.getBoolean("showBanMessages", ConfigDefaults.SHOWMESSAGES.getBoolVal());
        defaultReason = conf.getString("defaultBanMessage", ConfigDefaults.DEFAULTBAN.getStrVal());
        defaultKickMessage = conf.getString("defaultKickMessage", ConfigDefaults.DEFAULTKICK.getStrVal());
        website = conf.getString("website", ConfigDefaults.WEBSITE.getStrVal());
        disableIPFunctions = conf.getBoolean("disableIPFunctions", ConfigDefaults.DISABLEIPFUNCTIONS.getBoolVal());

    }

}
