package me.loogeh.Hype.Region;

import java.util.HashSet;

import org.bukkit.Location;

public class CuboidRegion extends Region {
	
	public Location upperCorner = null;
	public Location lowerCorner = null;

	public CuboidRegion(Location upperCorner, Location lowerCorner) {
		if(!upperCorner.getWorld().getName().equalsIgnoreCase(lowerCorner.getWorld().getName())) {
			throw new IllegalArgumentException("Worlds must be the same");
		}
		if(upperCorner.getY() > lowerCorner.getY()) {
			this.upperCorner = upperCorner;
			this.lowerCorner = lowerCorner;
		}
		else {
			this.upperCorner = lowerCorner;
			this.lowerCorner = upperCorner;
		}
	}
	
	public Location getUpperCorner() {
		return upperCorner;
	}
	
	public Location getLowerCorner() {
		return lowerCorner;
	}
	
	public boolean in(Location location) {
		return location.getWorld().getName().equalsIgnoreCase(upperCorner.getWorld().getName()) &&
				(location.getX() > lowerCorner.getX() && location.getX() < upperCorner.getX()) &&
				(location.getY() > lowerCorner.getY() && location.getY() < upperCorner.getY()) &&
				(location.getZ() > lowerCorner.getZ() && location.getZ() < upperCorner.getZ());
	}
	
	public boolean outskirts(Location location) {
		if((location.getBlockX() > upperCorner.getBlockX() - checkDistance
				|| location.getBlockX() < upperCorner.getBlockX() + checkDistance)
				|| (location.getBlockZ() > upperCorner.getBlockZ() - checkDistance
				|| location.getBlockZ() < upperCorner.getBlockZ() + checkDistance)
				|| (location.getBlockX() > lowerCorner.getBlockX() - checkDistance
				|| location.getBlockX() < lowerCorner.getBlockX() + checkDistance)
				|| (location.getBlockZ() > lowerCorner.getBlockZ() - checkDistance
				|| location.getBlockZ() < lowerCorner.getBlockZ() + checkDistance)) return true;
		return false;
	}

	public void addFlag(RFlag flag) {
		if(!regionFlags.contains(flag)) regionFlags.add(flag);
	}

	public boolean removeFlag(RFlag flag) {
		return regionFlags.remove(flag);
	}

	public boolean hasFlag(RFlag flag) {
		return regionFlags.contains(flag);
	}

	public HashSet<RFlag> getFlags() {
		return regionFlags;
	}

}
