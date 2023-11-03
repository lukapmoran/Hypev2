package me.loogeh.Hype.Event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoveBlockEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Location from;
	private Location to;
	
	public PlayerMoveBlockEvent(Player player, Location from, Location to) {
		this.player = player;
		this.from = from;
		this.to = to;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Location getFrom() {
		return this.from;
	}
	
	public Location getTo() {
		return this.to;
	}
	
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	
	
}
