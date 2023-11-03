package me.loogeh.Hype.Miscellaneous;

import java.util.HashMap;

public class AbilityCooldown {
	
	public HashMap<String, AbilityCooldown> cooldownMap = new HashMap<String, AbilityCooldown>();
	
	public String ability = "";
	public String player = "";
	public long seconds;
	public long systime;
	
	public AbilityCooldown(String player, long seconds) {
		this.player = player;
		this.seconds = seconds;
		this.systime = System.currentTimeMillis();
	}
	
	public AbilityCooldown(String player) {
		this.player = player;
	}
	

}
