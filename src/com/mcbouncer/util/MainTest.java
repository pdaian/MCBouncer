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
        System.out.println(((HashMap<String, Object>)MCBouncer.getIPBans("5.5.5.5", "apikey", "0", "50").get("1")).get("ip"));
    }
}
