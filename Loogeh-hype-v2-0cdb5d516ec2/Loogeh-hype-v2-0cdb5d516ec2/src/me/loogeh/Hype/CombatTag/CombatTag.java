package me.loogeh.Hype.CombatTag;

import java.util.Iterator;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Player.HPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CombatTag {
	
//	public static void add(Player player, long last_damage) {
//		if(player == null || last_damage == (Long) null) return;
//		HPlayer h_player = HPlayer.getHPlayer(player);
//		if(Game.getGame(player) != null) return;
//		if(h_player.getCombatTag() != null) {
//			h_player.getCombatTag().setLastDamage(last_damage);
//			return;
//		}
//		h_player.setCombatTag(new TagPlayer(player.getName(), last_damage));
//		M.message(player, "Combat Tag", ChatColor.WHITE + "You are now " + ChatColor.YELLOW + "Combat Tagged");
//	}
	
	@SuppressWarnings("deprecation")
	public static void remove(String player) {
		Player p_target = Bukkit.getPlayerExact(player);
		HPlayer h_player = HPlayer.getHPlayer(player);
		if(h_player == null) return;
		if(h_player.getCombatTag() == null) return;
		h_player.setCombatTag(null);
		if(p_target != null) M.message(p_target, "Combat Tag", ChatColor.WHITE + "You are no longer " + ChatColor.YELLOW + "Combat Tagged");
	}
	
	public static boolean isTagged(String player) {
		HPlayer h_player = HPlayer.getHPlayer(player);
		if(h_player == null) return false;
		if(h_player.getCombatTag() != null) {
			if(h_player.getCombatTag().getRemaining() <= 0.0) {
				remove(player);
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static void startCombatTagScheduler() {
		Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				Iterator<String> it = HPlayer.hPlayers.keySet().iterator();
				while(it.hasNext()) {
					String next = it.next();
					HPlayer h_player = HPlayer.hPlayers.get(next);
					if(h_player != null) {
						if(h_player.getCombatTag() != null) {
							if(h_player.getCombatTag().getRemaining() <= 0.0) remove(next);
						}
					}
				}
			}
		}, 20L, 20L);
	}
}
