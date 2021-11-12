package me.evasive.supersurvival.abilities;

import me.evasive.supersurvival.SuperSurvival;
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
    MiningEvents.experienceManager.onJoin(player.getUniqueId());
    if (!Abilities.map.containsKey(player.getUniqueId())){
        PlayerCooldowns playerCooldowns = new PlayerCooldowns();
        playerCooldowns.player = player;
        playerCooldowns.abilityNum = 0;
        playerCooldowns.miningID1 = 0;
        playerCooldowns.miningID2 = 0;
        playerCooldowns.miningCooldownID1 = 0;
        playerCooldowns.miningCooldownID2 = 0;
        playerCooldowns.time = 0;
        playerCooldowns.hasteCooldown = 0;
        playerCooldowns.explosiveCooldown = 0;
        playerCooldowns.blockFace = null;
        playerCooldowns.active = false;
        playerCooldowns.explosive = false;
        Abilities.map.put(e.getPlayer().getUniqueId(), playerCooldowns);
    }
}
}
