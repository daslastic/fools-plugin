package me.daslastic.fool.player;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import me.daslastic.fool.Fool;
import me.daslastic.fool.util.UConfig;

public class PlayerData {
    
    private final UConfig config;
    private final UUID uuid;
    private final PlayerManager pManager;
    private final Team team;

    public PlayerData(Player player, PlayerManager pManager) {
        this.uuid = player.getUniqueId();
        this.pManager = pManager;

        // INCASE THE DATA WAS NOT REMOVED PROPERLY. (hopefully this is not needed)
        Team oldTeam = pManager.getScoreboardManager().getTeam(player.getName());
        if(oldTeam != null) {
            oldTeam.unregister();
            Fool.getInstance().getServer().getLogger().warning(player.getName() + " HAD REMNANTS");
        }

        this.team = pManager.getScoreboardManager().registerNewTeam(player.getName());
        team.addEntry(player.getName());
        config = new UConfig(Fool.getInstance().getName(), "PlayerData/" + uuid.toString());
        pManager.getPlayerDataMap().put(uuid, this);
        pManager.getTasks().getJoinTasks().forEach(task -> {
            task.run(this);
        });
    }

    public FileConfiguration config() {
        return config.get();
    }

    public void save() {
        config.save();
    }

    public Player getPlayer() {
        return Fool.getInstance().getServer().getPlayer(this.uuid);
    }

    public void quit() {
        save();
        pManager.getTasks().getQuitTasks().forEach(task -> {
            task.run(this);
        });
        team.unregister();
        pManager.getPlayerDataMap().remove(uuid);
    }

    public Team team() {
        return this.team;
    }

}
