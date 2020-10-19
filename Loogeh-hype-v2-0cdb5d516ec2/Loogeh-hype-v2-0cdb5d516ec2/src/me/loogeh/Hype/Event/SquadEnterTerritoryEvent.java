package me.loogeh.Hype.Event;

import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.Squad.Relation;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SquadEnterTerritoryEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Squad fromSquad;
	private Squad toSquad;
	private Location fromLoc;
	private Location toLoc;
	
	
	public SquadEnterTerritoryEvent(Player player, Squad fromSquad, Squad toSquad, Location fromLoc, Location toLoc) {
		this.player = player;
		this.fromSquad = fromSquad;
		this.toSquad = toSquad;
		this.fromLoc = fromLoc;
		this.toLoc = toLoc;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Squad getFromSquad() {
		return this.fromSquad;
	}
	
	public Squad getToSquad() {
		return this.toSquad;
	}
	
	public Location getFromLocation() {
		return this.fromLoc;
	}
	
	public Location getToLocation() {
		return this.toLoc;
	}
	
	public Relation getRelation() {
		if(fromSquad == null || toSquad == null) return Relation.NEUTRAL;
		return fromSquad.getRelation(toSquad);
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
