package com.mcbouncer.util;

/**
 * Provides useful methods dealing with the network
 * From javawork.org
 */
public class NetUtil {

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

    public static boolean isIPAddress(String string) {
        try {
            return (NetUtil.long2ip(NetUtil.ip2long(string)).equals(string));
        } catch (Exception e) {
            return false;
        }
    }
}
