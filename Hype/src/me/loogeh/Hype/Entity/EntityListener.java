package me.loogeh.Hype.Entity;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilMath;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {
	Main plugin;
	
	public EntityListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		event.setDroppedExp(0);
		if(event.getEntity() instanceof Creeper) {
			event.getDrops().clear();
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.COAL, utilMath.getRandom(0, 3)));
		}
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		for(SeizureSheep sheep : SeizureSheep.seizureSheepSet) {
			if(entity.getUniqueId().compareTo(sheep.getEntity().getUniqueId()) == 0) event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntitySheepShear(PlayerShearEntityEvent event) {
		for(SeizureSheep sheep : SeizureSheep.seizureSheepSet) {
			if(event.getEntity().getUniqueId().compareTo(sheep.getEntity().getUniqueId()) == 0) event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityPlayerInteractEntity(PlayerInteractEntityEvent event) {
		for(SeizureSheep sheep : SeizureSheep.seizureSheepSet) {
			if(event.getRightClicked().getUniqueId().compareTo(sheep.getEntity().getUniqueId()) == 0) event.setCancelled(true);
		}
	}
}
