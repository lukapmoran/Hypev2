package me.loogeh.Hype.Sector;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MemberManager extends Sector {

	private HashMap<String, Member> serverMembers = new HashMap<String, Member>();

	public MemberManager() {
		super("Member");
		register();
	}
	
	public void add(Member member) {
		serverMembers.put(member.getUUID(), member);
	}
	
	public void remove(Member member) {
		serverMembers.remove(member.getUUID());
	}
	
	public void remove(String uuid) {
		serverMembers.remove(uuid);
	}
	
	public Member get(String uuid) {
		if(!serverMembers.containsKey(uuid)) return null;
		return serverMembers.get(uuid);
	}
	
	public Member get(Player player) {
		if(player == null) return null;
		return get(player.getUniqueId().toString());
	}
	
	public Member getByName(String name) {
		for(Member member : serverMembers.values()) {
			if(member.getName().equalsIgnoreCase(name)) return member;
		}
		return null;
	}

	public void load() {
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(get(player) == null) new Member(player.getName(), player.getUniqueId().toString());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Member member = Member.get(player);
		if(member != null) {
			if(!member.getSession().isTagged()) serverMembers.remove(member.getUUID());
		}
	}
}
