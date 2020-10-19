package me.loogeh.Hype.Server;

import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.UnknownCommand;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Squads.Squad.ClaimType;
import me.loogeh.Hype.Utility.utilMath;
import me.loogeh.Hype.Utility.utilServer;
import me.loogeh.Hype.World.WorldPowerUp;
import me.loogeh.Hype.World.WorldPowerUp.WPowerUp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class ServerListener implements Listener {
	Main plugin;
	
	public ServerListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onServerLeavesDecay(LeavesDecayEvent event) {
		Location location = event.getBlock().getLocation();
		if(!SquadManager.isClaimed(location.getChunk())) return;
		if(SquadManager.getClaimType(location.getChunk()) == ClaimType.SAFE) event.setCancelled(true);
	}
	
	@EventHandler
	public void onServerPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String cmd = event.getMessage();
		if(cmd.charAt(0) == '/') cmd = cmd.replaceFirst("/", "");
		cmd = cmd.split(" ")[0];
		if(!UnknownCommand.commandExists(cmd)) {
			event.setCancelled(true);
			M.message(player, "Server", ChatColor.WHITE + "Command doesn't exist " + ChatColor.YELLOW + "/help " + ChatColor.WHITE + "for help");
		}
	}
	
	@EventHandler
	public void onServerTickEvent(TickEvent event) {
		if(event.getType().equals(TickType.TWO_MINUTE)) {
			utilServer.printMemoryUsage();
		}
		if(event.getType().equals(TickType.MINUTE)) {
			if(utilMath.getChance(10)) {
				WorldPowerUp.spawn(WPowerUp.REGENERATION);
			}
		}
		World world = Bukkit.getWorld("world");
		if(world.getTime() > 14000 && world.getTime() < 24000) world.setTime(world.getTime() + 5);
		World game_world = Bukkit.getWorld("game_world");
		if(game_world.getTime() > 14000 && game_world.getTime() < 24000) game_world.setTime(game_world.getTime() + 5);
	}
	
	@EventHandler
	public void onServerListPingEvent(ServerListPingEvent event) {
		if(Main.plugin.getServer().hasWhitelist()) event.setMotd(C.boldBlue + "Locked > " + ChatColor.WHITE + "HypeMC is locked for " + ChatColor.YELLOW + Main.config.getString("server.lock_reason"));
		else event.setMotd(C.boldBlue + "HypeMC > " + ChatColor.WHITE + "Australian Minecraft Gameplay");
	}
	
	@EventHandler
	public void onServerStructureGrow(StructureGrowEvent event) {
		if(event.isFromBonemeal()) event.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onServerPlayerInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Block block = event.getClickedBlock();
		if(!block.getType().equals(Material.IRON_DOOR_BLOCK)) return;
		
		if(event.isCancelled()) {
			block.getWorld().playEffect(block.getLocation(), Effect.ZOMBIE_CHEW_WOODEN_DOOR, 0);
			return;
		}
		if(block.getData() >= 8) {
			block = block.getRelative(BlockFace.DOWN);
		}
		if(block.getData() < 4) block.setData((byte) (block.getData() + 4), true);
		else block.setData((byte) (block.getData() - 4), true);
		
	}
}
