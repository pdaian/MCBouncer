package com.mcbouncer.event.listener;

import com.mcbouncer.event.BlockBreakEvent;
import java.util.HashMap;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal block break event.
 * 
 * See BlockBreakEvent for description of event.
 * 
 */
public class BlockBreakListener implements MCBListener<BlockBreakEvent> {

    public void onEvent(BlockBreakEvent event) {
        HashMap<String, Long> loggedIn = event.getController().getCurrentlyLoggingIn();
        
        if (loggedIn.keySet().contains(event.getUser())) {
            event.setCancelled(true);
        }
    }
    
}
