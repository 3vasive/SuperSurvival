package me.evasive.supersurvival.abilities;

import de.jeff_media.customblockdata.CustomBlockData;
import me.evasive.supersurvival.SuperSurvival;
import me.evasive.supersurvival.experienceManagers.MiningExperienceManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class MiningEvents implements Listener {
    public SuperSurvival plugin;

    public MiningEvents(SuperSurvival plugin) {
        this.plugin = plugin;
    }

    public static EnumSet<Material> tier1 = EnumSet.of(Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE);
    public static EnumSet<Material> tier2 = EnumSet.of(Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE);
    public static EnumSet<Material> tier3 = EnumSet.of(Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
    public static EnumSet<Material> tier4 = EnumSet.of(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE);
    public static EnumSet<Material> tier5 = EnumSet.of(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE);
    public static MiningExperienceManager miningExperienceManager = new MiningExperienceManager();

    @EventHandler(ignoreCancelled = true)
    public void ExplosiveFace(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerCooldowns playerCooldowns = (PlayerCooldowns) Abilities.map.get(uuid);
        playerCooldowns.blockFace = e.getBlockFace();
        Abilities.map.put(uuid, playerCooldowns);
    }

    @EventHandler(ignoreCancelled = true)
    public void BlockBreaking(BlockBreakEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerCooldowns playerCooldowns = (PlayerCooldowns) Abilities.map.get(uuid);
        if (playerCooldowns.explosive) {
            e.setCancelled(true);
            if (playerCooldowns.blockFace.equals(BlockFace.UP) || playerCooldowns.blockFace.equals(BlockFace.DOWN)) {
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
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getType() != Material.BEDROCK) {
                        Block block = blocks.get(i);
                        Bukkit.getConsoleSender().sendMessage(block.getType().toString());
                        if (MiningEvents.tier1.contains(block.getType())) {
                            amount += 20;
                        }
                        if (MiningEvents.tier2.contains(block.getType())) {
                            amount += 30;
                        }
                        if (MiningEvents.tier3.contains(block.getType())) {
                            amount += 50;
                        }
                        if (MiningEvents.tier4.contains(block.getType())) {
                            amount += 100;
                        }
                        if (MiningEvents.tier5.contains(block.getType())) {
                            amount += 150;
                        }
                        block.breakNaturally(e.getPlayer().getItemInUse());
                    }
                }
                if (amount > 0) {
                    if (MiningEvents.miningExperienceManager.checkLevelUp(uuid, amount)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.miningExperienceManager.getLevel(uuid) + 1));
                    }
                    MiningEvents.miningExperienceManager.addExperience(uuid, amount);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.miningExperienceManager.getExperience(uuid) - MiningEvents.miningExperienceManager.getLevelExperience(uuid)) + "/" + MiningEvents.miningExperienceManager.getExperienceNeeded(uuid)));
                }
            } else if (playerCooldowns.blockFace.equals(BlockFace.NORTH) || playerCooldowns.blockFace.equals(BlockFace.SOUTH)) {
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
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getType() != Material.BEDROCK) {
                        Block block = blocks.get(i);
                        Bukkit.getConsoleSender().sendMessage(block.getType().toString());
                        if (MiningEvents.tier1.contains(block.getType())) {
                            amount += 20;
                        }
                        if (MiningEvents.tier2.contains(block.getType())) {
                            amount += 30;
                        }
                        if (MiningEvents.tier3.contains(block.getType())) {
                            amount += 50;
                        }
                        if (MiningEvents.tier4.contains(block.getType())) {
                            amount += 100;
                        }
                        if (MiningEvents.tier5.contains(block.getType())) {
                            amount += 150;
                        }
                        block.breakNaturally(e.getPlayer().getItemInUse());
                    }
                }
                if (amount > 0) {
                    if (MiningEvents.miningExperienceManager.checkLevelUp(uuid, amount)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.miningExperienceManager.getLevel(uuid) + 1));
                    }
                    MiningEvents.miningExperienceManager.addExperience(uuid, amount);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.miningExperienceManager.getExperience(uuid) - MiningEvents.miningExperienceManager.getLevelExperience(uuid)) + "/" + MiningEvents.miningExperienceManager.getExperienceNeeded(uuid)));
                }
            } else if (playerCooldowns.blockFace.equals(BlockFace.EAST) || playerCooldowns.blockFace.equals(BlockFace.WEST)) {
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
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getType() != Material.BEDROCK) {
                        Block block = blocks.get(i);
                        Bukkit.getConsoleSender().sendMessage(block.getType().toString());
                        if (MiningEvents.tier1.contains(block.getType())) {
                            amount += 20;
                        }
                        if (MiningEvents.tier2.contains(block.getType())) {
                            amount += 30;
                        }
                        if (MiningEvents.tier3.contains(block.getType())) {
                            amount += 50;
                        }
                        if (MiningEvents.tier4.contains(block.getType())) {
                            amount += 100;
                        }
                        if (MiningEvents.tier5.contains(block.getType())) {
                            amount += 150;
                        }
                        block.breakNaturally(e.getPlayer().getItemInUse());
                    }
                }
                if (amount > 0) {
                    if (MiningEvents.miningExperienceManager.checkLevelUp(uuid, amount)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.miningExperienceManager.getLevel(uuid) + 1));
                    }
                    MiningEvents.miningExperienceManager.addExperience(uuid, amount);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.miningExperienceManager.getExperience(uuid) - MiningEvents.miningExperienceManager.getLevelExperience(uuid)) + "/" + MiningEvents.miningExperienceManager.getExperienceNeeded(uuid)));
                }
            }
        } else {
            NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
            CustomBlockData customBlockData = new CustomBlockData(e.getBlock(), this.plugin);
            if (!customBlockData.has(key, PersistentDataType.STRING)) {
                if (tier1.contains(e.getBlock().getType())) {
                    if (miningExperienceManager.checkLevelUp(uuid, 20)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                    }
                    miningExperienceManager.addExperience(uuid, 20);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 20 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
                } else if (tier2.contains(e.getBlock().getType())) {
                    if (miningExperienceManager.checkLevelUp(uuid, 30)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                    }
                    miningExperienceManager.addExperience(uuid, 30);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 30 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
                } else if (tier3.contains(e.getBlock().getType())) {
                    if (miningExperienceManager.checkLevelUp(uuid, 50)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                    }
                    miningExperienceManager.addExperience(uuid, 50);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 50 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
                } else if (tier4.contains(e.getBlock().getType())) {
                    if (miningExperienceManager.checkLevelUp(uuid, 100)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                    }
                    miningExperienceManager.addExperience(uuid, 100);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 100 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
                } else if (tier5.contains(e.getBlock().getType())) {
                    if (miningExperienceManager.checkLevelUp(uuid, 150)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                    }
                    miningExperienceManager.addExperience(uuid, 150);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 150 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
                }
            }
            customBlockData.remove(key);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void placeOre(BlockPlaceEvent e) {
        Block b = e.getBlock();
        if (b.getType().equals(Material.COAL_ORE) || b.getType().equals(Material.DEEPSLATE_COAL_ORE) || b.getType().equals(Material.NETHER_QUARTZ_ORE) || b.getType().equals(Material.NETHER_GOLD_ORE) || b.getType().equals(Material.REDSTONE_ORE) || b.getType().equals(Material.DEEPSLATE_REDSTONE_ORE) || b.getType().equals(Material.LAPIS_ORE) || b.getType().equals(Material.DEEPSLATE_LAPIS_ORE) || b.getType().equals(Material.GOLD_ORE) || b.getType().equals(Material.DEEPSLATE_GOLD_ORE) || b.getType().equals(Material.EMERALD_ORE) || b.getType().equals(Material.DEEPSLATE_EMERALD_ORE) || b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.DEEPSLATE_DIAMOND_ORE)) {
            PersistentDataContainer customBlockData = new CustomBlockData(b, this.plugin);
            NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
            customBlockData.set(key, PersistentDataType.STRING, "Placed");
        }
    }
}
