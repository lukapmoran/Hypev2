package me.loogeh.Hype.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.event.block.Action;

public enum A {
	L("Left Click Air", Arrays.asList(Action.LEFT_CLICK_AIR)),
	R("Right Click Air", Arrays.asList(Action.RIGHT_CLICK_AIR)),
	L_BLOCK("Left Click Block", Arrays.asList(Action.LEFT_CLICK_BLOCK)),
	R_BLOCK("Right Click Block", Arrays.asList(Action.RIGHT_CLICK_BLOCK)),
	L_BOTH("Left Click", Arrays.asList(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)),
	R_BOTH("Right Click", Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)),
	P("Physical", Arrays.asList(Action.PHYSICAL)),
	C("Crouch", null),
	BOW_CHARGE("Bow Charge", null);
	
	private String name = "";
	private List<Action> bukkitActions = new ArrayList<Action>();
	
	A(String name, List<Action> bukkitActions) {
		this.name = name;
		this.bukkitActions = bukkitActions;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Action> getActions() {
		return this.bukkitActions;
	}
}
