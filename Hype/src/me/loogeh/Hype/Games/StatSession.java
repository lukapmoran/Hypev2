package me.loogeh.Hype.Games;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Utility.utilMath;

public abstract class StatSession {
	
	protected String uuid = "";
	protected Game game;
	protected long start_time;
	protected HashMap<ClassType, Integer> kills = new HashMap<ClassType, Integer>();
	protected HashMap<ClassType, Integer> deaths = new HashMap<ClassType, Integer>();
	protected int score = 0;
	protected int bow_shots = 0;
	protected int bow_hits = 0;
	
	public StatSession(String uuid, Game game) {
		this.uuid = uuid;
		this.game = game;
		HashMap<ClassType, Integer> kills = new HashMap<ClassType, Integer>();
		HashMap<ClassType, Integer> deaths = new HashMap<ClassType, Integer>();
		for(ClassType set : ClassType.values()) {
			kills.put(set, 0);
			deaths.put(set, 0);
		}
	}
	
	public StatSession(Player player, Game game) {
		this.uuid = player.getUniqueId().toString();
		this.game = game;
		HashMap<ClassType, Integer> kills = new HashMap<ClassType, Integer>();
		HashMap<ClassType, Integer> deaths = new HashMap<ClassType, Integer>();
		for(ClassType set : ClassType.values()) {
			kills.put(set, 0);
			deaths.put(set, 0);
		}
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public long getStartTime() {
		return this.start_time;
	}
	
	private HashMap<ClassType, Integer> getKillsMap() {
		return this.kills;
	}
	
	private HashMap<ClassType, Integer> getDeathsMap() {
		return this.deaths;
	}
	
	public int getKills(ClassType set) {
		return getKillsMap().get(set);
	}
	
	public int getDeaths(ClassType set) {
		return getDeathsMap().get(set);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getBowShots() {
		return this.bow_shots;
	}
	
	public int getBowHits() {
		return this.bow_hits;
	}
	
	public void setKills(ClassType set, int kills) {
		getKillsMap().put(set, kills);
	}
	
	public void addKills(ClassType set, int kills) {
		int cur = getKillsMap().get(set);
		getKillsMap().put(set, cur + kills);
	}
	
	public void setDeaths(ClassType set, int deaths) {
		getDeathsMap().put(set, deaths);
	}
	
	public void addDeaths(ClassType set, int deaths) {
		int cur = getDeathsMap().get(set);
		getDeathsMap().put(set, cur + deaths);
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void setBowShots(int shots) {
		this.bow_shots = shots;
	}
	
	public void addBowShots(int shots) {
		this.bow_shots += shots;
	}
	
	public void setBowHits(int hits) {
		this.bow_hits = hits;
	}
	
	public void addBowHits(int hits) {
		this.bow_hits += hits;
	}
	
	public double getKD(ClassType set) {
		double kills = getKills(set);
		double deaths = getDeaths(set);
		double ratio = kills / deaths;
		return utilMath.trim(ratio, 2);
	}
	
	public double getBowAccuracy(int decimalPlace) {
		double shots = (double) getBowShots();
		double hits = (double) getBowHits();
		double percent = (hits / shots) * 100;
		return utilMath.trim(percent, decimalPlace);
	}
	
	public abstract void save();

}
