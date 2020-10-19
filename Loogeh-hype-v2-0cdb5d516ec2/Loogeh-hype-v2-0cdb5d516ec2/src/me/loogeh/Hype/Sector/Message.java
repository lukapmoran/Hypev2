package me.loogeh.Hype.Sector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Utility.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message extends Sector implements CommandExecutor {

	private static Message instance;
	
	private HashMap<String, List<String>> commandAliases = new HashMap<String, List<String>>();
	
	public Message() {
		super("Message");
		HashMap<String, List<String>> commandAliases = new HashMap<String, List<String>>();
		commandAliases.put("message", new ArrayList<String>());
		commandAliases.get("message").addAll(Arrays.asList("msg", "m", "tell", "whisper", "pm"));
		commandAliases.put("reply", new ArrayList<String>());
		commandAliases.get("reply").addAll(Arrays.asList("r"));
		commandAliases.put("adminmessage", new ArrayList<String>());
		commandAliases.get("adminmessage").addAll(Arrays.asList("am", "a"));
		this.commandAliases = commandAliases;
		for(String command : commandAliases.keySet()) {
			getPlugin().getCommand(command).setExecutor(this);
		}
		instance = this;
	}
	
	public static Message get() {
		if(instance == null) instance = new Message();
		return instance;
	}
	
	public void load() {

	}

	public void help(Player player) {
		message(player, ChatColor.WHITE + "Commands " + ChatColor.GRAY + "(optional) <required>");
		M.sendHelpMessage(player, "/message <player> <message>", "Message a player");
		M.sendHelpMessage(player, "/reply <message>", "Message your last target");
		M.sendHelpMessage(player, "/adminmessage (player) <message>", "Message all of the online staff");
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Message - " + ChatColor.WHITE + "Console cannot use message commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			message(player, "Failed to find your player data");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("message") || this.commandAliases.get("message").contains(commandLabel)) {
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(args.length == 1) {
				help(player);
				return true;
			}
			if(args.length > 1) {
				System.out.println("message");
				if(args[0].contains("@")) {
					int messageIndex = -1;
					List<String> groupMembers = new ArrayList<String>();
					String members = "";
					for(int i = 0; i < args.length; i++) {
						if(args[i].contains("@")) {
							String name = args[i].replace("@", "");
							if(!groupMembers.contains(name)) {
								groupMembers.add(name);
								members = members + "@" + name + " ";
							}
						} else {
							messageIndex = i;
							break;
						}
					}
					if(messageIndex == -1) {
						message(player, ChatColor.WHITE + "You must input a message");
						return true;
					}
					String message = "";
					for(int i  = messageIndex; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					for(String groupMember : groupMembers) {
						Player pmember = Bukkit.getPlayer(groupMember);
						if(pmember != null) {
							Member mmember = Member.get(pmember);
							if(mmember != null) {
								String personalMessage =  mmember.getSettings().getPersonalMessage(player.getName(), members.substring(0, members.length() - 1), message);
								pmember.sendMessage(personalMessage);
							}
						}
					}
					groupMembers.clear();
					return true;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if(target == null) {
					message(player, ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
					return true;
				}
				Member mtarget = Member.get(target);
				if(mtarget == null) {
					message(player, "Failed to find " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.WHITE + "player data");
					return true;
				}
				String message = util.join(1, args);
				target.sendMessage(mtarget.getSettings().getPersonalMessage(player.getName(), target.getName(), message));
				player.sendMessage(member.getSettings().getPersonalMessage(player.getName(), target.getName(), message));
			}
		}
		if(commandLabel.equalsIgnoreCase("reply") || commandAliases.get("reply").contains(commandLabel)) {
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(member.getSession().getLastMessageTarget() == null) {
				message(player, "You have no one to reply to");
				return true;
			}
			Player target = Bukkit.getPlayer(member.getSession().getLastMessageTarget());
			if(target == null) {
				message(player, ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + member.getSession().getLastMessageTarget());
				return true;
			}
			Member mtarget = Member.get(target);
			if(mtarget == null) {
				message(player, "Failed to find " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.WHITE + "player data");
				return true;
			}
			String message = util.join(0, args);
			target.sendMessage(mtarget.getSettings().getPersonalMessage(player.getName(), target.getName(), message));
			player.sendMessage(member.getSettings().getPersonalMessage(player.getName(), target.getName(), message));
		}
		if(commandLabel.equalsIgnoreCase("adminmessage")) {
			if(args.length == 0) {
				help(player);
				return true;
			}
		}
		return false;
	}
	
	

}
