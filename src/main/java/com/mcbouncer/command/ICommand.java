package com.mcbouncer.command;

public interface ICommand {
    String getPlayerNameFromArgs(String arg);
    String getPlayerName(String name);
    boolean isPlayerOnline(String name);
    String getSenderName();
    String getIPFromArgs(String arg, String kickReason);
    void sendMessageToSender(String message);
    void sendMessageToMods(String message);
    void kickPlayer(String player, String reason);
    void kickPlayerWithIP(String ip, String reason);
    boolean senderHasPermission(String permission);
    boolean runCommand();
}
