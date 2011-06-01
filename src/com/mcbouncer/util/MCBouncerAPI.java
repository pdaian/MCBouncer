package com.mcbouncer.util;

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

    public static long getBanCount(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getBanCount/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (Long) result.get("totalcount");
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return 0;
    }
    public static long getNoteCount(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getNoteCount/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (Long) result.get("totalcount");
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return 0;
    }
    public static long getIPBanCount(String IP, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getIPBanCount/" + key + "/" + IP));
        if ((Boolean) result.get("success")) {
            return (Long) result.get("totalcount");
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return 0;
    }
    public static ArrayList<HashMap<String, Object>> getIPBans(String IP, String key, String page, String numEntries) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getIPBans/" + key + "/" + IP + "/" + page + "/" + numEntries));
        if ((Boolean) result.get("success")) {
            return (ArrayList<HashMap<String, Object>>) result.get("data");
        }
        return (ArrayList<HashMap<String, Object>>) MCBouncerAPI.setError((String) result.get("error"));
    }
    public static ArrayList<HashMap<String, Object>> getBans(String user, String key, String page, String numEntries) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getBans/" + key + "/" + user + "/" + page + "/" + numEntries));
        if ((Boolean) result.get("success")) {
            return (ArrayList<HashMap<String, Object>>) result.get("data");
        }
        return (ArrayList<HashMap<String, Object>>) MCBouncerAPI.setError((String) result.get("error"));
    }
    public static ArrayList<HashMap<String, Object>> getNotes(String user, String key, String page, String numEntries) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getNotes/" + key + "/" + user + "/" + page + "/" + numEntries));
        if ((Boolean) result.get("success")) {
            return (ArrayList<HashMap<String, Object>>) result.get("data");
        }
        return (ArrayList<HashMap<String, Object>>) MCBouncerAPI.setError((String) result.get("error"));
    }
    public static boolean updateUser(String user, String key, String IP) {
        return (Boolean) ((MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/updateUser/" + key + "/" + user + "/" + IP))).get("success"));
    }
    public static String getBanReason(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/getBanReason/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return (String) result.get("reason");
        }
        return (String) result.get("error");
    }
    public static boolean removeNote(int noteid, String key) {
        JSONObject result = (MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/removeNote/" + key + "/" + noteid)));
        if ((Boolean) result.get("success"))
            return true;
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }
    public static boolean removeBan(String user, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/removeBan/" + key + "/" + user));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }
    public static boolean removeIPBan(String IP, String key) {
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/removeIPBan/" + key + "/" + IP));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }    
    public static boolean addIPBan(String IP, String key, String issuer, String reason) {
        reason = java.net.URLEncoder.encode(reason);
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/addIPBan/" + key + "/" + issuer+ "/" + IP + "/" + reason));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }        
     public static boolean addBan(String user, String key, String issuer, String reason) {
        reason = java.net.URLEncoder.encode(reason);
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/addBan/" + key + "/" + issuer+ "/" + user + "/" + reason));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }   
     public static boolean addNote(String user, String key, String issuer, String note) {
        note = java.net.URLEncoder.encode(note);
        JSONObject result = MCBouncerAPI.parseJson(MCBouncerAPI.getUrl("http://mcbouncer.com/api/addNote/" + key + "/" + issuer+ "/" + user + "/" + note));
        if ((Boolean) result.get("success")) {
            return true;
        }
        MCBouncerAPI.setError((String) result.get("error"));
        return false;
    }      
    
    private static Object setError (String e) {
        MCBouncerAPI.lastFailure = (String) e;
        return null;
    }
    public static String getError() {
        return MCBouncerAPI.lastFailure;
    }

    private static String getUrl(String site) {
        String requestUrl = site;
        try {
            URL url = new URL(requestUrl.toString());
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

    private static JSONObject parseJson(String jsonin) {
        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(jsonin);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
