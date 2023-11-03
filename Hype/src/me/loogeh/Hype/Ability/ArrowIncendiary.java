package me.loogeh.Hype.Ability;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class ArrowIncendiary extends Ability {

	public ArrowIncendiary() {
		super("Noxious Arrow", AbilityInfo.ARROW_INCENDIARY);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(!getInfo().getSet().equals(Armour.getKit(player))) return;
			if(!getInfo().getType().holdingRequiredItem(player)) return;
			if(!member.getClasses().getArrowDrawn().equals(getInfo())) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(Cooldown.isCooling(player.getName(), getName())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
				M.message(player, "Ability", ChatColor.WHITE + "You drew a " + ChatColor.YELLOW + getName());
				member.getClasses().setArrowDrawn(getInfo());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onArrowShoot(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Member member = Member.get(player);
			if(member.getClasses().isUseable(getInfo())) {
				if(!getInfo().getSet().equals(Armour.getKit(player))) return;
				if(!member.getClasses().getArrowDrawn().equals(getInfo())) {

					AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
					Main.get().getServer().getPluginManager().callEvent(useEvent);
					if(useEvent.isCancelled()) return;
					
					event.getProjectile().setMetadata("incendiaryArrow", new FixedMetadataValue(Main.get(), Boolean.valueOf(true)));
					M.message(player, "Ability", ChatColor.WHITE + "You fired a " + ChatColor.YELLOW + getName());
					member.getClasses().setArrowDrawn(AbilityInfo.NONE);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
			Player player = (Player) event.getEntity();
			if(!event.isCancelled()) {
				if(event.getDamager().hasMetadata("incendiaryArrow")) {
					M.message(player, "Ability", ChatColor.WHITE + "You were hit by a " + ChatColor.YELLOW + getName());
					player.setFireTicks(player.getFireTicks() + 60);
				}
			}
		}
	}

}
