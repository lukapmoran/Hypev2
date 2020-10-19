package me.loogeh.Hype.Ability;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.Squad.Relation;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Utility.utilPlayer;

public class Smash extends Ability {
	
	public Smash() {
		super("Smash", AbilityInfo.SMASH);
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(getInfo().getType().holdingRequiredItem(player)) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			member.getClasses().addActiveAbility(getInfo());
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			player.setVelocity(new Vector(0, 2, 0));
			Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				public void run() {
					player.setVelocity(new Vector(0, -7, 0));
				}
			}, 10L);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			Member member = Member.get(player);
			Squad squad = SquadManager.getSquad(player);
			if(member.getClasses().getElapsed(getInfo()) != -1L) {
				List<Player> players = utilPlayer.getNearbyPlayers(player, 5);
				for(Player near : players) {
					Squad nsquad = SquadManager.getSquad(near);
					if(squad == null || nsquad == null) {
						near.setVelocity(new Vector(0, 2, 0));
						M.message(near, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " used " + ChatColor.YELLOW + getName());
					} else {
						if(squad.getRelation(nsquad).equals(Relation.ENEMY) || squad.getRelation(nsquad).equals(Relation.NEUTRAL)) {
							near.setVelocity(new Vector(0, 2, 0));
							M.message(near, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " used " + ChatColor.YELLOW + getName());
						}
					}
				}
			}
			member.getClasses().removeActiveAbility(getInfo());
		}
	}

}
