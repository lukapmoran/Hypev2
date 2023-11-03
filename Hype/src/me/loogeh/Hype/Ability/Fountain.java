package me.loogeh.Hype.Ability;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilItem;

public class Fountain extends Ability {

	private HashSet<String> activePlayers = new HashSet<String>();
	
	public Fountain() {
		super("Fountain", AbilityInfo.FOUNTAIN);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(getInfo().getType().holdingRequiredItem(player)) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			if(Cooldown.isCooling(player.getName(), getName())) {
				Cooldown.sendRemaining(player, getName());
				return;
			}
			Cooldown.add(player.getName(), getName(), getInfo().getCooldown());
			member.getClasses().addActiveAbility(getInfo());
			activePlayers.add(player.getUniqueId().toString());
			M.abilityUseMessage(player, getName());
		}
	}
	
	@EventHandler
	public void onServerTickEvent(TickEvent event) {
		if(!event.getType().equals(TickType.FIFTH)) return;
		for(String active : activePlayers) {
			Player player = Bukkit.getPlayer(UUID.fromString(active));
			if(player != null) {
				Member member = Member.get(player);
				if(!member.getClasses().hasAbilityElapsed(getInfo())) {
					ItemStack item = new ItemStack(Material.WEB, 1);
					for(int i = 0; i < 4; i++) {
						utilItem.launchHeadItem(player.getEyeLocation(), item, "fountain");
					}
				} else activePlayers.remove(player.getUniqueId().toString());
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		activePlayers.remove(event.getPlayer().getUniqueId().toString());
	}

}
