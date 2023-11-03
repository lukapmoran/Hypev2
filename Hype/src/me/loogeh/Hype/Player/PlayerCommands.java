package me.loogeh.Hype.Player;

import java.util.List;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Utility.utilMath;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "You cannot use player commands from the console");
			return true;
		}
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("p") || commandLabel.equalsIgnoreCase("player")) {
			if(args.length == 0) {
				//HypePlayer.sendHelpMenu(player)?
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("fly")) {
					if(!Permissions.isAdmin(player.getName())) {
						M.sendLackPermsMessage(player);
						return true;
					}
					player.sendMessage(M.getUsage("/" + commandLabel + " fly <speed>"));
					return true;
				}
				if(args[0].equalsIgnoreCase("heal")) {
					if(!Permissions.isAdmin(player.getName())) {
						M.sendLackPermsMessage(player);
						return true;
					}
					player.setHealth(player.getMaxHealth());
					player.setFoodLevel(20);
					player.setRemainingAir(300);
					M.message(player, "Player", ChatColor.WHITE + "You healed yourself");
					return true;
				}
				if(args[0].equalsIgnoreCase("circle")) {
					List<Location> circle = utilMath.getSphere(player.getLocation(), 5, 1, true, false, 0);
					System.out.println(circle.size() + " size");
					for(Location loc : circle) {
						if(utilMath.getRandom(0, 100) < 51) {
							FallingBlock f_block = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getType(), loc.getBlock().getData());
							f_block.setVelocity(new Vector(0.1, 0.7, 0.2));
							loc.getWorld().playSound(loc, Sound.ZOMBIE_WOODBREAK, 1.0F, 1.0F);
						}
					}
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("fly")) {
					if(!Permissions.isAdmin(player.getName())) {
						M.sendLackPermsMessage(player);
						return true;
					}
					float speed;
					try {
						speed = Float.parseFloat(args[1]);
					} catch(NumberFormatException e) {
						M.message(player, "Player", ChatColor.WHITE + "You must input a valid speed");
						return true;
					}
					if(speed > 0.55) {
						M.message(player, "Player", ChatColor.WHITE + "You may not exceed the maximum fly speed of " + ChatColor.YELLOW + "0.55");
						return true;
					}
					player.setFlySpeed(speed);
					M.message(player, "Player", ChatColor.WHITE + "You set your fly speed to " + ChatColor.YELLOW + speed);
					return true;
				}
				if(args[0].equalsIgnoreCase("potion") || args[0].equalsIgnoreCase("pe")) {
					if(args[1].equalsIgnoreCase("clear")) {
						player.getActivePotionEffects().clear();
						M.message(player, "Potion", ChatColor.WHITE + "You clear all " + ChatColor.YELLOW + "Potion Effects");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("arrow")) {
					if(!Permissions.isAdmin(player.getName())) {
						M.sendLackPermsMessage(player);
						return true;
					}
					float speed = 1.0F;
					try {
						speed = Float.parseFloat(args[1]);
					} catch (NumberFormatException e) {
						M.message(player, "Player", ChatColor.WHITE + "You must input a valid speed");
						return true;
					}
					player.getWorld().spawnArrow(player.getEyeLocation().add(player.getEyeLocation().getDirection()), player.getEyeLocation().getDirection(), speed, 1.0F);
					M.message(player, "Player", ChatColor.WHITE + "You shot an arrow with a speed of " + ChatColor.YELLOW + speed);
					return true;
				}
				if(args[0].equalsIgnoreCase("heal")) {
					if(!Permissions.isAdmin(player.getName())) {
						M.sendLackPermsMessage(player);
						return true;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) {
						M.message(player, "Player", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					target.setHealth(target.getMaxHealth());
					target.setFoodLevel(20);
					target.setRemainingAir(300);
					M.message(target, "Player", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " healed you");
					M.message(player, "Player", ChatColor.WHITE + "You healed " + ChatColor.YELLOW + target.getName());
					return true;
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("potion") || args[0].equalsIgnoreCase("pe")) {
					if(args[1].equalsIgnoreCase("remove")) {
						PotionEffectType type = PotionEffectType.getByName(args[2]);
						if(type == null) {
							M.message(player, "Potion", ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Potion Type");
							return true;
						}
						if(!player.hasPotionEffect(type)) {
							M.message(player, "Potion", ChatColor.WHITE + "You do not have " + ChatColor.YELLOW + WordUtils.capitalize(type.getName().toLowerCase().replaceAll("_", " ")));
							return true;
						}
						player.removePotionEffect(type);
						M.message(player, "Potion", ChatColor.WHITE + "You removed " + ChatColor.YELLOW + WordUtils.capitalize(type.getName().toLowerCase().replaceAll("_", " ")));
						return true;
					}
				}
			}
			if(args.length == 4) {
				if(args[0].equalsIgnoreCase("potion") || args[0].equalsIgnoreCase("pe")) {
					if(args[1].equalsIgnoreCase("add")) {
						PotionEffectType type = PotionEffectType.getByName(args[2]);
						if(type == null) {
							M.message(player, "Potion", ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Potion Type");
							return true;
						}
						int dur;
						try {
							dur = Integer.parseInt(args[3]);
						} catch(NumberFormatException e) {
							M.message(player, "Potion", ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Duration");
							return true;
						}
						player.addPotionEffect(new PotionEffect(type, dur, 0), true);
					}
				}
			}
		}
		return false;
	}

}
