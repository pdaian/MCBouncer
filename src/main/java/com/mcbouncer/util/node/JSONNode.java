package com.mcbouncer.util.node;

import com.mcbouncer.exception.InvalidArgumentException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Extension of MapNode that accepts, in addition 
 * to the standard Map<String, Object>, a JSONObject
 * or JSONArray that will be cast to a map. If an
 * JSONArray is passed, the key each array element will
 * be the numerical ID of the value.
 * 
 */
public class JSONNode extends MapNode {

    @SuppressWarnings("unchecked")
    public JSONNode(Object jsonObject) {
        try {
            if (jsonObject == null) {
                this.setBase(new HashMap<String, Object>());
            } else if (jsonObject instanceof Map) {
                this.setBase((Map<String, Object>) jsonObject);
            } else if (jsonObject instanceof JSONArray) {
                JSONArray arr = (JSONArray) jsonObject;
                HashMap<String, Object> newMap = new HashMap<String, Object>();

                Integer i = 0;
                for (Object obj : arr) {
                    newMap.put(i.toString(), obj);
                    i++;
                }
                this.setBase(newMap);
            } else if (jsonObject instanceof JSONObject) {
                this.setBase((Map<String, Object>) jsonObject);
            }
        } catch (ClassCastException e) {
            throw new InvalidArgumentException("Object must be a JSON object or a Map<String, Object>");
        }

    }
}
