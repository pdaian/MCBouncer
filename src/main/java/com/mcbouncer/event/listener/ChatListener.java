package com.mcbouncer.event.listener;

import com.mcbouncer.event.ChatEvent;
import java.util.HashMap;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal chat event.
 * 
 * See ChatEvent for description of event.
 * 
 */
public class ChatListener implements MCBListener<ChatEvent> {

    public void onEvent(ChatEvent event) {
        HashMap<String, Long> loggedIn = event.getController().getCurrentlyLoggingIn();
        
        if (loggedIn.keySet().contains(event.getUser())) {
            event.setCancelled(true);
        }
    }
}
