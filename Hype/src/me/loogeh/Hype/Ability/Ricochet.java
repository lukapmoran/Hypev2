package me.loogeh.Hype.Ability;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class Ricochet extends Ability {

	public Ricochet() {
		super("Ricochet", AbilityInfo.RICOCHET);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(getInfo().getType().holdingRequiredItem(player)) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;

			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			member.getClasses().addActiveAbility(getInfo());
			M.abilityUseMessage(player, getName());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			if(event.isCancelled()) return;
			Player player = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			Member member = Member.get(player);
			if(!member.getClasses().isUseable(getInfo())) return;
			if(!member.getClasses().hasAbilityElapsed(getInfo())) {
				event.setCancelled(true);
				damager.damage(event.getDamage());
				M.message(damager, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " deflected your attack with " + ChatColor.YELLOW + getName());
			}
			
		}
	}

}
