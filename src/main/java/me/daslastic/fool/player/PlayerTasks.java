package me.daslastic.fool.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.daslastic.fool.Fool;

public class PlayerTasks implements Listener {

    private final PlayerManager pManager;
    private final List<PlayerRunnable> joinTasks = new ArrayList<>(); 
    private final List<PlayerRunnable> quitTasks = new ArrayList<>(); 

    public PlayerTasks(Fool plugin, PlayerManager pManager) {
        this.pManager = pManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new PlayerData(event.getPlayer(), pManager);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        pManager.getPlayerData(event.getPlayer()).quit();
    }

    public void addJoinTask(PlayerRunnable task) {
        joinTasks.add(task);
    }

    public void addQuitTask(PlayerRunnable task) {
        quitTasks.add(task);
    }
    
    public List<PlayerRunnable> getJoinTasks() {
        return this.joinTasks;
    }

    public List<PlayerRunnable> getQuitTasks() {
        return this.quitTasks;
    }
}
