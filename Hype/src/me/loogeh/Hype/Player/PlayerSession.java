package me.loogeh.Hype.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Main.Main;

public class PlayerSession {
	
	private String uuid = "";
	private int joinTime = (int) (System.currentTimeMillis() / 1000); //in seconds
	private HashMap<ClassType, Integer> killsMap = new HashMap<ClassType, Integer>();
	private HashMap<ClassType, Integer> deathsMap = new HashMap<ClassType, Integer>();
	private int bowShots = 0;
	private int bowHits = 0;
	private int abilitiesUsed = 0;
	
	public PlayerSession(String uuid) {
		this.uuid = uuid;
		
		HashMap<ClassType, Integer> killsMap = new HashMap<ClassType, Integer>();
		HashMap<ClassType, Integer> deathsMap = new HashMap<ClassType, Integer>();
		for(ClassType set : ClassType.values()) {
			killsMap.put(set, 0);
			deathsMap.put(set, 0);
		}
		this.killsMap = killsMap;
		this.deathsMap = deathsMap;
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
	
	public int getBowHits() {
		return this.bowHits;
	}
	
	public int getAbilitiesUsed() {
		return this.abilitiesUsed;
	}
	
	public int getTimePlayed() {
		int played = (int) ((System.currentTimeMillis() / 1000) - getJoinTime());
		System.out.println("played " + played);
		return played;
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
	
	public void save()  {
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
	
	public void join() {
		ResultSet rs = Main.mysql.doQuery("SELECT uuid FROM player_stats WHERE uuid='" + getUUID() + "'");
		try {
			if(!rs.next()) {
				Main.mysql.doUpdate("INSERT INTO `player_stats`(`UUID`, `playTime`, `kills_diamond`, `kills_iron`, `kills_gold`, `kills_chain`, `kills_leather`, `kills_empty`, `kills_none`, `deaths_diamond`, `deaths_iron`, `deaths_gold`, `deaths_chain`, `deaths_leather`, `deaths_empty`, `deaths_none`, `bow_shots`, `bow_hits`, `abilities_used`) VALUES ('" + getUUID() + "',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendStats(Player player, Player target) {
		
	}
	
}
