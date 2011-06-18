package com.mcbouncer.util;

public class MCBValidators {
    /**
     * Returns true if a value exists inside a String[]
     */
    public static boolean inArray(String substring, String[] strings) {
        for (String string : strings) {
            if (string.equals(substring)) {
                return true;
            }
        }
        return false;
    }
}
