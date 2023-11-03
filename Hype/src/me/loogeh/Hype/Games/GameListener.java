package me.loogeh.Hype.Games;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Games.Game.GameType;
import me.loogeh.Hype.Games.GameInventory.GIType;
import me.loogeh.Hype.Games.GameInventory.GameCreator;
import me.loogeh.Hype.Inventory.HInventory;
import me.loogeh.Hype.Inventory.HInventory.HIType;
import me.loogeh.Hype.Inventory.IFlag;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.util;
import me.loogeh.Hype.Utility.utilItem;
import me.loogeh.Hype.Utility.utilPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameListener implements Listener {
	Main plugin;
	
	public GameListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onGameInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Member member = Member.get(player);
		Inventory inv = event.getInventory();
		HInventory h_inv = HInventory.getInventory(inv);
		
		ItemStack item = event.getCurrentItem();
		if(item == null) return;
		String item_name = "";
		if(!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		if(meta.hasDisplayName()) item_name = ChatColor.stripColor(meta.getDisplayName());
		
		if(h_inv != null) {
			if(h_inv.isType(HIType.GAME_TYPE_SELECTOR)) {
				GameType type = GameType.getType(item_name);
				if(type != null) {
					GameInventory match_selector = Game.getGMatchSelector(type);
					match_selector.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
					player.openInventory(match_selector.getInventory());
				}
			}
		}
		if(h_inv instanceof GameInventory) {
			if(member == null) {
				event.setCancelled(true);
				player.closeInventory();
				M.message(player, "Game", ChatColor.WHITE + "Failed to find your player data");
				return;
			}
			GameInventory g_inv = (GameInventory) h_inv;
			GameType type = g_inv.getGameType();
			if(g_inv.getGIType().equals(GIType.MATCH_SELECTOR)) {
				if(item.getType().equals(Material.DIAMOND_BLOCK)) {
					Integer id = util.getInteger(item_name);
					if(id == null) return;
					Game game = Game.getGame(id);
					if(game == null) {
						HInventory match_selector = Game.getMatchSelector(g_inv.getGameType());
						match_selector.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
						player.getOpenInventory().getTopInventory().setContents(g_inv.getInventory().getContents());
						event.setCancelled(true);
					}
				}
				if(item.getType().equals(Material.BOOK_AND_QUILL)) {
					GameCreator creator = new GameCreator(type);
					creator.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
					player.closeInventory();
					player.openInventory(creator.getInventory());
					event.setCancelled(true);
				}
			}
			if(g_inv.getGIType().equals(GIType.KIT_SELECTOR)) {
				if(utilItem.isArmour(ClassType.LEATHER, item.getType())) {
					utilPlayer.setSet(player, ClassType.LEATHER);
					//open ability selector
				} else if(utilItem.isArmour(ClassType.GOLD, item.getType())) {
					utilPlayer.setSet(player, ClassType.GOLD);
					//open ability selector
				} else if(utilItem.isArmour(ClassType.CHAIN, item.getType())) {
					utilPlayer.setSet(player, ClassType.CHAIN);
					//open ability selector
				} else if(utilItem.isArmour(ClassType.IRON, item.getType())) {
					utilPlayer.setSet(player, ClassType.IRON);
					//open ability selector
				} else if(utilItem.isArmour(ClassType.DIAMOND, item.getType())) {
					utilPlayer.setSet(player, ClassType.DIAMOND);
					//open ability selector
				}
			}
			if(g_inv instanceof GameCreator) {
				GameCreator g_create = (GameCreator) g_inv;
				if(g_create.getGameType().equals(GameType.CTF)) {
					if(item.getType().equals(Material.ENCHANTMENT_TABLE)) {
						if(member.getSession().isTagged()) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "You cannot create a " + ChatColor.GREEN + "Game " + ChatColor.WHITE + "while " + ChatColor.GREEN + "Combat Tagged");
							return;
						}
						if(Game.getGame(player) != null) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "You are already in a " + ChatColor.GREEN + "Game");
							return;
						}
						if(Game.getGameCount(g_create.getGameType()) > 19) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "Only " + ChatColor.YELLOW + "20 " + ChatColor.WHITE + "games of " + ChatColor.GREEN + "CTF " + ChatColor.WHITE + "can exist at one time");
							return;
						}
						Map map = g_create.getSelectedMap();
						if(map == null) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "An error occured while creating your game");
							return;
						}
						if(g_create.getInventory().getItem(22) == null) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "An error occured while creating your game");
							return;
						}
						int max_players = g_create.getInventory().getItem(22).getAmount();
						if(g_create.getInventory().getItem(31) == null) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "An error occured while creating your game");
							return;
						}
						boolean powerUps = g_create.getInventory().getItem(31).getType().equals(Material.EMERALD) ? true : false;
						if(g_create.getInventory().getItem(40) == null) {
							player.closeInventory();
							M.message(player, "Game", ChatColor.WHITE + "An error occured while creating your game");
							return;
						}
						int win_score = g_create.getInventory().getItem(40).getAmount();
						CTF ctf = new CTF(map, win_score);
						ctf.setMaxPlayers(max_players);
						ctf.setUsePowerUps(powerUps);
						ctf.join(player);
						player.closeInventory();
						M.message(player, "Game", ChatColor.WHITE + "You created " + ChatColor.GREEN + g_create.getGameType().getName() + " " + ctf.getID());
					}
					if(item.getType().equals(Material.TNT)) {
						player.closeInventory();
						M.message(player, "Game", ChatColor.WHITE + "Creation of a new " + ChatColor.GREEN + g_create.getGameType().getName() + ChatColor.WHITE + " was aborted");
						return;
					}
					if(item.getType().equals(Material.EMERALD_BLOCK)) {
						if(item_name.equals("Increase")) {
							ItemStack player_limit = inv.getItem(22);
							if(player_limit != null) {
								if(player_limit.getAmount() >= g_create.getSelectedMap().getPlayerLimit()) return;
								player_limit.setAmount(player_limit.getAmount() + 1);
							}
						}
						if(item_name.equals("True")) {
							ItemStack player_limit = inv.getItem(31);
							if(player_limit != null) {
								if(player_limit.getType().equals(Material.EMERALD)) return;
								player_limit.setType(Material.EMERALD);
							}
						}
						if(item_name.equals("Increase Score")) {
							ItemStack player_limit = inv.getItem(40);
							if(player_limit != null) {
								if(player_limit.getAmount() >= g_create.getGameType().getMaxWinScore()) return;
								player_limit.setAmount(player_limit.getAmount() + 1);
							}
						}
					}
					if(item.getType().equals(Material.REDSTONE_BLOCK)) {
						if(item_name.equals("Decrease")) {
							ItemStack player_limit = inv.getItem(22);
							if(player_limit != null) {
								if(player_limit.getAmount() <= g_create.getSelectedMap().getMinimumPlayers()) return;
								player_limit.setAmount(player_limit.getAmount() - 1);
							}
						}
						if(item_name.equals("False")) {
							ItemStack player_limit = inv.getItem(31);
							if(player_limit != null) {
								if(player_limit.getType().equals(Material.REDSTONE)) return;
								player_limit.setType(Material.REDSTONE);
							}
						}
						if(item_name.equals("Decrease Score")) {
							ItemStack player_limit = inv.getItem(40);
							if(player_limit != null) {
								if(player_limit.getAmount() <= g_create.getGameType().getMinWinScore()) return;
								player_limit.setAmount(player_limit.getAmount() - 1);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onGameCreatureSpawn(CreatureSpawnEvent event) {
		if(event.getLocation().getWorld().getName().equalsIgnoreCase("game_world")) {
			if(event.getSpawnReason().equals(SpawnReason.CUSTOM)) event.setCancelled(false);
			else event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onGameChunkUnload(ChunkUnloadEvent event) {
		Chunk chunk = event.getChunk();
		if(chunk.getWorld().getName().equalsIgnoreCase("game_world")) event.setCancelled(true);
	}
}
