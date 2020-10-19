package me.loogeh.Hype.Ability;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class Switch extends Ability {

	public Switch() {
		super("Switch", AbilityInfo.SWITCH);
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		if(!(entity instanceof Player) || !(damager instanceof Player)) return;
		Player player = (Player) entity;
		Player pdamager = (Player) damager;
		Member member = Member.get(player);
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(!member.getClasses().isUseable(getInfo())) return;
		if(player.isBlocking()) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(!Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
				player.teleport(pdamager.getLocation());
				pdamager.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), pdamager.getLocation().getYaw(), pdamager.getLocation().getPitch()));
				M.abilityUseMessage(player, "Switch");
				M.message(pdamager, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " used " + ChatColor.YELLOW + getName());
			}
		}
	}
	
}
