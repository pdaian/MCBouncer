package com.mcbouncer.util;

import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.config.MCBConfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MCBouncerAPI {

    private static String lastFailure = "";
    public static String website = "http://mcbouncer.com";

    /**
     * Returns the number of bans a user has on every server
     * @param user Username to check
     * @param key API key of the server
     * @return long Number of bans
     * @static
     */
    public static long getBanCount(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getBanCount/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (Long) result.get("totalcount");
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return 0;
    }

    /**
     * Returns the number of notes a user has on every server
     * @param user Username to check
     * @param key API key of the server
     * @return long Number of notes
     * @static
     */
    public static long getNoteCount(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getNoteCount/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (Long) result.get("totalcount");
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return 0;
    }

    /**
     * Returns the number of bans an IP has on every server
     * @param IP IP to check
     * @param key API key of the server
     * @return long Number of bans
     * @static
     */
    public static long getIPBanCount(String IP, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getIPBanCount/" + key + "/" + IP));
        if ((Boolean) result.get("success")) {
            return (Long) result.get("totalcount");
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return 0;
    }

    /**
     * Returns a list of all the bans that an IP has
     * @param IP IP to check
     * @param key Server API key
     * @param page Page to get (0 is the first page, 1 is the second, and so on)
     * @param numEntries Number of entries to get per page
     * @return ArrayList<HashMap<String, Object>> ArrayList of HashMaps. The hashmap key is the server address, and the value is the ban data
     * @static
     */
    public static ArrayList<HashMap<String, Object>> getIPBans(String IP, String key, String page, String numEntries) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getIPBans/" + key + "/" + IP + "/" + page + "/" + numEntries));
        if ((Boolean) result.get("success")) {
            return (ArrayList<HashMap<String, Object>>) result.get("data");
        }
        return (ArrayList<HashMap<String, Object>>) MCBouncerAPI.setError((String) result.get("error"));
    }

    /**
     * Returns a list of all the bans that a user has
     * @param user Username to check
     * @param key Server API key
     * @param page Page to get (0 is the first page, 1 is the second, and so on)
     * @param numEntries Number of entries to get per page
     * @return ArrayList<HashMap<String, Object>> ArrayList of HashMaps. The hashmap key is the server address, and the value is the ban data
     * @static
     */
    public static ArrayList<HashMap<String, Object>> getBans(String user, String key, String page, String numEntries) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getBans/" + key + "/" + user + "/" + page + "/" + numEntries));
        if ((Boolean) result.get("success")) {
            return (ArrayList<HashMap<String, Object>>) result.get("data");
        }
        return (ArrayList<HashMap<String, Object>>) MCBouncerAPI.setError((String) result.get("error"));
    }

    /**
     * Returns a list of all the notes that a user has
     * @param user Username to check
     * @param key Server API key
     * @param page Page to get (0 is the first page, 1 is the second, and so on)
     * @param numEntries Number of entries to get per page
     * @return ArrayList<HashMap<String, Object>> ArrayList of HashMaps. The hashmap key is the server address, and the value is the note data
     * @static
     */
    public static ArrayList<HashMap<String, Object>> getNotes(String user, String key, String page, String numEntries) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getNotes/" + key + "/" + user + "/" + page + "/" + numEntries));
        if ((Boolean) result.get("success")) {
            return (ArrayList<HashMap<String, Object>>) result.get("data");
        }
        return (ArrayList<HashMap<String, Object>>) MCBouncerAPI.setError((String) result.get("error"));
    }

    /**
     * Update the last login time of a username
     * @param user Username to update
     * @param key Server API key
     * @param IP IP that the user has logged in under
     * @return boolean Whether or not the API call succeeded
     */
    public static boolean updateUser(String user, String key, String IP) {
        return (Boolean) ((MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/updateUser/" + key + "/" + user + "/" + IP))).get("success"));
    }

    public static String getBanReason(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getBanReason/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (String) result.get("reason");
        }
        return (String) result.get("error");
    }

    public static String getIPBanReason(String ip, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getIPBanReason/" + key + "/" + ip));
        if ((Boolean) result.get("success")) {
            return (String) result.get("reason");
        }
        return (String) result.get("error");
    }

    public static boolean isBanned(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getBanReason/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (Boolean) result.get("is_banned");
        }
        return false;
    }

    public static boolean isIPBanned(String ip, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/getIPBanReason/" + key + "/" + ip));
        if ((Boolean) result.get("success")) {
            return (Boolean) result.get("is_banned");
        }
        return false;
    }

    public static boolean removeNote(int noteid, String key, String issuer) {
        JSONObject result = (MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/removeNote/" + key + "/" + issuer  + "/" + noteid)));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }

    public static boolean removeBan(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/removeBan/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }

    public static boolean removeIPBan(String IP, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/removeIPBan/" + key + "/" + IP));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }

    public static boolean addIPBan(String IP, String key, String issuer, String reason) {
        reason = encode(reason);
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/addIPBan/" + key + "/" + issuer + "/" + IP + "/" + reason));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }

    public static boolean addBan(String user, String key, String issuer, String reason) {
        reason = encode(reason);
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/addBan/" + key + "/" + issuer + "/" + user + "/" + reason));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }

    public static boolean addNote(String user, String key, String issuer, String note) {
        note = encode(note);
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl(website + "/api/addNote/" + key + "/" + issuer + "/" + user + "/" + note));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }

    private static Object setError(String e) {
        MCBouncerAPI.lastFailure = (String) e;
        return null;
    }

    public static String getError() {
        return MCBouncerAPI.lastFailure;
    }

    private static String getUrl(String site) {
        MCBouncer.log.debug("Getting URL - " + site);
        try {
            URL url = new URL(site.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine, result = "";
            while ((inputLine = in.readLine()) != null) {
                result = result.concat(inputLine);
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static JSONObject parseJson(String jsonin) {
        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(jsonin);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static String encode(String pText) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pText.length(); i++) {
            char token = pText.charAt(i);
            if (Character.isLetterOrDigit(token)) {
                builder.append(token);
                continue;
            }
            String s = "%" + Integer.toHexString((int) token);
            builder.append(s);
        }
        return builder.toString();
    }
}
