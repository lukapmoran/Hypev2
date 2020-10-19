package me.loogeh.Hype.Ability;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilPlayer;

public class MagneticHaul extends Ability {

	private HashMap<String, Long> activePlayers = new HashMap<String, Long>();
	
	public MagneticHaul() {
		super("Magnetic Haul", AbilityInfo.MAGNETIC_HAUL);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(!getInfo().getSet().equals(Armour.getKit(player))) return;
			if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
			if(getInfo().getType().holdingRequiredItem(player)) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(Cooldown.isCooling(player.getName(), getName())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
				member.getClasses().addActiveAbility(getInfo());
				activePlayers.put(player.getUniqueId().toString(), System.currentTimeMillis());
				M.abilityUseMessage(player, getName());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.FIFTH)) {
			for(String active : activePlayers.keySet()) {
				long start = activePlayers.get(active);
				if(System.currentTimeMillis() - start < getInfo().getDuration()) {
					Player player = Bukkit.getPlayer(UUID.fromString(active));
					if(player != null) {
						List<Player> playersNear = utilPlayer.getNearbyPlayers(player, 5);
						for(Player near : playersNear) {
							Vector velocity = near.getLocation().toVector().add(player.getLocation().toVector());
							player.setVelocity(velocity.normalize().multiply(2.0D));
						}
					}
					
				} else activePlayers.remove(active);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(activePlayers.containsKey(player.getUniqueId().toString())) activePlayers.remove(player.getUniqueId().toString());
	}

}
