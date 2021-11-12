package me.evasive.supersurvival.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PlayerConfig {

    private static FileConfiguration playerConfig;
    private static File file;

    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SuperSurvival")).getDataFolder(), "Players.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }

        }
        playerConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return playerConfig;
    }

    public static void saveConfig() {
        try {
            playerConfig.save(file);
        } catch (IOException ignored) {

        }

    }
}
