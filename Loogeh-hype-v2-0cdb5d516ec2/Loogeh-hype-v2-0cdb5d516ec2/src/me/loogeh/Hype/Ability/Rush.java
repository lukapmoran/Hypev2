package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.potion.PotionEffectType;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Miscellaneous.P;
import me.loogeh.Hype.Sector.Member;

public class Rush extends Ability {

	public Rush() {
		super("Rush", AbilityInfo.RUSH);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(!Armour.getKit(player).equals(getInfo().getSet())) return;
			if(!getInfo().getType().holdingRequiredItem(player)) return;
			
			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			P.addPotionEffect(player, PotionEffectType.SPEED, 100, 0);
			M.abilityUseMessage(player, getName());
		}
	}
	
}
