package me.loogeh.Hype.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.loogeh.Hype.Games.Game.GameType;
import me.loogeh.Hype.Games.Game.Team;
import me.loogeh.Hype.Region.Region;

import org.bukkit.Location;

public class Map {
	
	private String name;
	private Location area_1;
	private Location area_2;
	private List<GameType> playable_games = new ArrayList<GameType>();
	private HashMap<GameType, HashMap<Team, Location>> spawns = new HashMap<GameType, HashMap<Team, Location>>();
	//private ArrayList<Location> ffa_spawns = new ArrayList<Location>();
	private int player_limit = 20;
	private int minimum_players = 2;
	private Location lobby_location;
	private HashMap<String, Location> object_locations = new HashMap<String, Location>(); //e.g ctf_flag_1 - 143,64,1432
	private HashMap<String, Region> mapRegions = new HashMap<String, Region>(); //e.g hq1
	private Region region;
	
	public Map(String name, Location area_1, Location area_2, Location lobby_location, int player_limit, int minimum_players, Region region) {
		this.name = name;
		this.area_1 = area_1;
		this.area_2 = area_2;
		this.lobby_location = lobby_location;
		this.player_limit = player_limit;
		this.minimum_players = minimum_players;
		this.region = region;
		this.region.add();
	}
	
	public String getName() {
		return this.name;
	}
	
	public HashMap<GameType, HashMap<Team, Location>> getSpawns() {
		return this.spawns;
	}
	
	public HashMap<Team, Location> getSpawns(GameType type) {
		return spawns.get(type);
	}
	
	public Location getAreaOne() {
		return this.area_1;
	}
	
	public Location getAreaTwo() {
		return this.area_2;
	}
	
	public List<GameType> getGameTypes() {
		return this.playable_games;
	}
	
	public void addGameType(GameType type) {
		if(!this.playable_games.contains(type)) this.playable_games.add(type);
	}
	
	public Location getLobbyLocation() {
		return this.lobby_location;
	}
	
	public int getPlayerLimit() {
		return this.player_limit;
	}
	
	public int getMinimumPlayers() {
		return this.minimum_players;
	}
	
	public HashMap<String, Location> getObjectLocations() {
		return this.object_locations;
	}
	
	public HashMap<String, Region> getMapRegions() {
		return mapRegions;
	}


	public Region getRegion() {
		return this.region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public boolean inUse() {
		for(Game game: Game.getGames()) {
			if(game == null) continue;
			if(game.getMap() == null) continue;
			if(game.getMap().getName().equalsIgnoreCase(getName())) return true;
		}
		return false;
	}
	
}
