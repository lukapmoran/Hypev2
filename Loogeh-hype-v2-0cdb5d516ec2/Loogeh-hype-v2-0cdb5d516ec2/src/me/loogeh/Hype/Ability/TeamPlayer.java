package me.loogeh.Hype.Ability;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.Squad.Relation;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Utility.utilPlayer;

public class TeamPlayer extends Ability {

	public TeamPlayer() {
		super("Team Player", AbilityInfo.TEAM_PLAYER);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(event.isCancelled()) return;
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Member member = Member.get(damager);
			if(member.getClasses().isUseable(getInfo())) {
				if(!member.getClasses().hasAbilityElapsed(getInfo())) {
					event.setDamage(event.getDamage() * 1.2D);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		List<Player> nearby = utilPlayer.getNearbyPlayers(player, 15);
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return;
		for(Player near : nearby) {
			Squad nsquad = SquadManager.getSquad(near);
			if(nsquad != null) {
				if(squad.getRelation(nsquad).equals(Relation.SELF)) {
					
					AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
					Main.get().getServer().getPluginManager().callEvent(useEvent);
					if(useEvent.isCancelled()) return;
					
					member.getClasses().addActiveAbility(getInfo());
				}
			}
		}
	}
}
