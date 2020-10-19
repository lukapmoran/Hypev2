package me.loogeh.Hype.Ability;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
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

public class ArrowGrappling extends Ability {

	public ArrowGrappling() {
		super("Grappling Arrow", AbilityInfo.ARROW_GRAPPEL);
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
				if(member.getClasses().getArrowDrawn().equals(getInfo())) {

					AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
					Main.get().getServer().getPluginManager().callEvent(useEvent);
					if(useEvent.isCancelled()) return;
					
					event.getProjectile().setMetadata("grapplingArrow", new FixedMetadataValue(Main.get(), Boolean.valueOf(true)));
					M.message(player, "Ability", ChatColor.WHITE + "You fired a " + ChatColor.YELLOW + getName());
					member.getClasses().setArrowDrawn(AbilityInfo.NONE);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onProjectileHit(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if(!arrow.hasMetadata("grapplingArrow")) return;
			if(arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();
				player.setVelocity(player.getLocation().getDirection().multiply(1.25D));
			}
		}
	}

}
