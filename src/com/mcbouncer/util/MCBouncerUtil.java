package com.mcbouncer.util;

public class MCBouncerUtil {

    public static void addBan(String playerName, String admin, String reason) {
        MCBouncerAPI.addBan(playerName, MCBouncerConfig.getApiKey(), admin, reason);
    }
    public static int getBanCount(String playerName, String IP) {
        return (int) (MCBouncerAPI.getBanCount(playerName, MCBouncerConfig.getApiKey()) + MCBouncerAPI.getIPBanCount(IP, MCBouncerConfig.getApiKey()));
    }
    public static int getNoteCount(String playerName) {
        return (int) MCBouncerAPI.getNoteCount(playerName, MCBouncerConfig.getApiKey());
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
