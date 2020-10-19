package me.loogeh.Hype.Ability;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilPlayer;

public class Tremor extends Ability {

	public Tremor() {
		super("Tremor", AbilityInfo.TREMOR);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {

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
	
	@EventHandler
	public void onServerTick(TickEvent event) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			Member member = Member.get(player);
			if(member.getClasses().isUseable(getInfo())) {
				if(!member.getClasses().hasAbilityElapsed(getInfo())) {
					List<Player> nearby = utilPlayer.getNearbyPlayers(player, 6);
					for(Player near : nearby) {
						near.damage(0);
					}
				}
			}
		}
	}

}
