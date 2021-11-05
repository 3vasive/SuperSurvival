package me.evasive.supersurvival.Skills.Mining;

import me.evasive.supersurvival.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.lang.Math;

public class MiningExperienceManager {

    private final Map<UUID, Integer> miningMap;


    public MiningExperienceManager() {
        this.miningMap = new HashMap<>();
    }

    public void onJoin(UUID uuid){
        if (!miningMap.containsKey(uuid)){
            miningMap.put(uuid, 0);
        }
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

    public int getExperienceNeeded(UUID uuid){
        int xp = miningMap.get(uuid);
        int level = getLevel(uuid);
        int nextlevel = level + 1;
        int exp = 100 * (nextlevel * nextlevel);
        int prevxp = 100 * (level * level);
        return exp - prevxp;
    }

    public int getLevelExperience(UUID uuid){
        int level = getLevel(uuid);
        int xp = 100 * (level * level);
        return xp;
    }

    public boolean checkLevelUp(UUID uuid, int gain){
        int xp = miningMap.get(uuid);
        int level = getLevel(uuid);
        int nextlevel = level + 1;
        int exp = 100 * (nextlevel * nextlevel);
        if(xp + gain >= exp){
            return true;
        }else{
            return false;
        }
    }

    public int getLevel(UUID uuid){
        int level = (int) Math.sqrt(miningMap.get(uuid) / 100);
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
