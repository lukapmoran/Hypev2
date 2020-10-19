package me.loogeh.Hype.Shops;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.I;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Shops.ShopItem.ItemType;
import me.loogeh.Hype.Utility.utilItem;
import me.loogeh.Hype.Utility.utilWorld;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Shop {

	public static HashMap<String, Shop> shops = new HashMap<String, Shop>();
	public static HashMap<String, ShopLog> shopLog = new HashMap<String, ShopLog>();

	private String name;
	private Entity entity;
	private Location spawnLocation;
	private ShopType type;
	private HashMap<ItemStack, ShopItem> storedItems = new HashMap<ItemStack, ShopItem>();

	public Shop(String name, Location spawnLocation, ShopType type) {
		this.name = name;
		this.spawnLocation = spawnLocation;
		this.type = type;
		fill();
		this.entity = spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.VILLAGER);
		getVillager().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10));
		getVillager().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -10));
		getVillager().setCustomName(C.boldGreen + WordUtils.capitalize(name.toLowerCase()));
		getVillager().setCustomNameVisible(true);
		getVillager().setAdult();
		getVillager().setCanPickupItems(false);
		getVillager().setRemoveWhenFarAway(false);
		getVillager().setNoDamageTicks(Integer.MAX_VALUE);
		getVillager().setProfession(type.getProfession());
		getVillager().setBreed(false);
		shops.put(name.toLowerCase(), this);

	}

	public String getName() {
		return this.name;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public Villager getVillager() {
		return (Villager) this.entity;
	}

	public Location getSpawnLocation() {
		return this.spawnLocation;
	}

	public ShopType getType() {
		return this.type;
	}

	public HashMap<ItemStack, ShopItem> getStoredItems() {
		return this.storedItems;
	}

	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, C.boldDarkRed + "Shop " + WordUtils.capitalize(getName().toLowerCase()));
		for(Entry<ItemStack, ShopItem> entry : storedItems.entrySet()) {
			if(entry.getValue().getSlotID() < 54) {
				ItemStack item = entry.getKey();
				ItemMeta meta = item.getItemMeta();
				if(entry.getValue().getLore() != null) {
					if(entry.getValue().getBuyCost() < 1 && entry.getValue().getSellPrice() > 0) {
						meta = I.addShopLore(item, C.boldDarkGreen + "Sell " + ChatColor.GREEN + "$" + entry.getValue().getSellPrice(), null, entry.getValue().getLore());
					} else if(entry.getValue().getBuyCost() > 0 && entry.getValue().getSellPrice() < 1) {
						meta = I.addShopLore(item, null, C.boldDarkGreen + "Buy " + ChatColor.GREEN + "$" + entry.getValue().getBuyCost(), entry.getValue().getLore());
					} else if(entry.getValue().getBuyCost() > 0 && entry.getValue().getSellPrice() > 0) {
						meta = I.addShopLore(item, C.boldDarkGreen + "Sell " + ChatColor.GREEN + "$" + entry.getValue().getSellPrice(), C.boldDarkGreen + "Buy " + ChatColor.GREEN + "$" + entry.getValue().getBuyCost(), entry.getValue().getLore());
					} else {
						if(!entry.getValue().getLore().get(0).equalsIgnoreCase("")) {
							meta = I.addShopLore(item, null, null, entry.getValue().getLore());
						}
					}
				} else {
					if(entry.getValue().getBuyCost() < 1 && entry.getValue().getSellPrice() > 0) {
						meta = I.addShopLore(item, C.boldDarkGreen + "Sell " + ChatColor.GREEN + "$" + entry.getValue().getSellPrice(), null, null);
					} else if(entry.getValue().getBuyCost() > 0 && entry.getValue().getSellPrice() < 1) {
						meta = I.addShopLore(item, null, C.boldDarkGreen + "Buy " + ChatColor.GREEN + "$" + entry.getValue().getBuyCost(), null);
					} else if(entry.getValue().getBuyCost() > 0 && entry.getValue().getSellPrice() > 0) {
						meta = I.addShopLore(item, C.boldDarkGreen + "Buy " + ChatColor.GREEN + "$" + entry.getValue().getBuyCost(), C.boldDarkGreen + "Sell " + ChatColor.GREEN + "$" + entry.getValue().getSellPrice(), null);
					} else {
						if(!entry.getValue().getLore().get(0).equalsIgnoreCase("")) {
							meta = I.addShopLore(item, null, null, null);
						}
					}
				}
				meta.setDisplayName(C.boldGreen + entry.getValue().getDisplayName());
				item.setItemMeta(meta);
				inv.setItem(entry.getValue().getSlotID(), entry.getKey());
			}
		}
		player.openInventory(inv);
	}

	public void fill() {
		if(getType() == ShopType.WEAPONRY) {
			storedItems.put(new ItemStack(Material.EMERALD), new ShopItem("Information", Material.EMERALD, 0, 0, 4, ItemType.OTHER, I.getLoreFromString(ChatColor.GRAY + "Different armours can have different " + ChatColor.YELLOW + "Armour-" + ChatColor.YELLOW + "Abilities " + ChatColor.GRAY + "to provide additional gameplay")));
			storedItems.put(new ItemStack(Material.LEATHER_HELMET), new ShopItem("Archer Helmet", Material.LEATHER_HELMET, 3000, 0, 9, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.LEATHER_CHESTPLATE), new ShopItem("Archer Chestplate", Material.LEATHER_CHESTPLATE, 7000, 0, 10, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.LEATHER_LEGGINGS), new ShopItem("Archer Leggings", Material.LEATHER_LEGGINGS, 5000, 0, 11, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.LEATHER_BOOTS), new ShopItem("Archer Boots", Material.LEATHER_BOOTS, 3000, 0, 12, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.CHAINMAIL_HELMET), new ShopItem("Specialist Helmet", Material.CHAINMAIL_HELMET, 3000, 0, 18, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ShopItem("Specialist Chestplate", Material.CHAINMAIL_CHESTPLATE, 7000, 0, 19, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.CHAINMAIL_LEGGINGS), new ShopItem("Specialist Leggings", Material.CHAINMAIL_LEGGINGS, 5000, 0, 20, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.CHAINMAIL_BOOTS), new ShopItem("Specialist Boots", Material.CHAINMAIL_BOOTS, 3000, 0, 21, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.GOLD_HELMET), new ShopItem("Agility Helmet", Material.GOLD_HELMET, 3000, 0, 27, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.GOLD_CHESTPLATE), new ShopItem("Agility Chestplate", Material.GOLD_CHESTPLATE, 7000, 0, 28, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.GOLD_LEGGINGS), new ShopItem("Agility Leggings", Material.GOLD_LEGGINGS, 5000, 0, 29, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.GOLD_BOOTS), new ShopItem("Agility Boots", Material.GOLD_BOOTS, 3000, 0, 30, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.IRON_HELMET), new ShopItem("Samurai Helmet", Material.IRON_HELMET, 3000, 0, 36, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.IRON_CHESTPLATE), new ShopItem("Samurai Chestplate", Material.IRON_CHESTPLATE, 7000, 0, 37, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.IRON_LEGGINGS), new ShopItem("Samurai Leggings", Material.IRON_LEGGINGS, 5000, 0, 38, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.IRON_BOOTS), new ShopItem("Samurai Boots", Material.IRON_BOOTS, 3000, 0, 39, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.DIAMOND_HELMET), new ShopItem("Juggernaut Helmet", Material.DIAMOND_HELMET, 3000, 0, 45, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.DIAMOND_CHESTPLATE), new ShopItem("Juggernaut Chestplate", Material.DIAMOND_CHESTPLATE, 7000, 0, 46, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.DIAMOND_LEGGINGS), new ShopItem("Juggernaut Leggings", Material.DIAMOND_LEGGINGS, 5000, 0, 47, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.DIAMOND_BOOTS), new ShopItem("Juggernaut Boots", Material.DIAMOND_BOOTS, 3000, 0, 48, ItemType.ARMOUR));
			storedItems.put(new ItemStack(Material.IRON_SWORD), new ShopItem("Iron Sword", Material.IRON_SWORD, 3500, 0, 7, ItemType.WEAPON, I.SWORD));
			storedItems.put(new ItemStack(Material.DIAMOND_SWORD), new ShopItem("Diamond Sword", Material.DIAMOND_SWORD, 14000, 0, 8, ItemType.WEAPON, I.SWORD));
			storedItems.put(new ItemStack(Material.IRON_AXE), new ShopItem("Iron Axe", Material.IRON_AXE, 2500, 0, 16, ItemType.WEAPON, I.AXE));
			storedItems.put(new ItemStack(Material.GOLD_AXE), new ShopItem("Incendiary Axe", Material.GOLD_AXE, 10000, 0, 17, ItemType.WEAPON, I.G_AXE));
			storedItems.put(new ItemStack(Material.DIAMOND_HOE), new ShopItem("Lightning Sceptre", Material.DIAMOND_HOE, 12000, 0, 25, ItemType.WEAPON, I.D_HOE));
			storedItems.put(new ItemStack(Material.WEB), new ShopItem("Web Grenade", Material.WEB, 1000, 800, 26, ItemType.ITEM, I.ITEM_WEB));
			storedItems.put(new ItemStack(Material.MAGMA_CREAM), new ShopItem("Blaze Grenade", Material.MAGMA_CREAM, 1000, 800, 34, ItemType.ITEM, I.ITEM_MAGMA_CREAM));
			storedItems.put(new ItemStack(Material.BOW), new ShopItem("Bow", Material.BOW, 1500, 0, 35, ItemType.WEAPON));
			storedItems.put(new ItemStack(Material.ARROW), new ShopItem("Arrow", Material.ARROW, 100, 70, 43, ItemType.ITEM));
			storedItems.put(new ItemStack(Material.MUSHROOM_SOUP), new ShopItem("Mushroom Soup", Material.MUSHROOM_SOUP, 700, 500, 44, ItemType.ITEM));
		} else if(getType() == ShopType.BAKER) {
			storedItems.put(new ItemStack(Material.CARROT_ITEM), new ShopItem("Carrot", Material.CARROT_ITEM, 120, 70, 9, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.COOKIE), new ShopItem("Cookie", Material.COOKIE, 100, 65, 10, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.CAKE), new ShopItem("Cake", Material.CAKE, 1000, 700, 11, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.BREAD), new ShopItem("Bread", Material.BREAD, 300, 200, 12, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.APPLE), new ShopItem("Apple", Material.APPLE, 200, 130, 18, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.PORK), new ShopItem("Cooked Porkchop", Material.PORK, 350, 220, 19, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.COOKED_BEEF), new ShopItem("Steak", Material.COOKED_BEEF, 350, 220, 20, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.GRILLED_PORK), new ShopItem("Cooked Fish", Material.GRILLED_PORK, 150, 100, 21, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.BAKED_POTATO), new ShopItem("Baked Potato", Material.BAKED_POTATO, 200, 130, 27, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.COOKED_CHICKEN), new ShopItem("Cooked Chicken", Material.COOKED_CHICKEN, 200, 0, 28, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.GOLDEN_CARROT), new ShopItem("Golden Carrot", Material.GOLDEN_CARROT, 220, 0, 29, ItemType.FOOD));
			storedItems.put(new ItemStack(Material.PUMPKIN_PIE), new ShopItem("Pumpkin Pie", Material.PUMPKIN_PIE, 350, 0, 30, ItemType.FOOD));
		} else if(getType() == ShopType.BUILDER) {
			storedItems.put(new ItemStack(Material.COBBLESTONE), new ShopItem("Cobblestone", Material.COBBLESTONE, 1, 0, 9, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.DIRT), new ShopItem("Dirt", Material.DIRT, 1, 0, 10, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.SAND), new ShopItem("Sand", Material.SAND, 6, 0, 11, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.SANDSTONE), new ShopItem("Sandstone", Material.SANDSTONE, 12, 0, 12, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.LOG, 1, (short) 0), new ShopItem("Oak Log", Material.LOG, 120, 50, 18, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.LOG, 1, (short) 1), new ShopItem("Spruce Log", Material.LOG, 120, 50, 19, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.LOG, 1, (short) 2), new ShopItem("Birch Log", Material.LOG, 120, 50, 20, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.LOG, 1, (short) 3), new ShopItem("Jungle Log", Material.LOG, 120, 50, 21, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0), new ShopItem("Stone Brick", Material.SMOOTH_BRICK, 250, 100, 27, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2), new ShopItem("Cracked Stone Brick", Material.SMOOTH_BRICK, 250, 100, 28, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 3), new ShopItem("Chiseled Stone Brick", Material.SMOOTH_BRICK, 250, 100, 29, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.BRICK), new ShopItem("Brick", Material.BRICK, 200, 100, 30, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.GLASS), new ShopItem("Glass", Material.GLASS, 50, 10, 36, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.WOOL), new ShopItem("Wool", Material.WOOL, 50, 15, 37, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.PISTON_BASE), new ShopItem("Piston", Material.PISTON_BASE, 1500, 700, 38, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.PISTON_STICKY_BASE), new ShopItem("Sticky Piston", Material.PISTON_STICKY_BASE, 4000, 1500, 39, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.BOOKSHELF), new ShopItem("Bookshelf", Material.BOOKSHELF, 300, 150, 45, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.IRON_BARDING), new ShopItem("Iron Bars", Material.IRON_BARDING, 100, 15, 46, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.WOOD_STAIRS), new ShopItem("Wooden Stairs", Material.WOOD_STAIRS, 120, 700, 47, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.NOTE_BLOCK), new ShopItem("Note Block", Material.NOTE_BLOCK, 3000, 1000, 48, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK), new ShopItem("Black Dye", Material.INK_SACK, 50, 20, 24, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 1), new ShopItem("Red Dye", Material.INK_SACK, 50, 20, 7, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 2), new ShopItem("Green Dye", Material.INK_SACK, 50, 20, 8, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 3), new ShopItem("Brown Dye", Material.INK_SACK, 50, 20, 16, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 4), new ShopItem("Blue Dye", Material.INK_SACK, 50, 20, 17, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 5), new ShopItem("Purple Dye", Material.INK_SACK, 50, 20, 25, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 6), new ShopItem("Cyan Dye", Material.INK_SACK, 50, 20, 26, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 7), new ShopItem("Light Gray Dye", Material.INK_SACK, 50, 20, 34, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 8), new ShopItem("Gray Dye", Material.INK_SACK, 50, 20, 35, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 9), new ShopItem("Pink Dye", Material.INK_SACK, 50, 20, 43, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 10), new ShopItem("Lime Dye", Material.INK_SACK, 50, 20, 44, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 11), new ShopItem("Yellow Dye", Material.INK_SACK, 50, 20, 52, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 12), new ShopItem("Light Blue Dye", Material.INK_SACK, 50, 20, 53, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 13), new ShopItem("Magenta Dye", Material.INK_SACK, 50, 20, 51, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 14), new ShopItem("Orange Dye", Material.INK_SACK, 50, 20, 42, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.INK_SACK, 1, (short) 15), new ShopItem("White Dye", Material.INK_SACK, 50, 20, 33, ItemType.BLOCK));
		} else if(getType() == ShopType.ENCHANTER) {
			storedItems.put(new ItemStack(Material.EMERALD), new ShopItem("Information", Material.EMERALD, 0, 0, 4, ItemType.OTHER, I.getLoreFromString(ChatColor.GRAY + "Enchantments are limited to reduce-" + ChatColor.GRAY + "interference with " + ChatColor.YELLOW + "Armour Abilities")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.DIAMOND_SWORD), Enchantment.DAMAGE_ALL), new ShopItem("Sharpness", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.DAMAGE_ALL), 0, 9, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "2")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.DIAMOND_SWORD), Enchantment.KNOCKBACK), new ShopItem("Knockback", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.KNOCKBACK), 0, 10, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "2")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.DIAMOND_SWORD), Enchantment.DAMAGE_UNDEAD), new ShopItem("Smite", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.DAMAGE_UNDEAD), 0, 11, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "5")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.DIAMOND_SWORD), Enchantment.DAMAGE_ARTHROPODS), new ShopItem("Arthropods", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.DAMAGE_ARTHROPODS), 0, 12, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "5")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.DIAMOND_PICKAXE), Enchantment.DURABILITY), new ShopItem("Unbreaking", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.DURABILITY), 0, 18, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "3")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.DIAMOND_PICKAXE), Enchantment.LOOT_BONUS_BLOCKS), new ShopItem("Fortune", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.LOOT_BONUS_BLOCKS), 0, 19, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "2")));
			storedItems.put(getEnchantedItem(new ItemStack(Material.BOW), Enchantment.ARROW_DAMAGE), new ShopItem("Power", Material.DIAMOND_SWORD, getEnchantmentPrice(Enchantment.ARROW_DAMAGE), 0, 27, ItemType.ENCHANTMENT, I.getLoreFromString(ChatColor.WHITE + "Max Level " + ChatColor.YELLOW + "2")));
		} else if(getType() == ShopType.MINER) {
			storedItems.put(new ItemStack(Material.IRON_INGOT), new ShopItem("Iron Ingot", Material.IRON_INGOT, 1000, 700, 9, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.GOLD_INGOT), new ShopItem("Gold Ingot", Material.GOLD_INGOT, 1000, 700, 10, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.DIAMOND_PICKAXE), new ShopItem("Diamond Pickaxe", Material.DIAMOND_PICKAXE, 24000, 0, 11, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.IRON_PICKAXE), new ShopItem("Iron Pickaxe", Material.IRON_PICKAXE, 2500, 0, 12, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.GOLD_PICKAXE), new ShopItem("Gold Pickaxe", Material.GOLD_PICKAXE, 1200, 0, 18, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.STONE_PICKAXE), new ShopItem("Stone Pickaxe", Material.STONE_PICKAXE, 500, 0, 19, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.DIAMOND_SPADE), new ShopItem("Diamond Shovel", Material.DIAMOND_SPADE, 15000, 0, 20, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.IRON_SPADE), new ShopItem("Iron Shovel", Material.IRON_SPADE, 1700, 0, 21, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.STONE_SPADE), new ShopItem("Stone Shovel", Material.STONE_SPADE, 350, 0, 27, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.REDSTONE), new ShopItem("Redstone", Material.REDSTONE, 250, 100, 28, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.REDSTONE_BLOCK), new ShopItem("Redstone Block", Material.REDSTONE_BLOCK, 2250, 900, 29, ItemType.MINERAL));
			storedItems.put(new ItemStack(Material.COAL), new ShopItem("Coal", Material.COAL, 200, 40, 30, ItemType.MINERAL));
		} else if(getType() == ShopType.MISCELLANEOUS) {
			storedItems.put(new ItemStack(Material.TNT), new ShopItem("Iron Ingot", Material.TNT, 30000, 17000, 9, ItemType.BLOCK));
			storedItems.put(new ItemStack(Material.FLINT_AND_STEEL), new ShopItem("Gold Ingot", Material.FLINT_AND_STEEL, 1000, 700, 10, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.MAP), new ShopItem("Map", Material.MAP, 2400, 0, 11, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.LAVA_BUCKET), new ShopItem("Lava Bucket", Material.LAVA_BUCKET, 22000, 0, 12, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.WATER_BUCKET), new ShopItem("Water Bucket", Material.WATER_BUCKET, 3200, 0, 18, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.IRON_DOOR), new ShopItem("Iron Door", Material.IRON_DOOR, 6000, 3500, 19, ItemType.ITEM));
			storedItems.put(new ItemStack(Material.SADDLE), new ShopItem("Saddle", Material.SADDLE, 1200, 600, 20, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.TORCH), new ShopItem("Torch", Material.TORCH, 10, 5, 21, ItemType.ITEM));
			storedItems.put(new ItemStack(Material.DAYLIGHT_DETECTOR), new ShopItem("Daylight Detector", Material.DAYLIGHT_DETECTOR, 1200, 0, 27, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.REDSTONE_COMPARATOR), new ShopItem("Redstone Comparator", Material.REDSTONE_COMPARATOR, 650, 100, 28, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.DIODE), new ShopItem("Redstone Repeater", Material.DIODE, 600, 900, 29, ItemType.OTHER));
			storedItems.put(new ItemStack(Material.REDSTONE_TORCH_OFF), new ShopItem("Redstone Torch", Material.REDSTONE_TORCH_OFF, 200, 40, 30, ItemType.OTHER));
		}
	}
	
	public static ItemStack getEnchantedItem(ItemStack item, Enchantment enchantment) {
		if(item == null || enchantment == null) return null;
		if(getMaxEnchantmentLevel(enchantment) >= 10) return null;
		item.addEnchantment(enchantment, 1);
		return item;
	}

	public void buy(Player player, ItemStack item, int amount) {
		if(player == null || item == null) return;
		if(item.getType().equals(Material.AIR)) return;
		Member member = Member.get(player);
		boolean available = false;
		int balance = member.getEconomy().getBalance();
		if(amount > item.getType().getMaxStackSize()) amount = 1;
		for(int i = 0; i < player.getInventory().getSize(); i++) {
			if(player.getInventory().getItem(i) == null) available = true;
		}
		if(!available) {
			M.message(player, "Shops", ChatColor.WHITE + "Your inventory is " + ChatColor.YELLOW + "Full");
			return;
		}
		ShopItem s_item = getItem(item.getType());
		if(s_item == null) {
			M.message(player, "Shops", ChatColor.WHITE + "Failed to find the clicked item");
			return;
		}
		int cost = s_item.getBuyCost() * amount;
		if(cost == 0) return;
		if(balance < cost) {
			M.message(player, "Shops", ChatColor.WHITE + "You have insufficient money");
			return;
		}
		short data = item.getDurability();
		member.getEconomy().alterBalance(-cost);
		player.getInventory().addItem(new ItemStack(item.getType(), amount, data));
		if(shopLog.containsKey(player.getName())) {
			shopLog.get(player.getName()).cost += cost;
			shopLog.get(player.getName()).addSpent(item, amount);
		}
	}

	public void sell(Player player, ItemStack item, int amount, int slot) {
		if(player == null || item == null) return;
		ShopType sold = getWhereItemSold(item);
		if(sold == null) {
			M.message(player, "Shops", ChatColor.YELLOW + utilItem.toCommon(item.getType().toString().toLowerCase()) + ChatColor.WHITE + " cannot be sold");
			return;
		}
		if(sold != getType()) {
			M.message(player, "Shops", ChatColor.YELLOW + utilItem.toCommon(item.getType().toString().toLowerCase()) + ChatColor.WHITE + " is sold at " + ChatColor.YELLOW + sold.getName());
			return;
		}
		ShopItem s_item = getItem(item);
		if(s_item == null) {
			M.message(player, "Shops", ChatColor.YELLOW + utilItem.toCommon(item.getType().toString().toLowerCase()) + ChatColor.WHITE + " cannot be sold");
			return;
		}
		Member member = Member.get(player);
		int profit = s_item.getSellPrice() * amount;
		ItemStack item_click = player.getInventory().getItem(slot);
		if(item_click.getAmount() == amount) player.getInventory().setItem(slot, null);
		else if(item_click.getAmount() > amount) {
			player.getInventory().setItem(slot, new ItemStack(item_click.getType(), item_click.getAmount() - amount, item_click.getDurability()));
		}
		member.getEconomy().alterBalance(profit);
		if(shopLog.containsKey(player.getName())) {
			shopLog.get(player.getName()).cost -= profit;
			shopLog.get(player.getName()).addSold(item, amount);
		}
	}

	public static ShopType getWhereItemSold(ItemStack item) {
		for(Entry<String, Shop> entry_2 : shops.entrySet()) {
			Shop shop = entry_2.getValue();
			if(shop != null) {
				for(Entry<ItemStack, ShopItem> entry : shop.storedItems.entrySet()) {
					if(entry.getValue().getMaterial() == item.getType()) {
						if(entry.getValue().getSellPrice() > 0) {
							return shop.getType();
						}
					}
				}
			}
		}
		return null;
	}

	public ShopItem getItem(ItemStack item) {
		for(Entry<ItemStack, ShopItem> entry : storedItems.entrySet()) {
			if(entry.getKey().getType() == item.getType() && entry.getKey().getDurability() == item.getDurability()) return entry.getValue();
		}
		return null;
	}

	public ShopItem getItem(Material material) {
		for(Entry<ItemStack, ShopItem> entry : storedItems.entrySet()) {
			if(entry.getValue().getMaterial() == material) return entry.getValue();
		}
		return null;
	}

	public static void load() {
		Main.logger.log(Level.INFO, "Sector > Loading sector 'shops'");
		long start = System.currentTimeMillis();
		int count = 0;
		ResultSet rs_shop = Main.mysql.doQuery("SELECT * FROM shops");
		try {
			while(rs_shop.next()) {
				String name = rs_shop.getString(1);
				Location location = utilWorld.strToLoc(rs_shop.getString(3));
				if(location == null) {
					Main.logger.log(Level.INFO, "Sector > Error while loading sector 'shops' (#1)");
					continue;
				}
				ShopType type = ShopType.getType(rs_shop.getString(2).split("_")[0]);
				if(type == null) {
					Main.logger.log(Level.INFO, "Sector > Error while loading sector 'shops' (#2)");
					continue;
				}
				new Shop(WordUtils.capitalize(name.toLowerCase()), location, type);
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.INFO, "Sector > SQL Error while loading sector 'shops'");
		}
		Main.logger.log(Level.INFO, "Sector > Loaded sector 'shops' in " + (System.currentTimeMillis() - start) + "ms (" + count + ")");
	}
	
	public static void add(Player player, String name, ShopType type) {
		if(player == null) return;
		ResultSet rs = Main.mysql.doQuery("SELECT name FROM shops WHERE name='" + name.toLowerCase() + "'");
		try {
			if(rs.next()) {
				M.message(player, "Shops", ChatColor.YELLOW + name + ChatColor.WHITE + " already exists");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.INFO, "Sector > SQL Error while retrieving data for 'shops'");
		}
		Main.mysql.doUpdate("INSERT INTO `shops`(`name`, `type`, `location`) VALUES ('" + WordUtils.capitalize(name.toLowerCase()) + "','" + type.getName().toLowerCase() + "','" + utilWorld.locToStr(player.getLocation()) + "')");
		shops.put(name.toLowerCase(), new Shop(WordUtils.capitalize(name.toLowerCase()), player.getLocation(), type));
		M.message(player, "Shops", ChatColor.WHITE + "You added the shop " + ChatColor.YELLOW + WordUtils.capitalize(name.toLowerCase()));
	}
	
	public static void remove(Player player, String name) {
		if(player == null) return;
		ResultSet rs = Main.mysql.doQuery("SELECT name FROM shops WHERE name='" + name.toLowerCase() + "'");
		try {
			if(!rs.next()) {
				M.message(player, "Shops", ChatColor.YELLOW + name + ChatColor.WHITE + " doesn't exist exists");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.INFO, "Sector > SQL Error while retrieving data for 'shops'");
		}
		Main.mysql.doUpdate("DELETE FROM `shops` WHERE name='" + name.toLowerCase() + "'");
		if(Shop.shops.containsKey(name.toLowerCase())) {
			Shop.shops.get(name.toLowerCase()).getEntity().remove();
			Shop.shops.remove(name.toLowerCase());
		}
		M.message(player, "Shops", ChatColor.WHITE + "You removed " + ChatColor.YELLOW + WordUtils.capitalize(name.toLowerCase()));
	}

	public static void killShops() {
		for(Shop shop : shops.values()) {
			shop.getEntity().remove();
		}
	}

	public int getCost(ItemStack item) {
		if(item == null) return -1;
		for(Entry<ItemStack, ShopItem> items : storedItems.entrySet()) {
			if(items.getKey().getType() == item.getType()) return items.getValue().getBuyCost();
		}
		return -1;
	}

	public int getCost(Material item) {
		return 0;
	}

	public int getSellPrice(ItemStack item) {
		if(item == null) return -1;
		for(Entry<ItemStack, ShopItem> items : storedItems.entrySet()) {
			if(items.getKey().getType() == item.getType()) return items.getValue().getSellPrice();
		}
		return -1;
	}
	
	public static int getSellPriceShop(ItemStack item) {
		for(Entry<String, Shop> entry : shops.entrySet()) {
			for(Entry<ItemStack, ShopItem> shop_entry : entry.getValue().storedItems.entrySet()) {
				if(shop_entry.getKey().getType() == item.getType() && shop_entry.getKey().getDurability() == item.getDurability()) return shop_entry.getValue().getSellPrice();
			}
		}
		return -1;
	}

	public static Shop getShop(String name) {
		return shops.get(name);
	}
	
	public static Shop getShop(ShopType type) {
		for(Entry<String, Shop> entry : shops.entrySet()) {
			if(entry.getValue().getType().equals(type)) return entry.getValue();
		}
		return null;
	}
	
	public static Shop getShop(UUID uuid) {
		for(Entry<String, Shop> entry : shops.entrySet()) {
			if(entry.getValue().getEntity().getUniqueId().compareTo(uuid) == 0) return entry.getValue();
		}
		return null;
	}

	public static boolean isShop(UUID uuid) {
		for(Entry<String, Shop> entry : shops.entrySet()) {
			if(entry.getValue().getEntity().getUniqueId().compareTo(uuid) == 0) return true;
		}
		return false;
	}

	public void sendLog(Player player) {
		if(player == null) return;
		if(!shopLog.containsKey(player.getName())) return;
		Member member = Member.get(player);
		int money = member.getEconomy().getBalance();
		int cost = shopLog.get(player.getName()).cost;
		if(cost == 0) return;
		for(ItemStack keys : shopLog.get(player.getName()).items.keySet()) {
			player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + "- " + ChatColor.DARK_GREEN + "Buy [" + ChatColor.GRAY + utilItem.toCommon(keys.getType().toString().toLowerCase()) + " x" + shopLog.get(player.getName()).items.get(keys) + ChatColor.DARK_GREEN + "] - $" + ChatColor.GRAY + getCost(keys) * shopLog.get(player.getName()).items.get(keys));
		}
		for(ItemStack keys : shopLog.get(player.getName()).sold_items.keySet()) {
			player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + "- " + ChatColor.DARK_GREEN + "Sold [" + ChatColor.GRAY + utilItem.toCommon(keys.getType().toString().toLowerCase()) + " x" + shopLog.get(player.getName()).sold_items.get(keys) + ChatColor.DARK_GREEN + "] - $" + ChatColor.GRAY + getSellPrice(keys) * shopLog.get(player.getName()).sold_items.get(keys));
		}
		if(cost < 0) player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + " - You gained " + ChatColor.DARK_GREEN + "$" + Math.abs(cost) + ChatColor.GRAY + " that session");
		else if(cost > 0) player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + " - You spent " + ChatColor.DARK_GREEN + "$" + cost + ChatColor.GRAY + " that session");
		player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + " - New balance " + ChatColor.DARK_GREEN + "$" + money);
	}

	public void sendELog(Player player) {
		if(player == null) return;
		if(!shopLog.containsKey(player.getName())) {
			return;
		}
		Member member = Member.get(player);
		int money = member.getEconomy().getBalance();
		int cost = shopLog.get(player.getName()).cost;
		if(cost == 0) return;
		for(Enchantment keys : shopLog.get(player.getName()).enchantments.keySet()) player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + "- " + ChatColor.DARK_GREEN + "[" + ChatColor.GRAY + utilItem.toCommon(keys) + " x" + shopLog.get(player.getName()).enchantments.get(keys) + ChatColor.DARK_GREEN + "] - $" + ChatColor.GRAY + getEnchantmentPrice(keys) * shopLog.get(player.getName()).enchantments.get(keys));
				if(cost < 0) player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + " - You gained " + ChatColor.DARK_GREEN + "$" + Math.abs(cost) + ChatColor.GRAY + " that session");
				else if(cost > 0) player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + " - You spent " + ChatColor.DARK_GREEN + "$" + cost + ChatColor.GRAY + " that session");
				player.sendMessage(ChatColor.DARK_GREEN + "Shop Log " + ChatColor.GRAY + " - New balance " + ChatColor.DARK_GREEN + "$" + money);
	}

	public static void addEnchantLevel(Player player, Enchantment enchantment) {
		ItemStack item = player.getItemInHand();
		if(item == null) {
			M.message(player, "Shops", ChatColor.WHITE + "You must be holding an " + ChatColor.YELLOW + "Item");
			return;
		}
		if(!enchantment.canEnchantItem(item)) {
			M.message(player, "Shops", ChatColor.WHITE + "You cannot add" + ChatColor.YELLOW + utilItem.toCommon(enchantment) + ChatColor.WHITE + " to " + ChatColor.YELLOW + utilItem.toCommon(item.getType().toString().toLowerCase()));
			return;
		}
		if(!item.containsEnchantment(enchantment)) {
			item.addEnchantment(enchantment, 1);
			return;
		}
		if(item.getEnchantmentLevel(enchantment) >= getMaxEnchantmentLevel(enchantment)) {
			M.message(player, "Shops", ChatColor.WHITE + "You have reached the limit for " + ChatColor.YELLOW + utilItem.toCommon(enchantment));
			return;
		}
		item.addEnchantment(enchantment, item.getEnchantmentLevel(enchantment) + 1);
	}
	
	public void buyEnchantment(Player player, Enchantment enchantment) {
		if(player == null || enchantment == null) return;
		if(player.getItemInHand().getType().equals(Material.AIR)) {
			M.message(player, "Shops", ChatColor.WHITE + "You must be holding an " + ChatColor.YELLOW + "Enchantable Item");
			return;
		}
		Member member = Member.get(player);
		int balance = member.getEconomy().getBalance();
		if(balance < getEnchantmentPrice(enchantment)) {
			M.message(player, "Economy", ChatColor.WHITE + "You have insufficient money");
			return;
		}
		ItemStack item = player.getItemInHand();
		if(!enchantment.canEnchantItem(item)) {
			M.message(player, "Shops", ChatColor.WHITE + "You cannot add " + ChatColor.YELLOW + utilItem.toCommon(enchantment) + ChatColor.WHITE + " to " + ChatColor.YELLOW + utilItem.toCommon(item.getType().toString().toLowerCase()));
			return;
		}
		if(item.getEnchantmentLevel(enchantment) + 1 > getMaxEnchantmentLevel(enchantment)) {
			M.message(player, "Shops", ChatColor.WHITE + "You have reached the limit for " + ChatColor.YELLOW + utilItem.toCommon(enchantment));
			return;
		}
		if(item.getEnchantmentLevel(enchantment) >= enchantment.getMaxLevel()) return;
		if(!item.containsEnchantment(enchantment)) {
			item.addEnchantment(enchantment, 1);
			member.getEconomy().alterBalance(-getEnchantmentPrice(enchantment));
			M.message(player, "Shops", ChatColor.WHITE + "You bought " + ChatColor.YELLOW + utilItem.toCommon(enchantment) + " 1 ");
			if(shopLog.containsKey(player.getName())) {
				shopLog.get(player.getName()).cost += getEnchantmentPrice(enchantment);
				shopLog.get(player.getName()).addEnchantment(enchantment);
			}
			return;
		}
		item.addEnchantment(enchantment, item.getEnchantmentLevel(enchantment) + 1);
		member.getEconomy().alterBalance(-getEnchantmentPrice(enchantment));
		M.message(player, "Shops", ChatColor.WHITE + "You bought " + ChatColor.YELLOW + utilItem.toCommon(enchantment) + " " + item.getEnchantmentLevel(enchantment));
		if(shopLog.containsKey(player.getName())) {
			shopLog.get(player.getName()).cost += getEnchantmentPrice(enchantment);
			shopLog.get(player.getName()).addEnchantment(enchantment);
		}
	}

	public static int getEnchantmentPrice(Enchantment enchantment) {
		if(enchantment.getName().equalsIgnoreCase("ARROW_DAMAGE")) return 140000;
		if(enchantment.getName().equalsIgnoreCase("DAMAGE_ALL")) return 280000;
		if(enchantment.getName().equalsIgnoreCase("DAMAGE_ARTHROPODS") || enchantment.getName().equalsIgnoreCase("DAMAGE_UNDEAD")) return 45000;
		if(enchantment.getName().equalsIgnoreCase("DURABILITY")) return 45000;
		if(enchantment.getName().equalsIgnoreCase("KNOCKBACK")) return 190000;
		if(enchantment.getName().equalsIgnoreCase("LOOT_BONUS_BLOCKS")) return 180000;
		return 1000000000;
	}

	public static int getMaxEnchantmentLevel(Enchantment enchantment) {
		if(enchantment.equals(Enchantment.ARROW_DAMAGE)) return 2;
		else if(enchantment.equals(Enchantment.DAMAGE_ALL)) return 2;
		else if(enchantment.equals(Enchantment.DAMAGE_ARTHROPODS) || enchantment.equals(Enchantment.DAMAGE_UNDEAD)) return 5;
		else if(enchantment.equals(Enchantment.DURABILITY)) return 3;
		else if(enchantment.equals(Enchantment.KNOCKBACK)) return 2;
		else if(enchantment.equals(Enchantment.LOOT_BONUS_BLOCKS)) return 2;
		else return 10;
	}
	
	public static void databaseCheck() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS shops (name VARCHAR(60), type VARCHAR(20), location VARCHAR(35))");
	}

	public enum ShopType { 
		WEAPONRY("Weaponry", Profession.BLACKSMITH),
		BUILDER("Builder", Profession.FARMER),
		ENCHANTER("Enchanter", Profession.LIBRARIAN),
		MISCELLANEOUS("Miscellaneous", Profession.PRIEST),
		MINER("Miner", Profession.PRIEST),
		BAKER("Baker", Profession.BUTCHER);

		private String name;
		private Profession profession;

		ShopType(String name, Profession profession) {
			this.name = name;
			this.profession = profession;
		}

		public String getName() {
			return this.name;
		}

		public Profession getProfession() {
			return this.profession;
		}
		
		public static ShopType getType(String name) {
			if(name.toLowerCase().contains("misc")) return ShopType.MISCELLANEOUS;
			else if(name.toLowerCase().contains("weapon") || name.toLowerCase().contains("blacksmith")) return ShopType.WEAPONRY;
			else if(name.toLowerCase().contains("builder")) return ShopType.BUILDER;
			else if(name.toLowerCase().contains("enchant")) return ShopType.ENCHANTER;
			else if(name.toLowerCase().contains("miner")) return ShopType.MINER;
			else if(name.toLowerCase().contains("baker")) return ShopType.BAKER;
			else return null;
		}
	}
}
