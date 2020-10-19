package me.loogeh.Hype.Event;

import me.loogeh.Hype.Shops.Shop;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopLeaveEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Shop shop;
	
	public ShopLeaveEvent(Player player, Shop shop) {
		this.player = player;
		this.shop = shop;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Shop getShop() {
		return this.shop;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
