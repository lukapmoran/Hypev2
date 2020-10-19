package me.loogeh.Hype.Ability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.BowUnchargeEvent;
import me.loogeh.Hype.Event.BowUnchargeEvent.UnchargeReason;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.utilMath;

public class ArrowPower extends Ability {

	public ArrowPower() {
		super("Poison Arrow", AbilityInfo.ARROW_POWER);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member.getClasses().isUseable(getInfo())) {
			if(!getInfo().getSet().equals(Armour.getKit(player))) return;
			if(!getInfo().getType().holdingRequiredItem(player)) return;
			if(!member.getClasses().getArrowDrawn().equals(AbilityInfo.NONE)) return;

			AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
			Main.get().getServer().getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			member.getClasses().setBowCharging(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerTick(TickEvent event) {
		if(event.getType().equals(TickType.FIFTH)) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				Member member = Member.get(player);
				if(member.getClasses().isBowCharging()) {
					if(player.getItemInHand() == null) {
						member.getClasses().setBowCharging(false);
						member.getClasses().setBowCharge(0);
						member.getClasses().setBowChargeReady(false);
						continue;
					}
					if(!player.getItemInHand().getType().equals(Material.BOW)) {
						member.getClasses().setBowCharging(false);
						member.getClasses().setBowCharge(0);
						member.getClasses().setBowChargeReady(false);
						continue;
					}
					if(member.getClasses().getBowCharge() < 20) {
						if(System.currentTimeMillis() - member.getClasses().getLastBowCharge() < 2000) continue;
						member.getClasses().setBowCharge(member.getClasses().getBowCharge() + 1);
						int charge = member.getClasses().getBowCharge();
						if(charge == 5 || charge == 10 || charge == 15 || charge == 20) M.message(player, "Ability", ChatColor.WHITE + "Power Arrow " + ChatColor.YELLOW + utilMath.getPercentage(charge, 20, 1) + "% Damage");
						if(charge == 20) {
							member.getClasses().setBowCharging(false);
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onArrowShoot(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Member member = Member.get(player);
			if(member.getClasses().isUseable(getInfo())) {
				if(!getInfo().getSet().equals(Armour.getKit(player))) return;
				if(member.getClasses().isBowChargeReady() || member.getClasses().isBowCharging()) {
					if(!member.getClasses().getArrowDrawn().equals(AbilityInfo.NONE)) {
						member.getClasses().setBowChargeReady(false);
						member.getClasses().setBowCharge(0);
						member.getClasses().setLastBowCharge(0L);
						return;
					}
					int charge = member.getClasses().getBowCharge();
					if(charge == 20) event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(2.0D));
					else if(charge < 20 && charge > 14) event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.75D));
					else if(charge < 15 && charge > 9) event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.5D));
					else if(charge < 10 && charge > 4) event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.25D));
					event.getProjectile().setMetadata("powerArrow", new FixedMetadataValue(Main.get(), Boolean.valueOf(true)));
					member.getClasses().setBowChargeReady(false);
					member.getClasses().setBowCharge(0);
					member.getClasses().setLastBowCharge(0L);
				}
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
			Player player = (Player) event.getEntity();
			if(!event.isCancelled()) {
				if(event.getDamager().hasMetadata("powerArrow")) M.message(player, "Ability", ChatColor.WHITE + "You were hit by a " + ChatColor.YELLOW + getName());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBowUncharge(BowUnchargeEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!event.getReason().equals(UnchargeReason.SHOOT)) {
			member.getClasses().setBowChargeReady(false);
			member.getClasses().setBowCharge(0);
			member.getClasses().setLastBowCharge(0L);
		}
	}

}
