package me.loogeh.Hype.Ability;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffectType;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.P;
import me.loogeh.Hype.Sector.Member;

public class IncrementalUpsurge extends Ability {

	public IncrementalUpsurge() {
		super("Incremental Upsurge", AbilityInfo.INCREMENTAL_UPSURGE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.FIFTH)) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				Member member = Member.get(player);
				if(member.getClasses().isUseable(getInfo())) {
					if(!player.isSprinting()) continue;
					if(System.currentTimeMillis() - member.getClasses().getLastSprint() > 5000L) {
						member.getClasses().setLastSprint(System.currentTimeMillis());

						AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
						Main.get().getServer().getPluginManager().callEvent(useEvent);
						if(useEvent.isCancelled()) {
							member.getClasses().setLastSprint(0L);
							return;
						}
						
						if(P.getAmplifier(player, PotionEffectType.SPEED) < 2) P.increaseAmplifier(player, PotionEffectType.SPEED, 120, 1);
						else P.extendDurationForce(player, PotionEffectType.SPEED, 120, 2);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(player.isSprinting()) {
			member.getClasses().setLastSprint(0L);
			if(member.getClasses().isUseable(getInfo())) player.removePotionEffect(PotionEffectType.SPEED);
			return;
		}
		member.getClasses().setLastSprint(System.currentTimeMillis());
	}
	
}
