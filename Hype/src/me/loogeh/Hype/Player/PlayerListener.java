 package me.loogeh.Hype.Player;

import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Armour.KitSelector;
import me.loogeh.Hype.CombatTag.CombatTag;
import me.loogeh.Hype.Event.PlayerBlockEvent;
import me.loogeh.Hype.Event.PlayerEnterWaterEvent;
import me.loogeh.Hype.Event.PlayerLeaveWaterEvent;
import me.loogeh.Hype.Event.PlayerMoveBlockEvent;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Games.Game;
import me.loogeh.Hype.Games.GameInventory;
import me.loogeh.Hype.Games.GameInventory.GIType;
import me.loogeh.Hype.Inventory.IFlag;
import me.loogeh.Hype.Inventory.IType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad.ClaimType;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Utility.utilItem;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {
	Main plugin;

	
	public PlayerListener(Main instance) {
		plugin = instance;
	}
	
//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onPlayerAbilityUse(AbilityUseEvent event) {
//		if(event.isCancelled()) return;
//		Player player = event.getPlayer();
//		HPlayer hplayer = HPlayer.getHPlayer(player);
//		hplayer.getSession().addAbility();
//	}

//	@EventHandler
//	public void onPlayerDeathEvent(PlayerDeathEvent event) {
//		Player player = event.getEntity();
//		event.setDroppedExp(0);
//		HPlayer.getHPlayer(player).setLastDeathLocation(player.getLocation());
//		HPlayer h_player = HPlayer.getHPlayer(player);
//		if(h_player == null) {
//			HypePlayer.setDeathMessage(event, 0);
//			return;
//		}
//		HypePlayer.setDeathMessage(event);
//		h_player.setCloaked(false);
//		h_player.block_count = 0;
//		h_player.lastCharge = 0L;
//		h_player.lastSprint = 0L;
//		h_player.charge = 0;
//		h_player.charging = false;
//		h_player.getRecentKills().clear();
//		h_player.getRecentDamageReasons().clear();
//		h_player.getRecentDamagersMap().clear();
//		h_player.getAbilityUsageMap().clear();
//		h_player.setCombatTag(null);
//		h_player.getSession().addDeath(Armour.getKit(player));
//		if(player.getKiller() != null) {
//			HPlayer hkiller = HPlayer.getHPlayer(player.getKiller());
//			hkiller.getSession().addKill(Armour.getKit(player.getKiller()));
//		}
//		if(!player.getGameMode().equals(GameMode.CREATIVE)) player.setAllowFlight(false);
//	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if(block.getType().equals(Material.ENDER_PORTAL) || block.getType().equals(Material.ENDER_PORTAL_FRAME)) {
				World world = block.getWorld();
				player.teleport(world.getSpawnLocation());
				M.message(player, "Server", ChatColor.WHITE + "You teleported to the spawn of " + ChatColor.YELLOW + WordUtils.capitalize(world.getName().replaceAll("_", " ")));
			}
		}
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if(block.getType() == Material.ENDER_CHEST) event.setCancelled(true);
			else if(block.getType() == Material.ENCHANTMENT_TABLE) {
				event.setCancelled(true);
				if(player.getWorld().getName().equalsIgnoreCase("game_world")) {
					Game game = Game.getGame(player);
					if(game == null) return;
					GameInventory kit_selector = new GameInventory(GIType.KIT_SELECTOR, game.getType(), 6);
					kit_selector.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
					kit_selector.getInventory().setContents(IType.KIT_SELECTOR.getInventory().getContents());
					player.closeInventory();
					player.openInventory(kit_selector.getInventory());
				} else {
					KitSelector kit_selector = new KitSelector();
					kit_selector.open(player);
				}
				return;
			}
			else if(block.getType() == Material.BREWING_STAND || block.getType() == Material.BREWING_STAND_ITEM) event.setCancelled(true);
		}
		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {

		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(Armour.holdingSword(player)) {
				Main.plugin.getServer().getPluginManager().callEvent(new PlayerBlockEvent(player, event.getMaterial(), event.getAction()));
			}
			if(player.getItemInHand().getType().equals(Material.MUSHROOM_SOUP)) {
				if(player.getHealth() < player.getMaxHealth()) {
					player.setItemInHand(null);
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 1));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(C.boldDarkGray + "Join > " + ChatColor.GRAY + player.getName());
	}

	@EventHandler
	public void onPlayerGamemodeToggle(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if(event.getNewGameMode() == GameMode.CREATIVE) player.setAllowFlight(true);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(CombatTag.isTagged(player.getName())) event.setQuitMessage(C.boldDarkGray + "Tagged Quit > " + ChatColor.GRAY + player.getName());
		else event.setQuitMessage(C.boldDarkGray + "Quit > " + ChatColor.GRAY + player.getName());
		if(player.hasPotionEffect(PotionEffectType.SPEED)) {
			for(PotionEffect effect : player.getActivePotionEffects()) {
				if(effect.getDuration() > 10000) player.removePotionEffect(effect.getType());
			}
		}
		if(player.getLocation().getBlock().getType().equals(Material.WATER) || player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)) Main.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveWaterEvent(player));
