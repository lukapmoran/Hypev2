package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.potion.PotionEffectType;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.P;
import me.loogeh.Hype.Sector.Member;

public class Adrenaline extends Ability {

	public Adrenaline() {
		super("Adrenaline", AbilityInfo.ADRENALINE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.HALF)) {
			for(Player player : Main.get().getServer().getOnlinePlayers()) {
				Member member = Member.get(player);
				if(member.getClasses().isUseable(getInfo())) {
					if(!getInfo().getSet().equals(Armour.getKit(player))) return;
					
					AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
					Main.get().getServer().getPluginManager().callEvent(useEvent);
					if(useEvent.isCancelled()) return;
					
					if(player.getHealth() >= 1.5 && player.getHealth() <= 3.0) P.forcePotionEffect(player, PotionEffectType.REGENERATION, 20, 0);
					else if(player.getHealth() < 1.5) P.forcePotionEffect(player, PotionEffectType.REGENERATION, 20, 1);
				}
			}
		}
	}

}
