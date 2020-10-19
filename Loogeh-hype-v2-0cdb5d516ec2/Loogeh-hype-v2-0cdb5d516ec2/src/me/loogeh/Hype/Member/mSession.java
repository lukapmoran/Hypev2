package me.loogeh.Hype.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.loogeh.Hype.Player.Damage;
import me.loogeh.Hype.Sector.Sector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class mSession extends Sector {
	
	private boolean bypassMode = false;

	private String lastMessageTarget = null;
	
	private boolean logActive = false;
	private long logStart = 0L;
	
	private long joinTime = System.currentTimeMillis();
	private long leaveTime = 0L;
	private Player leaveData = null;
	private Location leaveLocation = null;
	
	private boolean invErase = false;
	private ItemStack[] invContents = null;
	
	private Location lastDeathLocation = null;
	
	private long lastTag = 0L;
	
	private List<Location> teleportHistory = new ArrayList<Location>();
	private HashMap<String, Long> recentlyDamaged = new HashMap<String, Long>();
	private HashMap<String, Long> recentlyKilled = new HashMap<String, Long>();
	private List<Damage> damageList = new ArrayList<Damage>();
	
	public mSession() {
		super("Session");
	}
	
	public boolean isBypassMode() {
		return bypassMode;
	}
	
	public void setBypassMode(boolean bypassMode) {
		this.bypassMode = bypassMode;
	}

	public String getLastMessageTarget() {
		return lastMessageTarget;
	}

	public void setLastMessageTarget(String lastMessageTarget) {
		this.lastMessageTarget = lastMessageTarget;
	}

	public boolean isLogActive() {
		return logActive;
	}

	public void setLogActive(boolean logActive) {
		this.logActive = logActive;
	}
	
	public long getLogStart() {
		return logStart;
	}
	
	public void setLogStart(long logStart) {
		this.logStart = logStart;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Player getLeaveData() {
		return leaveData;
	}

	public void setLeaveData(Player leaveData) {
		this.leaveData = leaveData;
	}

	public Location getLeaveLocation() {
		return leaveLocation;
	}

	public void setLeaveLocation(Location leaveLocation) {
		this.leaveLocation = leaveLocation;
	}

	public boolean isInvErase() {
		return invErase;
	}

	public void setInvErase(boolean invErase) {
		this.invErase = invErase;
	}

	public ItemStack[] getInvContents() {
		return invContents;
	}

	public void setInvContents(ItemStack[] invContents) {
		this.invContents = invContents;
	}

	public Location getLastDeathLocation() {
		return lastDeathLocation;
	}

	public void setLastDeathLocation(Location lastDeathLocation) {
		this.lastDeathLocation = lastDeathLocation;
	}

	public List<Location> getTeleportHistory() {
		return teleportHistory;
	}

	public void setTeleportHistory(List<Location> teleportHistory) {
		this.teleportHistory = teleportHistory;
	}
	
	public void addTeleportHistory(Location location) {
		teleportHistory.add(location);
	}
	
	public Location getTeleport(int index) {
		int size = getTeleportHistory().size();
		if(size - index < 0) return null;
		if(getTeleportHistory().get(size - index) == null) return null;
		return getTeleportHistory().get(size - index);
	}

	public long getLastTag() {
		return lastTag;
	}

	public void setLastTag(long lastTag) {
		this.lastTag = lastTag;
	}
	
	public boolean isTagged() {
		if(this.lastTag != 0L) lastTag = System.currentTimeMillis() > (this.lastTag + 50000L) ? 0L : this.lastTag;
		return this.lastTag != 0L;
	}

	public HashMap<String, Long> getRecentlyDamaged() {
		return recentlyDamaged;
	}

	public void setRecentlyDamaged(HashMap<String, Long> recentlyDamaged) {
		this.recentlyDamaged = recentlyDamaged;
	}
	
	public void addRecentlyDamaged(String damaged) {
		this.recentlyDamaged.put(damaged, System.currentTimeMillis());
		int iter = this.recentlyDamaged.size() > 20 ? 20 : this.recentlyDamaged.size() - 1;
		for(Entry<String, Long> entry : getRecentlyDamaged().entrySet()) {
			if(System.currentTimeMillis() - entry.getValue() > 20000L) this.recentlyDamaged.remove(entry.getKey());
			if(iter < 1) break;
			iter--;
		}
	}

	public HashMap<String, Long> getRecentlyKilled() {
		return recentlyKilled;
	}

	public void setRecentlyKilled(HashMap<String, Long> recentlyKilled) {
		this.recentlyKilled = recentlyKilled;
	}

	public void addRecentlyKilled(String killed) {
		this.recentlyKilled.put(killed, System.currentTimeMillis());
		int iter = this.recentlyKilled.size() > 20 ? 20 : this.recentlyKilled.size() - 1;
		for(Entry<String, Long> entry : getRecentlyKilled().entrySet()) {
			if(System.currentTimeMillis() - entry.getValue() > 20000L) this.recentlyKilled.remove(entry.getKey());
			if(iter < 1) break;
			iter--;
		}
	}
	
	public List<Damage> getDamageList() {
		return damageList;
	}

	public void setDamageList(List<Damage> damageList) {
		this.damageList = damageList;
	}
	
	public void addDamage(Damage damage) {
		this.damageList.add(damage);
	}

	public void load() {
		
	}

}
