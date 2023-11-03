package me.loogeh.Hype.Region;

import org.bukkit.Location;

public class EllipsoidRegion extends Region {
	
	private Location centerPoint;
	private double xRadius;
	private double yRadius;
	private double zRadius;
	
	public EllipsoidRegion(Location centerPoint, double xRadius, double yRadius, double zRadius) {
		this.centerPoint = centerPoint;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.zRadius = zRadius;
	}
	
	public Location getCenterPoint() {
		return this.centerPoint;
	}
	
	public double getRadiusX() {
		return this.xRadius;
	}
	
	public double getRadiusY() {
		return this.yRadius;
	}
	
	public double getRadiusZ() {
		return this.zRadius;
	}

	public boolean in(Location location) {
		double cx = centerPoint.getX();
		double cy = centerPoint.getY();
		double cz = centerPoint.getZ();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double dX = (cx - x) * (cx - x);
		double dY = (cy - y) * (cy - y);
		double dZ = (cz - z) * (cz - z);
		return dX < Math.pow(xRadius, 2) && dY < Math.pow(yRadius, 2) && dZ < Math.pow(zRadius, 2);
	}

	public boolean outskirts(Location location) {
		return false;
	}

}
