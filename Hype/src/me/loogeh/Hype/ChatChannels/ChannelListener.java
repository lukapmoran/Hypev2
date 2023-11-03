package me.loogeh.Hype.ChatChannels;

import me.loogeh.Hype.Main.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChannelListener implements Listener {
	public Main plugin;
	
	public ChannelListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Channel active = ChannelManager.getActiveChannel(player.getName());
		if(active != null) ChannelManager.setInactive(player.getName(), active);
	}

}
