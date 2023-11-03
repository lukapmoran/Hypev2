package me.loogeh.Hype.Event;

import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.Squad.Relation;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SquadRelationChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Squad changer;
	private Squad squad;
	private Relation newRelation;
	
	
	public SquadRelationChangeEvent(Squad changer, Squad squad, Relation newRelation) {
		this.changer = changer;
		this.squad = squad;
		this.newRelation = newRelation;
	}
	
	public Squad getChanger() {
		return this.changer;
	}
	
	public Squad getSquad() {
		return this.squad;
	}
	
	public Relation getNewRelation() {
		return this.newRelation;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
