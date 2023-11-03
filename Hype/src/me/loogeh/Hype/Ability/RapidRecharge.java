package me.loogeh.Hype.Ability;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.CooldownReceiveEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilMath;

public class RapidRecharge extends Ability {

	public RapidRecharge() {
		super("Rapid Recharge", AbilityInfo.RAPID_RECHARGE);
	}

	@EventHandler
	public void onCooldownReceive(CooldownReceiveEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(utilMath.getChance(15)) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;

				double amount = event.getDuration() * 0.75;
				double regAmount = event.getDuration();
				event.setDuration((long) amount);
				M.message(player, "Ability", ChatColor.YELLOW + "Rapid Recharge " + ChatColor.WHITE + "reduced " + ChatColor.YELLOW + event.getName() + ChatColor.WHITE + " by " + ChatColor.YELLOW + (regAmount - amount) + " Seconds");
				return;
			}
		}
	}

}
