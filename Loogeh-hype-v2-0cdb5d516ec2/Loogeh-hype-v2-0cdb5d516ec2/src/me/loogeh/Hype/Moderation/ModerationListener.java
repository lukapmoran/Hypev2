package me.loogeh.Hype.Moderation;

import org.bukkit.event.Listener;

//
//import me.loogeh.Hype.Main.Main;
//
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerLoginEvent;
//import org.bukkit.event.player.PlayerLoginEvent.Result;
//
public class ModerationListener implements Listener {
	
}
//	public static Main plugin;
//	public ModerationListener(Main instance) {
//		plugin = instance;
//	}
//	
//	
//	@EventHandler
//	public void onModerationLoginEvent(PlayerLoginEvent event) {
//		Player player = event.getPlayer();
//		if(Ban.isBanned(player.getName())) {
//			if(Ban.getRemInt(player.getName()) <= 0.0) {
//				Ban.unban(player.getName());
//				return;
//			} else {
//				event.disallow(Result.KICK_OTHER, ChatColor.BLUE + "Banned - " + ChatColor.WHITE + "You are banned [" + ChatColor.YELLOW + Ban.getRemaining(player.getName()) + ChatColor.WHITE + "] for " + ChatColor.YELLOW + Ban.getReason(player.getName()));
//			}
//		}
//		if(event.getResult() == Result.KICK_WHITELIST) {
//			event.disallow(Result.KICK_WHITELIST, ChatColor.BLUE + "Locked - " + ChatColor.WHITE + "The server is locked for " + ChatColor.YELLOW + Main.config.getString("server.lock_reason"));
//		}
//	}
//
//}
