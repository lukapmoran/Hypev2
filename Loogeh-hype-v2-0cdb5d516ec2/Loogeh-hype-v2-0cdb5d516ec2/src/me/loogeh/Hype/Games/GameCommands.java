package me.loogeh.Hype.Games;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Games.Game.GameType;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "Console cannot use Game Commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			M.message(player, "Game", ChatColor.WHITE + "Failed to find your player data");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("game")) {
			if(args.length == 0) {
				
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("join")) {
					player.openInventory(Game.game_selector.getInventory());
					return true;
				}
			}
			if(args.length == 2) {
				if(Permissions.isAdmin(player.getName())) {
					if(args[0].equalsIgnoreCase("create")) {
						player.sendMessage(M.getUsage("/game create <type> <map>"));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("join")) {
					player.openInventory(Game.game_selector.getInventory());
					return true;
				}
				if(args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("quit")) {
					
				}
				if(args[0].equalsIgnoreCase("spawn")) {
					if(args[1].equalsIgnoreCase("flag")) {
					}
				}
			}
			if(args.length == 3) {
				if(Permissions.isAdmin(player.getName())) {
					if(args[0].equalsIgnoreCase("create")) {
						Integer id = util.getInteger(args[1]);
						if(id == null) {
							M.message(player, "Game", ChatColor.WHITE + "You must enter a valid " + ChatColor.GREEN + "ID");
							return true;
						}
						GameType type = GameType.getType(id);
						if(type == null) {
							M.message(player, "Game", ChatColor.WHITE + "You must enter a valid " + ChatColor.GREEN + "ID");
							return true;
						}
						Map map = MapManager.getMap(args[2]);
						if(map == null) {
							M.message(player, "Game", ChatColor.WHITE + "You must enter a valid " + ChatColor.GREEN + "Map");
							return true;
						}
						if(map.inUse()) {
							M.message(player, "Game", ChatColor.GREEN + map.getName() + ChatColor.WHITE + " is already in use");
							return true;
						}
						if(!map.getGameTypes().contains(type)) {
							M.message(player, "Game", ChatColor.GREEN + type.getName() + ChatColor.WHITE + " is not playable on " + ChatColor.GREEN + map.getName());
							return true;
						}
						if(type.equals(GameType.ARCTIC_BRAWL)) {
							
						}
						if(type.equals(GameType.BATTLE_OF_HORSES)) {
							
						}
						if(type.equals(GameType.BOAT_WARS)) {
							
						}
						if(type.equals(GameType.CASTLE_DEFENSE)) {
							
						}
						if(type.equals(GameType.CTF)) {
							CTF game = new CTF(map);
							M.message(player, "Game", ChatColor.WHITE + "You created a game of " + ChatColor.GREEN + "Capture The Flag " + ChatColor.WHITE + "with ID " + ChatColor.GREEN + game.getID()); 
						}
						return true;
					}
				}
			}
		}
		return false;
	}

}
