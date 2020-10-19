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
public class Mute {
	
}
//
//	private static HashMap<String, Mute> muteMap = new HashMap<String, Mute>();
//	
//	private String player; //player who's muted
//	private String muter; //the person who muted the muted player
//	private long mute_time; //the time at which they were muted (milliseconds)
//	private double mute_duration; //duration of the mute (hours)
//	private String reason; //reason for the mute
//	
//	public Mute(String player, String muter, long mute_time, double mute_duration, String reason) {
//		this.player = player;
//		this.muter = muter;
//		this.mute_time = mute_time;
//		this.mute_duration = mute_duration;
//		this.reason = reason;
//	}
//	
//	public Mute(String player, String muter, double mute_duration, String reason) {
//		this.player = player;
//		this.muter = muter;
//		this.mute_time = System.currentTimeMillis();
//		this.mute_duration = mute_duration;
//		this.reason = reason;
//	}
//	
//	public String getPlayer() {
//		return this.player;
//	}
//	
//	public long getTime() {
//		return this.mute_time;
//	}
//	
//	public double getDuration() {
//		return this.mute_duration;
//	}
//	
//	public long getDurationMillis() {
//		return Math.round(getDuration() * 3600000);
//	}
//	
//	public String getReason() {
//		return this.reason;
//	}
//	
//	public String getMuter() {
//		return this.muter;
//	}
//	
//	public long getRemaining() {
//		return (getTime() + getDurationMillis()) - System.currentTimeMillis();
//	}
//	
//	public String getRemainingString() {
//		return utilTime.convertString(getRemaining(), TimeUnit.BEST, 1);
//	}
//	
//	public double getRemaining(TimeUnit unit) {
//		return utilTime.convert((getTime() + getDurationMillis()) - System.currentTimeMillis(), unit, 1);
//	}
//	
//	public String getRemainingString(TimeUnit unit) {
//		return utilTime.convertString((getTime() + getDurationMillis()) - System.currentTimeMillis(), unit, 1);
//	}
//
//	public static void load() {
//		ResultSet rs = Main.mysql.doQuery("SELECT player,muter,reason,hours,mutetime,systime FROM player_mute");
//		int count = 0;
//		int disposed = 0;
//		try {
//			while(rs.next()) {
//				if((rs.getLong(5) + rs.getLong(4)) - System.currentTimeMillis() > 0) {
//					Mute mute = new Mute(rs.getString(1), rs.getString(2), rs.getLong(5), rs.getDouble(4), rs.getString(3));
//					muteMap.put(rs.getString(1), mute);
//					count++;
//				} else {
//					Main.mysql.doUpdate("DELETE FROM player_mute WHERE player='" + rs.getString(1) + "'");
//					disposed++;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		Main.logger.log(Level.INFO, "Mutes > Loaded " + count + " mute(s)");
//		if(disposed > 0) Main.logger.log(Level.INFO, "Mutes > Successfully disposed of " + disposed + " mute(s)");
//		
//	}
//	
//	public static void mute(String player, Player muter, double duration, String reason) {
//		Player mutee = Bukkit.getServer().getPlayer(player);
//		if(!Permissions.isStaff(muter.getName())) {
//			M.sendLackPermsMessage(muter);
//			return;
//		}
//		if(player == null) {
//			muter.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "That player " + ChatColor.WHITE + "[" + ChatColor.YELLOW + player + ChatColor.WHITE + "]" + ChatColor.GRAY + " is offline");
//			return;
//		}
//		long millis = (long) duration * 3600000;
//		if(mutee == null) {
//			if(isMuted(player)) {
//				muter.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "The player " + ChatColor.WHITE + "[" + ChatColor.YELLOW + player + ChatColor.WHITE + "]" + ChatColor.GRAY + " is already muted for " + getMute(player).getRemaining());
//				return;
//			}
//			if(!Permissions.outranks(muter.getName(), player)) {
//				muter.sendMessage(ChatColor.GRAY + "You must outrank" + ChatColor.WHITE + " [" + ChatColor.YELLOW + player + ChatColor.WHITE + "]" + ChatColor.GRAY + " to mute them");
//				return;
//			}
//			Main.mysql.doUpdate("INSERT INTO `player_mute`(`player`, `muter`, `rank`, `reason`, `hours`, `mutetime`, `systime`, `date`) VALUES ('" + player + "','" + muter.getName() + "','" + Permissions.getLevel(muter.getName()) + "','" + reason + "'," + duration + "," + (duration * 3600000.0) + "," + System.currentTimeMillis() + ",'" + utilTime.timeStr() + "')");
//			Mute mute = new Mute(player, muter.getName(), duration, reason);
//			muteMap.put(player, mute);
//			if(duration >= 9999999999.0D) {
//				M.broadcast(ChatColor.DARK_AQUA + muter.getName() + ChatColor.YELLOW + " muted " + ChatColor.DARK_AQUA + player + ChatColor.YELLOW + " permanently for " + ChatColor.DARK_AQUA + reason);
//				return;
//			}
//			M.broadcast(ChatColor.DARK_AQUA + muter.getName() + ChatColor.YELLOW + " muted " + ChatColor.DARK_AQUA + player + ChatColor.YELLOW + " [" + ChatColor.AQUA + utilTime.convertString(millis, TimeUnit.BEST, 1) + ChatColor.YELLOW + "] for " + ChatColor.DARK_AQUA + reason);
//		}
//		if(isMuted(mutee)) {
//			muter.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "The player " + ChatColor.WHITE + "[" + ChatColor.YELLOW + mutee.getName() + ChatColor.WHITE + "]" + ChatColor.GRAY + " is already muted for " + getMute(player).getRemaining());
//			return;
//		}
//		if(!Permissions.outranks(muter.getName(), mutee.getName())) {
//			muter.sendMessage(ChatColor.GRAY + "You must outrank" + ChatColor.WHITE + " [" + ChatColor.YELLOW + mutee.getName() + ChatColor.WHITE + "]" + ChatColor.GRAY + " to mute them");
//			return;
//		}
//		Main.mysql.doUpdate("INSERT INTO `player_mute`(`player`, `muter`, `rank`, `reason`, `hours`, `mutetime`, `systime`, `date`) VALUES ('" + mutee.getName() + "','" + muter.getName() + "','" + Permissions.getLevel(muter.getName()) + "','" + reason + "'," + duration + "," + (duration * 3600000.0) + "," + System.currentTimeMillis() + ",'" + utilTime.timeStr() + "')");
//		Mute mute = new Mute(player, muter.getName(), duration, reason);
//		muteMap.put(player, mute);
//		if(duration >= 9999999999.0D) {
//			M.broadcast(ChatColor.DARK_AQUA + muter.getName() + ChatColor.YELLOW + " muted " + ChatColor.DARK_AQUA + mutee.getName() + ChatColor.YELLOW + " permanently for " + ChatColor.DARK_AQUA + reason);
//			return;
//		}
//		M.broadcast(ChatColor.DARK_AQUA + muter.getName() + ChatColor.YELLOW + " muted " + ChatColor.DARK_AQUA + mutee.getName() + ChatColor.YELLOW + " [" + ChatColor.AQUA + utilTime.convertString(millis, TimeUnit.BEST, 1) + ChatColor.YELLOW + "] for " + ChatColor.DARK_AQUA + reason);
//	}
//
//	public static void unmute(String target, Player unmuter) {
//		Player player = Bukkit.getPlayer(target);
//		if(player != null) {
//			if(muteMap.containsKey(player.getName())) muteMap.remove(player.getName());
//			Main.mysql.doUpdate("DELETE FROM player_mute WHERE player='" + player.getName() + "'");
//			M.broadcast(ChatColor.GOLD + "Mute - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was unmuted by " + ChatColor.YELLOW + unmuter.getName());
//			return;
//		}
//		if(!muteMap.containsKey(target)) {
//			unmuter.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.YELLOW + target + ChatColor.WHITE + " is not muted");
//			return;
//		}
//		if(muteMap.containsKey(target)) muteMap.remove(target);
//		Main.mysql.doUpdate("DELETE FROM player_mute WHERE player='" + target + "'");
//		M.broadcast(ChatColor.GOLD + "Mute - " + ChatColor.YELLOW + target + ChatColor.WHITE + " was unmuted by " + ChatColor.YELLOW + unmuter.getName());
//	}
//	
//	public static void unmute(String player) {
//		if(muteMap.containsKey(player)) muteMap.remove(player);
//		Main.mysql.doUpdate("DELETE FROM player_mute WHERE player='" + player + "'");
//	}
//
//	public static boolean isMuted(Player player) {
//		if(!muteMap.containsKey(player.getName())) return false;
//		Mute mute = muteMap.get(player.getName());
//		if(mute.getRemaining() < 1) {
//			unmute(player.getName());
//			return false;
//		}
//		return true;
//	}
//	
//	public static boolean isMuted(String player) {
//		if(!muteMap.containsKey(player)) return false;
//		Mute mute = muteMap.get(player);
//		if(mute.getRemaining() < 1) {
//			unmute(player);
//			return false;
//		}
//		return true;
//	}
//	
//	public static Mute getMute(String player) {
//		if(!isMuted(player)) return null;
//		return muteMap.get(player);
//	}
//}
