package me.loogeh.Hype.Player;

public class Damage {
	
	private String damageReason;
	private String attacker = "";
	private long time = System.currentTimeMillis();
	private double damage = 0;
	
	public Damage(String damageReason, String attacker, double damage) {
		this.damageReason = damageReason;
		this.attacker = attacker;
		this.damage = damage;
	}
	
	public String getReason() {
		return this.damageReason;
	}
	
	
	public String getAttacker() {
		return this.attacker;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public double getDamage() {
		return this.damage;
	}
}
