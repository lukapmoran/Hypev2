package me.loogeh.Hype.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CooldownReceiveEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled = false;
	
	private Player player;
	private String name;
	private long duration;
	
	
	public CooldownReceiveEvent(Player player, String name, long duration) {
		this.player = player;
		this.name = name;
		this.duration = duration;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
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
