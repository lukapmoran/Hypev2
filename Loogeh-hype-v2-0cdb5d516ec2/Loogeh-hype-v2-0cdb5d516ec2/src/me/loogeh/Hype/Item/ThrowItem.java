package me.loogeh.Hype.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilItem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public abstract class ThrowItem {

	public static HashSet<ThrowItem> thrownItems = new HashSet<ThrowItem>();

	private String shooter;
	private Item item;
	private Location startLocation;
	private Vector direction;
	private long throwTime = System.currentTimeMillis();
	private double damage;
	private List<CollisionType> collisionTypes = new ArrayList<CollisionType>();

	public ThrowItem(Player shooter, ItemStack itemstack, Location startLocation, Vector direction, double damage, List<CollisionType> collisionTypes) {
		this.shooter = shooter.getName();
		this.item = startLocation.getWorld().dropItem(startLocation.add(direction), itemstack);
		this.collisionTypes = collisionTypes;
		this.startLocation = startLocation;
		this.direction = direction;
		this.damage = damage;
		this.item.setMetadata("throw", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
		this.item.setMetadata("damage", new FixedMetadataValue(Main.plugin, Double.valueOf(damage)));
		this.item.setVelocity(direction);
		this.item.setPickupDelay(200);
		thrownItems.add(this);
	}
	
	public String getShooter() {
		return this.shooter;
	}

	public Item getItem() {
		return this.item;
	}

	public Location getStartLocation() {
		return this.startLocation;
	}

	public Vector getDirection() {
		return this.direction;
	}

	public double getDamage() {
		return this.damage;
	}
	
	public abstract void performHit();
	
	public void update() {
		Location location = this.item.getLocation();
		if(System.currentTimeMillis() - this.throwTime < 400) return;
		if(collisionTypes.contains(CollisionType.BLOCK)) {
			if(utilItem.block(location.getBlock().getRelative(BlockFace.UP)) || utilItem.block(location.getBlock().getRelative(BlockFace.NORTH)) || utilItem.block(location.getBlock().getRelative(BlockFace.EAST)) || utilItem.block(location.getBlock().getRelative(BlockFace.SOUTH)) || utilItem.block(location.getBlock().getRelative(BlockFace.WEST))) {
				performHit();
				return;
			}
		}
		if(collisionTypes.contains(CollisionType.BLOCK_DOWN)) {
			Block down = this.item.getLocation().getBlock().getRelative(BlockFace.DOWN);
			if(!down.getType().equals(Material.AIR) && down.getType().isBlock()) {
				performHit();
				return;
			}
		}
		if(collisionTypes.contains(CollisionType.PLAYER)) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(this.getItem().getLocation().toVector().subtract(players.getLocation().toVector()).length() < 1.1D || this.getItem().getLocation().toVector().subtract(players.getEyeLocation().toVector()).length() < 1.1D) {
					performHit();
					break;
				}
			}
		}
	}
	
	public static void startScheduler() {
		Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			
			public void run() {
				Iterator<ThrowItem> it = thrownItems.iterator();
				while(it.hasNext()) {
					ThrowItem item = it.next();
					item.update();
				}
			}
		}, 2L, 2L);
	}
	
	public enum CollisionType {
		BLOCK_DOWN,
		BLOCK,
		PLAYER;
	}
}
