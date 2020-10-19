package me.loogeh.Hype.Sector;

import java.util.UUID;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Member.mClass;
import me.loogeh.Hype.Member.mEcon;
import me.loogeh.Hype.Member.mPerms;
import me.loogeh.Hype.Member.mSession;
import me.loogeh.Hype.Member.mSettings;
import me.loogeh.Hype.Member.mStats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Member extends Sector {
	
	private String name = "None";
	private String uuid = "None";
	
	private String squad = "None";
	
	private mClass classes;
	private mSession session;
	private mSettings settings;
	private mStats stats;
	private mPerms permissions;
	private mEcon economy;
	
	public Member(String name, String uuid) {
		super("Member");
		this.name = name;
		this.uuid = uuid;
		this.classes = new mClass(this);
		this.session = new mSession();
		this.settings = new mSettings();
		this.stats = new mStats(this);
		this.permissions = new mPerms(this);
		this.economy = new mEcon(this);
		load();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getSquad() {
		return this.squad;
	}
	
	public void setSquad(String squad) {
		this.squad = squad;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(UUID.fromString(getUUID()));
	}

	public mClass getClasses() {
		return this.classes;
	}

	public void setClasses(mClass classes) {
		this.classes = classes;
	}

	public mSession getSession() {
		return this.session;
	}

	public void setSession(mSession session) {
		this.session = session;
	}

	public mSettings getSettings() {
		return this.settings;
	}

	public void setSettings(mSettings settings) {
		this.settings = settings;
	}
	
	public mStats getStats() {
		return this.stats;
	}
	
	public void setStats(mStats stats) {
		this.stats = stats;
	}
	
	public mPerms getPermissions() {
		return this.permissions;
	}
	
	public void setPermissions(mPerms permissions) {
		this.permissions = permissions;
	}

	public mEcon getEconomy() {
		return economy;
	}

	public void setEconomy(mEcon economy) {
		this.economy = economy;
	}

	public void load() {
		classes.load();
		session.load();
		settings.load();
		stats.load();
		permissions.load();
		economy.load();
		
		Main.memberManager.add(this);
	}
	
	public static Member get(Player player) {
		if(player == null) return null;
		return Main.memberManager.get(player.getUniqueId().toString());
	}
	
	public static void reload() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			new Member(player.getName(), player.getUniqueId().toString());
		}
	}
}
