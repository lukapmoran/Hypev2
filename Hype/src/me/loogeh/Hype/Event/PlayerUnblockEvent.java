package me.loogeh.Hype.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUnblockEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private long duration;
	
	public PlayerUnblockEvent(Player player, long duration) {
		this.player = player;
		this.duration = duration;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
