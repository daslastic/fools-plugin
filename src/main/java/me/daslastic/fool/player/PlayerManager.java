package me.daslastic.fool.player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import me.daslastic.fool.Fool;
import me.daslastic.fool.player.events.PlayerChat;
import me.daslastic.fool.player.ranks.RankManager;

public class PlayerManager {
    
    private Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    private final PlayerTasks playerTasks;
    private final RankManager rankManager;

    private final Scoreboard scoreboard;

    public PlayerManager(Fool plugin) {
        this.scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
        playerTasks = new PlayerTasks(plugin, this);
        rankManager = new RankManager(plugin, this);
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            new PlayerData(player, this);
        });

        // add event listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerChat(this), plugin);
    }

    public void onShutdown() {
        playerDataMap.values().forEach(playerData -> {
            playerData.quit();
        });
        rankManager.save();
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

    public boolean isPlayerData(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public Scoreboard getScoreboardManager() {
        return scoreboard;
    }

    public PlayerTasks getTasks() {
        return playerTasks;
    }

    public RankManager getRanks() {
        return rankManager;
    }

}
