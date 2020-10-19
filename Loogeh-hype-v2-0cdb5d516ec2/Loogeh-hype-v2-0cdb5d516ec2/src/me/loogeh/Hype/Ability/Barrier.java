package me.loogeh.Hype.Ability;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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

public class Barrier extends Ability {

	public Barrier() {
		super("Barrier", AbilityInfo.BARRIER);
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
				M.abilityUseMessage(player, getName());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.FIFTH)) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				Member member = Member.get(player);
				if(!member.getClasses().hasAbilityElapsed(getInfo())) {
					List<Player> playersNear = utilPlayer.getNearbyPlayers(player, 5);
					for(Player near : playersNear) {
						near.setVelocity(player.getVelocity().subtract(near.getVelocity()));
					}
				}
			}
		}
	}

}
