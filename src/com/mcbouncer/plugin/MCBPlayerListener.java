package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MCBPlayerListener extends PlayerListener {
        
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) { // Thread this
        if (MCBouncerUtil.isAllowedToJoin(event.getPlayer().getName(), event.getPlayer().getAddress().toString())) { // Not sure
            event.getPlayer().kickPlayer(MCBouncerUtil.getBanReason(event.getPlayer().getName()));
            return;
        }
        MCBouncerUtil.modMessage();
    }
    
}
