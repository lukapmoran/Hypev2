package me.loogeh.Hype.Armour;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Player.HypePlayer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArmourCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "Console cannot use armour commands");
			return true;
		}
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("armour")) {
			if(!Permissions.isAdmin(player.getName())) {
				M.sendLackPermsMessage(player);
				return true;
			}
			if(args.length == 0) {
				
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("diamond") || args[0].equalsIgnoreCase("jugg") || args[0].equalsIgnoreCase("juggernaut")) {
					HypePlayer.setJuggernaut(player);
					M.message(player, "Armour", ChatColor.WHITE + "You set your kit to " + ChatColor.YELLOW + ClassType.DIAMOND.getKitName());
				}
				if(args[0].equalsIgnoreCase("iron") || args[0].equalsIgnoreCase("samurai")) {
					HypePlayer.setSamuri(player);
					M.message(player, "Armour", ChatColor.WHITE + "You set your kit to " + ChatColor.YELLOW + ClassType.IRON.getKitName());
				}
				if(args[0].equalsIgnoreCase("gold") || args[0].equalsIgnoreCase("agility")) {
					HypePlayer.setAgility(player);
					M.message(player, "Armour", ChatColor.WHITE + "You set your kit to " + ChatColor.YELLOW + ClassType.GOLD.getKitName());
				}
				if(args[0].equalsIgnoreCase("chain") || args[0].equalsIgnoreCase("specialist")) {
					HypePlayer.setSpecialist(player);
					M.message(player, "Armour", ChatColor.WHITE + "You set your kit to " + ChatColor.YELLOW + ClassType.CHAIN.getKitName());
				}
				if(args[0].equalsIgnoreCase("leather") || args[0].equalsIgnoreCase("archer")) {
					HypePlayer.setArcher(player);
					M.message(player, "Armour", ChatColor.WHITE + "You set your kit to " + ChatColor.YELLOW + ClassType.LEATHER.getKitName());
				}
			}
		}
		return false;
	}

}
