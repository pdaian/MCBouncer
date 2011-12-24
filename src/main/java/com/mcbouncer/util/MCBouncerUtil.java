package com.mcbouncer.util;

import com.mcbouncer.util.config.MCBConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MCBouncerUtil {

    public static boolean addBan(String playerName, String admin, String reason) {
        return MCBouncerAPI.addBan(playerName, MCBConfiguration.getApiKey(), admin, reason);
    }

    public static boolean addIPBan(String IP, String admin, String reason) {
        return MCBouncerAPI.addIPBan(IP, MCBConfiguration.getApiKey(), admin, reason);
    }

    public static boolean addNote(String playerName, String admin, String note) {
        return MCBouncerAPI.addNote(playerName, MCBConfiguration.getApiKey(), admin, note);
    }

    public static boolean removeBan(String playerName) {
        return MCBouncerAPI.removeBan(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean removeIPBan(String playerName) {
        return MCBouncerAPI.removeIPBan(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean removeNote(int noteID, String admin) {
        return MCBouncerAPI.removeNote(noteID, MCBConfiguration.getApiKey(), admin);
    }

    public static int getBanCount(String playerName, String IP) {
        return (int) (MCBouncerAPI.getBanCount(playerName, MCBConfiguration.getApiKey()) + MCBouncerAPI.getIPBanCount(IP, MCBConfiguration.getApiKey()));
    }

    public static int getNoteCount(String playerName) {
        return (int) MCBouncerAPI.getNoteCount(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean isBanned(String playerName) {
        return MCBouncerAPI.isBanned(playerName, MCBConfiguration.getApiKey());
    }

    public static boolean isIPBanned(String IP) {
        return MCBouncerAPI.isIPBanned(IP, MCBConfiguration.getApiKey());
    }

    public static void updateUser(String playerName, String IP) {
        MCBouncerAPI.updateUser(playerName, MCBConfiguration.getApiKey(), IP);
    }

    public static String getBanReason(String playerName) {
        return MCBouncerAPI.getBanReason(playerName, MCBConfiguration.getApiKey());
    }

    public static String getIPBanReason(String IP) {
        return MCBouncerAPI.getIPBanReason(IP, MCBConfiguration.getApiKey());
    }

    public static ArrayList<HashMap<String, Object>> getBans(String user) {
        return MCBouncerAPI.getBans(user, MCBConfiguration.getApiKey(), "0", "50");
    }

    public static ArrayList<HashMap<String, Object>> getIPBans(String ip) {
        return MCBouncerAPI.getIPBans(ip, MCBConfiguration.getApiKey(), "0", "50");
    }

    public static ArrayList<HashMap<String, Object>> getNotes(String user) {
        return MCBouncerAPI.getNotes(user, MCBConfiguration.getApiKey(), "0", "50");
    }

    // Joining
    //-----------------------------------------------------------------------
    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No separator is added to the joined String.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null)            = null
     * StringUtils.join([])              = ""
     * StringUtils.join([null])          = ""
     * StringUtils.join(["a", "b", "c"]) = "abc"
     * StringUtils.join([null, "", "a"]) = "a"
     * </pre>
     *
     * @param <T> the specific type of values to join together
     * @param elements  the values to join together, may be null
     * @return the joined String, {@code null} if null array input
     * @since 2.0
     * @since 3.0 Changed signature to use varargs
     */
    public static <T> String join(T... elements) {
        return join(elements, null);
    }

    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null array input
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from.  It is
     * an error to pass in an end index past the end of the array
     * @param endIndex the index to stop joining from (exclusive). It is
     * an error to pass in an end index past the end of the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String debugMap(Map<?, ?> map) {
        StringBuilder build = new StringBuilder();

        for (Object key : map.keySet()) {
            build.append(key).append(" - ").append(map.get(key)).append("\n");
        }

        return build.toString();
    }
}
