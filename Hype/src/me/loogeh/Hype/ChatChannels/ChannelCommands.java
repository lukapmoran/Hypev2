package me.loogeh.Hype.ChatChannels;

import me.loogeh.Hype.ChatChannels.Channel.Status;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Chat Channels - " + ChatColor.WHITE + "Chat Channel commands can only be used as a " + ChatColor.YELLOW + "Player");
			return true;
		}
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("chatchannel") || commandLabel.equalsIgnoreCase("chatchannels") || commandLabel.equalsIgnoreCase("cc")) {
			if(args.length == 0) {
				Channel channel = ChannelManager.getActiveChannel(player.getName());
				if(channel == null) {
					M.message(player, "Channels", ChatColor.WHITE + "You are not " + ChatColor.YELLOW + "Active " + ChatColor.WHITE + "in any channels");
					return true;
				}
				channel.sendChannelInfo(player);
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("create")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc create <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("leave")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc leave <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("join")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc join <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("i")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc invite <player> <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("uninvite")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc uninvite <player> <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("end")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc end <squad>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("kick")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc kick <player> <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("toggle")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc toggle <channel>");
					return true;
				} else if(args[0].equalsIgnoreCase("status")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc status <status> <channel>");
					return true;
				} else if(args[0].equalsIgnoreCase("list")) {
					ChannelManager.sendChannelList(player);
					return true;
				} else if(args[0].equalsIgnoreCase("owner")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc owner <player> <channel>");
					return true;
				} else {
					Channel target = ChannelManager.searchChannel(args[0]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
						return true;
					}
					target.sendChannelInfo(player);
					return true;
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("create")) {
					Channel.create(args[1], player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("leave")) {
					Channel target = ChannelManager.searchChannel(args[1]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					target.leave(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("join")) {
					Channel target = ChannelManager.searchChannel(args[1]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					target.join(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("i")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc invite <player> <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("uninvite")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc uninvite <player> <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("end")) {
					Channel target = ChannelManager.searchChannel(args[1]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					target.remove(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("kick")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc kick <player> <channel>");
					return true;
				}
				else if(args[0].equalsIgnoreCase("toggle")) {
					Channel target = ChannelManager.searchChannel(args[1]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					target.toggle(player);
					return true;
				} else if(args[0].equalsIgnoreCase("status")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc status <status> <channel>");
					return true;
				} else if(args[0].equalsIgnoreCase("owner")) {
					M.message(player, "Usage", ChatColor.WHITE + "/cc owner <player> <channel>");
					return true;
				} else {
					Channel target = ChannelManager.searchChannel(args[0]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						return true;
					}
					target.chat(player, args[1]);
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("i")) {
					Channel target = ChannelManager.searchChannel(args[2]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
						return true;
					}
					target.invite(player, args[1]);
					return true;
				}
				else if(args[0].equalsIgnoreCase("uninvite")) {
					Channel target = ChannelManager.searchChannel(args[2]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
						return true;
					}
					target.uninvite(player, args[1]);
					return true;
				}
				else if(args[0].equalsIgnoreCase("kick")) {
					Channel target = ChannelManager.searchChannel(args[2]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
						return true;
					}
					target.kick(player, args[1]);
					return true;
				} else if(args[0].equalsIgnoreCase("status")) {
					Channel target = ChannelManager.searchChannel(args[2]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
						return true;
					}
					Status newStatus = Status.getStatus(args[1]);
					target.setStatus(player, newStatus);
					return true;
				} else if(args[0].equalsIgnoreCase("owner")) {
					Channel target = ChannelManager.searchChannel(args[2]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
						return true;
					}
					target.passOwnership(player, args[1]);
				} else {
					Channel target = ChannelManager.searchChannel(args[0]);
					if(target == null) {
						M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
						return true;
					}
					String join = util.join(1, args);
					target.invite(player, join);
					return true;
				}
			}
			if(args.length > 4) {
				Channel target = ChannelManager.searchChannel(args[0]);
				if(target == null) {
					M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[2]);
					return true;
				}
				String join = util.join(1, args);
				target.invite(player, join);
				return true;
			}
		}
		return false;
	}

}
