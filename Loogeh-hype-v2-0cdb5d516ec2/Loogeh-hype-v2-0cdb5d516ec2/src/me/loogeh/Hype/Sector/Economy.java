package me.loogeh.Hype.Sector;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Member.mPerms.Rank;
import me.loogeh.Hype.Utility.util;
import me.loogeh.Hype.Utility.utilServer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Economy extends Sector implements CommandExecutor {
	
	private static Economy instance;

	public Economy() {
		super("Economy");
		Main.get().getCommand("economy").setExecutor(this);
		Main.get().getCommand("money").setExecutor(this);
		Main.get().getCommand("balance").setExecutor(this);
		instance = this;
	}
	
	public static Economy get() {
		if(instance == null) instance = new Economy();
		return instance;
	}

	public void load() {
		
	}
	
	public void help(Player player) {
		M.sendHelpMessage(player, "/economy (player)", "View balance");
		M.sendHelpMessage(player, "/economy pay <player> <amount>", "Transfer money");
		M.sendHelpMessage(player, "/economy grant <player> <amount>", "Give money");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Economy - " + ChatColor.WHITE + "Console cannot use economy commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			message(player, "Failed to find your player data");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("economy") || commandLabel.equalsIgnoreCase("money") || commandLabel.equalsIgnoreCase("balance")) {
			if(args.length == 0) {
				message(player, "$" + member.getEconomy().getBalance());
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("pay")) {
					M.sendHelpMessage(player, "/" + commandLabel + " pay <player> <amount>", "Transfer money");
					return true;
				}
				if(args[0].equalsIgnoreCase("grant") || args[0].equalsIgnoreCase("add")) {
					M.sendHelpMessage(player, "/" + commandLabel + " grant <player> <amount>", "Give money");
					return true;
				}
				Player target = utilServer.findPlayer(args[1]);
				if(target == null) {
					message(player, "Found 0 matches for " + ChatColor.YELLOW + args[1]);
					return true;
				}
				Member mtarget = Member.get(target);
				if(mtarget == null) {
					message(player, "Failed to find your target's player data");
					return true;
				}
				message(player, "$" + mtarget.getEconomy().getBalance());
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("pay")) {
					M.sendHelpMessage(player, "/" + commandLabel + " pay <player> <amount>", "Give a player some of your money");
					return true;
				}
				if(args[0].equalsIgnoreCase("grant") || args[0].equalsIgnoreCase("add")) {
					M.sendHelpMessage(player, "/" + commandLabel + " grant <player> <amount>", "Give money");
					return true;
				}
				help(player);
				return true;
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("pay")) {
					Player target = utilServer.findPlayer(args[1]);
					if(target == null) {
						message(player, "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					Member mtarget = Member.get(target);
					if(mtarget == null) {
						message(player, "Failed to find your target's player data");
						return true;
					}
					Integer amount = util.getInteger(args[2]);
					if(amount == null) {
						message(player, "You must enter a valid " + ChatColor.YELLOW + "amount");
						return true;
					}
					if(amount > member.getEconomy().getBalance()) {
						message(player, "Insufficient balance");
						return true;
					}
					member.getEconomy().alterBalance(-amount);
					mtarget.getEconomy().alterBalance(amount);
					message(player, "You payed " + ChatColor.YELLOW + target.getName() + " $" + amount);
					message(target, ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " payed you " + ChatColor.YELLOW + "$" + amount);
					return true;
				}
				if(args[0].equalsIgnoreCase("grant") || args[0].equalsIgnoreCase("add")) {
					if(!member.getPermissions().is(Rank.ADMIN)) {
						M.sendLackPermsMessage(player);
						return true;
					}
					Player target = utilServer.findPlayer(args[1]);
					if(target == null) {
						message(player, "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					Member mtarget = Member.get(target);
					if(mtarget == null) {
						message(player, "Failed to find your target's player data");
						return true;
					}
					Integer amount = util.getInteger(args[2]);
					if(amount == null) {
						message(player, "You must enter a valid " + ChatColor.YELLOW + "amount");
						return true;
					}
					mtarget.getEconomy().alterBalance(amount);
					message(player, "You granted " + ChatColor.YELLOW + target.getName() + " $" + amount);
					message(target, ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " granted you " + ChatColor.YELLOW + "$" + amount);
					return true;
				}
				help(player);
				return true;
			}
		}
		return false;
	}

}
