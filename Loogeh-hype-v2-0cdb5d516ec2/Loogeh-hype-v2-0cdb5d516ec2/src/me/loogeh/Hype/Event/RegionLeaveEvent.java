package me.loogeh.Hype.Event;

import me.loogeh.Hype.Region.Region;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionLeaveEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancel = false;
	
	private Player player;
	private Location from;
	private Location to;
	private Region region;
	private String message;
	
	public RegionLeaveEvent(Player player, Location from, Location to, Region region, String message) {
		this.player = player;
		this.from = from;
		this.to = to;
		this.region = region;
		this.setMessage(message);
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
	
	public Region getRegion(){
		return this.region;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public boolean isCancelled() {
		return cancel;
	}

	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
