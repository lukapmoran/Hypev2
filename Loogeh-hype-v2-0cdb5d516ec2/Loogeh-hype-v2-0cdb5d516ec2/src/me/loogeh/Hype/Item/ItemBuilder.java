package me.loogeh.Hype.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
	
	private ItemStack item;
	private String name = "";
	private List<String> lore = new ArrayList<String>();
	private short durability = -5;
	private MaterialData data;
	
	public ItemBuilder(Material item) {
		this.item = new ItemStack(item);
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	
	public String getName() {
		return this.name;
	}
	
	public short getDurability() {
		return this.durability;
	}
	
	public void amount(int amount) {
		item.setAmount(amount);
	}
	
	public ItemBuilder addName(String name) {
		this.name = name;
		return this;
	}
	
	public ItemBuilder addLore(String...lore) {
		this.lore.addAll(Arrays.asList(lore));
		return this;
	}
	
	public ItemBuilder addLore(List<String> lore) {
		this.lore.addAll(lore);
		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean safe) {
		if(safe) this.item.addEnchantment(enchantment, level);
		else this.item.addUnsafeEnchantment(enchantment, level);
		return this;
	}
	
	public ItemBuilder addDurability(short durability) {
		this.durability = durability;
		return this;
	}
	
	public ItemBuilder addDurability(int durability) {
		this.durability = (short) durability;
		return this;
	}
	
	public ItemBuilder addData(MaterialData data) {
		this.data = data;
		return this;
	}
	
	public ItemStack toItemStack() {
		ItemStack item = new ItemStack(getItem().getType(), getItem().getAmount());
		if(getDurability() != -5) item.setDurability(getDurability());
		if(this.data != null) item.setData(this.data);
		ItemMeta meta = item.getItemMeta();
		if(!getName().equals("")) meta.setDisplayName(getName());
		if(!this.lore.isEmpty()) meta.setLore(this.lore);
		item.setItemMeta(meta);
		return item;
	}

}
