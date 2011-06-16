package com.mcbouncer.bukkit;

import com.mcbouncer.util.MCBouncerUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPreLoginEvent;

public class MCBPlayerListener extends PlayerListener {

    private MCBouncer parent;

    public MCBPlayerListener(MCBouncer parent) {
        this.parent = parent;
    }

    @Override
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {

        String playerName = event.getName();
        String IP = event.getAddress().getHostAddress();

        MCBouncerUtil.updateUser(playerName, IP);
        if (MCBouncerUtil.isBanned(playerName)) {
            event.disallow(PlayerPreLoginEvent.Result.KICK_BANNED, "Banned: " + MCBouncerUtil.getBanReason(playerName));
            return;
        }
        if (MCBouncerUtil.isIPBanned(IP)) {
            event.disallow(PlayerPreLoginEvent.Result.KICK_BANNED, "Banned: " + MCBouncerUtil.getIPBanReason(IP));
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