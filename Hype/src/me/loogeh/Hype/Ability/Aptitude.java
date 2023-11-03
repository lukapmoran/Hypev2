package me.loogeh.Hype.Ability;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.potion.PotionEffectType;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.P;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.PlayerMoveBlockEvent;

public class Aptitude extends Ability {
	
	public Aptitude() {
		super("Aptitude", AbilityInfo.APTITUDE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerMoveBlock(PlayerMoveBlockEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SNOW)) {
			if(!member.getClasses().isUseable(getInfo())) {

				AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
				Main.get().getServer().getPluginManager().callEvent(useEvent);
				if(useEvent.isCancelled()) return;
				
				P.forcePotionEffect(player, PotionEffectType.SLOW, 40, 3);
			} else return;
		}
	}

}
