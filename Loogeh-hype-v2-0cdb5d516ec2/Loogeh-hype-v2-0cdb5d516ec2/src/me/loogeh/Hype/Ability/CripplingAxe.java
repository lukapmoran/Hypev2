package me.loogeh.Hype.Ability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.P;
import me.loogeh.Hype.Player.Damage;
import me.loogeh.Hype.Sector.Member;

public class CripplingAxe extends Ability {

	public CripplingAxe() {
		super("Crippling Axe", AbilityInfo.CRIPPLING_AXE);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		if(damager.getItemInHand() == null) return;
		if(!damager.getItemInHand().getType().equals(Material.DIAMOND_AXE) && !damager.getItemInHand().getType().equals(Material.IRON_AXE) && !damager.getItemInHand().getType().equals(Material.GOLD_AXE)) return;
		Member member = Member.get(damager);
		if(member.getClasses().isUseable(getInfo())) {

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			P.forcePotionEffect(player, PotionEffectType.SLOW, 60, 2);
			member.getSession().addDamage(new Damage("Cripplign Axe", damager.getName(), event.getDamage()));
		}
	}

}
