package me.loogeh.Hype.Ability;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
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
import me.loogeh.Hype.Event.PlayerMoveBlockEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;

public class FrostAxe extends Ability {

	private HashMap<Location, Long> affectedBlocks = new HashMap<Location, Long>();
	
	public FrostAxe() {
		super("Frost Axe", AbilityInfo.FROST_AXE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
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
			member.getClasses().addActiveAbility(getInfo());
			M.abilityUseMessage(player, getName());
		}
	}
	
	@EventHandler
	public void onPlayerMoveBlock(PlayerMoveBlockEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(!member.getClasses().hasAbilityElapsed(getInfo())) {
				Location location = player.getLocation();
				int cx = location.getBlockX();
				int cy = location.getBlockY();
				int cz = location.getBlockZ();
				int r = 6;
				for(int x = cx - r; x < cx + r; x++) {
					for(int y = cy - 2; y < cy + 2; y++) {
						for(int z = cz - r; z < cz + r; z++) {
							double d = ((cx - x) * (cx - x)) + ((cz - z) * (cz - z));
							if((int) d < r * r) {
								Block block = player.getWorld().getBlockAt(x, y, z);
								if(block.getType().equals(Material.WATER) || block.getType().equals(Material.STATIONARY_WATER)) {
									if(block.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
										block.setType(Material.ICE);
										affectedBlocks.put(block.getLocation(), System.currentTimeMillis());
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.HALF)) {
			Iterator<Location> it = affectedBlocks.keySet().iterator();
			while(it.hasNext()) {
				Location location = it.next();
				long time= affectedBlocks.get(location);
				if(System.currentTimeMillis() - time > 3000L) {
					location.getBlock().setType(Material.WATER);
					it.remove();
				}
			}
		}
	}
	

}
