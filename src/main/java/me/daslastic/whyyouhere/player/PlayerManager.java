package me.daslastic.whyyouhere.player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.daslastic.whyyouhere.SMP;
import me.daslastic.whyyouhere.player.ranks.RankManager;

public class PlayerManager implements Listener {
    
    private Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    private static PlayerManager playerManager;
    private static PlayerTasks playerTasks;
    private static RankManager rankManager;

    public PlayerManager(SMP plugin) {
        playerManager = this;
        playerTasks = new PlayerTasks(plugin);
        rankManager = new RankManager(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public PlayerData getPlayerDataByUUID(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return this.playerDataMap;
    }

    public boolean isDataSet(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public static PlayerManager getManager() {
        return playerManager;
    }

    public static PlayerTasks getTasks() {
        return playerTasks;
    }

    public static RankManager getRanks() {
        return rankManager;
    }

}
