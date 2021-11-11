package me.evasive.supersurvival.abilities;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerCooldowns {
    Player player;
    boolean active;
    boolean explosive;
    BlockFace blockFace;
    int miningID1;
    int miningID2;
    int miningCooldownID1;
    int miningCooldownID2;
    int time;
    int abilityNum;
    int hasteCooldown;
    int explosiveCooldown;
}
