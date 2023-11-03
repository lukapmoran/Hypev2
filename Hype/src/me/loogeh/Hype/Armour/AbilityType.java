package me.loogeh.Hype.Armour;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum AbilityType {
	AXE("axe") {

		public boolean holdingRequiredItem(Player player) {
			if(player.getItemInHand() == null) return false;
			Material hold = player.getItemInHand().getType();
			return hold.equals(Material.GOLD_AXE) || hold.equals(Material.IRON_AXE) || hold.equals(Material.DIAMOND_AXE);
		}

		public String getItemRequiredString() {
			return "an Axe";
		}


	},
	SWORD("sword") {
		public boolean holdingRequiredItem(Player player) {
			if(player.getItemInHand() == null) return false;
			Material hold = player.getItemInHand().getType();
			return hold.equals(Material.IRON_SWORD) || hold.equals(Material.DIAMOND_SWORD) || hold.equals(Material.GOLD_SWORD);
		}

		public String getItemRequiredString() {
			return "a Sword";
		}

	},
	BOW("bow") {

		public boolean holdingRequiredItem(Player player) {
			if(player.getItemInHand() == null) return false;
			return player.getItemInHand().getType().equals(Material.BOW);
		}

		public String getItemRequiredString() {
			return "a Bow";
		}

	},
	PASSIVE("passive") {

		public boolean holdingRequiredItem(Player player) {
			return true;
		}

		public String getItemRequiredString() {
			return "Anything";
		}

	},
	PASSIVE_II("passive_II") {

		public boolean holdingRequiredItem(Player player) {
			return true;
		}

		public String getItemRequiredString() {
			return "Anything";
		}

	},
	PASSIVE_III("passive_III") {

		public boolean holdingRequiredItem(Player player) {
			return true;
		}

		public String getItemRequiredString() {
			return "Anything";
		}

	},
	NONE {

		@Override
		public boolean holdingRequiredItem(Player player) {
			return false;
		}

		@Override
		public String getItemRequiredString() {
			return "None";
		}

	};

	public abstract boolean holdingRequiredItem(Player player);
	public abstract String getItemRequiredString();
	
	private String dbColumnName = "column";
	
	AbilityType(String dbColumnName) {
		this.dbColumnName = dbColumnName;
	}
	
	AbilityType() {}
	
	public String getDatabaseColumnName() {
		return this.dbColumnName;
	}
}
