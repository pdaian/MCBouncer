package com.mcbouncer.util;

import com.mcbouncer.util.config.MCBConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class MCBouncerUtil {

    public static boolean addBan(String playerName, String admin, String reason) {
        return MCBouncerAPI.addBan(playerName, MCBConfiguration.getApiKey(), admin, reason);
    }

    public static boolean addIPBan(String IP, String admin, String reason) {
        return MCBouncerAPI.addIPBan(IP, MCBConfiguration.getApiKey(), admin, reason);
    }
    
    public static boolean addNote(String playerName, String admin, String note) {
        return MCBouncerAPI.addNote(playerName, MCBConfiguration.getApiKey(), admin, note);
    }

    public static boolean removeBan(String playerName) {
        return MCBouncerAPI.removeBan(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean removeIPBan(String playerName) {
        return MCBouncerAPI.removeIPBan(playerName, MCBConfiguration.getApiKey());
    }
    
    public static boolean removeNote(int noteID, String admin) {
        return MCBouncerAPI.removeNote(noteID, MCBConfiguration.getApiKey(), admin);
    }

    public static int getBanCount(String playerName, String IP) {
        return (int) (MCBouncerAPI.getBanCount(playerName, MCBConfiguration.getApiKey()) + MCBouncerAPI.getIPBanCount(IP, MCBConfiguration.getApiKey()));
    }

    public static int getNoteCount(String playerName) {
        return (int) MCBouncerAPI.getNoteCount(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean isBanned(String playerName) {
        return MCBouncerAPI.isBanned(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean isIPBanned(String IP) {
        return MCBouncerAPI.isIPBanned(IP, MCBConfiguration.getApiKey());
    }

    public static void updateUser(String playerName, String IP) {
        MCBouncerAPI.updateUser(playerName, MCBConfiguration.getApiKey(), IP);
    }

    public static String getBanReason(String playerName) {
        return MCBouncerAPI.getBanReason(playerName, MCBConfiguration.getApiKey());
    }

    public static String getIPBanReason(String IP) {
        return MCBouncerAPI.getIPBanReason(IP, MCBConfiguration.getApiKey());
    }

    public static boolean isLocallyBanned(String playerName) {
        return true;
    }

    public static ArrayList<HashMap<String, Object>> getBans(String user) {
        return MCBouncerAPI.getBans(user, MCBConfiguration.getApiKey(), "0", "50");
    }

    public static String implodeWithoutFirstElement(String[] array, String glue) {
        String out = "";
        if (array.length == 0) {
            return out;
        }
        boolean first = true;
        for (String part : array) {
            if (first) {
                first = false;
            } else {
                out += part + glue;
            }
        }
        if ("".equals(out)) {
            return "";
        }
        return out.substring(0, out.length() - glue.length());
    }

    public static boolean isIPAddress(String string) {
        try {
            return (NetUtil.long2ip(NetUtil.ip2long(string)).equals(string));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDefaultReason(String[] args, String main, String defaultString) {
        return args.length > 1 ? main : defaultString;
    }

    public static String plural(int i, String one, String notone) {
        return i== 1 ? one : notone;
    }

    public static String[] JSONtoString(JSONArray arr) {
        String[] out = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            out[i] = arr.get(i).toString();
        }
        return out;
    }
}
