package me.daslastic.whyyouhere.player;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.daslastic.whyyouhere.SMP;
import me.daslastic.whyyouhere.util.UConfig;

public class PlayerData {
    
    private UConfig config;
    private UUID uuid;
    private final PlayerManager pManager;

    public PlayerData(Player player, PlayerManager pManager) {
        this.uuid = player.getUniqueId();
        this.pManager = pManager;
        config = new UConfig(SMP.getInstance().getName(), "PlayerData/" + uuid.toString());
        pManager.getPlayerDataMap().put(uuid, this);
        pManager.getTasks().getJoinTasks().forEach( task -> {
            task.run(this);
        });
    }

    public FileConfiguration config() {
        return config.get();
    }

    public void save() {
        config.save();
    }

    public void quit() {
        save();
        pManager.getPlayerDataMap().remove(uuid);
        pManager.getTasks().getQuitTasks().forEach( task -> {
            task.run(this);
        });
    }

}
