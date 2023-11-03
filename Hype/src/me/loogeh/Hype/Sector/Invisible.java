package me.loogeh.Hype.Sector;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Invisible extends Sector {

	private static HashMap<String, Long> invisiblePlayers = new HashMap<String, Long>();
	
	private static Invisible instance;

	public Invisible() {
		super("Invisible");
		register();
		instance = this;
	}
	
	public static Invisible get() {
		if(instance == null) instance = new Invisible();
		return instance;
	}

	public void add(Player player, long duration, boolean tell, boolean withDuration) {
		if(player == null) return;
		invisiblePlayers.put(player.getUniqueId().toString(), System.currentTimeMillis() + duration);
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.hidePlayer(player);
		}
		if(tell) {
			if(withDuration) message(player, "You are invisible for " + ChatColor.YELLOW + utilTime.convertString(duration, TimeUnit.BEST, 1));
			else message(player, "You have turned invisible");
		}
		player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 5);
	}

	public void remove(Player player, boolean tell) {
		if(invisiblePlayers.containsKey(player.getUniqueId().toString())) {
			invisiblePlayers.remove(player.getUniqueId().toString());
			for(Player players : Bukkit.getOnlinePlayers()) {
				players.showPlayer(player);
			}
			if(tell) message(player, "You have turned visible");
		}
		player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 5);
	}
	
	public boolean isInvisible(Player player) {
		return invisiblePlayers.containsKey(player.getUniqueId().toString());
	}

	public void update() {
		for(Entry<String, Long> entry : invisiblePlayers.entrySet()) {
			if(entry.getValue() != 0L) {
				Player player = Bukkit.getPlayer(UUID.fromString(entry.getKey()));
				if(System.currentTimeMillis() > entry.getValue()) {
					if(player != null) remove(player, true);
				}
			}

		}
	}

	public void load() {
	}

	@EventHandler
	public void onInvisibleServerTick(TickEvent event) {
		if(event.getType().equals(TickType.HALF)) update();
	}
	
	@EventHandler
	public void onInvisiblePlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(isInvisible(player)) remove(player, false);
	}

}
