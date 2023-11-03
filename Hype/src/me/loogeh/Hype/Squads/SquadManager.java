package me.loogeh.Hype.Squads;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.FuzzySearch;
import me.loogeh.Hype.Squads.Squad.ClaimType;
import me.loogeh.Hype.Squads.Squad.Rank;
import me.loogeh.Hype.Utility.utilWorld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SquadManager {
	
	public static HashMap<String, Squad> squads = new HashMap<String, Squad>();
	public static HashMap<Long, String> powerRegen = new HashMap<Long, String>();
	public static HashMap<String, String> claims = new HashMap<String, String>(); //chunk - squad
	
	public static void save() {
		Main.logger.log(Level.INFO, "Sector > Saving sector 'squads'");
		long start = System.currentTimeMillis();
		int count = 0;
		for(Entry<String, Squad> entry : squads.entrySet()) {
			count++;
			String name = entry.getValue().getName();
			int power = entry.getValue().getPower();
			short admin = Squad.booleanToShort(entry.getValue().admin);
			String create_date = entry.getValue().getCreationDate();
			long create_time = entry.getValue().getCreationTime();
			long lastPower = 0L;
			int bank_balance = entry.getValue().getBankBalance();
			if(powerRegen.containsValue(entry.getValue().getName())) {
				for(Entry<Long, String>  regen_entry : powerRegen.entrySet()) {
					if(regen_entry.getValue().equalsIgnoreCase(entry.getValue().getName())) lastPower = regen_entry.getKey();
				}
			}
			ResultSet rs = Main.mysql.doQuery("SELECT * FROM squad WHERE squad='" + entry.getValue().getName() + "'");
			try {
				if(rs.next()) {
					Main.mysql.doUpdate("UPDATE `squad` SET `power`=" + power + ",`lastPower`=" + lastPower + " WHERE squad='" + entry.getValue().getName() + "'");
				} else {
					Main.mysql.doUpdate("INSERT INTO `squad`(`squad`, `power`, `admin`, `date`, `create_time`, `lastPower`) VALUES ('" + name + "','" + power + "','" + admin + "','" + create_date + "'," + create_time + "," + lastPower + ")");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for(Entry<String, ClaimType> claim_entry : entry.getValue().claimMap.entrySet()) {
				insertTerritory(name, claim_entry.getKey(), claim_entry.getValue());
			}
			for(Entry<String, Boolean> ally_entry : entry.getValue().allyMap.entrySet()) {
				ResultSet rs2 = Main.mysql.doQuery("SELECT * FROM squad_ally WHERE squad='" + entry.getValue().getName() + "' AND ally='" + ally_entry.getKey() + "'");
				try {
					if(rs2.next()) {
						boolean trust = rs2.getBoolean(3);
						if(ally_entry.getValue() != trust) Main.mysql.doUpdate("UPDATE `squad_ally` SET `trust`=" + Squad.booleanToShort(ally_entry.getValue()) + " WHERE squad='" + entry.getValue().getName() + "' AND ally='" + ally_entry.getKey() + "'");
					} else {
						Main.mysql.doUpdate("INSERT INTO `squad_ally`(`squad`, `ally`, `trust`) VALUES ('" + entry.getKey() + "','" + ally_entry.getKey() + "'," + Squad.booleanToShort(ally_entry.getValue()) + ")");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			for(Entry<String, Integer> enemy_entry :  entry.getValue().enemyMap.entrySet()) {
				ResultSet rs3 = Main.mysql.doQuery("SELECT * FROM squad_enemy WHERE squad='" + entry.getKey() + "' AND enemy='" + enemy_entry.getKey() + "'");
				try {
					if(!rs3.next()) {
						Main.mysql.doUpdate("INSERT INTO `squad_enemy`(`squad`, `enemy`) VALUES ('" + entry.getKey() + "','" + enemy_entry.getKey() + "')");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Location home = entry.getValue().getHome();
			ResultSet rs4 = Main.mysql.doQuery("SELECT * FROM squad_home WHERE squad='" + entry.getKey() + "'");
			try {
				if(rs4.next()) {
					if(home != null) Main.mysql.doUpdate("UPDATE `squad_home` SET `x`=" + home.getX() + ",`y`=" + home.getY() + ",`z`=" + home.getZ() + ",`yaw`=" + home.getYaw() + ",`world`='" + home.getWorld().getName() + "' WHERE squad='" + entry.getKey() + "'");
				} else {
					if(home != null) Main.mysql.doUpdate("INSERT INTO `squad_home`(`squad`, `x`, `y`, `z`, `yaw`, `world`) VALUES ('" + entry.getKey() + "'," + home.getX() + "," + home.getY() + "," + home.getZ() + "," + home.getYaw() + ",'" + home.getWorld().getName() + "')");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ResultSet rs5 = Main.mysql.doQuery("SELECT * FROM squad_bank WHERE squad='" + entry.getKey() + "'");
			try {
				if(!rs5.next()) {
					Main.mysql.doUpdate("INSERT INTO `squad_bank`(`squad`, `balance`, `withdraw_limit`) VALUES ('" + entry.getKey() + "'," + bank_balance + "," + Squad.getHourWithdrawLimit() + ")");
				} else {
					Main.mysql.doUpdate("UPDATE `squad_bank` SET balance=" + bank_balance + ", withdraw_limit=" + Squad.getHourWithdrawLimit());
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		Main.logger.log(Level.INFO, "Sector > Saved sector 'squads' in " + (System.currentTimeMillis() - start) + "ms (" + count + ")");
	}
	
	public static void load() {
		Main.logger.log(Level.INFO, "Sector > Loading sector 'squads'");
		int count = 0;
		long start = System.currentTimeMillis();
		try {
			ResultSet rs = Main.mysql.doQuery("SELECT squad,power,admin,date,create_time,lastPower FROM squad");
			while(rs.next()) {
				count++;
				Squad squad = new Squad(rs.getString(1), rs.getInt(2), rs.getBoolean(3), rs.getString(4), rs.getLong(5));
				ResultSet rs0 = Main.mysql.doQuery("SELECT player,role,squad FROM squad_player WHERE squad='" + squad.getName() + "'");
				while(rs0.next()) {
					String player = rs0.getString(1);
					String role = rs0.getString(2);
					Rank rank = Rank.getRank(role);
					if(rank != null) squad.memberMap.put(player, rank);
					powerRegen.put(rs.getLong(5), squad.getName());
				}
				ResultSet rs1 = Main.mysql.doQuery("SELECT squad,enemy FROM squad_enemy WHERE squad='" + squad.getName() + "'");
				while(rs1.next()) {
					String enemy = rs1.getString(2);
					ResultSet dom_rs = Main.mysql.doQuery("SELECT amount FROM squad_dominance WHERE squad='" + squad.getName() + "' AND target='" + enemy + "'");
					if(dom_rs.next()) {
						squad.enemyMap.put(enemy, dom_rs.getInt(1));
					}
				}
				ResultSet rs2 = Main.mysql.doQuery("SELECT squad,ally,trust FROM squad_ally WHERE squad='" + squad.getName() + "'");
				while(rs2.next()) {
					String ally = rs2.getString(2);
					boolean trust = rs2.getBoolean(3);
					squad.allyMap.put(ally, trust);
				}
				ResultSet rs3 = Main.mysql.doQuery("SELECT squad,chunk,type FROM squad_claims WHERE squad='" + squad.getName() + "'");
				while(rs3.next()) {
					String chunk = rs3.getString(2);
					ClaimType type = ClaimType.getType(rs3.getString(3)); 
					squad.claimMap.put(chunk, type);
					claims.put(chunk, squad.getName());
				}
				ResultSet rs4 = Main.mysql.doQuery("SELECT squad,x,y,z,yaw,world FROM squad_home WHERE squad='" + squad.getName() + "'");
				if(rs4.next()) {
					double x = rs4.getDouble(2);
					double y = rs4.getDouble(3);
					double z = rs4.getDouble(4);
					float yaw = rs4.getFloat(5);
					World world =  Bukkit.getWorld(rs4.getString(6));
					Location loc = new Location(world, x, y, z, yaw, 0.0F);
					squad.home = loc;
				}
				squads.put(squad.getName(), squad);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.INFO, "Sector > SQL Error while loading sector 'squads'");
			Main.logger.log(Level.WARNING, "Sector > Shutting down server due to load fail in 'squads'");
			Bukkit.shutdown();
		}
		Main.logger.log(Level.INFO, "Sector > Loaded sector 'squads' in " + (System.currentTimeMillis() - start) + "ms (" + count + ")");
	}
	
	public static void insertTerritory(String squad, String chunk, ClaimType type) {
		ResultSet rs = Main.mysql.doQuery("SELECT chunk FROM squad_claims WHERE chunk='" + chunk + "'");
		try {
			if(rs.next()) {
				return;
			} else {
				Main.mysql.doUpdate("INSERT INTO squad_claims (squad,chunk,type) VALUES ('" + squad + "','" + chunk + "','" + type.toString().toLowerCase() + "')");
				Main.logger.log(Level.INFO, "Squads > Saved Chunk " + chunk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Sector > 'squads' Failed to insert claim '" + chunk + "' into database");
		}
	}
	
	public static boolean hasSquad(String name) {
		for(Entry<String, Squad> entry : squads.entrySet()) {
			if(entry.getValue().memberMap.containsKey(name)) return true;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static Squad getSquad(String name) {
		if(!squads.containsKey(name)) {
			Player player = Bukkit.getPlayer(name);
			if(player != null) {
				Squad p_squad = getSquad(player);
				if(p_squad != null) return p_squad;
			} else {
				ResultSet rs = Main.mysql.doQuery("SELECT squad FROM squad WHERE squad='" + name + "'");
				try {
					if(rs.next()) return squads.get(rs.getString(1));
				} catch (SQLException e) {
					System.out.println("Sector > 'squads' Failed to retrieve squad '" + name + "' from database");
				}
			}
		}
		else return squads.get(name);
		return null;
	}
	
	public static Squad getSquad(Player player) {
		String p = player.getName();
		for(Entry<String, Squad> entry : squads.entrySet()) {
			if(entry.getValue().memberMap.containsKey(p)) return entry.getValue();
		}
		return null;
	}
	
	public static Squad getSquadFromPlayer(String name) {
		for(Entry<String, Squad> entry : squads.entrySet()) {
			if(entry.getValue().memberMap.containsKey(name)) return entry.getValue();
		}
		return null;
	}
	
	public static boolean isClaimed(Chunk chunk) {
		String chunk_str = utilWorld.chunkToStr(chunk);
		return claims.containsKey(chunk_str);
	}
	
	public static Squad getOwner(Chunk chunk) {
		String chunk_str = utilWorld.chunkToStr(chunk);
		if(claims.containsKey(chunk_str)) return getSquad(claims.get(chunk_str));
		else return null;
	}
	
	public static String searchPlayer(Squad squad, String token) {
		if(squad == null) return null;
		String id = FuzzySearch.nextID();
		String[] list = new String[squad.memberMap.size()];
		int count = 0;
		for(String member : squad.memberMap.keySet()) {
			list[count] = member;
			count++;
		}
		FuzzySearch.addSearcher(id, list);
		String closest = FuzzySearch.getClosestWord2(id, token);
		return closest;
	}
	
	public static Squad searchPlayer(String token) {
		Squad squad = null;
		for(String loop : squads.keySet()) {
			squad = squads.get(loop);
			if(squad.memberMap.containsKey(token)) return squad;
			String id = FuzzySearch.nextID();
			String[] list = new String[squad.memberMap.size()];
			int count = 0;
			for(String member : squad.memberMap.keySet()) {
				list[count] = member;
				count++;
			}
			FuzzySearch.addSearcher(id, list);
			String closest = FuzzySearch.getClosestWord2(id, token);	
			if(squad.memberMap.containsKey(closest)) return squad;
		}
		return squad;
	}
	
	public static Squad search(String token) {
		return getSquad(token);
	}
	
	public static ClaimType getClaimType(Chunk chunk) {
		String chunk_str = utilWorld.chunkToStr(chunk);
		for(Entry<String, Squad> entry : squads.entrySet()) {
			if(entry.getValue().claimMap.containsKey(chunk_str)) return entry.getValue().claimMap.get(chunk_str);
		}
		return null;
	}

	public static void startPowerScheduler() {
		Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			
			public void run() {
				Iterator<Long> it = powerRegen.keySet().iterator();
				while(it.hasNext()) {
					long systime = it.next();
					long regen = Squad.getPowerRegenTime();
					if(System.currentTimeMillis() - systime > regen) {
						Squad squad = getSquad(powerRegen.get(systime));
						if(squad != null) {
							if(squad.getPower() >= squad.getMaxPower()) {
								it.remove();
								return;
							}
							squad.alterPower(1);
							squad.sendMessage(ChatColor.WHITE + "Your squad has regenerated " + ChatColor.YELLOW + "1 power");
						} else {
							it.remove();
						}
					}
				}
			}
		}, 600L, 600L);
	}
	
	public static long getNextAvailableMillis(long start) {
		long next = start;
		if(!powerRegen.containsKey(next)) return next;
		for(int i = 1; i < 1000; i++) {
			next = next + i;
			if(!powerRegen.containsKey(next)) break;
		}
		return next;
	}
}
