package com.mcbouncer.util.node;

import com.mcbouncer.util.node.transformer.BooleanTransformer;
import com.mcbouncer.util.node.transformer.DoubleTransformer;
import com.mcbouncer.util.node.transformer.IntegerTransformer;
import com.mcbouncer.util.node.transformer.MapNodeTransformer;
import com.mcbouncer.util.node.transformer.StringTransformer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

/**
 * Simple Map accessor class. For Maps of Maps, it's far easier
 * to call these methods using node syntax (key/key/key) instead
 * of Map.get(Map.get()) and whatnot. 
 * 
 * @author yetanotherx
 */
public class MapNode {

    protected Map<String, Object> base;

    public MapNode(Map<String, Object> base) {
        this.base = base;
    }

    public MapNode() {
        this.base = new HashMap<String, Object>();
    }

    public Map<String, Object> getBase() {
        return base;
    }

    public void setBase(Map<String, Object> base) {
        this.base = base;
    }

    /**
     * Gets the item located at the given location, separated
     * by slashes. For example, if there is a YAML file that
     * is formatted as such:
     *   yaml:
     *     foo:
     *       bar: baz
     * 
     * Calling getObjectFromPath("yaml/foo/bar") will return "baz"
     * 
     * @param prop
     * @return 
     */
    @SuppressWarnings("unchecked")
    public Object getObjectFromPath(String prop) {
        if (prop.contains("/")) {
            String[] split = prop.split("/");

            Map<String, Object> newBase = this.base;
            Object out = null;
            for (String splat : split) {
                Object got = newBase.get(splat);

                if (got == null) {
                    return null;
                } else {
                    try {
                        newBase = (Map<String, Object>) got;
                    } catch (ClassCastException ex) {
                        out = got;
                    }
                }

            }
            return out;
        } else {
            if (prop.length() == 0) {
                return base;
            }

            return base.get(prop);
        }
    }

    public String getString(String prop) {
        return getString(prop, null);
    }

    public String getString(String prop, String defaultResult) {
        Object out = this.getObjectFromPath(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return out.toString();
        }
    }

    public Integer getInteger(String prop) {
        return getInteger(prop, null);
    }

    public Integer getInteger(String prop, Integer defaultResult) {
        Object out = this.getObjectFromPath(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return Integer.parseInt(out.toString());
        }
    }

    public Double getDouble(String prop) {
        return getDouble(prop, null);
    }

    public Double getDouble(String prop, Double defaultResult) {
        Object out = this.getObjectFromPath(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return Double.parseDouble(out.toString());
        }
    }

    public Boolean getBoolean(String prop) {
        return getBoolean(prop, null);
    }

    public Boolean getBoolean(String prop, Boolean defaultResult) {
        Object out = this.getObjectFromPath(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return Boolean.parseBoolean(out.toString());
        }
    }

    public MapNode getMapNode(String prop) {
        return getMapNode(prop, null);
    }

    @SuppressWarnings("unchecked")
    public MapNode getMapNode(String prop, MapNode defaultResult) {
        Object out = this.getObjectFromPath(prop);
        if (out == null) {
            return defaultResult;
        } else {
            if (out instanceof Map) {
                return new MapNode((Map<String, Object>) out);
            } else {
                return null;
            }
        }
    }

    public List<Object> getList(String prop) {
        return getList(prop, new ArrayList<Object>());
    }

    @SuppressWarnings("unchecked")
    public List<Object> getList(String prop, List<Object> defaultResult) {
        Object out = this.getObjectFromPath(prop);

        if (out == null) {
            return defaultResult;
        } else {
            if (out instanceof List) {
                return (List<Object>) out;
            } else {
                return defaultResult;
            }
        }
    }

    public List<Integer> getIntegerList(String prop) {
        return getIntegerList(prop, new ArrayList<Integer>());
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getIntegerList(String prop, List<Integer> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<Integer> newList = new ArrayList<Integer>();
            CollectionUtils.collect(out, new IntegerTransformer(), newList);
            return newList;
        }
    }

    public List<Double> getDoubleList(String prop) {
        return getDoubleList(prop, new ArrayList<Double>());
    }

    @SuppressWarnings("unchecked")
    public List<Double> getDoubleList(String prop, List<Double> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<Double> newList = new ArrayList<Double>();
            CollectionUtils.collect(out, new DoubleTransformer(), newList);
            return newList;
        }
    }

    public List<String> getStringList(String prop) {
        return getStringList(prop, new ArrayList<String>());
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String prop, List<String> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<String> newList = new ArrayList<String>();
            CollectionUtils.collect(out, new StringTransformer(), newList);
            return newList;
        }
    }

    public List<Boolean> getBooleanList(String prop) {
        return getBooleanList(prop, new ArrayList<Boolean>());
    }

    @SuppressWarnings("unchecked")
    public List<Boolean> getBooleanList(String prop, List<Boolean> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<Boolean> newList = new ArrayList<Boolean>();
            CollectionUtils.collect(out, new BooleanTransformer(), newList);
            return newList;
        }
    }

    public List<MapNode> getMapNodeList(String prop) {
        return getMapNodeList(prop, new ArrayList<MapNode>());
    }

    @SuppressWarnings("unchecked")
    public List<MapNode> getMapNodeList(String prop, List<MapNode> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<MapNode> newList = new ArrayList<MapNode>();
            CollectionUtils.collect(out, new MapNodeTransformer(), newList);
            return newList;
        }
    }
}
