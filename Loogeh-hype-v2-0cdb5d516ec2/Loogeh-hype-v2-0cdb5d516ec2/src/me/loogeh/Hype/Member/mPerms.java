package me.loogeh.Hype.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Sector.Sector;
import me.loogeh.Hype.Utility.utilServer;

public class mPerms extends Sector {

	private String uuid;
	
	private HashSet<String> permissionNodes = new HashSet<String>();
	
	public mPerms(Member member) {
		super("Permissions");
		this.uuid = member.getUUID();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public boolean promote() {
		Rank rank = getRank();
		Player player = utilServer.getPlayer(getUUID());
		if(rank.equals(Rank.OWNER)) return false;
		if(rank.equals(Rank.ADMIN)) {
			permissionNodes.add("owner");
			Main.mysql.doUpdate("UPDATE permissions SET rank='owner' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been promoted to " + ChatColor.YELLOW + "Owner");
			return true;
		}
		if(rank.equals(Rank.MODERATOR)) {
			permissionNodes.add("admin");
			Main.mysql.doUpdate("UPDATE permissions SET rank='admin' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been promoted to " + ChatColor.YELLOW + "Admin");
			return true;
		}
		if(rank.equals(Rank.HELPER)) {
			permissionNodes.add("moderator");
			Main.mysql.doUpdate("UPDATE permissions SET rank='moderator' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been promoted to " + ChatColor.YELLOW + "Moderator");
			return true;
		}
		if(rank.equals(Rank.MEMBER)) {
			permissionNodes.add("helper");
			Main.mysql.doUpdate("UPDATE permissions SET rank='helper' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been promoted to " + ChatColor.YELLOW + "Helper");
			return true;
		}
		return true;
	}
	
	public boolean demote() {
		Rank rank = getRank();
		Player player = utilServer.getPlayer(getUUID());
		if(rank.equals(Rank.OWNER)) return false;
		if(rank.equals(Rank.ADMIN)) {
			permissionNodes.remove("admin");
			Main.mysql.doUpdate("UPDATE permissions SET rank='moderator' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been demoted to " + ChatColor.YELLOW + "Moderator");
			return true;
		}
		if(rank.equals(Rank.MODERATOR)) {
			permissionNodes.remove("moderator");
			Main.mysql.doUpdate("UPDATE permissions SET rank='helper' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been demoted to " + ChatColor.YELLOW + "Helper");
			return true;
		}
		if(rank.equals(Rank.HELPER)) {
			permissionNodes.remove("helper");
			Main.mysql.doUpdate("UPDATE permissions SET rank='member' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "You have been demoted to " + ChatColor.YELLOW + "Member");
			return true;
		}
		if(rank.equals(Rank.MEMBER)) return false;
		return false;
	}
	
	public boolean set(Rank rank) {
		Player player = utilServer.getPlayer(getUUID());
		if(rank.equals(Rank.OWNER))  {
			permissionNodes.remove("owner");
			permissionNodes.remove("admin");
			permissionNodes.remove("moderator");
			permissionNodes.remove("helper");
			permissionNodes.remove("member");
			permissionNodes.add("owner");
			Main.mysql.doUpdate("UPDATE permissions SET rank='owner' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "Your rank has been set to " + ChatColor.YELLOW + "Owner");
			return true;
		}
		if(rank.equals(Rank.ADMIN)) {
			permissionNodes.remove("owner");
			permissionNodes.remove("admin");
			permissionNodes.remove("moderator");
			permissionNodes.remove("helper");
			permissionNodes.remove("member");
			permissionNodes.add("admin");
			Main.mysql.doUpdate("UPDATE permissions SET rank='admin' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "Your rank has been set to " + ChatColor.YELLOW + "Admin");
			return true;
		}
		if(rank.equals(Rank.MODERATOR)) {
			permissionNodes.remove("owner");
			permissionNodes.remove("admin");
			permissionNodes.remove("moderator");
			permissionNodes.remove("helper");
			permissionNodes.remove("member");
			permissionNodes.add("moderator");
			Main.mysql.doUpdate("UPDATE permissions SET rank='moderator' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "Your rank has been set to " + ChatColor.YELLOW + "Moderator");
			return true;
		}
		if(rank.equals(Rank.HELPER)) {
			permissionNodes.remove("owner");
			permissionNodes.remove("admin");
			permissionNodes.remove("moderator");
			permissionNodes.remove("helper");
			permissionNodes.remove("member");
			permissionNodes.add("helper");
			Main.mysql.doUpdate("UPDATE permissions SET rank='helper' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "Your rank has been set to " + ChatColor.YELLOW + "Helper");
			return true;
		}
		if(rank.equals(Rank.MEMBER)) {
			permissionNodes.remove("owner");
			permissionNodes.remove("admin");
			permissionNodes.remove("moderator");
			permissionNodes.remove("helper");
			permissionNodes.remove("member");
			permissionNodes.add("member");
			Main.mysql.doUpdate("UPDATE permissions SET rank='admin' WHERE uuid='" + getUUID() + "'");
			if(player != null) message(player, ChatColor.WHITE + "Your rank has been set to " + ChatColor.YELLOW + "Member");
			return true;
		}
		return false;
	}
	
	public boolean is(Rank rank) {
		if(rank.equals(Rank.OWNER)) return permissionNodes.contains("owner");
		if(rank.equals(Rank.ADMIN)) return permissionNodes.contains("owner") || permissionNodes.contains("admin");
		if(rank.equals(Rank.MODERATOR)) return permissionNodes.contains("owner") || permissionNodes.contains("admin") || permissionNodes.contains("moderator");
		if(rank.equals(Rank.HELPER)) return permissionNodes.contains("owner") || permissionNodes.contains("admin") || permissionNodes.contains("moderator") || permissionNodes.contains("helper");
		if(rank.equals(Rank.MEMBER)) return permissionNodes.contains("owner") || permissionNodes.contains("admin") || permissionNodes.contains("moderator") || permissionNodes.contains("helper") || permissionNodes.contains("member");
		return false;
	}
	
	public Rank getRank() {
		if(permissionNodes.contains("owner")) return Rank.OWNER;
		if(permissionNodes.contains("admin")) return Rank.ADMIN;
		if(permissionNodes.contains("moderator")) return Rank.MODERATOR;
		if(permissionNodes.contains("helper")) return Rank.HELPER;
		return Rank.MEMBER;
	}
	
	public void load() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS permissions (uuid TEXT, rank VARCHAR(12))");
		
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM permissions WHERE uuid='" + getUUID() + "'");
		try {
			if(rs.next()) {
				if(rs.getString(2).equalsIgnoreCase("owner")) {
					permissionNodes.add("owner");
					permissionNodes.add("admin");
					permissionNodes.add("moderator");
					permissionNodes.add("helper");
					permissionNodes.add("member");
				}
				if(rs.getString(2).equalsIgnoreCase("admin")) {
					permissionNodes.add("admin");
					permissionNodes.add("moderator");
					permissionNodes.add("helper");
					permissionNodes.add("member");
				}
				if(rs.getString(2).equalsIgnoreCase("moderator")) {
					permissionNodes.add("moderator");
					permissionNodes.add("helper");
					permissionNodes.add("member");
				}
				if(rs.getString(2).equalsIgnoreCase("helper")) {
					permissionNodes.add("helper");
					permissionNodes.add("member");
				}
				if(rs.getString(2).equalsIgnoreCase("member")) {
					permissionNodes.add("member");
				}
			} else {
				Main.mysql.doUpdate("INSERT INTO permissions (uuid, rank) VALUES ('" + getUUID() + "','member')");
				permissionNodes.add("member");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Permissions Nodes " + permissionNodes.size());
	}
	
	public enum Rank {
		OWNER("Owner", 5, C.DarkRed),
		ADMIN("Admin", 4, C.Red),
		MODERATOR("Moderator", 3, C.Green),
		HELPER("Helper", 2, C.LightPurple),
		MEMBER("Member", 1, ChatColor.WHITE.toString());
		
		private String name;
		private int id;
		private String colour;
		
		Rank(String name, int id, String colour) {
			this.name = name;
			this.id = id;
			this.colour = colour;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getID() {
			return this.id;
		}
		
		public String getColour() {
			return this.colour;
		}
		
		public static Rank getRank(String name) {
			for(Rank rank : values()) {
				if(rank.getName().equalsIgnoreCase(name)) return rank;
			}
			return null;
		}
		
		
		
	}
	
	

}
