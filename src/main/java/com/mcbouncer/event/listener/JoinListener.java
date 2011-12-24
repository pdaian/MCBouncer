package com.mcbouncer.event.listener;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.JoinEvent;
import com.mcbouncer.util.ChatColor;
import com.mcbouncer.util.MCBouncerUtil;
import net.lahwran.fevents.MCBListener;

public class JoinListener implements MCBListener<JoinEvent> {

    public void onEvent(JoinEvent event) {
        String username = event.getUser();
        String ip = event.getIP();
        MCBouncer controller = event.getController();
        
        MCBouncerUtil.updateUser(username, ip);

        if (MCBouncerUtil.isBanned(username)) {
            controller.setLastKickedUser(username);
            controller.getPlugin().kickPlayer(username, "Banned: " + MCBouncerUtil.getBanReason(username));
            controller.getLogger().info(username + " attempted to join with IP " + ip);
            return;
        }
        if (MCBouncerUtil.isIPBanned(ip)) {
            controller.setLastKickedUser(username);
            controller.getPlugin().kickPlayer(username, "Banned: " + MCBouncerUtil.getIPBanReason(ip));
            controller.getLogger().info(username + " attempted to join with IP " + ip);
            return;
        }
        int numBans = MCBouncerUtil.getBanCount(username, ip);
        int numNotes = MCBouncerUtil.getNoteCount(username);
        if (numBans > 0 || numNotes > 0) {
            String response = username + " has ";
            if (numNotes == 0) {
                response += numBans + " ban" + (numBans == 1 ? "." : "s.");
            } else if (numBans == 0) {
                response += numNotes + " note" + (numNotes == 1 ? "." : "s.");
            } else {
                response += numBans + " ban" + (numBans == 1 ? "" : "s") + " and " + numNotes + " note" + (numNotes == 1 ? "." : "s.");
            }
            controller.getPlugin().messageMods(ChatColor.GREEN + response);
        }
        
        controller.getCurrentlyLoggingIn().remove(username);
        controller.getPlugin().broadcastMessage(event.getMessage());

    }
}
