package me.evasive.supersurvival.Skills.Mining.Events;

//import me.evasive.supersurvival.BlockData;
import de.jeff_media.customblockdata.CustomBlockData;
import me.evasive.supersurvival.Skills.Mining.ExperienceManager;
//import me.evasive.supersurvival.Skills.PlacedBlockManager;
import me.evasive.supersurvival.SuperSurvival;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MiningEvents implements Listener {
    public MiningEvents(SuperSurvival plugin){
        this.plugin = plugin;
    }

    public static ExperienceManager experienceManager = new ExperienceManager();
    //public static PlacedBlockManager placedBlockManager = new PlacedBlockManager();
    public SuperSurvival plugin;

    @EventHandler(ignoreCancelled = true)
    public void mineBlock(BlockBreakEvent e){
        Player player = e.getPlayer();
        NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
        CustomBlockData customBlockData = new CustomBlockData(e.getBlock(), this.plugin);
        if (!customBlockData.has(key, PersistentDataType.STRING)){
            if (e.getBlock().getType().equals(Material.COAL_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_COAL_ORE) || e.getBlock().getType().equals(Material.NETHER_GOLD_ORE) || e.getBlock().getType().equals(Material.NETHER_QUARTZ_ORE) || e.getBlock().getType().equals(Material.REDSTONE_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_REDSTONE_ORE) || e.getBlock().getType().equals(Material.LAPIS_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_LAPIS_ORE)){
                experienceManager.addExperience(player.getUniqueId(), 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 1 + " XP"));
            }else if (e.getBlock().getType().equals(Material.IRON_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_IRON_ORE) || e.getBlock().getType().equals(Material.COPPER_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_COPPER_ORE)){
                experienceManager.addExperience(player.getUniqueId(), 3);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 3 + " XP"));
            }else if (e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_GOLD_ORE)){
                experienceManager.addExperience(player.getUniqueId(), 5);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 5 + " XP"));
            }else if (e.getBlock().getType().equals(Material.DIAMOND_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_DIAMOND_ORE) || e.getBlock().getType().equals(Material.EMERALD_ORE) || e.getBlock().getType().equals(Material.DEEPSLATE_EMERALD_ORE)){
                experienceManager.addExperience(player.getUniqueId(), 10);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "+ " + 10 + " XP"));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void placeOre(BlockPlaceEvent e){
        Block b = e.getBlock();
        if (b.getType().equals(Material.COAL_ORE) || b.getType().equals(Material.DEEPSLATE_COAL_ORE) || b.getType().equals(Material.NETHER_QUARTZ_ORE) || b.getType().equals(Material.NETHER_GOLD_ORE) || b.getType().equals(Material.REDSTONE_ORE) || b.getType().equals(Material.DEEPSLATE_REDSTONE_ORE) || b.getType().equals(Material.LAPIS_ORE) || b.getType().equals(Material.DEEPSLATE_LAPIS_ORE) || b.getType().equals(Material.GOLD_ORE) || b.getType().equals(Material.DEEPSLATE_GOLD_ORE) || b.getType().equals(Material.EMERALD_ORE) || b.getType().equals(Material.DEEPSLATE_EMERALD_ORE) || b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.DEEPSLATE_DIAMOND_ORE)){
            /*b.setMetadata("PLACED", new FixedMetadataValue(this.plugin, "player"));

            String cord = b.getLocation().toString();
            String chunk = b.getChunk().toString();
            String shortChunk = chunk.substring(11, chunk.length() - 1);*/
            PersistentDataContainer customBlockData = new CustomBlockData(b, this.plugin);
            NamespacedKey key = new NamespacedKey(this.plugin, "SuperSurvival");
            customBlockData.set(key, PersistentDataType.STRING , "Placed");
            //placedBlockManager.addBlock(shortChunk, cord);
        }
    }
}