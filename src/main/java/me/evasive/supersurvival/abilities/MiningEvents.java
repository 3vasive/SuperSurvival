package me.evasive.supersurvival.abilities;

import de.jeff_media.customblockdata.CustomBlockData;
import me.evasive.supersurvival.SuperSurvival;
import me.evasive.supersurvival.experienceManagers.ExperienceManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class MiningEvents implements Listener {
    public SuperSurvival plugin;

    public MiningEvents(SuperSurvival plugin) {
        this.plugin = plugin;
    }

    public static EnumSet<Material> wood1 = EnumSet.of(Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG);
    public static EnumSet<Material> wood2 = EnumSet.of(Material.CRIMSON_STEM, Material.WARPED_STEM);
    public static EnumSet<Material> tier1 = EnumSet.of(Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE);
    public static EnumSet<Material> tier2 = EnumSet.of(Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE);
    public static EnumSet<Material> tier3 = EnumSet.of(Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
    public static EnumSet<Material> tier4 = EnumSet.of(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE);
    public static EnumSet<Material> tier5 = EnumSet.of(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE);
    public static EnumSet<Material> explosiveList = EnumSet.of(Material.STONE, Material.COBBLESTONE, Material.DEEPSLATE, Material.MOSSY_COBBLESTONE,
            Material.GRANITE, Material.DIORITE, Material.ANDESITE, Material.SANDSTONE, Material.RED_SANDSTONE,
            Material.NETHERRACK, Material.NETHER_BRICK, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.BLACKSTONE, Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.IRON_ORE,
            Material.DEEPSLATE_IRON_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE, Material.LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE, Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.EMERALD_ORE,
            Material.DEEPSLATE_EMERALD_ORE);
    public static ExperienceManager experienceManager = new ExperienceManager();

    @EventHandler(ignoreCancelled = true)
    public void ExplosiveFace(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerCooldowns playerCooldowns = Abilities.map.get(uuid);
        playerCooldowns.blockFace = e.getBlockFace();
        Abilities.map.put(uuid, playerCooldowns);
    }

    @EventHandler(ignoreCancelled = true)
    public void BlockBreaking(BlockBreakEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerCooldowns playerCooldowns = Abilities.map.get(uuid);
        //----------------------------------Mining----------------------------------//
        if (Abilities.picks.contains(player.getInventory().getItemInMainHand().getType())) {
            //----------------------------------Passive----------------------------------//
            int level = experienceManager.getLevel(uuid, 1);
            Random r = new Random();
            int random = r.nextInt(100) + 1;
            Block block = e.getBlock();
            if (random <= (double) level / 4 && (tier1.contains(block.getType()) || tier2.contains(block.getType()) || tier3.contains(block.getType()) || tier4.contains(block.getType()) || tier5.contains(block.getType()))) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "*Double Drop*");
                e.setDropItems(false);
                for (ItemStack item : e.getBlock().getDrops(player.getInventory().getItemInMainHand())) {
                    item.setAmount(item.getAmount() * 2);
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), item);
                }
            }
            //---------------------------------Explosive---------------------------------//
            if (playerCooldowns.explosive) {
                e.setCancelled(true);
                int amount = 0;
                if (playerCooldowns.blockFace.equals(BlockFace.UP) || playerCooldowns.blockFace.equals(BlockFace.DOWN)) {
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
                    for (Block value : blocks) {
                        if (explosiveList.contains(value.getType())) {
                            if (MiningEvents.tier1.contains(value.getType())) {
                                amount += 20;
                            }
                            if (MiningEvents.tier2.contains(value.getType())) {
                                amount += 30;
                            }
                            if (MiningEvents.tier3.contains(value.getType())) {
                                amount += 50;
                            }
                            if (MiningEvents.tier4.contains(value.getType())) {
                                amount += 100;
                            }
                            if (MiningEvents.tier5.contains(value.getType())) {
                                amount += 150;
                            }
                            value.breakNaturally(e.getPlayer().getItemInUse());
                        } else if (value == block9) {
                            block9.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        }
                    }
                } else if (playerCooldowns.blockFace.equals(BlockFace.NORTH) || playerCooldowns.blockFace.equals(BlockFace.SOUTH)) {
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
                    for (Block value : blocks) {
                        if (explosiveList.contains(value.getType())) {
                            if (MiningEvents.tier1.contains(value.getType())) {
                                amount += 20;
                            }
                            if (MiningEvents.tier2.contains(value.getType())) {
                                amount += 30;
                            }
                            if (MiningEvents.tier3.contains(value.getType())) {
                                amount += 50;
                            }
                            if (MiningEvents.tier4.contains(value.getType())) {
                                amount += 100;
                            }
                            if (MiningEvents.tier5.contains(value.getType())) {
                                amount += 150;
                            }
                            value.breakNaturally(e.getPlayer().getItemInUse());
                        } else if (value == block9) {
                            block9.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        }
                    }
                } else if (playerCooldowns.blockFace.equals(BlockFace.EAST) || playerCooldowns.blockFace.equals(BlockFace.WEST)) {
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
                    for (Block value : blocks) {
                        if (explosiveList.contains(value.getType())) {
                            if (MiningEvents.tier1.contains(value.getType())) {
                                amount += 20;
                            }
                            if (MiningEvents.tier2.contains(value.getType())) {
                                amount += 30;
                            }
                            if (MiningEvents.tier3.contains(value.getType())) {
                                amount += 50;
                            }
                            if (MiningEvents.tier4.contains(value.getType())) {
                                amount += 100;
                            }
                            if (MiningEvents.tier5.contains(value.getType())) {
                                amount += 150;
                            }
                            value.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        } else if (value == block9) {
                            block9.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        }
                    }
                }
                if (amount > 0) {
                    if (MiningEvents.experienceManager.checkLevelUp(uuid, amount, 1)) {
                        player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (MiningEvents.experienceManager.getLevel(uuid, 1) + 1));
                    }
                    MiningEvents.experienceManager.addExperience(uuid, amount, 1);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + amount + " XP " + (MiningEvents.experienceManager.getExperience(uuid, 1) - MiningEvents.experienceManager.getLevelExperience(uuid, 1)) + "/" + MiningEvents.experienceManager.getExperienceNeeded(uuid, 1)));
                }
            } else {
                NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
                CustomBlockData customBlockData = new CustomBlockData(e.getBlock(), this.plugin);
                if (!customBlockData.has(key, PersistentDataType.STRING)) {
                    //----------------------------------------Mining Experience----------------------------------------//
                    if (tier1.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 20, 1)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 1) + 1));
                        }
                        experienceManager.addExperience(uuid, 20, 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 20 + " XP " + (experienceManager.getExperience(uuid, 1) - experienceManager.getLevelExperience(uuid, 1)) + "/" + experienceManager.getExperienceNeeded(uuid, 1)));
                    } else if (tier2.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 30, 1)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 1) + 1));
                        }
                        experienceManager.addExperience(uuid, 30, 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 30 + " XP " + (experienceManager.getExperience(uuid, 1) - experienceManager.getLevelExperience(uuid, 1)) + "/" + experienceManager.getExperienceNeeded(uuid, 1)));
                    } else if (tier3.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 50, 1)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 1) + 1));
                        }
                        experienceManager.addExperience(uuid, 50, 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 50 + " XP " + (experienceManager.getExperience(uuid, 1) - experienceManager.getLevelExperience(uuid, 1)) + "/" + experienceManager.getExperienceNeeded(uuid, 1)));
                    } else if (tier4.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 100, 1)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 1) + 1));
                        }
                        experienceManager.addExperience(uuid, 100, 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 100 + " XP " + (experienceManager.getExperience(uuid, 1) - experienceManager.getLevelExperience(uuid, 1)) + "/" + experienceManager.getExperienceNeeded(uuid, 1)));
                    } else if (tier5.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 150, 1)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 1) + 1));
                        }
                        experienceManager.addExperience(uuid, 150, 1);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 150 + " XP " + (experienceManager.getExperience(uuid, 1) - experienceManager.getLevelExperience(uuid, 1)) + "/" + experienceManager.getExperienceNeeded(uuid, 1)));
                    }
                    //----------------------------------------Logging Experience----------------------------------------//
                    if (wood1.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 50, 2)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Logging level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 2) + 1));
                        }
                        experienceManager.addExperience(uuid, 50, 2);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 50 + " XP " + (experienceManager.getExperience(uuid, 2) - experienceManager.getLevelExperience(uuid, 2)) + "/" + experienceManager.getExperienceNeeded(uuid, 2)));
                    } else if (wood2.contains(e.getBlock().getType())) {
                        if (experienceManager.checkLevelUp(uuid, 100, 2)) {
                            player.sendMessage(ChatColor.GOLD + "You have reached Logging level " + ChatColor.BLUE + (experienceManager.getLevel(uuid, 2) + 1));
                        }
                        experienceManager.addExperience(uuid, 100, 2);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 100 + " XP " + (experienceManager.getExperience(uuid, 2) - experienceManager.getLevelExperience(uuid, 2)) + "/" + experienceManager.getExperienceNeeded(uuid, 2)));
                    }
                }
                customBlockData.remove(key);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void placeOre(BlockPlaceEvent e) {
        Block b = e.getBlock();
        Material mat = b.getType();
        if (wood1.contains(mat) || wood2.contains(mat) || tier1.contains(mat) || tier2.contains(mat) || tier3.contains(mat) || tier4.contains(mat) || tier5.contains(mat)) {
            PersistentDataContainer customBlockData = new CustomBlockData(b, this.plugin);
            NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
            customBlockData.set(key, PersistentDataType.STRING, "Placed");
        }
    }
}
