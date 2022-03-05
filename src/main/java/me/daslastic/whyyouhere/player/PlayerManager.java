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

    private final PlayerTasks playerTasks;
    private final RankManager rankManager;

    public PlayerManager(SMP plugin) {
        playerTasks = new PlayerTasks(plugin, this);
        rankManager = new RankManager(plugin, this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addDefaultValue(String key, Object value) {
        playerTasks.addJoinTask(playerData -> {
            if(!playerData.config().isSet(key)) {
                playerData.config().set(key, value);
            }
        });
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

    public PlayerTasks getTasks() {
        return playerTasks;
    }

    public RankManager getRanks() {
        return rankManager;
    }

}
