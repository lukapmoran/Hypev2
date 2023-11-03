package me.loogeh.Hype.ChatChannels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import me.loogeh.Hype.ChatChannels.Channel.Status;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ChannelManager {

	public static HashMap<String, Channel> channelMap = new HashMap<String, Channel>();
	
	public static void load() {
		Main.logger.log(Level.INFO, "Sector > Loading sector 'chat channels'");
		long start = System.currentTimeMillis();
		int count = 0;
		if(Main.channels != null) {
			ConfigurationSection section = Main.channels.getConfigurationSection("channels");
			if(section != null) {
				Set<String> set = section.getKeys(true);
				if(set != null) {
					for(String elem : set) {
						if(elem != null) {
							String name = null;
							if(!elem.contains(".")) name = elem;
							ArrayList<String> list = (ArrayList<String>) util.getListFromString(Main.channels.getString("channels." + name + ".members"), ",");
							HashMap<String, Boolean> members = new HashMap<String, Boolean>();
							for(String member : list) {
								members.put(member, false);
							}
							Status status = Status.getStatus(Main.channels.getString("channels." + name + ".status"));
							String creator = Main.channels.getString("channels." + name + ".creator");
							long creation_time = Main.channels.getLong("channels." + name + ".creation_time");
							String owner = Main.channels.getString("channels." + name + ".owner");
							Channel channel = new Channel(name, creator);
							channel.setStatus(status);
							channel.setOwner(owner);
							channel.setCreationTime(creation_time);
							channel.setMembers(members);
							channelMap.put(name, channel);
						}
					}
					count++;	
				}
			}
		}
		Main.logger.log(Level.INFO, "Sector > Loaded sector 'chat channels' in " + (System.currentTimeMillis() - start) + "ms (" + count + ")");
	}
	
	public static Channel getChannel(String name) {
		if(channelMap.containsKey(name)) return channelMap.get(name);
		else return null;
	}
	
	public static boolean exists(String name, boolean case_sensitive) {
		for(String channel : channelMap.keySet()) {
			if(case_sensitive) {
				if(name.equals(channel)) return true;
			} else {
				if(name.equalsIgnoreCase(channel)) return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static Channel searchChannel(String token) {
		if(getChannel(token) != null) return getChannel(token);
		Player target = Bukkit.getPlayer(token);
		for(Entry<String, Channel> entry : channelMap.entrySet()) {
			if(target != null) {
				if(entry.getValue().getMembers().containsKey(target.getName())) return entry.getValue();
			} else {
				if(entry.getValue().getMembers().containsKey(token)) return entry.getValue();
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static void sendPlayerInfo(Player player, String target) {
		Player p_target = Bukkit.getPlayer(target);
		if(p_target != null) {
			String channels = getChannelsAsString(p_target.getName());
			Channel active = getActiveChannel(p_target.getName());
			String created = getCreatedAsString(p_target.getName());
			String owner = getOwnerAsString(p_target.getName());
			player.sendMessage(ChatColor.LIGHT_PURPLE + p_target.getName() + ChatColor.DARK_PURPLE + " > " + ChatColor.LIGHT_PURPLE + "Player Info");
			if(channels == null) player.sendMessage(ChatColor.YELLOW + "Channels" + ChatColor.GREEN + "0" + ChatColor.YELLOW + " > " + channels);
			else player.sendMessage(ChatColor.YELLOW + "Channels" + ChatColor.GREEN + getChannelCount(p_target.getName()) + ChatColor.YELLOW + " > " + channels);
			if(active == null) player.sendMessage(ChatColor.YELLOW + "Active >");
			else player.sendMessage(ChatColor.YELLOW + "Active > " + active.getName());
			if(created == null) player.sendMessage(ChatColor.YELLOW + "Created" + ChatColor.GREEN + "0" + ChatColor.YELLOW + " > " + channels);
			else player.sendMessage(ChatColor.YELLOW + "Created" + ChatColor.GREEN + getChannelCount(p_target.getName()) + ChatColor.YELLOW + " > " + channels);
			if(owner == null) player.sendMessage(ChatColor.YELLOW + "Owner" + ChatColor.GREEN + "0" + ChatColor.YELLOW + " > " + channels);
			else player.sendMessage(ChatColor.YELLOW + "Owner" + ChatColor.GREEN + getChannelCount(p_target.getName()) + ChatColor.YELLOW + " > " + channels);
			return;
		}
		String channels = getChannelsAsString(target);
		Channel active = getActiveChannel(target);
		String created = getCreatedAsString(target);
		String owner = getOwnerAsString(target);
		player.sendMessage(ChatColor.LIGHT_PURPLE + target + ChatColor.DARK_PURPLE + " > " + ChatColor.LIGHT_PURPLE + "Player Info");
		if(channels == null) player.sendMessage(ChatColor.YELLOW + "Channels" + ChatColor.GREEN + "0" + ChatColor.YELLOW + " > " + channels);
		else player.sendMessage(ChatColor.YELLOW + "Channels" + ChatColor.GREEN + getChannelCount(target) + ChatColor.YELLOW + " > " + channels);
		if(active == null) player.sendMessage(ChatColor.YELLOW + "Active >");
		else player.sendMessage(ChatColor.YELLOW + "Active > " + active.getName());
		if(created == null) player.sendMessage(ChatColor.YELLOW + "Created" + ChatColor.GREEN + "0" + ChatColor.YELLOW + " > " + channels);
		else player.sendMessage(ChatColor.YELLOW + "Created" + ChatColor.GREEN + getChannelCount(target) + ChatColor.YELLOW + " > " + channels);
		if(owner == null) player.sendMessage(ChatColor.YELLOW + "Owner" + ChatColor.GREEN + "0" + ChatColor.YELLOW + " > " + channels);
		else player.sendMessage(ChatColor.YELLOW + "Owner" + ChatColor.GREEN + getChannelCount(target) + ChatColor.YELLOW + " > " + channels);
	}
	
	public static void sendChannelInfo(Player player, String target) {
		if(player == null) return;
		Channel channel = searchChannel(target);
		if(channel == null) {
			M.message(player, "Channels", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target);
			return;
		}
		player.sendMessage(ChatColor.LIGHT_PURPLE + channel.getName() + ChatColor.DARK_PURPLE + " > " + ChatColor.LIGHT_PURPLE + "Channel Info");
		player.sendMessage(ChatColor.YELLOW + "Members " + ChatColor.GREEN + channel.getMembers().size() + ChatColor.YELLOW + " > " + channel.getMembersFormatted());
		player.sendMessage(ChatColor.YELLOW + "Status > " + ChatColor.WHITE + channel.getStatus().getName());
		player.sendMessage(ChatColor.YELLOW + "Creator > " + ChatColor.WHITE + channel.getCreator());
		player.sendMessage(ChatColor.YELLOW + "Owner > " + ChatColor.WHITE + channel.getOwner());
		player.sendMessage(ChatColor.YELLOW + "Age > " + ChatColor.WHITE + channel.getAge());
		player.sendMessage(ChatColor.YELLOW + "Invited > " + ChatColor.GREEN + channel.getInvites().size());
	}
	
	public static void sendChannelList(Player player) {
		if(player == null) return;
		List<Channel> channels = getChannels(player.getName());
		if(channels.size() == 0) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not in any " + ChatColor.YELLOW + "Channels");
			return;
		}
		String channelsList = "";
		for(Channel channel : channels) {
			channelsList = channelsList + channel.getName() + ", ";
		}
		player.sendMessage(ChatColor.BLUE + "Channels List " + ChatColor.WHITE + "> Your Channels");
		player.sendMessage(ChatColor.BLUE + "List - " + ChatColor.WHITE + channelsList.substring(0, channelsList.length() - 2));
		return;
	}
	
	public static boolean hasChannel(String player) {
		for(Entry<String, Channel> entry : channelMap.entrySet()) {
			Channel channel = entry.getValue();
			if(channel.getMembers().containsKey(player)) return true;
		}
		return false;
	}
	
	public static List<Channel> getChannels(String player) {
		ArrayList<Channel> channels = new ArrayList<Channel>();
		for(Entry<String, Channel> entry : channelMap.entrySet()) {
			Channel channel = entry.getValue();
			if(channel.getMembers().containsKey(player)) channels.add(channel);
		}
		return channels;
	}
	
	public static int getChannelCount(String player) {
		return getChannels(player).size();
	}
	
	public static boolean isMember(String player, String channel) {
		if(!channelMap.containsKey(channel)) return false;
		if(channelMap.get(channel).getMembers().containsKey(player)) return true;
		else return false;
	}
	
	public static String getChannelsAsString(String player) {
		if(!hasChannel(player)) return null;
		ArrayList<Channel> channels = (ArrayList<Channel>) getChannels(player);
		if(channels.size() == 0) return null;
		String channelsString = "";
		for(Channel channel : channels) {
			String name = channel.getName();
			channelsString = channelsString + name + ", ";
		}
		return channelsString.substring(0, channelsString.length() - 2);
	}
	
	public static Channel getActiveChannel(String player) {
		ArrayList<Channel> channels = (ArrayList<Channel>) getChannels(player);
		for(Channel channel : channels) {
			if(channel.getMembers().containsKey(player)) {
				if(channel.getMembers().get(player) == true) return channel;
			}
		}
		return null;
	}
	
	public static List<Channel> getCreatedChannels(String player) {
		ArrayList<Channel> created = new ArrayList<Channel>();
		for(Entry<String, Channel> entry : channelMap.entrySet()) {
			Channel channel = entry.getValue();
			if(channel.getCreator().equalsIgnoreCase(player)) created.add(channel);
		}
		return created;
	}
	
	public static List<Channel> getOwnerChannels(String player) {
		ArrayList<Channel> owner = new ArrayList<Channel>();
		for(Entry<String, Channel> entry : channelMap.entrySet()) {
			Channel channel = entry.getValue();
			if(channel.getOwner().equalsIgnoreCase(player)) owner.add(channel);
		}
		return owner;
	}
	
	public static String getCreatedAsString(String player) {
		if(!hasChannel(player)) return null;
		ArrayList<Channel> created = (ArrayList<Channel>) getCreatedChannels(player);
		if(created.size() == 0) return null;
		String channelsString = "";
		for(Channel channel : created) {
			String name = channel.getName();
			channelsString = channelsString + name + ", ";
		}
		return channelsString.substring(0, channelsString.length() - 2);
	}
	
	public static String getOwnerAsString(String player) {
		if(!hasChannel(player)) return null;
		ArrayList<Channel> owner = (ArrayList<Channel>) getOwnerChannels(player);
		if(owner.size() == 0) return null;
		String channelsString = "";
		for(Channel channel : owner) {
			String name = channel.getName();
			channelsString = channelsString + name + ", ";
		}
		return channelsString.substring(0, channelsString.length() - 2);
	}
	
	public static void setActive(String player, String channel) {
		if(!isMember(player, channel)) return;
		Channel target = getChannel(channel);
		if(target == null) return;
		Channel active = getActiveChannel(player);
		if(active != null) {
			if(active.getName().equalsIgnoreCase(channel)) return;
			active.addMember(player, false);
		}
		target.addMember(player, true);
	}
	
	public static void setActive(String player, Channel channel) {
		if(channel == null) return;
		if(!channel.getMembers().containsKey(player)) return;
		Channel active = getActiveChannel(player);
		if(active != null) {
			if(active.getName().equalsIgnoreCase(channel.getName())) return;
			active.addMember(player, false);
		}
		channel.addMember(player, true);
	}
	
	public static void setInactive(String player, Channel channel) {
		if(channel == null) return;
		if(!channel.getMembers().containsKey(player)) return;
		channel.addMember(player, false);
	}
}
