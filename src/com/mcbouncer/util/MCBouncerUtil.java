package com.mcbouncer.util;

public class MCBouncerUtil {

    public static void addBan(String playerName) {
        
    }
    public static boolean isAllowedToJoin(String playerName, String IP) {
        MCBouncerAPI.updateUser(playerName, MCBouncerConfig.getApiKey(), IP);
        return isLocallyBanned(playerName);
    }
    public static String getBanReason(String playerName) {
        if (isLocallyBanned(playerName)) {// local ban
            return MCBouncerAPI.getBanReason(playerName, MCBouncerConfig.getApiKey());
        }
        return "Exceeded ban threshold. mcbouncer.com/banned for more info.";
    }
    public static boolean isLocallyBanned(String playerName) {
        return true;
    }
    public static void modMessage(String message) {
        // Steal this
    }


}
