package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class TripleShot extends Ability {
	
	public TripleShot() {
		super("Triple Shot", AbilityInfo.TRIPLE_SHOT);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(member.getClasses().isUseable(getInfo())) {
			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			if(getInfo().getType().holdingRequiredItem(player)) {
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			}
		}
	}

}
