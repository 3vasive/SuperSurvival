package me.evasive.supersurvival.experienceManagers;

import me.evasive.supersurvival.config.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import java.lang.Math;

public class ExperienceManager {

    private final Map<UUID, PlayerExperience> experienceMap;


    public ExperienceManager() {
        this.experienceMap = new HashMap<>();
    }

    public void onJoin(UUID uuid) {
        if (!experienceMap.containsKey(uuid)) {
            PlayerExperience playerExperience = new PlayerExperience();
            playerExperience.miningExperience = 0;
            playerExperience.farmingExperience = 0;
            playerExperience.loggingExperience = 0;
            playerExperience.huntingExperience = 0;
            playerExperience.fishingExperience = 0;
            experienceMap.put(uuid, playerExperience);
        }
    }

    /*Skill int checks what ability xp is being changed in
     * Skill 1 = Mining
     * Skill 2 = Logging
     * Skill 3 = Hunting
     * Skill 4 = Farming
     * Skill 5 = Fishing */

    public void addExperience(UUID uuid, int amount, int skill) {
        if (experienceMap.containsKey(uuid)) {
            PlayerExperience playerExperience;
            playerExperience = experienceMap.get(uuid);
            if (skill == 1) {
                playerExperience.miningExperience += amount;
            } else if (skill == 2) {
                playerExperience.loggingExperience += amount;
            }
            experienceMap.put(uuid, playerExperience);
        }
    }

    public void removeExperience(UUID uuid, int amount) {
        //Ignore Errors for now
    }

    public int getExperience(UUID uuid, int skill) {
        PlayerExperience playerExperience = experienceMap.get(uuid);
        int experience = 0;
        if (skill == 1) {
            experience = playerExperience.miningExperience;
        } else if (skill == 2) {
            experience = playerExperience.loggingExperience;
        }
        return experience;
    }

    public int getExperienceNeeded(UUID uuid, int skill) {
        int exp = 0;
        int prevxp = 0;
        if (skill == 1) {
            exp = (int) (100 * Math.pow(getLevel(uuid, 1) + 1, 1.85));
            prevxp = (int) (100 * Math.pow(getLevel(uuid, 1), 1.85));
        } else if (skill == 2) {
            exp = (int) (100 * Math.pow(getLevel(uuid, 2) + 1, 1.85));
            prevxp = (int) (100 * Math.pow(getLevel(uuid, 2), 1.85));
        }
        return exp - prevxp;
    }

    public int getLevelExperience(UUID uuid, int skill) {
        if (skill == 1) {
            return (int) (100 * Math.pow(getLevel(uuid, 1), 1.85));
        } else if (skill == 2) {
            return (int) (100 * Math.pow(getLevel(uuid, 2), 1.85));
        }
        return 0;
    }

    public boolean checkLevelUp(UUID uuid, int gain, int skill) {
        PlayerExperience playerExperience = experienceMap.get(uuid);
        int exp = 0;
        int playerexp = 0;
        if (skill == 1) {
            exp = (int) (100 * Math.pow(getLevel(uuid, 1) + 1, 1.85));
            playerexp = playerExperience.miningExperience;
        } else if (skill == 2) {
            exp = (int) (100 * Math.pow(getLevel(uuid, 2) + 1, 1.85));
            playerexp = playerExperience.loggingExperience;
        }
        return playerexp + gain >= exp;
    }

    public int getLevel(UUID uuid, int skill) {
        PlayerExperience playerExperience = experienceMap.get(uuid);
        double level = 0;
        if (skill == 1) {
            double exp = playerExperience.miningExperience;
            level = Math.pow(exp / 100, 1 / 1.85);
        } else if (skill == 2) {
            double exp = playerExperience.loggingExperience;
            level = Math.pow(exp / 100, 1 / 1.85);
        }
        return (int) level;
    }

    public void setExperience(UUID uuid, int amount) {
        //Ignore errors for now
    }

    public void saveWorldData() {
        PlayerConfig.setup();
        for (Map.Entry<UUID, PlayerExperience> players : experienceMap.entrySet()) {
            PlayerConfig.getConfig().set("players." + players.getKey() + ".mining", players.getValue().miningExperience);
            PlayerConfig.getConfig().set("players." + players.getKey() + ".logging", players.getValue().loggingExperience);
        }
        PlayerConfig.saveConfig();
    }

    public void loadWorldData() {
        FileConfiguration load = PlayerConfig.getConfig();
        if (load != null) {
            if (load.getConfigurationSection("players") != null) {
                Objects.requireNonNull(load.getConfigurationSection("players")).getKeys(false).forEach(key -> {
                    PlayerExperience playerExperience = new PlayerExperience();
                    playerExperience.miningExperience = PlayerConfig.getConfig().getInt("players." + key + ".mining");
                    playerExperience.loggingExperience = PlayerConfig.getConfig().getInt("players." + key + ".logging");
                    experienceMap.put(UUID.fromString(key), playerExperience);
                });
                PlayerConfig.saveConfig();
            }
        }
    }
}
