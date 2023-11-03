package me.loogeh.Hype.Miscellaneous;

import java.util.HashMap;

public class IDHashMap<V> extends HashMap<Integer, V> {

	private static final long serialVersionUID = -4709846781390089200L;
	private int lastID = 0;
	
	public Integer put(V value) {
		int id = getNextID();
		this.put(id, value);
		return id;
	}
	
	public boolean contains(Integer integer) {
		return containsKey(integer);
	}
	
	private Integer getNextID() {
		if(containsKey(lastID)) {
			for(int id = lastID; id < lastID + 10000; lastID++) {
				if(!containsKey(lastID)) return lastID;
			}
		}
		return lastID;
	}
}
