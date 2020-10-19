package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class Blacksmith extends Ability {
	
	public Blacksmith() {
		super("Blacksmith", AbilityInfo.BLACKSMITH);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(!event.getCause().equals(DamageCause.LAVA)) return;
		
		AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
		Main.get().getServer().getPluginManager().callEvent(useEvent);
		if(useEvent.isCancelled()) return;
		
		player.getInventory().getHelmet().setDurability((short) (player.getInventory().getHelmet().getDurability() + 1));
		player.getInventory().getChestplate().setDurability((short) (player.getInventory().getChestplate().getDurability() + 1));
		player.getInventory().getLeggings().setDurability((short) (player.getInventory().getLeggings().getDurability() + 1));
		player.getInventory().getBoots().setDurability((short) (player.getInventory().getBoots().getDurability() + 1));
	}

}
