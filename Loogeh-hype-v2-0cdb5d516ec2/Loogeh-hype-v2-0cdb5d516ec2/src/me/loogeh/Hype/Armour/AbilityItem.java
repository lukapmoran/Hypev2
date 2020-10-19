package me.loogeh.Hype.Armour;

import java.util.ArrayList;
import java.util.List;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.B;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Squads.Squad.ClaimType;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class AbilityItem {
	
	@SuppressWarnings("deprecation")
	public static void useLightningSceptre(Player player) {
		if(player == null) return;
		if(player.getItemInHand().getType() != getLightningSceptre()) return;
		if(!getLightningSceptreEnabled()) return;
		if(Cooldown.isCooling(player.getName(), "Lightning Sceptre")) {
			Cooldown.sendRemaining(player, "Lightning Sceptre");
			return;
		}
		Cooldown.add(player.getName(), "Lightning Sceptre", getLightningSceptreCooldown());
		Location target_loc = player.getTargetBlock(null, 50).getLocation();
		if(SquadManager.isClaimed(target_loc.getChunk())) {
			if(SquadManager.getClaimType(target_loc.getChunk()) == ClaimType.SAFE) {
				M.message(player, "Item", ChatColor.WHITE + "You cannot " + ChatColor.YELLOW + "Strike Lightning " + ChatColor.WHITE + "in a " + ChatColor.YELLOW + "Safe Zone");
				return;
			}
		}
		player.getWorld().strikeLightning(player.getTargetBlock(null, 50).getLocation());
		M.itemUseMessage(player, "Lightning Sceptre");
		player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 77));
		
	}
	
	public static void shootWebGrenade(Player player) {
		if(player == null) return;
		if(player.getItemInHand().getType() != Material.WEB) return;
		if(!getWebGrenadeEnabled()) return;
		util.subtractItem(player, 1);
		Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation(), player.getLocation().getDirection().multiply(1.3), 1.3F, 0.0F);
		arrow.setMetadata("web_grenade", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
		arrow.setShooter(player);
		player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.5F, 0.5F);
		M.message(player, "Item", ChatColor.WHITE + "You shot a " + ChatColor.YELLOW + "Web Grenade");
	}
	
	public static void hitWebGrenade(final Location location) {
		final List<Location> editedBlocks = new ArrayList<Location>();
		for(int x = (int) location.getX(); x < location.getX() + 1; x++) {
			for(int y = (int) location.getY(); y < location.getY() + 1; y++) {
				for(int z = (int) location.getZ(); z < location.getZ() + 1; z++) {
					Block block = location.getWorld().getBlockAt(x, y, z);
					if(block.getType() == Material.AIR || block.getType() == Material.DEAD_BUSH || block.getType() == Material.LONG_GRASS || B.isBlock(block, Material.LONG_GRASS, (byte) 1) || B.isBlock(block, Material.LONG_GRASS, (byte) 2) || block.getType() == Material.FIRE) {
						block.setType(Material.WEB);
						editedBlocks.add(new Location(location.getWorld(), x, y, z));
					}
				}	
			}
		}
		Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			
			public void run() {
				for(Location loc : editedBlocks) {
					Block block = location.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
					block.setType(Material.AIR);
				}
			}
		}, 80L);
	}
	
	public static void shootFireGrenade(Player player) {
		if(player == null) return;
		if(player.getItemInHand().getType() != Material.MAGMA_CREAM) return;
		if(!getFireGrenadeEnabled()) return;
		util.subtractItem(player, 1);
		Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation(), player.getLocation().getDirection().multiply(1.3), 1.3F, 0.0F);
		arrow.setMetadata("fire_grenade", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
		arrow.setShooter(player);
		player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.5F, 0.5F);
		M.message(player, "Item", ChatColor.WHITE + "You shot a " + ChatColor.YELLOW + "Blaze Grenade");
	}
	
	public static void hitFireGrenade(final Location location) {
		final List<Location> editedBlocks = new ArrayList<Location>();
		for(int x = (int) location.getX(); x < location.getX() + 1; x++) {
			for(int y = (int) location.getY(); y < location.getY() + 1; y++) {
				for(int z = (int) location.getZ(); z < location.getZ() + 1; z++) {
					Block block = location.getWorld().getBlockAt(x, y, z);
					if(block.getType() == Material.AIR || block.getType() == Material.DEAD_BUSH || block.getType() == Material.LONG_GRASS || B.isBlock(block, Material.LONG_GRASS, (byte) 1) || B.isBlock(block, Material.LONG_GRASS, (byte) 2) || block.getType() == Material.FIRE) {
						block.setType(Material.FIRE);
						editedBlocks.add(new Location(location.getWorld(), x, y, z));
					}
				}	
			}
		}
		Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			
			public void run() {
				for(Location loc : editedBlocks) {
					Block block = location.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
					block.setType(Material.AIR);
					
				}
			}
		}, 80L);
	}
	
	public static boolean getLightningSceptreEnabled() {
		return Main.config.getBoolean("item.lightning_sceptre_enabled");
	}
	
	@SuppressWarnings("deprecation")
	public static Material getLightningSceptre() {
		return Material.getMaterial(Main.config.getInt("item.lightning_sceptre"));
	}
	
	public static int getLightningSceptreCooldown() {
		return Main.config.getInt("item.lightning_sceptre_cooldown");
	}
	
	public static boolean getIncendiaryAxeEnabled() {
		return Main.config.getBoolean("item.incendiary_axe_enabled");
	}
	
	@SuppressWarnings("deprecation")
	public static Material getIncendiaryAxe() {
		return Material.getMaterial(Main.config.getInt("item.incendiary_axe"));
	}
	
	public static int getIncendiaryAxeTime() {
		return Main.config.getInt("item.incendiary_axe_time") * 20;
	}

	public static boolean getCripplingAxeEnabled() {
		return Main.config.getBoolean("item.crippling_axe_enabled");
	}
	
	@SuppressWarnings("deprecation")
	public static Material getCripplingAxe() {
		return Material.getMaterial(Main.config.getInt("item.crippling_axe"));
	}
	
	public static int getCripplingAxeTime() {
		return Main.config.getInt("item.crippling_axe_time") * 20;
	}
	
	public static int getCripplingAxeAmplifier() {
		int amp = Main.config.getInt("item.crippling_axe_amplifier");
		if(amp < 0) return 0;
		if(amp > 0) return amp - 1;
		return 0;
	}
	
	public static boolean getWebGrenadeEnabled() {
		return Main.config.getBoolean("item.web_grenade_enabled");
	}
	
	public static boolean getFireGrenadeEnabled() {
		return Main.config.getBoolean("item.fire_grenade_enabled");
	}

}
