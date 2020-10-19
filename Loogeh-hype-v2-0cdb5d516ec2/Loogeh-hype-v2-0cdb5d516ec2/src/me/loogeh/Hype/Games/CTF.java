package me.loogeh.Hype.Games;

import java.util.HashMap;
import java.util.UUID;

import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Countdown;
import me.loogeh.Hype.Sector.Invisible;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.TeamManager;
import me.loogeh.Hype.Utility.utilPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class CTF extends Game {
	
	public Objective flag_red;
	public Objective flag_blue;
	public Flag flag_r;
	public Flag flag_b;
	
	public CTF(Map map, Location lobby_loc) {
		super(map, GameType.CTF, lobby_loc);
		super.getTeamScores().put(Team.RED, 0);
		super.getTeamScores().put(Team.BLUE, 0);
	}
	
	public CTF(Map map, Location lobby_loc, int win_score) {
		super(map, GameType.CTF, lobby_loc);
		super.setWinScore(win_score);
		super.getTeamScores().put(Team.RED, 0);
		super.getTeamScores().put(Team.BLUE, 0);
	}
	
	public CTF(Map map, int win_score) {
		super(map, GameType.CTF, map.getLobbyLocation());
		super.setWinScore(win_score);
		super.getTeamScores().put(Team.RED, 0);
		super.getTeamScores().put(Team.BLUE, 0);
	}
	
	public CTF(Map map) {
		super(map, GameType.CTF, map.getLobbyLocation());
		super.getTeamScores().put(Team.RED, 0);
		super.getTeamScores().put(Team.BLUE, 0);
	}
	
	public void start() {
		Location redspawn = this.getMap().getSpawns().get(GameType.CTF).get(Team.RED);
		Location bluespawn = this.getMap().getSpawns().get(GameType.CTF).get(Team.BLUE);
		this.setStatus(GameStatus.PLAYING);
		HashMap<UUID, Team> players = this.getPlayers();
		for(UUID key : players.keySet()) {
			Player player = Bukkit.getPlayer(key);
			if(player == null) continue;
			boolean cancel = false;
			if(players.size() < getMap().getMinimumPlayers()) {
				cancel = true;
				player.teleport(getMap().getLobbyLocation().getWorld().getSpawnLocation());
				player.setAllowFlight(false);
				player.setFallDistance(0.0F);
				player.setGameMode(GameMode.SURVIVAL);
				player.setCanPickupItems(true);
				player.setNoDamageTicks(0);
				player.setFireTicks(0);
				M.message(player, "Game", ChatColor.WHITE + "Your game was cancelled due to " + ChatColor.YELLOW + "Insufficient Players");
			}
			if(cancel) {
				this.remove();
				return;
			}
			Team team = players.get(key);
			if(team.equals(Team.RED)) player.teleport(redspawn);
			else player.teleport(bluespawn);
			player.setAllowFlight(false);
			player.setFallDistance(0.0F);
			player.setGameMode(GameMode.SURVIVAL);
			player.setCanPickupItems(true);
			player.setNoDamageTicks(0);
			player.setFireTicks(0);
			player.setExhaustion(0.0F);
			utilPlayer.heal(player, false);
		}
	}

	public void join(Player player) {
		if(player ==  null) return;
		Member member = Member.get(player);
		if(member == null) return;
		if(!this.getStatus().equals(GameStatus.LOBBY)) {
			M.message(player, "Game", ChatColor.GREEN + "Capture The Flag " + ChatColor.WHITE + "is not joinable");
			return;
		}
		if(member.getSession().isTagged()) {
			M.message(player, "Game", ChatColor.WHITE + "You cannot join while " + ChatColor.GREEN + "Combat Tagged");
			return;
		}
		if(Game.getGame(player) != null) {
			M.message(player, "Game", ChatColor.WHITE + "You are already in a " + ChatColor.GREEN + "Game");
			return;
		}
		int balance = member.getEconomy().getBalance();
		if(balance < getJoinCost()) {
			M.message(player, "Game", ChatColor.WHITE + "You have insufficient funds for this game");
			return;
		}
		member.getEconomy().alterBalance(-getJoinCost());
		player.setAllowFlight(true);
		player.setGameMode(GameMode.SURVIVAL);
		this.getPlayers().put(player.getUniqueId(), this.getSmallestTeam());
		player.teleport(getLobbyLocation());
		this.message(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " joined " + ChatColor.GREEN + this.getPlayers().get(player.getName()).getPhoneticName(), true);
		Scoreboard board;
		if(TeamManager.boards.containsKey(player.getName())) board = TeamManager.boards.get(player.getName());
		else board = TeamManager.getNewScoreboard(player);
		registerObjectives(player, board);
		getStatSessions().put(player.getUniqueId().toString(), new CTFStatSession(player.getUniqueId().toString(), this));
		if(this.getPlayers().size() == 1) {
			if(this.countdown == null) {
				this.countdown = new Countdown("CTF", 60, 10) {
					
					@Override
					public void doAction() {
						if(this.seconds == 0) {
							this.setMarked(true);
							start();
							return;
						}
						if(this.seconds < 11) {
							message(ChatColor.WHITE + "Starting in " + ChatColor.GREEN + getSeconds() + " Seconds", true);
						}
						this.seconds--;
					}
				};
			}
		}
	}

	public void leave(Player player) {
		if(player == null) return;
		if(!this.getPlayers().containsKey(player.getName())) return;
		player.teleport(this.getMap().getAreaOne().getWorld().getSpawnLocation());
		this.getPlayers().remove(player.getName());
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		player.getScoreboard().clearSlot(DisplaySlot.BELOW_NAME);
		player.setCanPickupItems(true);
		player.setNoDamageTicks(0);
		if(getPlayers().size() < getMap().getMinimumPlayers()) {
			if(countdown != null) {
				countdown = null;
				message(ChatColor.WHITE + "Countdown cancelled due to insufficient players", true);
			}
		}
	}

	public void lose(Player player) {
		
	}
	
	public void end() {
		
	}
	
	public void die(final Player player, PlayerDeathEvent event) {
		if(player == null) return;
		ClassType set = Armour.getKit(player);
		if(!this.getPlayers().containsKey(player.getName())) return;
		M.message(player, "Game", ChatColor.WHITE + "You will respawn in " + ChatColor.GREEN + "4 Seconds");
		event.setDeathMessage(null);
//		Team team = getTeam(player.getName());
//		if(team != null) message(C.boldGray + "Death > " + team.getChatColor() + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.GREEN + player.getKiller().getName(), true); fix
		player.setHealth(player.getMaxHealth());
		player.getInventory().clear();
		player.setAllowFlight(true);
		player.setCanPickupItems(false);
		player.setNoDamageTicks(1200);
		Invisible.get().add(player, 4000L, true, false);
		final Location team_spawn = this.getMap().getSpawns(GameType.CTF).get(this.getPlayers().get(player.getName()));
		getStats(player.getUniqueId().toString()).addDeaths(set, 1);
		Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			
			public void run() {
				player.setAllowFlight(false);
				player.teleport(team_spawn);
				player.setCanPickupItems(true);
				player.setNoDamageTicks(0);
			}
		}, 80L);
	}
	
	public void onDamageFromEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		
		if(entity instanceof Player) {
			Player player = (Player) entity;
			
			if(damager instanceof Player) {
				Player p_damager = (Player) damager;
				
				Team team = getTeam(p_damager);
				Team p_team = getTeam(player);
				if(team != null && p_team != null) {
					if(team.equals(p_team)) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	public void onItemDrop(PlayerDropItemEvent event) {
	}

	public void onItemPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		Item item = event.getItem();
		if(item.hasMetadata("supplies")) {
			
		}
		if(item.hasMetadata("health_pack")) {
			player.setHealth(player.getMaxHealth());
			M.message(player, "Power Up", ChatColor.WHITE + "You picked up a " + ChatColor.YELLOW + "Health Pack");
		}
	}

	public void onItemBreak(PlayerItemBreakEvent event) {
		
	}

	public void onPlayerDamage(EntityDamageEvent event) {
		
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(true);
	}

	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}
	
	public void capture(Player player) {
		Team team = getTeam(player.getName());
		Team enemy;
		if(team.equals(Team.RED)) enemy = Team.BLUE;
		else enemy = Team.RED;
		addScore(team, 1);
		message(team, ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " captured the " + ChatColor.GREEN + "Flag", true);
		message(enemy, team.getChatColor() + team.getPhoneticName() + ChatColor.WHITE + " captured the " + ChatColor.GREEN + "Flag", true);
	}
	
	public void spawnFlag(Team team, Location location) {
		switch(team){
			case RED:
				flag_r = new Flag(location, Team.RED);
				flag_r.spawn();
			case BLUE:
				flag_b = new Flag(location, Team.BLUE);
				flag_b.spawn();
		}
				
	}
	
	@SuppressWarnings("deprecation")
	private void registerObjectives(Player player, Scoreboard board) {
		Objective obj = board.registerNewObjective("team_info", "dummy");
		Objective team_obj = board.registerNewObjective("team", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(C.boldGreen + "Team Info");
		Team team = getTeam(player.getName());
		if(team != null) {
			team_obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
			team_obj.setDisplayName(team.getChatColor() + team.getPhoneticName());
		}
		Score red_score = obj.getScore(Bukkit.getOfflinePlayer(C.boldRed + "Red " + ChatColor.WHITE));
		if(getScore(Team.RED) != null) red_score.setScore(getScore(Team.RED));
		Score blue_score = obj.getScore(Bukkit.getOfflinePlayer(C.boldBlue + "Blue " + ChatColor.WHITE));
		if(getScore(Team.BLUE) != null) blue_score.setScore(getScore(Team.BLUE));
		player.setScoreboard(board);
	}
	
	private class Flag {
		
		private Slime parent;
		private Location spawnLocation;
		private Team team;
		
		public Flag(Location spawnLocation, Team team) {
			this.parent = (Slime) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.SLIME);
			this.spawnLocation = spawnLocation;
			this.team = team;
			
		}
		
		public void spawn() {
			if(parent == null || spawnLocation == null || team == null) return;
			World world = spawnLocation.getWorld();
			parent.setSize(1);
			parent.setCanPickupItems(false);
			parent.setNoDamageTicks(Integer.MAX_VALUE);
			parent.setRemoveWhenFarAway(false);
			parent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
			ItemStack wool = null;
			if(team.equals(Team.RED)) wool = new ItemStack(Material.WOOL, 1, (short) 14);
			else wool = new ItemStack(Material.WOOL, 1, (short) 11);
			Item wool1 = world.dropItem(spawnLocation, wool);
			wool1.setMetadata("flag", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
			Item wool2 = world.dropItem(spawnLocation, wool);
			wool2.setMetadata("flag", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
			Item wool3 = world.dropItem(spawnLocation, wool);
			wool3.setMetadata("flag", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
			Item wool4 = world.dropItem(spawnLocation, wool);
			wool4.setMetadata("flag", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
			Item wool5 = world.dropItem(spawnLocation, wool);
			wool5.setMetadata("flag", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
			parent.setPassenger(wool1);
			wool1.setPassenger(wool2);
			wool2.setPassenger(wool3);
			wool3.setPassenger(wool4);
			wool4.setPassenger(wool5);
		}
	}
	
	static class CTFStatSession extends StatSession {
		
		int captures = 0;
		int flag_retrieve = 0;
		int flag_returns = 0;
		
		public CTFStatSession(String uuid, Game game) {
			super(uuid, game);
		}
		
		public int getCaptures() {
			return this.captures;
		}
		
		public int getRetrieves() {
			return this.flag_retrieve;
		}
		
		public int getReturns() {
			return this.flag_returns;
		}

		public void save() {
			
		}
		
	}
}
