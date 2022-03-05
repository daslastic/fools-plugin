package me.daslastic.whyyouhere.player.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import me.daslastic.whyyouhere.player.PlayerManager;

public class PlayerChat implements Listener {

    private final PlayerManager pManager;

    public PlayerChat(PlayerManager pManager) {
        this.pManager = pManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Team team = pManager.getPlayerData(event.getPlayer()).team();
        event.setFormat(String.format("%s%s%s", team.getPrefix(), "%1$s: %2$s", team.getSuffix()));
    }
    
}
