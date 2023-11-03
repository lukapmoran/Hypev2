package me.loogeh.Hype.Ability;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Player.Damage;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Squads.Squad.Relation;
import me.loogeh.Hype.Utility.utilPlayer;

public class Shockwave extends Ability {

	public Shockwave() {
		super("Shockwave", AbilityInfo.SHOCKWAVE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
		Squad squad = SquadManager.getSquad(player);
		
		AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
		Main.get().getServer().getPluginManager().callEvent(useEvent);
		if(useEvent.isCancelled()) return;
		
		if(Cooldown.isCooling(player.getName(), getName())) {
			Cooldown.sendRemaining(player, getName());
			return;
		}
		Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
		List<Player> players = utilPlayer.getNearbyPlayers(player, 7); //make distance be affected by player level?
		for(Player near : players) {
			if(near.getGameMode().equals(GameMode.CREATIVE)) continue; 
			Member mnear = Member.get(near);
			Squad n_squad = SquadManager.getSquad(near);
			if(squad.getRelation(n_squad).equals(Relation.ENEMY) || squad.getRelation(n_squad).equals(Relation.NEUTRAL)) {
				near.setVelocity(near.getLocation().getDirection().multiply(-2.0D).add(new Vector(0, 0.5, 0)));
				mnear.getSession().addDamage(new Damage("Shockwave", player.getName(), 0.0F));
				M.message(player, "Ability", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " used " + ChatColor.YELLOW + getName());
			}
		}
		M.abilityUseMessage(player, getName());
	}
	
}
