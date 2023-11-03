package me.loogeh.Hype.Region;

import java.util.HashSet;

import org.bukkit.Location;

public class CylinderRegion extends EllipsoidRegion { //test EllipsoidRegion

	public Location centerPoint;
	public double radius = 0.0D;
	
	public CylinderRegion(Location centerPoint, double radius) {
		super(centerPoint, radius, 0, radius);
		this.centerPoint = centerPoint;
		this.radius = radius;
	}
	
	public Location getCenter() {
		return this.centerPoint;
	}
	
	public boolean in(Location location) {
		double cx = centerPoint.getX();
		double cz = centerPoint.getZ();
		double x = location.getX();
		double z = location.getZ();
		double d = ((cx - x) * (cx - x)) + ((cz - z) * (cz - z));
		return d < radius * radius;
	}
	
	public boolean outskirts(Location location) {
		double cx = centerPoint.getX();
		double cz = centerPoint.getZ();
		double x = location.getX();
		double z = location.getZ();
		double d = ((cx - x) * (cx - x)) + ((cz - z) * (cz - z));
		double diff = Math.max(d, radius * radius) - Math.min(d, radius * radius);
		return diff < 50;
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
