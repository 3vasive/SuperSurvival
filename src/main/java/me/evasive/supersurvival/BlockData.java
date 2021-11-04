package me.evasive.supersurvival;

/*import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BlockData {
    private SuperSurvival plugin;
    private static FileConfiguration playerConfig;
    private static File file;

    public BlockData(SuperSurvival plugin){
        this.plugin = plugin;
    }

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SuperSurvival").getDataFolder(), "BlockData.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

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
        } catch (IOException e) {

        }

    }

    public static void reload() {
        playerConfig = YamlConfiguration.loadConfiguration(file);
    }
}
*/