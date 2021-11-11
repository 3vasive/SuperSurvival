package me.evasive.supersurvival.experienceManagers;

import me.evasive.supersurvival.config.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.lang.Math;

public class MiningExperienceManager {

    private final Map<UUID, PlayerExperience> miningMap;


    public MiningExperienceManager() {
        this.miningMap = new HashMap<>();
    }

    public void onJoin(UUID uuid){
        if (!miningMap.containsKey(uuid)){
            PlayerExperience playerExperience = new PlayerExperience();
            playerExperience.miningExperience = 0;
            playerExperience.farmingExperience = 0;
            playerExperience.loggingExperience = 0;
            playerExperience.huntingExperience = 0;
            miningMap.put(uuid, playerExperience);
        }
    }

    public void addExperience(UUID uuid, int amount) {
        if (miningMap.containsKey(uuid)) {
            PlayerExperience playerExperience;
            playerExperience = miningMap.get(uuid);
            playerExperience.miningExperience += amount;
            miningMap.put(uuid, playerExperience);
        }
    }

    public void removeExperience(UUID uuid, int amount){

    }

    public int getExperience(UUID uuid){
        PlayerExperience playerExperience = miningMap.get(uuid);
        return playerExperience.miningExperience;
    }

    public int getExperienceNeeded(UUID uuid){
        int exp = (int) (100 * Math.pow(getLevel(uuid) + 1, 1.85));
        int prevxp = (int) (100 * Math.pow(getLevel(uuid), 1.85));
        return exp - prevxp;
    }

    public int getLevelExperience(UUID uuid){
        return (int) (100 * Math.pow(getLevel(uuid), 1.85));
    }

    public boolean checkLevelUp(UUID uuid, int gain){
        PlayerExperience playerExperience = miningMap.get(uuid);
        int exp = (int) (100 * Math.pow(getLevel(uuid) + 1, 1.85));
        if(playerExperience.miningExperience + gain >= exp){
            return true;
        }else{
            return false;
        }
    }

    public int getLevel(UUID uuid){
        PlayerExperience playerExperience = miningMap.get(uuid);
        return (int) Math.pow(playerExperience.miningExperience / 100, 1/1.85);
    }

    public void setExperience(UUID uuid, int amount){

    }

    public void saveWorldData(){
        PlayerConfig.setup();
        for (Map.Entry<UUID, PlayerExperience> players : miningMap.entrySet()){
            PlayerConfig.getConfig().set("players." + players.getKey() + ".mining", players.getValue().miningExperience);
        }
        PlayerConfig.saveConfig();
    }

    public void loadWorldData(){
        FileConfiguration load = PlayerConfig.getConfig();
        if (load != null){
            if (load.getConfigurationSection("players") != null){
                load.getConfigurationSection("players").getKeys(false).forEach(key -> {
                    if (miningMap.containsKey(UUID.fromString(key))){
                        PlayerExperience playerExperience = miningMap.get(UUID.fromString(key));
                        playerExperience.miningExperience = PlayerConfig.getConfig().getInt("players." + key + ".mining");
                        miningMap.put(UUID.fromString(key), playerExperience);
                    }else{
                        PlayerExperience playerExperience = new PlayerExperience();
                        playerExperience.miningExperience = PlayerConfig.getConfig().getInt("players." + key + ".mining");
                        miningMap.put(UUID.fromString(key), playerExperience);
                    }
                });
                PlayerConfig.saveConfig();
            }
        }
    }
}
