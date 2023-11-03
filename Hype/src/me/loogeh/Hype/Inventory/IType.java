package me.loogeh.Hype.Inventory;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Formatting.C;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public enum IType {
	KIT_SELECTOR {
		
		public void open(Player player) {
			HInventory inv = new HInventory(C.boldGreen + "Kit Selector", 6);
			inv.setItem(Material.LEATHER_HELMET, C.boldGreen + ClassType.LEATHER.getKitName(), 9);
			inv.setItem(Material.LEATHER_CHESTPLATE, C.boldGreen + ClassType.LEATHER.getKitName(), 18);
			inv.setItem(Material.LEATHER_LEGGINGS, C.boldGreen + ClassType.LEATHER.getKitName(), 27);
			inv.setItem(Material.LEATHER_BOOTS, C.boldGreen + ClassType.LEATHER.getKitName(), 36);
			
			inv.setItem(Material.GOLD_HELMET, C.boldGreen + ClassType.GOLD.getKitName(), 11);
			inv.setItem(Material.GOLD_CHESTPLATE, C.boldGreen + ClassType.GOLD.getKitName(), 20);
			inv.setItem(Material.GOLD_LEGGINGS, C.boldGreen + ClassType.GOLD.getKitName(), 29);
			inv.setItem(Material.GOLD_BOOTS, C.boldGreen + ClassType.GOLD.getKitName(), 38);
			
			inv.setItem(Material.CHAINMAIL_HELMET, C.boldGreen + ClassType.CHAIN.getKitName(), 13);
			inv.setItem(Material.CHAINMAIL_CHESTPLATE, C.boldGreen + ClassType.CHAIN.getKitName(), 22);
			inv.setItem(Material.CHAINMAIL_LEGGINGS, C.boldGreen + ClassType.CHAIN.getKitName(), 31);
			inv.setItem(Material.CHAINMAIL_BOOTS, C.boldGreen + ClassType.CHAIN.getKitName(), 40);
			
			inv.setItem(Material.IRON_HELMET, C.boldGreen + ClassType.IRON.getKitName(), 15);
			inv.setItem(Material.IRON_CHESTPLATE, C.boldGreen + ClassType.IRON.getKitName(), 24);
			inv.setItem(Material.IRON_LEGGINGS, C.boldGreen + ClassType.IRON.getKitName(), 33);
			inv.setItem(Material.IRON_BOOTS, C.boldGreen + ClassType.IRON.getKitName(), 42);
			
			inv.setItem(Material.DIAMOND_HELMET, C.boldGreen + ClassType.DIAMOND.getKitName(), 17);
			inv.setItem(Material.DIAMOND_CHESTPLATE, C.boldGreen + ClassType.DIAMOND.getKitName(), 26);
			inv.setItem(Material.DIAMOND_LEGGINGS, C.boldGreen + ClassType.DIAMOND.getKitName(), 35);
			inv.setItem(Material.DIAMOND_BOOTS, C.boldGreen + ClassType.DIAMOND.getKitName(), 44);
			player.openInventory(inv.getInventory());
		}

		public Inventory getInventory() {
			HInventory inv = new HInventory(C.boldGreen + "Kit Selector", 6);
			inv.setItem(Material.LEATHER_HELMET, C.boldGreen + ClassType.LEATHER.getKitName(), 9);
			inv.setItem(Material.LEATHER_CHESTPLATE, C.boldGreen + ClassType.LEATHER.getKitName(), 18);
			inv.setItem(Material.LEATHER_LEGGINGS, C.boldGreen + ClassType.LEATHER.getKitName(), 27);
			inv.setItem(Material.LEATHER_BOOTS, C.boldGreen + ClassType.LEATHER.getKitName(), 36);
			
			inv.setItem(Material.GOLD_HELMET, C.boldGreen + ClassType.GOLD.getKitName(), 11);
			inv.setItem(Material.GOLD_CHESTPLATE, C.boldGreen + ClassType.GOLD.getKitName(), 20);
			inv.setItem(Material.GOLD_LEGGINGS, C.boldGreen + ClassType.GOLD.getKitName(), 29);
			inv.setItem(Material.GOLD_BOOTS, C.boldGreen + ClassType.GOLD.getKitName(), 38);
			
			inv.setItem(Material.CHAINMAIL_HELMET, C.boldGreen + ClassType.CHAIN.getKitName(), 13);
			inv.setItem(Material.CHAINMAIL_CHESTPLATE, C.boldGreen + ClassType.CHAIN.getKitName(), 22);
			inv.setItem(Material.CHAINMAIL_LEGGINGS, C.boldGreen + ClassType.CHAIN.getKitName(), 31);
			inv.setItem(Material.CHAINMAIL_BOOTS, C.boldGreen + ClassType.CHAIN.getKitName(), 40);
			
			inv.setItem(Material.IRON_HELMET, C.boldGreen + ClassType.IRON.getKitName(), 15);
			inv.setItem(Material.IRON_CHESTPLATE, C.boldGreen + ClassType.IRON.getKitName(), 24);
			inv.setItem(Material.IRON_LEGGINGS, C.boldGreen + ClassType.IRON.getKitName(), 33);
			inv.setItem(Material.IRON_BOOTS, C.boldGreen + ClassType.IRON.getKitName(), 42);
			
			inv.setItem(Material.DIAMOND_HELMET, C.boldGreen + ClassType.DIAMOND.getKitName(), 17);
			inv.setItem(Material.DIAMOND_CHESTPLATE, C.boldGreen + ClassType.DIAMOND.getKitName(), 26);
			inv.setItem(Material.DIAMOND_LEGGINGS, C.boldGreen + ClassType.DIAMOND.getKitName(), 35);
			inv.setItem(Material.DIAMOND_BOOTS, C.boldGreen + ClassType.DIAMOND.getKitName(), 44);
			return inv.getInventory();
		}
		
	},
	ABILITY_SELECTOR {

		public void open(Player player) {
			return;
		}

		public Inventory getInventory() {
			return null;
		}
		
	},
	KIT_ARENA_SELECTOR {

		public void open(Player player) {
		}

		public Inventory getInventory() {
			return null;
		}
	};
	

	public abstract void open(Player player);
	public abstract Inventory getInventory();
	
}