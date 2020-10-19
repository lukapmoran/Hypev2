package me.loogeh.Hype.Armour;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.loogeh.Hype.Main.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Armour {
	
	public static ClassType getArmourPieceSet(Material material) {
		if(material.equals(Material.DIAMOND_HELMET) || material.equals(Material.DIAMOND_CHESTPLATE) || material.equals(Material.DIAMOND_LEGGINGS) || material.equals(Material.DIAMOND_BOOTS)) return ClassType.DIAMOND;
		else if(material.equals(Material.IRON_HELMET) || material.equals(Material.IRON_CHESTPLATE) || material.equals(Material.IRON_LEGGINGS) || material.equals(Material.IRON_BOOTS)) return ClassType.IRON;
		else if(material.equals(Material.CHAINMAIL_HELMET) || material.equals(Material.CHAINMAIL_CHESTPLATE) || material.equals(Material.CHAINMAIL_LEGGINGS) || material.equals(Material.CHAINMAIL_BOOTS)) return ClassType.CHAIN;
		else if(material.equals(Material.GOLD_HELMET) || material.equals(Material.GOLD_CHESTPLATE) || material.equals(Material.GOLD_LEGGINGS) || material.equals(Material.GOLD_BOOTS)) return ClassType.GOLD;
		else if(material.equals(Material.LEATHER_HELMET) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_BOOTS)) return ClassType.LEATHER;
		else return ClassType.NONE;
	}
	
	public static ClassType getKit(Player player) {
		if(player == null) return ClassType.NONE;
		PlayerInventory inv = player.getInventory();
		if(inv.getBoots() == null && inv.getChestplate() == null && inv.getLeggings() == null && inv.getHelmet() == null) return ClassType.EMPTY;
		else if(inv.getBoots() == null || inv.getChestplate() == null || inv.getLeggings() == null || inv.getHelmet() == null) return ClassType.NONE;
		else if(inv.getBoots().getType() == Material.DIAMOND_BOOTS && inv.getLeggings().getType() == Material.DIAMOND_LEGGINGS && inv.getChestplate().getType() == Material.DIAMOND_CHESTPLATE && inv.getHelmet().getType() == Material.DIAMOND_HELMET) return ClassType.DIAMOND;
		else if(inv.getBoots().getType() == Material.IRON_BOOTS && inv.getLeggings().getType() == Material.IRON_LEGGINGS && inv.getChestplate().getType() == Material.IRON_CHESTPLATE && inv.getHelmet().getType() == Material.IRON_HELMET) return ClassType.IRON;
		else if(inv.getBoots().getType() == Material.CHAINMAIL_BOOTS && inv.getLeggings().getType() == Material.CHAINMAIL_LEGGINGS && inv.getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE && inv.getHelmet().getType() == Material.CHAINMAIL_HELMET) return ClassType.CHAIN;
		else if(inv.getBoots().getType() == Material.GOLD_BOOTS && inv.getLeggings().getType() == Material.GOLD_LEGGINGS && inv.getChestplate().getType() == Material.GOLD_CHESTPLATE && inv.getHelmet().getType() == Material.GOLD_HELMET) return ClassType.GOLD;
		else if(inv.getBoots().getType() == Material.LEATHER_BOOTS && inv.getLeggings().getType() == Material.LEATHER_LEGGINGS && inv.getChestplate().getType() == Material.LEATHER_CHESTPLATE && inv.getHelmet().getType() == Material.LEATHER_HELMET) return ClassType.LEATHER;
		else return null;
	}
	
	public static int getArmourPieces(Player player) {
		if(player == null) return (Integer) null;
		int count = 0;
		for(ItemStack item : player.getInventory().getArmorContents()) {
			if(item != null) count++;
		}
		return count;
	}
	
	public static boolean holdingSword(Player player) {
		Material holding = player.getItemInHand().getType();
		return holding == Material.WOOD_SWORD || holding == Material.STONE_SWORD || holding == Material.GOLD_SWORD || holding == Material.IRON_SWORD || holding == Material.DIAMOND_SWORD;
	}
	
	public static void databaseCheck() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS armour_builds (uuid TEXT, kit VARCHAR(10), build INTEGER(11), sword INTEGER(11), axe INTEGER(11), bow INTEGER(11), passive INTEGER(11), passive_II INTEGER(11), passive_III INTEGER(11))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS ability_unlocks (uuid TEXT, kit VARCHAR(10), ability INTEGER(11))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS selected_builds (uuid TEXT, kit VARCHAR(10), build INTEGER(11))");
	}
	
	public static boolean tryDefaults(String uuid) {
		boolean defaults = false;
		if(getBuildsCount(uuid) == 0) {
			Main.mysql.doUpdate("DELETE FROM `armour_builds` WHERE uuid='" + uuid + "'");
			Main.mysql.doUpdate("DELETE FROM `ability_unlocks` WHERE uuid='" + uuid + "'");
			Main.mysql.doUpdate("DELETE FROM `selected_builds` WHERE uuid='" + uuid + "'");
			for(ClassType set : ClassType.values()) {
				if(!set.equals(ClassType.NONE) && !set.equals(ClassType.EMPTY)) {
					Main.mysql.doUpdate("INSERT INTO `selected_builds`(`uuid`, `kit`, `build`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', 1)");
					AbilityInfo[] abilities = AbilityInfo.getDefaults(set);
					Main.mysql.doUpdate("INSERT INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + abilities[0].getID() + ")");
					Main.mysql.doUpdate("INSERT INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + abilities[1].getID() + ")");
					Main.mysql.doUpdate("INSERT INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + abilities[2].getID() + ")");
					Main.mysql.doUpdate("INSERT INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + abilities[3].getID() + ")");
					Main.mysql.doUpdate("INSERT INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + abilities[4].getID() + ")");
					Main.mysql.doUpdate("INSERT INTO `ability_unlocks`(`uuid`, `kit`, `ability`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + abilities[5].getID() + ")");
					for(int i = 1; i < 6; i++) {
						Main.mysql.doUpdate("INSERT INTO `armour_builds`(`uuid`, `kit`, `build`, `sword`, `axe`, `bow`, `passive`, `passive_II`, `passive_III`) VALUES ('" + uuid + "','" + set.getMinecraftName() + "', " + i + ", " + abilities[0].getID() + ", " + abilities[1].getID() + ", " + abilities[2].getID() + ", " + abilities[3].getID() + ", " + abilities[4].getID() + ", " + abilities[5].getID() + ")");	
					}
				}
			}
			defaults = true;
		}
		return defaults;
	}
	
	public static boolean getBuildsContains(String uuid) {
		ResultSet rs = Main.mysql.doQuery("SELECT player FROM armour_builds WHERE uuid='" + uuid + "'");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean getSelectedBuildsContains(String uuid) {
		ResultSet rs = Main.mysql.doQuery("SELECT player FROM selected_builds WHERE uuid='" + uuid + "'");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getSelectedBuildsCount(String uuid) {
		ResultSet rs = Main.mysql.doQuery("SELECT COUNT(*) FROM selected_builds WHERE uuid='" + uuid + "'");
		try {
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 5;
	}
	
	public static int getBuildsCount(String uuid) {
		ResultSet rs = Main.mysql.doQuery("SELECT COUNT(*) FROM armour_builds WHERE uuid='" + uuid + "'");
		try {
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 5;
	}
	
	public static int getInventoryRow(AbilityType type) {
		if(type.equals(AbilityType.SWORD)) return 0;
		else if(type.equals(AbilityType.AXE)) return 1;
		else if(type.equals(AbilityType.BOW)) return 2;
		else if(type.equals(AbilityType.PASSIVE)) return 3;
		else if(type.equals(AbilityType.PASSIVE_II)) return 4;
		else return 5;
	}
}
