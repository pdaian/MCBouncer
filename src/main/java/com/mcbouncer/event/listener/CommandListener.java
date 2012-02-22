package com.mcbouncer.event.listener;

import com.mcbouncer.event.CommandEvent;
import java.util.HashMap;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal command event.
 * 
 * See CommandEvent for description of event.
 * 
 */
public class CommandListener implements MCBListener<CommandEvent> {

    public void onEvent(CommandEvent event) {
        HashMap<String, Long> loggedIn = event.getController().getCurrentlyLoggingIn();

        if (loggedIn.keySet().contains(event.getUser())) {
            event.setCancelled(true);
        }
    }
}
