package me.loogeh.Hype.Armour;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Inventory.IFlag;

import org.bukkit.Material;

public class BuildSelector extends ArmourInventory {

	private BuildManager buildManager;
	private ClassType armourSet;
	
	public BuildSelector(BuildManager buildManager, ClassType set) {
		super(AIType.BUILD_SELECTOR);
		this.buildManager = buildManager;
		this.armourSet = set;
		this.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
		update();
	}
	
	public BuildManager getBuildManager() {
		return this.buildManager;
	}
	
	public ClassType getArmourSet() {
		return this.armourSet;
	}
	
	public void update() {
		if(getBuildManager() == null) {
			System.out.println("null manager");
			return;
		}
		if(getArmourSet() == null) {
			System.out.println("null set");
			return;
		}
		if(getBuildManager().getSelectedBuild(getArmourSet()) == 1) setItem(Material.EXP_BOTTLE, C.boldGreen + "Build 1", 9);
		else setItem(Material.POTION, C.boldRed + "Build 1", 9);
		setItem(Material.BOOK_AND_QUILL, C.boldAqua + "Edit Build 1", 18);
		setItem(Material.FIRE, C.boldRed + "Clear Build 1", 45);
		
		if(getBuildManager().getSelectedBuild(getArmourSet()) == 2) setItem(Material.EXP_BOTTLE, C.boldGreen + "Build 2", 11);
		else setItem(Material.POTION, C.boldRed + "Build 2", 11);
		setItem(Material.BOOK_AND_QUILL, C.boldAqua + "Edit Build 2", 20);
		setItem(Material.FIRE, C.boldRed + "Clear Build 2", 47);
		
		if(getBuildManager().getSelectedBuild(getArmourSet()) == 3) setItem(Material.EXP_BOTTLE, C.boldGreen + "Build 3", 13);
		else setItem(Material.POTION, C.boldRed + "Build 3", 13);
		setItem(Material.BOOK_AND_QUILL, C.boldAqua + "Edit Build 3", 22);
		setItem(Material.FIRE, C.boldRed + "Clear Build 2", 49);
		
		if(getBuildManager().getSelectedBuild(getArmourSet()) == 4) setItem(Material.EXP_BOTTLE, C.boldGreen + "Build 4", 15);
		else setItem(Material.POTION, C.boldRed + "Build 4", 15);
		setItem(Material.BOOK_AND_QUILL, C.boldAqua + "Edit Build 4", 24);
		setItem(Material.FIRE, C.boldRed + "Clear Build 2", 51);
		
		if(getBuildManager().getSelectedBuild(getArmourSet()) == 5) setItem(Material.EXP_BOTTLE, C.boldGreen + "Build 5", 17);
		else setItem(Material.POTION, C.boldRed + "Build 5", 17);
		setItem(Material.BOOK_AND_QUILL, C.boldRed + "Edit Build 5", 26);
		setItem(Material.FIRE, C.boldRed + "Clear Build 2", 53);
	}
}