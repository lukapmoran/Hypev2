package me.loogeh.Hype.Moderation;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.logging.Level;
//
//import me.loogeh.Hype.Formatting.M;
//import me.loogeh.Hype.Main.Main;
//import me.loogeh.Hype.Utility.utilTime;
//import me.loogeh.Hype.Utility.utilTime.TimeUnit;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//
//
public class Ban {
}
//	public static HashMap<String, Double> bantimeMap = new HashMap<String, Double>();
//	public static HashMap<String, Long> bansysMap = new HashMap<String, Long>();
//	public static HashMap<String, Double> banMap = new HashMap<String, Double>();
//	public static HashMap<String, String> banreasonMap = new HashMap<String, String>();
//	
//	public static void load() {
//		ResultSet rs = Main.mysql.doQuery("SELECT player,reason,hours,bantime,systime FROM player_ban");
//		int count = 0;
//		int disposed = 0;
//		try {
//			while(rs.next()) {
//				if((rs.getLong(5) + rs.getLong(4)) - System.currentTimeMillis() > 0) {
//					banMap.put(rs.getString(1), rs.getDouble(3));
//					bantimeMap.put(rs.getString(1), rs.getDouble(4));
//					bansysMap.put(rs.getString(1), rs.getLong(5));
//					banreasonMap.put(rs.getString(1), rs.getString(2));
//					count++;
//				} else {
//					Main.mysql.doUpdate("DELETE FROM player_ban WHERE player='" + rs.getString(1) + "'");
//					disposed++;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			Main.logger.log(Level.INFO, "Bans > Error while loading bans");
//		}
//		Main.logger.log(Level.INFO, "Bans > Successfully loaded " + count + " ban(s)");
//		if(disposed > 0) Main.logger.log(Level.INFO, "Bans > Successfully disposed of " + disposed + " ban(s)");
//	}
//	
//	public static void banPlayer(String player, Player banner, double duration, String reason) {
//		Player banee = Bukkit.getServer().getPlayer(player);
//		if(!Permissions.outranks(banner.getName(), player)) {
//			banner.sendMessage(ChatColor.GRAY + "You must outrank" + ChatColor.WHITE + " [" + ChatColor.YELLOW + banee.getName() + ChatColor.WHITE + "]" + ChatColor.GRAY + " to ban them");
//			return;
//		}
//		if(isBanned(banee.getName())) {
//			banner.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.YELLOW + banee.getName() + ChatColor.GRAY + " is already banned for " + ChatColor.GREEN + getRemaining(banee.getName()));
//			return;
//		}
//		Main.mysql.doUpdate("INSERT INTO `player_ban`(`player`, `banner`, `rank`, `reason`, `hours`, `bantime`, `systime`, `date`) VALUES ('" + banee.getName() + "','" + banner.getName() + "','" + Permissions.getLevel(banner.getName()) + "','" + reason + "'," + duration + "," + (duration * 3600000.0) + "," + System.currentTimeMillis() + ",'" + utilTime.timeStr() + "')");
//		bantimeMap.put(banee.getName(), duration * 3600000.0);
//		banMap.put(banee.getName(), duration);
//		bansysMap.put(banee.getName(), System.currentTimeMillis());
//		banreasonMap.put(banee.getName(), reason);
//		banee.kickPlayer(ChatColor.BLUE + "Banned - " + ChatColor.WHITE + "You have been banned [" + ChatColor.YELLOW + getRemaining(player) + ChatColor.WHITE + "] for " + ChatColor.YELLOW + reason);
//		if(duration >= 9999999999.0D) {
//			M.broadcast(ChatColor.GOLD + banner.getName() + ChatColor.YELLOW + " banned " + ChatColor.GOLD + banee.getName() + ChatColor.YELLOW + " permanently for " + ChatColor.AQUA + reason);
//			return;
//		}
//		M.broadcast(ChatColor.GOLD + banner.getName() + ChatColor.YELLOW + " banned " + ChatColor.GOLD + banee.getName() + ChatColor.YELLOW + " [" + ChatColor.AQUA + getRemaining(player) + ChatColor.YELLOW + "] for " + ChatColor.AQUA + reason);
//	}
//	
//	public static void banOfflinePlayer(String player, Player banner, double duration, String reason) {
//		if(!Permissions.outranks(banner.getName(), player)) {
//			banner.sendMessage(ChatColor.GRAY + "You must outrank" + ChatColor.WHITE + " [" + ChatColor.YELLOW + player + ChatColor.WHITE + "]" + ChatColor.GRAY + " to ban them");
//			return;
//		}
//		if(isBanned(player)) {
//			banner.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.YELLOW + player + ChatColor.GRAY + " is already banned for " + ChatColor.GREEN + getRemaining(player));
//			return;
//		}
//		Main.mysql.doUpdate("INSERT INTO `player_ban`(`player`, `banner`, `rank`, `reason`, `hours`, `bantime`, `systime`, `date`) VALUES ('" + player + "','" + banner.getName() + "','" + Permissions.getLevel(banner.getName()) + "','" + reason + "'," + duration + "," + (duration * 3600000.0) + "," + System.currentTimeMillis() + ",'" + utilTime.timeStr() + "')");
//		bantimeMap.put(player, duration * 3600000.0);
//		banMap.put(player, duration);
//		bansysMap.put(player, System.currentTimeMillis());
//		banreasonMap.put(player, reason);
//		if(duration >= 9999999999.0D) {
//			M.broadcast(ChatColor.GOLD + banner.getName() + ChatColor.YELLOW + " banned " + ChatColor.GOLD + player + ChatColor.YELLOW + " permanently for " + ChatColor.AQUA + reason);
//			return;
//		}
//		M.broadcast(ChatColor.GOLD + banner.getName() + ChatColor.YELLOW + " banned " + ChatColor.GOLD + player + ChatColor.YELLOW + " [" + ChatColor.AQUA + getRemaining(player) + ChatColor.YELLOW + "] for " + ChatColor.AQUA + reason);
//	}
//	
//	
//	
//	public static double getDuration(String player) { return (banMap.get(player)); }
//	
//	public static String getRemaining(String player) {
//		if(!isBanned(player)) {
//			return player + "is not banned";
//		}
//		return utilTime.convertString(bansysMap.get(player) + bantimeMap.get(player).longValue() - System.currentTimeMillis(), TimeUnit.BEST, 1);
//	}
//	
//	public static double getRemInt(String player) {
//		if(!isBanned(player)) {
//			return 0.0;
//		}
//		return utilTime.convert(bansysMap.get(player) + bantimeMap.get(player).longValue() - System.currentTimeMillis(), TimeUnit.BEST, 1);
//	}
//	
//	public static boolean isBanned(String player) {
//		return banMap.containsKey(player);
//	}
//	
//	public static String getBanner(String player) {
//		ResultSet rs =Main.mysql.doQuery("SELECT banner FROM player_ban WHERE player='" + player + "'");
//		try {
//			if(rs.next()) {
//				return rs.getString(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public static void unban(String player, Player caller) {
//		Player banee = Bukkit.getServer().getPlayer(player);
//		if(banee != null) {
//			if(banMap.containsKey(banee.getName())) {
//				banMap.remove(banee.getName());
//				bantimeMap.remove(banee.getName());
//				bansysMap.remove(banee.getName());
//				banreasonMap.remove(banee.getName());
//				M.broadcast(ChatColor.GOLD + "Ban - " + ChatColor.DARK_AQUA + caller.getName() + ChatColor.WHITE + " unbanned " + ChatColor.DARK_AQUA + banee.getName());
//				Main.mysql.doUpdate("DELETE FROM player_ban WHERE player='" + player + "'");
//				return;
//			}
//		}
//		if(banMap.containsKey(player)) {
//			banMap.remove(player);
//			bantimeMap.remove(player);
//			bansysMap.remove(player);
//			banreasonMap.remove(player);
//			M.broadcast(ChatColor.GOLD + "Ban - " + ChatColor.DARK_AQUA + caller.getName() + ChatColor.WHITE + " unbanned " + ChatColor.DARK_AQUA + player);
//			Main.mysql.doUpdate("DELETE FROM player_ban WHERE player='" + player + "'");
//			return;
//		}
//		caller.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.DARK_AQUA + player + ChatColor.GRAY + " is not banned");
//	}
//	
//	public static void unban(String player) {
//		if(banMap.containsKey(player)) {
//			banMap.remove(player);
//			bantimeMap.remove(player);
//			bansysMap.remove(player);
//			banreasonMap.remove(player);
//			Main.mysql.doUpdate("DELETE FROM player_ban WHERE player='" + player + "'");
//			return;
//		}
//	}
//	
//	public static String getReason(String player) {
//		return banreasonMap.get(player);
//	}
//}
