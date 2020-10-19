package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class Leap extends Ability {

	public Leap() {
		super("Leap", AbilityInfo.LEAP);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(getInfo().getType().holdingRequiredItem(player)) {
			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			player.setVelocity(player.getLocation().getDirection().multiply(1.1D).add(new Vector(0, 0.3, 0)));
			M.abilityUseMessage(player, getName());
		}
	}

}
