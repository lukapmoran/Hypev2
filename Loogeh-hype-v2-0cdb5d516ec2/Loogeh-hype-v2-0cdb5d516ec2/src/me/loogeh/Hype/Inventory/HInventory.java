package me.loogeh.Hype.Inventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import me.loogeh.Hype.Miscellaneous.IDHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HInventory {

	public static IDHashMap<HInventory> hInvMap = new IDHashMap<HInventory>();

	private int id;
	private Inventory inventory;
	private HashSet<IFlag> flags = new HashSet<IFlag>();
	private boolean destroyOnClose = true;

	public HInventory(int rows) {
		if(rows * 9 > 54) {
			throw new IllegalArgumentException("Invalid inventory size");
		}

		this.inventory = Bukkit.createInventory(null, rows * 9);
		int id = hInvMap.put(this);
		this.inventory.setMaxStackSize(id + 65);
		this.id = this.inventory.getMaxStackSize();
	}

	public HInventory(String title, int rows) {
		if(rows * 9 > 54) {
			throw new IllegalArgumentException("Invalid inventory size");
		}
		this.inventory = Bukkit.createInventory(null, rows * 9, title);
		int id = hInvMap.put(this);
		this.inventory.setMaxStackSize(id + 65);
		this.id = this.inventory.getMaxStackSize();
	}

	public HInventory(InventoryType type) {
		this.inventory = Bukkit.createInventory(null, type);
		int id = hInvMap.put(this);
		this.inventory.setMaxStackSize(id + 65);
		this.id = this.inventory.getMaxStackSize();
	}

	public HInventory(String title, InventoryType type) {
		this.inventory = Bukkit.createInventory(null, type.getDefaultSize(), title);
		int id = hInvMap.put(this);
		this.inventory.setMaxStackSize(id + 65);
		this.id = this.inventory.getMaxStackSize();
	}

	public int getID() {
		return this.id;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public HashSet<IFlag> getFlags() {
		return this.flags;
	}

	public boolean getDestroyOnClose() {
		return this.destroyOnClose;
	}

	public void setDestroyOnClose(boolean destroy) {
		this.destroyOnClose = destroy;
	}

	public void addFlag(IFlag flag) {
		if(flags.contains(flag)) return;
		flags.add(flag);
	}

	public void open(Player player) {
		if(player == null) return;
		player.closeInventory();
		player.openInventory(getInventory());
	}

	public static HInventory getInventory(Inventory inv) {
		Iterator<HInventory> it = hInvMap.values().iterator();
		while(it.hasNext()) {
			HInventory hInv = it.next();
			if(hInv.getID() == inv.getMaxStackSize()) return hInv;
		}
		return null;
	}

	public static void remove(HInventory h_inv) {
		if(h_inv == null || h_inv.getInventory() == null) return;
		h_inv.getInventory().setMaxStackSize(13);
		if(hInvMap.contains(h_inv.getID())) hInvMap.remove(h_inv);
	}

	public void remove() {
		remove(this);
	}

	public boolean isType(HIType type) {
		String title = ChatColor.stripColor(this.inventory.getTitle());
		return title.equalsIgnoreCase(type.getTitle());
	}

	public void addItem(ItemStack item, String display_name, String... lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.addItem(item);
	}

	public void addItem(Material material, String display_name, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.addItem(item);
	}

	public void addItem(ItemStack item, String display_name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		item.setItemMeta(meta);
		this.inventory.addItem(item);
	}

	public void addItem(Material material, String display_name) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		item.setItemMeta(meta);
		this.inventory.addItem(item);
	}

	public void addItem(ItemStack item, String... lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.addItem(item);
	}

	public void addItem(Material material, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.addItem(item);
	}

	public void setItem(ItemStack item, String display_name, int slot, String... lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(Material material, String display_name, int slot, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(ItemStack item, String display_name, int slot) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(Material material, String display_name, int slot) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(ItemStack item, int slot, String... lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(Material material, int slot, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(Material material, int slot, String display_name, List<String> lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(ItemStack item, int slot, String display_name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.inventory.setItem(slot, item);
	}

	public void setItem(ItemStack item, int[] slots, String display_name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		item.setItemMeta(meta);
		for(int slot : slots) {
			this.inventory.setItem(slot, item);
		}
	}

	public void setItem(ItemStack item, int[] slots, String display_name, String... lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		for(int slot : slots) {
			this.inventory.setItem(slot, item);
		}
	}

	public void setItem(Material material, int[] slots, String display_name) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		item.setItemMeta(meta);
		for(int slot : slots) {
			this.inventory.setItem(slot, item);
		}
	}

	public void setItem(Material material, int[] slots, String display_name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display_name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		for(int slot : slots) {
			this.inventory.setItem(slot, item);
		}
	}

	public enum HIType {
		GAME_TYPE_SELECTOR("Game Type Selector", "Select Game");

		private String name = "";
		private String title = "";

		HIType(String name, String title) {
			this.name = name;
			this.title = title;
		}

		public String getName() {
			return this.name;
		}

		public String getTitle() {
			return this.title;
		}
	}
}
