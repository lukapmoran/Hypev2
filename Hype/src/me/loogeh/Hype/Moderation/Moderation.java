package me.loogeh.Hype.Moderation;
//
//import me.loogeh.Hype.Formatting.M;
//import me.loogeh.Hype.Main.Main;
//import me.loogeh.Hype.Moderation.Permissions.Ranks;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//
public class Moderation {
	
}
//	
//	public static boolean chat = true;
//	
//	public static void databaseCheck() {
//		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS player_ban (player TEXT, banner TEXT, rank TEXT, reason TEXT, hours DOUBLE, bantime BIGINT(20), systime BIGINT(20), date TEXT)");
//		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS player_mute (player TEXT, muter TEXT, rank TEXT, reason TEXT, hours DOUBLE, mutetime BIGINT(20), systime BIGINT(20), date TEXT)");
//	}
//	
//	public static void sendHelpMenu(Player player) {
//		if(Permissions.getRank(player.getName()).toInt() == 0) {
//			M.sendLackPermsMessage(player);
//			return;
//		}
//		M.message(player, "Help", ChatColor.WHITE + "Moderation Help");
//		M.sendHelpMessage(player, "/ban <player> <hours/perm> <reason>", "Bans a player");
//		M.sendHelpMessage(player, "/mute <player> <hours/perm> <reason>", "Mutes a player");
//		M.sendHelpMessage(player, "/permissions promote <player>", "Promotes a player");
//		M.sendHelpMessage(player, "/permissions demote <player>", "Demotes a player");
//		M.sendHelpMessage(player, "/permissions set <player> <rank>", "Sets a player's rank");
//		M.sendHelpMessage(player, "/server lock toggle", "Toggles the server lock");
//		M.sendHelpMessage(player, "/server lock add <player>", "Adds a player to the lock-exempt list");
//		M.sendHelpMessage(player, "/server chat <enable/disable>", "Enables or disables the chat");
//		M.sendHelpMessage(player, "/say <message>", "Broadcasts a message to the server");
//	}
//	
//	public static double getMaxModeratorMuteDuration() {
//		return Main.config.getDouble("moderation.moderator_max_mute_duration");
//	}
//	
//	public static double getMaxModeratorBanDuration() {
//		return Main.config.getDouble("moderation.moderator_max_ban_duration");
//	}
//	
//	public static void kick(Player player, String target, String reason) {
//		if(player == null) return;
//		if(!Permissions.isStaff(player.getName())) {
//			M.sendLackPermsMessage(player);
//			return;
//		}
//		Player p_target = Bukkit.getPlayer(target);
//		if(p_target == null) {
//			M.message(player, "Moderation", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target);
//			return;
//		}
//		if(!Permissions.outranks(player.getName(), p_target.getName())) {
//			M.message(player, "Moderation", ChatColor.WHITE + "You do not out rank " + ChatColor.YELLOW + p_target.getName());
//			return;
//		}
//		p_target.kickPlayer(ChatColor.BLUE + "Kicked - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " kicked you for " + ChatColor.YELLOW + reason);
//		Bukkit.broadcastMessage(ChatColor.BLUE + "Kick - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " kicked " + ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " for " + ChatColor.YELLOW + reason);
//	}
//	
//	
//	public static void lock(Player player, String reason) {
//		if(player == null) return;
//		if(Permissions.getRank(player.getName()) != Ranks.OWNER) return;
//		if(Main.plugin.getServer().hasWhitelist()) {
//			Main.plugin.getServer().setWhitelist(false);
//			Main.config.set("server.lock_reason", null);
//			M.broadcast("Server", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " unlocked the server");
//		} else {
//			if(reason == null) return;
//			Main.plugin.getServer().setWhitelist(true);
//			Main.config.set("server.lock_reason", reason);
//			for(Player players : Bukkit.getOnlinePlayers()) {
//				if(!Permissions.isAdmin(players.getName())) {
//					players.kickPlayer(ChatColor.BLUE + "Server - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " locked the server for " + ChatColor.YELLOW + reason);
//				}
//			}
//			M.broadcast("Server", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " locked the server for " + ChatColor.YELLOW + reason);
//		}
//	}
//	
//}
