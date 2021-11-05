package me.evasive.supersurvival;

import me.evasive.supersurvival.Skills.Mining.Events.MiningEvents;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    public SuperSurvival plugin;
    public JoinEvent(SuperSurvival plugin){
        this.plugin = plugin;
    }

@EventHandler
    public void playerJoin(PlayerJoinEvent e){
    Player player = e.getPlayer();
    MiningEvents.miningExperienceManager.onJoin(player.getUniqueId());
}
}
