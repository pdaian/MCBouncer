package com.mcbouncer.util;

import com.mcbouncer.plugin.MCBouncer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;

public class MCBouncerUtil {

    public static boolean addBan(String playerName, String admin, String reason) {
        return MCBouncerAPI.addBan(playerName, MCBouncerConfig.getApiKey(), admin, reason);
    }
    public static boolean addIPBan(String IP, String admin, String reason) {
        return MCBouncerAPI.addIPBan(IP, MCBouncerConfig.getApiKey(), admin, reason);
    }
    public static boolean removeBan(String playerName) {
        return MCBouncerAPI.removeBan(playerName, MCBouncerConfig.getApiKey());
    }

    public static int getBanCount(String playerName, String IP) {
        return (int) (MCBouncerAPI.getBanCount(playerName, MCBouncerConfig.getApiKey()) + MCBouncerAPI.getIPBanCount(IP, MCBouncerConfig.getApiKey()));
    }

    public static int getNoteCount(String playerName) {
        return (int) MCBouncerAPI.getNoteCount(playerName, MCBouncerConfig.getApiKey());
    }

    public static boolean isNotAllowedToJoin(String playerName, String IP) {
        MCBouncerAPI.updateUser(playerName, MCBouncerConfig.getApiKey(), IP);
        return MCBouncerAPI.isBanned(playerName, MCBouncerConfig.getApiKey()) || MCBouncerAPI.isBanned(playerName, MCBouncerConfig.getApiKey()); // Second one should be isIpBanned()
    }

    public static String getBanReason(String playerName) {
        return MCBouncerAPI.getBanReason(playerName, MCBouncerConfig.getApiKey());
    }

    public static boolean isLocallyBanned(String playerName) {
        return true;
    }

    public static void modMessage(String message, Plugin plugin) {
        final Player[] p = plugin.getServer().getOnlinePlayers();
        for (int z = 0; z < p.length; z++) {
            if (MCBouncer.permissionHandler.has(p[z], "mcbouncer.mod")) {
                p[z].sendMessage(message);
            }
        }
    }
    public static ArrayList<HashMap<String, Object>> getBans(String user) {
        return MCBouncerAPI.getBans(user, MCBouncerConfig.getApiKey(), "0", "50");
    }

    public static void appropriateNotify(String string) {
        return;
    }
    
    public static JSONObject getMCBLookup(String playerName) { // [irc.esper.net] #mcbans <Firestar> and tbh i have said i do not care if people make their own plugins that integrate with mcbans
        StringBuilder response = new StringBuilder();
        if (MCBouncerConfig.getMcBansKey().equals("sample")) {
            return null;
        }
        try {
            String request = "player" + URLEncoder.encode(playerName, "UTF-8") + "&exec=lookup_user";
            URLConnection conn = new URL("http://72.10.39.172/"+MCBouncerConfig.getMcBansKey()).openConnection(); // http://www.exampledepot.com/egs/java.net/Post.html
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(request);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            wr.close();
            rd.close();
            return MCBouncerAPI.parseJson(response.toString());
        } catch (Exception e) {}
        return null;
    }
    
    public static String implode(String[] s, String delimiter) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 1; i < s.length; i++) {
            buffer.append(s[i]).append(delimiter);
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }
}
