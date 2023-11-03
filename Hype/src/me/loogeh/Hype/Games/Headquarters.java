package me.loogeh.Hype.Games;

import java.util.UUID;

import me.loogeh.Hype.Event.RegionEnterEvent;
import me.loogeh.Hype.Games.Game.GameType;
import me.loogeh.Hype.Games.Game.Team;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Region.Region;
import me.loogeh.Hype.Utility.utilPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Headquarters extends Game {

	private long lastRotation = 0L;
	private String currentHeadquarter = "";
	private int currentHQID = 1;
	private long rotationDuration = 60000L;
	private int checkerID = -1;

	public Headquarters(Map map, Location lobby_loc, int win_score) {
		super(map, GameType.HEADQUARTERS, lobby_loc);
		super.setWinScore(win_score);
		super.getTeamScores().put(Team.RED, 0);
		super.getTeamScores().put(Team.BLUE, 0); 
	}

	public Headquarters(Map map, int win_score) {
		super(map, GameType.HEADQUARTERS, map.getLobbyLocation());
		super.setWinScore(win_score);
		super.getTeamScores().put(Team.RED, 0);
		super.getTeamScores().put(Team.BLUE, 0); 
	}

	public long getLastRotation() {
		return lastRotation;
	}

	public void setLastRotation(long lastRotation) {
		this.lastRotation = lastRotation;
	}

	public String getCurrentHeadquarter() {
		return currentHeadquarter;
	}

	public void setCurrentHeadquarter(String currentHeadquarter) {
		this.currentHeadquarter = currentHeadquarter;
	}

	public long getRotationDuration() {
		return rotationDuration;
	}

	public void setRotationDuration(long rotationDuration) {
		this.rotationDuration = rotationDuration;
	}

	public void start() {
		Location redspawn = this.getMap().getSpawns().get(GameType.CTF).get(Team.RED);
		Location bluespawn = this.getMap().getSpawns().get(GameType.CTF).get(Team.BLUE);
		this.setStatus(GameStatus.PLAYING);
		for(UUID key : getPlayers().keySet()) {
			Player player = Bukkit.getPlayer(key);
			if(player == null) {
				getPlayers().remove(key);
				continue;
			}
			Team team = getPlayers().get(key);
			player.teleport(team.equals(Team.RED) ? redspawn : bluespawn);
			player.setAllowFlight(false);
			player.setFallDistance(0.0F);
			player.setGameMode(GameMode.SURVIVAL);
			player.setCanPickupItems(true);
			player.setNoDamageTicks(0);
			player.setFireTicks(0);
			player.setExhaustion(0.0F);
			utilPlayer.heal(player, false);
		}
		this.setLastRotation(System.currentTimeMillis() - (rotationDuration - 10000L));
	}

	public void join(Player player) {
		
	}

	public void leave(Player player) {
		
	}

	public void lose(Player player) {
		
	}

	public void end() {
		
	}
	
	public void checker() {
		if(checkerID == -1) {
			checkerID = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), new Runnable() {
				
				public void run() {
					if(System.currentTimeMillis() - lastRotation > 60000L) {
						setLastRotation(System.currentTimeMillis());
					}
				}
			}, 20L, 20L);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(!getPlayers().containsKey(player.getUniqueId())) return;
		if(player.getItemInHand().getType().equals(Material.COMPASS)) event.setCancelled(true);
	}
	
	@EventHandler
	public void onRegionEnter(RegionEnterEvent event) {
		Player player = event.getPlayer();
		Region region = event.getRegion();
		if(getMap().getMapRegions().containsValue(region)) {
			 
			event.setMessage(null);
		}
	}

}
