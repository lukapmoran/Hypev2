package me.loogeh.Hype.Event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class PlayerBlockEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private Material material;
	private Action action;
	
	public PlayerBlockEvent(Player player, Material material, Action action) {
		this.player = player;
		this.material = material;
		this.action = action;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Material getItem() {
		return this.material;
	}
	
	public Action getAction() {
		return this.action;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
