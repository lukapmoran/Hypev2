package me.loogeh.Hype.Utility;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.loogeh.Hype.Main.Main;

public class utilServer {

	public static void printMemoryUsage() {
		Runtime runtime = Runtime.getRuntime();
		
	    long maxMemory = runtime.maxMemory();
//	    long freeMemory = runtime.freeMemory();
	    double used = utilMath.getPercentage(getFreeMemory(),(maxMemory / 1048576), 1);
	    Main.logger.log(Level.INFO, "-------------------- < Memory Usage > ---------------------");
	    Main.logger.log(Level.INFO, "Memory >> Max " + (maxMemory / 1048576) + " MB");
	    Main.logger.log(Level.INFO, "Memory >> Free " + getFreeMemory() + " MB");
	    Main.logger.log(Level.INFO, "Memory >> Un-used " + used + "%");
	    Main.logger.log(Level.INFO, "-------------------- < Memory Usage > ---------------------");
	}
	
	public static int getFreeMemory() {
		Runtime runtime = Runtime.getRuntime();
		return (int) ((runtime.maxMemory() - runtime.totalMemory() + runtime.freeMemory()) / 1048576);
	}
	
	public static Player getPlayer(String uuid) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getUniqueId().toString().equals(uuid)) return player;
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static Player findPlayer(String name) {
		return Bukkit.getPlayer(name);
	}
	
}
