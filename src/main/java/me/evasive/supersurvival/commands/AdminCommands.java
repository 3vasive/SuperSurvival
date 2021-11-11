package me.evasive.supersurvival.commands;

import me.evasive.supersurvival.MiningEvents;
import me.evasive.supersurvival.SuperSurvival;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {

    public SuperSurvival plugin;

    public AdminCommands(SuperSurvival plugin){
        this.plugin = plugin;
        plugin.getCommand("admin").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("supersurvival.admin")){
            if (args.length == 0){
                sender.sendMessage("/admin <skill> <player>");
                return true;
            }else if(args.length == 1){
                return true;
            }else if(args.length == 2){
                if(args[0].equals("mining")){
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null){
                        player.sendMessage(player.getDisplayName() + " is level " + MiningEvents.miningExperienceManager.getLevel(player.getUniqueId()) + " and has " + MiningEvents.miningExperienceManager.getExperience(player.getUniqueId()) + " experience");
                    }
                }
                return true;
            }
        }
        return false;
    }
}
