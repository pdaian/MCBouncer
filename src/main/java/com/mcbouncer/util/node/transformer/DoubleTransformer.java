package com.mcbouncer.util.node.transformer;

import org.apache.commons.collections.Transformer;

/**
 * Apache Commons collection transformer
 * Transforms objects to doubles.
 * 
 */
public class DoubleTransformer implements Transformer {

    public Object transform(Object o) {
        if (o == null) {
            return null;
        }

        return Double.parseDouble(o.toString());
    }
}
