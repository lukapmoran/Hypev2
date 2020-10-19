package me.loogeh.Hype.Server;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilTime;

public class ServerManager {
	
	public static void databaseCheck() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS build (date VARCHAR(30), build INT(11))");
	}
	
	public static void recordBuild() {
		Main.mysql.doUpdate("INSERT INTO `build` (`date`) VALUES ('" + utilTime.timeStr() + "')");
	}
	

}
