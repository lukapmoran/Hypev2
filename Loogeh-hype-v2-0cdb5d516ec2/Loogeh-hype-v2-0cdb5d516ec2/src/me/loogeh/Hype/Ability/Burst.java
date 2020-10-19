package me.loogeh.Hype.Ability;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.PlayerBlockEvent;
import me.loogeh.Hype.Event.PlayerUnblockEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilPlayer;

public class Burst extends Ability {

	public Burst() { //unfinished > Make new ThrowItem for applying effects to players
		super("Burst", AbilityInfo.BURST);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerBlock(PlayerBlockEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		if(getInfo().getType().holdingRequiredItem(player)) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(!member.getClasses().isPlayerBlocking()) {
				if(player.isBlocking()) {
					if(member.getClasses().hasAbilityElapsed(getInfo())) {
						if(Cooldown.isCooling(player.getName(), getName()) && member.getClasses().getActiveAbilities().containsKey(getInfo())) {
							Cooldown.sendRemaining(player, getName());
							return;
						}
						Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
						member.getClasses().addActiveAbility(getInfo());
						member.getClasses().setPlayerBlocking(true);
					}
				}
				if(member.getClasses().getSwordBlockCount() < 20) {
					for(int i = 0; i < 4; i++) {
						Item fire = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.FIRE));
						fire.setVelocity(utilPlayer.getBlizzardVector(fire.getVelocity(), false));
					}
					member.getClasses().setSwordBlockCount(member.getClasses().getSwordBlockCount() + 1);
				}
			} else {
				if(Cooldown.isCooling(player.getName(), getName()) && member.getClasses().getActiveAbilities().containsKey(getInfo())) {
					Cooldown.sendRemaining(player, getName());
					return;
				}
				if(member.getClasses().getSwordBlockCount() < 20) {
					for(int i = 0; i < 4; i++) {
						Item fire = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.FIRE));
						fire.setVelocity(utilPlayer.getBlizzardVector(fire.getVelocity(), false));
					}
					member.getClasses().setSwordBlockCount(member.getClasses().getSwordBlockCount() + 1);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerUnblock(PlayerUnblockEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		member.getClasses().setPlayerBlocking(false);
		member.getClasses().setSwordBlockCount(0);
		member.getClasses().getActiveAbilities().remove(getInfo());
	}

}
