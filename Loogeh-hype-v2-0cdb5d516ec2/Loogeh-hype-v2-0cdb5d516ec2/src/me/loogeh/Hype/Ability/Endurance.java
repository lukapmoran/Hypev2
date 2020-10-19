package me.loogeh.Hype.Ability;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class Endurance extends Ability {

	public Endurance() {
		super("Endurance", AbilityInfo.ENDURANCE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(!Armour.getKit(player).equals(getInfo().getSet())) return;
			if(!event.getCause().equals(DamageCause.LAVA)) return;
			Member member = Member.get(player);
			if(member.getClasses().isUseable(getInfo())) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				event.setCancelled(true);
				player.getWorld().playEffect(player.getEyeLocation().add(0, 1, 0), Effect.STEP_SOUND, Material.BLAZE_POWDER);
			}
		}
	}

}
