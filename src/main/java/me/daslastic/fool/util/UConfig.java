package me.daslastic.fool.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UConfig {

    private File file;
    private FileConfiguration fileConfig;
    private Plugin plugin;

    public UConfig(String pluginName, String configName) {
        this.plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
        this.file = new File(plugin.getDataFolder(), configName + ".yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //waaa
            }
        }

        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() {
        return fileConfig;
    }

    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            plugin.getServer().broadcastMessage("Couldn't save " + file.getName());
        }
    }

    public void loadDefaults() {
        InputStream inputStream = plugin.getResource(file.getName());
        if(inputStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            this.fileConfig.setDefaults(defaultConfig);
        }
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }
    
}
