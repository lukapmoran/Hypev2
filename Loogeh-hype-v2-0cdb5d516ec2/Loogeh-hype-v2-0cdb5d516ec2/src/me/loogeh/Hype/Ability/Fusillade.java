package me.loogeh.Hype.Ability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.BowUnchargeEvent;
import me.loogeh.Hype.Event.TickEvent;
import me.loogeh.Hype.Event.BowUnchargeEvent.UnchargeReason;
import me.loogeh.Hype.Event.TickEvent.TickType;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;

public class Fusillade extends Ability {

	public Fusillade() {
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
		for(Player player : Bukkit.getOnlinePlayers()) {
			Member member = Member.get(player);
			if(event.getType().equals(TickType.FIFTH)) {
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
						member.getClasses().setBowCharge(member.getClasses().getBowCharge());
						if(member.getClasses().getBowCharge() == 5 || member.getClasses().getBowCharge() == 10 || member.getClasses().getBowCharge() == 15 || member.getClasses().getBowCharge() == 20) M.message(player, "Ability", ChatColor.WHITE + "Fusillade " + ChatColor.YELLOW + member.getClasses().getBowCharge() + " Arrow(s)");
						if(member.getClasses().getBowCharge() == 20) member.getClasses().setBowCharging(false);
					}
				}
			}

			if(event.getType().equals(TickType.TENTH)) {
				if(member.getClasses().isUseable(getInfo())) {
					if(member.getClasses().isBowChargeReady()) {
						if(member.getClasses().getBowCharge() > 0) {
							player.launchProjectile(Arrow.class);
							member.getClasses().setBowCharge(member.getClasses().getBowCharge() - 1);
						} else member.getClasses().setBowChargeReady(false);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBowUncharge(BowUnchargeEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(!event.getReason().equals(UnchargeReason.SHOOT)) {
			member.getClasses().setBowCharging(false);
			member.getClasses().setBowCharge(0);
			member.getClasses().setBowChargeReady(false);
			member.getClasses().setLastBowCharge(0L);
		}
	}
}
