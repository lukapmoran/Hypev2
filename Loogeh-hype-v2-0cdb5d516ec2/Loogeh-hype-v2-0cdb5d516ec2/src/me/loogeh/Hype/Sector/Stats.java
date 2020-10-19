package me.loogeh.Hype.Sector;

import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Stats extends Sector {

	private static Stats instance;
	
	public Stats() {
		super("Stats");
	}
	
	public static Stats get() {
		if(instance == null) instance = new Stats();
		return instance;
	}
	
	public void load() {
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onStatsProjectileHit(EntityShootBowEvent event) {
		if(event.isCancelled()) return;
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Member member = Member.get(player);
			if(member != null) member.getStats().addBowShot();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onStatsPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Member member = Member.get(player);
		if(member != null) member.getStats().addDeath(Armour.getKit(player));
		if(player.getKiller() != null) {
			Player killer = player.getKiller();
			Member mkiller = Member.get(killer);
			if(mkiller != null) mkiller.getStats().addKill(Armour.getKit(killer));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onStatsAbilityUse(AbilityUseEvent event) {
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member != null) member.getStats().addAbility();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onStatsEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(event.isCancelled()) return;
		if(!(event.getDamager() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getDamager();
		if(!(arrow.getShooter() instanceof Player)) return;
		Player player = (Player) arrow.getShooter();
		Member member = Member.get(player);
		if(member != null) member.getStats().addBowHit();
	}

}
