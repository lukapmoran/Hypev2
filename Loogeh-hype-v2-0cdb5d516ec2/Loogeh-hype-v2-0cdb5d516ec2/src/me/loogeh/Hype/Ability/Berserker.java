package me.loogeh.Hype.Ability;

import org.bukkit.entity.Entity;
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

public class Berserker extends Ability {

	public Berserker() {
		super("Berserker", AbilityInfo.BERSERKER);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(member.getClasses().isUseable(getInfo())) return;
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(getInfo().getType().holdingRequiredItem(player)) {
			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			M.abilityUseMessage(player, getName());
			member.getClasses().addActiveAbility(getInfo());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if(!(damager instanceof Player)) return;
		Player player = (Player) damager;
		Member member = Member.get(player);
		if(!event.isCancelled()) {
			if(!member.getClasses().hasAbilityElapsed(getInfo())) {
				event.setDamage(event.getDamage() * 1.3D);
			}
		}
	}

}
