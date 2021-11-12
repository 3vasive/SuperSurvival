package me.evasive.supersurvival.commands;

import me.evasive.supersurvival.abilities.MiningEvents;
import me.evasive.supersurvival.SuperSurvival;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    public SuperSurvival plugin;

    public Commands(SuperSurvival plugin){
        this.plugin = plugin;
        plugin.getCommand("skills").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("supersurvival.admin")){
            if (args.length == 0){
                Player player = (Player) sender;
                sender.sendMessage("Mining: " + MiningEvents.experienceManager.getLevel(player.getUniqueId(), 1));
                sender.sendMessage("Logging: " + MiningEvents.experienceManager.getLevel(player.getUniqueId(), 2));
                sender.sendMessage("Fishing: null");
                sender.sendMessage("Farming: null");
                sender.sendMessage("Hunting: null");
                sender.sendMessage("Cooking: null");
                //Show command running players skills
                return true;
            }else if(args.length == 1) {
                //Show playername skills
                return true;
            }
        }
        return false;
    }
}
