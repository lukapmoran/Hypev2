package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class BreakFall extends Ability {

	public BreakFall() {
		super("Break Fall", AbilityInfo.BREAK_FALL);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(event.getCause().equals(DamageCause.FALL)) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				event.setCancelled(true);
			}
		}
	}

}
