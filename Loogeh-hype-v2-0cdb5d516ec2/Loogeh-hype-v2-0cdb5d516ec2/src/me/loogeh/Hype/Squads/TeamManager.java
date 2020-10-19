package me.loogeh.Hype.Squads;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad.Relation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public class TeamManager {

	public static HashMap<String, Scoreboard> boards = new HashMap<String, Scoreboard>();

	public static Scoreboard getNewScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Squad squad = SquadManager.getSquad(player);
		if(squad != null) {
			Team enemies = board.registerNewTeam("enemies");
			Team allies = board.registerNewTeam("allies");
			Team trusts = board.registerNewTeam("trusts");
			Team neutrals = board.registerNewTeam("neutrals");
			Team members = board.registerNewTeam("members");

			enemies.setPrefix(ChatColor.DARK_RED + "");
			setPlayers(enemies, getEnemies(player));

			allies.setPrefix(ChatColor.GREEN + "");
			setPlayers(allies, getAllies(player));

			trusts.setPrefix(ChatColor.DARK_GREEN + "");
			setPlayers(trusts, getTrusts(player));

			neutrals.setPrefix(ChatColor.YELLOW + "");
			setPlayers(neutrals, getNeutrals(player));

			members.setPrefix(ChatColor.AQUA + "");
			setPlayers(members, getMembers(player));
		} else {
			Team neutrals = board.registerNewTeam("neutrals");
			neutrals.setPrefix(ChatColor.YELLOW + "");
		}
		return board;
	}

	public static List<String> getEnemies(Player player) {
		ArrayList<String> enemies = new ArrayList<String>();
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return enemies;
		else enemies = (ArrayList<String>) squad.getEnemies();
		return enemies;
	}

	public static List<String> getAllies(Player player) {
		ArrayList<String> allies = new ArrayList<String>();
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return allies;
		else allies = (ArrayList<String>) squad.getAllies();
		return allies;
	}

	public static List<String> getTrusts(Player player) {
		ArrayList<String> trusts = new ArrayList<String>();
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return trusts;
		else trusts = (ArrayList<String>) squad.getTrusts();
		return trusts;
	}

	public static List<String> getNeutrals(Player player) {
		ArrayList<String> neutrals = new ArrayList<String>();
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return neutrals;
		else neutrals = (ArrayList<String>) squad.getNeutrals();
		return neutrals;
	}

	public static List<String> getMembers(Player player) {
		ArrayList<String> members = new ArrayList<String>();
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return members;
		else members = (ArrayList<String>) squad.getMembers();
		return members;
	}

	public static void reloadStaff(Player player, Scoreboard scoreboard) {
		if(player == null) return;
		Squad squad = SquadManager.getSquad(player);
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM permissions WHERE rank='owner' OR rank='admin' OR rank='mod'");
		try {
			while(rs.next()) {
				Squad staff_squad = SquadManager.getSquad(rs.getString(1));
				if(squad == null || staff_squad == null) scoreboard.getTeam(rs.getString(2) + "s").addPlayer(Bukkit.getOfflinePlayer(rs.getString(1)));
				else {
			 		Relation rel = squad.getRelation(staff_squad);
					if(rel == Relation.ENEMY) scoreboard.getTeam("enemy_" + rs.getString(2) + "s").addPlayer(Bukkit.getOfflinePlayer(rs.getString(1)));
					else if(rel == Relation.ALLY) scoreboard.getTeam("ally_" + rs.getString(2) + "s").addPlayer(Bukkit.getOfflinePlayer(rs.getString(1)));
					else if(rel == Relation.TRUST) scoreboard.getTeam("trust_" + rs.getString(2) + "s").addPlayer(Bukkit.getOfflinePlayer(rs.getString(1)));
					else if(rel == Relation.SELF) scoreboard.getTeam("self_" + rs.getString(2) + "s").addPlayer(Bukkit.getOfflinePlayer(rs.getString(1)));
					else scoreboard.getTeam(rs.getString(2) + "s").addPlayer(Bukkit.getOfflinePlayer(rs.getString(1)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "TeamManager > Failed to load 'staff' for " + player.getName());
		}
	}

	public static void join(Player player) {
		if(player == null) return;
		Squad squad = SquadManager.getSquad(player);
		Member member = Member.get(player);
		if(member == null) return;
		for(Player players : Bukkit.getOnlinePlayers()) {
			Squad p_squad = SquadManager.getSquad(players);
			Scoreboard p_board = players.getScoreboard();
			if(p_board != null) {
				if(p_squad == null || squad == null) {
					p_board.getTeam("neutrals").addPlayer(Bukkit.getOfflinePlayer(player.getName()));
				}
			} else {
				Scoreboard newBoard = TeamManager.getNewScoreboard(players);
				players.setScoreboard(newBoard);
				TeamManager.boards.put(players.getName(), newBoard);
			}
		}
	}
	
	public static void leave(Player player) {
		
	}

	public static void registerNewTeams(Player player) {
		if(player == null) return;
		if(!boards.containsKey(player.getName())) {
			return;
		}
		Scoreboard scoreboard = boards.get(player.getName());
		if(scoreboard.getTeam("enemy_owners") == null) scoreboard.registerNewTeam("enemy_owners");
		if(scoreboard.getTeam("enemy_admins") == null) scoreboard.registerNewTeam("enemy_admins");
		if(scoreboard.getTeam("enemy_mods") == null) scoreboard.registerNewTeam("enemy_mods");
		if(scoreboard.getTeam("ally_mods") == null) scoreboard.registerNewTeam("ally_mods");
		if(scoreboard.getTeam("ally_owners") == null) scoreboard.registerNewTeam("ally_owners");
		if(scoreboard.getTeam("ally_admins") == null) scoreboard.registerNewTeam("ally_admins");
		if(scoreboard.getTeam("trust_mods") == null) scoreboard.registerNewTeam("trust_mods");
		if(scoreboard.getTeam("trust_owners") == null) scoreboard.registerNewTeam("trust_owners");
		if(scoreboard.getTeam("trust_admins") == null) scoreboard.registerNewTeam("trust_admins");
		if(scoreboard.getTeam("self_mods") == null) scoreboard.registerNewTeam("self_mods");
		if(scoreboard.getTeam("self_owners") == null) scoreboard.registerNewTeam("self_owners");
		if(scoreboard.getTeam("self_admins") == null) scoreboard.registerNewTeam("self_admins");
	}

	public  static void setPlayers(Team team, List<String>players) {
		for(String cur : players) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(cur);
			if(!team.hasPlayer(p)) team.addPlayer(p);
		}
	}

	public static boolean hasTeam(Player player, String team) {
		if(player == null) return false;
		if(!boards.containsKey(player.getName())) {
			Scoreboard board = getNewScoreboard(player);
			boards.put(player.getName(), board);
			player.setScoreboard(board);
			if(board.getTeam(team) != null) return true;
			else return false;
		}
		if(boards.get(player.getName()).getTeam(team) != null) return true;
		else return false;
	}

	public static Team getTeam(Player player, String team) {
		if(player == null) return null;
		if(!boards.containsKey(player.getName())) {
			Scoreboard board = getNewScoreboard(player);
			boards.put(player.getName(), board);
			player.setScoreboard(board);
			return board.getTeam(team);
		}
		return boards.get(player.getName()).getTeam(team);
	}

	public static boolean hasPlayer(Player player, String team, String target) {
		if(!boards.containsKey(player.getName())) {
			Scoreboard board = getNewScoreboard(player);
			boards.put(player.getName(), board);
		}
		return getTeam(player, team).hasPlayer(Bukkit.getOfflinePlayer(target));
	}

	public static void addPlayer(Player player, String toAdd, Relation team) {
		OfflinePlayer o_toAdd = Bukkit.getOfflinePlayer(toAdd);
		Team new_team = getTeam(player, team.getTeam());
		Team ally_team = getTeam(player, "allies");
		Team enemy_team = getTeam(player, "enemies");
		Team trust_team = getTeam(player, "trusts");
		Team member_team = getTeam(player, "members");
		Team neutral_team = getTeam(player, "neutrals");
		if(new_team == null) return;
		if(ally_team != null) if(ally_team.hasPlayer(o_toAdd)) ally_team.removePlayer(o_toAdd);
		if(enemy_team != null) if(enemy_team.hasPlayer(o_toAdd)) enemy_team.removePlayer(o_toAdd);
		if(trust_team != null) if(trust_team.hasPlayer(o_toAdd)) trust_team.removePlayer(o_toAdd);
		if(member_team != null) if(member_team.hasPlayer(o_toAdd)) member_team.removePlayer(o_toAdd);
		if(neutral_team != null) if(neutral_team.hasPlayer(o_toAdd)) neutral_team.removePlayer(o_toAdd);
		new_team.addPlayer(o_toAdd);
		player.setScoreboard(boards.get(player.getName()));
	}

	public static void checkOnlinePlayers() {
		if(Squad.getChangeNameColour()) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				Scoreboard board = TeamManager.getNewScoreboard(players);
				players.setScoreboard(board);
				TeamManager.boards.put(players.getName(), board);
			}
		}
	}
}
