/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcbouncer.util;

import java.util.HashMap;

/**
 *
 * @author kingnerd
 */
public class MainTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(MCBouncerAPI.getIPBans("123.123.123.123", "apikey", "0", "20").get(0).get("server"));
        System.out.println(MCBouncerAPI.getIPBanCount("5.5.5.5", "apikey"));
        System.out.println(MCBouncerAPI.getBanReason("allnaturalx", "apikey"));
    }
}
