package me.loogeh.Hype.Ability;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilPlayer;

public class LightningSceptre extends Ability {

	public LightningSceptre() {
		super("Lightning Sceptre", AbilityInfo.LIGHTNING_SCEPTRE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(player.getItemInHand() == null) return;
		if(!player.getItemInHand().getType().equals(Material.DIAMOND_HOE)) return;
		if(member.getClasses().isUseable(getInfo())) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			Location target = utilPlayer.getTargetBlock(player, 40).getLocation();
			if(target != null) target.getWorld().strikeLightning(target);
			M.abilityUseMessage(player, getName());
		}
		
	}

}
