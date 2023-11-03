package me.loogeh.Hype.Miscellaneous;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Moderation.Permissions.Ranks;

import org.bukkit.entity.Player;

public class WorldBorder {
	
	public static void set(Player player, BorderType type, int radius) {
		if(player == null) return;
		if(Permissions.getRank(player.getName()) != Ranks.OWNER) {
			M.sendLackPermsMessage(player);
			return;
		}
		if(type.equals(BorderType.RADIAL)) {
//			Location location = player.getLocation();
			
		}
		
	}

	public enum BorderType {
		RADIAL;
	}
	
}
