package com.mcbouncer.event.listener;

import com.mcbouncer.event.LoginEvent;
import java.util.HashMap;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal login event.
 * 
 * See LoginEvent for description of event.
 * 
 */
public class LoginListener implements MCBListener<LoginEvent> {

    public void onEvent(LoginEvent event) {
        HashMap<String, Long> loggedIn = event.getController().getCurrentlyLoggingIn();

        if (loggedIn.keySet().contains(event.getUser())) {
            if (System.currentTimeMillis() - loggedIn.get(event.getUser()) > 5000) {
                loggedIn.put(event.getUser(), System.currentTimeMillis());
            } else
            {
                event.setCancelled(true);
            }
        } else {
            loggedIn.put(event.getUser(), System.currentTimeMillis());
        }
    }
}
