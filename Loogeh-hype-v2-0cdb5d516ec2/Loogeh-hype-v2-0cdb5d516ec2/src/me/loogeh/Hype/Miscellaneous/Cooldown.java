package me.loogeh.Hype.Miscellaneous;

import java.util.HashMap;
import java.util.Iterator;

import me.loogeh.Hype.Event.CooldownReceiveEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Cooldown {
	private static Main plugin = Main.plugin;
	
	public static HashMap<String, AbilityCooldown> cooldownPlayers = new HashMap<String, AbilityCooldown>();
	
	@SuppressWarnings("deprecation")
	public static void add(String name, String ability, long seconds) {
		Player player = Bukkit.getPlayer(name);
		if(player == null) return;
		if(!cooldownPlayers.containsKey(name)) cooldownPlayers.put(name, new AbilityCooldown(name));
		if(isCooling(name, ability)) return;
		
		CooldownReceiveEvent event = new CooldownReceiveEvent(player, ability, seconds);
		Main.get().getServer().getPluginManager().callEvent(event);
		if(event.isCancelled()) return;
		
		seconds = event.getDuration();
		
		cooldownPlayers.get(name).cooldownMap.put(ability, new AbilityCooldown(name, seconds * 1000));
	}
	
	public static boolean isCooling(String player, String ability) {
		if(!cooldownPlayers.containsKey(player)) return false;
		if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return false;
		return true;
	}
	
	public static double getRemaining(String player, String ability) {
		if(!cooldownPlayers.containsKey(player)) return 0.0;
		if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return 0.0;
		return utilTime.convert((cooldownPlayers.get(player).cooldownMap.get(ability).seconds + cooldownPlayers.get(player).cooldownMap.get(ability).systime) - System.currentTimeMillis(), TimeUnit.BEST, 1);
	}
	
	public static String getRemainingString(String player, String ability) {
		if(!cooldownPlayers.containsKey(player)) return "0 Seconds";
		if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return "0 Seconds";
		return utilTime.convertString((cooldownPlayers.get(player).cooldownMap.get(ability).seconds + cooldownPlayers.get(player).cooldownMap.get(ability).systime) - System.currentTimeMillis(), TimeUnit.BEST, 1);
	}
	
	public static void sendRemaining(Player player, String ability) {
		if(player == null) return;
		if(!isCooling(player.getName(), ability)) return;
		M.message(player, "Cooldown", ChatColor.YELLOW + WordUtils.capitalize(ability.toLowerCase()) + " " + ChatColor.WHITE + getRemainingString(player.getName(), ability));
	}
	
	@SuppressWarnings("deprecation")
	public static void remove(String player, String ability) {
		if(!cooldownPlayers.containsKey(player)) return;
		if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return;
		cooldownPlayers.get(player).cooldownMap.remove(ability);
		Player cPlayer = Bukkit.getPlayer(player);
		if(cPlayer != null) cPlayer.sendMessage(M.getCooldownMessage(ability));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle() {
		if(cooldownPlayers.isEmpty()) return;
		Iterator<String> it = cooldownPlayers.keySet().iterator();
		while(it.hasNext()) {
			String player = it.next();
			Iterator<String> iter = cooldownPlayers.get(player).cooldownMap.keySet().iterator();
			while(iter.hasNext()) {
				String cooldown = iter.next();
				if(getRemaining(player, cooldown) <= 0.0D) {
					Player p = Bukkit.getPlayer(player);
					if(p != null) p.sendMessage(M.getCooldownMessage(cooldown));
					iter.remove();
				}
			}
		}
	}
	
	public static void startCooldownScheduler() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				handle();
			}
		}, 1L, 1L);
	}
}
