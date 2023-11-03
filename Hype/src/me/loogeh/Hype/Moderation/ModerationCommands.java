package me.loogeh.Hype.Moderation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//
//import java.util.HashMap;
//
//import me.loogeh.Hype.Formatting.M;
//import me.loogeh.Hype.Main.Main;
//import me.loogeh.Hype.Moderation.Permissions.Ranks;
//import me.loogeh.Hype.Utility.util;
//
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//
public class ModerationCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
//	public static HashMap<String, String> banTrueList = new HashMap<String, String>();
//	public static HashMap<String, Double> banDurList = new HashMap<String, Double>();
//	public static HashMap<String, String> banReasonList = new HashMap<String, String>();
//
//	@Override
//	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
//		if(!(sender instanceof Player)) {
//			sender.sendMessage(ChatColor.BLUE + "Server - " + ChatColor.WHITE + "You cannot use moderation commands from the console");
//			return true;
//		}
//		if(sender instanceof Player) {
//			final Player player = (Player) sender;
//			if(commandLabel.equalsIgnoreCase("mute")) {
//				if(!Permissions.isStaff(player.getName())) {
//					M.sendLackPermsMessage(player);
//					return true;
//				} else {
//				if(args.length < 2) {
//					player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "/mute <player> <duration> <reason>");
//					return true;
//				} else {
//					if(args[1].equalsIgnoreCase("perm") || args[1].equalsIgnoreCase("permanent")) {
//						String reason = util.join(2, args);
//						Player mutee = Bukkit.getServer().getPlayer(args[0]);
//						if(args[2] == null) {
//							player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "/mute <player> <duration> <reason>");
//							return true;
//						}
//						if(Permissions.getRank(player.getName()) == Permissions.Ranks.MODERATOR) {
//							player.sendMessage(ChatColor.GREEN + "Moderators " + ChatColor.GRAY + "cannot mute permanently");
//							return true;
//						}
//						if(mutee == null) {
//							Mute.mute(args[0], player, 9999999999.0D, reason);
//							return true;
//						}
//						Mute.mute(mutee.getName(), player, 9999999999.0D, reason);
//						return true;
//					}
//					double dur = -1.0D;;
//					try {
//						dur = Double.parseDouble(args[1]);
//					} catch(NumberFormatException e) {
//						player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "Please enter a valid duration");
//						return true;
//					}
//					if(dur == -1.0D) {
//						player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "Please enter a valid duration");
//						return true;
//					}					
//					String reason = util.join(2, args);
//					if(reason == null) {
//						player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "You must input a reason");
//						return true;
//					}
//					Player mutee = Bukkit.getServer().getPlayer(args[0]);
//					if(args[2] == null) {
//						player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "/mute <player> <duration> <reason>");
//						return true;
//					}
//					if(Permissions.getRank(player.getName()) == Permissions.Ranks.MODERATOR) {
//						if(dur > Moderation.getMaxModeratorMuteDuration()) {
//							player.sendMessage(ChatColor.GREEN + "Moderators " + ChatColor.GRAY + "can mute for a maximum of " + ChatColor.GREEN + "24 Hours");
//							return true;
//						}
//					}
//					Mute.mute(mutee.getName(), player, dur, reason);
//					return true;
//					}
//				}
//			}
//			if(commandLabel.equalsIgnoreCase("unmute")) {
//				if(!Permissions.isStaff(player.getName())) {
//					M.sendLackPermsMessage(player);
//					return true;
//				}
//				if(args.length != 1) {
//					player.sendMessage(ChatColor.GOLD + "Mute - " + ChatColor.GRAY + "/unmute <player>");
//					return true;
//				}
//				Player mutee = Bukkit.getServer().getPlayer(args[0]);
//				if(mutee == null) {
//					Mute.unmute(args[0], player);
//					return true;
//				}
//				Mute.unmute(mutee.getName(), player);
//				return true;
//				
//			}
//			if(commandLabel.equalsIgnoreCase("ban")) {
//				if(!Permissions.isStaff(player.getName())) {
//					M.sendLackPermsMessage(player);
//					return true;
//				}
//				if(args.length == 0) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "/ban <player> <duration> <reason>");
//					return true;
//				}
//				if(args.length == 1) {
//					if(args[0].equalsIgnoreCase("yes")) {
//						if(!banTrueList.containsKey(player.getName())) {
//							player.sendMessage(ChatColor.GRAY + "There is nobody to ban");
//							return true;
//						}
//						Ban.banOfflinePlayer(banTrueList.get(player.getName()), player, banDurList.get(player.getName()), banReasonList.get(player.getName()));
//						banTrueList.remove(player.getName());
//						banDurList.remove(player.getName());
//						banReasonList.remove(player.getName());
//						return true;
//					}
//					if(args[0].equalsIgnoreCase("no")) {
//						if(!banTrueList.containsKey(player.getName())) {
//							player.sendMessage(ChatColor.GRAY + "There is nobody to ban");
//							return true;
//						}
//						player.sendMessage(ChatColor.GRAY + "You chose not to ban " + ChatColor.DARK_AQUA + banDurList.get(player.getName()));
//						banTrueList.remove(player.getName());
//						banDurList.remove(player.getName());
//						banReasonList.remove(player.getName());
//						return true;
//					}
//				}
//				if(args.length < 2) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "/ban <player> <duration> <reason>");
//					return true;
//				}
//				if(args[2] == null) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "/ban <player> <duration> <reason>");
//					return true;
//				}
//				Player banee = Bukkit.getServer().getPlayer(args[0]);
//				if(args[1].equalsIgnoreCase("permanent") || args[1].equalsIgnoreCase("perm")) {
//					String reason = util.join(2, args);
//					if(Permissions.getRank(player.getName()) == Permissions.Ranks.MODERATOR) {
//							player.sendMessage(ChatColor.GREEN + "Moderators " + ChatColor.GRAY + "cannot ban permanently");
//							return true;
//					}
//					if(reason == null) {
//						player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "You must input a reason");
//						return true;
//					}
//					if(banee == null) {
//						banTrueList.put(player.getName(), args[0]);
//						banDurList.put(player.getName(), 9999999999.0D);
//						banReasonList.put(player.getName(), reason);
//						player.sendMessage(ChatColor.DARK_AQUA + "Player offline - " + ChatColor.GRAY + "Type " + ChatColor.YELLOW + "/ban yes " + ChatColor.GRAY + "if you want to ban " + ChatColor.WHITE + "[" + ChatColor.AQUA + args[0] + ChatColor.WHITE + "]");
//						Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
//							
//							public void run() {
//								if(banTrueList.containsKey(player.getName())) banTrueList.remove(player.getName());
//								if(banDurList.containsKey(player.getName())) banDurList.remove(player.getName());
//								if(banReasonList.containsKey(player.getName())) banReasonList.remove(player.getName());
//							}
//						}, 600L);
//						return true;
//					}
//					Ban.banPlayer(banee.getName(), player, 9999999999.0D, reason);
//					return true;
//				}
//				double dur = -1.0D;
//				try {
//					dur = Double.parseDouble(args[1]);
//				} catch(NumberFormatException e) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "Please enter a valid duration");
//					return true;
//				}
//				if(dur == -1.0D) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "Please enter a valid duration");
//					return true;
//				}
//				String reason = util.join(2, args);
//				if(reason == null) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "You must input a reason");
//					return true;
//				}
//				if(Permissions.getRank(player.getName()) == Permissions.Ranks.MODERATOR) {
//					if(dur > Moderation.getMaxModeratorBanDuration()) {
//						player.sendMessage(ChatColor.GREEN + "Moderators " + ChatColor.GRAY + "can ban for a maximum of " + ChatColor.GREEN + "8 Hours");
//						return true;
//					}
//				}
//				if(banee == null) {
//					banTrueList.put(player.getName(), args[0]);
//					banDurList.put(player.getName(), dur);
//					banReasonList.put(player.getName(), reason);
//					player.sendMessage(ChatColor.DARK_AQUA + "Player offline - " + ChatColor.GRAY + "Type " + ChatColor.YELLOW + "/ban yes " + ChatColor.GRAY + "if you want to ban " + ChatColor.WHITE + "[" + ChatColor.AQUA + args[0] + ChatColor.WHITE + "]");
//					return true;
//				}
//				Ban.banPlayer(banee.getName(), player, dur, reason);
//				return true;
//				
//			}
//			if(commandLabel.equalsIgnoreCase("unban")) {
//				if(!Permissions.isAdmin(player.getName())) {
//					M.sendLackPermsMessage(player);
//					return true;
//				}
//				if(args.length != 1) {
//					player.sendMessage(ChatColor.GOLD + "Ban - " + ChatColor.GRAY + "/unban <player>");
//					return true;
//				}
//				String unbanee = args[0];
//				Player pUnbanee = Bukkit.getServer().getPlayer(unbanee);
//				if(pUnbanee != null) {
//					Ban.unban(pUnbanee.getName(), player);
//					return true;
//				}
//				Ban.unban(unbanee, player);
//			}
//			if(commandLabel.equalsIgnoreCase("permissions") || commandLabel.equalsIgnoreCase("perms")) {
//				if(Permissions.getRank(player.getName()).toInt() < 2 && !player.isOp()) {
//					M.sendLackPermsMessage(player);
//					return true;
//				}
//				if(args.length == 0) {
//					Moderation.sendHelpMenu(player);
//					return true;
//				}
//				if(args.length == 1) {
//					if(args[0].equalsIgnoreCase("promote")) {
//						M.message(player, "Usage", ChatColor.YELLOW + "/permissions promote <player>");
//						return true;
//					}
//					if(args[0].equalsIgnoreCase("demote")) {
//						M.message(player, "Usage", ChatColor.YELLOW + "/permissions demote <player>");
//						return true;
//					}
//					if(args[0].equalsIgnoreCase("set")) {
//						M.message(player, "Usage", ChatColor.YELLOW + "/permissions set <player> <rank>");
//						return true;
//					}
//					if(args[0].equalsIgnoreCase("help")) {
//						Moderation.sendHelpMenu(player);
//					}
//				}
//				if(args.length == 2) {
//					if(args[0].equalsIgnoreCase("promote")) {
//						Player target = Bukkit.getPlayer(args[1]);
//						if(target != null) {
//							if(target.getName().equalsIgnoreCase(player.getName())) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You cannot promote yourself");
//								return true;
//							}
//							if(Permissions.getRank(target.getName()).toInt() > 2) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not promote this player");
//								return true;
//							}
//							if(Permissions.outranks(target.getName(), player.getName())) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not promote " + ChatColor.YELLOW + target.getName());
//								return true;
//							}
//							if(Permissions.getRank(target.getName()) == Permissions.getRank(player.getName())) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not promote " + ChatColor.YELLOW + target.getName());
//								return true;
//							}
//							Ranks rank = Permissions.promote(target.getName());
//							M.message(target, "Permissions", ChatColor.WHITE + "You have been promoted to " + ChatColor.YELLOW + rank.getName());
//							M.message(player, "Permissions", ChatColor.WHITE + "You promoted " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " to " + ChatColor.YELLOW + rank.getName());
//							return true;
//						}
//						if(Permissions.getRank(args[1]).toInt() > 2) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not promote this player");
//							return true;
//						}
//						if(Permissions.outranks(args[1], player.getName())) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not promote " + ChatColor.YELLOW + args[1]);
//							return true;
//						}
//						if(Permissions.getRank(args[1]) == Permissions.getRank(player.getName())) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not promote " + ChatColor.YELLOW + args[1]);
//							return true;
//						}
//						Ranks rank = Permissions.promote(args[1]);
//						M.message(player, "Permissions", ChatColor.WHITE + "You promoted " + ChatColor.YELLOW + args[1] + ChatColor.WHITE + " to " + ChatColor.YELLOW + rank.getName());
//						return true;
//					}
//					if(args[0].equalsIgnoreCase("demote")) {
//						Player target = Bukkit.getPlayer(args[1]);
//						if(target != null) {
//							if(Permissions.getRank(target.getName()).toInt() < 1) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not demote " + ChatColor.YELLOW + target.getName());
//								return true;
//							}
//							if(Permissions.outranks(target.getName(), player.getName())) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not demote " + ChatColor.YELLOW + target.getName());
//								return true;
//							}
//							if(Permissions.getRank(target.getName()) == Permissions.getRank(player.getName())) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not demote " + ChatColor.YELLOW + target.getName());
//								return true;
//							}
//							Ranks rank = Permissions.demote(target.getName());
//							M.message(target, "Permissions", ChatColor.WHITE + "You have been demoted to " + ChatColor.YELLOW + rank.getName());
//							M.message(player, "Permissions", ChatColor.WHITE + "You demoted " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " to " + ChatColor.YELLOW + rank.getName());
//							return true;
//						}
//						if(Permissions.getRank(args[1]).toInt() < 1) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not demote " + ChatColor.YELLOW + args[1]);
//							return true;
//						}
//						if(Permissions.outranks(args[1], player.getName())) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not demote " + ChatColor.YELLOW + args[1]);
//							return true;
//						}
//						if(Permissions.getRank(args[1]) == Permissions.getRank(player.getName())) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not demote " + ChatColor.YELLOW + args[1]);
//							return true;
//						}
//						Ranks rank = Permissions.demote(args[1]);
//						M.message(player, "Permissions", ChatColor.WHITE + "You demoted " + ChatColor.YELLOW + args[1] + ChatColor.WHITE + " to " + ChatColor.YELLOW + rank.getName());
//						return true;
//					}
//					if(args[0].equalsIgnoreCase("set")) {
//						M.message(player, "Usage", ChatColor.YELLOW + "/permissions set <player> <rank>");
//						return true;
//					}
//				}
//				if(args.length == 3) {
//					if(args[0].equalsIgnoreCase("set")) {
//						if(Permissions.getRank(player.getName()).toInt() != 3) {
//							M.sendLackPermsMessage(player);
//							return true;
//						}
//						Ranks newRank = Ranks.getRank(args[2]);
//						if(newRank == null) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You must enter a valid rank");
//							return true;
//						}
//						Player target = Bukkit.getPlayer(args[1]);
//						if(target != null) {
//							if(Permissions.getRank(target.getName()) == Ranks.OWNER) {
//								M.message(player, "Permissions", ChatColor.WHITE + "You may not change " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.WHITE + "rank");
//								return true;
//							}
//							if(Permissions.getRank(target.getName()) == newRank) {
//								M.message(player, "Permissions", ChatColor.YELLOW + args[1] + ChatColor.WHITE + " is already " + ChatColor.YELLOW + newRank.getName());
//								return true;
//							}
//							Permissions.setRank(target.getName(), newRank);
//							M.message(player, "Permissions", ChatColor.WHITE + "You set " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.WHITE + "rank to " + ChatColor.YELLOW + newRank.getName());
//							M.message(target, "Permissions", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " set your rank to " + ChatColor.YELLOW + newRank.getName());
//							return true;
//						}
//						if(Permissions.getRank(args[1]) == Ranks.OWNER) {
//							M.message(player, "Permissions", ChatColor.WHITE + "You may not change " + ChatColor.YELLOW + args[1] + "'s " + ChatColor.WHITE + "rank");
//							return true;
//						}
//						if(Permissions.getRank(args[1]) == newRank) {
//							M.message(player, "Permissions", ChatColor.YELLOW + args[1] + ChatColor.WHITE + " is already " + ChatColor.YELLOW + newRank.getName());
//							return true;
//						}
//						Permissions.setRank(args[1], newRank);
//						M.message(player, "Permissions", ChatColor.WHITE + "You set " + ChatColor.YELLOW + args[1] + "'s " + ChatColor.WHITE + "rank to " + ChatColor.YELLOW + newRank.getName());
//						return true;
//					}
//				}
//			}
//			
//			if(commandLabel.equalsIgnoreCase("say")) {
//				if(!Permissions.isStaff(player.getName())) {
//					M.sendLackPermsMessage(player);
//					return true;
//				}
//				if(args.length == 0) {
//					player.sendMessage(M.getUsage("/say <message>"));
//					return true;
//				}
//				if(args.length > 0) {
//					String message = util.join(0, args);
//					M.broadcastServer(player.getName(), message);
//					return true;
//				}
//			}
//			if(commandLabel.equalsIgnoreCase("kick")) {
//				if(!Permissions.isStaff(player.getName())) {
//					M.sendLackPermsMessage(player);
//					return true;
//				}
//				if(args.length < 2) {
//					player.sendMessage(M.getUsage("/kick <player> <reason>"));
//					return true;
//				}
//				String reason = util.join(1, args);
//				Moderation.kick(player, args[0], reason);
//			}
//			if(commandLabel.equalsIgnoreCase("ignore")) {
//				if(args.length == 0) {
//					M.sendHelpMessage(player, "/ignore", "Toggles ignore on a player");
//					return true;
//				}
//				if(args.length == 1) {
//					Player p_target = Bukkit.getPlayer(args[0]);
//					if(p_target == null) {
//						if(Ignore.isIgnored(player, args[0])) {
//							Ignore.remove(player, args[0]);
//							M.message(player, "Ignore", ChatColor.WHITE + "You unignored " + ChatColor.YELLOW + args[0]);
//							return true;
//						} else {
//							M.message(player, "Ignore", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
//							return true;
//						}
//					}
//					if(Ignore.isIgnored(player, p_target.getName())) {
//						Ignore.remove(player, p_target.getName());
//						M.message(player, "Ignore", ChatColor.WHITE + "You unignored " + ChatColor.YELLOW + p_target.getName());
//						return true;
//					} else {
//						Ignore.add(player, p_target.getName());
//						M.message(player, "Ignore", ChatColor.WHITE + "You ignored " + ChatColor.YELLOW + p_target.getName());
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//}
