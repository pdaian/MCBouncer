package com.mcbouncer.util.node;

import com.mcbouncer.exception.InvalidArgumentException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONNode extends MapNode {

    @SuppressWarnings("unchecked")
    public JSONNode(Object jsonObject) {
        try {
            if (jsonObject == null) {
                this.setRoot(new HashMap<String, Object>());
            } else if (jsonObject instanceof Map) {
                this.setRoot((Map<String, Object>) jsonObject);
            } else if (jsonObject instanceof JSONArray) {
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
        } catch (ClassCastException e) {
            throw new InvalidArgumentException("Object must be a JSON object or a Map<String, Object>");
        }

    }
}
