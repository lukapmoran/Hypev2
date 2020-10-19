package me.loogeh.Hype.Miscellaneous;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class VortexEntity {

//	private Entity entity = null;
//	private boolean remove = false;
	
//	private float verticalTicker = 0.0F;
//	private float horizontalTicker = (float) (Math.random() * 2 * Math.PI);
	
	public VortexEntity(Entity entity, Location location) {
		
	}
	
	public abstract void spawn();
	
}
