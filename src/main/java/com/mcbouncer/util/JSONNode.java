package com.mcbouncer.util;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONNode extends MapNode {

    @SuppressWarnings("unchecked")
    public JSONNode(Object jsonObject) {
        if (jsonObject instanceof JSONArray) {
            JSONArray arr = (JSONArray) jsonObject;
            HashMap<String, Object> newMap = new HashMap<String, Object>();

            Integer i = 0;
            for (Object obj : arr) {
                newMap.put(i.toString(), obj);
                i++;
            }
            this.setRoot(newMap);
        } else if (jsonObject instanceof JSONObject) {
            this.setRoot((Map<String, Object>) jsonObject);
        }

    }
}
