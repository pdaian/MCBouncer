package com.mcbouncer.event.listener;

import com.mcbouncer.event.LoginEvent;
import java.util.List;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal login event.
 * 
 * See LoginEvent for description of event.
 * 
 */
public class LoginListener implements MCBListener<LoginEvent> {

    public void onEvent(LoginEvent event) {
        List<String> loggedIn = event.getController().getCurrentlyLoggingIn();
        
        if( loggedIn.contains(event.getUser()) ) {
            event.setCancelled(true);
        }
        else {
            loggedIn.add(event.getUser());
        }
    }
    
}
