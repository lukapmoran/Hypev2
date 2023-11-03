package me.loogeh.Hype.Ability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Item.Item;
import me.loogeh.Hype.Item.ThrowItem;
import me.loogeh.Hype.Item.ThrowItem.CollisionType;
import me.loogeh.Hype.Miscellaneous.DelayedTask;

public class ItemWeb extends Ability {

	public HashMap<Location, Long> affectedBlocks = new HashMap<Location, Long>();
	
	public ItemWeb() {
		super("Web", AbilityInfo.NONE);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(player.getItemInHand() == null) return;
		if(!player.getItemInHand().getType().equals(Material.WEB)) return;
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			new ThrowItem(player, Item.WEB.getItem(), player.getEyeLocation(), player.getLocation().getDirection(), 0.0D, Arrays.asList(CollisionType.BLOCK_DOWN, CollisionType.PLAYER)) {
				
				@Override
				public void performHit() {
					final List<Location> blocks = new ArrayList<Location>();
					Location location = this.getItem().getLocation();
					this.getItem().remove();
					int cx = location.getBlockX();
					int cz = location.getBlockZ();
					for(int x = cx; x < cx + 2; x++) {
						for(int z = cz; z < cz + 2; z++) {
							Block block = location.getWorld().getBlockAt(x, location.getBlockY(), z);
							if(block.getType().equals(Material.AIR) || block.getType().equals(Material.DEAD_BUSH) || block.getType().equals(Material.LONG_GRASS)) {
								block.setType(Material.WEB);
								blocks.add(block.getLocation());
							}
						}
					}
					location.getWorld().playEffect(location, Effect.STEP_SOUND, Material.WEB);
					new DelayedTask(3000L, new Runnable() {
						
						public void run() {
							Iterator<Location> it = blocks.iterator();
							while(it.hasNext()) {
								Location location = it.next();
								location.getBlock().setType(Material.AIR);
								it.remove();
							}
						}
					});
				}
			};
		}
	}
	
	@EventHandler
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.HALF)) {
			Iterator<Location> it = affectedBlocks.keySet().iterator();
			while(it.hasNext()) {
				Location location = it.next();
				long time = affectedBlocks.get(location);
				if(System.currentTimeMillis() - time > 2000L) {
					location.getBlock().setType(Material.AIR);
					it.remove();
				}
			}
		}
	}

}
