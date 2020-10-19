package me.loogeh.Hype.Armour;

public enum ClassType {
	DIAMOND("Diamond", "Beast"),
	IRON("Iron", "Knight"),
	CHAIN("Chainmail", "Rogue"),
	GOLD("Gold", "Wizard"),
	LEATHER("Leather", "Archer"),
	EMPTY("Empty", "Empty"),
	NONE("None", "None");
	
	private String minecraft_name;
	private String kit_name;
	
	ClassType(String minecraft_name, String kit_name) {
		this.minecraft_name = minecraft_name;
		this.kit_name = kit_name;
	}
	
	public String getMinecraftName() {
		return this.minecraft_name;
	}
	
	public String getKitName() {
		return this.kit_name;
	}
	
	public static ClassType getSet(String name) {
		for(ClassType set : values()) {
			if(set.getMinecraftName().equalsIgnoreCase(name) || set.getKitName().equalsIgnoreCase(name)) return set;
		}
		return null;
	}
}