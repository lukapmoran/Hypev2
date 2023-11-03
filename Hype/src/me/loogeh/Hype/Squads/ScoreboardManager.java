package me.loogeh.Hype.Squads;

import me.loogeh.Hype.Member.mPerms.Rank;
import me.loogeh.Hype.Sector.Member;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {
	
	private Member member;
	private Scoreboard scoreboard;
	
	public ScoreboardManager(Member member) {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	public Member getMember() {	
		return this.member;
	}
	
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	
	@SuppressWarnings("deprecation")
	public void update() {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Squad squad = SquadManager.getSquad(member.getName());
		for(Player player : Bukkit.getOnlinePlayers()) {
			Squad tsquad = SquadManager.getSquad(player);
//			Member member = Member.get(player);
			Rank rank = Rank.MEMBER;
//			if(member != null) rank = member.getPermissions().getRank();
			if(!rank.equals(Rank.MEMBER)) {
				Team team = scoreboard.registerNewTeam(player.getName());
				if(squad == null || tsquad == null) team.setPrefix(rank.getColour() + ChatColor.BOLD.toString() + rank.getName().toUpperCase() + ChatColor.WHITE);
				else team.setPrefix(rank.getColour() + ChatColor.BOLD.toString() + rank.getName().toUpperCase() + squad.getRelationColour(tsquad.getName()));
				team.addPlayer(Bukkit.getOfflinePlayer(player.getName()));
			}
		}
		this.scoreboard = scoreboard;
	}

}
