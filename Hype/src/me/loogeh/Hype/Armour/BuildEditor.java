package me.loogeh.Hype.Armour;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Inventory.IFlag;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.util;

public class BuildEditor extends ArmourInventory {
	
	private Member holder;
	private BuildFactory build;

	public BuildEditor(Member holder, BuildFactory build) {
		super(AIType.BUILD_EDITOR, C.boldGreen + "Edit Build " + build.getArmourSet().getKitName() + " " + build.getBuildId());
		super.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
		if(holder.getPlayer() == null) {
			super.remove();
			try { 
				throw new NullPointerException("Sector > 'armour inventory' > 'build editor' player cannot be null");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		this.holder = holder;
		this.build = build;
		update();
	}
	
	public Member getHolder() {
		return this.holder;
	}
	
	public BuildFactory getBuildFactory() {
		return this.build;
	}
	
	public void setBuildFactory(BuildFactory buildFactory) {
		this.build = buildFactory;
	}
	
	public void update() {
		setItem(Material.IRON_SWORD, C.boldGreen + "Sword Abilities", 0);
		setItem(Material.IRON_AXE, C.boldGreen + "Axe Abilities", 9);
		setItem(Material.BOW, C.boldGreen + "Bow Abilities", 18);
		setItem(Material.IRON_INGOT, C.boldGreen + "Passive I", 27);
		setItem(Material.GOLD_INGOT, C.boldGreen + "Passive II", 36);
		setItem(Material.EMERALD, C.boldGreen + "Passive III", 45);
		int count = 1;
		for(AbilityInfo sword : AbilityInfo.getAbilities(getBuildFactory().getArmourSet(), AbilityType.SWORD)) {
			AbilityBook book = new AbilityBook(getHolder(), getBuildFactory().getArmourSet(), getBuildFactory().getBuildId(), sword);
			setItem(book.getItem(), count, util.getArrayFromList(sword.getLore()));
			count++;
		}
		count = 10;
		for(AbilityInfo axe : AbilityInfo.getAbilities(getBuildFactory().getArmourSet(), AbilityType.AXE)) {
			AbilityBook book = new AbilityBook(getHolder(), getBuildFactory().getArmourSet(), getBuildFactory().getBuildId(), axe);
			setItem(book.getItem(), count, util.getArrayFromList(axe.getLore()));
			count++;
		}
		
		count = 19;
		for(AbilityInfo bow : AbilityInfo.getAbilities(getBuildFactory().getArmourSet(), AbilityType.BOW)) {
			AbilityBook book = new AbilityBook(getHolder(), getBuildFactory().getArmourSet(), getBuildFactory().getBuildId(), bow);
			setItem(book.getItem(), count, util.getArrayFromList(bow.getLore()));
			count++;
		}
		
		count = 28;
		for(AbilityInfo passive : AbilityInfo.getAbilities(getBuildFactory().getArmourSet(), AbilityType.PASSIVE)) {
			AbilityBook book = new AbilityBook(getHolder(), getBuildFactory().getArmourSet(), getBuildFactory().getBuildId(), passive);
			setItem(book.getItem(), count, util.getArrayFromList(passive.getLore()));
			count++;
		}
		
		count = 37;
		for(AbilityInfo passiveII : AbilityInfo.getAbilities(getBuildFactory().getArmourSet(), AbilityType.PASSIVE_II)) {
			AbilityBook book = new AbilityBook(getHolder(), getBuildFactory().getArmourSet(), getBuildFactory().getBuildId(), passiveII);
			setItem(book.getItem(), count, util.getArrayFromList(passiveII.getLore()));
			count++;
		}
		
		count = 46;
		for(AbilityInfo passiveIII : AbilityInfo.getGlobalPassives()) {
			AbilityBook book = new AbilityBook(getHolder(), getBuildFactory().getArmourSet(), getBuildFactory().getBuildId(), passiveIII);
			setItem(book.getItem(), count, util.getArrayFromList(passiveIII.getLore()));
			count++;
		}
	}
	
	
	class AbilityBook {
		
		private ItemStack item;
		private ClassType set;
		private int build;
		private AbilityInfo ability;
		
		public AbilityBook(Member member, ClassType set, int build, AbilityInfo ability) {
			BuildManager manager = member.getClasses().getBuildManager();
			if(manager.isSelected(set, ability)) {
				this.item = new ItemStack(Material.ENCHANTED_BOOK);
				ItemMeta meta = this.item.getItemMeta();
				meta.setDisplayName(C.boldGreen + ability.getName());
				meta.setLore(ability.getLore());
				this.item.setItemMeta(meta);
			} else if(manager.isUnlocked(set, ability)) {
				this.item = new ItemStack(Material.BOOK);
				ItemMeta meta = this.item.getItemMeta();
				meta.setDisplayName(C.boldGreen + ability.getName());
				meta.setLore(ability.getLore());
				this.item.setItemMeta(meta);
			} else {
				this.item = new ItemStack(Material.BOOK_AND_QUILL);
				ItemMeta meta = this.item.getItemMeta();
				meta.setDisplayName(C.boldRed + ability.getName() + ChatColor.RED + " (Locked)");
				meta.setLore(ability.getLore());
				this.item.setItemMeta(meta);
			}
		}
		
		public ItemStack getItem() {
			return this.item;
		}
		
		public ClassType getArmourSet() {
			return this.set;
		}
		
		public int getBuildId() {
			return this.build;
		}
		
		public AbilityInfo getAbility() {
			return this.ability;
		}
		
	}
	
}
