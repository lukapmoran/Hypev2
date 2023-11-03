package me.loogeh.Hype.ChatChannels;

import java.util.HashMap;
import java.util.Map.Entry;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Utility.util;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Channel {
	
	private String name;
	private HashMap<String, Boolean> memberMap = new HashMap<String, Boolean>();
	private Status status = Status.CLOSED;
	private String creator;
	private String owner;
	private long creation_time;
	
	private HashMap<String, Long> inviteMap = new HashMap<String, Long>();
	
	public Channel(String name, String creator) {
		this.name = name;
		this.creator = creator; 
		this.owner = creator;
		this.creation_time = System.currentTimeMillis();
	}
	
	public String getName() {
		return this.name;
	}
	
	public HashMap<String, Boolean> getMembers() {
		return this.memberMap;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public String getCreator() {
		return this.creator;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public long getCreationTime() {
		return this.creation_time;
	}
	
	public HashMap<String, Long> getInvites() {
		return this.inviteMap;
	}
	
	public String getAge() {
		return utilTime.convertString((System.currentTimeMillis() - getCreationTime()), TimeUnit.BEST, 1);
	}
	
	public String getMembersFormatted() {
		String members = "";
		for(Entry<String, Boolean> entry : memberMap.entrySet()) {
			if(!entry.getValue())  members = members + ChatColor.RED + entry.getKey() + ", ";
			else members = members + ChatColor.GREEN + entry.getKey() + ", ";
		}
		return members.substring(0, members.length() - 2);
	}
	
	public void setStatus(Status status) {
		this.status = status;
		Main.channels.set("channels." + getName() + ".status", status.toString());
		Main.channelsFile.save();
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
		Main.channels.set("channels." + getName() + ".owner", owner);
		Main.channelsFile.save();
	}
	
	public void setMembers(HashMap<String, Boolean> members) {
		this.memberMap = members;
	}
	
	public void setCreationTime(long time_millis) {
		this.creation_time = time_millis;
	}
	
	public static void create(String name, Player creator) {
		if(ChannelManager.exists(name, false)) {
			M.message(creator, "Channels", ChatColor.YELLOW + name + ChatColor.WHITE + " already exists");
			return;
		}
		if(!getCanHaveSpecialCharacters()) {
			if(util.containsSpecialChar(name)) {
				M.message(creator, "Channels", ChatColor.YELLOW + "Channel Names " + ChatColor.WHITE + "cannot contain " + ChatColor.YELLOW + "Special Characters");
				return;
			}
		}
		if(getMaxChannelsPerPlayer() > -1) {
			if(ChannelManager.getChannels(creator.getName()).size() >= getMaxChannelsPerPlayer()) {
				M.message(creator, "Channels", ChatColor.WHITE + "You have reached your " + ChatColor.YELLOW + "Channel Limit");
				return;
			}
		}
		if(name.length() > getMaxNameLength()) {
			M.message(creator, "Channels", ChatColor.YELLOW + name + ChatColor.WHITE + " exceeds the maximum character limit");
			return;
		}
		if(name.length() < getMinNameLength()) {
			M.message(creator, "Channels", ChatColor.YELLOW + name + ChatColor.WHITE + " does not reach the minimum character requirement");
			return;
		}
		ChannelManager.channelMap.put(name, new Channel(name, creator.getName()));
		ChannelManager.channelMap.get(name).addMember(creator.getName(), false);
		ChannelManager.setActive(creator.getName(), name);
		M.message(creator, "Channels", ChatColor.WHITE + "You created the chat channel " + ChatColor.YELLOW + name);
		ConfigurationSection section = Main.channels.getConfigurationSection("channels");
		if(section == null) {
			Main.channels.createSection("channels");
		}
		section = Main.channels.getConfigurationSection("channels");
		if(section != null) {
			Main.channels.createSection("channels." + name);
			Main.channels.createSection("channels." + name + ".members");
			Main.channels.createSection("channels." + name + ".creator");
			Main.channels.createSection("channels." + name + ".creation_time");
			Main.channels.createSection("channels." + name + ".owner");
			Main.channels.set("channels." + name + ".members", creator.getName());
			Main.channels.set("channels." + name + ".status", Status.CLOSED.toString());
			Main.channels.set("channels." + name + ".creator", creator.getName());
			Main.channels.set("channels." + name + ".creation_time", System.currentTimeMillis());
			Main.channels.set("channels." + name + ".owner", creator.getName());
			Main.channelsFile.save();
		}
	}
	
	public void remove(Player player) {
		if(player == null) return;
		if(!memberMap.containsKey(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not a member of " + ChatColor.YELLOW + getName());
			return;
		}
		if(!getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not the owner of " + ChatColor.YELLOW + getName());
			return;
		}
		sendMessageAll(ChatColor.GREEN + getName() + ChatColor.WHITE + " was ended by " + ChatColor.DARK_GREEN + player.getName());
		Main.channels.set("channels." + getName(), null);
		ChannelManager.channelMap.remove(getName());
		Main.channelsFile.save();
	}
	
	@SuppressWarnings("deprecation")
	public void passOwnership(Player player, String target) {
		if(player == null) return;
		if(!getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You must be the " + ChatColor.YELLOW + "Owner " + ChatColor.WHITE + "to " + ChatColor.YELLOW + "Pass Ownership");
			return;
		}
		Player p_target = Bukkit.getPlayer(target);
		if(p_target != null) {
			if(!memberMap.containsKey(p_target.getName())) {
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " is not a member of " + ChatColor.YELLOW + getName());
				return;
			}
			setOwner(p_target.getName());
			sendMessageAll(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " passed the ownership of " + ChatColor.DARK_GREEN + getName() + ChatColor.WHITE + " to " + ChatColor.GREEN + p_target.getName());
			return;
		}
		if(!memberMap.containsKey(target)) {
			M.message(player, "Channels", ChatColor.YELLOW + target + ChatColor.WHITE + " is not a member of " + ChatColor.YELLOW + getName());
			return;
		}
		setOwner(target);
		sendMessageAll(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " passed the ownership of " + ChatColor.DARK_GREEN + getName() + ChatColor.WHITE + " to " + ChatColor.GREEN + target);
		return;
	}
	
	public void leave(Player player) {
		if(player == null) return;
		if(!memberMap.containsKey(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not a member of " + ChatColor.YELLOW + getName());
			return;
		}
		if(memberMap.size() > 1 && getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You must " + ChatColor.YELLOW + "Pass Ownership " + ChatColor.WHITE + "before leaving");
			return;
		}
		memberMap.remove(player.getName());
		sendMessageAll(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " left " + ChatColor.GREEN + getName());
		M.message(player, "Channels", ChatColor.WHITE + "You left " + ChatColor.YELLOW + getName());
		if(memberMap.size() == 0) {
			Main.channels.set("channels." + getName(), null);
			if(ChannelManager.channelMap.containsKey(getName())) ChannelManager.channelMap.remove(getName());
			return;
		}
		Main.channels.set("channels." + getName() + ".members", getMemberList());
		Main.channelsFile.save();
	}
	
	public String getMemberList() {
		String members = "";
		for(String member : memberMap.keySet()) {
			members = members + "," + member;
		}
		return members.substring(0, members.length() - 1);
	}
	
	@SuppressWarnings("deprecation")
	public void kick(Player player, String target) {
		if(player == null) return;
		if(player.getName().equalsIgnoreCase(target)) {
			M.message(player, "Channels", ChatColor.WHITE + "You cannot kick yourself");
			return;
		}
		if(!getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "Only the " + ChatColor.YELLOW + "Owner " + ChatColor.WHITE + "can " + ChatColor.YELLOW + "Kick");
			return;
		}
		if(memberMap.containsKey(target)) {
			Player exact_target = Bukkit.getPlayerExact(target);
			if(exact_target != null) {
				memberMap.remove(target);
				Main.channels.set("channels." + getName() + ".members", getMemberList());
				M.message(exact_target, "Channels", ChatColor.WHITE + "You were kicked from " + ChatColor.YELLOW + getName());
				sendMessageActiveSender(player, ChatColor.GREEN + exact_target.getName() + ChatColor.WHITE + " was kicked from " + ChatColor.DARK_GREEN + getName());
				return;
			}
			memberMap.remove(target);
			Main.channels.set("channels." + getName() + ".members", getMemberList());
			sendMessageActiveSender(player, ChatColor.GREEN + target + ChatColor.WHITE + " was kicked from " + ChatColor.DARK_GREEN + getName());
			return;
		}
		Player p_target = Bukkit.getPlayer(target);
		if(p_target != null) {
			if(player.getName().equalsIgnoreCase(p_target.getName())) {
				M.message(player, "Channels", ChatColor.WHITE + "You cannot kick yourself");
				return;
			}
			if(!memberMap.containsKey(p_target.getName())) {
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " is not a member of " + ChatColor.YELLOW + getName());
				return;
			}
			memberMap.remove(p_target.getName());
			Main.channels.set("channels." + getName() + ".members", getMemberList());
			M.message(p_target, "Channels", ChatColor.WHITE + "You were kicked from " + ChatColor.YELLOW + getName());
			sendMessageActiveSender(player, ChatColor.GREEN + p_target.getName() + ChatColor.WHITE + " was kicked from " + ChatColor.DARK_GREEN + getName());
			return;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void invite(Player player, String target) {
		if(player == null) return;
		if(!getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "Only the " + ChatColor.YELLOW + "Owner " + ChatColor.WHITE + "can invite people");
			return;
		}
		Player p_target = Bukkit.getPlayer(target);
		if(p_target != null) {
			if(memberMap.containsKey(p_target.getName())) {
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " is already in " + ChatColor.YELLOW + getName());
				return;
			}
			if(inviteMap.containsKey(p_target.getName())) {
				double rem = utilTime.convert((86400000 + inviteMap.get(p_target.getName()) - System.currentTimeMillis()), TimeUnit.SECONDS, 1);
				if(rem <= 0.0) {
					inviteMap.put(p_target.getName(), System.currentTimeMillis());
					sendMessageActiveSender(player, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " invited " + ChatColor.DARK_GREEN + p_target.getName());
					M.message(p_target, "Channels", ChatColor.WHITE + "You have been invited to join " + ChatColor.YELLOW + getName());
					return;
				}
				String remaining = utilTime.convertString((86400000 + inviteMap.get(p_target.getName()) - System.currentTimeMillis()), TimeUnit.SECONDS, 1);
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + "'s " + ChatColor.WHITE + "invite expires in " + remaining);
				return;
			}
			inviteMap.put(p_target.getName(), System.currentTimeMillis());
			sendMessageActiveSender(player, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " invited " + ChatColor.DARK_GREEN + p_target.getName());
			M.message(p_target, "Channels", ChatColor.WHITE + "You have been invited to join " + ChatColor.YELLOW + getName());
			return;
			
		}
		if(memberMap.containsKey(target)) {
			M.message(player, "Channels", ChatColor.YELLOW + target + ChatColor.WHITE + " is already in " + ChatColor.YELLOW + getName());
			return;
		}
		if(inviteMap.containsKey(target)) {
			double rem = utilTime.convert((86400000 + inviteMap.get(target)) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
			if(rem <= 0.0) {
				inviteMap.put(target, System.currentTimeMillis());
				sendMessageActiveSender(player, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " invited " + ChatColor.DARK_GREEN + target);
				return;
			}
			String remaining = utilTime.convertString((86400000 + inviteMap.get(target) - System.currentTimeMillis()), TimeUnit.SECONDS, 1);
			M.message(player, "Channels", ChatColor.YELLOW + target + "'s " + ChatColor.WHITE + "invite expires in " + remaining);
			return;
		}
		inviteMap.put(target, System.currentTimeMillis());
		sendMessageActiveSender(player, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " invited " + ChatColor.DARK_GREEN + target);
	}
	
	@SuppressWarnings("deprecation")
	public void uninvite(Player player, String target) {
		if(player == null) return;
		if(!getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "Only the " + ChatColor.YELLOW + "Owner " + ChatColor.WHITE + "can uninvite people");
			return;
		}
		Player p_target = Bukkit.getPlayer(target);
		if(p_target != null) {
			if(!memberMap.containsKey(p_target.getName())) {
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " is not a member of " + ChatColor.YELLOW + getName());
				return;
			}
			if(!inviteMap.containsKey(p_target.getName())) {
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " is not invited to " + ChatColor.YELLOW + getName());
				return;
			}
			double rem = utilTime.convert((86400000 + inviteMap.get(target)) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
			if(rem <= 0.0) {
				inviteMap.remove(p_target.getName());
				M.message(player, "Channels", ChatColor.YELLOW + p_target.getName() + "'s " + ChatColor.WHITE + "invite has " + ChatColor.YELLOW + "expired");
				return;
			}
			inviteMap.remove(p_target.getName());
			sendMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " uninvited " + ChatColor.GREEN + p_target.getName() + ChatColor.WHITE + " from " + ChatColor.DARK_GREEN + getName());
			return;
		}
		if(!memberMap.containsKey(target)) {
			M.message(player, "Channels", ChatColor.YELLOW + target + ChatColor.WHITE + " is not a member of " + ChatColor.YELLOW + getName());
			return;
		}
		if(!inviteMap.containsKey(target)) {
			M.message(player, "Channels", ChatColor.YELLOW + target + ChatColor.WHITE + " is not invited to " + ChatColor.YELLOW + getName());
			return;
		}
		double rem = utilTime.convert((86400000 + inviteMap.get(target)) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
		if(rem <= 0.0) {
			inviteMap.remove(target);
			M.message(player, "Channels", ChatColor.YELLOW + target + "'s " + ChatColor.WHITE + "invite has " + ChatColor.YELLOW + "expired");
			return;
		}
		inviteMap.remove(target);
		sendMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " uninvited " + ChatColor.GREEN + target + ChatColor.WHITE + " from " + ChatColor.DARK_GREEN + getName());
		return;
	}
	
	public void join(Player player) {
		if(player == null) return;
		if(memberMap.containsKey(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are already a member of " + ChatColor.YELLOW + getName());
			return;
		}
		if(getMaxChannelsPerPlayer() > -1) {
			if(ChannelManager.getChannels(player.getName()).size() >= getMaxChannelsPerPlayer()) {
				M.message(player, "Channels", ChatColor.WHITE + "You have reached your " + ChatColor.YELLOW + "Channel Limit");
				return;
			}
		}
		if(getStatus() == Status.OPEN) {
			addMember(player.getName(), false);
			return;
		}
		if(!inviteMap.containsKey(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not invited to " + ChatColor.YELLOW + getName());
			return;
		}
		double rem = utilTime.convert((86400000 + inviteMap.get(player.getName())) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
		if(rem <= 0.0) {
			M.message(player, "Channels", ChatColor.WHITE + "Your invitation from " + ChatColor.YELLOW + getName() + ChatColor.WHITE + " expired");
			return;
		}
		addMember(player.getName(), false);
		inviteMap.remove(player.getName());
	}
	
	@SuppressWarnings("deprecation")
	public void addMemberSilent(String player, boolean active) {
		Player p_player = Bukkit.getPlayer(player);
		if(p_player != null) {
			if(getMaxChannelsPerPlayer() > -1) {
				if(ChannelManager.getChannels(p_player.getName()).size() >= getMaxChannelsPerPlayer()) return;
			}
			boolean newMember = memberMap.containsKey(p_player.getName());
			memberMap.put(p_player.getName(), active);
			if(newMember) {
				M.message(p_player, "Channels", ChatColor.WHITE + "You joined " + ChatColor.YELLOW + getName());
				if(memberMap.size() < 1) Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + p_player.getName());
				else Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + "," + p_player.getName());
			}
			Main.channelsFile.save();
			return;
		}
		if(getMaxChannelsPerPlayer() > -1) {
			if(ChannelManager.getChannels(player).size() >= getMaxChannelsPerPlayer()) return;
		}
		boolean newMember = memberMap.containsKey(player);
		memberMap.put(player, active);
		if(newMember) {
			if(memberMap.size() < 1) Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + player);
			else Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + "," + player);
		}
		Main.channelsFile.save();
	}
	
	@SuppressWarnings("deprecation")
	public void addMember(String player, boolean active) {
		Player p_player = Bukkit.getPlayer(player);
		if(p_player != null) {
			if(getMaxChannelsPerPlayer() > -1) {
				if(ChannelManager.getChannels(p_player.getName()).size() >= getMaxChannelsPerPlayer()) return;
			}
			boolean newMember = !memberMap.containsKey(p_player.getName());
			memberMap.put(p_player.getName(), active);
			if(newMember) {
				sendMessageAll(ChatColor.GREEN + p_player.getName() + ChatColor.WHITE + " joined " + ChatColor.GREEN + getName());
				if(memberMap.size() < 1) Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + p_player.getName());
				else Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + "," + p_player.getName());
				Main.channelsFile.save();
			}
			return;
		}
		if(getMaxChannelsPerPlayer() > -1) {
			if(ChannelManager.getChannels(player).size() >= getMaxChannelsPerPlayer()) return;
		}
		boolean newMember = !memberMap.containsKey(player);
		memberMap.put(player, active);
		if(newMember) {
			sendMessageAll(ChatColor.GREEN + player + ChatColor.WHITE + " joined " + ChatColor.GREEN + getName());
			if(memberMap.size() < 1) Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + player);
			else Main.channels.set("channels." + getName() + ".members", Main.channels.getString("channels." + getName() + ".members") + "," + player);
			Main.channelsFile.save();
		}
	}
	
	public void put(String player, boolean active) {
		memberMap.put(player, active);
	}
	
	public void toggle(Player player) {
		if(player == null) return;
		Channel active = ChannelManager.getActiveChannel(player.getName());
		if(active != null) {
			if(active.getName().equalsIgnoreCase(getName())) {
				if(memberMap.get(player.getName()) == false) memberMap.put(player.getName(), true);
				else memberMap.put(player.getName(), false);
				M.message(player, "Channels", ChatColor.WHITE + "You toggled chat for " + ChatColor.YELLOW + getName());
				return;
			}
			active.memberMap.put(player.getName(), false);
		}
		if(memberMap.get(player.getName()) == false) memberMap.put(player.getName(), true);
		else memberMap.put(player.getName(), false);
		M.message(player, "Channels", ChatColor.WHITE + "You toggled chat for " + ChatColor.YELLOW + getName());
	}
	
	public void chat(Player player, String message) {
		if(player == null) return;
		if(!memberMap.containsKey(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not a member of " + ChatColor.YELLOW + getName());
			return;
		}
		sendMessage(player.getName(), message);
	}
	
	@SuppressWarnings("deprecation")
	public void sendMessage(String message) {
		for(Entry<String, Boolean> entry : memberMap.entrySet()) {
			if(entry.getValue()) {
				Player p_member = Bukkit.getPlayer(entry.getKey());
				if(p_member != null) p_member.sendMessage(ChatColor.DARK_GREEN + getName() + " > " + message);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendMessageAll(String message) {
		for(String member : memberMap.keySet()) {
			Player p_member = Bukkit.getPlayer(member);
			if(p_member != null) p_member.sendMessage(ChatColor.DARK_GREEN + getName() + " > " + message);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendMessage(String sender, String message) {
		for(String member : memberMap.keySet()) {
			Player p_member = Bukkit.getPlayer(member);
			String format = getFormat(sender, message);
			if(format != null) {
				if(p_member != null) p_member.sendMessage(format);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendMessageActiveSender(Player player, String message) {
		Channel active = ChannelManager.getActiveChannel(player.getName());
		if(active == null) {
			player.sendMessage(ChatColor.DARK_GREEN + getName() + ChatColor.GREEN + " > " + message);
			return;
		}
		if(!active.getName().equalsIgnoreCase(getName())) {
			player.sendMessage(ChatColor.DARK_GREEN + getName() + ChatColor.GREEN + " > " + message);
			return;
		}
		for(String member : memberMap.keySet()) {
			Player p_member = Bukkit.getPlayer(member);
			if(p_member != null) p_member.sendMessage(ChatColor.DARK_GREEN + getName() + ChatColor.GREEN + " > " + message);
		}
	}
	
	public void setStatus(Player player, Status status) {
		if(player == null) return;
		if(!memberMap.containsKey(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You are not a member of " + ChatColor.YELLOW + getName());
			return;
		}
		if(!getOwner().equalsIgnoreCase(player.getName())) {
			M.message(player, "Channels", ChatColor.WHITE + "You must be the " + ChatColor.YELLOW + "Owner " + ChatColor.WHITE + "to change the " + ChatColor.YELLOW + "Status");
			return;
		}
		if(getStatus() == status) {
			M.message(player, "Channels", ChatColor.YELLOW + getName() + "'s Status " + ChatColor.WHITE + "is already " + ChatColor.YELLOW + status.getName());
			return;
		}
		setStatus(status);
		sendMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " changed the " + ChatColor.DARK_GREEN + "Chat Status " + ChatColor.WHITE + "to " + ChatColor.DARK_GREEN + status.getName());
	}
	
	public void sendChannelInfo(Player player) {
		if(player == null) return;
		player.sendMessage(ChatColor.LIGHT_PURPLE + getName() + ChatColor.DARK_PURPLE + " > " + ChatColor.LIGHT_PURPLE + "Channel Info");
		player.sendMessage(ChatColor.YELLOW + "Members " + ChatColor.GREEN + getMembers().size() + ChatColor.YELLOW + " > " + getMembersFormatted());
		player.sendMessage(ChatColor.YELLOW + "Status > " + ChatColor.WHITE + getStatus().getName());
		player.sendMessage(ChatColor.YELLOW + "Creator > " + ChatColor.WHITE + getCreator());
		player.sendMessage(ChatColor.YELLOW + "Owner > " + ChatColor.WHITE + getOwner());
		player.sendMessage(ChatColor.YELLOW + "Age > " + ChatColor.WHITE + getAge());
		player.sendMessage(ChatColor.YELLOW + "Invited > " + ChatColor.GREEN + getInvites().size());
	}
	
	public static boolean getCanHaveSpecialCharacters() {
		return Main.config.getBoolean("channels.can_have_special_chars");
	}
	
	public static int getMaxMembers() {
		return Main.config.getInt("channels.max_channels_per_player");
	}
	
	public static int getMaxChannelsPerPlayer() {
		return Main.config.getInt("channels.max_channels_per_player");
	}
	
	public static int getMaxNameLength() {
		return Main.config.getInt("channels.max_name_length");
	}
	
	public static int getMinNameLength() {
		return Main.config.getInt("channels.min_name_length");
	}
	
	public static String getRawFormat() {
		return Main.config.getString("channels.format");
	}
	
	public String getFormat(String sender, String message) {
//		String format = getRawFormat();
//		System.out.println(format);
//		if(format == null) return null;
//		format.replaceAll("%channel_name%", getName()).replaceAll("%player_name%", sender).replaceAll("%message%", message);
//		format = ChatColor.translateAlternateColorCodes('&', format);
//		System.out.println(format);
		return ChatColor.DARK_GREEN + getName() + ChatColor.GREEN + " " + sender + ChatColor.DARK_GREEN + " > " + ChatColor.GREEN + message;
	}
	
	public enum Status {
		OPEN(1, "Open"),
		CLOSED(0, "Closed");
		
		private int id;
		private String name;
		
		Status(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public int getID() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public static Status getStatusFromID(int id) {
			if(id == 0) return CLOSED;
			else if(id == 1) return OPEN;
			return CLOSED;
		}
		
		public static Status getStatus(String status) {
			if(status == null) return Status.CLOSED;
			if(status.equalsIgnoreCase("closed")) return Status.CLOSED;
			if(status.equalsIgnoreCase("open")) return Status.OPEN;
			else return Status.CLOSED;
		}
	}
}