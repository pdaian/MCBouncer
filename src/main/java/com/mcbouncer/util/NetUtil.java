package com.mcbouncer.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Provides useful methods dealing with the network
 * From javawork.org
 */
public class NetUtil {

    /**
     * Gets the local IP address
     *
     * @return The local IP address
     */
    public static String getLocalIpAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * IP address to long
     *
     * @param aIpAddress
     * @return Number representing an ip address
     */
    public static long ip2long(String aIpAddress) {
        long ip = 0;
        String[] t = aIpAddress.split("\\.");
        ip = Long.parseLong(t[0]) * 256 * 256 * 256;
        ip += Long.parseLong(t[1]) * 256 * 256;
        ip += Long.parseLong(t[2]) * 256;
        ip += Long.parseLong(t[3]);
        return ip;
    }

    /**
     * Long to IP address
     *
     * @param aIpAddress
     * @return String representing an ip address
     */
    public static String long2ip(long aIpAddress) {
        return (aIpAddress >> 24 & 255) + "." + (aIpAddress >> 16 & 255) + "." + (aIpAddress >> 8 & 255) + "." + (aIpAddress & 255);
    }
}
