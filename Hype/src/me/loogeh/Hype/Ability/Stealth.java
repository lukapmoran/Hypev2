package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.PlayerMoveBlockEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class Stealth extends Ability {

	public Stealth() {
		super("Stealth", AbilityInfo.STEALTH);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			player.setSneaking(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerMoveBlock(PlayerMoveBlockEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) {
			if(player.isSneaking() && player.isSprinting()) player.setSneaking(false);
		}
	}

}
