package me.loogeh.Hype.Sector;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.loogeh.Hype.Region.CuboidRegion;
import me.loogeh.Hype.Region.RFlag;
import me.loogeh.Hype.Region.Region;

public class Border extends Sector {

	private HashMap<World, Region> worldRegions = new HashMap<World, Region>();
	
	private Border instance;
	
	public Border() {
		super("Border");
		add(Bukkit.getWorld("world"), new CuboidRegion(new Location(Bukkit.getWorld("world"), -1455.0D, 0.0D, -1300.0D), new Location(Bukkit.getWorld("world"), 1360.0D, 1000000.0D, 1520.0D)), true);
		instance = this;
	}
	
	public Border get() {
		if(instance == null) instance = new Border();
		return instance;
	}
	
	public boolean add(World world, Region region, boolean override) {
		region.addFlag(RFlag.DISABLE_LEAVE);
		region.checkDistance = 5.0D;
		if(override) {
			worldRegions.put(world, region);
			return true;
		}
		if(worldRegions.containsKey(world)) return false;
		worldRegions.put(world, region);
		return true;
	}
	
	public boolean remove(World world) {
		if(!worldRegions.containsKey(world)) return false;
		worldRegions.remove(world);
		return true;
	}


	public void load() {
		
	}
}
