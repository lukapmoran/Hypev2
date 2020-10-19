package me.loogeh.Hype.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilWorld;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SeizureSheep {

	public static HashSet<SeizureSheep> seizureSheepSet = new HashSet<SeizureSheep>();

	private String name;
	private Location location;
	private Entity entity;
	private boolean remove = false;
	private static int scheduler = -1;

	public SeizureSheep(String name, Location location) {
		this.name = name;
		this.location = location;
		this.entity = location.getWorld().spawnEntity(location, EntityType.SHEEP);
		Sheep sheep = (Sheep) this.entity;
		sheep.setColor(DyeColor.RED);
		sheep.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
		sheep.setCustomNameVisible(true);
		sheep.setRemoveWhenFarAway(false);
		sheep.setTicksLived(1);
		sheep.setNoDamageTicks(Integer.MAX_VALUE);
		sheep.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10));
		sheep.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -10));
	}

	public String getName() {
		return this.name;
	}

	public Sheep getSheep() {
		return (Sheep) this.entity;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public static DyeColor getNextColour(DyeColor current) {
		if(current == DyeColor.RED) return DyeColor.ORANGE;
		else if(current == DyeColor.ORANGE) return DyeColor.YELLOW;
		else if(current == DyeColor.YELLOW) return DyeColor.GREEN;
		else if(current == DyeColor.GREEN) return DyeColor.LIGHT_BLUE;
		else if(current == DyeColor.LIGHT_BLUE) return DyeColor.BLUE;
		else if(current == DyeColor.BLUE) return DyeColor.PURPLE;
		else return DyeColor.RED;
	}

	public static boolean exists(String name) {
		ResultSet rs = Main.mysql.doQuery("SELECT name FROM entity_load WHERE name='" + name + "' AND type='seizure sheep'");
		try {
			if(rs.next()) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static SeizureSheep getSheep(String name) {
		for(SeizureSheep sheep : seizureSheepSet) {
			if(ChatColor.stripColor(sheep.getName()).equalsIgnoreCase(name)) return sheep;
		}
		return null;
	}

	public void add() {
		if(scheduler != -1) Main.plugin.getServer().getScheduler().cancelTask(scheduler);
		seizureSheepSet.add(this);
		startSeizureSheepScheduler();
		if(!exists(getName())) Main.mysql.doUpdate("INSERT INTO `entity_load` (`name`, `type`, `location`) VALUES ('" + ChatColor.translateAlternateColorCodes('&', getName()) + "','seizure sheep','" + utilWorld.locToStr(getLocation()) + "')");
	}

	public void addSilent() {
		seizureSheepSet.add(this);
		if(!exists(getName())) Main.mysql.doUpdate("INSERT INTO `entity_load` (`name`, `type`, `location`) VALUES ('" + ChatColor.translateAlternateColorCodes('&', getName()) + "','seizure sheep','" + utilWorld.locToStr(getLocation()) + "')");
	}

	public static void load() {
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM entity_load");
		try {
			while(rs.next()) {
				if(rs.getString(2).equalsIgnoreCase("seizure sheep")) {
					SeizureSheep sheep = new SeizureSheep(ChatColor.translateAlternateColorCodes('&', rs.getString(1)), utilWorld.strToLoc(rs.getString(3)));
					sheep.addSilent();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "Entity > Failed to load entities from database");
		}
		startSeizureSheepScheduler();
	}

	public static void stop() {
		if(scheduler != -1) Main.plugin.getServer().getScheduler().cancelTask(scheduler);
		Iterator<SeizureSheep> it = SeizureSheep.seizureSheepSet.iterator();
		while(it.hasNext()) {
			SeizureSheep next = it.next();
			next.getEntity().remove();
			it.remove();
		}
	}

	public static void startSeizureSheepScheduler() {
		SeizureSheep.scheduler = Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {

			@Override
			public void run() {
				Iterator<SeizureSheep> it = SeizureSheep.seizureSheepSet.iterator();
				while(it.hasNext()) {
					SeizureSheep next = it.next();
					if(next.remove) {
						next.getEntity().remove();
						Main.mysql.doUpdate("DELETE FROM entity_load WHERE name='" + next.getName() + "' AND type='seizure sheep'");
						it.remove();
					} else {
						next.getSheep().setTicksLived(1);
						next.getSheep().setSheared(false);
						next.getSheep().setColor(SeizureSheep.getNextColour(next.getSheep().getColor()));
					}
				}
			}
		}, 2L, 2L);
	}

}
