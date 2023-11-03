package me.loogeh.Hype.Region;

import java.util.HashSet;

import org.bukkit.Location;

public abstract class Region {
	
	public String name = "Region";
	
	public HashSet<RFlag> regionFlags = new HashSet<RFlag>();
	
	public String enterFormat = "&2&lRegion - &fEnter &e%regionname%";
	public String leaveFormat = "&2&lRegion - &fLeave &e%regionname%";;
	
	public double checkDistance = 1.5D;
	
	public abstract boolean in(Location location);
	
	public abstract boolean outskirts(Location location);

	public void addFlag(RFlag flag) {
		if(!regionFlags.contains(flag)) regionFlags.add(flag);
	}
	
	public boolean removeFlag(RFlag flag) {
		return this.regionFlags.remove(flag);
	}
	
	public boolean hasFlag(RFlag flag) {
		return this.regionFlags.contains(flag);
	}
	
	public HashSet<RFlag> getFlags() {
		return this.regionFlags;
	}
	
	public void add() {
		RegionManager.addRegion(this);
	}
	
}
