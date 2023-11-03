package me.loogeh.Hype.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Sector.Sector;
import me.loogeh.Hype.Utility.utilMath;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;

public class mStats extends Sector {

	private String name;
	private String uuid;
	
	private int joinTime = (int) (System.currentTimeMillis() / 1000); //in seconds
	private HashMap<ClassType, Integer> killsMap = new HashMap<ClassType, Integer>();
	private HashMap<ClassType, Integer> deathsMap = new HashMap<ClassType, Integer>();
	private int bowShots = 0;
	private int bowHits = 0;
	private int abilitiesUsed = 0;
	
	private int totalPlayTime = 0;
	private HashMap<ClassType, Integer> totalKillsMap = new HashMap<ClassType, Integer>();
	private HashMap<ClassType, Integer> totalDeathsMap = new HashMap<ClassType, Integer>();
	private int totalBowShots = 0;
	private int totalBotHits = 0;
	private int totalAbilitiesUsed = 0;
	
	public mStats(Member member) {
		super("Stats");
		super.register();
		this.name = member.getName();
		this.uuid = member.getUUID();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public long getJoinTime() {
		return this.joinTime;
	}
	
	private HashMap<ClassType, Integer> getKillsMap() {
		return this.killsMap;
	}
	
	private HashMap<ClassType, Integer> getDeathsMap() {
		return this.deathsMap;
	}
	
	public int getKills(ClassType set) {
		return getKillsMap().get(set);
	}
	
	public int getDeaths(ClassType set) {
		return getDeathsMap().get(set);
	}
	
	public int getBowShots() {
		return this.bowShots;
	}
	
	public void setBowShots(int bowShots) {
		this.bowShots = bowShots;
	}
	
	public void addBowShot() {
		this.bowShots++;
	}
	
	public int getBowHits() {
		return this.bowHits;
	}
	
	public void setBowHits(int bowHits) {
		this.bowHits = bowHits;
	}
	
	public void addBowHit() {
		this.bowHits++;
	}
	
	public int getAbilitiesUsed() {
		return this.abilitiesUsed;
	}
	
	public int getTimePlayed() {
		int played = (int) ((System.currentTimeMillis() / 1000) - getJoinTime());
		return played;
	}
	
	public double getBowAccuracy() {
		if(getBowShots() == 0) return -1.0D;
		double percent = ((double) getBowHits() / (double) getBowShots()) * 100;
		return utilMath.trim(percent, 1);
	}
	
	public void setJoinTime(int seconds) {
		this.joinTime = seconds;
	}
	
	public void setKills(ClassType set, int kills) {
		getKillsMap().put(set, kills);
	}
	
	public void setDeaths(ClassType set, int deaths) {
		getDeathsMap().put(set, deaths);
	}
	
	public void setAbilitiesUsed(int abilitiesUsed) {
		this.abilitiesUsed = abilitiesUsed;
	}
	
	public void addKill(ClassType set) {
		int cur = getKills(set);
		setKills(set, cur + 1);
	}
	
	public void addDeath(ClassType set) {
		int cur = getDeaths(set);
		setDeaths(set, cur + 1);
	}
	
	public void addAbility() {
		this.abilitiesUsed++;
	}
	
	public int getKills() {
		int kills = 0;
		for(ClassType classType : ClassType.values()) {
			kills += getKills(classType);
		}
		return kills;
	}
	
	public int getDeaths() {
		int deaths = 0;
		for(ClassType classType : ClassType.values()) {
			deaths += getDeaths(classType);
		}
		return deaths;
	}
	
	public ClassType getFavouriteClass() {
		ClassType favourite = ClassType.NONE;
		int lastKills = 0;
		for(ClassType classType : ClassType.values()) {
			if(getTotalKills(classType) > lastKills) {
				lastKills = getTotalKills(classType);
				favourite = classType;
			}
		}
		return favourite;
	}
	
	public double getKillsPercent(ClassType classType) {
		int totalKills = getTotalKills();
		int classKills = getTotalKills(classType);
		return utilMath.trim((double) classKills / (double) totalKills, 1);
	}
	
	public double getKD() {
		int kills = getKills();
		int deaths = getDeaths();
		return utilMath.trim((double) kills / (double) deaths, 2);
	}
	
	public int getTotalPlayTime() {
		return totalPlayTime + getTimePlayed();
	}

	public void setTotalPlayTime(int totalPlayTime) {
		this.totalPlayTime = totalPlayTime;
	}

	private HashMap<ClassType, Integer> getTotalKillsMap() {
		return totalKillsMap;
	}

	private HashMap<ClassType, Integer> getTotalDeathsMap() {
		return totalDeathsMap;
	}

	public int getTotalBowShots() {
		return totalBowShots;
	}

	public void setTotalBowShots(int totalBowShots) {
		this.totalBowShots = totalBowShots;
	}

	public int getTotalBowHits() {
		return totalBotHits;
	}

	public void setTotalBowHits(int totalBotHits) {
		this.totalBotHits = totalBotHits;
	}
	
	public double getTotalBowAccuracy() {
		if(getTotalBowShots() == 0) return -1.0D;
		double percent = ((double) getTotalBowHits() / (double) getTotalBowShots()) * 100;
		return utilMath.trim(percent, 1);
	}

	public int getTotalAbilitiesUsed() {
		return totalAbilitiesUsed;
	}

	public void setTotalAbilitiesUsed(int totalAbilitiesUsed) {
		this.totalAbilitiesUsed = totalAbilitiesUsed;
	}
	
	public int getTotalKills(ClassType classType) {
		return getTotalKillsMap().get(classType) + getKills(classType);
	}
	
	public void setTotalKills(ClassType classType, int kills) {
		getTotalKillsMap().put(classType, kills);
	}
	
	public int getTotalDeaths(ClassType classType) {
		return getTotalDeathsMap().get(classType) + getDeaths(classType);
	}
	
	public void setTotalDeaths(ClassType classType, int deaths) {
		getTotalDeathsMap().put(classType, deaths);
	}
	
	public int getTotalKills() {
		int kills = 0;
		for(ClassType classType : ClassType.values()) {
			kills += getTotalKills(classType);
		}
		return kills;
	}
	
	public int getTotalDeaths() {
		int deaths = 0;
		for(ClassType classType : ClassType.values()) {
			deaths += getTotalDeaths(classType);
		}
		return deaths;
	}
	
	public double getTotalKD() {
		int kills = getTotalKills();
		int deaths = getTotalDeaths();
		return utilMath.trim((double) kills / (double) deaths, 2);
	}
	
	public void send(Player player, StatDisplay displayType) {
		if(displayType.equals(StatDisplay.OVERVIEW)) {
			player.sendMessage(ChatColor.GREEN + getName() + "'s Overview");
			player.sendMessage(ChatColor.GRAY + "Play Time > " + ChatColor.GREEN + utilTime.convertString(getTotalPlayTime() * 1000, TimeUnit.BEST, 1) + ChatColor.GRAY + " (" + utilTime.convertString(getTimePlayed(), TimeUnit.BEST, 1) + " this session)");
			player.sendMessage(ChatColor.GRAY + "Kills > " + ChatColor.GREEN + getTotalKills() + ChatColor.GRAY + " (" + getKills() + " this session)");
			player.sendMessage(ChatColor.GRAY + "Deaths > " + ChatColor.GREEN + getTotalDeaths() + ChatColor.GRAY + " (" + getDeaths() + " this session)");
			player.sendMessage(ChatColor.GRAY + "KD > " + ChatColor.GREEN + getTotalKD() + ChatColor.GRAY + " (" + (getTotalKD() - getKD()) + " session difference)");
			player.sendMessage(ChatColor.GRAY + "Abilities > " + ChatColor.GREEN + getTotalAbilitiesUsed() + ChatColor.GRAY + " (" + getAbilitiesUsed() + " this session)");
			double totalAccuracy = getTotalBowAccuracy();
			double sessionAccuracy = getBowAccuracy();
			if(totalAccuracy >= 0.0 && sessionAccuracy >= 0.0D) player.sendMessage(ChatColor.GRAY + "Bow Accuracy > " + ChatColor.GREEN + getBowAccuracy() + "%" + ChatColor.GRAY + " (" + (totalAccuracy - sessionAccuracy) + " session difference");
			else if(totalAccuracy >= 0.0D && sessionAccuracy < 0.0D) player.sendMessage(ChatColor.GRAY + "Bow Accuracy > " + ChatColor.GREEN + getBowAccuracy() + "%");
			else if(totalAccuracy < 0.0D && sessionAccuracy < 0.0D) player.sendMessage(ChatColor.GRAY + "Bow Accuracy > " + ChatColor.GREEN + "0 Shots taken");
			player.sendMessage(ChatColor.GRAY + "Favourite Class > " + ChatColor.GREEN + getFavouriteClass().getKitName() + ChatColor.GRAY + " (" + getKillsPercent(getFavouriteClass()) + "%)");
		}
		if(displayType.equals(StatDisplay.CLASS_EVALUATION)) {
			
		}
	}
	

	public void save() {
		Main.mysql.doUpdate("UPDATE `player_stats` SET " +
				"`playTime`=playTime+" + getTimePlayed() + "," +
				"`kills_diamond`=kills_diamond+" + getKills(ClassType.DIAMOND) + "," +
				"`kills_iron`=kills_iron+" + getKills(ClassType.IRON) + "," +
				"`kills_gold`=kills_gold+" + getKills(ClassType.GOLD) + "," +
				"`kills_chain`=kills_chain+" + getKills(ClassType.CHAIN) + "," +
				"`kills_leather`=kills_leather+" + getKills(ClassType.LEATHER) + "," +
				"`kills_empty`=kills_empty+" + getKills(ClassType.EMPTY) + "," +
				"`kills_none`=kills_none+" + getKills(ClassType.NONE) + "," +
				"`deaths_diamond`=deaths_diamond+" + getDeaths(ClassType.DIAMOND) + "," +
				"`deaths_iron`=deaths_iron+" + getDeaths(ClassType.IRON) + "," +
				"`deaths_gold`=deaths_gold+" + getDeaths(ClassType.GOLD) + "," +
				"`deaths_chain`=deaths_chain+" + getDeaths(ClassType.CHAIN) + "," +
				"`deaths_leather`=deaths_leather+" + getDeaths(ClassType.LEATHER) + "," +
				"`deaths_empty`=deaths_empty+" + getDeaths(ClassType.EMPTY) + "," +
				"`deaths_none`=deaths_none+" + getDeaths(ClassType.NONE) + "," +
				"`bow_shots`=bow_shots+" + getBowShots() + "," +
				"`bow_hits`=bow_hits+" + getBowHits() + "," +
				"`abilities_used`=" + getAbilitiesUsed() + " " +
				"WHERE UUID='" + getUUID() + "'");
	}

	public void load() {
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM player_stats");
		try {
			if(rs.next()) {
				setTotalPlayTime(rs.getInt(2));
				setTotalKills(ClassType.DIAMOND, rs.getInt(3));
				setTotalKills(ClassType.IRON, rs.getInt(4));
				setTotalKills(ClassType.GOLD, rs.getInt(5));
				setTotalKills(ClassType.CHAIN, rs.getInt(6));
				setTotalKills(ClassType.LEATHER, rs.getInt(7));
				setTotalKills(ClassType.EMPTY, rs.getInt(8));
				setTotalKills(ClassType.NONE, rs.getInt(9));
				setTotalDeaths(ClassType.DIAMOND, rs.getInt(10));
				setTotalDeaths(ClassType.IRON, rs.getInt(11));
				setTotalDeaths(ClassType.GOLD, rs.getInt(12));
				setTotalDeaths(ClassType.CHAIN, rs.getInt(13));
				setTotalDeaths(ClassType.LEATHER, rs.getInt(14));
				setTotalDeaths(ClassType.EMPTY, rs.getInt(15));
				setTotalDeaths(ClassType.NONE, rs.getInt(16));
				setTotalBowShots(rs.getInt(17));
				setTotalBowHits(rs.getInt(18));
				setTotalAbilitiesUsed(rs.getInt(18));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Stats " + (deathsMap.size() + killsMap.size() + totalDeathsMap.size() + totalKillsMap.size()));
	}
	
	
	public enum StatDisplay {
		OVERVIEW,
		CLASS_EVALUATION;
	}
}
