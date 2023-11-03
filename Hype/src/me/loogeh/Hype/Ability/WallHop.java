package me.loogeh.Hype.Ability;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Utility.utilPlayer;

public class WallHop extends Ability {

	public WallHop() {
		super("Wall Hop", AbilityInfo.WALL_HOP);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if(player.isSneaking()) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		Block block = utilPlayer.getTargetBlock(player, 2);
		if(block.getType().isSolid()) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			player.setVelocity(player.getLocation().getDirection().multiply(-1.85D));
			M.abilityUseMessage(player, getName());
		}
	}

}
