/*package me.evasive.supersurvival.Skills;

import me.evasive.supersurvival.Skills.Mining.Events.MiningEvents;
import me.evasive.supersurvival.SuperSurvival;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.List;

public class ChunkLoad implements Listener {

    public SuperSurvival plugin;

    public ChunkLoad(SuperSurvival plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void ChunkLoad(ChunkLoadEvent e) {
        String chunk = e.getChunk().toString();
        String shortChunk = chunk.substring(11, chunk.length() - 1);
        Bukkit.getConsoleSender().sendMessage(shortChunk);
        List list = MiningEvents.placedBlockManager.checkBlocks(shortChunk);
        /*
        if (list != null){
            for (int i = 0; i < list.size(); i++){
                Bukkit.getConsoleSender().sendMessage("World: " + e.getWorld());
                String str = (String) list.get(i);
                String[] strings = str.split(",", 6);
                String x = strings[1].substring(2);
                String y = strings[2].substring(2);
                String z = strings[3].substring(2);
                Bukkit.getConsoleSender().sendMessage("x: " + x);
                Bukkit.getConsoleSender().sendMessage("y: " + y);
                Bukkit.getConsoleSender().sendMessage("z: " + z);
            }
        }

    }
}
*/