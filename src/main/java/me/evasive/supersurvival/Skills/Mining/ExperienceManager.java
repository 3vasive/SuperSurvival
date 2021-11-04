package me.evasive.supersurvival.Skills.Mining;

import me.evasive.supersurvival.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.lang.Math;

public class ExperienceManager {

    private final Map<UUID, Integer> miningMap;


    public ExperienceManager() {
        this.miningMap = new HashMap<>();
    }

    public void addExperience(UUID uuid, int amount){
        if (miningMap.containsKey(uuid)){
            miningMap.put(uuid, miningMap.get(uuid) + amount);
        }else{
            miningMap.put(uuid, amount);
        }
    }

    public void removeExperience(UUID uuid, int amount){
        if (miningMap.containsKey(uuid) && miningMap.get(uuid) >= amount){
            miningMap.put(uuid, miningMap.get(uuid) - amount);
        }else{
            miningMap.put(uuid, 0);
        }
    }

    public int getExperience(UUID uuid){
        return miningMap.get(uuid);
    }

    public long getLevel(UUID uuid){
        int level = (int) Math.sqrt(miningMap.get(uuid) / 25);
        return level;
    }

    public void setExperience(UUID uuid, int amount){
        miningMap.put(uuid, amount);
    }

    public void saveWorldData(){
        PlayerConfig.setup();
        for (Map.Entry<UUID, Integer> players : miningMap.entrySet()){
            PlayerConfig.getConfig().set("players." + players.getKey() + ".mining", players.getValue());
        }
        PlayerConfig.saveConfig();
    }

    public void loadWorldData(){
        FileConfiguration load = PlayerConfig.getConfig();
        if (load != null){
            if (load.getConfigurationSection("players") != null){
                load.getConfigurationSection("players").getKeys(false).forEach(key -> {
                    miningMap.put(UUID.fromString(key), PlayerConfig.getConfig().getInt("players." + key + ".mining"));
                });
                PlayerConfig.saveConfig();
            }
        }
    }
}
