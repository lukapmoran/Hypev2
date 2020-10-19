package me.loogeh.Hype.Armour;

import me.loogeh.Hype.Formatting.C;

import org.bukkit.Material;

public class KitSelector extends ArmourInventory {

	public KitSelector() {
		super(AIType.KIT_SELECTOR);
		update();
	}
	
	public void update() {
		setItem(Material.LEATHER_HELMET, C.boldGreen + ClassType.LEATHER.getKitName(), 9);
		setItem(Material.LEATHER_CHESTPLATE, C.boldGreen + ClassType.LEATHER.getKitName(), 18);
		setItem(Material.LEATHER_LEGGINGS, C.boldGreen + ClassType.LEATHER.getKitName(), 27);
		setItem(Material.LEATHER_BOOTS, C.boldGreen + ClassType.LEATHER.getKitName(), 36);
		
		setItem(Material.GOLD_HELMET, C.boldGreen + ClassType.GOLD.getKitName(), 11);
		setItem(Material.GOLD_CHESTPLATE, C.boldGreen + ClassType.GOLD.getKitName(), 20);
		setItem(Material.GOLD_LEGGINGS, C.boldGreen + ClassType.GOLD.getKitName(), 29);
		setItem(Material.GOLD_BOOTS, C.boldGreen + ClassType.GOLD.getKitName(), 38);
		
		setItem(Material.CHAINMAIL_HELMET, C.boldGreen + ClassType.CHAIN.getKitName(), 13);
		setItem(Material.CHAINMAIL_CHESTPLATE, C.boldGreen + ClassType.CHAIN.getKitName(), 22);
		setItem(Material.CHAINMAIL_LEGGINGS, C.boldGreen + ClassType.CHAIN.getKitName(), 31);
		setItem(Material.CHAINMAIL_BOOTS, C.boldGreen + ClassType.CHAIN.getKitName(), 40);
		
		setItem(Material.IRON_HELMET, C.boldGreen + ClassType.IRON.getKitName(), 15);
		setItem(Material.IRON_CHESTPLATE, C.boldGreen + ClassType.IRON.getKitName(), 24);
		setItem(Material.IRON_LEGGINGS, C.boldGreen + ClassType.IRON.getKitName(), 33);
		setItem(Material.IRON_BOOTS, C.boldGreen + ClassType.IRON.getKitName(), 42);
		
		setItem(Material.DIAMOND_HELMET, C.boldGreen + ClassType.DIAMOND.getKitName(), 17);
		setItem(Material.DIAMOND_CHESTPLATE, C.boldGreen + ClassType.DIAMOND.getKitName(), 26);
		setItem(Material.DIAMOND_LEGGINGS, C.boldGreen + ClassType.DIAMOND.getKitName(), 35);
		setItem(Material.DIAMOND_BOOTS, C.boldGreen + ClassType.DIAMOND.getKitName(), 44);
	}
}