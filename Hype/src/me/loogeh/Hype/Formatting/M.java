package me.loogeh.Hype.Formatting;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class M {
	
	public static String getCooldownMessage(String variable) {
		return ChatColor.BLUE + "Cooldown - " + ChatColor.WHITE + "You can now use " + ChatColor.YELLOW + WordUtils.capitalize(variable.toLowerCase());
	}
	
	public static void message(Player player, String subject, String message) {
		player.sendMessage(C.Blue + subject + " - " + message);
	}
	
	public static void channelMessage(String sender, Player reciever, String channel, String message) {
		reciever.sendMessage(ChatColor.DARK_GREEN + sender + " > " + ChatColor.GREEN + message);
	}
	
	public static void broadcast(String subject, String message) {
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA + subject + " > " + ChatColor.AQUA + message);
	}
	
	public static void broadcastServer(String player, String message) {
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Server " + ChatColor.AQUA + player + ChatColor.DARK_AQUA + " > " + ChatColor.AQUA + message);
	}
	
	public static void broadcast(String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}

	public static String getUsage(String usage) {
		return ChatColor.BLUE + "Usage - " + ChatColor.YELLOW + usage;
	}
	
	public static void sendHelpMessage(Player player, String command, String description) {
		player.sendMessage(ChatColor.BLUE + command + ChatColor.WHITE + " " + description);
	}
	
	public static void sendLackPermsMessage(Player player) {
		message(player, "Permissions", ChatColor.WHITE + "You lack permission to do this");
	}
	
	public static void abilityUseMessage(Player player, String ability) {
		message(player, "Ability", ChatColor.WHITE + "You used " + ChatColor.YELLOW + ability);
	}
	
	public static void itemUseMessage(Player player, String ability) {
		message(player, "Item", ChatColor.WHITE + "You used " + ChatColor.YELLOW + ability);
	}
	
	@SuppressWarnings("deprecation")
	public static void debug(String sector, String message) {
		Player player = Bukkit.getPlayerExact("Loogeh");
		if(player == null) return;
		message(player, "Debug " + sector, message);
	}
	
}
