package me.loogeh.Hype.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TickEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private TickType type;
	
	public TickEvent(TickType type) {
		this.type = type;
	}
	
	public TickType getType() {
		return this.type;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public enum TickType {
		TICK(1L),
		TENTH(100L),
		FIFTH(200L),
		HALF(500L),
		SECOND(1000L),
		TWO_SECOND(2000L),
		FOUR_SECOND(4000L),
		TEN_SECOND(10000L),
		FIFTEEN_SECOND(15000L),
		MINUTE(60000L),
		TWO_MINUTE(120000L),
		FIVE_MINUTE(300000L),
		TEN_MINUTE(600000L),
		HALF_HOUR(1800000L);
		
		private long value;
		
		TickType(long value) {
			this.value = value;
		}
		
		public long getValue() {
			return this.value;
		}
	}
	
}
