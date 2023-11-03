package me.loogeh.Hype.Miscellaneous;

import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class P {

	
	public static void addPotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
		if(player == null || type == null || duration < 0 || amplifier < 0) return;
		ClassType set = Armour.getKit(player);
		if(set.equals(ClassType.DIAMOND)) {
			Member member = Member.get(player);
			if(member.getClasses().isUseable(AbilityInfo.SOOTHE)) player.addPotionEffect(new PotionEffect(type, duration, ((int) Math.floor(amplifier / 2))));
			else player.addPotionEffect(new PotionEffect(type, duration, amplifier));
		}
		else player.addPotionEffect(new PotionEffect(type, duration, amplifier));
	}
	
	public static void forcePotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
		if(player == null || type == null || duration < 0 || amplifier < 0) return;
		ClassType set = Armour.getKit(player);
		if(set.equals(ClassType.DIAMOND)) {
			Member member = Member.get(player);
			if(member.getClasses().isUseable(AbilityInfo.SOOTHE)) player.addPotionEffect(new PotionEffect(type, duration, ((int) Math.floor(amplifier / 2))), true);
			else player.addPotionEffect(new PotionEffect(type, duration, amplifier), true);
		}
		else player.addPotionEffect(new PotionEffect(type, ((int) duration / 2), amplifier), true);
	}
	
	public static void extendDuration(Player player, PotionEffectType type, int added_duration) {
		if(player == null || type == null || added_duration < 0) return;
		if(!player.hasPotionEffect(type)) {
			player.addPotionEffect(new PotionEffect(type, added_duration, 0));
			return;
		}
		int curDuration = 0;
		for(PotionEffect effect : player.getActivePotionEffects()) {
			if(effect.getType().equals(type)) curDuration = effect.getDuration();
		}
		player.addPotionEffect(new PotionEffect(type, curDuration + added_duration, 0));
	}
	
	public static void extendDuration(Player player, PotionEffectType type, int added_duration, int amplifier) {
		if(player == null || type == null || added_duration < 0 || amplifier < 0) return;
		if(!player.hasPotionEffect(type)) {
			player.addPotionEffect(new PotionEffect(type, added_duration, amplifier));
			return;
		}
		int curDuration = 0;
		for(PotionEffect effect : player.getActivePotionEffects()) {
			if(effect.getType().equals(type)) curDuration = effect.getDuration();
		}
		player.addPotionEffect(new PotionEffect(type, curDuration + added_duration, amplifier));
	}
	
	public static void extendDurationForce(Player player, PotionEffectType type, int added_duration, int amplifier) {
		if(player == null || type == null || added_duration < 0 || amplifier < 0) return;
		if(!player.hasPotionEffect(type)) {
			player.addPotionEffect(new PotionEffect(type, added_duration, amplifier), true);
			return;
		}
		int curDuration = 0;
		for(PotionEffect effect : player.getActivePotionEffects()) {
			if(effect.getType().equals(type)) curDuration = effect.getDuration();
		}
		player.addPotionEffect(new PotionEffect(type, curDuration + added_duration, amplifier), true);
	}
	
	public static void increaseAmplifier(Player player, PotionEffectType type, int duration, int increment) {
		if(player == null || type == null || increment < 1 || duration < 0) return;
		if(!player.hasPotionEffect(type)) {
			player.addPotionEffect(new PotionEffect(type, duration, increment - 1));
			M.message(player, "Ability", ChatColor.WHITE + "Speed increased to " + ChatColor.YELLOW + util.getRomanNumeral(1));
			return;
		}
		int curAmp = 0;
		for(PotionEffect effect : player.getActivePotionEffects()) {
			if(effect.getType().equals(type)) curAmp = effect.getAmplifier();
		}
		player.addPotionEffect(new PotionEffect(type, duration, curAmp + increment), true);
		M.message(player, "Ability", ChatColor.WHITE + "Speed increased to " + ChatColor.YELLOW + util.getRomanNumeral(curAmp + increment + 1));
	}
	
	public static int getAmplifier(Player player, PotionEffectType type) {
		if(player == null || type == null) return 0;
		if(!player.hasPotionEffect(type)) return 0;
		for(PotionEffect effect : player.getActivePotionEffects()) {
			if(effect.getType().equals(type)) return effect.getAmplifier();
		}
		return 0;
	}
	
	public static String getName(PotionEffectType type) {
		if(type.equals(PotionEffectType.ABSORPTION)) return "Absorption";
		else return "Speed";
	}
	
}
