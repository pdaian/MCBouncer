package com.mcbouncer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.util.config.Configuration;

public class MCBouncerConfig {
    private static boolean debugMode = false;
    private static int numBansDisallow = 10;
    private static String apiKey = "";
    private static boolean showBanMessages = true;
    private static String defaultReason = "Banned for rule violation.";
    private static String defaultKickMessage = "Kicked by an admin.";
    private static Configuration config = null;

    public static void load(File folder) {
        folder.mkdirs();
        File file = new File(folder, "config.yml");
        config = new Configuration(file);
        config.load();
        if (!file.exists()) {
            try {
                File f = new File(folder.getPath() + "/config.properties");
                f.createNewFile();
                FileWriter fstream = new FileWriter(folder.getPath() + "/config.yml");
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("# Replace this with your API key from mcbouncer.com/apikey\napiKey: REPLACE\nnumBansDisallow: 10\nshowBanMessages: true\ndefaultBanMessage: 'Banned for rule violation.'\ndefaultKickMessage: 'Kicked by an admin.;\n");
                out.close();
                config.load();
            } catch (IOException ex) {
                Logger.getLogger(MCBouncerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setSettings();
    }
    private static void setSettings() {
        debugMode = config.getBoolean("debug", debugMode);
        apiKey = config.getString("apiKey", apiKey);
        numBansDisallow = config.getInt("numBansDisallow", numBansDisallow);
        showBanMessages = config.getBoolean("showBansMessages", showBanMessages);
        defaultReason = config.getString("defaultBanMessage", "Banned for rule violation.");
        defaultKickMessage = config.getString("defaultKickMessage", "Kicked by an admin.");
    }
    public static String getApiKey() {
        return apiKey;
    }
    public static Configuration getConfig() {
        return config;
    }
    public static boolean isDebugMode() {
        return debugMode;
    }
    public static String getDefaultReason() {
        return defaultReason;
    }
    public static int getNumBansDisallow() {
        return numBansDisallow;
    }
    public static boolean isShowBanMessages() {
        return showBanMessages;
    }
    public static String getDefaultKickMessage() {
        return defaultKickMessage;
    }
}