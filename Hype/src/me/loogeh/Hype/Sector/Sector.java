package me.loogeh.Hype.Sector;

import java.util.HashMap;
import java.util.logging.Level;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Sector implements Listener {
	
	private static HashMap<String, Sector> sectorList = new HashMap<String, Sector>();
	
	private String name = "Sector";
	private JavaPlugin plugin;
	private boolean enabled = false;
	private boolean registered = false;
	
	
	public Sector(String name, JavaPlugin plugin) {
		this.name = name;
		this.plugin = plugin;
	}
	
	public Sector(String name) {
		this.name = name;
		this.plugin = Main.get();
	}
	
	public String getName() {
		return this.name;
	}
	
	public JavaPlugin getPlugin() {
		return this.plugin;
	}
	
	public boolean getEnabled() {
		return this.enabled;
	}
	
	public boolean getRegistered() {
		return this.registered;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	private void setRegistered() {
		this.registered = true;
	}
	
	public void message(Player player, String message) {
		M.message(player, getName(), ChatColor.WHITE + message);
	}
	
	public void broadcast(String message) {
		M.broadcast(getName(), message);
	}
	
	public abstract void load();
	
	public void initiate(boolean add) {
		long start = System.currentTimeMillis();
		Main.logger.log(Level.INFO, "Sector > Enabling '" + getName() + "'");
		load();
		this.enabled = true;
		if(add) sectorList.put(getName(), this);
		Main.logger.log(Level.INFO, "Sector > '" + getName() + "' enabled in " + (System.currentTimeMillis() - start) + " milliseconds");
	}
	
	public void register() {
		if(getRegistered()) return;
		getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
		setRegistered();
	}

	public static void addSector(Sector sector) {
		sectorList.put(sector.getName(), sector);
	}
	
	public static void addSector(String name, Sector sector) {
		sectorList.put(name, sector);
	}
	
	public static void removeSector(String name) {
		sectorList.remove(name);
	}
	
	public static void loadAll() {
		Teleport.get();
		Message.get();
		Animal.get();
		Debug.get();
		Stats.get();
		Permissions.get();
		Settings.get();
		Invisible.get();
		Economy.get();
	}
	
}
