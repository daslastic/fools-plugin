package me.daslastic.whyyouhere;

import org.bukkit.plugin.java.JavaPlugin;

import me.daslastic.whyyouhere.player.PlayerManager;

public class SMP extends JavaPlugin {

    private static SMP instance;

    @Override
    public void onEnable() {

        new PlayerManager(this);

    }

    @Override
    public void onDisable() {

        

    }

    public static SMP getInstance() {
        return instance;
    }

}