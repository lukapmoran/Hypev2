package me.loogeh.Hype.Utility;

import java.util.Iterator;
import java.util.Random;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Main.Main;
import net.minecraft.server.v1_7_R3.EntityItem;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class utilItem {

	@SuppressWarnings("deprecation")
	public static String getStringFromItemStack(ItemStack itemstack) {
		if(itemstack == null) return null;
		String item = itemstack.getType().name() + ";" + itemstack.getAmount() + ":" + itemstack.getData().getData();
		return item;
	}

	@SuppressWarnings("deprecation")
	public static void getInvItemFromString(Inventory inventory, String item) {
		if(item == null) return;
		String[] split = null;
		if(item.contains("'")) split = item.split("'");
		String[] splitSemicolon = split[1].split(";");
		String[] splitColon = splitSemicolon[1].split(":");
		String name = splitSemicolon[0];
		String slotStr = splitColon[1].split("/")[1];
		int amount = (Integer) null;
		byte data = (Byte) null;
		int slot = (Integer) null;
		try {
			amount = Integer.parseInt(splitColon[0]);
			data = Byte.parseByte(splitColon[1].replace(',', '\u0000'));
			slot = Integer.parseInt(slotStr);
		} catch(NumberFormatException e) {
			return;
		}
		if(slot == (Integer) null || amount == (Integer) null ||  data == (Byte) null) return;
		ItemStack parsedItem = new ItemStack(Material.getMaterial(name), amount);
		parsedItem.setData(new MaterialData(Material.getMaterial(name), data));
		inventory.setItem(slot, parsedItem);
	}

	public static ItemStack getItemFromString(String item) {
		return null;
	}

	public static int getEnchantmentLevel(ItemStack item) {
		int total = 0;
		for(Iterator<Integer> enchCounter = item.getEnchantments().values().iterator(); enchCounter.hasNext(); ) {
			int enchLvl = ((Integer) enchCounter.next()).intValue();
			total += enchLvl;
		}
		return total;
	}

	public static String toCommon(String item) {
		if(item.equalsIgnoreCase("smooth_brick")) return "Stone Brick";
		else if(item.contains("spade")) return WordUtils.capitalize(item.toLowerCase().replaceAll("_", " ").replaceAll("spade", "shovel"));
		else if(item.equalsIgnoreCase("tnt")) return "TNT";
		else if(item.equalsIgnoreCase("piston_base")) return "Piston";
		else if(item.equalsIgnoreCase("piston_sticky_base")) return "Sticky Piston";
		else if(item.equalsIgnoreCase("command")) return "Command Block";
		else if(item.equalsIgnoreCase("smooth_stairs")) return "Stone Brick Stairs";
		else if(item.contains("chainmail")) return WordUtils.capitalize(item.toLowerCase().replaceAll("_", " ").replace("chainmail", "Chain"));
		else if(item.equalsIgnoreCase("nether_stalk")) return "Nether Wart";
		return WordUtils.capitalize(item.toLowerCase().replaceAll("_", " "));
	}

	public static String toCommon(Enchantment enchantment) {
		if(enchantment.equals(Enchantment.ARROW_DAMAGE)) return "Power";
		if(enchantment.equals(Enchantment.ARROW_FIRE)) return "Flame";
		if(enchantment.equals(Enchantment.ARROW_INFINITE)) return "Infinity";
		if(enchantment.equals(Enchantment.ARROW_KNOCKBACK)) return "Punch";
		if(enchantment.equals(Enchantment.DAMAGE_ALL)) return "Sharpness";
		if(enchantment.equals(Enchantment.DAMAGE_ARTHROPODS)) return "Bane of Arthropods";
		if(enchantment.equals(Enchantment.DAMAGE_UNDEAD)) return "Smite";
		if(enchantment.equals(Enchantment.DIG_SPEED)) return "Efficiency";
		if(enchantment.equals(Enchantment.DURABILITY)) return "Unbreaking";
		if(enchantment.equals(Enchantment.FIRE_ASPECT)) return "Fire Aspect";
		if(enchantment.equals(Enchantment.KNOCKBACK)) return "Knockback";
		if(enchantment.equals(Enchantment.LOOT_BONUS_BLOCKS)) return "Fortune";
		if(enchantment.equals(Enchantment.LOOT_BONUS_MOBS)) return "Looting";
		if(enchantment.equals(Enchantment.OXYGEN)) return "Respiration";
		if(enchantment.equals(Enchantment.PROTECTION_ENVIRONMENTAL)) return "Protection";
		if(enchantment.equals(Enchantment.PROTECTION_EXPLOSIONS)) return "Blast Protection";
		if(enchantment.equals(Enchantment.PROTECTION_FALL)) return "Feather Falling";
		if(enchantment.equals(Enchantment.PROTECTION_FIRE)) return "Fire Protection";
		if(enchantment.equals(Enchantment.PROTECTION_PROJECTILE)) return "Projectile Protection";
		if(enchantment.equals(Enchantment.SILK_TOUCH)) return "Silk Touch";
		if(enchantment.equals(Enchantment.THORNS)) return "Thorns";
		if(enchantment.equals(Enchantment.WATER_WORKER)) return "Aqua Affinity";
		if(enchantment.equals(Enchantment.LURE)) return "Lure";
		if(enchantment.equals(Enchantment.LUCK)) return "Lock of the Sea";
		return "None";
	}

	public static void drop(Material material, Location location, Direction direction, int amount, String metaIdentifier, double vectorDivisor) {
		ItemStack item = new ItemStack(material, 1);
		if(direction == Direction.RADIAL) {
			double incr = Math.round(360.0 / amount);
			for(int i = 1; i <= amount; i++) {
				for (double angle = 0.0; angle < 360.0; angle += incr) {
					double x = Math.cos(angle) / vectorDivisor;
					double z = Math.sin(angle) / vectorDivisor;
					Vector dir = new Vector(x, 0.1, z);
					Item d_item = location.getWorld().dropItem(location, item);
					d_item.setMetadata(metaIdentifier, new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
					d_item.setVelocity(dir);
					d_item.setPickupDelay(10);
					setTicksLived(d_item, 5940);
				}
			}
		}
	}
	
	public static Item shootAngledItem(Material material, Location location, double angle, double y, double divisor) {
		Item item = location.getWorld().dropItem(location, new ItemStack(material, 1));
		item.setVelocity(item.getVelocity().add(new Vector(Math.cos(angle) / divisor, y, Math.sin(angle) / divisor)));
		return item;
	}
	
	public static Item launchHeadItem(Location location, ItemStack itemstack, String metaIdentifier) {
		double[] random = {0.06, 0.09, 0.11, -0.09, -0.06, -0.11, 0.05, 0.02, 0.1, 0.08, 0.12, -0.05, -0.02, -0.1, -0.08};
		Random r = new Random();
		Item item = location.getWorld().dropItemNaturally(location, itemstack);
		item.setVelocity(new Vector(random[r.nextInt(random.length - 1)], 0.3, random[r.nextInt(random.length - 1)]));
		item.setPickupDelay(15);
		item.setMetadata(metaIdentifier, new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
		setTicksLived(item, 5940);
		return item;
	}
	
	public static void setTicksLived(Item item, int ticks) {
		((EntityItem) ((CraftItem) item).getHandle()).age = ticks;
	}
	
	public static boolean block(Block block) {
		if(block == null) return false;
		return block.getType().isBlock();
	}
	
	public static boolean isArmour(ClassType set, Material material) {
		if(set.equals(ClassType.LEATHER)) return material.equals(Material.LEATHER_BOOTS) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_HELMET);
		else if(set.equals(ClassType.GOLD)) return material.equals(Material.GOLD_BOOTS) || material.equals(Material.GOLD_LEGGINGS) || material.equals(Material.GOLD_CHESTPLATE) || material.equals(Material.GOLD_HELMET);
		else if(set.equals(ClassType.CHAIN)) return material.equals(Material.CHAINMAIL_BOOTS) || material.equals(Material.CHAINMAIL_LEGGINGS) || material.equals(Material.CHAINMAIL_CHESTPLATE) || material.equals(Material.CHAINMAIL_HELMET);
		else if(set.equals(ClassType.IRON)) return material.equals(Material.IRON_BOOTS) || material.equals(Material.IRON_LEGGINGS) || material.equals(Material.IRON_CHESTPLATE) || material.equals(Material.IRON_HELMET);
		else if(set.equals(ClassType.DIAMOND)) return material.equals(Material.DIAMOND_BOOTS) || material.equals(Material.DIAMOND_LEGGINGS) || material.equals(Material.DIAMOND_CHESTPLATE) || material.equals(Material.DIAMOND_HELMET);
		return false;
	}
	
	public static ItemStack getHeadItem(HeadType type, int amount) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, amount, type.getData());
		return head;
	}
	
	public enum HeadType {
		WITHER_SKELETON((byte) 1),
		SKELETON((byte) 0),
		ZOMBIE((byte) 2),
		CREEPER((byte) 4),
		PLAYER((byte) 3);
		
		private byte data;
		
		HeadType(byte data) {
			this.data = data;
		}
		
		public byte getData() {
			return this.data;
		}
	}
	
	public enum Direction {
		UP,
		RADIAL;
	}
}
