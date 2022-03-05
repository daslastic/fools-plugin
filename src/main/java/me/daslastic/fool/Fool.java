package me.daslastic.fool;

import org.bukkit.plugin.java.JavaPlugin;

import me.daslastic.fool.commands.CommandManager;
import me.daslastic.fool.player.PlayerManager;

public class Fool extends JavaPlugin {

    private static Fool instance;
    private PlayerManager pManager;

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

    public PlayerManager getPlayerManager() {
        return pManager;
    }

    public static Fool getInstance() {
        return instance;
    }

}