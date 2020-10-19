package me.loogeh.Hype.Shops;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Shops.Shop.ShopType;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopsCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel,String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "Shops commands cannot be used by the console");
			return true;
		}
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("shops")) {
			if(!Permissions.isAdmin(player.getName())) {
				M.sendLackPermsMessage(player);
				return true;
			}
			if(args.length == 0) {
				
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("remove")) {
					String name = args[1];
					Shop.remove(player, name);
					return true;
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("open")) {
					if(args[1].equalsIgnoreCase("weaponry")) {
						Shop shop = Shop.getShop("weaponry");
						if(shop == null) {
							M.message(player, "Shops", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + "Weaponry");
							return true;
						}
						shop.open(player);
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("add")) {
					String name = args[2];
					ShopType type = ShopType.getType(args[1]);
					if(type == null) {
						M.message(player, "Shops", ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Shop Type");
						return true;
					}
					Shop.add(player, name, type);
					return true;
				}
				if(args[0].equalsIgnoreCase("remove")) {
					String name = args[1];
					Shop.remove(player, name);
					return true;
				}
			}
			if(args.length > 2) {
				if(args[0].equalsIgnoreCase("add")) {
					String name = util.join(2, args);
					ShopType type = ShopType.getType(args[1]);
					if(type == null) {
						M.message(player, "Shops", ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Shop Type");
						return true;
					}
					Shop.add(player, name, type);
					return true;
				}
				if(args[0].equalsIgnoreCase("remove")) {
					String name = util.join(1, args);
					Shop.remove(player, name);
					return true;
				}
			}
		}
		return false;
	}
	

}
