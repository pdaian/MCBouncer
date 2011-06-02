package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class MCBPlayerListener extends PlayerListener {

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        Runnable r = new Runnable() {
            String playerName, IP;
            public void run() {
                this.playerName = player.getName();
                this.IP = player.getAddress().getAddress().getHostAddress();
                if (MCBouncerUtil.isAllowedToJoin(playerName, IP)) {
                    player.kickPlayer(MCBouncerUtil.getBanReason(playerName));
                    return;
                }
                int numBans = MCBouncerUtil.getBanCount(playerName, IP);
                int numNotes = MCBouncerUtil.getNoteCount(playerName);
                String response = numBans > 0 ? numBans + " bans. " : " ";
                response += numNotes > 0 ? numNotes + " notes." : "";
                if (!response.isEmpty()) {
                    MCBouncerUtil.modMessage(ChatColor.GREEN + response);
                }
            }
        };
        r.run();
    }c
}
