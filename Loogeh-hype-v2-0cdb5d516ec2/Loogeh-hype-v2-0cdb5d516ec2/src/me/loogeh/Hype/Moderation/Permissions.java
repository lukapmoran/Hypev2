package me.loogeh.Hype.Moderation;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.loogeh.Hype.Main.Main;

public class Permissions {

	public static String getLevel(String player) {
		String level = "";
		ResultSet rs = Main.mysql.doQuery("SELECT rank FROM permissions WHERE player ='" + player + "'");
		try {
			if(rs.next()) {
				level = rs.getString(1);
				rs.close();
			} else {
				level = "default";
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return level;
	}

	public static Ranks promote(String player) {
		if(getLevel(player).equalsIgnoreCase("default")) {
			Main.mysql.doUpdate("INSERT INTO permissions VALUES ('" + player + "', 'mod')");
			return Ranks.MODERATOR;
		} else if(getLevel(player).equalsIgnoreCase("mod")) {
			Main.mysql.doUpdate("UPDATE permissions SET rank='admin' WHERE player='" + player + "'");
			return Ranks.ADMIN;
		} else if(getLevel(player).equalsIgnoreCase("admin")) {
			Main.mysql.doUpdate("UPDATE permissions SET rank='owner' WHERE player='" + player + "'");
			return Ranks.OWNER;
		} else if(getLevel(player).equalsIgnoreCase("owner")) {
			return null;
		}
		return Ranks.DEFAULT;
	}
	
	public static Ranks demote(String player) {
		if(getLevel(player).equalsIgnoreCase("default")) {
			return null;
		} else if(getLevel(player).equalsIgnoreCase("mod")) {
			Main.mysql.doUpdate("DELETE FROM permissions WHERE player='" + player + "'");
			return Ranks.DEFAULT;
		} else if(getLevel(player).equalsIgnoreCase("admin")) {
			Main.mysql.doUpdate("UPDATE permissions SET rank='mod' WHERE player='" + player + "'");
			return Ranks.OWNER;
		} else if(getLevel(player).equalsIgnoreCase("owner")) {
			return null;
		}
		return Ranks.DEFAULT;
	}
	
	public static void setRank(String player, Ranks rank) {
		ResultSet rs = Main.mysql.doQuery("SELECT player FROM permissions WHERE player='" + player + "'");
		try {
			if(rs.next()) {
				if(rank == Ranks.DEFAULT) Main.mysql.doUpdate("DELETE FROM permissions WHERE player='" + player + "'");
				else Main.mysql.doUpdate("UPDATE permissions SET `rank`='" + rank.getName().toLowerCase() + "' WHERE player='" + player + "'");
			} else {
				Main.mysql.doUpdate("INSERT INTO permissions (`player`,`rank`) VALUES ('" + player + "','" + rank.getName().toLowerCase() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getID(String player) {
		return getRank(player).toInt();
	}


	public static boolean isStaff(String player) {
		if(getLevel(player).equalsIgnoreCase("owner")) return true;
		if(getLevel(player).equalsIgnoreCase("admin")) return true;
		if(getLevel(player).equalsIgnoreCase("mod")) return true;
		else return false;
	}

	public static boolean isAdmin(String player) {
		if(getLevel(player).equalsIgnoreCase("admin") || getLevel(player).equalsIgnoreCase("owner")) return true;
		else return false;
	}

	public enum Ranks {
		OWNER(3, "Owner"),
		ADMIN(2, "Admin"),
		MODERATOR(1, "Mod"),
		DEFAULT(0, "Default");

		private int i;
		private String name;

		private Ranks(int value, String name) {
			this.i = value;
			this.name = name;
		}

		public int toInt() {
			return this.i;
		}
		
		public String getName() {
			return this.name;
		}
		
		public static Ranks getRank(String name) {
			if(name.equalsIgnoreCase("owner")) return Ranks.OWNER;
			else if(name.equalsIgnoreCase("admin")) return Ranks.ADMIN;
			else if(name.equalsIgnoreCase("mod") || name.equalsIgnoreCase("moderator")) return Ranks.MODERATOR;
			else if(name.equalsIgnoreCase("default") || name.equalsIgnoreCase("none") || name.equalsIgnoreCase("member")) return Ranks.DEFAULT;
			else return null;
		}
	}

	public static Ranks getRank(String player) {
		if(getLevel(player).equalsIgnoreCase("owner")) return Ranks.OWNER;
		if(getLevel(player).equalsIgnoreCase("admin")) return Ranks.ADMIN;
		if(getLevel(player).equalsIgnoreCase("mod")) return Ranks.MODERATOR;
		else return Ranks.DEFAULT;
	}

	public static boolean outranks(String player, String target) {
		return (getID(player) > getID(target));
	}
	
}
