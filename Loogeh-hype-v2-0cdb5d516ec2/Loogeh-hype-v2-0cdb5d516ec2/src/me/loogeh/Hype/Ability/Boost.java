package me.loogeh.Hype.Ability;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.PlayerEnterWaterEvent;
import me.loogeh.Hype.Event.PlayerLeaveWaterEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class Boost extends Ability {

	public Boost() {
		super("Boost", AbilityInfo.BOOST);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerToggleFligh(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			event.setCancelled(false);
			return;
		}
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(player.getLocation().getBlock().getType().equals(Material.WATER) || player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)) {
				
				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(Cooldown.isCooling(player.getName(), getName())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
				player.setVelocity(player.getLocation().getDirection().multiply(1.6D));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerEnterWater(PlayerEnterWaterEvent event) { 
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(player.getGameMode().equals(GameMode.CREATIVE)) return;
			player.setAllowFlight(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLeaveWater(PlayerLeaveWaterEvent event) { 
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(player.getGameMode().equals(GameMode.CREATIVE)) return;
			player.setAllowFlight(false);
		}
	}
	
	

}
