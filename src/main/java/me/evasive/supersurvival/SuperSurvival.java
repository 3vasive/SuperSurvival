package me.evasive.supersurvival;

//import me.evasive.supersurvival.Skills.ChunkLoad;
import me.evasive.supersurvival.Skills.Commands.AdminCommands;
import me.evasive.supersurvival.Skills.Mining.Events.MiningEvents;
//import me.evasive.supersurvival.Skills.PlacedBlockManager;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperSurvival extends JavaPlugin {

    public PlayerConfig pManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        PlayerConfig.setup();
        getServer().getPluginManager().registerEvents(new MiningEvents(this), this);
        //getServer().getPluginManager().registerEvents(new ChunkLoad(this), this);
        MiningEvents.experienceManager.loadWorldData();
        //MiningEvents.placedBlockManager.loadWorldData();
        new AdminCommands(this);
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "SuperSurvival Started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MiningEvents.experienceManager.saveWorldData();
        //MiningEvents.placedBlockManager.saveWorldData();
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "SuperSurvival Shutdown");
    }
}
