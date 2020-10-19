package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.event.EventHandler;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class Dodge extends Ability {

	public Dodge() {
		super("Dodge", AbilityInfo.DODGE);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member == null) return;
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(getInfo().getType().holdingRequiredItem(player)) {
			
			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			player.setVelocity(player.getLocation().getDirection().multiply(13.5D));
			M.abilityUseMessage(player, "Dodge");
		}
	}
	
}
