package me.loogeh.Hype.Sector;

import java.util.ArrayList;
import java.util.List;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Member.mPerms.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permissions extends Sector implements CommandExecutor {

	private static Permissions instance;
	
	public Permissions() {
		super("Permissions");
		List<String> commandList = new ArrayList<String>();
		commandList.add("permissions");
		for(String command : commandList) {
			getPlugin().getCommand(command).setExecutor(this);
		}
		instance = this;
	}
	
	public static Permissions get() {
		if(instance == null) instance = new Permissions();
		return instance;
	}

	public void help(Player player) {
		message(player, ChatColor.WHITE + "Commands " + ChatColor.GRAY + "(optional) <required> [command options]");
		M.sendHelpMessage(player, "/permissions [demote/promote] <player>", "Change a player's rank");
		M.sendHelpMessage(player, "/permissions set <player> <rank>", "Set a player's rank");
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Permissions - " + ChatColor.WHITE + "Console cannot use permissions commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("permissions")) {
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(args.length == 1) {
				help(player);
				return true;
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("promote")) {
					if(member.getPermissions().getRank().getID() < 4) {
						M.sendLackPermsMessage(player);
						return true;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) {
						message(player, ChatColor.WHITE + "Found 0 matches for " + args[1]);
						return true;
					}
					Member mtarget = Member.get(target);
					if(mtarget.getPermissions().getRank().getID() >= member.getPermissions().getRank().getID()) {
						message(player, "You must outrank " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " to promote them");
						return true;
					}
					boolean success = mtarget.getPermissions().promote();
					if(success) message(player, ChatColor.WHITE + "You promoted " + ChatColor.YELLOW + target.getName());
					else message(player, ChatColor.WHITE + "You cannot promote " + ChatColor.YELLOW + target.getName());
					return true;
				}
				if(args[0].equalsIgnoreCase("demote")) {
					if(member.getPermissions().getRank().getID() < 4) {
						M.sendLackPermsMessage(player);
						return true;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) {
						message(player, ChatColor.WHITE + "Found 0 matches for " + args[1]);
						return true;
					}
					Member mtarget = Member.get(target);
					if(mtarget.getPermissions().getRank().getID() >= member.getPermissions().getRank().getID()) {
						message(player, "You must outrank " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " to demote them");
						return true;
					}
					boolean success = mtarget.getPermissions().demote();
					if(success) message(player, ChatColor.WHITE + "You demoted " + ChatColor.YELLOW + target.getName());
					else message(player, ChatColor.WHITE + "You cannot demote " + ChatColor.YELLOW + target.getName());
					return true;
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("set")) {
					if(member.getPermissions().getRank().getID() < 4) {
						M.sendLackPermsMessage(player);
						return true;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) {
						message(player, ChatColor.WHITE + "Found 0 matches for " + args[1]);
						return true;
					}
					Member mtarget = Member.get(target);
					Rank rank = Rank.getRank(args[2]);
					if(rank == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Rank");
						return true;
					}
					if(member.getPermissions().getRank().getID() < rank.getID()) {
						message(player, "You may only chose a rank lower than or equal to yours");
						return true;
					}
					boolean success = mtarget.getPermissions().demote();
					if(success) message(player, ChatColor.WHITE + "You set " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " as " + ChatColor.YELLOW + rank.getName());
					else message(player, ChatColor.WHITE + "You cannot set the rank of " + ChatColor.YELLOW + target.getName());
					return true;
				}
			}
		}
		return false;
	}

	public void load() {
		
	}
}
