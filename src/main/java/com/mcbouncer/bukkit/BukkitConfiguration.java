package com.mcbouncer.bukkit;

import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.node.YAMLFileNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO: Implement the remaining config nodes
 * 
 * @author yetanotherx
 */
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
//TODO: Broken
        setSettings();
        
    }

    protected void setSettings() {
        debugMode = conf.getBoolean("debug", debugMode);
        apiKey = conf.getString("apiKey", apiKey);
        numBansDisallow = conf.getInteger("numBansDisallow", numBansDisallow);
        showBanMessages = conf.getBoolean("showBansMessages", showBanMessages);
        defaultReason = conf.getString("defaultBanMessage", defaultReason);
        defaultKickMessage = conf.getString("defaultKickMessage", defaultKickMessage);
        website = conf.getString("website", website); 
        //plugins = (ArrayList<String>) config.getStringList("plugins", plugins);
    }

    @Override
    public String getAPIKey() {
        return apiKey;
    }

    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public String getDefaultKickReason() {
        return defaultKickMessage;
    }

    @Override
    public String getDefaultReason() {
        return defaultReason;
    }

    @Override
    public int getNumBansDisallow() {
        return numBansDisallow;
    }

    @Override
    public boolean isShowBanMessages() {
        return showBanMessages;
    }

    @Override
    public String getWebsite() {
        return website;
    }
    
}
