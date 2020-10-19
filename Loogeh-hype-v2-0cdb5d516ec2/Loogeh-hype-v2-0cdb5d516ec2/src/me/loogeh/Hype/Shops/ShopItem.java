package me.loogeh.Hype.Shops;

import java.util.List;

import org.bukkit.Material;

public class ShopItem {
	
	private String display_name;
	private Material material;
	private short data = (short) 0;
	private int buy_cost; //0 means can't buy
	private int sell_price; //0 means can't sell
	private int slot_id;
	private ItemType category;
	private List<String> lore = null;
	
	public ShopItem(String display_name, Material material, short data, int buy_cost, int sell_price, int slot_id, ItemType category) {
		this.display_name = display_name;
		this.material = material;
		this.data = data;
		this.buy_cost = buy_cost;
		this.sell_price = sell_price;
		this.slot_id = slot_id;
		this.category = category;
	}
	
	public ShopItem(String display_name, Material material, int buy_cost, int sell_price, int slot_id, ItemType category) {
		this.display_name = display_name;
		this.material = material;
		this.buy_cost = buy_cost;
		this.sell_price = sell_price;
		this.slot_id = slot_id;
		this.category = category;
	}
	
	public ShopItem(String display_name, Material material, short data, int buy_cost, int sell_price, int slot_id, ItemType category, List<String> lore) {
		this.display_name = display_name;
		this.material = material;
		this.data = data;
		this.buy_cost = buy_cost;
		this.sell_price = sell_price;
		this.slot_id = slot_id;
		this.category = category;
		this.lore = lore;
	}
	
	public ShopItem(String display_name, Material material, int buy_cost, int sell_price, int slot_id, ItemType category, List<String> lore) {
		this.display_name = display_name;
		this.material = material;
		this.buy_cost = buy_cost;
		this.sell_price = sell_price;
		this.slot_id = slot_id;
		this.category = category;
		this.lore = lore;
	}
	
//	public String getName() {
//		return Item.getName(this.material);
//	}
	
	public String getDisplayName() {
		return this.display_name;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public short getData() {
		return this.data;
	}
	
	public int getBuyCost() {
		return this.buy_cost;
	}
	
	public int getSellPrice() {
		return this.sell_price;
	}
	
	public int getSlotID() {
		return this.slot_id;
	}
	
	public ItemType getCategory() {
		return this.category;
	}
	
	public List<String> getLore() {
		return this.lore;
	}
	
	public void setDisplayName(String name) {
		this.display_name = name;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public void setData(byte data) {
		this.data = data;
	}
	
	public void setBuyCost(int cost) {
		this.buy_cost = cost;
	}
	
	public void setSellPrice(int price) {
		this.sell_price = price;
	}
	
	public void setSlotID(int slot_id) {
		this.slot_id = slot_id;
	}
	
	public void setCategory(ItemType category) {
		this.category = category;
	}
	
	
	public enum ItemType {
		ARMOUR(0, "Armour"),
		WEAPON(1, "Weapon"),
		FOOD(2, "Food"),
		ENCHANTMENT(3, "Enchantment"),
		BLOCK(4, "Block"),
		MINERAL(5, "Mineral"),
		ITEM(6, "Item"),
		SUPPLY(7, "Supply"),
		DECORATION(8, "Decoration"),
		OTHER(9, "Other");
		
		private int id;
		private String name;
		
		ItemType(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public int getID() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
	}
}


