package me.loogeh.Hype.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.AbilityType;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Armour.BuildFactory;
import me.loogeh.Hype.Armour.BuildManager;
import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.CombatTag.TagPlayer;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HPlayer {

	public static HashMap<String, HPlayer> hPlayers = new HashMap<String, HPlayer>();

	private String name = null; //player
	private String uuid = null;
	private long lastRespawn;
	private Location lastDeathLocation = null;
	private long lastJoin = 0L;
	private boolean online;
	private boolean cloak = false;

	private String lastMessageTo = null; //last receiver of this player's message
	private boolean blocking = false;
	public int block_count = 0; //stores current sword blocking count //set to 0 when player leaves
	public long lastBlock = 0L;
	public int charge = 0; //charge for fusillade and power arrow //set to 0 when player leaves
	public long lastCharge = 0L; //set to 0 when player leaves
	public boolean charging = false;
	public boolean charge_ready = false; 
	public long lastSprint = 0L; //set to 0 when player leaves
	public int kills = 0; //set to 0 when player leaves
	public int abilities_used = 0; //set to 0 when player leaves
	public int assists = 0;
	public long lastReward = 0L;
	public long screenShake = 0L;
	public boolean screenShaking = false;
	public long lastCrouch = 0L;
	public int attackCount = 0;
	public long lastAttack = 0L;
	public long lastMove = System.currentTimeMillis();
	private ClassType currentKit = ClassType.NONE;
	public AbilityInfo arrowDrawn = AbilityInfo.NONE;
	
	private ArrayList<Location> teleportHistory = new ArrayList<Location>(); //max size = 10
	private HashMap<String, Long> recentDamagers = new HashMap<String, Long>(); //stores the damager's name and the time of their last damage
	private TagPlayer tag = null;
	private HashMap<AbilityInfo, Long> abilityUsage = new HashMap<AbilityInfo, Long>();
	public HashMap<ClassType, List<AbilityInfo>> unlockedAbilities = new HashMap<ClassType, List<AbilityInfo>>();
	private HashSet<String> recentKills = new HashSet<String>();
	private List<Damage> recentDamageReasons = new ArrayList<Damage>();
	private HashMap<ClassType, Integer> selectedBuilds = new HashMap<ClassType, Integer>();
	
	private BuildManager buildManager;
	private PlayerSession session;
	
	static {
		hPlayers.put("f7adad94-62e8-4e70-b10e-f9506ef26ff4", new HPlayer("Loogeh", "f7adad94-62e8-4e70-b10e-f9506ef26ff4"));
	}

	public HPlayer(String name, String uuid) {
		this.name = name;
		this.uuid = uuid;
		load();
	}
	

	public String getName() {
		return this.name;
	}

	public String getUUID() {
		return this.uuid;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(UUID.fromString(uuid));
	}

	public String getLastMessageTo() {
		return this.lastMessageTo;
	}

	public ArrayList<Location> getTeleportHistory() {
		return this.teleportHistory;
	}

	public HashMap<String, Long> getRecentDamagersMap() {
		return this.recentDamagers;
	}

	public TagPlayer getCombatTag() {
		return this.tag;
	}

	public long getLastRespawn() {
		return this.lastRespawn;
	}

	public Location getLastDeathLocation() {
		return this.lastDeathLocation;
	}

	public int getBlockCount() {
		return this.block_count;
	}

	public boolean isBlocking() {
		return this.blocking;
	}

	public boolean getOnline() {
		return this.online;
	}

	public long getLastJoin() {
		return this.lastJoin;
	}

	public ClassType getCurrentKit() {
		return this.currentKit;
	}
	
	public BuildManager getBuildManager() {
		return this.buildManager;
	}

	public PlayerSession getSession() {
		return this.session;
	}
	
	public void addDamage(Damage damage) {
		if(recentDamageReasons.size() > 30) {
			for(int i = recentDamageReasons.size() - 1; i > 30; i--) {
				recentDamageReasons.remove(i);
			}
		}
		for(int i = 0; i < recentDamageReasons.size() - 1; i++) {
			if(i < 30) {
				if(recentDamageReasons.get(i).getReason().equalsIgnoreCase(damage.getReason())) break;
			} else {
				recentDamageReasons.add(damage);
				break;
			}
		}
	}
	
	public HashSet<String> getRecentKills() {
		return this.recentKills;
	}
	
	public List<Damage> getRecentDamageReasons() {
		return this.recentDamageReasons;
	}

	public void setLastJoin(long lastJoin) {
		this.lastJoin = lastJoin;
	}

	public boolean getCloaked() {
		return this.cloak;
	}

	public int getBuild(ClassType set) {
		return getBuildManager().getSelectedBuild(set);
	}

	public boolean getAbilityUseable(AbilityInfo ability) {
		if(ability.getSet().equals(ClassType.NONE) || ability.getSet().equals(ClassType.EMPTY)) return false;
		if(getBuildManager() == null) return false;
		if(getBuildManager().getBuild(ability.getSet(), getBuild(ability.getSet())) == null) return false;
		return getBuildManager().getBuild(ability.getSet(), getBuild(ability.getSet())).getBuildAbilities().containsValue(ability.getID());
	}

	public long getAbilityUsage(AbilityInfo ability) {
		if(!abilityUsage.containsKey(ability)) return -1L;
		else return abilityUsage.get(ability);
	}
	
	public void removeAbilityUsage(AbilityInfo ability) {
		if(!abilityUsage.containsKey(ability)) return;
		else abilityUsage.remove(ability);
	}
	
	public boolean getAbilityUsageElapsed(AbilityInfo ability, long duration) {
		long lastUsage = getAbilityUsage(ability);
		if(lastUsage == -1L) return true;
		else {
			if(System.currentTimeMillis() - lastUsage > duration) {
				if(abilityUsage.containsKey(ability)) abilityUsage.remove(ability);
				return true;
			}
		}
		return false;
	}
	
	public long getElapsed(AbilityInfo ability) {
		long lastUsage = getAbilityUsage(ability);
		if(lastUsage == -1L) return -1L;
		else return System.currentTimeMillis() - lastUsage;
	}
	
	public HashMap<AbilityInfo, Long> getAbilityUsageMap() {
		return this.abilityUsage;
	}
	public HashMap<ClassType, Integer> getSelectedBuilds() {
		return this.selectedBuilds;
	}
	
	public boolean getBuildSelected(ClassType set, int build) {
		return this.selectedBuilds.get(set) == build;
	}

	public Location getLastTeleport() {
		if(teleportHistory.isEmpty()) return null;
		return teleportHistory.get(0);
	}

	public Location getTeleport(int index) {
		if(teleportHistory.isEmpty()) return null;
		if(teleportHistory.size() - 1 < index) return null;
		return teleportHistory.get(index);
	}

	public int getRecentDamagersCount() { //only use upon death to avoid potential ConcurrentModificationException
		if(recentDamageReasons.isEmpty()) return 0;
		List<String> recents = new ArrayList<String>();
		for(int i = 0; i < 20; i++) {
			if(i < recentDamageReasons.size()) {
				Damage damage = recentDamageReasons.get(i);
				if(System.currentTimeMillis() - damage.getTime() < 20000) {
					if(damage.getAttacker() != null) {
						if(!recents.contains(damage.getAttacker())) recents.add(damage.getAttacker());
					}
				}
			}
		}
		return recents.size();
	}
	
	public String getLastDamageCauses() { //only use upon death to avoid potential ConcurrentModificationException
		String causes = "";
		TreeMap<Double, List<Damage>> sortMap = new TreeMap<Double, List<Damage>>();
		for(int i = 0; i < 20; i++) {
			if(i < recentDamageReasons.size()) {
				Damage damage = recentDamageReasons.get(i);
				if(System.currentTimeMillis() - damage.getTime() < 20000) {
					if(!sortMap.containsKey(damage.getDamage())) {
						sortMap.put(damage.getDamage(), new ArrayList<Damage>());
						sortMap.get(damage.getDamage()).add(damage);
						continue;
					} else {
						for(Damage listed : sortMap.get(damage.getDamage())) {
							if(listed.getReason().equalsIgnoreCase(damage.getReason())) continue;
							else sortMap.get(damage.getDamage()).add(damage);
						}
					}
				}
			}
		}
		int count = 0;
		List<String> listCauses = new ArrayList<String>();
		for(Entry<Double, List<Damage>> entry : sortMap.descendingMap().entrySet()) {
			for(Damage listed : entry.getValue()) {
				if(count < 4) {
					if(listCauses.contains(listed.getReason())) listCauses.add(listed.getReason());
					count++;
				} else break;
			}
		}
		for(String cause : listCauses) {
			causes = causes + cause + ", ";
		}
		return causes;
	}

	public void setLastMessageTo(String target) {
		this.lastMessageTo = target;
	}

	public void setCombatTag(TagPlayer tag) {
		this.tag = tag;
	}

	public void setLastRespawn(long lastRespawn) {
		this.lastRespawn = lastRespawn;
	}

	public void setLastDeathLocation(Location location) {
		this.lastDeathLocation = location;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public void addBlockCount() {
		this.block_count++;
	}

	public void setBlockCount(int count) {
		this.block_count = count;
	}
	
	public void setBuildManager(BuildManager manager) {
		this.buildManager = manager;
	}
	
	public void setCurrentKit(ClassType set) {
		this.currentKit = set;
	}

	public void setCloaked(boolean cloaked) {
		this.cloak = cloaked;
		Player player = getPlayer();
		if(player == null) return;
		if(cloaked) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				players.hidePlayer(player);
			}
		} else {
			for(Player players : Bukkit.getOnlinePlayers()) {
				players.showPlayer(player);
			}
		}
	}
	
	public void sendKitInfo() { //fix NPE on 405 - Not storing Passive III?
		Player player = getPlayer();
		ClassType currentSet = Armour.getKit(player);
		if(currentSet.equals(ClassType.NONE) || currentSet.equals(ClassType.EMPTY)) return;
		BuildFactory build = getBuildManager().getBuild(currentSet, getBuild(currentSet));
		if(player != null) {
			AbilityInfo sword = AbilityInfo.getAbility(build.getBuildAbilities().get(AbilityType.SWORD));
			AbilityInfo axe = AbilityInfo.getAbility(build.getBuildAbilities().get(AbilityType.AXE));
			AbilityInfo bow = AbilityInfo.getAbility(build.getBuildAbilities().get(AbilityType.BOW));
			AbilityInfo passive = AbilityInfo.getAbility(build.getBuildAbilities().get(AbilityType.PASSIVE));
			AbilityInfo passiveII = AbilityInfo.getAbility(build.getBuildAbilities().get(AbilityType.PASSIVE_II));
			AbilityInfo passiveIII = AbilityInfo.getAbility(build.getBuildAbilities().get(AbilityType.PASSIVE_III));
			player.sendMessage(C.boldAqua + "> Kit Information <");
			player.sendMessage(C.boldGreen + "Kit > " + C.boldReset + currentSet.getKitName());
			if(sword != null) player.sendMessage(C.boldGreen + "Sword > " + C.boldReset + sword.getName());
			else player.sendMessage(C.boldGreen + "Sword > " + C.boldReset + "Unknown");
			if(axe != null) player.sendMessage(C.boldGreen + "Axe > " + C.boldReset + axe.getName());
			else player.sendMessage(C.boldGreen + "Axe > " + C.boldReset + "Unknown");
			if(bow != null) player.sendMessage(C.boldGreen + "Bow > " + C.boldReset + bow.getName());
			else player.sendMessage(C.boldGreen + "Bow > " + C.boldReset + "Unknown");
			if(passive != null) player.sendMessage(C.boldGreen + "Passive > " + C.boldReset + passive.getName());
			else player.sendMessage(C.boldGreen + "Passive > " + C.boldReset + "Unknown");
			if(passiveII != null) player.sendMessage(C.boldGreen + "Passive II > " + C.boldReset + passiveII.getName());
			else player.sendMessage(C.boldGreen + "Passive II > " + C.boldReset + "Unknown");
			if(passiveIII != null) player.sendMessage(C.boldGreen + "Passive III > " + C.boldReset + passiveIII.getName());
			else player.sendMessage(C.boldGreen + "Passive III > " + C.boldReset + "Unknown");
		}
	}

	public void addTeleportHistoryElement(Location location) {
		if(teleportHistory.size() > 9) {
			teleportHistory.remove(9);
		}
		int size = teleportHistory.size();
		if(teleportHistory.size() > 10) size = 10;
		for(int i = 1; i < size; i++) {
			teleportHistory.set(i, teleportHistory.get(i - 1));
		}
		if(teleportHistory.size() == 0) {
			teleportHistory.add(location);
			return;
		}
		teleportHistory.set(0, location);
	}

	public void addAbilityUsage(AbilityInfo ability, long usage) {
		abilityUsage.put(ability, usage);
	}

	public void addActiveAbility(AbilityInfo ability) {
		abilityUsage.put(ability, System.currentTimeMillis());
	}

	public void addRecentDamager(String damager) {
		recentDamagers.put(damager, System.currentTimeMillis());
	}

	public List<String> getRecentDamagers() {
		if(recentDamagers.isEmpty()) return null;
		ArrayList<String> list = new ArrayList<String>();
		for(Entry<String, Long> entry : recentDamagers.entrySet()) {
			if(System.currentTimeMillis() - entry.getValue() > 30000) recentDamagers.remove(entry.getKey());
			else list.add(entry.getKey());
		}
		return list;
	}

