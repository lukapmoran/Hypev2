package me.loogeh.Hype.CombatTag;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;

public class TagPlayer {
	
	private String player; //Player who's tagged
	private long last_damage; //Last time they damaged another player
	
	public TagPlayer(String player, long last_damage) {
		this.player = player;
		this.last_damage = last_damage;
	}
	
	public String getPlayer() {
		return this.player;
	}
	
	public long getLastDamage() {
		return this.last_damage;
	}
	
	public void setLastDamage(long millis) {
		this.last_damage = millis;
	}
	
	public double getRemaining() {
		return utilTime.convert((last_damage + getUntagTimeMillis()) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
	}
	
	public static int getUntagTime() {
		return Main.config.getInt("server.untag_time");
	}
	
	public static int getUntagTimeMillis() {
		return getUntagTime() * 1000;
	}
}