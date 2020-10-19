package me.loogeh.Hype.Ability;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class Beam extends Ability {

	public Beam() {
		super("Beam", AbilityInfo.BEAM);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(getInfo().getType().holdingRequiredItem(player)) {
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(Cooldown.isCooling(player.getName(), getName())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				Location startLocation = player.getLocation();
				double range = 12.0D;
				Location targetLocation = player.getLocation().add(player.getLocation().getDirection().multiply(range));
				if(!targetLocation.getBlock().getType().isSolid()) {
					player.teleport(targetLocation);
					Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
					M.abilityUseMessage(player, getName());
					
					startLocation.getWorld().playEffect(startLocation, Effect.SMOKE, 5);
					player.getWorld().playEffect(player.getLocation(), Effect.EXTINGUISH, 0);
				}
				
			}
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				
				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(Cooldown.isCooling(player.getName(), getName())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				Block block = event.getClickedBlock();
				if(!block.getRelative(BlockFace.UP).getType().isSolid()) {
					Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
					player.teleport(block.getRelative(BlockFace.UP).getLocation());
					M.abilityUseMessage(player, getName());
					block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 5);
					player.getWorld().playEffect(player.getLocation(), Effect.EXTINGUISH, 0);
				}
				
			}
			
		}
	}

}
