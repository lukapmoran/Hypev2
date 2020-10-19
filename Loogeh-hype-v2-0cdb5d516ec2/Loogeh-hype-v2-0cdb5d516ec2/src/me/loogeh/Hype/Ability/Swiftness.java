package me.loogeh.Hype.Ability;

import org.bukkit.Bukkit;
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

public class Swiftness extends Ability {

	public Swiftness() {
		super("Haste", AbilityInfo.HASTE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.SECOND)) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				Member member = Member.get(player);
				if(member.getClasses().isUseable(getInfo())) {
					if(getInfo().getSet().equals(Armour.getKit(player))) {
						AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
						Main.get().getServer().getPluginManager().callEvent(useEvent);
						if(useEvent.isCancelled()) return;
						
						P.addPotionEffect(player, PotionEffectType.SPEED, 50, 1);
					}
				}
			}
		}
	}

}