//		HPlayer h_player = HPlayer.getHPlayer(player);
//		h_player.setCloaked(false);
//		h_player.getSession().save();
//		for(Player players : Bukkit.getOnlinePlayers()) {
//			player.showPlayer(players);
//		}
//		if(h_player.getCombatTag() == null) {
//			if(HPlayer.hPlayers.containsKey(player.getName())) HPlayer.hPlayers.remove(player.getName());
//		}
		player.setAllowFlight(false);
	}

	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		Player player = event.getPlayer();
		Chunk chunk = event.getBlockClicked().getChunk();
		if(!SquadManager.isClaimed(chunk)) return;
		ClaimType type = SquadManager.getClaimType(chunk);
		if(type == ClaimType.SAFE) {
			event.setCancelled(true);
			M.message(player, "Squads", ChatColor.WHITE + "You cannot " + ChatColor.YELLOW + "Fill Buckets " + ChatColor.WHITE + "in a " + ChatColor.YELLOW + "Safe Zone");
			return;
		}
		if(!event.getBlockClicked().getType().equals(Material.WATER) && !event.getBlockClicked().getType().equals(Material.STATIONARY_WATER)) {
			event.setCancelled(true);
			M.message(player, "Server", ChatColor.WHITE + "You may only fill buckets with " + ChatColor.YELLOW + "Water");
		}
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Chunk chunk = event.getBlockClicked().getChunk();
		if(!SquadManager.isClaimed(chunk)) return;
		ClaimType type = SquadManager.getClaimType(chunk);
		if(type == ClaimType.SAFE) {
			event.setCancelled(true);
			M.message(player, "Squads", ChatColor.WHITE + "You cannot " + ChatColor.YELLOW + "Place Buckets " + ChatColor.WHITE + "in a " + ChatColor.YELLOW + "Safe Zone");
			return;
		}
	}

	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent event) {
		event.setExpToDrop(0);
		if(event.getBlock().getType().equals(Material.OBSIDIAN)) {
			event.setCancelled(true);
			event.getBlock().setType(Material.AIR);
		}
	}

	@EventHandler
	public void onPlayerBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if(!Squad.adminMode.contains(player.getName())) {
			if(block.getType() == Material.OBSIDIAN || block.getType() == Material.ENCHANTMENT_TABLE || block.getType() == Material.ENDER_CHEST) {
				M.message(player, "Server", ChatColor.WHITE + "You cannot place " + ChatColor.YELLOW + utilItem.toCommon(block.getType().toString().toLowerCase()));
				event.setCancelled(true);
			} else if(block.getType() == Material.BREWING_STAND_ITEM || block.getType() == Material.BREWING_STAND) {
				M.message(player, "Server", ChatColor.WHITE + "You cannot place " + ChatColor.YELLOW + "Brewing Stand");
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerBrewPotion(BrewEvent event) {
		if(Main.config.getBoolean("server.disable_brewing")) event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		DamageCause cause = event.getCause();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			Member member = Member.get(player);
			if(member == null) return;
			if(cause.equals(DamageCause.BLOCK_EXPLOSION) || cause.equals(DamageCause.ENTITY_EXPLOSION)) member.getSession().addDamage(new Damage("Explosion", null, event.getDamage()));
			if(cause.equals(DamageCause.CONTACT)) member.getSession().addDamage(new Damage("Cactus", null, event.getDamage()));
			if(cause.equals(DamageCause.DROWNING)) member.getSession().addDamage(new Damage("Drowning", null, event.getDamage()));
			if(cause.equals(DamageCause.FALL)) member.getSession().addDamage(new Damage("Falling", null, event.getDamage()));
			if(cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK) || cause.equals(DamageCause.LAVA)) member.getSession().addDamage(new Damage("Combustion", null, event.getDamage()));
			if(cause.equals(DamageCause.LIGHTNING)) member.getSession().addDamage(new Damage("Lightning", null, event.getDamage()));
			if(cause.equals(DamageCause.POISON)) member.getSession().addDamage(new Damage("Poison", null, event.getDamage()));
			if(cause.equals(DamageCause.STARVATION)) member.getSession().addDamage(new Damage("Malnutrition", null, event.getDamage()));
			if(cause.equals(DamageCause.SUFFOCATION)) member.getSession().addDamage(new Damage("Asphyxiation", null, event.getDamage()));
			if(cause.equals(DamageCause.SUICIDE)) member.getSession().addDamage(new Damage("Suicide", null, event.getDamage()));
			if(cause.equals(DamageCause.VOID)) member.getSession().addDamage(new Damage("Void", null, event.getDamage()));
			if(cause.equals(DamageCause.WITHER)) member.getSession().addDamage(new Damage("Wither", null, event.getDamage()));
		}
	}
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location from = event.getFrom();
		Location to = event.getTo();
		int fx = event.getFrom().getBlockX();
		int fz = event.getFrom().getBlockZ();
		int tx = event.getTo().getBlockX();
		int tz = event.getTo().getBlockZ();
		if((fx != tx) || (fz != tz)) Main.plugin.getServer().getPluginManager().callEvent(new PlayerMoveBlockEvent(player, from, to));
	}
	
	@EventHandler
	public void onPlayerMoveBlockEvent(PlayerMoveBlockEvent event) {
		Player player = event.getPlayer();
		Location from = event.getFrom();
		Location to = event.getTo();
		if(from.getBlock().getType() == Material.WATER || from.getBlock().getType() == Material.STATIONARY_WATER  && (to.getBlock().getType() != Material.WATER && to.getBlock().getType() != Material.STATIONARY_WATER)) Main.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveWaterEvent(player));
		else if(to.getBlock().getType() == Material.WATER || to.getBlock().getType() == Material.STATIONARY_WATER  && (from.getBlock().getType() != Material.WATER && from.getBlock().getType() != Material.STATIONARY_WATER)) Main.plugin.getServer().getPluginManager().callEvent(new PlayerEnterWaterEvent(player));
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if(player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER) Main.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveWaterEvent(player));
		if(player.getLocation().getBlock().getType() != Material.WATER && player.getLocation().getBlock().getType() != Material.STATIONARY_WATER) Main.plugin.getServer().getPluginManager().callEvent(new PlayerEnterWaterEvent(player));
	}

	@EventHandler
	public void onPlayerCraftItem(CraftItemEvent event) {
		if(event.getInventory().getResult().getType() == Material.ENCHANTMENT_TABLE || event.getInventory().getResult().getType() == Material.ENDER_CHEST || event.getInventory().getResult().getType() == Material.BREWING_STAND || event.getInventory().getResult().getType() == Material.BREWING_STAND_ITEM) {
			event.setCancelled(true);
		}
	}

