package me.daslastic.whyyouhere;

import org.bukkit.plugin.java.JavaPlugin;

import me.daslastic.whyyouhere.player.PlayerManager;

public class SMP extends JavaPlugin {

    private static SMP instance;
    private PlayerManager pManager;

    @Override
    public void onEnable() {
        instance = this;
        this.pManager = new PlayerManager(this);
    }

    @Override
    public void onDisable() {
        this.pManager.onShutdown();
    }

    public static SMP getInstance() {
        return instance;
    }

}