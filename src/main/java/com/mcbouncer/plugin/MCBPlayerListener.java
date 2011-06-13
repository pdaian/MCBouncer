package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;

public class MCBPlayerListener extends PlayerListener {
    
    private MCBouncer plugin;

    public MCBPlayerListener(MCBouncer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Thread r = new PlayerJoinThread(event.getPlayer());
        r.start();
    }
    
    public class PlayerJoinThread extends Thread {        
        Player player;
        public PlayerJoinThread(Player player) {
            this.player = player;
        }
        
        @Override
        public void run() {
            String playerName = player.getName();
            String IP = player.getAddress().getAddress().getHostAddress();
            if (MCBouncerUtil.isNotAllowedToJoin(playerName, IP)) {
                player.kickPlayer("Banned: " + MCBouncerUtil.getBanReason(playerName));
                return;
            }
            int numBans = MCBouncerUtil.getBanCount(playerName, IP);
            int numNotes = MCBouncerUtil.getNoteCount(playerName);
            String response = numBans > 0 || numNotes > 0 ? playerName+" has " : "";
            response += numBans > 0 ? numBans + " ban(s). " : "";
            response += numNotes > 0 ? numNotes + " note(s)." : "";
            if (!response.isEmpty()) {
                MCBouncerUtil.modMessage(ChatColor.GREEN + response, plugin);
            }
        }
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        if( plugin.muted.contains(event.getPlayer())) {
            event.setCancelled(true);
            plugin.log.info("Muted player chat: " + event.getPlayer().getName() + " : " + event.getMessage());
        }
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if( plugin.muted.contains(event.getPlayer()) && event.getMessage().substring(0, 3).equals("/me")) {
            event.setCancelled(true);
            plugin.log.info("Muted player chat: " + event.getPlayer().getName() + " : " + event.getMessage());
        }
    }
    
    
}