//	public static HPlayer getHPlayer(Player player) {
////		if(player == null) return null;
////		if(!hPlayers.containsKey(player.getUniqueId().toString())) {
////			HPlayer h_player = new HPlayer(player.getName(), player.getUniqueId().toString());
////			h_player.load();
////		}
////		return hPlayers.get(player.getUniqueId().toString());
//		return hPlayers.get("f7adad94-62e8-4e70-b10e-f9506ef26ff4");
//	}

	public static HPlayer getHPlayer(String player) {
		if(player == null) return null;
		if(!hPlayers.containsKey(player)) return null;
		hPlayers.get(player).load();
		return hPlayers.get(player);
	}

	public boolean isUnlocked(ClassType set, AbilityInfo ability) {
		if(getBuildManager() == null) return false;
		return getBuildManager().isUnlocked(set, ability);
	}
	
	public void loadArmour() {
		Player player = getPlayer();
		if(player == null) return;
		Armour.tryDefaults(getUUID());
		BuildManager manager = new BuildManager(false);
		
		ResultSet unlocks = Main.mysql.doQuery("SELECT * FROM ability_unlocks WHERE uuid='" + getUUID() + "'");
		try {
			while(unlocks.next()) {
				ClassType set = ClassType.getSet(unlocks.getString(2));
				manager.addUnlock(set, unlocks.getInt(3));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet sel_builds = Main.mysql.doQuery("SELECT * FROM selected_builds WHERE uuid='" + getUUID() + "'");
		try {
			while(sel_builds.next()) {
				manager.setSelectedBuild(ClassType.getSet(sel_builds.getString(2)), sel_builds.getInt(3));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM armour_builds WHERE uuid='" + getUUID() + "'");
		try {
			while(rs.next()) {
				BuildFactory factory = new BuildFactory(rs.getInt(3), ClassType.getSet(rs.getString(2)));
				
				AbilityInfo sword = AbilityInfo.getAbility(rs.getInt(4));
				AbilityInfo axe = AbilityInfo.getAbility(rs.getInt(5));
				AbilityInfo bow = AbilityInfo.getAbility(rs.getInt(6));
				AbilityInfo passive = AbilityInfo.getAbility(rs.getInt(7));
				AbilityInfo passive_II = AbilityInfo.getAbility(rs.getInt(8));
				AbilityInfo passive_III = AbilityInfo.getAbility(rs.getInt(9));
				
				if(sword.equals(AbilityInfo.NONE)) sword = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.SWORD);
				if(axe.equals(AbilityInfo.NONE)) axe = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.AXE);
				if(bow.equals(AbilityInfo.NONE)) bow = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.BOW);
				if(passive.equals(AbilityInfo.NONE)) passive = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.PASSIVE);
				if(passive_II.equals(AbilityInfo.NONE)) passive_II = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.PASSIVE_II);
				if(passive_III.equals(AbilityInfo.NONE)) passive_III = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.PASSIVE_III);
				
				factory.withAbilities(new AbilityInfo[] {sword, axe, bow, passive, passive_II, passive_III});
				manager.setBuild(factory.getArmourSet(), factory.getBuildId(), factory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "Sector > Failed to load 'armour_builds' for " + this.name);
		}
		setBuildManager(manager);
	}
	
	public void load() {
		loadArmour();
		this.session = new PlayerSession(getUUID());
	}
	
	public static void reload() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			HPlayer hplayer = new HPlayer(player.getName(), player.getUniqueId().toString());
			hPlayers.put(player.getUniqueId().toString(), hplayer);
		}
		Main.logger.log(Level.INFO, "Sector > Loaded HPlayer");
	}

	public boolean purchaseAbility(ClassType set, AbilityInfo ability) {
		Player player = getPlayer();
		if(player == null) return false;
		if(getBuildManager() == null) return false;
		if(getBuildManager().isUnlocked(set, ability)) {
			M.message(player, "Build", ChatColor.YELLOW + ability.getName() + ChatColor.WHITE + " is already unlocked");
			return false;
		}
		Main.mysql.doUpdate("REPLACE INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + player.getUniqueId().toString() + "','" + set.getMinecraftName() + "', " + ability.getID() + ")");
		getBuildManager().addUnlock(set, ability);
		M.message(player, "Purchase", ChatColor.WHITE + "You purchased " + ChatColor.YELLOW + ability.getName() + ChatColor.WHITE + " for " + ChatColor.YELLOW + set.getKitName());
		return true;
	}
	
	public boolean selectedAbility(ClassType set, AbilityInfo ability, int build) {
		Player player = getPlayer();
		if(player == null) return false;
		if(getBuildManager() == null) return false;
		if(!getBuildManager().isUnlocked(set, ability)) {
			M.message(player, "Build", ChatColor.WHITE + "You must first unlock " + ChatColor.YELLOW + ability.getName());
			return false;
		}
		if(getBuildManager().isSelected(set, ability, build)) {
			M.message(player, "Build", ChatColor.YELLOW + ability.getName() + ChatColor.WHITE + " is already selected");
			return false;
		}
		Main.mysql.doUpdate("UPDATE armour_builds SET " + ability.getType().getDatabaseColumnName() + "=" + ability.getID() + " WHERE build=" + build + " AND kit='" + set.getMinecraftName() + "'");
		getBuildManager().getBuild(set, build).withAbility(ability);
		M.message(player, "Build", ChatColor.WHITE + "You selected " + ChatColor.YELLOW + ability.getName() + ChatColor.WHITE + " for " + ChatColor.YELLOW + set.getKitName() + " " + build);
		return true;
	}
	
}
