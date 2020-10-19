package me.loogeh.Hype.Region;

import me.loogeh.Hype.Event.PlayerMoveBlockEvent;
import me.loogeh.Hype.Event.RegionEnterEvent;
import me.loogeh.Hype.Event.RegionLeaveEvent;
import me.loogeh.Hype.Formatting.MessageFormatter;
import me.loogeh.Hype.Main.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class RegionListener implements Listener {
	Main plugin;

	public RegionListener(Main instance) {
		plugin = instance;
	}

	@EventHandler
	public void onRegionPlayerMoveBlock(PlayerMoveBlockEvent event) {
		if(RegionManager.regionSet.isEmpty()) return;
		Player player = event.getPlayer();
		Location from = event.getFrom();
		Location to = event.getTo();
		for(Region region : RegionManager.regionSet) {
			if(region.outskirts(to)) {
				MessageFormatter formatter = new MessageFormatter(region.enterFormat, new String[]{"%regionname%"}, new String[]{region.name});
				RegionEnterEvent enterEvent = new RegionEnterEvent(player, from, to, region, formatter.format());
				Bukkit.getServer().getPluginManager().callEvent(enterEvent);
				if(enterEvent.isCancelled()) {
					if(region instanceof CuboidRegion) {
//						CuboidRegion cuboid = (CuboidRegion) region;
					}
					if(region instanceof CylinderRegion) {
						CylinderRegion cylinder = (CylinderRegion) region;
						Vector velocity = player.getLocation().toVector().add(cylinder.getCenter().toVector());
						player.setVelocity(velocity.normalize().multiply(2.0D));
					}
					if(region instanceof SphereRegion) {
						SphereRegion sphere = (SphereRegion) region;
						Vector velocity = player.getLocation().toVector().add(sphere.getCenter().toVector());
						player.setVelocity(velocity);
					}
					player.teleport(from);
					return;
				}
				if(enterEvent.getMessage() != null) {
					if(region.hasFlag(RFlag.INFORM_ENTER)) player.sendMessage(enterEvent.getMessage());
				}
			}
			if(region.in(from) && !region.in(to)) {
				MessageFormatter formatter = new MessageFormatter(region.leaveFormat, new String[]{"%regionname%"}, new String[]{region.name});
				player.sendMessage(formatter.format());
				RegionLeaveEvent leaveEvent = new RegionLeaveEvent(player, from, to, region, formatter.format());
				Bukkit.getServer().getPluginManager().callEvent(leaveEvent);
				if(leaveEvent.isCancelled()) {
					player.teleport(from);
					return;
				}
				if(leaveEvent.getMessage() != null) {
					if(region.hasFlag(RFlag.INFORM_LEAVE)) player.sendMessage(leaveEvent.getMessage());
				}
			}
		}
	}

	@EventHandler
	public void onRegionEnter(RegionEnterEvent event) {
		Region region = event.getRegion();
		if(region.hasFlag(RFlag.DISABLE_ENTER)) event.setCancelled(true);
	}
	
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent event) {
		Region region = event.getRegion();
		if(region.hasFlag(RFlag.DISABLE_LEAVE)) event.setCancelled(true);
	}
}
