package me.loogeh.Hype.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.loogeh.Hype.Games.Game.GameType;
import me.loogeh.Hype.Games.Game.Team;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Region.CuboidRegion;
import me.loogeh.Hype.Region.CylinderRegion;
import me.loogeh.Hype.Region.RFlag;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class MapManager {

	public static HashMap<String, Map> loadedMaps = new HashMap<String, Map>();
	
	public static boolean inUse(Map map) {
		return false;
	}
	
	public static Game getGame(Map map) {
		return null;
	}
	
	public static void load() {
		World world = Bukkit.getWorld("game_world");
		if(world == null) {
			Main.logger.log(Level.SEVERE, "Sector > 'map manager' failed to load maps because 'game_world' is null");
			return;
		}
		Map towers = new Map("Towers", null, null, new Location(world, 850.5, 48.2, -371.5, 90.0F, 0.0F), 20, 2, new CylinderRegion(new Location(world, 796.5D, 13.0D, -364.5), 102.0D));
		towers.getRegion().addFlag(RFlag.DISABLE_ENTER);
		towers.getRegion().addFlag(RFlag.DISABLE_LEAVE);
		towers.getObjectLocations().put("red_flag", new Location(world, 850.5, 30, -371.5));
		towers.getObjectLocations().put("blue_flag", new Location(world, 719.5, 30, -371.5));
		towers.addGameType(GameType.CTF);
		towers.getSpawns().put(GameType.CTF, new HashMap<Game.Team, Location>());
		towers.getSpawns(GameType.CTF).put(Team.RED, new Location(world, 850.5, 48.2, -371.5, 90.0F, 0.0F));
		towers.getSpawns(GameType.CTF).put(Team.BLUE, new Location(world, 719.5, 49.2, -371.5, -90.0F, 0.0F));
		Map hq = new Map("Headquarters", new Location(world, 696, 0, -485), new Location(world, 789, 10000, -624), null, 20, 2, new CuboidRegion(null, null));
		hq.setRegion(new CuboidRegion(hq.getAreaOne(), hq.getAreaTwo()));
		hq.getObjectLocations().put("hq1", new Location(world, 709, 25, -504)); //find region with location.add(5, 0, 5) and location.add(-5, 0, -5)
		hq.getMapRegions().put("hq1", new CuboidRegion(new Location(world, 714, 24, -509), new Location(world, 704, 30, -499)));
		hq.addGameType(GameType.HEADQUARTERS);
		loadedMaps.put(towers.getName().toLowerCase(), towers);
		loadedMaps.put(hq.getName().toLowerCase(), hq);
	}
	
	public static Map getMap(String name) {
		if(loadedMaps.containsKey(name.toLowerCase())) return loadedMaps.get(name.toLowerCase());
		return null;
	}
	
	public static List<Map> getMaps(GameType type) {
		List<Map> list = new ArrayList<Map>();
		for(Entry<String, Map> entry : loadedMaps.entrySet()) {
			if(entry.getValue().getGameTypes().contains(type)) list.add(entry.getValue());
		}
		return list;
	}
	
}
