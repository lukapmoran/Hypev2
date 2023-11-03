package me.loogeh.Hype.Squads;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class WandSession {
	
	public static HashMap<String, WandSession> sessions = new HashMap<String, WandSession>();
	
	public Location selection_1 = null;
	public Location selection_2 = null;
	
	public WandSession(Location selection_1) {
		this.selection_1 = selection_1;
	}
	
	public void setSelection(int sel, Location location) {
		if(sel == 1) this.selection_1 = location;
		if(sel == 2) this.selection_2 = location;
	}
	
	public void setSelection1(Location location) {
		this.selection_1 = location;
	}
	
	public void setSelection2(Location location) {
		this.selection_2 = location;
	}
	
	public Location getSelection1() {
		return this.selection_1;
	}
	
	public Location getSelection2() {
		return this.selection_2;
	}
	
	public Chunk getSelectionChunk1() {
		return this.selection_1.getChunk();
	}
	
	public Chunk getSelectionChunk2() {
		return this.selection_2.getChunk();
	}
}
