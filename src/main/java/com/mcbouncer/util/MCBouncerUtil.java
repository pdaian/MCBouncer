package com.mcbouncer.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
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

    public static boolean removeIPBan(String playerName) {
        return MCBouncerAPI.removeIPBan(playerName, MCBouncerConfig.getApiKey());
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

    public static ArrayList<HashMap<String, Object>> getBans(String user) {
        return MCBouncerAPI.getBans(user, MCBouncerConfig.getApiKey(), "0", "50");
    }

    public static JSONObject getMCBLookup(String playerName) { // [irc.esper.net] #mcbans <Firestar> and tbh i have said i do not care if people make their own plugins that integrate with mcbans
        StringBuilder response = new StringBuilder();
        if (MCBouncerConfig.getMcBansKey().equals("sample")) {
            return null;
        }
        try {
            String request = "player" + URLEncoder.encode(playerName, "UTF-8") + "&exec=lookup_user";
            URLConnection conn = new URL("http://72.10.39.172/" + MCBouncerConfig.getMcBansKey()).openConnection(); // http://www.exampledepot.com/egs/java.net/Post.html
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
        } catch (Exception e) {
        }
        return null;
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
                out = out + part + glue;
            }
        }

        if ("".equals(out)) {
            return "";
        }

        out = out.substring(0, out.length() - glue.length());

        return out;
    }

    public static boolean isIPAddress(String string) {
        try {
            return (NetUtil.long2ip(NetUtil.ip2long(string)).equals(string));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDefaultReason(String[] args, String main, String defaultString) {
        if (args.length > 1) {
            return main;
        } else {
            return defaultString;
        }
    }

    public static String plural(int i, String one, String notone) {
        if (i == 1) {
            return one;
        }
        return notone;
    }

    public static String[] JSONtoString(JSONArray arr) {
        String[] out = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            out[i] = arr.get(i).toString();
        }
        return out;
    }
}
