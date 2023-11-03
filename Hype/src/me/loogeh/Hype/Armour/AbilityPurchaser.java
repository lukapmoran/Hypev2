package me.loogeh.Hype.Armour;

import me.loogeh.Hype.Formatting.C;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AbilityPurchaser extends ArmourInventory {

	private ClassType set;
	private AbilityInfo ability;
	private int build;
	
	public AbilityPurchaser(AbilityInfo ability, ClassType set, int build) {
		super(AIType.PURCHASE_CONFIRM);
		this.ability = ability;
		this.set = set;
		this.build = build;
		update();
	}
	
	public AbilityInfo getAbility() {
		return this.ability;
	}
	
	public ClassType getArmourSet() {
		return this.set;
	}
	
	public int getBuild() {
		return this.build;
	}
	
	public void update() {
		setItem(new ItemStack(Material.BOOK), C.boldAqua + ability.getName(), 22);
		
		setItem(Material.EMERALD_BLOCK, new int[] {27, 28, 29, 36, 37, 38, 45, 46, 47}, C.boldGreen + "CONFIRM");
		
		setItem(Material.REDSTONE_BLOCK, new int[] {33, 34, 35, 42, 43, 44, 51, 52, 53}, C.boldRed + "CANCEL");
	}
	
	

}
