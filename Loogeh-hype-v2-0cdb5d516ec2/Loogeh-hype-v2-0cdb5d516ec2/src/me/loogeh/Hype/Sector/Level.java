package me.loogeh.Hype.Sector;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilMath;
import me.loogeh.Hype.Utility.utilMath.PlaceValue;

import org.bukkit.entity.Player;

public class Level extends Sector {
	
	public Level() {
		super("Level", Main.get());
	}

	private static int MAX_LEVEL = 20;
	
	public static int getExpRequirement(int level) {
		if(level >= 20) return -1;
		double levelCalc = 100 * level;
		double additionalXP = (((((double) level / 25) * 2500) + ((double) level / MAX_LEVEL) * 100)); // divide by 10? 
		double total = levelCalc + additionalXP;
		return utilMath.round((int) total, PlaceValue.TENS);
	}
	
	public static int calculateReward(Player player, Player killed) {
		return 10;
	}

	public void load() {
		
	}
	
	

}
