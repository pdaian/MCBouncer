package com.mcbouncer.event.listener;

import com.mcbouncer.event.ChatEvent;
import java.util.List;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal chat event.
 * 
 * See ChatEvent for description of event.
 * 
 */
public class ChatListener implements MCBListener<ChatEvent> {

    public void onEvent(ChatEvent event) {
        List<String> loggedIn = event.getController().getCurrentlyLoggingIn();
        
        if( loggedIn.contains(event.getUser()) ) {
            event.setCancelled(true);
        }
    }
    
}
