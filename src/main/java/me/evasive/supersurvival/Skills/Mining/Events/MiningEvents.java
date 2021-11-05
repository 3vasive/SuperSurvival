package me.evasive.supersurvival.Skills.Mining.Events;

import de.jeff_media.customblockdata.CustomBlockData;
import me.evasive.supersurvival.Skills.Mining.MiningExperienceManager;
import me.evasive.supersurvival.SuperSurvival;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class MiningEvents implements Listener {
    public MiningEvents(SuperSurvival plugin){
        this.plugin = plugin;
    }

    public static MiningExperienceManager miningExperienceManager = new MiningExperienceManager();
    public SuperSurvival plugin;

    @EventHandler(ignoreCancelled = true)
    public void mineBlock(BlockBreakEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
        CustomBlockData customBlockData = new CustomBlockData(e.getBlock(), this.plugin);
        if (!customBlockData.has(key, PersistentDataType.STRING)){
            if (e.getBlock().getType().equals(Material.COAL_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_COAL_ORE) || e.getBlock().getType().equals(Material.NETHER_GOLD_ORE) || e.getBlock().getType().equals(Material.NETHER_QUARTZ_ORE)) {
                if (miningExperienceManager.checkLevelUp(uuid, 20)) {
                    player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                }
                miningExperienceManager.addExperience(uuid, 20);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 20 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
            }else if (e.getBlock().getType().equals(Material.IRON_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_IRON_ORE) || e.getBlock().getType().equals(Material.COPPER_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_COPPER_ORE)){
                if (miningExperienceManager.checkLevelUp(uuid, 30)){
                    player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                }
                miningExperienceManager.addExperience(uuid, 30);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 30 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
            }else if (e.getBlock().getType().equals(Material.REDSTONE_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_REDSTONE_ORE) || e.getBlock().getType().equals(Material.LAPIS_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_LAPIS_ORE)){
                if (miningExperienceManager.checkLevelUp(uuid, 50)){
                    player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                }
                miningExperienceManager.addExperience(uuid, 50);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 50 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
            }else if (e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_GOLD_ORE)){
                if (miningExperienceManager.checkLevelUp(uuid, 100)){
                    player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                }
                miningExperienceManager.addExperience(uuid, 100);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 100 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
            }else if (e.getBlock().getType().equals(Material.DIAMOND_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_DIAMOND_ORE) || e.getBlock().getType().equals(Material.EMERALD_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_EMERALD_ORE)){
                if (miningExperienceManager.checkLevelUp(uuid, 150)){
                    player.sendMessage(ChatColor.GOLD + "You have reached Mining level " + ChatColor.BLUE + (miningExperienceManager.getLevel(uuid) + 1));
                }
                miningExperienceManager.addExperience(uuid, 150);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 150 + " XP " + (miningExperienceManager.getExperience(uuid) - miningExperienceManager.getLevelExperience(uuid)) + "/" + miningExperienceManager.getExperienceNeeded(uuid)));
            }
        }
        customBlockData.remove(key);
    }

    @EventHandler(ignoreCancelled = true)
    public void placeOre(BlockPlaceEvent e){
        Block b = e.getBlock();
        if (b.getType().equals(Material.COAL_ORE) || b.getType().equals(Material.DEEPSLATE_COAL_ORE) || b.getType().equals(Material.NETHER_QUARTZ_ORE) || b.getType().equals(Material.NETHER_GOLD_ORE) || b.getType().equals(Material.REDSTONE_ORE) || b.getType().equals(Material.DEEPSLATE_REDSTONE_ORE) || b.getType().equals(Material.LAPIS_ORE) || b.getType().equals(Material.DEEPSLATE_LAPIS_ORE) || b.getType().equals(Material.GOLD_ORE) || b.getType().equals(Material.DEEPSLATE_GOLD_ORE) || b.getType().equals(Material.EMERALD_ORE) || b.getType().equals(Material.DEEPSLATE_EMERALD_ORE) || b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.DEEPSLATE_DIAMOND_ORE)){
            PersistentDataContainer customBlockData = new CustomBlockData(b, this.plugin);
            NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
            customBlockData.set(key, PersistentDataType.STRING , "Placed");
        }
    }
}