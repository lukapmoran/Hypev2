package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class Toughness extends Ability {

	/* This checks if the player doesn't have toughness
	 * then reduces the damage if they don't instead of
	 * checking if they have it and increasing it */
	
	public Toughness() {
		super("Toughness", AbilityInfo.TOUGHNESS);
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if(!getInfo().getSet().equals(Armour.getKit(damager))) return;
			Member member = Member.get(damager);
			if(!member.getClasses().isUseable(getInfo())) {

				AbilityUseEvent useEvent = new AbilityUseEvent(damager, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				event.setDamage(event.getDamage() * 0.8D);
			}
		}
	}

}
