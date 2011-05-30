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

public class MCBouncer {

    public static long getBanCount(String user, String key){
       HashMap<String, Object> result = MCBouncer.parseJson(MCBouncer.getUrl("http://mcbouncer.com/api/getBanCount/"+key+"/"+user), new String[] {"totalcount", "success"});
       if ((Boolean) result.get("success"))
           return (Long) result.get("totalcount");
       return 0;
    }
    public static long getNoteCount(String user, String key){
       HashMap<String, Object> result = MCBouncer.parseJson(MCBouncer.getUrl("http://mcbouncer.com/api/getNoteCount/"+key+"/"+user), new String[] {"totalcount", "success"});
       if ((Boolean) result.get("success"))
           return (Long) result.get("totalcount");
       return 0;
    }
    public static long getIPBanCount(String IP, String key){
       HashMap<String, Object> result = MCBouncer.parseJson(MCBouncer.getUrl("http://mcbouncer.com/api/getIPBanCount/"+key+"/"+IP), new String[] {"totalcount", "success"});
       if ((Boolean) result.get("success"))
           return (Long) result.get("totalcount");
       return 0;
    }
    public static ArrayList<HashMap<String, Object>> getIPBans(String IP, String key, String page, String numEntries) {
       HashMap<String, Object> result = MCBouncer.parseJson(MCBouncer.getUrl("http://mcbouncer.com/api/getIPBans/"+key+"/"+IP+"/"+page+"/"+numEntries), new String[] {"data", "success"});
       if ((Boolean) result.get("success"))
           return (ArrayList<HashMap<String, Object>>) result.get("data");
       return null;
    }
    public static ArrayList<HashMap<String, Object>> getBans(String user, String key, String page, String numEntries) {
       HashMap<String, Object> result = MCBouncer.parseJson(MCBouncer.getUrl("http://mcbouncer.com/api/getBans/"+key+"/"+user+"/"+page+"/"+numEntries), new String[] {"data", "success"});
       if ((Boolean) result.get("success"))
           return (ArrayList<HashMap<String, Object>>) result.get("data");
       return null;
    }
    public static ArrayList<HashMap<String, Object>> getNotes(String user, String key, String page, String numEntries) {
       HashMap<String, Object> result = MCBouncer.parseJson(MCBouncer.getUrl("http://mcbouncer.com/api/getNotes/"+key+"/"+user+"/"+page+"/"+numEntries), new String[] {"data", "success"});
       if ((Boolean) result.get("success"))
           return (ArrayList<HashMap<String, Object>>) result.get("data");
       return null;
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
    
    private static HashMap<String, Object> parseJson(String jsonin, String[] args) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        JSONObject json;
        try {
            json = (JSONObject)new JSONParser().parse(jsonin);
            for (String arg : args) {
                result.put(arg,json.get(arg));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }        
        return result;
    }
    
}
