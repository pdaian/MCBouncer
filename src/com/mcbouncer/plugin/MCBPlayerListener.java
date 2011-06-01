package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MCBPlayerListener extends PlayerListener {
        
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Runnable r = new Runnable() {
            String playerName, IP;
            Player player;
            public void Runnable(Player player) {
                this.player = player;
            }

            public void run() {
                this.playerName = player.getName();
                this.IP = player.getAddress().toString();
                if (MCBouncerUtil.isAllowedToJoin(playerName, IP)) {
                    player.kickPlayer(MCBouncerUtil.getBanReason(playerName));
                    return;
                }
                int numBans = MCBouncerUtil.getBanCount(playerName, IP);
                int numNotes = MCBouncerUtil.getNoteCount(playerName);
                String response = (numBans > 0) ? numBans + " bans." : "";
                response += numNotes > 0 ? " " + numNotes + " notes." : "";
                if (!response.isEmpty()) {
                    MCBouncerUtil.modMessage(ChatColor.GREEN + response);
                }
            }
        };
        r.run();
    }
    
}
