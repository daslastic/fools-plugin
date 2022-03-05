package me.daslastic.whyyouhere;

import org.bukkit.plugin.java.JavaPlugin;

import me.daslastic.whyyouhere.commands.CommandManager;
import me.daslastic.whyyouhere.player.PlayerManager;

public class SMP extends JavaPlugin {

    private static SMP instance;
    private static PlayerManager pManager;

    @Override
    public void onEnable() {
        instance = this;
        pManager = new PlayerManager(this);
        new CommandManager(this);
    }

    @Override
    public void onDisable() {
        pManager.onShutdown();
    }

    public static PlayerManager playerManager() {
        return pManager;
    }

    public static SMP getInstance() {
        return instance;
    }

}