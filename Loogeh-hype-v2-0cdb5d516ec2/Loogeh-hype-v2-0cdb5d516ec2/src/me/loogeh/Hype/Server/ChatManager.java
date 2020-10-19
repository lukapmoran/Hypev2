package me.loogeh.Hype.Server;

import java.util.HashMap;

import me.loogeh.Hype.Main.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {
	
	public static HashMap<String, Long> lastChat = new HashMap<String, Long>();
	

	@SuppressWarnings("deprecation")
	public static boolean getCanChat(String player) {
		Player p_player = Bukkit.getPlayer(player);
		if(p_player == null) {
			if(lastChat.containsKey(player)) lastChat.remove(player);
			return false;
		}
		if(!lastChat.containsKey(p_player.getName())) return true;
		else {
			if((System.currentTimeMillis() - lastChat.get(p_player.getName()) > getChatCooldown())) {
				lastChat.remove(p_player.getName());
				return true;
			} else return false;
		}
	}
	
	public static int getChatCooldown() {
		return Main.config.getInt("server.chat_cooldown");
	}
}
