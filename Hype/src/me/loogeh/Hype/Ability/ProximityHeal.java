package me.loogeh.Hype.Ability;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class ProximityHeal extends Ability {

	public ProximityHeal() {
		super("Proximity Heal", AbilityInfo.PROXIMITY_HEAL);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(member.getClasses().isUseable(getInfo())) {
			
			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			if(getInfo().getType().holdingRequiredItem(player)) {
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
				int heal = 6;
				List<Entity> nearby = player.getNearbyEntities(7, 7, 7);
				for(Entity entity : nearby) {
					if(entity instanceof Player) {
						Player target = (Player) entity;
						if(target.getHealth() <= target.getMaxHealth() - heal) target.setHealth(target.getHealth() + heal);
						else target.setHealth(target.getMaxHealth());
						M.message(target, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " healed you of " + (heal / 2) + " hearts with " + ChatColor.YELLOW + "Proximity Heal");
					}
				}
			}
			M.abilityUseMessage(player, "Proximity Heal");
		}
	}

}
