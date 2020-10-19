package me.loogeh.Hype.Region;

import java.util.HashSet;

import org.bukkit.Location;

public class SphereRegion extends Region {

	public Location centerPoint;
	public double radius = 0.0D;
	
	public SphereRegion(Location centerPoint, double radius) {
		this.centerPoint = centerPoint;
		this.radius = radius;
	}
	
	public Location getCenter() {
		return this.centerPoint;
	}
	
	public boolean in(Location location) {
		double cx = centerPoint.getX();
		double cy = centerPoint.getY();
		double cz = centerPoint.getZ();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double d = ((cx - x) * (cx - x)) + ((cy - y) * (cy - y)) + ((cz - z) * (cz - z));
		return d < radius * radius;
	}
	
	public boolean outskirts(Location location) {
		return true;
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
