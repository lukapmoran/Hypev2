package me.loogeh.Hype.Entity;

import me.loogeh.Hype.Main.Main;

public class Entity {

	public static void databaseCheck() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS entity_load (name TEXT, type TEXT, location TEXT)");
	}

}

