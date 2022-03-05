package me.daslastic.whyyouhere.player;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.daslastic.whyyouhere.SMP;
import me.daslastic.whyyouhere.util.UConfig;

public class PlayerData {
    
    private UConfig config;
    private UUID uuid;

    public PlayerData(Player player) {
        this.uuid = player.getUniqueId();
        config = new UConfig(SMP.getInstance().getName(), "PlayerData/" + uuid.toString());
        PlayerManager.getManager().getPlayerDataMap().put(uuid, new PlayerData(player));
        PlayerManager.getTasks().getJoinTasks().forEach( task -> {
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
        PlayerManager.getManager().getPlayerDataMap().remove(uuid);
        PlayerManager.getTasks().getQuitTasks().forEach( task -> {
            task.run(this);
        });
    }

}
