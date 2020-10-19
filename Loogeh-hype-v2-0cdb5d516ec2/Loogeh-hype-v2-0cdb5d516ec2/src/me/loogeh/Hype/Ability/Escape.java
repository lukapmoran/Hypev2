package me.loogeh.Hype.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class Escape extends Ability {

	public Escape() {
		super("Escape", AbilityInfo.ESCAPE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		Member member = Member.get(player);
		if(member == null) return;
		if(member.getClasses().isUseable(getInfo())) {
			if(!player.isSneaking()) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				member.getClasses().setLastCrouch(System.currentTimeMillis());
			} else {
				if(System.currentTimeMillis() - member.getClasses().getLastCrouch() > 1500) {

					AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
					Main.get().getServer().getPluginManager().callEvent(useEvent);
					if(useEvent.isCancelled()) return;
					
					double elapsed = System.currentTimeMillis() - member.getClasses().getLastCrouch();
					if(elapsed > 6000) elapsed = 6000;
					double force = elapsed / 3600.0D;
					if(elapsed < 3500) force = elapsed / 2800.0D;
					player.setVelocity(new Vector(0, force, 0));
					member.getClasses().setLastCrouch(0L);
					M.abilityUseMessage(player, getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member == null) return;
		member.getClasses().setLastCrouch(0L);
	}

}
