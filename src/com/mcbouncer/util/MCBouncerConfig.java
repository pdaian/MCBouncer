package com.mcbouncer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MCBouncerConfig {

    private String apiKey = "";
    private int numBansDisallow = 10;
    private boolean showBanMessages = true;

    public MCBouncerConfig(File folder) {
        Properties configFile = new Properties();
        try {
            File f = new File(folder.getPath() + "/config.properties");
            if (f.exists()) {
                configFile.load(this.getClass().getClassLoader().getResourceAsStream(folder.getPath() + "/config.properties"));
            } else {
                FileWriter fstream = new FileWriter(folder.getPath() + "/config.properties");
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("# Replace this with your API key from mcbouncer.com/apikey\napiKey:REPLACE\nnumBansDisallow:10\nshowBanMessages:true\n");
                out.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MCBouncerConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

