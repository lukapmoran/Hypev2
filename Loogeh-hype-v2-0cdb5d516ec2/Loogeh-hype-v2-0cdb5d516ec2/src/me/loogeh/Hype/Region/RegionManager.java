package me.loogeh.Hype.Region;

import java.util.HashSet;

public class RegionManager {
	
	public static HashSet<Region> regionSet = new HashSet<Region>();
	
	public static void addRegion(Region region) {
		regionSet.add(region);
	}
	
	public static boolean removeRegion(Region region) {
		return regionSet.remove(region);
	}
	
	public static HashSet<Region> getRegions() {
		return regionSet;
	}
}
