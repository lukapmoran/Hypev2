package me.loogeh.Hype.Armour;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Inventory.HInventory;
import me.loogeh.Hype.Inventory.IFlag;

public class ArmourInventory extends HInventory {
	
	private AIType type;

	public ArmourInventory(AIType type) {
		super(C.boldGreen + type.getTitle(), 6);
		this.type = type;
		this.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
	}
	
	public ArmourInventory(AIType type, String title) {
		super(title, 6);
		this.type = type;
		this.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
	}
	
	public AIType getAIType() {
		return this.type;
	}
	
	public enum AIType {
		KIT_SELECTOR("Kit Selector", "Select Kit"),
		BUILD_SELECTOR("Build Selector", "Select Build"),
		BUILD_EDITOR("Build Editor", "Edit Build"),
		PURCHASE_CONFIRM("Purchase Confirm", "Confirm Purchase");
		
		private String name;
		private String title;
		
		AIType(String name, String title) {
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
