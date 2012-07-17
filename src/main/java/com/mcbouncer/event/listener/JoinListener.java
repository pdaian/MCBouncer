package com.mcbouncer.event.listener;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.JoinEvent;
import com.mcbouncer.event.PlayerUpdateEvent;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.util.ChatColor;
import net.lahwran.fevents.MCBEventHandler;
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
            PlayerUpdateEvent updateEvent = new PlayerUpdateEvent(username, ip);
            MCBEventHandler.callEvent(updateEvent);

            if (updateEvent.isCancelled()) {
                return;
            }

            username = updateEvent.getUsername();
            ip = updateEvent.getIP();

            if (!controller.getConfiguration().isIPFunctionsDisabled()) {
                controller.getAPI().updateUser(username, ip);
            }

            if (controller.getAPI().isBanned(username)) {
                controller.getServer().kickPlayer(username, "Banned: " + controller.getAPI().getBanReason(username));
                controller.getLogger().info(username + " attempted to join with IP " + ip);
                return;
            }

            if (!controller.getConfiguration().isIPFunctionsDisabled() && controller.getAPI().isIPBanned(ip)) {
                controller.getServer().kickPlayer(username, "Banned: " + controller.getAPI().getIPBanReason(ip));
                controller.getLogger().info(username + " attempted to join with IP " + ip);
                return;
            }

            int numBans = controller.getAPI().getTotalBanCount(username, ip);
            int numNotes = controller.getAPI().getNoteCount(username);

            if (controller.getConfiguration().getNumBansDisallow() > 0 && numBans > controller.getConfiguration().getNumBansDisallow()) {
                controller.getServer().kickPlayer(username, "You are banned on too many servers to log in here.");
                controller.getLogger().info(username + " is banned on " + numBans + " servers, and has been disallowed");
                return;
            }

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
            controller.getServer().kickPlayer(username, "MCBouncer timeout.");
        } catch (APIException ae) {
            controller.getLogger().severe("Uh oh! API error occurred!", ae);
            controller.getServer().kickPlayer(username, "API Error");
        }

    }
}
