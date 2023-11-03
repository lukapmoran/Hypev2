package me.loogeh.Hype.Entity;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EntityCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "Console cannot use entity commands");
			return true;
		}
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("entity")) {
			if(args.length == 0) {
				
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("ss") || args[0].equalsIgnoreCase("seizuresheep")) {
					M.sendHelpMessage(player, "/entity ss spawn <name>", "Marks a seizure sheep for removal");
					M.sendHelpMessage(player, "/entity ss remove <name>", "Marks a seizure sheep for removal");
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("ss") || args[0].equalsIgnoreCase("seizuresheep")) {
					if(args[1].equalsIgnoreCase("spawn")) {
						player.sendMessage(M.getUsage("/entity " + args[0] + " spawn <name>"));
						return true;
					}
					if(args[1].equalsIgnoreCase("remove")) {
						player.sendMessage(M.getUsage("/entity " + args[0] + " remove <name>"));
						return true;
					}
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("ss") || args[0].equalsIgnoreCase("seizuresheep")) {
					if(args[1].equalsIgnoreCase("spawn")) {
						if(SeizureSheep.exists(args[2])) {
							M.message(player, "Entity", ChatColor.WHITE + "Seizure Sheep " + ChatColor.translateAlternateColorCodes('&', args[2]) + ChatColor.WHITE + " already exists");
							return true;
						}
						SeizureSheep sheep = new SeizureSheep(args[2], player.getLocation());
						sheep.add();
						M.message(player, "Entity", ChatColor.WHITE + "You added a " + ChatColor.YELLOW + "Seizure Sheep");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("ss") || args[0].equalsIgnoreCase("seizuresheep")) {
					if(args[1].equalsIgnoreCase("remove")) {
						SeizureSheep target = SeizureSheep.getSheep(args[2]);
						if(target == null) {
							M.message(player, "Entity", ChatColor.YELLOW + args[2] + ChatColor.WHITE + " doesn't exist");
							return true;
						}
						target.setRemove(true);
						M.message(player, "Entity", ChatColor.WHITE + "You marked " + ChatColor.YELLOW + args[2] + ChatColor.WHITE + " for removal");
						return true;
					}
				}
			}
			if(args.length > 3) {
				if(args[0].equalsIgnoreCase("ss") || args[0].equalsIgnoreCase("seizuresheep")) {
					if(args[1].equalsIgnoreCase("spawn")) {
						String name = util.join(2, args);
						if(SeizureSheep.exists(name)) {
							M.message(player, "Entity", ChatColor.WHITE + "Seizure Sheep " + ChatColor.translateAlternateColorCodes('&', name) + ChatColor.WHITE + " already exists");
							return true;
						}
						SeizureSheep sheep = new SeizureSheep(name, player.getLocation());
						sheep.add();
						M.message(player, "Entity", ChatColor.WHITE + "You added a " + ChatColor.YELLOW + "Seizure Sheep");
						return true;
					}
					if(args[1].equalsIgnoreCase("remove")) {
						String name = util.join(2, args);
						SeizureSheep target = SeizureSheep.getSheep(name);
						if(target == null) {
							M.message(player, "Entity", ChatColor.YELLOW + name + ChatColor.WHITE + " doesn't exist");
							return true;
						}
						target.setRemove(true);
						M.message(player, "Entity", ChatColor.WHITE + "You marked " + ChatColor.YELLOW + name + ChatColor.WHITE + " for removal");
						return true;
					}
				}
			}
		}
		return false;
	}

}
