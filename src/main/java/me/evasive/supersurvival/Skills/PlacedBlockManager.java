/*package me.evasive.supersurvival.Skills;

import me.evasive.supersurvival.BlockData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacedBlockManager {
    private final Map<String, List<String>> placeBlockMap;

    public PlacedBlockManager() {
        this.placeBlockMap = new HashMap<>();
    }


    public void addBlock(String chunk, String blockData){
        if (!placeBlockMap.containsKey(chunk)){
            List<String> list = new ArrayList<>();
            list.add(blockData);
            placeBlockMap.put(chunk, list);
        }else{
            List<String> list = placeBlockMap.get(chunk);
            if (!list.contains(blockData)){
                list.add(blockData);
                placeBlockMap.put(chunk, list);
            }
        }
    }

    public List<String> checkBlocks(String chunk){
        if (placeBlockMap.containsKey(chunk)) {
            return placeBlockMap.get(chunk);
        }
        return null;
    }


    public void removeBlock(String chunk, String blockData){
        if (placeBlockMap.containsKey(chunk)){
            List<String> list = placeBlockMap.get(chunk);
            if (list.contains(blockData)){
                list.remove(blockData);
                placeBlockMap.put(chunk, list);
            }
        }
    }

    public void saveWorldData(){
        BlockData.setup();
        for (Map.Entry<String, List<String>> blocks : placeBlockMap.entrySet()){
            BlockData.getConfig().set("Chunk." + blocks.getKey(), blocks.getValue());
        }
        BlockData.saveConfig();
    }

    public void loadWorldData(){
        FileConfiguration load = BlockData.getConfig();
        if (load != null){
            if (load.getConfigurationSection("Chunk") != null){
                load.getConfigurationSection("Chunk").getKeys(false).forEach(key -> {
                    placeBlockMap.entrySet(key, load.getConfigurationSection().getStringList()
                });
                BlockData.saveConfig();
            }
        }
    }
}
*/