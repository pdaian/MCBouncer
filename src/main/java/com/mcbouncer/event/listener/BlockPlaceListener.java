package com.mcbouncer.event.listener;

import com.mcbouncer.event.BlockPlaceEvent;
import java.util.HashMap;
import net.lahwran.fevents.MCBListener;

/**
 * Listener for internal block break event.
 * 
 * See BlockBreakEvent for description of event.
 * 
 */
public class BlockPlaceListener implements MCBListener<BlockPlaceEvent> {

    public void onEvent(BlockPlaceEvent event) {
        HashMap<String, Long> loggedIn = event.getController().getCurrentlyLoggingIn();
        
        if (loggedIn.keySet().contains(event.getUser())) {
            event.setCancelled(true);
        }
    }
    
}
