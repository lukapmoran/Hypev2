package me.loogeh.Hype.Games;

import java.util.HashSet;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.utilPlayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PublicArena implements Listener {
	Main plugin;
	
	public PublicArena(Main instance) {
		plugin = instance;
	}
	
	public HashSet<String> blue = new HashSet<String>();
	public HashSet<String> red = new HashSet<String>();
	@EventHandler
	public void onPAInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if(block.getType().equals(Material.STONE_BUTTON)) {
				Block behind = block.getRelative(event.getBlockFace(), -1);
				Block behind2 = block.getRelative(event.getBlockFace(), -2);
				if(behind2.getType().equals(Material.REDSTONE_BLOCK)) {
					if(behind.getType().equals(Material.DIAMOND_BLOCK)) {
						utilPlayer.setSet(player, ClassType.DIAMOND);
					}
					if(behind.getType().equals(Material.IRON_BLOCK)) {
						utilPlayer.setSet(player, ClassType.IRON);
					}
					if(behind.getType().equals(Material.GOLD_BLOCK)) {
						utilPlayer.setSet(player, ClassType.GOLD);
					}
					if(behind.getType().equals(Material.LOG)) {
						utilPlayer.setSet(player, ClassType.LEATHER);
					}
					if(behind.getType().equals(Material.WOOL)) {
						utilPlayer.setSet(player, ClassType.CHAIN);
					}
				}
			}
		}
		if(event.getAction().equals(Action.PHYSICAL)) {
			Block block = event.getClickedBlock();
			if(block.getType().equals(Material.STONE_PLATE)) {
				Block behind = block.getRelative(BlockFace.UP, -1);
				System.out.println(behind.getType());
				if(behind.getType().equals(Material.LAPIS_BLOCK)) {
					blue.add(player.getName());
					player.teleport(new Location(player.getWorld(), -207.8, 45.2, 462.5, 90.0F,  0.0F));
				} else if(behind.getType().equals(Material.REDSTONE_BLOCK)) {
					red.add(player.getName());
					player.teleport(new Location(player.getWorld(), 128.2, 44.2, 462.5, -90.0F, 0.0F));
				}
			}
		}
	}
	
	@EventHandler
	public void onPAEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(blue.contains(player.getName())) {
				if(player.getHealth() - event.getDamage() <= 0.0D) {
					event.setCancelled(true);
					player.teleport(new Location(player.getWorld(), 212.5, 46.2, 462.5, 90.0F, 0.0F));
					player.getInventory().clear();
					blue.remove(player.getName());
				}
			}
			if(red.contains(player.getName())) {
				if(player.getHealth() - event.getDamage() <= 0.0D) {
					event.setCancelled(true);
					player.teleport(new Location(player.getWorld(), 123.5, 46.2, 462.5, -90.0F, 0.0F));
					player.getInventory().clear();
					red.remove(player.getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onPAPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(red.contains(player.getName())) {
			player.teleport(new Location(player.getWorld(), 123.5, 46.2, 462.5, -90.0F, 0.0F));
			player.getInventory().clear();
			red.remove(player.getName());
		}
		if(blue.contains(player.getName())) {
			player.teleport(new Location(player.getWorld(), 212.5, 46.2, 462.5, 90.0F, 0.0F));
			player.getInventory().clear();
			blue.remove(player.getName());
		}
	}
	
}
