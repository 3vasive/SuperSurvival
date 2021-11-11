package me.evasive.supersurvival.abilities;

import com.google.common.collect.Sets;
import me.evasive.supersurvival.MiningEvents;
import me.evasive.supersurvival.SuperSurvival;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class ExplosiveEvent implements Listener {
    public SuperSurvival plugin;
    public ExplosiveEvent(SuperSurvival plugin){
        this.plugin = plugin;
    }
    @EventHandler(ignoreCancelled = true)
    public void Explosive(PlayerInteractEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerCooldowns playerCooldowns = (PlayerCooldowns) Abilities.map.get(uuid);
        playerCooldowns.blockFace = e.getBlockFace();
        Abilities.map.put(uuid, playerCooldowns);
    }
    @EventHandler(ignoreCancelled = true)
    public void Explosive(BlockBreakEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerCooldowns playerCooldowns = (PlayerCooldowns) Abilities.map.get(uuid);
        if (playerCooldowns.explosive == true){
            e.setCancelled(true);
            if(playerCooldowns.blockFace.equals(BlockFace.UP) || playerCooldowns.blockFace.equals(BlockFace.DOWN)) {
                int amount = 0;
                Block block1 = e.getBlock().getRelative(BlockFace.EAST);
                Block block2 = e.getBlock().getRelative(BlockFace.WEST);
                Block block3 = e.getBlock().getRelative(BlockFace.NORTH);
                Block block4 = e.getBlock().getRelative(BlockFace.SOUTH);
                Block block5 = e.getBlock().getRelative(BlockFace.SOUTH_WEST);
                Block block6 = e.getBlock().getRelative(BlockFace.SOUTH_EAST);
                Block block7 = e.getBlock().getRelative(BlockFace.NORTH_EAST);
                Block block8 = e.getBlock().getRelative(BlockFace.NORTH_WEST);
                Block block9 = e.getBlock();
                List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block5, block6, block7, block8, block9);
                for (int i = 0; i< blocks.size(); i++){
                    if (blocks.get(i).getType() != Material.BEDROCK){
                        Block block = blocks.get(i);
                        Bukkit.getConsoleSender().sendMessage(block.getType().toString());
                        if (MiningEvents.tier1.contains(block.getType())){
                            amount += 20;
                        }
                        if (MiningEvents.tier2.contains(block.getType())){
                            amount += 30;
                        }
                        if (MiningEvents.tier3.contains(block.getType())){
                            amount += 50;
                        }
                        if (MiningEvents.tier4.contains(block.getType())){
                            amount += 100;
                        }
                        if (MiningEvents.tier5.contains(block.getType())){
                            amount += 150;
                        }
                        block.breakNaturally(e.getPlayer().getItemInUse());
                    }
                }
                if (amount > 0){
                    if (MiningEvents.miningExperienceManager.checkLevelUp(uuid, amount)){
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.miningExperienceManager.getLevel(uuid) + 1));
                    }
                    MiningEvents.miningExperienceManager.addExperience(uuid, amount);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.miningExperienceManager.getExperience(uuid) - MiningEvents.miningExperienceManager.getLevelExperience(uuid)) + "/" + MiningEvents.miningExperienceManager.getExperienceNeeded(uuid)));
                }
            }else if(playerCooldowns.blockFace.equals(BlockFace.NORTH) || playerCooldowns.blockFace.equals(BlockFace.SOUTH)){
                int amount = 0;
                Block block1 = e.getBlock().getRelative(BlockFace.UP);
                Block block2 = e.getBlock().getRelative(BlockFace.DOWN);
                Block block3 = e.getBlock().getRelative(BlockFace.EAST);
                Block block4 = e.getBlock().getRelative(BlockFace.WEST);
                Block block5 = block3.getRelative(BlockFace.UP);
                Block block6 = block3.getRelative(BlockFace.DOWN);
                Block block7 = block4.getRelative(BlockFace.UP);
                Block block8 = block4.getRelative(BlockFace.DOWN);
                Block block9 = e.getBlock();
                List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block5, block6, block7, block8, block9);
                for (int i = 0; i< blocks.size(); i++){
                    if (blocks.get(i).getType() != Material.BEDROCK){
                        Block block = blocks.get(i);
                        Bukkit.getConsoleSender().sendMessage(block.getType().toString());
                        if (MiningEvents.tier1.contains(block.getType())){
                            amount += 20;
                        }
                        if (MiningEvents.tier2.contains(block.getType())){
                            amount += 30;
                        }
                        if (MiningEvents.tier3.contains(block.getType())){
                            amount += 50;
                        }
                        if (MiningEvents.tier4.contains(block.getType())){
                            amount += 100;
                        }
                        if (MiningEvents.tier5.contains(block.getType())){
                            amount += 150;
                        }
                        block.breakNaturally(e.getPlayer().getItemInUse());
                    }
                }
                if (amount > 0){
                    if (MiningEvents.miningExperienceManager.checkLevelUp(uuid, amount)){
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.miningExperienceManager.getLevel(uuid) + 1));
                    }
                    MiningEvents.miningExperienceManager.addExperience(uuid, amount);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.miningExperienceManager.getExperience(uuid) - MiningEvents.miningExperienceManager.getLevelExperience(uuid)) + "/" + MiningEvents.miningExperienceManager.getExperienceNeeded(uuid)));
                }
            }else if(playerCooldowns.blockFace.equals(BlockFace.EAST) || playerCooldowns.blockFace.equals(BlockFace.WEST)){
                int amount = 0;
                Block block1 = e.getBlock().getRelative(BlockFace.UP);
                Block block2 = e.getBlock().getRelative(BlockFace.DOWN);
                Block block3 = e.getBlock().getRelative(BlockFace.NORTH);
                Block block4 = e.getBlock().getRelative(BlockFace.SOUTH);
                Block block5 = block3.getRelative(BlockFace.UP);
                Block block6 = block3.getRelative(BlockFace.DOWN);
                Block block7 = block4.getRelative(BlockFace.UP);
                Block block8 = block4.getRelative(BlockFace.DOWN);
                Block block9 = e.getBlock();
                List<Block> blocks = Arrays.asList(block1, block2, block3, block4, block5, block6, block7, block8, block9);
                for (int i = 0; i< blocks.size(); i++){
                    if (blocks.get(i).getType() != Material.BEDROCK){
                        Block block = blocks.get(i);
                        Bukkit.getConsoleSender().sendMessage(block.getType().toString());
                        if (MiningEvents.tier1.contains(block.getType())){
                            amount += 20;
                        }
                        if (MiningEvents.tier2.contains(block.getType())){
                            amount += 30;
                        }
                        if (MiningEvents.tier3.contains(block.getType())){
                            amount += 50;
                        }
                        if (MiningEvents.tier4.contains(block.getType())){
                            amount += 100;
                        }
                        if (MiningEvents.tier5.contains(block.getType())){
                            amount += 150;
                        }
                        block.breakNaturally(e.getPlayer().getItemInUse());
                    }
                }
                if (amount > 0){
                    if (MiningEvents.miningExperienceManager.checkLevelUp(uuid, amount)){
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.miningExperienceManager.getLevel(uuid) + 1));
                    }
                    MiningEvents.miningExperienceManager.addExperience(uuid, amount);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.miningExperienceManager.getExperience(uuid) - MiningEvents.miningExperienceManager.getLevelExperience(uuid)) + "/" + MiningEvents.miningExperienceManager.getExperienceNeeded(uuid)));
                }
            }
        }
    }
}
