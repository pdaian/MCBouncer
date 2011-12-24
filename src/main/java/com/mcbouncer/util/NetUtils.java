package com.mcbouncer.util;

/**
 * Provides useful methods dealing with the network
 * From javawork.org
 */
public class NetUtils {

    public static long ip2long(String aIpAddress) {
        long ip = 0;
        String[] t = aIpAddress.split("\\.");
        ip = Long.parseLong(t[0]) * 256 * 256 * 256;
        ip += Long.parseLong(t[1]) * 256 * 256;
        ip += Long.parseLong(t[2]) * 256;
        ip += Long.parseLong(t[3]);
        return ip;
    }

    public static String long2ip(long aIpAddress) {
        return (aIpAddress >> 24 & 255) + "." + (aIpAddress >> 16 & 255) + "." + (aIpAddress >> 8 & 255) + "." + (aIpAddress & 255);
    }

    /**
     * Returns whether or not the given string is a valid IP address.
     * 
     * Not only will this return false for IP addresses
     * that are formatted like "foobagoo", but will also
     * detect invalid IPs like "0.0.0.257" (cannot have 257
     * as a number), "1.22.333.4444" (cannot have 4 digits),
     * and anything that does not resolve to a valid address.
     * 
     * @param string
     * @return 
     */
    public static boolean isIPAddress(String string) {
        try {
            return (NetUtils.long2ip(NetUtils.ip2long(string)).equals(string));
        } catch (Exception e) {
            return false;
        }
    }
}
