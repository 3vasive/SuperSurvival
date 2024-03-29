package me.evasive.supersurvival;

import me.evasive.supersurvival.abilities.MiningEvents;
import me.evasive.supersurvival.commands.AdminCommands;
import me.evasive.supersurvival.commands.Commands;
import me.evasive.supersurvival.abilities.Abilities;
import me.evasive.supersurvival.abilities.JoinEvent;
import me.evasive.supersurvival.config.PlayerConfig;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        PlayerConfig.setup();
        getServer().getPluginManager().registerEvents(new MiningEvents(this), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new Abilities(this), this);
        MiningEvents.experienceManager.loadWorldData();
        new AdminCommands(this);
        new Commands(this);
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "SuperSurvival Started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MiningEvents.experienceManager.saveWorldData();
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "SuperSurvival Shutdown");
    }
}
