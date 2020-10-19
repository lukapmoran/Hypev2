package me.loogeh.Hype.Glitch;

import me.loogeh.Hype.Main.Main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GlitchListener implements Listener {
	Main plugin;
	
	public GlitchListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onGlitchBlockBreak(BlockBreakEvent event) {
		if(!event.isCancelled()) return;
		Player player = event.getPlayer();
		Block pblock = player.getLocation().getBlock();
		Block block = event.getBlock();
		if(!pblock.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) return;
		if(block.getY() != pblock.getY() + 2) return;
		if((block.getX() != pblock.getX()) && (block.getZ() != pblock.getZ())) return;
		if((Math.abs(block.getX() - pblock.getX()) != 1) && (Math.abs(block.getZ() - pblock.getZ()) != 1)) return;
		
		player.teleport(player.getLocation().add(0.5D, 0, 0.5D));
			
	}
}
