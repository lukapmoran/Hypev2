package me.loogeh.Hype.Ability;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.PlayerBlockEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilMath;

public class Boreas extends Ability {

	private HashMap<Location, Long> affectedBlocks = new HashMap<Location, Long>();

	public Boreas() {
		super("Boreas", AbilityInfo.BOREAS);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerBlock(PlayerBlockEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(getInfo().getType().holdingRequiredItem(player)) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;

			if(!member.getClasses().isPlayerBlocking()) {
				if(player.isBlocking()) {
					if(member.getClasses().hasAbilityElapsed(getInfo())) {
						if(Cooldown.isCooling(player.getName(), getName())) {
							Cooldown.sendRemaining(player, getName());
							return;
						}
						Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
						member.getClasses().addActiveAbility(getInfo());
						member.getClasses().setPlayerBlocking(true);
					}
				}
				if(member.getClasses().getSwordBlockCount() < 20) {
					if(member.getClasses().getSwordBlockCount() % 4 == 0) {
						Location location = player.getLocation();
						int cx = location.getBlockX();
						int cz = location.getBlockZ();
						int r = 6;
						for(int x = cx - r; x < cx + r; x++) {
							for(int z = cz - r; z < cz + r; z++) {
								double d = ((cx - x) * (cx - x)) + ((cz - z) * (cz - z));
								if((int) d < r * r) {
									Block block = player.getWorld().getBlockAt(x, location.getBlockY() - 1, z);
									if(block.getType().equals(Material.AIR)) {
										block.setType(Material.SNOW);
										if(d <= 4) block.setData((byte) (utilMath.r(1) + 2));
										else block.setData((byte) (utilMath.r(1) + 1));
										affectedBlocks.put(block.getLocation(), System.currentTimeMillis());
									}
								}
							}
						}
					}
					member.getClasses().setSwordBlockCount(member.getClasses().getSwordBlockCount() + 1);
				}
			} else {
				if(Cooldown.isCooling(player.getName(), getName()) && member.getClasses().hasAbilityElapsed(getInfo())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				if(member.getClasses().getSwordBlockCount() % 4 == 0) {
					Location location = player.getLocation();
					int cx = location.getBlockX();
					int cz = location.getBlockZ();
					int r = 6;
					for(int x = cx - r; x < cx + r; x++) {
						for(int z = cz - r; z < cz + r; z++) {
							double d = ((cx - x) * (cx - x)) + ((cz - z) * (cz - z));
							if((int) d < r * r) {
								Block block = player.getWorld().getBlockAt(x, location.getBlockY() - 1, z);
								if(block.getType().equals(Material.AIR)) {
									block.setType(Material.SNOW);
									if(d <= 4) block.setData((byte) (utilMath.r(1) + 2));
									else block.setData((byte) (utilMath.r(1) + 1));
									affectedBlocks.put(block.getLocation(), System.currentTimeMillis());
								}
							}
						}
					}
					member.getClasses().setSwordBlockCount(member.getClasses().getSwordBlockCount() + 1);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.HALF)) {
			Iterator<Location> it = affectedBlocks.keySet().iterator();
			while(it.hasNext()) {
				Location location = it.next();
				long time = affectedBlocks.get(location);
				if(System.currentTimeMillis() - time > 3000L) {
					
				}
			}
		}
	}

}
