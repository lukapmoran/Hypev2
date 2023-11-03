package me.loogeh.Hype.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BowUnchargeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private UnchargeReason reason;
	
	public BowUnchargeEvent(Player player, UnchargeReason reason) {
		this.player = player;
		this.reason = reason;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public UnchargeReason getReason() {
		return this.reason;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public enum UnchargeReason {
		SHOOT,
		DEATH,
		ITEM_CHANGE,
		TELEPORT,
		PLAYER_LEAVE;
	}
}
