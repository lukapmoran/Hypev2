package me.loogeh.Hype.Ability;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;

public class InfernoAxe extends Ability {

	public InfernoAxe() {
		super("Inferno Axe", AbilityInfo.INFERNO_AXE);
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(!getInfo().getSet().equals(Armour.getKit(player))) return;
			if(!getInfo().getType().holdingRequiredItem(player)) return;
			Member member = Member.get(player);
			if(member.getClasses().isUseable(getInfo())) {
				if(!getInfo().getSet().equals(Armour.getKit(player))) return;

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(event.getEntity() instanceof Player) {
					Player target = (Player) event.getEntity();
					target.setFireTicks(target.getFireTicks() + 50);
					M.message(player, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " hit you with an " + ChatColor.YELLOW + getName());
					return;
				}
				event.getEntity().setFireTicks(event.getEntity().getFireTicks() + 50);
			}
		}
	}

}
