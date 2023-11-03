package me.loogeh.Hype.Trading;

import java.util.HashMap;

import me.loogeh.Hype.Formatting.M;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Trade {
	
	public static HashMap<String, TradeSession> tradeSessions = new HashMap<String, TradeSession>();
	public static HashMap<String, String> tradeRequests = new HashMap<String, String>();
	
	@SuppressWarnings("deprecation")
	public static void request(Player player, String target) {
		if(player == null) return;
		Player p_target = Bukkit.getPlayer(target);
		if(p_target == null) {
			M.message(player, "Trade", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target);
			return;
		}
		if(tradeRequests.containsKey(player.getName())) {
			if(tradeRequests.get(player.getName()).equalsIgnoreCase(p_target.getName())) {
				M.message(player, "Trade", ChatColor.WHITE + "You have already sent a request to " + ChatColor.YELLOW + p_target.getName());
				return;
			} else {
				M.message(player, "Trade", ChatColor.WHITE + "You must first cancel your request with " + ChatColor.YELLOW + tradeRequests.get(player.getName()));
				return;
			}
		}
		
	}
	
	public static void cancelRequest(Player player, String target) {
		
	}
	
	public static void startTrade(Player player, String target) {
		
	}
	
	public static void cancelTrade(Player player, String target) {
		
	}
	
}
