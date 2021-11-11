package me.evasive.supersurvival.abilities;

import me.evasive.supersurvival.SuperSurvival;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

public class Abilities implements Listener {

    EnumSet<Material> picks = EnumSet.of(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
    public SuperSurvival plugin;

    public Abilities(SuperSurvival plugin) {
        this.plugin = plugin;
    }

    public static HashMap map = new HashMap<UUID, PlayerCooldowns>();

    @EventHandler
    public void Abilities(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        EquipmentSlot equipmentSlot = e.getHand();
        PlayerCooldowns playerCooldowns = (PlayerCooldowns) map.get(uuid);
        //Need timer to count down cooldowns (Do not need to save on shutdown as cooldowns would only be a few mins anyways)
        if (e.getAction().toString().equals("RIGHT_CLICK_AIR") || e.getAction().toString().equals("RIGHT_CLICK_BLOCK")) {
            if (picks.contains(e.getPlayer().getInventory().getItemInMainHand().getType()) && equipmentSlot.equals(EquipmentSlot.HAND)) {
                if (player.isSneaking()) {
                    if (playerCooldowns.abilityNum != 1) {
                        playerCooldowns.abilityNum = 1;
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "Explosive"));
                    } else {
                        playerCooldowns.abilityNum = 0;
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "Haste Miner"));
                    }
                } else {
                    if (playerCooldowns.abilityNum != 1) {
                        //----------------------------------------------Haste Ability----------------------------------------------//
                        if (playerCooldowns.active == false && playerCooldowns.hasteCooldown <= 0) {
                            playerCooldowns.time = 20;
                            playerCooldowns.active = true;
                            playerCooldowns.hasteCooldown = 30;
                            map.put(uuid, playerCooldowns);
                            player.sendMessage("Haste Miner has started!!!");
                            PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 500, 2);
                            player.addPotionEffect(potionEffect);
                            playerCooldowns.miningID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperSurvival.getPlugin(SuperSurvival.class), new TimerTask() {
                                @Override
                                public void run() {
                                    if (playerCooldowns.time == 0) {
                                        Bukkit.getScheduler().cancelTask(playerCooldowns.miningID1);
                                        player.removePotionEffect(potionEffect.getType());
                                        player.sendMessage("Time is up for Haste Miner");
                                        playerCooldowns.active = false;
                                        playerCooldowns.miningID1 = 0;
                                        map.put(uuid, playerCooldowns);
                                        playerCooldowns.miningCooldownID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperSurvival.getPlugin(SuperSurvival.class), new TimerTask() {
                                            @Override
                                            public void run() {
                                                if (playerCooldowns.hasteCooldown == 0) {
                                                    Bukkit.getScheduler().cancelTask(playerCooldowns.miningCooldownID1);
                                                    player.sendMessage("HateMiner is ready to be used");
                                                    playerCooldowns.miningCooldownID1 = 0;
                                                    map.put(uuid, playerCooldowns);
                                                } else {
                                                    playerCooldowns.hasteCooldown -= 1;
                                                }
                                            }
                                        }, 20, 20);
                                    } else {
                                        playerCooldowns.time -= 1;
                                    }
                                }
                            }, 20, 20);
                        } else if (playerCooldowns.active == true) {
                            player.sendMessage("You already have another ability active");
                        } else if (playerCooldowns.hasteCooldown > 0) {
                            player.sendMessage("Ability is on cooldown for " + playerCooldowns.hasteCooldown + " seconds");
                        }
                    } else {
                        //----------------------------------------------Explosive Ability----------------------------------------------//
                        if (playerCooldowns.active == false && playerCooldowns.explosiveCooldown <= 0) {
                            playerCooldowns.time = 20;
                            playerCooldowns.active = true;
                            playerCooldowns.explosive = true;
                            playerCooldowns.explosiveCooldown = 30;
                            map.put(uuid, playerCooldowns);
                            player.sendMessage("Explosive Activated");
                            playerCooldowns.miningID2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperSurvival.getPlugin(SuperSurvival.class), new TimerTask() {
                                @Override
                                public void run() {
                                    if (playerCooldowns.time == 0) {
                                        Bukkit.getScheduler().cancelTask(playerCooldowns.miningID2);
                                        player.sendMessage("Time is up for Explosive");
                                        playerCooldowns.active = false;
                                        playerCooldowns.explosive = false;
                                        playerCooldowns.miningID2 = 0;
                                        map.put(uuid, playerCooldowns);
                                        playerCooldowns.miningCooldownID2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperSurvival.getPlugin(SuperSurvival.class), new TimerTask() {
                                            @Override
                                            public void run() {
                                                if (playerCooldowns.explosiveCooldown == 0) {
                                                    Bukkit.getScheduler().cancelTask(playerCooldowns.miningCooldownID2);
                                                    player.sendMessage("Explosive is ready to be used");
                                                    playerCooldowns.miningCooldownID2 = 0;
                                                    map.put(uuid, playerCooldowns);
                                                } else {
                                                    playerCooldowns.explosiveCooldown -= 1;
                                                }
                                            }
                                        }, 20, 20);
                                    } else {
                                        playerCooldowns.time -= 1;
                                    }
                                }
                            }, 20, 20);
                        } else if (playerCooldowns.active == true) {
                            player.sendMessage("You already have another ability active");
                        } else if (playerCooldowns.explosiveCooldown > 0) {
                            player.sendMessage("Ability is on cooldown for " + playerCooldowns.explosiveCooldown + " seconds");
                        }
                    }
                }
            }
        }
    }
}


