package com.mcbouncer.util.config;

import com.mcbouncer.plugin.MCBouncer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MCBConfiguration {

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

            config.load();
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