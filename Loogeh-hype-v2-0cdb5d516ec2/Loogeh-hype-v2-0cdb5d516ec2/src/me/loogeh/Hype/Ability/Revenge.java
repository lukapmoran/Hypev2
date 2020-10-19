package me.loogeh.Hype.Ability;

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
import me.loogeh.Hype.Squads.SquadManager;

public class Revenge extends Ability {

	public Revenge() {
		super("Revenge", AbilityInfo.REVENGE);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			if(event.isCancelled()) return;
			Player player = (Player) event.getEntity();
			Member member = Member.get(player);
			if(!member.getClasses().isUseable(getInfo())) return;
			Player damager = (Player) event.getDamager();
			Squad squad = SquadManager.getSquad(player);
			Member mdamager = Member.get(damager);
			for(String members : squad.getMembers()) {
				if(mdamager.getSession().getRecentlyDamaged().containsKey(members)) {

					AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
					Main.get().getServer().getPluginManager().callEvent(useEvent);
					if(useEvent.isCancelled()) return;

					event.setDamage(event.getDamage() * 1.15D);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		final Player killer = player.getKiller();
		Member member = Member.get(killer);
		member.getSession().addRecentlyKilled(player.getName());
	}
}
