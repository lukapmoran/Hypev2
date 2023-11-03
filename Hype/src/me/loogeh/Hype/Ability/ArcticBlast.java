package me.loogeh.Hype.Ability;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Item.Item;
import me.loogeh.Hype.Item.ThrowItem;
import me.loogeh.Hype.Item.ThrowItem.CollisionType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilMath;

public class ArcticBlast extends Ability {

	public ArcticBlast() {
		super("Arctic Blast", AbilityInfo.ARCTIC_BLAST);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(!getInfo().getSet().equals(Armour.getKit(player))) return;
			if(!getInfo().getType().holdingRequiredItem(player)) return;
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				if(Cooldown.isCooling(player.getName(), getName())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
				
				M.abilityUseMessage(player, getName());
				new ThrowItem(player, Item.ICE.getItem(), player.getEyeLocation(), player.getLocation().getDirection(), 0.0D, Arrays.asList(CollisionType.BLOCK, CollisionType.BLOCK_DOWN)) {
					
					public HashMap<Block, Long> arcticBlast = new HashMap<Block, Long>();
					
					@SuppressWarnings("deprecation")
					public void performHit() {
						int cx = this.getItem().getLocation().getBlockX();
						int cy = this.getItem().getLocation().getBlockY();
						int cz = this.getItem().getLocation().getBlockZ();
						World world = this.getItem().getWorld();
						this.getItem().remove();
						ThrowItem.thrownItems.remove(this);
						for(int x = cx - 5; x < cx + 5; x++) {
							for(int y = cy - 2; y < cy + 2; y++) {
								for(int z = cz - 5; z < cz + 5; z++) {
									final Location loc = new Location(world, x, y, z);
									double d = ((cx - x) * (cx - x)) + ((cz - z) * (cz - z));
									if(d < 25) {
										if(loc.getBlock().getType().equals(Material.AIR) || (loc.getBlock().getType().equals(Material.DEAD_BUSH)) && !loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR) && !loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SNOW) && !loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SAND)) {
											loc.getBlock().setType(Material.SNOW);
											loc.getBlock().setData((byte) utilMath.r(2));
											this.getItem().remove();
											arcticBlast.put(loc.getBlock(), System.currentTimeMillis());
										}
									}
								}
							}
						}
						Main.get().getServer().getScheduler().scheduleSyncDelayedTask(Main.get(), new Runnable() {
							
							public void run() {
								Iterator<Block> it = arcticBlast.keySet().iterator();
								while(it.hasNext()) {
									Block block = it.next();
									long elapsed = System.currentTimeMillis() - arcticBlast.get(block);
									if(elapsed > 2500) {
										block.setType(Material.AIR);
										it.remove();
									}
								}
							}
						}, 50L);
					}
				};
			}
		}
	}

}