//	@EventHandler
//	public void onPlayerTickEvent(TickEvent event) {
//		for(Player players : Bukkit.getOnlinePlayers()) {
//			HPlayer h_player = HPlayer.getHPlayer(players); //periodic prize
//			if(h_player == null) continue;
//			if(event.getType().equals(TickType.MINUTE)) {
//				long lastReward = h_player.lastReward;
//				if(lastReward == 0L) continue;
//				if(System.currentTimeMillis() - lastReward > TimeUnit.MINUTES.toMillis(15)) {
//					h_player.lastReward = System.currentTimeMillis();
//					int prize = 3000;
//					int extra = (h_player.kills * 40) + (h_player.abilities_used * 10) + (h_player.assists * 20);
//					prize += extra;
//					Economy.alterBalance(players, prize);
//					M.message(players, "Periodic Reward", ChatColor.YELLOW + "15 Minute " + ChatColor.WHITE + "periodic reward");
//					M.message(players, "Reward", ChatColor.YELLOW + "$" + prize);
//					M.message(players, "New Balance", ChatColor.YELLOW + "$" + Economy.getBalance(players));
//				}
//			}
//		}
//	}
	
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		event.setAmount(0);
	}
	
	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if(world.getName().equalsIgnoreCase("world"))  M.message(player, "Realm", ChatColor.WHITE + "Entered Realm: " + ChatColor.YELLOW + "Overworld");
		if(world.getName().equals("world_nether")) M.message(player, "Realm", ChatColor.WHITE + "Entered Realm: " + ChatColor.YELLOW + "Underworld");
		if(world.getName().equals("game_world")) M.message(player, "Realm", ChatColor.WHITE + "Entered Realm: " + ChatColor.YELLOW + "Arcade");
	}
}
