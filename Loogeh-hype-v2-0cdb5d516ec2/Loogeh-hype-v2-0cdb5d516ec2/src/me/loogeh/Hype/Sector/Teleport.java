package me.loogeh.Hype.Sector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Member.mPerms.Rank;
import me.loogeh.Hype.Moderation.Permissions;
import me.loogeh.Hype.Utility.util;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;
import me.loogeh.Hype.Utility.utilWorld;

@SuppressWarnings("deprecation")
public class Teleport extends Sector implements CommandExecutor {

	private static Teleport instance;
	
	public HashMap<String, Warp> warpMap = new HashMap<String, Warp>();
	
	public Teleport() {
		super("Teleport");
		List<String> commandList = new ArrayList<String>();
		commandList.add("tp");
		commandList.add("warp");
		for(String command : commandList) {
			getPlugin().getCommand(command).setExecutor(this);
		}
		instance = this;
		load();
	}
	
	public static Teleport get() {
		if(instance == null) instance = new Teleport();
		return instance;
	}
	
	public void teleport(Player player, String target) {
		if(player == null) return;
		Player p_target = Bukkit.getPlayer(target);
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return;
		}
		if(p_target == null) {
			M.message(player, "Teleport", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target);
			return;
		}
		if(player.getName().equalsIgnoreCase(p_target.getName())) {
			M.message(player, "Teleport", ChatColor.WHITE + "You cannot teleport to yourself");
			return;
		}
		Location location = p_target.getLocation();
		player.teleport(location);
		M.message(player, "Teleport", ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + p_target.getName());
		M.message(p_target, "Teleport", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " teleported to you");
		member.getSession().addTeleportHistory(location);
	}
	
	public void teleport(Player player, String target, String target_2) {
		if(player == null) return;
		if(!Permissions.isAdmin(player.getName())) {
			M.sendLackPermsMessage(player);
			return;
		}
		Player p_target = Bukkit.getPlayer(target);
		Player p_target_2 = Bukkit.getPlayer(target_2);
		if(p_target == null) {
			M.message(player, "Teleport", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target);
			return;
		}
		if(p_target_2 == null) {
			M.message(player, "Teleport", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target_2);
			return;
		}
		if(p_target.getName().equalsIgnoreCase(p_target_2.getName())) {
			M.message(player, "Teleport", ChatColor.WHITE + "You cannot teleport players to themselves");
			return;
		}
		Location location = p_target_2.getLocation();
		p_target.teleport(location);
		M.message(player, "Teleport", ChatColor.WHITE + "You teleported " + ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " to " + ChatColor.YELLOW + p_target_2.getName());
		M.message(p_target, "Teleport", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " teleported you to " + ChatColor.YELLOW + p_target_2.getName());
		M.message(p_target, "Teleport", ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " was teleported to you by " + ChatColor.YELLOW + player.getName());
		Member member = Member.get(p_target_2);
		if(member != null) member.getSession().addTeleportHistory(location);
	}
	
	public void teleportHistory(Member member, int index) {
		if(member == null) return;
		Player player = member.getPlayer();
		if(player == null) return;
		if(index < 1) {
			message(player, ChatColor.WHITE + "Index must be positive");
		}
		index--;
		Location location = member.getSession().getTeleport(index);
		if(location == null) {
			message(player, ChatColor.WHITE + "Failed to find location for index " + ChatColor.YELLOW + (index + 1));
			return;
		}
		player.teleport(location);
		message(player, ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + "history index " + (index + 1));
	}
	
	public void teleport(Player player, int x, int y, int z) {
		if(player == null) return;
		Member member = Member.get(player);
		message(player, ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + x + "," + y + "," + z);
		Location location = new Location(player.getWorld(), x, y, z);
		player.teleport(location);
		if(member != null) member.getSession().addTeleportHistory(location);
	}
	
	public void teleport(Player player, World world, int x, int y, int z) {
		if(player == null) return;
		Member member = Member.get(player);
		message(player, ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + x + "," + y + "," + z + ChatColor.WHITE + " in world " + ChatColor.YELLOW + world.getName());
		Location location = new Location(world, x, y, z);
		player.teleport(location);
		if(member != null) member.getSession().addTeleportHistory(location);
	}
	
	public void teleportSpawn(Player player) {
		if(player == null) return;
		Member member = Member.get(player);
		String world = "";
		if(player.getWorld().getName().contains("nether")) world = "Nether";
		else if(player.getWorld().getName().contains("end")) world = "End";
		else world = "World";
		player.teleport(player.getWorld().getSpawnLocation());
		message(player, ChatColor.WHITE + "You teleported to the spawn of " + ChatColor.YELLOW + world);
		if(member != null) member.getSession().addTeleportHistory(player.getWorld().getSpawnLocation());
	}
	
	public void teleportHere(Player player, String target) {
		if(player == null) return;
		Player p_target = Bukkit.getPlayer(target);
		if(p_target == null) {
			message(player, ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + target);
			return;
		}
		Member member = Member.get(p_target);
		p_target.teleport(player);
		message(player,  ChatColor.WHITE + "You teleported " + ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " here");
		message(p_target, ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " teleported you to himself");
		if(member != null) member.getSession().addTeleportHistory(player.getLocation());
		return;
	}
	
	public void teleportBack(Member member) {
		if(member == null) return;
		Player player = member.getPlayer();
		if(player == null) return;
		if(member.getSession().getLastDeathLocation() == null) {
			message(player, ChatColor.WHITE + "You don't have a location to go back to");
			return;
		}
		player.teleport(member.getSession().getLastDeathLocation());
		message(player, ChatColor.WHITE + "You teleported to your last " + ChatColor.YELLOW + "Death Location");
		member.getSession().addTeleportHistory(member.getSession().getLastDeathLocation());
	}
	
	public void teleportWorld(Player player, String world_name) {
		if(player == null) return;
		Member member = Member.get(player);
		World world = Bukkit.getWorld(world_name);
		if(world == null) {
			message(player, ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + world_name);
			return;
		}
		Location location = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ());
		player.teleport(location);
		message(player, ChatColor.WHITE + "You teleported to the spawn of " + ChatColor.YELLOW + world.getName());
		if(member != null) member.getSession().addTeleportHistory(location);
	}

	public void load() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS server_warps (name VARCHAR(35), id INTEGER(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, location VARCHAR(50), creator VARCHAR(16), creationTime BIGINT(20))");
		
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM server_warps");
		
		int count = 0;
		try {
			while(rs.next()) {
				String name = rs.getString(1);
				int id = rs.getInt(2);
				Location location = utilWorld.strToLoc(rs.getString(3));
				String creator = rs.getString(4);
				long creationTime = rs.getLong(5);
				warpMap.put(name.toLowerCase(), new Warp(name, id, location, creator, creationTime));
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "Sector > 'Teleport' failed to load warps");
		}
		Main.logger.log(Level.INFO, "Sector > 'Teleport' loaded " + count + " warps");
	}
	
	
	static class Warp {
		
		private String name;
		private int id;
		private Location location;
		private String creator;
		private long creationTime;
		
		public Warp(String name, int id, Location location, String creator, long creationTime) {
			this.name = name;
			this.id = id;
			this.location = location;
			this.creator = creator;
			this.creationTime = creationTime;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getID() {
			return this.id;
		}
		
		public void setID(int id) {
			this.id = id;
		}
		
		public Location getLocation() {
			return this.location;
		}
		
		public void setLocation(Location location) {
			this.location = location;
		}
		
		public String getCreator() {
			return this.creator;
		}
		
		public long getCreationTime() {
			return this.creationTime;
		}
		
		public void save() {
			String location = utilWorld.locToStr(getLocation());
			ResultSet rs = Main.mysql.doQuery("SELECT * FROM server_warps WHERE location='" + location + "'");
			try {
				if(rs.next()) {
					if(rs.getString(3).equalsIgnoreCase(location)) return;
					Main.mysql.doUpdate("UPDATE server_warps SET location='" + location + "' WHERE name='" + getName() + "'");
				} else {
					Main.mysql.doUpdate("INSERT INTO `server_warps`(`name`, `id`, `location`, `creator`, `creationTime`) VALUES ('" + getName() + "', id, '" + location + "', '" + getCreator() + "', " + getCreationTime() + ")");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public int getDatabaseID() {
			ResultSet rs = Main.mysql.doQuery("SELECT id FROM server_warps WHERE name='" + getName() + "'");
			try {
				if(rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
		}
		
	}
	
	public void help(Player player) {
		message(player, ChatColor.WHITE + "Commands " + ChatColor.GRAY + "(optional) <required>");
		M.sendHelpMessage(player, "/teleport <player>", "Teleports you to a player");
		M.sendHelpMessage(player, "/teleport <player> <player>", "Teleports a player to another player");
		M.sendHelpMessage(player, "/teleport <h/here> <player>", "Teleports a player to you");
		M.sendHelpMessage(player, "/teleport history <index>", "Teleports to a location in your tp history");
		M.sendHelpMessage(player, "/teleport spawn", "Teleports you to the world's spawn");
		M.sendHelpMessage(player, "/teleport back", "Teleports you to your last death location");
		M.sendHelpMessage(player, "/teleport x, y, z", "Teleports you to coordinates");
		M.sendHelpMessage(player, "/teleport <world> x, y, z", "Teleports you to coordinates");
		M.sendHelpMessage(player, "/warp <warp>", "Warps you to a location");
		M.sendHelpMessage(player, "/warp [create/remove] <warp>", "Manages warps");
		M.sendHelpMessage(player, "/warp info <warp>", "Retrieves warp information");
	}


	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Teleport - " + ChatColor.WHITE + "Console cannot use teleport commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return true;
		}
		if(!member.getPermissions().is(Rank.ADMIN)) {
			System.out.println(member.getPermissions().getRank().getName());
			M.sendLackPermsMessage(player);
			return true;
		}
		if(commandLabel.equalsIgnoreCase("tp")) {
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("back")) {
					teleportBack(member);
					return true;
				}
				if(args[0].equalsIgnoreCase("spawn")) {
					teleportSpawn(player);
					return true;
				}
				teleport(player, args[0]);
				return true;
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("here") || args[0].equalsIgnoreCase("h")) {
					teleportHere(player, args[1]);
					return true;
				}
				if(args[0].equalsIgnoreCase("history")) {
					Integer index = util.getInteger(args[1]);
					if(index == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Index");
						return true;
					}
					teleportHistory(member, index);
					return true;
				}
				Player teleported = Bukkit.getPlayer(args[0]);
				if(teleported == null) {
					message(player, ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
					return true;
				}
				teleport(teleported, args[1]);
				return true;
			}
			if(args.length == 3) {
				Integer x = util.getInteger(args[0]);
				Integer y = util.getInteger(args[1]);
				Integer z = util.getInteger(args[2]);
				if(x == null || y == null || z == null) {
					message(player, ChatColor.WHITE + "You must enter valid " + ChatColor.YELLOW + "coordinates");
					return true;
				}
				Location location = new Location(player.getWorld(), x, y, z);
				player.teleport(location);
				message(player, ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + x + ", " + y + ", " + z);
				member.getSession().addTeleportHistory(location);
				return true;
			}
			if(args.length == 4) {
				World world = Bukkit.getWorld(args[0]);
				if(world == null) {
					message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "world");
					return true;
				}
				Integer x = util.getInteger(args[1]);
				Integer y = util.getInteger(args[2]);
				Integer z = util.getInteger(args[3]);
				if(x == null || y == null || z == null) {
					message(player, ChatColor.WHITE + "You must enter valid " + ChatColor.YELLOW + "coordinates");
					return true;
				}
				Location location = new Location(world, x, y, z);
				player.teleport(location);
				message(player, ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + x + ", " + y + ", " + z + ChatColor.WHITE + " in " + ChatColor.YELLOW + world.getName());
				member.getSession().addTeleportHistory(location);
				return true;
			}
		}
		if(commandLabel.equalsIgnoreCase("warp")) {
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(args.length == 1) {
				String result = util.search(args[0], warpMap.keySet());
				if(result == null) {
					message(player, ChatColor.WHITE + "Failed to find warp with name " + ChatColor.YELLOW + args[0]);
					return true;
				}
				Warp warp = warpMap.get(result);
				if(warp == null) {
					message(player, ChatColor.WHITE + "Failed to find warp with name " + ChatColor.YELLOW + args[0]);
					return true;
				}
				
				player.teleport(warp.getLocation());
				message(player, ChatColor.WHITE + "You warped to " + ChatColor.YELLOW + warp.getName());
				member.getSession().addTeleportHistory(warp.getLocation());
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("create")) {
					if(warpMap.containsKey(args[1])) {
						message(player, ChatColor.YELLOW + args[1] + ChatColor.WHITE + " already exists");
						return true;
					}
					Warp newWarp = new Warp(args[1], 0, player.getLocation(), player.getName(), System.currentTimeMillis());
					newWarp.save();
					int id = newWarp.getDatabaseID();
					if(id == -1) return true;
					newWarp.setID(id);
					warpMap.put(args[1].toLowerCase(), newWarp);
					message(player, ChatColor.WHITE + "You created a new warp " + ChatColor.YELLOW + args[1]);
					return true;
				}
				if(args[0].equalsIgnoreCase("remove")) {
					if(!warpMap.containsKey(args[1].toLowerCase())) {
						message(player, ChatColor.YELLOW + args[1] + ChatColor.WHITE + " doesn't exist");
						return true;
					}
					Warp warp = warpMap.get(args[1].toLowerCase());
					Main.mysql.doUpdate("DELETE FROM server_warps WHERE name='" + args[1] + "'");
					warpMap.remove(args[1].toLowerCase());
					message(player, ChatColor.WHITE + "You deleted warp " + ChatColor.YELLOW + warp.getName());
					return true;
				}
				if(args[0].equalsIgnoreCase("info")) {
					if(!warpMap.containsKey(args[1].toLowerCase())) {
						message(player, ChatColor.YELLOW + args[1] + ChatColor.WHITE + " doesn't exist");
						return true;
					}
					Warp warp = warpMap.get(args[1].toLowerCase());
					player.sendMessage(ChatColor.GRAY + warp.getName() + "'s Info");
					player.sendMessage(ChatColor.GRAY + "Name > " + ChatColor.GREEN + warp.getName());
					player.sendMessage(ChatColor.GRAY + "Location > " + ChatColor.GREEN + utilWorld.locToStr(warp.getLocation()));
					player.sendMessage(ChatColor.GRAY + "Creator > " + ChatColor.GREEN + warp.getCreator());
					player.sendMessage(ChatColor.GRAY + "Age > " + ChatColor.GREEN + utilTime.convertString(System.currentTimeMillis() - warp.getCreationTime(), TimeUnit.BEST, 1));
				}
			}
		}
		return false;
	}

}
