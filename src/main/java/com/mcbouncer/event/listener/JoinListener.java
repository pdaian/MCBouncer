package com.mcbouncer.event.listener;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.JoinEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.util.ChatColor;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal join event.
 * 
 * See JoinEvent for description of event.
 * 
 */
public class JoinListener implements MCBListener<JoinEvent> {

    public void onEvent(JoinEvent event) {
        String username = event.getUser();
        String ip = event.getIP();
        MCBouncer controller = event.getController();

        try {
            controller.getAPI().updateUser(username, ip);

            if (controller.getAPI().isBanned(username)) {
                controller.setLastKickedUser(username);
                controller.getCurrentlyLoggingIn().remove(username);
                controller.getServer().kickPlayer(username, "Banned: " + controller.getAPI().getBanReason(username));
                controller.getLogger().info(username + " attempted to join with IP " + ip);
                return;
            }
            if (controller.getAPI().isIPBanned(ip)) {
                controller.setLastKickedUser(username);
                controller.getCurrentlyLoggingIn().remove(username);
                controller.getServer().kickPlayer(username, "Banned: " + controller.getAPI().getIPBanReason(ip));
                controller.getLogger().info(username + " attempted to join with IP " + ip);
                return;
            }
            int numBans = controller.getAPI().getTotalBanCount(username, ip);
            int numNotes = controller.getAPI().getNoteCount(username);
            if (numBans > 0 || numNotes > 0) {
                String response = username + " has ";
                if (numNotes == 0) {
                    response += numBans + " ban" + (numBans == 1 ? "." : "s.");
                } else if (numBans == 0) {
                    response += numNotes + " note" + (numNotes == 1 ? "." : "s.");
                } else {
                    response += numBans + " ban" + (numBans == 1 ? "" : "s") + " and " + numNotes + " note" + (numNotes == 1 ? "." : "s.");
                }
                controller.getServer().messageMods(ChatColor.GREEN + response);
            }
        } catch (NetworkException ne) {
            controller.getLogger().severe("Uh oh! Network error occurred!", ne);
        } catch (APIException ae) {
            controller.getLogger().severe("Uh oh! API error occurred!", ae);
        }

        controller.getCurrentlyLoggingIn().remove(username);
        controller.getServer().broadcastMessage(event.getMessage());

    }
}
