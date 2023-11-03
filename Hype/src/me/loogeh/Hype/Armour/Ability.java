package me.loogeh.Hype.Armour;

import org.bukkit.event.Listener;

public abstract class Ability implements Listener {

	private String name = "Ability";
	private AbilityInfo info;

	public Ability(String name, AbilityInfo info) {
		this.name = name;
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public AbilityInfo getInfo() {
		return this.info;
	}
}
