package com.mcbouncer.util;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.config.MCBConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MCBUpdateThread extends Thread {

    private MCBouncer plugin;

    public MCBUpdateThread(MCBouncer plugin) {
        this.plugin = plugin;
    }

    public void run() {
        if (MCBConfiguration.isOnlineMode()) {
            save(MCBouncerUtil.getServerBans(), "server_bans_backup.dat");
            save(MCBouncerUtil.getServerIPBans(), "server_ip_bans_backup.dat");
        }
    }

    private void save(ArrayList<HashMap<String, Object>> list, String filename) {
        try {
            FileOutputStream fout = new FileOutputStream(plugin.getDataFolder() + File.separator + filename);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, Object>> load(String filename) {
        try {
            FileInputStream fin = new FileInputStream("plugins" + File.separator + "MCBouncer" + File.separator + filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            ArrayList<HashMap<String, Object>> obj = (ArrayList<HashMap<String, Object>>) ois.readObject();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}