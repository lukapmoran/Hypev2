package me.loogeh.Hype.Event;

import me.loogeh.Hype.Armour.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AbilityUseEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled = false;
	
	private Player player;
	private Ability ability;
	
	public AbilityUseEvent(Player player, Ability ability) {
		this.player = player;
		this.ability = ability;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Ability getAbility() {
		return this.ability;
	}
	

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
