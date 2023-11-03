package me.loogeh.Hype.World;

import java.util.HashSet;
import java.util.Iterator;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Utility.utilItem;
import me.loogeh.Hype.Utility.utilMath;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class WorldPowerUp {
	
	public static HashSet<Item> powerUps = new HashSet<Item>();
	public static HashSet<Item> marked = new HashSet<Item>(); //items marked for removal
	private static boolean scheduler = false;
	private static int schedulerId;
	
	public static void spawn(WPowerUp type) {
		Location location = new Location(Bukkit.getWorld("world"), utilMath.getRandom(-1000, 1000), 0, utilMath.getRandom(-1000, 1000));
		if(SquadManager.isClaimed(location.getChunk())) return;
		location.setY(location.getWorld().getHighestBlockYAt(location));
		Item item = location.getWorld().dropItem(location, type.getItem());
		item.setMetadata(type.getMetaIdentifier(), new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
		if(!scheduler) {
			scheduler = true;
			setSchedulerId(Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
				
				public void run() {
					Iterator<Item> it = powerUps.iterator();
					while(it.hasNext()) {
						Item item = it.next();
						utilItem.setTicksLived(item, 1);
					}
				}
			}, 1200L, 1200L));
		}
	}
	
	public static void spawn(WPowerUp type, Location spawnLocation) {
		if(SquadManager.isClaimed(spawnLocation.getChunk())) return;
		int y = spawnLocation.getWorld().getHighestBlockYAt(spawnLocation);
		spawnLocation.setY(y);
		Item item = spawnLocation.getWorld().dropItem(spawnLocation, type.getItem());
		item.setMetadata(type.getMetaIdentifier(), new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
		if(!scheduler) {
			scheduler = true;
			setSchedulerId(Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
				
				public void run() {
					Iterator<Item> it = powerUps.iterator();
					while(it.hasNext()) {
						Item item = it.next();
						if(item != null) {
							if(marked.contains(item)) { 
								it.remove();
								item.remove();
							} else {
								utilItem.setTicksLived(item, 1);
							}
							
						}
					}
				}
			}, 1200L, 1200L));
		}
	}
	
	public static int getSchedulerId() {
		return schedulerId;
	}

	public static void setSchedulerId(int schedulerId) {
		WorldPowerUp.schedulerId = schedulerId;
	}

	public enum WPowerUp {
		REGENERATION(new ItemStack(Material.INK_SACK, 1, (short) 1), "power_up_regeneration");
		
		
		private ItemStack item;
		private String metaIdentifier;
		
		WPowerUp(ItemStack item, String metaIdentifier) {
			this.item = item;
			this.metaIdentifier = metaIdentifier;
		}
		
		public ItemStack getItem() {
			return this.item;
		}
		
		public String getMetaIdentifier() {
			return this.metaIdentifier;
		}
	}
}
