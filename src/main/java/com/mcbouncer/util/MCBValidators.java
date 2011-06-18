package com.mcbouncer.util;

public class MCBValidators {

    public static boolean UserAndReasonValidator(String[] args) {
        return args.length > 0;
    }

    public static boolean UserValidator(String[] args) {
        return args.length == 1;
    }
    
    public static boolean thereAreTooManyValidators(String[] args) {
        return args.length > 1;
    }

    /**
     * Returns true if the string is an integer
     */
    public static boolean isInteger(String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if the string is a number
     */
    public static boolean isNumeric(String arg) {
        try {
            Double.parseDouble(arg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if a string is at least a length
     */
    public static boolean isAtLeastLength(String str, int length) {
        return str.length() >= length;
    }

    /**
     * Returns true if there are at least a number of arguments
     */
    public static boolean isAtLeastArgs(String[] args, int length) {
        return args.length >= length;
    }

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
