package com.mcbouncer.plugin;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class MCBPlayerListener extends PlayerListener {

    private MCBouncer parent;

    public MCBPlayerListener(MCBouncer parent) {
        this.parent = parent;
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

            if (numBans > 0 || numNotes > 0) {
                String response = playerName + " has ";

                if (numNotes == 0) {
                    response += numBans + " ban" + MCBouncerUtil.plural(numBans, ".", "s.");
                } else if (numBans == 0) {
                    response += numNotes + " note" + MCBouncerUtil.plural(numNotes, ".", "s.");
                } else {
                    response += numBans + " ban" + MCBouncerUtil.plural(numBans, "", "s") + " and " + numNotes + " note" + MCBouncerUtil.plural(numNotes, ".", "s.");
                }

                parent.messageMods(ChatColor.GREEN + response);
            }
        }
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        if (parent.muted.contains(event.getPlayer())) {
            event.setCancelled(true);
            MCBouncer.log.info("Muted player chat: " + event.getPlayer().getName() + " : " + event.getMessage());
        }
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (parent.muted.contains(event.getPlayer()) && event.getMessage().substring(0, 3).equals("/me")) {
            event.setCancelled(true);
            MCBouncer.log.info("Muted player chat: " + event.getPlayer().getName() + " : " + event.getMessage());
        }
    }
}
