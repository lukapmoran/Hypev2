package me.loogeh.Hype.Shops;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ShopLog {

	public HashMap<ItemStack, Integer> items = new HashMap<ItemStack, Integer>();
	public HashMap<ItemStack, Integer> sold_items = new HashMap<ItemStack, Integer>();
	public HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
	public int cost = 0;
	public String player;

	public ShopLog(String player) {
		this.player = player;
		this.cost = 0;
	}

	public int getCost() {
		return this.cost;
	}

	public String getPlayer() {
		return this.player;
	}

	public boolean bought(ItemStack stack) {
		for(ItemStack item : items.keySet()) {
			if(item.getType() == stack.getType() && item.getDurability() == stack.getDurability()) return true;
		}
		return false;
	}
	
	public boolean sold(ItemStack stack) {
		for(ItemStack item : sold_items.keySet()) {
			if(item.getType() == stack.getType() && item.getDurability() == stack.getDurability()) return true;
		}
		return false;
	}

	public void addSpent(ItemStack item, int amount) {
		if(!bought(item)) {
			items.put(item, amount);
			return;
		}
		HashMap<ItemStack, Integer> temp = new HashMap<ItemStack, Integer>();
		Iterator<ItemStack> it = items.keySet().iterator();
		while(it.hasNext()) {
			ItemStack next = it.next();
			if(next.getType() == item.getType() && next.getDurability() == item.getDurability()) {
				int cur = next.getAmount();
				it.remove();
				temp.put(new ItemStack(item.getType(), cur + amount, item.getDurability()), cur + amount);
			}
		}
		items.putAll(temp);
	}
	
	public void addSold(ItemStack item, int amount) {
		if(!sold(item)) {
			sold_items.put(item, amount);
			return;
		}
		HashMap<ItemStack, Integer> temp = new HashMap<ItemStack, Integer>();
		Iterator<ItemStack> it = sold_items.keySet().iterator();
		while(it.hasNext()) {
			ItemStack next = it.next();
			if(next.getType() == item.getType() && next.getDurability() == item.getDurability()) {
				int cur = sold_items.get(next);
				it.remove();
				temp.put(new ItemStack(item.getType(), cur + amount, item.getDurability()), cur + amount);
			}
		}
		sold_items.putAll(temp);
	}
	
	public void addEnchantment(Enchantment enchantment) {
		if(!enchantments.containsKey(enchantment)) {
			enchantments.put(enchantment, 1);
			return;
		}
		HashMap<Enchantment, Integer> temp = new HashMap<Enchantment, Integer>();
		Iterator<Enchantment> it = enchantments.keySet().iterator();
		while(it.hasNext()) {
			Enchantment ench = it.next();
			if(ench == enchantment) {
				int cur = enchantments.get(ench);
				it.remove();
				temp.put(ench, cur + 1);
			}
		}
		enchantments.putAll(temp);
	}

}