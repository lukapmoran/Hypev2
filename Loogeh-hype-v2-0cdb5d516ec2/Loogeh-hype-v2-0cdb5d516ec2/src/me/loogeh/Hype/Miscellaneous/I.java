package me.loogeh.Hype.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class I {
	
//	public static ItemStack strengthPotion = strengthPotionStuff
	
	public static List<String> D_SWORD = Arrays.asList(" ", "§fUses your selected §eBeast Sword Ability");
	public static List<String> D_AXE = Arrays.asList(" ", "§fUses your selected §eAxe Ability §fwith your current §eArmour Set", "§fCripples opponents upon hitting them");
	public static List<String> D_HOE = Arrays.asList(" ", "§fCasts a §eLightning Strike §fwhere you're looking");
	
	public static List<String> I_SWORD = Arrays.asList(" ", "§fUses your selected §eKnight Sword Ability");
	public static List<String> I_AXE = Arrays.asList(" ", "§fUses your selected §eKnight Axe Ability");
	
	public static List<String> C_SWORD = Arrays.asList(" ", "§fUses your selected §eRogue Sword Ability");
	public static List<String> C_AXE = Arrays.asList(" ", "§fUses your selected §eRogue Axe Ability");
	
	public static List<String> G_SWORD = Arrays.asList(" ", "§fUses your selected §eWizard Sword Ability");
	public static List<String> G_AXE = Arrays.asList(" ", "§fSets opponents on fire upon hitting them");
	
	public static List<String> L_SWORD = Arrays.asList(" ", "§fUses your selected §eArcher Sword Ability");
	public static List<String> L_AXE = Arrays.asList(" ", "§fUses your selected §eArcher Axe Ability");
	
	public static List<String> SWORD = Arrays.asList(" ", "§fUses your selected §eSword Ability §fwith your current §eArmour Set");
	public static List<String> AXE = Arrays.asList(" ", "§fUses your selected §eAxe Ability §fwith your current §eArmour Set");
	
	public static List<String> ITEM_WEB = Arrays.asList(" ", "§fShoots and arrow which generates a 2x2x2", "§farea of §eWeb §fupon hitting an object");
	public static List<String> ITEM_MAGMA_CREAM = Arrays.asList(" ", "§fShoots and arrow which generates a 2x2 area of §eFire §fupon hitting an object");

	public static List<String> getLoreFromString(String lore_str) {
		List<String> list = new ArrayList<String>();
		if(!lore_str.contains("-")) {
			list.add(lore_str);
			return list;
		}
		String[] split = lore_str.split("-");
		for(String elem : split) {
			list.add(elem);
		}
		return list;
	}
	
	public static List<String> getLoreFromString(String... lore) {
		return Arrays.asList(lore);
	}
	
	public static List<String> getLoreFromString(String lore, int index_target) {//test
		List<String> list = new ArrayList<String>();
		if(!lore.contains(" ")) {
			list.add(lore);
			return list;
		}
		int last_space = 0, last_sub = 0;
		for(int i = 0; i < lore.length(); i++) {
			if(lore.charAt(i) == ' ') {
				if(i >= index_target) {
					last_space = i;
					list.add(lore.substring(last_sub, last_space));
					last_sub = last_space;
				}
			}
		}
		return list;
	}
	
	public static ItemMeta addShopLore(ItemStack item, String line_1, String line_2, List<String> lore) {
		if(item == null) return null;
		ItemMeta meta = item.getItemMeta();
		List<String> newLore = new ArrayList<String>();
		if(line_1 != null) newLore.add(line_1);
		if(line_2 != null) newLore.add(line_2);
		if(lore != null) newLore.addAll(lore);
		meta.setLore(newLore);
		return meta;
	}
	
	
}
