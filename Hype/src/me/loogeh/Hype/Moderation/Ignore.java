package me.loogeh.Hype.Moderation;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import me.loogeh.Hype.Formatting.M;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//
public class Ignore {
}
//	
//	public static HashMap<String, ArrayList<String>> ignoreMap = new HashMap<String, ArrayList<String>>();
//	
//	public static void add(Player player, String target) {
//		if(player == null) return;
//		if(!ignoreMap.containsKey(player.getName())) {
//			ignoreMap.put(player.getName(), new ArrayList<String>());
//		}
//		Player p_target = Bukkit.getPlayer(target);
//		if(p_target != null) {
//			if(player.getName().equalsIgnoreCase(p_target.getName())) {
//				M.message(player, "Ignore", ChatColor.WHITE + "You cannot " + ChatColor.YELLOW + "Ignore " + ChatColor.WHITE + "yourself");
//				return;
//			}
//			if(ignoreMap.get(player.getName()).contains(p_target.getName())) {
//				M.message(player, "Ignore", ChatColor.WHITE + "You have already ignored " + ChatColor.YELLOW + p_target.getName());
//				return;
//			}
//			ignoreMap.get(player.getName()).add(p_target.getName());
//			M.message(player, "Ignore", ChatColor.WHITE + "You ignored " + ChatColor.YELLOW + p_target.getName());
//			return;
//			
//		}
//		if(player.getName().equalsIgnoreCase(target)) {
//			M.message(player, "Ignore", ChatColor.WHITE + "You cannot " + ChatColor.YELLOW + "Ignore " + ChatColor.WHITE + "yourself");
//			return;
//		}
//		if(ignoreMap.get(player.getName()).contains(target)) {
//			M.message(player, "Ignore", ChatColor.WHITE + "You have already ignored " + ChatColor.YELLOW + target);
//			return;
//		}
//		ignoreMap.get(player.getName()).add(target);
//		M.message(player, "Ignore", ChatColor.WHITE + "You ignored " + ChatColor.YELLOW + target);
//		return;
//		
//	}
//	
//	public static void remove(Player player, String target) {
//		if(player == null) return;
//		Player p_target = Bukkit.getPlayer(target);
//		if(p_target != null) {
//			if(!ignoreMap.containsKey(player.getName())) {
//				M.message(player, "Ignore", ChatColor.WHITE + "You have not ignored " + ChatColor.YELLOW + p_target.getName());
//				return;
//			}
//		}
//		if(!ignoreMap.containsKey(player.getName())) {
//			M.message(player, "Ignore", ChatColor.WHITE + "You have not ignored " + ChatColor.YELLOW + target);
//			return;
//		}
//		
//	}
//	
//	public static boolean isIgnored(Player player, String target) {
//		if(player == null) return false;
//		Player p_target = Bukkit.getPlayer(target);
//		if(p_target == null) {
//			
//		}
//		return false;
//	}
//
//}
