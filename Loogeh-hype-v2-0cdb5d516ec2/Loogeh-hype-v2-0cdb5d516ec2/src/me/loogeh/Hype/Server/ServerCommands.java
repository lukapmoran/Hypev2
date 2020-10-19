package me.loogeh.Hype.Server;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Item.ItemBuilder;
import me.loogeh.Hype.Item.MetaBuilder;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Moderation.Permissions.Ranks;
import me.loogeh.Hype.World.Boss.BossType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "You cannot use server commands from the console");
			return true;
		}
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("server")) {
			if(Permissions.getRank(player.getName()) != Ranks.OWNER) {
				M.sendLackPermsMessage(player);
				return true;
			}
			if(args.length == 0) {
				M.message(player, "Help", ChatColor.WHITE + "Server Help");
				M.sendHelpMessage(player, "/server lock toggle", "Toggles the server lock");
				M.sendHelpMessage(player, "/server lock add <player>", "Adds a player to the lock-exempt list");
				M.sendHelpMessage(player, "/server chat <enable/disable>", "Sets chat status");
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("chat")) {
					player.sendMessage(M.getUsage("/server chat <enable/disable>"));
					return true;
				}
				if(args[0].equalsIgnoreCase("lock")) {
					player.sendMessage(M.getUsage("/server lock toggle"));
					player.sendMessage(M.getUsage("/server lock add <player>"));
					return true;
				}
				if(args[0].equalsIgnoreCase("metabuilder")) {
					ItemBuilder ibuilder = new ItemBuilder(Material.DIRT).addDurability(2);
					ItemStack item = ibuilder.toItemStack();
					MetaBuilder mbuilder = new MetaBuilder(-1, 35, true).withName(C.boldAqua + "Podzol").withColor(C.boldYellow).withColor(C.Gray).withLore("Granite is a common type of intrusive, felsic, igneous rock which is granular and phaneritic in texture. It consists of mainly quartz and mica");
					ItemMeta cur_meta = item.getItemMeta();
					ItemMeta new_meta = mbuilder.toItemMeta(cur_meta);
					item.setItemMeta(new_meta);
					player.getInventory().addItem(item);
				}
			}
//			if(args.length == 2) {
//				if(args[0].equalsIgnoreCase("chat")) {
//					if(args[1].equalsIgnoreCase("enable")) {
//						if(Moderation.chat) {
//							M.message(player, "Chat", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "is already " + ChatColor.YELLOW + "Enabled");
//							return true;
//						}
//						Moderation.chat = true;
//						M.broadcast("Chat", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "has been " + ChatColor.YELLOW + "Enabled");
//						return true;
//					} else if(args[1].equalsIgnoreCase("disable")) {
//						if(!Moderation.chat) {
//							M.message(player, "Chat", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "is already " + ChatColor.YELLOW + "Disabled");
//							return true;
//						}
//						Moderation.chat = false;
//						M.broadcast("Chat", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "has been " + ChatColor.YELLOW + "Disabled");
//						return true;
//					} else {
//						player.sendMessage(M.getUsage("/chat <enable/disable>"));
//						return true;
//					}
//				}
				if(args[0].equalsIgnoreCase("lock")) {
					if(Main.plugin.getServer().hasWhitelist()) {
						Main.plugin.getServer().setWhitelist(false);
						Main.config.set("server.lock_reason", null);
						M.broadcast("Server", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " unlocked the server");
					} else {
						M.message(player, "Server", ChatColor.WHITE + "The server isn't locked");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("set")) {
					if(args[1].equalsIgnoreCase("spawn")) {
						Location location = player.getLocation();
						player.getWorld().setSpawnLocation((int) location.getX(), (int) location.getY(), (int) location.getZ());
						M.message(player, "Server", ChatColor.WHITE + "You set the spawn to your " + ChatColor.YELLOW + "Location");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("boss")) {
					if(args[1].equalsIgnoreCase("slime")) {
						BossType.SLIME.spawn(player.getLocation());
					}
				}
			}
			if(args.length > 2) {
				if(args[0].equalsIgnoreCase("lock")) {
					if(args[1].equalsIgnoreCase("toggle")) {
//						Moderation.lock(player, util.join(2, args));
					}
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("lock")) {
					if(args[1].equalsIgnoreCase("add")) {
						String target = args[2];
						OfflinePlayer p_target = Bukkit.getOfflinePlayer(target);
						p_target.setWhitelisted(true);
						M.message(player, "Server", ChatColor.WHITE + "You added " + ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " to the whitelist");
						Main.plugin.getServer().reloadWhitelist();
						return true;
					}
				}
			}
//		}
//		if(commandLabel.equalsIgnoreCase("give") || commandLabel.equalsIgnoreCase("g")) {
//			if(!Permissions.isAdmin(player.getName())) {
//				M.sendLackPermsMessage(player);
//				return true;
//			}
//			if(args.length == 0) {
//				M.sendHelpMessage(player, "/give <player> <item> (amount)", "Give a target player an item");
//				M.sendHelpMessage(player, "/give <item> (amount)", "Give yourself an item");
//				M.sendHelpMessage(player, "/give all <item> (amount)", "Give all online players an item");
//				return true;
//			}
//			if(args.length == 1) {
//				Item item = Item.getItem(args[0]);
//				if(item.equals(Item.NULL)) {
//					M.message(player, "Give", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
//					return true;
//				}
//				if(player.getInventory().firstEmpty() == -1) {
//					player.getWorld().dropItem(player.getLocation(), item.getItem());
//					M.message(player, "Give", ChatColor.WHITE + "You gave yourself " + ChatColor.YELLOW + "1 " + item.getName());
//					return true;
//				}
//				player.getInventory().addItem(item.getItem());
//				M.message(player, "Give", ChatColor.WHITE + "You gave yourself " + ChatColor.YELLOW + "1 " + item.getName());
//			}
//		}
		return false;
	}

}
