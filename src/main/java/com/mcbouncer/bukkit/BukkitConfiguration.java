package com.mcbouncer.bukkit;

import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.node.YAMLFileNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BukkitConfiguration extends LocalConfiguration {

    protected File dataFolder;

    public BukkitConfiguration(File dataFolder) {
        this.dataFolder = dataFolder;
    }
    
    @Override
    public void load() {
        
        dataFolder.getParentFile().mkdirs();
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

        setSettings();
        
    }

    protected void setSettings() {
        debugMode = conf.getBoolean("config.debug", debugMode);
        apiKey = conf.getString("config.apiKey", apiKey);
        numBansDisallow = conf.getInt("config.numBansDisallow", numBansDisallow);
        showBanMessages = conf.getBoolean("config.showBansMessages", showBanMessages);
        defaultReason = conf.getString("config.defaultBanMessage", defaultReason);
        defaultKickMessage = conf.getString("config.defaultKickMessage", defaultKickMessage);
        //plugins = (ArrayList<String>) config.getStringList("plugins", plugins);
    }
    
}
