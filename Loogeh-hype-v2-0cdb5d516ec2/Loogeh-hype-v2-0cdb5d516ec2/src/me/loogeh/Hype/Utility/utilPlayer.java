package me.loogeh.Hype.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Formatting.M;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class utilPlayer {

	public static void setSet(Player player, ClassType set) {
		player.getInventory().clear();
		if(set == ClassType.LEATHER) {
			player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			for(int i = 1; i < 6; i++) player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
			player.getInventory().setItem(6, new ItemStack(Material.IRON_AXE));
			player.getInventory().setItem(7, new ItemStack(Material.COOKED_BEEF, 8));
			player.getInventory().setItem(8, new ItemStack(Material.BOW));
			player.getInventory().setItem(9, new ItemStack(Material.ARROW, 24));
		}
		if(set == ClassType.GOLD) {
			player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			for(int i = 1; i < 7; i++) player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
			player.getInventory().setItem(7, new ItemStack(Material.GOLD_AXE));
			player.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, 8));
			player.getInventory().setItem(8, new ItemStack(Material.BOW));
			player.getInventory().setItem(9, new ItemStack(Material.ARROW, 24));
		}
		if(set == ClassType.CHAIN) {
			player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			for(int i = 1; i < 7; i++) player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
			player.getInventory().setItem(7, new ItemStack(Material.IRON_AXE));
			player.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, 8));
			player.getInventory().setItem(8, new ItemStack(Material.BOW));
			player.getInventory().setItem(9, new ItemStack(Material.ARROW, 24));
		}
		if(set == ClassType.IRON) {
			player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			for(int i = 1; i < 7; i++) player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
			player.getInventory().setItem(7, new ItemStack(Material.IRON_AXE));
			player.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, 8));
			player.getInventory().setItem(8, new ItemStack(Material.BOW));
			player.getInventory().setItem(9, new ItemStack(Material.ARROW, 24));
		}
		if(set == ClassType.DIAMOND) {
			player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			for(int i = 1; i < 7; i++) player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
			player.getInventory().setItem(6, new ItemStack(Material.IRON_AXE));
			player.getInventory().setItem(7, new ItemStack(Material.COOKED_BEEF, 8));
			player.getInventory().setItem(8, new ItemStack(Material.BOW));
			player.getInventory().setItem(9, new ItemStack(Material.ARROW, 24));
		}
	}
	
	public static Vector getBlizzardVector(Vector vector, boolean y) {
		if(y) return vector.add(new Vector(utilMath.rD(0.0, 0.4), utilMath.rD(0.0, 0.4), utilMath.rD(0.0, 0.4)));
		else return vector.add(new Vector(utilMath.rD(0.0, 0.4), 0.0, utilMath.rD(0.0, 0.4)));
	}
	
	public static void heal(Player player, boolean notify) {
		if(player == null) return;
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setRemainingAir(300);
		if(notify) M.message(player, "Server", ChatColor.WHITE + "You have been healed");
	}
	
	public static boolean holding(Player player, ItemStack item) {
		if(player == null || item == null) return false;
		return player.getItemInHand().isSimilar(item);
	}
	
	public static boolean holding(Player player, ItemStack[] items) {
		if(player == null) return false;
		for(ItemStack item : items) {
			if(player.getItemInHand().isSimilar(item)) return true;
		}
		return false;
	}
	
	public static boolean holding(Player player, Material material) {
		if(player == null || material == null) return false;
		return player.getItemInHand().getType().equals(material);
	}
	
	public static boolean holding(Player player, Material[] materials) {
		if(player == null) return false;
		for(Material material : materials) {
			if(player.getItemInHand().getType().equals(material)) return true;
		}
		return false;
	}
	
	public static List<Player> getNearbyPlayers(Player player, double distance) {
		List<Entity> nearby = player.getNearbyEntities(distance, distance, distance);
		List<Player> players = new ArrayList<Player>();
		for(Entity near : nearby) {
			if(near instanceof Player) {
				Player p_near = (Player) near;
				players.add(p_near);
			}
		}
		return players;
	}
	
	public static Block getTargetBlock(Player player, int maxDistance) {
		Iterator<Block> it = new BlockIterator(player, maxDistance);
		while(it.hasNext()) {
			if(it.next().getType().equals(Material.AIR)) return it.next();
		}
		return null;
	}
}
