package com.mcbouncer.util.node.transformer;

import com.mcbouncer.util.node.MapNode;
import java.util.Map;
import org.apache.commons.collections.Transformer;

/**
 * Apache Commons collection transformer
 * Transforms objects to MapNodes.
 * 
 */
public class MapNodeTransformer implements Transformer {

    @SuppressWarnings("unchecked")
    public Object transform(Object o) {
        if (o == null) {
            return null;
        }

        if (o instanceof Map) {
            return new MapNode((Map<String, Object>) o);
        } else {
            return null;
        }
    }
}
