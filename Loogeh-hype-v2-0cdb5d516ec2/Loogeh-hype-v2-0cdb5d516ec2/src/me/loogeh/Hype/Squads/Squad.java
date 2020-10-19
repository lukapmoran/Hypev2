package me.loogeh.Hype.Squads;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.loogeh.Hype.ChatChannels.Channel;
import me.loogeh.Hype.ChatChannels.ChannelManager;
import me.loogeh.Hype.Event.SquadRelationChangeEvent;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Server.ChatManager;
import me.loogeh.Hype.Utility.utilMath;
import me.loogeh.Hype.Utility.utilTime;
import me.loogeh.Hype.Utility.utilTime.TimeUnit;
import me.loogeh.Hype.Utility.utilWorld;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

@SuppressWarnings("deprecation")
public class Squad {

	public Main plugin = Main.plugin;

	public String name = null; //name of squad
	public Location home = null; //home of squad
	public String create_date = utilTime.timeStr(); //date of creation
	public int power = 0; // current power
	public long lastPowerLost = -1; //last power lost
	public long create_time = System.currentTimeMillis(); //time of creation (in milliseconds)
	public long lastOnline = System.currentTimeMillis(); //last time a member of the squad was online (in milliseconds)
	public boolean admin = false; //admin status of the squad
	public int bank_balance = 0; // balance of the squad's bank
	public int bank_withdraw_limit = 80000; //max amount of money a player can withdraw from the squad bank in 1 hour

	public HashMap<String, Boolean> allyMap = new HashMap<String, Boolean>(); //allies' name and boolean representing trust
	public HashMap<String, Integer> enemyMap = new HashMap<String, Integer>(); //enemy squads' name and boolean for pointless value - HashMap for HashMap.get(), containsKey() and containsValue() methods
	public HashMap<String, Rank> memberMap = new HashMap<String, Rank>(); //member and their rank within the squad
	public HashMap<String, ClaimType> claimMap = new HashMap<String, ClaimType>(); // claimed chunk, claimType
	public HashMap<String, Boolean> squadChat = new HashMap<String, Boolean>(); // squad chat channel

	public HashMap<String, Long> inviteeMap = new HashMap<String, Long>(); //player invited, systime of when they were invited (to add expiration time)
	public HashMap<String, Long> allyRequest = new HashMap<String, Long>(); //squads' name and time of request
	public HashMap<String, Long> neutralRequest = new HashMap<String, Long>(); //squads' name and time of request

	public static HashSet<Integer> denyInteract = new HashSet<Integer>();
	public static HashSet<Integer> allowInteract = new HashSet<Integer>();
	public static HashSet<Integer> denyPlace = new HashSet<Integer>();
	public static HashSet<Integer> allowPlace = new HashSet<Integer>();
	public static HashSet<Integer> allowBreak = new HashSet<Integer>();
	public static HashSet<Integer> interactableBlocks = new HashSet<Integer>();
	public static HashSet<Integer> trustInteractableBlocks = new HashSet<Integer>();
	public static HashSet<String> homeMove = new HashSet<String>();
	public static HashSet<String> adminMode = new HashSet<String>();

	public static HashMap<String, String> adminActionConfirm = new HashMap<String, String>();

	public static String[] commands = {"create", "claim", "unclaim", "disband", "join", "leave", "kick", "demote", "promote", "invite", "home", "list", "delete", "i", "admin", "tp", "ally", "trust", "enemy", "view", "display", "v", "set", "chat", "load", "save", "neutral", "search", "deinvite", "uninvite", "untrust", "revoketrust", "about"};
	public static String[] two_arg = {"join", "kick", "demote", "promote", "leader", "ally", "enemy", "trust", "neutral", "invite", "untrust", "revoketrust"};

	public Squad(String name, String creator, boolean admin) {
		this.name = name;
		this.admin = admin;
		this.power = getStartingPower() + getPowerPerPlayer();
		memberMap.put(creator, Rank.LEADER);
		squadChat.put(creator, false);
		SquadManager.squads.put(name, this);
	}

	public Squad(String name, int power, boolean admin, String create_date, long create_time) {
		this.name = name;
		this.admin = admin;
		this.power = power;
		this.create_date = create_date;
		this.create_time = create_time;
	}

	public String getName() {
		return this.name;
	}

	public Location getHome() {
		return this.home;
	}

	public String getCreationDate() {
		return this.create_date;
	}

	public int getPower() {
		if(admin) return 10000;
		return this.power;
	}

	public int getMaxPower() {
		if(admin) return getMaxAdminPower();
		else return getStartingPower() + (getPowerPerPlayer() * getMemberCount());
	}

	public static int getStartingPower() {
		return Main.config.getInt("squads.starting_power");
	}

	public static int getPowerPerPlayer() {
		return Main.config.getInt("squads.power_per_player");
	}

	public long getLastPowerLost() {
		return this.lastPowerLost;
	}

	public long getCreationTime() {
		return this.create_time;
	}

	public long getLastOnline() {
		return this.lastOnline;
	}

	public int getAllyCount() {
		return allyMap.size();
	}

	public int getEnemyCount() {
		return enemyMap.size();
	}

	public int getBankBalance() {
		return this.bank_balance;
	}
	
	public int getBankWithdrawLimit() {
		return this.bank_withdraw_limit;
	}
	
	public int getMaxAllies() {
		if(admin) return getMaxAdminAllies();
		int max = getStartingAllies() - getMemberCount();
		if(max < 0) max = 0;
		if(max > getAbsoluteMaxAllies()) max = getAbsoluteMaxAllies();
		return max;
	}

	public static int getStartingAllies() {
		return Main.config.getInt("squads.starting_allies");
	}

	public int getLand() {
		return claimMap.size();
	}

	public int getMaxLand() {
		if(admin) return getMaxAdminLand();
		int starting_land = getStartingLand();
		if(starting_land < 0) starting_land = 0;
		int max = starting_land + (getMemberCount() * getLandPerPlayer());
		if(max > getAbsoluteMaxLand()) return getAbsoluteMaxLand();
		else return max;
	}

	public static int getStartingLand() {
		return Main.config.getInt("squads.starting_land");
	}

	public static int getAbsoluteMaxLand() {
		return Main.config.getInt("squads.max_land");
	}

	public static int getMaxEnemies(Squad squad) {
		if(squad.admin) return getMaxAdminEnemies();
		return Main.config.getInt("squads.max_enemies");
	}

	public static int getAbsoluteMaxAllies() {
		return Main.config.getInt("squads.max_allies");
	}

	public static int getLandPerPlayer() {
		return Main.config.getInt("squads.land_per_player");
	}

	public int getMemberCount() {
		return memberMap.size();
	}

	public long getAge() {
		return System.currentTimeMillis() - this.create_time;
	}

	public String getAge(TimeUnit unit) {
		return utilTime.convertString(getAge(), unit, 1);
	}

	public boolean areAlly(String name) {
		Squad target = SquadManager.getSquad(name);
		if(target == null) return false;
		if(allyMap.containsKey(name) && target.allyMap.containsKey(getName())) return true;
		if(allyMap.containsKey(name) && !target.allyMap.containsKey(getName())) {
			allyMap.remove(name);
			return false;
		}
		if(!allyMap.containsKey(name) && target.allyMap.containsKey(getName())) {
			target.allyMap.remove(getName());
			return false;
		}
		return false;
	}
	
	public boolean areAlly(Squad squad) {
		if(squad == null) return false;
		if((allyMap.containsKey(squad.getName())) && squad.allyMap.containsKey(getName())) return true;
		if(allyMap.containsKey(squad.getName()) && !squad.allyMap.containsKey(getName())) {
			allyMap.remove(squad.getName());
			return false;
		}
		if(!allyMap.containsKey(squad.getName()) && squad.allyMap.containsKey(getName())) {
			squad.allyMap.remove(squad.getName());
			return false;
		}
		return false;
	}

	public boolean areEnemy(String name) {
		Squad target = SquadManager.getSquad(name);
		if(target == null) return false;
		if(enemyMap.containsKey(name) && target.enemyMap.containsKey(getName())) return true;
		else return false;
	}
	
	public boolean areEnemy(Squad squad) {
		if(squad == null) return false;
		if(enemyMap.containsKey(squad.getName()) && squad.enemyMap.containsKey(getName())) return true;
		else return false;
	}

	public boolean bothTrust(String name) {
		Squad target = SquadManager.getSquad(name);
		if(target == null) return false;
		if(areAlly(name) && target.allyMap.get(getName()) == true && allyMap.get(name) == true) return true;
		else return false;
	}
	
	public boolean bothTrust(Squad squad) {
		if(squad == null) return false;
		if(areAlly(squad) && squad.allyMap.get(getName()) == true && allyMap.get(squad.getName()) == true) return true;
		else return false;
	}

	public boolean getTrust(String name) {
		Squad target = SquadManager.getSquad(name);
		if(target == null) return false;
		if(!areAlly(name)) return false;
		if(target.allyMap.get(getName()) == true) return true;
		return false;
	}
	
	public boolean getTrust(Squad squad) {
		if(squad == null) return false;
		if(!areAlly(squad)) return false;
		if(squad.allyMap.get(getName()) == true) return true;
		else return false;
	}

	public long getTimeBeforePowerRegen() {
		if(lastPowerLost == (Long) null) return (Long) null;
		return (System.currentTimeMillis() - lastPowerLost) + getPowerRegenTime();
	}

	public static long getPowerRegenTime() {
		return (long) Main.config.getLong("squads.power_regeneration_time");
	}

	public Rank getRank(String player) {
		if(!memberMap.containsKey(player)) return null;
		else return memberMap.get(player);
	}

	public String getMembersFormatted() {
		String members = "";
		for(Entry<String, Rank> entry : memberMap.entrySet()) {
			String p = entry.getKey();
			Player player = Bukkit.getPlayer(p);
			Rank rank = entry.getValue();
			String prefix = "";
			if(rank == Rank.LEADER) prefix = ChatColor.YELLOW + "L:";
			else if(rank == Rank.ADMIN) prefix = ChatColor.YELLOW + "A:";
			if(player != null) members = members + prefix + ChatColor.GREEN + p + ", ";
			else members = members + prefix + ChatColor.RED + p + ", ";
		}
		if(members.length() == 0) return members;
		else return members.substring(0, members.length() - 2);
	}

	public String getAlliesFormatted() {
		String allies = "";
		for(Entry<String, Boolean> entry : allyMap.entrySet()) {
			String ally = entry.getKey();
			boolean trust = entry.getValue();
			if(trust) allies = allies + ChatColor.DARK_GREEN + ally + ", ";
			else allies = allies + ChatColor.GREEN + ally + ", ";
		}
		if(allies.length() == 0) return allies;
		else return allies.substring(0, allies.length() - 2);
	}

	public String getEnemiesFormatted() {
		String enemies = "";
		for(String enemy : enemyMap.keySet()) {
			enemies = enemies + ChatColor.RED + enemy + ", ";
		}
		if(enemies.length() == 0) return enemies;
		else return enemies.substring(0, enemies.length() - 2);
	}

	public ChatColor getRelationColour(String name) {
		Squad squad = SquadManager.getSquad(name);
		if(squad == null) return ChatColor.WHITE;
		Relation relation = getRelation(squad);
		switch (relation) {
			case SELF: return ChatColor.AQUA;
			case ALLY: return ChatColor.GREEN;
			case TRUST: return ChatColor.DARK_GREEN;
			case ENEMY: return ChatColor.RED;
			default: return ChatColor.WHITE;
		}
	}

	public static int getWandID() {
		return Main.config.getInt("squads.wand_id");
	}



	public boolean isSquadChat(String name) {
		if(!squadChat.containsKey(name)) return false;
		if(squadChat.get(name) == true) return true;
		else return false;
	}

	public boolean setHome(Player player) {
		if(player ==  null) return false;
		if(this.home != null) return false;
		Location location = player.getLocation();
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be atleast " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to set home");
			return false;
		}
		if(SquadManager.isClaimed(location.getChunk())) {
			if(!SquadManager.getOwner(location.getChunk()).getName().equalsIgnoreCase(getName())) {
				M.message(player, "Squads", ChatColor.WHITE + "You can only set home in your own territory");
				return false;
			}
			if(hasHome()) Main.mysql.doUpdate("UPDATE `squad_home` SET `x`=" + location.getX() + ",`y`=" + location.getY() + ",`z`=" + location.getZ() + ",`yaw`=" + location.getYaw() + ",`world`='" + location.getWorld().getName() + "' WHERE squad='" + getName() + "'");
			else Main.mysql.doUpdate("INSERT INTO `squad_home`(`squad`, `x`, `y`, `z`, `yaw`, `world`) VALUES ('" + getName() + "', " + utilMath.trim(location.getX(), 2) + ", " + utilMath.trim(location.getY(), 2) + ", " + utilMath.trim(location.getZ(), 2) + ", " + utilMath.trim(location.getYaw(), 2) + ", '" + location.getWorld().getName() + "')");
			this.home = location;
			return true;
		}
		if(!SquadManager.isClaimed(location.getChunk())) {
			M.message(player, "Squads", ChatColor.WHITE + "You can only set home in your own territory");
			return false;
		}
		return true;
	}

	public boolean hasHome() {
		ResultSet rs = Main.mysql.doQuery("SELECT squad FROM squad_home WHERE squad='" + getName() + "'");
		try {
			if(rs.next()) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setPower(int newPower) {
		this.power = newPower;
	}

	public void alterPower(int change) {
		this.power += change;
	}

	public void setLastPowerLost(long millis) {
		Main.mysql.doUpdate("UPDATE `squad` SET `lastPower`=" + millis + " WHERE `squad`='" + getName() + "'");
		this.lastPowerLost = millis;
	}

	public void setLastOnline(long millis) {
		this.lastOnline = millis;
	}
	
	public void setBankBalance(int balance) {
		this.bank_balance = balance;
	}
	
	public void alterBankBalance(int amount) {
		if(bank_balance < amount || (bank_balance + amount) > getMaxBankBalance()) return;
		bank_balance += amount;
	}

	public void depositMoneyIntoBank(Player player, int amount) {
		if(player == null) return;
		Member member = Member.get(player);
		int p_balance = member.getEconomy().getBalance();
		float tax_bracket = getTaxBracketPercent(p_balance);
		float divisor = tax_bracket / 100;
		int tax = (int) (divisor * amount);
		int deposit_taxed = (int) (amount - tax);
		if(p_balance < deposit_taxed) {
			M.message(player, "Squads", ChatColor.WHITE + "You do not have enough money to deposit " + ChatColor.YELLOW + "$" + deposit_taxed);
			return;
		}
		if(getBankBalance() + deposit_taxed >= getMaxBankBalance()) {
			M.message(player, "Squads", ChatColor.YELLOW + "$" + amount + ChatColor.WHITE + " would breach the bank limit of " + ChatColor.YELLOW + "$" + getMaxBankBalance());
			return;
		}
		member.getEconomy().alterBalance(-amount);
		alterBankBalance(+ deposit_taxed);
		Main.mysql.doUpdate("INSERT INTO `squad_bank_log`(`player`, `squad`, `amount`, `useTime`) VALUES ('" + player.getName() + "','" + getName() + "'," + amount + "," + System.currentTimeMillis() + ")");
		M.message(player, "Squads", ChatColor.WHITE + "You deposited " + ChatColor.YELLOW + "$" + deposit_taxed + ChatColor.WHITE + " into the squad bank with a " + ChatColor.YELLOW + "$" + tax + ChatColor.WHITE + " tax");
	}

	public void withdrawMoneyFromBank(Player player, int amount) {
		if(player == null) return;
		if(getTotalDepositHour(player.getName()) >= getHourWithdrawLimit()) {
			M.message(player, "Squads", ChatColor.WHITE + "You may make another withdrawal in " + getBankWithdrawRemaining(player));
			return;
		}
		if((getTotalDepositHour(player.getName()) + amount) >= getHourWithdrawLimit()) {
			M.message(player, "Squads", ChatColor.WHITE + "You may only withdraw another" + ChatColor.YELLOW + "$" + (getHourWithdrawLimit() - getTotalDepositHour(player.getName())));
			return;
		}
		Squad owner = SquadManager.getOwner(player.getLocation().getChunk());
		if(owner == null) {
			M.message(player, "Squads", ChatColor.WHITE + "You can only withdraw squad money in your own territory");
			return;
		}
		if(!owner.getName().equalsIgnoreCase(getName())) {
			M.message(player, "Squads", ChatColor.WHITE + "You can only withdraw squad money in your own territory");
			return;
		}
	}

	public float getTaxBracketPercent(int money) {//0.74 * 60000 - taxed amount = 60000 - (0.74  * 60000)
		if(money < 7000) return 0.0F;
		else if(money < 30000) return 3.0F;
		else if(money < 60000) return 7.4F;
		else if(money < 110000) return 10.6F;
		else return 13.0F;
	}

	public void setBankWithdrawLimit(int limit) {
		this.bank_withdraw_limit = limit;
	}

	public int getTotalDepositHour(String player) {
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM squad_bank_log WHERE player='" + player + "' AND squad='" + getName() + "'");
		int total = 0;
		try {
			while(rs.next()) {
				if(System.currentTimeMillis() - rs.getLong(4) < 3600000) total += rs.getInt(3);
				else Main.mysql.doUpdate("DELETE FROM squad_bank_log WHERE player='" + player + "' AND squad='" + getName() + "' AND useTime=" + rs.getLong(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public String getBankWithdrawRemaining(Player player) {
		if(player == null) return null;
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM `squad_bank_log` WHERE player='" + player.getName() + "' AND squad='" + getName() + "' ORDER BY useTime ASC LIMIT 0,1");
		long earliest_time = 0;
		try {
			if(rs.next()) {
				earliest_time = rs.getLong(4);
			} else return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
		return utilTime.convertString((earliest_time + 3600000) - System.currentTimeMillis(), TimeUnit.BEST, 1);
	}

	public void requestAlliance(Player player, String name) {
		Squad target = SquadManager.getSquad(name);
		if(target == null) {
			M.message(player, "Squads", ChatColor.WHITE + "Failed to find squad from " + ChatColor.YELLOW + name);
			return;
		}
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to ally");
			return;
		}
		if(target.getName().equalsIgnoreCase(getName())) {
			M.message(player, "Squads", ChatColor.WHITE + "You cannot ally yourself");
			return;
		}
		if(areEnemy(name)) {
			setNeutral(player, name);
			return;
		}
		if(areAlly(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You are already allies with " + ChatColor.YELLOW + target.getName());
			return;
		}
		if(getMaxAllies() > -1) {
			if(getAllyCount() >= getMaxAllies()) {
				M.message(player, "Squads", ChatColor.WHITE + "You have reached your ally limit");
				return;
			}
		}
		if(allyRequest.containsKey(target.getName())) {
			allyMap.put(target.getName(), false);
			target.allyMap.put(getName(), false);
			allyRequest.remove(target.getName());
			if(target.allyRequest.containsKey(getName())) target.allyRequest.remove(getName());
			sendMessage(ChatColor.WHITE + "You are now allies with " + ChatColor.YELLOW + target.getName());
			target.sendMessage(ChatColor.WHITE + "You are now allies with " + ChatColor.YELLOW + getName());
			Main.mysql.doUpdate("INSERT INTO `squad_ally`(`squad`, `ally`, `trust`) VALUES ('" + getName() + "', '" + target.getName() + "', 0)");
			Main.mysql.doUpdate("INSERT INTO `squad_ally`(`squad`, `ally`, `trust`) VALUES ('" + target.getName() + "', '" + getName() + "', 0)");
			Bukkit.getServer().getPluginManager().callEvent(new SquadRelationChangeEvent(this, target, Relation.ALLY));
			return;
		}
		target.allyRequest.put(getName(), System.currentTimeMillis());
		sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " requested alliance with " + ChatColor.YELLOW + target.getName());
		target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " requested alliance with you");
	}

	public void sendClaims(Player player) {
		if(player == null) return;
		if(claimMap.size() == 0) {
			M.message(player, "Squads", ChatColor.WHITE + "You own 0 land");
			return;
		}
		M.message(player, "Squads", ChatColor.WHITE + "Claims list > " + ChatColor.YELLOW + "Length - " + claimMap.size());
		int count = 0;
		for(Entry<String, ClaimType> entry : claimMap.entrySet()) {
			player.sendMessage(ChatColor.BLUE + "Claim " + count + ChatColor.WHITE + " " + entry.getKey() + ", " + entry.getValue().getFormattedName());
			count++;
		}
	}

	public static List<String> getDisallowedNames() {
		String names = Main.config.getString("squads.disallowed_names");
		String[] split = names.split(",");
		ArrayList<String> list = new ArrayList<String>();
		for(String name : split) {
			list.add(name);
		}
		return list;
	}

	public Relation getRelation(String name) {
		Squad squad = SquadManager.getSquad(name);
		if(squad == null) return Relation.NEUTRAL;
		if(getTrust(name)) return Relation.TRUST;
		else if(areAlly(name)) return Relation.ALLY;
		else if(areEnemy(name)) return Relation.ENEMY;
		else if(getName().equalsIgnoreCase(name)) return Relation.SELF;
		else return Relation.NEUTRAL;
	}
	
	public Relation getRelation(Squad squad) {
		if(squad == null) return Relation.NEUTRAL;
		if(getTrust(squad.getName())) return Relation.TRUST;
		else if(areAlly(squad.getName())) return Relation.ALLY;
		else if(areEnemy(squad.getName())) return Relation.ENEMY;
		else if(getName().equalsIgnoreCase(squad.getName())) return Relation.SELF;
		else return Relation.NEUTRAL;
	}

	public Relation getRelation(Player player) {
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return Relation.NEUTRAL;
		if(getTrust(squad.getName())) return Relation.TRUST;
		else if(areAlly(squad.getName())) return Relation.ALLY;
		else if(areEnemy(squad.getName())) return Relation.ENEMY;
		else if(getName().equalsIgnoreCase(squad.getName())) return Relation.SELF;
		return Relation.NEUTRAL;
	}
	
	public static void sendAbout(Player player) {
		M.message(player, "Squads", ChatColor.WHITE + "About Squads");
		player.sendMessage("Squads is a smaller, custom made version of MassiveCraft's Factions. It incorporates many of the features of Factions but with less hassle. You can view all the commands with the /s help (page) command.");
	}
	
	public static boolean getCanClaimNextTo() {
		return Main.config.getBoolean("squads.can_claim_next_to");
	}

	public static boolean getChangeNameColour() {
		return Main.config.getBoolean("squads.nametag_change");
	}

	public static int getMaxAdminPower() {
		return Main.config.getInt("squads.max_admin_power");
	}

	public static int getMaxAdminLand() {
		return Main.config.getInt("squads.max_admin_land");
	}

	public static int getMaxAdminAllies() {
		return Main.config.getInt("squads.max_admin_allies");
	}

	public static int getMaxAdminEnemies() {
		return Main.config.getInt("squads.max_admin_enemies");
	}

	public void addEnemy(Player player, String name) {
		if(!memberMap.containsKey(player.getName())) return;
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to enemy");
			return;
		}
		if(getName().equalsIgnoreCase(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You cannot enemy yourself");
			return;
		}
		if(areEnemy(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You are already enemies with " + ChatColor.YELLOW + name);
			return;
		}
		if(getMaxEnemies(this) > -1) {
			if(getEnemyCount() >= getMaxEnemies(this)) {
				M.message(player, "Squads", ChatColor.WHITE + "You have reached your enemy limit");
				return;
			}
		}
		if(SquadManager.getSquad(name) == null) {
			M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " does not exist");
			return;
		}
		Squad target = SquadManager.getSquad(name);
		if(areAlly(name)) {
			setNeutral(player, target.getName());
			return;
		}
		target.enemyMap.put(getName(), 16);
		enemyMap.put(target.getName(), 16);
		target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " enemied you");
		sendMessage(ChatColor.WHITE + "You enemied " + ChatColor.YELLOW + name);
		Main.mysql.doUpdate("INSERT INTO `squad_enemy`(`squad`, `enemy`) VALUES ('" + getName() + "','" + target.getName() + "')");
		Main.mysql.doUpdate("INSERT INTO `squad_enemy`(`squad`, `enemy`) VALUES ('" + target.getName() + "','" + getName() + "')");
		Bukkit.getServer().getPluginManager().callEvent(new SquadRelationChangeEvent(this, target, Relation.ENEMY));
	}
	
	public void addDominance(Squad squad) {
		if(squad == null) return;
		if(!areEnemy(squad)) return;
		if(getDominance(squad) < 1 || getDominance(squad) > 31) return;
		enemyMap.put(squad.getName(), getDominance(squad) + 1);
		squad.enemyMap.put(getName(), squad.getDominance(this) - 1);
		Main.mysql.doUpdate("UPDATE `squad_dominanace` SET amount=" + enemyMap.get(squad.getName()) + " WHERE squad='" + getName() + "' AND target='" + squad.getName() + "'");
		Main.mysql.doUpdate("UPDATE `squad_dominanace` SET amount=" + squad.enemyMap.get(getName()) + " WHERE squad='" + squad.getName() + "' AND target='" + getName() + "'");
	}
	
	public int getDominance(Squad squad) {
		if(!areEnemy(squad)) return (Integer) null;
		return enemyMap.get(squad.getName());
	}
	
	public void giveTrust(Player player, String name) {
		if(!memberMap.containsKey(player.getName())) return;
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to give trust");
			return;
		}
		if(getName().equalsIgnoreCase(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You cannot trust yourself");
			return;
		}
		if(!areAlly(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be allies with " + ChatColor.YELLOW + name + ChatColor.WHITE + " to give them " + ChatColor.YELLOW + "trust");
			return;
		}
		if(getMaxTrustedAllies() > -1) {
			if(getTrustCount() >= getMaxTrustedAllies()) {
				M.message(player, "Squads", ChatColor.WHITE + "You have reached your trusted ally limit");
				return;
			}
		}
		Squad target = SquadManager.getSquad(name);
		if(target == null) {
			M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " does not exist");
			return;
		}
		if(target.getTrust(getName()) == true) {
			M.message(player, "Squads", ChatColor.WHITE + "You have already given " + ChatColor.YELLOW + name + ChatColor.WHITE + " trust");
			return;
		}
		allyMap.put(name, true);
		Main.mysql.doUpdate("UPDATE `squad_ally` SET trust=1 WHERE squad='" + getName() + "' AND ally='" + name + "'");
		target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " gave you trust");
		sendMessage(ChatColor.WHITE + "You gave trust to " + ChatColor.YELLOW + name);
		Bukkit.getServer().getPluginManager().callEvent(new SquadRelationChangeEvent(this, target, Relation.TRUST));
	}
	
	public int getTrustCount() {
		int count = 0;
		for(String allies : allyMap.keySet()) {
			if(allyMap.get(allies) == true) count++;
		}
		return count;
	}

	public void revokeTrust(Player player, String name) {
		if(!memberMap.containsKey(player.getName())) return;
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to revoke trust");
			return;
		}
		if(getName().equalsIgnoreCase(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You cannot revoke trust from yourself");
			return;
		}
		if(!areAlly(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be allies with " + ChatColor.YELLOW + name + ChatColor.WHITE + " to revoke trust from them");
			return;
		}
		Squad target = SquadManager.getSquad(name);
		if(target == null) {
			M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " does not exist");
			return;
		}
		if(target.getTrust(getName()) == false) {
			M.message(player, "Squads", ChatColor.WHITE + "You have not given " + ChatColor.YELLOW + name + ChatColor.WHITE + " trust");
			return;
		}
		allyMap.put(target.getName(), false);
		Main.mysql.doUpdate("UPDATE `squad_ally` SET trust=0 WHERE squad='" + getName() + "' AND ally='" + name + "'");
		target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " revoked their trust");
		sendMessage(ChatColor.WHITE + "You revoked trust from " + ChatColor.YELLOW + name);
		Bukkit.getServer().getPluginManager().callEvent(new SquadRelationChangeEvent(this, target, Relation.ALLY));
	}

	public void setNeutral(Player player, String name) {
		if(!memberMap.containsKey(player.getName())) return;
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to neutralize a relationship");
			return;
		}
		if(getName().equalsIgnoreCase(name)) {
			M.message(player, "Squads", ChatColor.WHITE + "You cannot neutralize yourself");
			return;
		}
		Squad target = SquadManager.getSquad(name);
		if(target == null) {
			M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " does not exist");
			return;
		}
		if(areAlly(name)) {
			allyMap.remove(name);
			target.allyMap.remove(getName());
			Main.mysql.doUpdate("DELETE FROM `squad_ally` WHERE squad='" + getName() + "' AND ally='" + name + "'");
			Main.mysql.doUpdate("DELETE FROM `squad_ally` WHERE squad='" + name + "' AND ally='" + getName() + "'");
			sendMessage(ChatColor.WHITE + "You neutralized your relation with " + ChatColor.YELLOW + target.getName());
			target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " neutralized you");
			Bukkit.getServer().getPluginManager().callEvent(new SquadRelationChangeEvent(this, target, Relation.NEUTRAL));
			return;
		}
		if(areEnemy(name)) {
			if(!neutralRequest.containsKey(target.getName())) {
				target.neutralRequest.put(getName(), System.currentTimeMillis());
				sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " has requested to be neutral with " + ChatColor.YELLOW + target.getName());
				target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " has requested to be neutral with you");
				return;
			}
			if((System.currentTimeMillis() - neutralRequest.get(target.getName())) > 86400000) {
				M.message(player, "Squads", ChatColor.YELLOW + target.getName() + "'s " + ChatColor.WHITE + " request to neutralize has expired");
				return;
			}
			enemyMap.remove(name);
			target.enemyMap.remove(getName());
			Main.mysql.doUpdate("DELETE FROM `squad_enemy` WHERE squad='" + getName() + "' AND enemy='" + name + "'");
			Main.mysql.doUpdate("DELETE FROM `squad_enemy` WHERE squad='" + name + "' AND enemy='" + getName() + "'");
			sendMessage(ChatColor.WHITE + "You neutralized your relation with " + ChatColor.YELLOW + target.getName());
			target.sendMessage(ChatColor.YELLOW + getName() + ChatColor.WHITE + " neutralized you");
			Bukkit.getServer().getPluginManager().callEvent(new SquadRelationChangeEvent(this, target, Relation.NEUTRAL));
			return;
		}
		M.message(player, "Squads", ChatColor.WHITE + "You are already neutral with " + ChatColor.YELLOW + target.getName());
	}

	public void promote(Player sender, String player) {
		if(!memberMap.containsKey(player)) return;
		Rank cur = getRank(player);
		Rank sender_rank = getRank(sender.getName());
		if(sender_rank == Rank.MEMBER) {
			M.message(sender, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to promote");
			return;
		}
		if(cur != Rank.MEMBER) {
			M.message(sender, "Squads", ChatColor.WHITE + "Only people with the rank " + ChatColor.YELLOW + "Member " + ChatColor.WHITE + "can be promoted");
			return;
		}
		memberMap.put(player, Rank.ADMIN);
		Main.mysql.doUpdate("UPDATE squad_player SET `role`='ADMIN' WHERE player='" + player + "'");
		M.message(sender, "Squads", ChatColor.WHITE + "You promoted " + ChatColor.YELLOW + player + ChatColor.WHITE + " to " + ChatColor.YELLOW + "Admin");
	}



	public void demote(Player sender, String player) {
		if(!memberMap.containsKey(player));
		Rank cur = getRank(player);
		Rank sender_rank = getRank(sender.getName());
		if(sender_rank != Rank.LEADER) {
			M.message(sender, "Squads", ChatColor.WHITE + "You must be the " + ChatColor.YELLOW + "Leader " + ChatColor.WHITE + "to demote");
			return;
		}
		if(cur != Rank.ADMIN) {
			M.message(sender, "Squads", ChatColor.WHITE + "Only people with the rank " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "can be demoted");
			return;
		}
		memberMap.put(player, Rank.MEMBER);
		Main.mysql.doUpdate("UPDATE squad_player SET `role`='MEMBER' WHERE player='" + player + "'");
		M.message(sender, "Squads", ChatColor.WHITE + "You demoted " + ChatColor.YELLOW + player + ChatColor.WHITE + " to " + ChatColor.YELLOW + "Member");
	}

	public void passLeadership(Player sender, String player) {
		Player target = Bukkit.getPlayer(player);
		String name = player;
		if(target != null) name = target.getName();
		if(!memberMap.containsKey(sender.getName())) return;
		if(getRank(sender.getName()) != Rank.LEADER) {
			M.message(sender, "Squads", ChatColor.WHITE + "Only the " + ChatColor.YELLOW + "Leader " + ChatColor.WHITE + "can " + ChatColor.YELLOW + "pass leadership");
			return;
		}
		if(!memberMap.containsKey(player)) {
			M.message(sender, "Squads", ChatColor.YELLOW + player + ChatColor.WHITE + " is not in your squad");
			return;
		}
		setRank(sender.getName(), Rank.ADMIN);
		setRank(name, Rank.LEADER);
		sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.WHITE + " gave leadership to " + ChatColor.YELLOW + name);

	}

	public void setRank(String player, Rank rank) {
		if(!memberMap.containsKey(player)) return;
		memberMap.put(player, rank);
		Main.mysql.doUpdate("UPDATE `squad_player` SET role='" + rank.toString() + "' WHERE player='" + player + "'");
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public static void sendHelpMenu(Player player, int page) {
		if(page > 3 && !player.hasPermission("squads.admin")) {
			M.message(player, "Squads", ChatColor.WHITE + "You must enter a number inclusively between 1 and 3");
			return;
		}
		if(page > 4 && player.hasPermission("squads.admin")) {
			M.message(player, "Squads", ChatColor.WHITE + "You must enter a number inclusively between 1 and 4");
			return;
		}
		player.sendMessage(ChatColor.BLUE + "Squads Help >" + ChatColor.WHITE + " Page " + page);
		player.sendMessage(ChatColor.BLUE + "Ranks - " + ChatColor.YELLOW + "L - Leader, A - Admin, M - Member, N - None");
		if(page == 1) {
			player.sendMessage(ChatColor.BLUE + "/s (squad) " + ChatColor.WHITE + "Displays Squad Info " + ChatColor.YELLOW + "M");
			player.sendMessage(ChatColor.BLUE + "/s create <name> " + ChatColor.WHITE + "Creates a new Squad " + ChatColor.YELLOW + "N");
			player.sendMessage(ChatColor.BLUE + "/s set home " + ChatColor.WHITE + "Set new home " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s home " + ChatColor.WHITE + "Teleport to home " + ChatColor.YELLOW + "M");
			player.sendMessage(ChatColor.BLUE + "/s claim " + ChatColor.WHITE + "Claims a chunk " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s unclaim " + ChatColor.WHITE + "Unclaims a chunk " + ChatColor.YELLOW + "A");
			return;
		}
		if(page == 2) {
			player.sendMessage(ChatColor.BLUE + "/s invite <player> " + ChatColor.WHITE + "Invite a player " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s deinvite <player> " + ChatColor.WHITE + "De-invite a player " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s ally <squad> " + ChatColor.WHITE + "Request alliance with a Squad " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s neutral <squad> " + ChatColor.WHITE + "Neutralize relation with Squad " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s enemy <squad> " + ChatColor.WHITE + "Enemy a squad " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s leave " + ChatColor.WHITE + "Leave your current Squad " + ChatColor.YELLOW + "M");
			player.sendMessage(ChatColor.BLUE + "/s join <player> " + ChatColor.WHITE + "Join a Squad " + ChatColor.YELLOW + "N");
			return;
		}
		if(page == 3) {
			player.sendMessage(ChatColor.BLUE + "/s kick <player> " + ChatColor.WHITE + "Kick a member " + ChatColor.YELLOW + "L");
			player.sendMessage(ChatColor.BLUE + "/s promote <player> " + ChatColor.WHITE + "Promote player to Admin " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s demote <player> " + ChatColor.WHITE + "Demote player to member " + ChatColor.YELLOW + "L");
			player.sendMessage(ChatColor.BLUE + "/s <unclaim-all:uca> " + ChatColor.WHITE + "Unclaims all land " + ChatColor.YELLOW + "L");
			player.sendMessage(ChatColor.BLUE + "/s trust <squad> " + ChatColor.WHITE + "Give trust to an ally " + ChatColor.YELLOW + "A");
			player.sendMessage(ChatColor.BLUE + "/s chat " + ChatColor.WHITE + "Toggles squad chat " + ChatColor.YELLOW + "M");
			player.sendMessage(ChatColor.BLUE + "/s neutral <squad> " + ChatColor.WHITE + "Neutral a squad from enemy " + ChatColor.YELLOW + "A");
			return;
		}
		if(page == 4 && player.hasPermission("squads.admin")) {
			player.sendMessage(ChatColor.DARK_RED + "/sa toggle" + ChatColor.WHITE + " Toggle squad admin mode");
			player.sendMessage(ChatColor.DARK_RED + "/sa disband <squad>" + ChatColor.WHITE + " Force disband a squad");
			player.sendMessage(ChatColor.DARK_RED + "/sa claim <squad>" + ChatColor.WHITE + " Force claim a chunk for a squad");
			player.sendMessage(ChatColor.DARK_RED + "/sa unclaim <squad>" + ChatColor.WHITE + " Force unclaim a chunk from a squad");
			player.sendMessage(ChatColor.DARK_RED + "/sa tp <squad>" + ChatColor.WHITE + " Teleport to a squad's home");
			player.sendMessage(ChatColor.DARK_RED + "/sa clear" + ChatColor.WHITE + " Clear all squad data permanently");
			return;
		}
	}

	public void sendHome(final Player player) {
		if(home == null) {
			M.message(player, "Squads", ChatColor.WHITE + "Your Squad has not set a home");
			return;
		}
		if(Cooldown.isCooling(player.getName(), "Squad Home")) {
			Cooldown.sendRemaining(player, "Squad Home");
			return;
		}
		boolean use_anywhere = Main.config.getBoolean("squads.use_home_anywhere");
		final int cooldown = Main.config.getInt("squads.home_cooldown");
		int delay = Main.config.getInt("squads.home_delay");
		if(delay < 0) delay = 0;
		if(!use_anywhere) {
			if(!SquadManager.isClaimed(player.getLocation().getChunk())) {
				M.message(player, "Squads", ChatColor.WHITE + "You must be in a " + ChatColor.YELLOW + "safe claim " + ChatColor.WHITE + "to go home");
				return;
			}
			ClaimType type = SquadManager.getClaimType(player.getLocation().getChunk());
			if(type != ClaimType.SAFE) {
				M.message(player, "Squads", ChatColor.WHITE + "You must be in a " + ChatColor.YELLOW + "safe claim " + ChatColor.WHITE + "to go home");
				return;
			}
			if(delay == 1) M.message(player, "Squads", ChatColor.WHITE + "Don't move for 1 second");
			else if(delay > 1) M.message(player, "Squads", ChatColor.WHITE + "Don't move for " + delay + " seconds");
			homeMove.add(player.getName());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				public void run() {
					if(!homeMove.contains(player.getName())) return;
					player.teleport(home);
					M.message(player, "Squads", ChatColor.WHITE + "You teleported " + ChatColor.YELLOW + "home");
					Cooldown.add(player.getName(), "Squad Home", cooldown);
				}
			}, 20 * delay);
			return;
		}
		if(delay == 1) M.message(player, "Squads", ChatColor.WHITE + "Don't move for 1 second");
		else if(delay > 1) M.message(player, "Squads", ChatColor.WHITE + "Don't move for " + delay + " seconds");
		homeMove.add(player.getName());
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			public void run() {
				if(!homeMove.contains(player.getName())) return;
				player.teleport(home);
				M.message(player, "Squads", ChatColor.WHITE + "You teleported " + ChatColor.YELLOW + "home");
				Cooldown.add(player.getName(), "Squad Home", cooldown);
			}
		}, 20 * delay);
	}

	private static String getMapIcon(Player player, String name) {
		Squad squad = SquadManager.getSquad(player);
		if(squad != null) {
			if(squad.areAlly(name)) return "A";
			else if(squad.areEnemy(name)) return "E";
			else if(squad.getTrust(name)) return "T";
			else if(name.equalsIgnoreCase(squad.getName())) return "S";
			else if(!squad.areAlly(name) && !squad.areEnemy(name) && !squad.getTrust(name) && !name.equalsIgnoreCase(squad.getName())) return "N";
			else return "N";	
		}
		return "N";
	}

	public static void sendMap(Player player, int distance, int height) {
		if(player == null) return;
		if(distance <= 0 || distance > 10 || height <= 0 || height > 10) return;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Chunk cur = player.getLocation().getChunk();
		Squad squad = SquadManager.getSquad(player);
		String row = "";
		for(int z = 0; z < height; z++) {
			for(int x = -distance; x < distance; x--) {
				Chunk chunk = player.getWorld().getChunkAt(cur.getX() + x, cur.getZ() + z);
				Squad owner = SquadManager.getOwner(chunk);
				if(owner == null || squad == null) {
					row = row + "+ ";
				} else if(owner != null && squad != null) {
					row = row + getMapIcon(player, owner.getName() + " ");
				}
			}
			map.put(z, row);
		}

		M.message(player, "Squads", ChatColor.WHITE + "You are viewing a map of nearby chunks");
		for(int z_id : map.keySet()) {
			String rawRow = map.get(z_id);
			String format = formatMapRow(rawRow);
			player.sendMessage(format);
		}
	}

	private static String formatMapRow(String row) {
		String[] icons = row.split(" ");
		String format = "";
		for(String icon : icons) {
			if(icon.equalsIgnoreCase("A")) format = format + ChatColor.GREEN + "A";
			else if(icon.equalsIgnoreCase("E")) format = format + ChatColor.RED + "E";
			else if(icon.equalsIgnoreCase("T")) format = format + ChatColor.DARK_GREEN + "T";
			else if(icon.equalsIgnoreCase("S")) format = format + ChatColor.AQUA + "S";
			else if(icon.equalsIgnoreCase("N")) format = format + ChatColor.YELLOW + "N";
			else format = format + ChatColor.WHITE + icon;
		}
		return format;
	}

	public void join(Player player) {
		if(!inviteeMap.containsKey(player.getName())) {
			M.message(player, "Squads", ChatColor.WHITE + "You have not been invited to " + ChatColor.YELLOW + getName());
			return;
		}
		if(inviteeMap.containsKey(player.getName())) {
			if(SquadManager.hasSquad(player.getName())) {
				M.message(player, "Squads", ChatColor.WHITE + "You must leave your current squad to join another");
				return;
			}
			if((System.currentTimeMillis() - inviteeMap.get(player.getName())) > 86400000) {
				M.message(player, "Squads", ChatColor.WHITE + "Your invite from " + ChatColor.YELLOW + getName() + ChatColor.WHITE + " has expired");
				inviteeMap.remove(player.getName());
				return;
			}
			inviteeMap.remove(player.getName());
			memberMap.put(player.getName(), Rank.MEMBER);
			Main.mysql.doUpdate("INSERT INTO `squad_player`(`player`, `role`, `squad`) VALUES ('" + player.getName() + "','MEMBER','" + getName() + "')");
			sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " joined your squad");
			if(getChangeNameColour()) {
				TeamManager.boards.put(player.getName(), TeamManager.getNewScoreboard(player));
				player.setScoreboard(TeamManager.boards.get(player.getName()));
			}
		}
	}

	public void invite(Player inviter, String invitee) {
		Player p_invitee = Bukkit.getPlayer(invitee);
		String name = invitee;
		if(!memberMap.containsKey(inviter.getName())) return;
		if(memberMap.containsKey(name)) {
			M.message(inviter, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " is already in your squad");
			return;
		}
		if(getRank(inviter.getName()) == Rank.MEMBER) {
			M.message(inviter, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to invite players");
			return;
		}
		if(p_invitee != null) name = p_invitee.getName();
		if(inviteeMap.containsKey(name)) {
			long inv_time = inviteeMap.get(name);
			long dif = System.currentTimeMillis() - inv_time;
			if(dif > 86400000) {
				inviteeMap.put(name, System.currentTimeMillis());
				if(p_invitee != null) M.message(p_invitee, "Squads", ChatColor.WHITE + "You have been invited to join " + ChatColor.YELLOW + getName());
				sendMessage(ChatColor.YELLOW + inviter.getName() + ChatColor.WHITE + " invited " + ChatColor.YELLOW + name + ChatColor.WHITE + " to your squad");
				return;
			} else {
				M.message(inviter, "Squads", ChatColor.YELLOW + invitee + "'s" + ChatColor.WHITE + " invite doesn't expire for " + ChatColor.YELLOW + utilTime.convertString((86400000L + inviteeMap.get(name)) - System.currentTimeMillis(), TimeUnit.BEST, 1));
				return;
			}
		}
		inviteeMap.put(name, System.currentTimeMillis());
		if(p_invitee != null) M.message(p_invitee, "Squads", ChatColor.WHITE + "You have been invited to join " + ChatColor.YELLOW + getName());
		sendMessage(ChatColor.YELLOW + inviter.getName() + ChatColor.WHITE + " invited " + ChatColor.YELLOW + name + ChatColor.WHITE + " to your squad");
	}

	public void uninvite(Player player, String target) {
		Player p_target = Bukkit.getPlayer(target);
		String name = target;
		if(p_target != null) name = p_target.getName();
		if(!memberMap.containsKey(player.getName())) return;
		if(memberMap.containsKey(name)) {
			M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " is already in your squad");
			return;
		}
		if(!inviteeMap.containsKey(name)) {
			M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " is not invited");
			return;
		}
		if(getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must atleast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to uninvite players");
			return;
		}
		inviteeMap.remove(name);
		if(p_target != null) M.message(p_target, "Squads", ChatColor.WHITE + "You have been un-invited from " + ChatColor.YELLOW + getName());
		sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " un-invited " + ChatColor.YELLOW + name + ChatColor.WHITE + " from your squad");
	}

	public void showInvitations(Player player) {
		if(!memberMap.containsKey(player.getName())) return;
		if(inviteeMap.size() == 0) {
			M.message(player, "Squads", ChatColor.WHITE + "Invitation list is empty");
			return;
		}
		for(Entry<String, Long> entry : inviteeMap.entrySet()) {
			long inv_time = entry.getValue();
			long dif = System.currentTimeMillis() - inv_time;
			if(dif < 86400000) {
				M.message(player, "Invite", ChatColor.YELLOW + entry.getKey() + ChatColor.WHITE + " expires in " + ChatColor.YELLOW + utilTime.convertString((86400000L + entry.getValue()) - System.currentTimeMillis(), TimeUnit.BEST, 1));
			}
		}
	}

	public void sendChannelMessage(String sender, String message) {
		for(String players : memberMap.keySet()) {
			Player player = Bukkit.getPlayer(players);
			if(player != null) M.channelMessage(sender, player, getName(), message);
		}
	}

	public void sendMessage(String message) {
		for(String players : memberMap.keySet()) {
			Player player = Bukkit.getPlayer(players);
			if(player != null) M.message(player, "Squads", message);
		}
	}

	public void showDetails(Player player) {
		Squad squad = SquadManager.getSquad(player);
		player.sendMessage(ChatColor.LIGHT_PURPLE + "Squad " + getName() + ChatColor.YELLOW + " > " + ChatColor.LIGHT_PURPLE + getAge(TimeUnit.BEST));
		player.sendMessage(ChatColor.YELLOW + "Power > " + ChatColor.GREEN + getPower() + ChatColor.YELLOW + "/" + ChatColor.GREEN + getMaxPower() + ChatColor.YELLOW + "");
		player.sendMessage(ChatColor.YELLOW + "Members " + ChatColor.GREEN + getMemberCount() + ChatColor.YELLOW + " > " + getMembersFormatted());
		player.sendMessage(ChatColor.YELLOW + "Allies " + ChatColor.GREEN + getAllyCount() + ChatColor.YELLOW + "/" + ChatColor.GREEN + getMaxAllies() + ChatColor.YELLOW + " > " + getAlliesFormatted());
		if(getMaxEnemies(this) == -1) player.sendMessage(ChatColor.YELLOW + "Enemies " + ChatColor.GREEN + getEnemyCount() + ChatColor.YELLOW + " > " + getEnemiesFormatted());
		else player.sendMessage(ChatColor.YELLOW + "Enemies " + ChatColor.GREEN + getEnemyCount() + ChatColor.YELLOW + "/" + ChatColor.GREEN + getMaxEnemies(this) + ChatColor.YELLOW + " > " + getEnemiesFormatted());
		player.sendMessage(ChatColor.YELLOW + "Land > " + ChatColor.GREEN + getLand() + ChatColor.YELLOW + "/" + ChatColor.GREEN + getMaxLand() + ChatColor.YELLOW + "");
		if(squad != null) {
			if(squad.areEnemy(this)) player.sendMessage(ChatColor.YELLOW + "Dominanace > " + ChatColor.GREEN + squad.getDominance(this) + ChatColor.WHITE + ":" + ChatColor.RED + getDominance(squad));
		}
	}

	public static boolean create(String name, String creator, boolean admin) {
		if(SquadManager.squads.containsKey(name)) return false;
		Player p_creator = Bukkit.getPlayer(creator);
		if(p_creator == null) return false;
		Squad squad = new Squad(name, creator, admin);
		SquadManager.squads.put(name, squad);
		try {
			Scoreboard newBoard = TeamManager.getNewScoreboard(p_creator);
			TeamManager.boards.put(p_creator.getName(), newBoard);
			p_creator.setScoreboard(newBoard);
		} catch(NullPointerException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "Squads > Failed to generate new team for " + creator);
			M.message(p_creator, "Squads", ChatColor.WHITE + "Failed to generate " + ChatColor.YELLOW + "scoreboard data");
			M.message(p_creator, "Squads", ChatColor.WHITE + "Please notify an " + ChatColor.YELLOW + "Admin");
			return false;
		}
		Main.mysql.doUpdate("INSERT INTO `squad`(`squad`, `power`, `admin`, `date`, `create_time`, `lastPower`) VALUES ('" + name + "'," + squad.getPower() + "," + booleanToShort(admin) + ",'" + utilTime.timeStr() + "'," + System.currentTimeMillis() + ",-1)");
		Main.mysql.doUpdate("INSERT INTO `squad_player`(`player`, `role`, `squad`) VALUES ('" + creator + "', '" + Rank.LEADER.toString() + "', '" + name + "')");
		return true;
	}

	public void claim(Chunk chunk, ClaimType type) {
		if(SquadManager.isClaimed(chunk)) return;
		if(!getCanClaimNextTo()) {
			Chunk plusX = chunk.getWorld().getChunkAt(chunk.getX() + 1, chunk.getZ());
			Chunk subX = chunk.getWorld().getChunkAt(chunk.getX() - 1, chunk.getZ());
			Chunk plusZ = chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() + 1);
			Chunk subZ = chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() - 1);
			if(SquadManager.isClaimed(plusX)) {
				Squad owner = SquadManager.getOwner(plusX);
				if(owner != null) {
					if(!owner.getName().equalsIgnoreCase(getName())) return;
				}
			}
			if(SquadManager.isClaimed(subX)) {
				Squad owner = SquadManager.getOwner(subX);
				if(owner != null) {
					if(!owner.getName().equalsIgnoreCase(getName())) return;
				}
			}
			if(SquadManager.isClaimed(plusZ)) {
				Squad owner = SquadManager.getOwner(plusZ);
				if(owner != null) {
					if(!owner.getName().equalsIgnoreCase(getName())) return;
				}
			}
			if(SquadManager.isClaimed(subZ)) {
				Squad owner = SquadManager.getOwner(subZ);
				if(owner != null) {
					if(!owner.getName().equalsIgnoreCase(getName())) return;
				}
			}
		}
		if(getLand() >= getMaxLand()) return;
		claimMap.put(utilWorld.chunkToStr(chunk), type);
		SquadManager.claims.put(utilWorld.chunkToStr(chunk), getName());
		Main.mysql.doUpdate("INSERT INTO `squad_claims`(`squad`, `chunk`, `type`) VALUES ('" + getName() + "','" + utilWorld.chunkToStr(chunk) + "','" + type.toString().toLowerCase() + "')");
//		Block[] corners = getCorners(chunk);
//		for(int x1 = corners[0].getX(); x1 < corners[0].getX() + 16; x1++) {
//			Location loc = new Location(corners[0].getWorld(), corners[0].getX() + x1, corners[0].getY(), corners[0].getZ());
//			loc.setY(loc.getWorld().getHighestBlockYAt(loc));
//			loc.getWorld().getBlockAt(loc).setType(Material.DIRT);
//		}
	}
	
	public Block[] getCorners(Chunk chunk) {
		Block[] array = new Block[4];
		array[0] = chunk.getBlock(0, 0, 0);
		array[1] = chunk.getBlock(15, 0, 0);
		array[2] = chunk.getBlock(0, 0, 15);
		array[3] = chunk.getBlock(15, 0, 15);
		return array;
	}

	public void forceClaim(Chunk chunk, ClaimType type) {
		if(SquadManager.isClaimed(chunk)) return;
		if(!getCanClaimNextTo()) {
			Chunk plusX = chunk.getWorld().getChunkAt(chunk.getX() + 1, chunk.getZ());
			Chunk subX = chunk.getWorld().getChunkAt(chunk.getX() - 1, chunk.getZ());
			Chunk plusZ = chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() + 1);
			Chunk subZ = chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() - 1);
			if(SquadManager.isClaimed(plusX)) {
				Squad owner = SquadManager.getOwner(plusX);
				if(!owner.getName().equalsIgnoreCase(getName())) return;
			}
			if(SquadManager.isClaimed(subX)) {
				Squad owner = SquadManager.getOwner(subX);
				if(!owner.getName().equalsIgnoreCase(getName())) return;
			}
			if(SquadManager.isClaimed(plusZ)) {
				Squad owner = SquadManager.getOwner(plusZ);
				if(!owner.getName().equalsIgnoreCase(getName())) return;
			}
			if(SquadManager.isClaimed(subZ)) {
				Squad owner = SquadManager.getOwner(subZ);
				if(!owner.getName().equalsIgnoreCase(getName())) return;
			}
		}
		if(getLand() >= getMaxLand()) return;
		claimMap.put(utilWorld.chunkToStr(chunk), type);
		SquadManager.claims.put(utilWorld.chunkToStr(chunk), getName());
		Main.mysql.doUpdate("INSERT INTO `squad_claims`(`squad`, `chunk`, `type`) VALUES ('" + getName() + "','" + utilWorld.chunkToStr(chunk) + "','" + type.toString().toLowerCase() + "')");
		sendMessage(ChatColor.WHITE + "A chunk was forceably claimed via " + ChatColor.YELLOW + "Server Admin");
	}

	public void unclaim(Player player) {
		Squad owner = SquadManager.getOwner(player.getLocation().getChunk());
		Squad squad = SquadManager.getSquad(player);
		if(squad.getRank(player.getName()).getID() < 2) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to " + ChatColor.YELLOW + "unclaim");
			return;
		}
		if(owner == null) {
			M.message(player, "Squads", ChatColor.WHITE + "You do not own this land");
			return;
		}
		if(!owner.getName().equalsIgnoreCase(squad.getName())) {
			M.message(player, "Squads", ChatColor.WHITE + "You do not own this land");
			return;
		}
		claimMap.remove(utilWorld.chunkToStr(player.getLocation().getChunk()));
		SquadManager.claims.remove(utilWorld.chunkToStr(player.getLocation().getChunk()));
		Main.mysql.doUpdate("DELETE FROM squad_claims WHERE chunk='" + utilWorld.chunkToStr(player.getLocation().getChunk()) + "'");
		M.message(player, "Squads", ChatColor.WHITE + "You unclaimed a chunk");
	}

	public static void unclaim(Player player, Chunk chunk, Source src) {
		if(!SquadManager.isClaimed(chunk)) return;
		Squad owner = SquadManager.getOwner(chunk);
		owner.claimMap.remove(utilWorld.chunkToStr(chunk));
		SquadManager.claims.remove(utilWorld.chunkToStr(chunk));
		Main.mysql.doUpdate("DELETE FROM squad_claims WHERE chunk='" + utilWorld.chunkToStr(chunk) + "'");
		owner.sendMessage(ChatColor.WHITE + "A chunk has been unclaimed via " + ChatColor.YELLOW + src.getMessage());
		if(src == Source.OTHER_SQUAD) {
			M.message(player, "Squads", ChatColor.WHITE + "You stole a chunk from " + ChatColor.YELLOW + owner.getName()); 
			return;
		}
		else if(src == Source.SERVER_ADMIN) {
			M.message(player, "Squads", ChatColor.WHITE + "You unclaimed a chunk from " + ChatColor.YELLOW + owner.getName());
			return;
		}
		
	}

	public void unclaimAll() {
		for(String str_chunk : claimMap.keySet()) {
			SquadManager.claims.remove(str_chunk);
		}
		claimMap.clear();
		Main.mysql.doUpdate("DELETE FROM squad_claims WHERE squad='" + getName() + "'");
		sendMessage(ChatColor.WHITE + "Your land has been unclaimed via " + ChatColor.YELLOW + "Server Admin");
	}

	public void unclaimAll(Player player) {
		if(getRank(player.getName()) != Rank.LEADER) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be the " + ChatColor.YELLOW + "Leader " + ChatColor.WHITE + "to" + ChatColor.YELLOW + " unclaim all land");
			return;
		}
		for(String str_chunk : claimMap.keySet()) {
			SquadManager.claims.remove(str_chunk);
		}
		claimMap.clear();
		Main.mysql.doUpdate("DELETE FROM squad_claims WHERE squad='" + getName() + "'");
		M.message(player, "Squads", ChatColor.WHITE + "You unclaimed all land");
	}

	public void stealClaim(Player player, Chunk chunk, ClaimType type) {
		if(!SquadManager.isClaimed(chunk)) return;
		Squad owner = SquadManager.getOwner(chunk);
		if(owner.admin) return;
		ClaimType cur_type = SquadManager.getClaimType(chunk);
		if(cur_type != ClaimType.DEFAULT) return;
		Squad.unclaim(player, chunk, Source.OTHER_SQUAD);
		claim(chunk, type);
		M.message(player, "Squads", ChatColor.WHITE + "You stole a chunk from " + owner.getName());
	}

	public void leave(Player player) {
		if(!memberMap.containsKey(player.getName())) return;
		if(admin) {
			memberMap.remove(player.getName());
			M.message(player, "Squads", ChatColor.WHITE + "You left " + ChatColor.YELLOW + getName());
			Main.mysql.doUpdate("DELETE FROM squad_player WHERE player='" + player.getName() + "'");
			if(getChangeNameColour()) {
				TeamManager.boards.put(player.getName(), TeamManager.getNewScoreboard(player));
				player.setScoreboard(TeamManager.boards.get(player.getName()));
				for(String members : memberMap.keySet()) {
					Player member = Bukkit.getPlayer(members);
					if(member != null) {
						if(TeamManager.boards.containsKey(member.getName())) {
							if(TeamManager.boards.get(member.getName()).getTeam("members").hasPlayer(Bukkit.getOfflinePlayer(player.getName()))) TeamManager.boards.get(member.getName()).getTeam("members").removePlayer(Bukkit.getOfflinePlayer(player.getName())); 
						}
						else TeamManager.boards.put(member.getName(), TeamManager.getNewScoreboard(member));
					}
				}
			}
			return;
		}
		if(getLand() >= getMaxLand() - getLandPerPlayer()) {
			int land_per_player = getLandPerPlayer();
			if(land_per_player > 1) {
				M.message(player, "Squads", ChatColor.WHITE + "You must unclaim " + land_per_player + " chunks before leaving");
				return;
			}
			M.message(player, "Squads", ChatColor.WHITE + "You must unclaim " + land_per_player + " chunk before leaving");
			return;
		}
		if(getMemberCount() == 1 && !admin) {
			disband(player);
			return;
		}
		if(getPower() >= getMaxPower()) alterPower(- getPowerPerPlayer());
		memberMap.remove(player.getName());
		Main.mysql.doUpdate("DELETE FROM squad_player WHERE player='" + player.getName() + "'");
		M.message(player, "Squads", ChatColor.WHITE + "You left " + ChatColor.YELLOW + getName());
		if(getChangeNameColour()) {
			TeamManager.boards.put(player.getName(), TeamManager.getNewScoreboard(player));
			player.setScoreboard(TeamManager.boards.get(player.getName()));
			for(String members : memberMap.keySet()) {
				Player member = Bukkit.getPlayer(members);
				if(member != null) {
					if(TeamManager.boards.containsKey(member.getName())) {
						if(TeamManager.boards.get(member.getName()).getTeam("members").hasPlayer(Bukkit.getOfflinePlayer(player.getName()))) TeamManager.boards.get(member.getName()).getTeam("members").removePlayer(Bukkit.getOfflinePlayer(player.getName())); 
					}
					else TeamManager.boards.put(member.getName(), TeamManager.getNewScoreboard(member));
				}
			}
		}
	}

	public void kick(Player kicker, String target) {
		if(kicker == null) return;
		if(kicker.getName().equalsIgnoreCase(target)) {
			M.message(kicker, "Squads", ChatColor.WHITE + "You cannot kick yourself");
			return;
		}
		if(!memberMap.containsKey(kicker.getName())) return;
		if(getRank(kicker.getName()) != Rank.LEADER) {
			M.message(kicker, "Squads", ChatColor.WHITE + "You must be the " + ChatColor.YELLOW + "Leader " + ChatColor.WHITE + "to kick players");
			return;
		}
		if(!memberMap.containsKey(target)) {
			Player p_target = Bukkit.getPlayer(target);
			if(p_target == null) {
				M.message(kicker, "Squads", ChatColor.YELLOW + target + ChatColor.WHITE + " is not in your squad");
				return;
			}
			if(getLand() >= getMaxLand() - getLandPerPlayer()) {
				int land_per_player = getLandPerPlayer();
				if(land_per_player > 1) {
					M.message(kicker, "Squads", ChatColor.WHITE + "You must unclaim " + ChatColor.YELLOW + land_per_player + ChatColor.WHITE + " chunks before leaving");
					return;
				}
				M.message(kicker, "Squads", ChatColor.WHITE + "You must unclaim " + ChatColor.YELLOW + land_per_player + ChatColor.WHITE + " chunk before leaving");
				return;
			}
			if(getPower() >= getMaxPower()) alterPower(- getPowerPerPlayer());
			memberMap.remove(p_target.getName());
			Main.mysql.doUpdate("DELETE FROM squad_player WHERE player='" + p_target.getName() + "'");
			M.message(p_target, "Squads", ChatColor.WHITE + "You were kicked from " + ChatColor.YELLOW + getName() + ChatColor.WHITE + " by " + ChatColor.YELLOW + kicker.getName());
			if(getChangeNameColour()) {
				TeamManager.boards.put(p_target.getName(), TeamManager.getNewScoreboard(p_target));
				p_target.setScoreboard(TeamManager.boards.get(p_target.getName()));
				for(String members : memberMap.keySet()) {
					Player member = Bukkit.getPlayer(members);
					if(member != null) {
						if(TeamManager.boards.containsKey(member.getName())) {
							if(TeamManager.boards.get(member.getName()).getTeam("members").hasPlayer(Bukkit.getOfflinePlayer(p_target.getName()))) TeamManager.boards.get(member.getName()).getTeam("members").removePlayer(Bukkit.getOfflinePlayer(p_target.getName())); 
						}
						else TeamManager.boards.put(member.getName(), TeamManager.getNewScoreboard(member));
					}
				}
			}
			return;
		}
		if(memberMap.containsKey(target)) {
			Player p_target = Bukkit.getPlayer(target);
			if(getLand() >= getMaxLand() - getLandPerPlayer()) {
				int land_per_player = getLandPerPlayer();
				if(land_per_player > 1) {
					M.message(kicker, "Squads", ChatColor.WHITE + "You must unclaim " + ChatColor.YELLOW + land_per_player + ChatColor.WHITE + " chunks before leaving");
					return;
				}
				M.message(kicker, "Squads", ChatColor.WHITE + "You must unclaim " + ChatColor.YELLOW + land_per_player + ChatColor.WHITE + " chunk before leaving");
				return;
			}
			if(getPower() >= getMaxPower()) alterPower(- getPowerPerPlayer());
			memberMap.remove(target);
			Main.mysql.doUpdate("DELETE FROM squad_player WHERE player='" + target + "'");
			if(p_target == null) {
				sendMessage(ChatColor.YELLOW + target + ChatColor.WHITE + " was kicked by " + ChatColor.YELLOW + kicker.getName());
				for(String members : memberMap.keySet()) {
					Player member = Bukkit.getPlayer(members);
					if(member != null) {
						if(TeamManager.boards.containsKey(member.getName())) {
							if(TeamManager.boards.get(member.getName()).getTeam("members").hasPlayer(Bukkit.getOfflinePlayer(target))) TeamManager.boards.get(member.getName()).getTeam("members").removePlayer(Bukkit.getOfflinePlayer(target)); 
						}
						else TeamManager.boards.put(member.getName(), TeamManager.getNewScoreboard(member));
					}
				}
				return;
			}
			M.message(p_target, "Squads", ChatColor.WHITE + "You were kicked from " + ChatColor.YELLOW + getName() + ChatColor.WHITE + " by " + ChatColor.YELLOW + kicker.getName());
			sendMessage(ChatColor.YELLOW + p_target.getName() + ChatColor.WHITE + " was kicked by " + ChatColor.YELLOW + kicker.getName());
			if(getChangeNameColour()) {
				TeamManager.boards.put(p_target.getName(), TeamManager.getNewScoreboard(p_target));
				p_target.setScoreboard(TeamManager.boards.get(p_target.getName()));
				for(String members : memberMap.keySet()) {
					Player member = Bukkit.getPlayer(members);
					if(member != null) {
						if(TeamManager.boards.containsKey(member.getName())) {
							if(TeamManager.boards.get(member.getName()).getTeam("members").hasPlayer(Bukkit.getOfflinePlayer(p_target.getName()))) TeamManager.boards.get(member.getName()).getTeam("members").removePlayer(Bukkit.getOfflinePlayer(p_target.getName())); 
						}
						else TeamManager.boards.put(member.getName(), TeamManager.getNewScoreboard(member));
					}
				}
			}
			return;
		}
	}

	public List<String> getEnemies() {
		ArrayList<String> enemies = new ArrayList<String>();
		for(String enemy : enemyMap.keySet()) {
			Squad squad = SquadManager.getSquad(enemy);
			if(squad != null) {
				for(String player : squad.memberMap.keySet()) {
					enemies.add(player);
				}
			}
		}
		return enemies;
	}

	public List<String> getAllies() {
		ArrayList<String> allies = new ArrayList<String>();
		for(Entry<String, Boolean> entry : allyMap.entrySet()) {
			Squad squad = SquadManager.getSquad(entry.getKey());
			if(squad != null) {
				if(!getTrust(squad.getName())) {
					for(String player : squad.memberMap.keySet()) {
						allies.add(player);
					}
				}
			}
		}
		return allies;
	}

	public List<String> getNeutrals() {
		ArrayList<String> neutrals = new ArrayList<String>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			Squad squad = SquadManager.getSquad(player);
			if(squad == null) {
				neutrals.add(player.getName());
			} else {
				if(!squad.areAlly(getName()) && !squad.areEnemy(getName()) && !squad.getTrust(getName()) && !getTrust(squad.getName())) {
					for(String member : squad.memberMap.keySet()) {
						neutrals.add(member);
					}
				}
			}
		}
		return neutrals;
	}

	public List<String> getTrusts() {
		ArrayList<String> trusts = new ArrayList<String>();
		for(Entry<String, Boolean> entry : allyMap.entrySet()) {
			Squad squad = SquadManager.getSquad(entry.getKey());
			if(squad != null) {
				if(getTrust(squad.getName())) {
					for(String player : squad.memberMap.keySet()) {
						trusts.add(player);
					}
				}
			}
		}
		return trusts;
	}

	public List<String> getMembers() {
		ArrayList<String> members = new ArrayList<String>();
		for(String member : memberMap.keySet()) {
			members.add(member);
		}
		return members;
	}

	public void claim(Player player, Chunk c1, Chunk c2, ClaimType type) {
		if(!c1.getWorld().getName().equalsIgnoreCase(c2.getWorld().getName())) {
			M.message(player, "Squad", ChatColor.WHITE + "You cannot selection claim through different worlds");
			return;
		}
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) {
			M.message(player, "Squads", ChatColor.WHITE + "You are not in a squad");
			return;
		}
		ArrayList<Chunk> selected = new ArrayList<Chunk>();
		selected.add(c1);
		selected.add(c2);
		if(c1.getX() > c2.getX() && c1.getZ() > c2.getZ()) {
			for(int x = c1.getX(); x > c2.getX(); x--) {
				for(int z = c1.getZ(); z > c2.getZ(); z--) {
					Chunk c = c1.getWorld().getChunkAt(x, z);
					if(!Squad.getCanClaimNextTo()) {
						Chunk plusX = c.getWorld().getChunkAt(c.getX() + 1, c.getZ());
						Chunk subX = c.getWorld().getChunkAt(c.getX() - 1, c.getZ());
						Chunk plusZ = c.getWorld().getChunkAt(c.getX(), c.getZ() + 1);
						Chunk subZ = c.getWorld().getChunkAt(c.getX(), c.getZ() - 1);
						boolean canClaimHere = true;
						if(SquadManager.isClaimed(plusX)) {
							Squad owner = SquadManager.getOwner(plusX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subX)) {
							Squad owner = SquadManager.getOwner(subX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(plusZ)) {
							Squad owner = SquadManager.getOwner(plusZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subZ)) {
							Squad owner = SquadManager.getOwner(subZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						if(!canClaimHere) {
							M.message(player, "Squads", ChatColor.WHITE + "You cannot claim next to another squad");
							return;
						}
					}
					selected.add(c);
				}
			}
		}
		if(c1.getX() < c2.getX() && c1.getZ() < c2.getZ()) {
			for(int x = c1.getX(); x < c2.getX(); x++) {
				for(int z = c1.getZ(); z < c2.getZ(); z++) {
					Chunk c = c1.getWorld().getChunkAt(x, z);
					if(!Squad.getCanClaimNextTo()) {
						Chunk plusX = c.getWorld().getChunkAt(c.getX() + 1, c.getZ());
						Chunk subX = c.getWorld().getChunkAt(c.getX() - 1, c.getZ());
						Chunk plusZ = c.getWorld().getChunkAt(c.getX(), c.getZ() + 1);
						Chunk subZ = c.getWorld().getChunkAt(c.getX(), c.getZ() - 1);
						boolean canClaimHere = true;
						if(SquadManager.isClaimed(plusX)) {
							Squad owner = SquadManager.getOwner(plusX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subX)) {
							Squad owner = SquadManager.getOwner(subX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(plusZ)) {
							Squad owner = SquadManager.getOwner(plusZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subZ)) {
							Squad owner = SquadManager.getOwner(subZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						if(!canClaimHere) {
							M.message(player, "Squads", ChatColor.WHITE + "You cannot claim next to another squad");
							return;
						}
					}
					selected.add(c);
				}
			}
		}
		if(c1.getX() > c2.getX() && c1.getZ() < c2.getZ()) {
			for(int x = c1.getX(); x > c2.getX(); x--) {
				for(int z = c1.getZ(); z < c2.getZ(); z++) {
					Chunk c = c1.getWorld().getChunkAt(x, z);
					if(!Squad.getCanClaimNextTo()) {
						Chunk plusX = c.getWorld().getChunkAt(c.getX() + 1, c.getZ());
						Chunk subX = c.getWorld().getChunkAt(c.getX() - 1, c.getZ());
						Chunk plusZ = c.getWorld().getChunkAt(c.getX(), c.getZ() + 1);
						Chunk subZ = c.getWorld().getChunkAt(c.getX(), c.getZ() - 1);
						boolean canClaimHere = true;
						if(SquadManager.isClaimed(plusX)) {
							Squad owner = SquadManager.getOwner(plusX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subX)) {
							Squad owner = SquadManager.getOwner(subX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(plusZ)) {
							Squad owner = SquadManager.getOwner(plusZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subZ)) {
							Squad owner = SquadManager.getOwner(subZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						if(!canClaimHere) {
							M.message(player, "Squads", ChatColor.WHITE + "You cannot claim next to another squad");
							return;
						}
					}
					selected.add(c);
				}
			}
		}
		if(c1.getX() < c2.getX() && c1.getZ() > c2.getZ()) {
			for(int x = c1.getX(); x < c2.getX(); x++) {
				for(int z = c1.getZ(); z > c2.getZ(); z--) {
					Chunk c = c1.getWorld().getChunkAt(x, z);
					if(!Squad.getCanClaimNextTo()) {
						Chunk plusX = c.getWorld().getChunkAt(c.getX() + 1, c.getZ());
						Chunk subX = c.getWorld().getChunkAt(c.getX() - 1, c.getZ());
						Chunk plusZ = c.getWorld().getChunkAt(c.getX(), c.getZ() + 1);
						Chunk subZ = c.getWorld().getChunkAt(c.getX(), c.getZ() - 1);
						boolean canClaimHere = true;
						if(SquadManager.isClaimed(plusX)) {
							Squad owner = SquadManager.getOwner(plusX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subX)) {
							Squad owner = SquadManager.getOwner(subX);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(plusZ)) {
							Squad owner = SquadManager.getOwner(plusZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						else if(SquadManager.isClaimed(subZ)) {
							Squad owner = SquadManager.getOwner(subZ);
							if(!owner.getName().equalsIgnoreCase(squad.getName())) {
								canClaimHere = false;
							}
						}
						if(!canClaimHere) {
							M.message(player, "Squads", ChatColor.WHITE + "You cannot claim next to another squad");
							return;
						}
					}
					selected.add(c);
				}
			}
		}
		int claimed_count = 0;
		for(Chunk chunk : selected) {
			if(SquadManager.isClaimed(chunk)) {
				Squad owner = SquadManager.getOwner(chunk);
				if(owner != null) {
					if(!owner.getName().equalsIgnoreCase(getName())) claimed_count++;
				}
			}
		}
		if(claimed_count > 0) {
			M.message(player, "Squads", ChatColor.WHITE + "You may not claim these chunks as " + ChatColor.YELLOW + claimed_count + ChatColor.WHITE + "/" + ChatColor.YELLOW + selected.size() + ChatColor.WHITE + " are already claimed");
			return;
		}
		for(Chunk chunk : selected) {
			claim(chunk, type);
		}
		M.message(player, "Squads", ChatColor.WHITE + "You claimed " + ChatColor.YELLOW + (selected.size() - 1) + ChatColor.WHITE + " chunks");
	}

	public void disband(Player player) {
		if(getRank(player.getName()) != Rank.LEADER) {
			M.message(player, "Squads", ChatColor.WHITE + "You must be the " + ChatColor.YELLOW + "Leader " + ChatColor.WHITE + "to disband");
			return;
		}
		if(getChangeNameColour()) {
			for(String ally : getAllies()) {
				Player ally_p = Bukkit.getPlayer(ally);
				if(ally_p != null) {
					Scoreboard newBoard = TeamManager.getNewScoreboard(ally_p);
					ally_p.setScoreboard(newBoard);
					TeamManager.boards.put(ally_p.getName(), newBoard);
				}
			}
			for(String enemy : getEnemies()) {
				Player enemy_p = Bukkit.getPlayer(enemy);
				if(enemy_p != null) {
					Scoreboard newBoard = TeamManager.getNewScoreboard(enemy_p);
					enemy_p.setScoreboard(newBoard);
					TeamManager.boards.put(enemy_p.getName(), newBoard);
				}
			}
		}
		for(String ally_squad : allyMap.keySet()) {
			Squad squad = SquadManager.getSquad(ally_squad);
			if(squad != null) {
				if(squad.allyMap.containsKey(getName())) squad.allyMap.remove(getName());
			}
		}
		for(String enemy_squad : enemyMap.keySet()) {
			Squad squad = SquadManager.getSquad(enemy_squad);
			if(squad != null) {
				if(squad.enemyMap.containsKey(getName())) squad.enemyMap.remove(getName());
			}
		}
		sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " disbanded " + ChatColor.YELLOW + getName());
		Main.mysql.doUpdate("DELETE FROM squad WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_ally WHERE squad='" + getName() + "' OR ally='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_claims WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_enemy WHERE squad='" + getName() + "' OR enemy='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_home WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_player WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_bank WHERE squad='" + getName() + "'");
		SquadManager.squads.remove(getName());
		if(getChangeNameColour()) {
			for(String member : memberMap.keySet()) {
				Player p_member = Bukkit.getPlayer(member);
				if(p_member != null) {
					TeamManager.boards.put(p_member.getName(), TeamManager.getNewScoreboard(player));
					p_member.setScoreboard(TeamManager.boards.get(p_member.getName()));
				}
			}
		}
	}

	public void forceDisband() {
		for(String ally : allyMap.keySet()) {
			Squad squad = SquadManager.getSquad(ally);
			if(squad != null) {
				if(squad.allyMap.containsKey(getName())) squad.allyMap.remove(getName());
			}
		}
		for(String enemy : enemyMap.keySet()) {
			Squad squad = SquadManager.getSquad(enemy);
			if(squad != null) {
				if(squad.enemyMap.containsKey(getName())) squad.enemyMap.remove(getName());
			}
		}
		if(getChangeNameColour()) {
			for(String ally : getAllies()) {
				Player ally_p = Bukkit.getPlayer(ally);
				if(ally_p != null) {
					Scoreboard newBoard = TeamManager.getNewScoreboard(ally_p);
					ally_p.setScoreboard(newBoard);
					TeamManager.boards.put(ally_p.getName(), newBoard);
				}
			}
			for(String enemy : getEnemies()) {
				Player enemy_p = Bukkit.getPlayer(enemy);
				if(enemy_p != null) {
					Scoreboard newBoard = TeamManager.getNewScoreboard(enemy_p);
					enemy_p.setScoreboard(newBoard);
					TeamManager.boards.put(enemy_p.getName(), newBoard);
				}
			}
		}
		sendMessage(ChatColor.WHITE + "Your squad was disbanded via " + ChatColor.YELLOW + "Server Admin");
		Main.mysql.doUpdate("DELETE FROM squad WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_ally WHERE squad='" + getName() + "' OR ally='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_claims WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_enemy WHERE squad='" + getName() + "' OR enemy='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_home WHERE squad='" + getName() + "'");
		Main.mysql.doUpdate("DELETE FROM squad_player WHERE squad='" + getName() + "'");
		SquadManager.squads.remove(getName());
		if(getChangeNameColour()) {
			for(String member : memberMap.keySet()) {
				Player p_member = Bukkit.getPlayer(member);
				if(p_member != null) {
					TeamManager.boards.put(p_member.getName(), TeamManager.getNewScoreboard(p_member));
					p_member.setScoreboard(TeamManager.boards.get(p_member.getName()));
				}
			}
		}
	}

	public static void toggleAdminMode(Player player) {
		if(player == null) return;
		if(adminMode.contains(player.getName())) {
			adminMode.remove(player.getName());
			if(WandSession.sessions.containsKey(player.getName())) WandSession.sessions.remove(player.getName());
			M.message(player, "Squads", ChatColor.WHITE + "Admin Mode disabled");
			return;
		}
		if(!adminMode.contains(player.getName())) {
			adminMode.add(player.getName());
			M.message(player, "Squads", ChatColor.WHITE + "Admin Mode enabled");
			return;
		}
	}

	public static boolean isAdminMode(Player player) {
		if(player == null) return false;
		return adminMode.contains(player.getName());
	}

	public static int getMinimumPower() {
		return Main.config.getInt("squads.min_power");
	}

	public static void databaseCheck() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad (squad TEXT, power INT(11), admin TINYINT(1), date TEXT, create_time BIGINT(20), lastPower BIGINT(20))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_ally (squad TEXT, ally TEXT, trust TINYINT(1))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_claims (squad TEXT, chunk TEXT, type TEXT)");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_enemy (squad TEXT, enemy TEXT)");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_home (squad TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw FLOAT, world TEXT)");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_player (player TEXT, role TEXT, squad TEXT, firstBankUse BIGINT(20), hourWithdraw INT(11))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_bank (squad TEXT, balance INT(11), withdraw_limit INT(11))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_dominance (squad TEXT, target TEXT, amount INT(11))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS squad_bank_log (player TEXT, squad TEXT, amount INT(11), useTime BIGINT(20))");
	}

	public static void sendChat(Player player, String message) {
		if(player == null) return;
//		if(!Moderation.chat) {
//			if(!Permissions.isStaff(player.getName())) {
//				M.message(player, "Chat", ChatColor.WHITE + "Chat is disabled");
//				return;
//			}
//		}
		if(!ChatManager.getCanChat(player.getName())) {
			M.message(player, "Chat", ChatColor.WHITE + "You are sending messages too frequently");
			return;
		}
		Channel active = ChannelManager.getActiveChannel(player.getName());
		if(active != null) {
			active.sendMessage(player.getName(), message);
			ChatManager.lastChat.put(player.getName(), System.currentTimeMillis());
			return;
		}
		Squad squad = SquadManager.getSquad(player);
		if(squad != null) {
			if(squad.isSquadChat(player.getName())) {
				squad.sendChannelMessage(player.getName(), message);
				ChatManager.lastChat.put(player.getName(), System.currentTimeMillis());
				return;
			}
		}
//		if(Mute.isMuted(player)) {
//			Mute mute = Mute.getMute(player.getName());
//			M.message(player, "Mute", ChatColor.WHITE + "Muted [" + ChatColor.GREEN + mute.getRemainingString() + ChatColor.WHITE + "] for " + ChatColor.GREEN + mute.getReason());
//			ChatManager.lastChat.put(player.getName(), System.currentTimeMillis());
//			return;
//		}
		HashSet<String> receivers = new HashSet<String>();
		for(Player players : Bukkit.getServer().getOnlinePlayers()) {
			receivers.add(players.getName());
		}
		for(String receiver : receivers) {
			Player p_receiver = Bukkit.getPlayer(receiver);
			if(p_receiver == null) receivers.remove(receiver);
//			if(Ignore.isIgnored(p_receiver, player.getName())) receivers.remove(p_receiver);
		}
		ChatManager.lastChat.put(player.getName(), System.currentTimeMillis());
		for(String rec : receivers) {
			Player r = Bukkit.getPlayer(rec);
			Squad r_squad = SquadManager.getSquad(r);
			if(squad == null) {
				r.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " " + message);
			} else if(r_squad == null) {
				r.sendMessage(ChatColor.GOLD + squad.getName() + " " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " " + message);
			} else {
				Relation relation = r_squad.getRelation(squad.getName());
				if(relation == Relation.ENEMY) r.sendMessage(ChatColor.DARK_RED + squad.getName() + " " + ChatColor.RED + player.getName() + ChatColor.WHITE + " " + message);
				else if(relation == Relation.ALLY || relation == Relation.TRUST) r.sendMessage(ChatColor.DARK_GREEN + squad.getName() + " " + ChatColor.GREEN + player.getName() + ChatColor.WHITE + " " + message);
				else if(relation == Relation.SELF)  r.sendMessage(ChatColor.DARK_AQUA + squad.getName() + " " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + " " + message);
				else r.sendMessage(ChatColor.GOLD + squad.getName() + " " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " " + message);
			}
		}
	}

	public static short booleanToShort(boolean value) {
		if(value == true) return 1;
		else return 0;
	}

	public static void fillDisallowInteract() {
		String[] ids = null;
		if(!Main.config.getString("squads.disallow_interact").contains(",")) {
			try {
				int parsed = Integer.parseInt(Main.config.getString("squads.disallow_interact"));
				denyInteract.add(parsed);
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'disallow_interact' contains a non-integer");
			}
		} else {
			ids = Main.config.getString("squads.disallow_interact").split(",");
			for(String id : ids) {
				try {
					int parsed = Integer.parseInt(id);
					denyInteract.add(parsed);
				} catch(NumberFormatException e) {
					Main.logger.log(Level.WARNING, "Squads > Config Element 'disallow_interact' contains a non-integer");
				}
			}
		}

	}

	public static void fillAllowInteract() {
		String[] ids = null;
		if(!Main.config.getString("squads.allow_interact").contains(",")) {
			try {
				int parsed = Integer.parseInt(Main.config.getString("squads.allow_interact"));
				allowInteract.add(parsed);
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'allow_interact' contains a non-integer");
			}
		} else {
			ids = Main.config.getString("squads.allow_interact").split(",");
			for(String id : ids) {
				try {
					int parsed = Integer.parseInt(id);
					allowInteract.add(parsed);
				} catch(NumberFormatException e) {
					Main.logger.log(Level.WARNING, "Squads > Config Element 'allow_interact' contains a non-integer");
				}	
			}
		}
	}

	public static void fillAllowPlace() {
		if(Main.config.getString("squads.allow_place") == null) return;
		if(!Main.config.getString("squads.allow_place").contains(",")) {
			try {
				int parsed = Integer.parseInt(Main.config.getString("squads.allow_place"));
				allowPlace.add(parsed);
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'allow_place' contains a non-integer");
			}
		} else {
			String[] ids = Main.config.getString("squads.allow_place").split(",");
			for(String id : ids) {
				try {
					int parsed = Integer.parseInt(id);
					allowPlace.add(parsed);
				} catch(NumberFormatException e) {
					Main.logger.log(Level.WARNING, "Squads > Config Element 'allow_place' contains a non-integer");
				}
			}
		}
	}

	public static void fillAllowBreak() {
		if(Main.config.getString("squads.allow_break") == null) return;
		if(!Main.config.getString("squads.allow_break").contains(",")) {
			try {
				int parsed = Integer.parseInt(Main.config.getString("squads.allow_break"));
				allowBreak.add(parsed);
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'allow_break' contains a non-integer");
			}
		} else {
			String[] ids = Main.config.getString("squads.allow_break").split(",");
			for(String id : ids) {
				try {
					int parsed = Integer.parseInt(id);
					allowBreak.add(parsed);
				} catch(NumberFormatException e) {
					Main.logger.log(Level.WARNING, "Squads > Config Element 'allow_break' contains a non-integer");
				}
			}
		}
	}

	public static void fillInteractableBlocks() {
		if(Main.config.getString("squads.all_interactable_blocks") == null) return;
		if(!Main.config.getString("squads.all_interactable_blocks").contains(",")) {
			try {
				int parsed = Integer.parseInt(Main.config.getString("squads.all_interactable_blocks"));
				interactableBlocks.add(parsed);
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'all_interactable_blocks' contains a non-integer");
			}
		} else {
			String[] ids = Main.config.getString("squads.all_interactable_blocks").split(",");
			for(String id : ids) {
				try {
					int parsed = Integer.parseInt(id);
					interactableBlocks.add(parsed);
				} catch(NumberFormatException e) {
					Main.logger.log(Level.WARNING, "Squads > Config Element 'all_interactable_blocks' contains a non-integer");
				}
			}
		}
	}

	public static void fillTrustInteractableBlocks() {
		if(Main.config.getString("squads.trust_interactable_blocks") == null) {
			Main.logger.log(Level.INFO, "Squads > Config Element 'interactable_blocks' is null");
			return;
		}
		if(Main.config.getString("squads.trust_interactable_blocks").contains(",")) {
			try {
				for(String id : Main.config.getString("squads.trust_interactable_blocks").split(",")) {
					int parsed = Integer.parseInt(id);
					trustInteractableBlocks.add(parsed);
				}
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'trust_interactable_blocks' contains a non-integer");
			}
		} else {
			try {
				int parsed = Integer.parseInt(Main.config.getString("squads.trust_interactable_blocks"));
				trustInteractableBlocks.add(parsed);
			} catch(NumberFormatException e) {
				Main.logger.log(Level.WARNING, "Squads > Config Element 'trust_interactable_blocks' contains a non-integer");
			}
		}
	}

	public static void fillAllSets() {
		fillAllowBreak();
		fillAllowInteract();
		fillAllowPlace();
		fillDisallowInteract();
		fillInteractableBlocks();
		fillTrustInteractableBlocks();
	}

	public static void clearAll(Player player) {
		if(player == null) return;
		if(!player.isOp()) {
			M.sendLackPermsMessage(player);
			return;
		}
		if(!adminActionConfirm.containsKey(player.getName())) {
			adminActionConfirm.put(player.getName(), "clear_all");
			M.message(player, "Squads", ChatColor.WHITE + "If you are sure you want to " + ChatColor.DARK_RED + "clear all squads data permanently " + ChatColor.WHITE + "write " + ChatColor.YELLOW + " /sa clear" + ChatColor.WHITE + " again");
			return;
		}
		if(adminActionConfirm.get(player.getName()).equalsIgnoreCase("clear_all")) {
			Main.mysql.doUpdate("DELETE FROM squad");
			Main.mysql.doUpdate("DELETE FROM squad_ally");
			Main.mysql.doUpdate("DELETE FROM squad_enemy");
			Main.mysql.doUpdate("DELETE FROM squad_claims");
			Main.mysql.doUpdate("DELETE FROM squad_home");
			Main.mysql.doUpdate("DELETE FROM squad_player");
			Main.mysql.doUpdate("DELETE FROM squad_bank");
			Main.mysql.doUpdate("DELETE FROM squad_bank_log");
			Main.mysql.doUpdate("DELETE FROM squad_dominance");
			SquadManager.claims.clear();
			SquadManager.powerRegen.clear();
			SquadManager.squads.clear();
			M.broadcast("Squads", ChatColor.AQUA + "All Squads data has been cleared by " + ChatColor.DARK_AQUA + player.getName());
		}

	}

	public static boolean disallowInteract(int id) {
		if(denyInteract.isEmpty()) fillDisallowInteract();
		return denyInteract.contains(Integer.valueOf(id));
	}

	public static boolean allowInteract(int id) {
		if(allowInteract.isEmpty()) fillAllowInteract();
		return allowInteract.contains(Integer.valueOf(id));
	}

	public static boolean denyPlace(int id) {
		if(allowPlace.isEmpty()) fillAllowPlace();
		return !allowPlace.contains(Integer.valueOf(id));
	}

	public static boolean allowPlace(int id) {
		if(allowPlace.isEmpty()) fillAllowPlace();
		return allowPlace.contains(Integer.valueOf(id));
	}

	public static boolean canBreak(int id) {
		if(allowBreak.isEmpty()) fillAllowBreak();
		return allowBreak.contains(Integer.valueOf(id));
	}

	public static String getUsage(String command) {
		if(command.equalsIgnoreCase("ally")) return M.getUsage("/s ally <squad>");
		else if(command.equalsIgnoreCase("enemy")) return M.getUsage("/s enemy <squad>");
		else if(command.equalsIgnoreCase("invite") || command.equalsIgnoreCase("i")) return M.getUsage("/s <invite/i> <player>");
		else if(command.equalsIgnoreCase("trust")) return M.getUsage("/s trust <squad>");
		else if(command.equalsIgnoreCase("promote")) return M.getUsage("/s promote <player>");
		else if(command.equalsIgnoreCase("demote")) return M.getUsage("/s demote <player>");
		else if(command.equalsIgnoreCase("kick")) return M.getUsage("/s kick <player>");
		else if(command.equalsIgnoreCase("leader")) return M.getUsage("/s leader <player>");
		else if(command.equalsIgnoreCase("join")) return M.getUsage("/s join <squad>");
		else if(command.equalsIgnoreCase("untrust") || command.equalsIgnoreCase("revoketrust")) return M.getUsage("/s untrust <squad>");
		else if(command.equalsIgnoreCase("neutral")) return M.getUsage("/s neutral <squad>");
		else return null;
	}

	public static boolean getDisableFireSpread() {
		return Main.config.getBoolean("squads.disable_fire_spread");
	}

	public static boolean getTakeFallDamageInSafeZone() {
		return Main.config.getBoolean("squads.fall_damage_in_safezone");
	}

	public static boolean getBlockUsageOfPressurePlates() {
		return Main.config.getBoolean("squads.block_usage_pressure_plates");
	}
	
	public static int getMaxTrustedAllies() {
		return Main.config.getInt("squads.max_trusted_allies");
	}
	
	public static int getMaxBankBalance() {
		return Main.config.getInt("squads.max_bank_balance");
	}
	
	public static int getHourWithdrawLimit() {
		return Main.config.getInt("squads.hourly_bank_withdraw_limit");
	}

	public enum Rank {
		LEADER(3),
		ADMIN(2),
		MEMBER(1),
		NONE(0);

		private int id;

		Rank(int id) {
			this.id = id;
		}

		public int getID() {
			return this.id;
		}

		public static Rank getRank(int id) {
			if(id == 3) return LEADER;
			else if(id == 2) return ADMIN;
			else if(id == 1) return MEMBER;
			else if(id == 0) return NONE;
			else return NONE;
		}

		public static Rank getNextRank(Rank rank) {
			if(rank == MEMBER) return ADMIN;
			else if(rank == ADMIN) return LEADER;
			else return NONE;
		}

		public static String format(Rank rank) {
			return WordUtils.capitalize(rank.toString().toLowerCase());
		}

		public static Rank getRank(String rank) {
			if(rank.equalsIgnoreCase("leader")) return LEADER;
			else if(rank.equalsIgnoreCase("admin")) return ADMIN;
			else if(rank.equalsIgnoreCase("member")) return MEMBER;
			else if(rank.equalsIgnoreCase("none")) return NONE;
			else return null;
		}
	}

	public enum ClaimType {
		DEFAULT("Default"),
		SAFE("Safe");

		public String formatName;

		ClaimType(String formatName) {
			this.formatName = formatName;
		}

		public String getFormattedName() {
			return this.formatName;
		}

		public static ClaimType getType(String type) {
			if(type.equalsIgnoreCase("default") || type.equalsIgnoreCase("1")) return DEFAULT;
			else if(type.equalsIgnoreCase("safe") || type.equalsIgnoreCase("2")) return SAFE;
			else return null;
		}
	}

	public enum Source {
		SERVER_ADMIN("Server Admin"),
		OTHER_SQUAD("Other Squad");

		String message;

		Source(String message) {
			this.message = message;
		}

		public String getMessage() {
			return this.message;
		}
	}

	public enum Relation {
		ENEMY("enemy", "enemies"),
		ALLY("ally", "allies"),
		TRUST("trust", "trusts"),
		SELF("self", "members"),
		NEUTRAL("neutral", "neutrals");

		public String name;
		public String team;

		Relation(String name, String team) {
			this.name = name;
			this.team = team;
		}

		public String getName() {
			return this.name;
		}

		public String getTeam() {
			return this.team;
		}
	}
	
	public enum Wildcard {
		EXTRA_POWER(3, 100000),
		EXTRA_LAND(3, 80000),
		EXTRA_ALLIES(4, 50000),
		REDUCED_POWER_REGENERATION(2, 90000);
		
		private int max_level;
		private int cost;
		
		Wildcard(int max_level, int cost) {
			this.max_level = max_level;
			this.cost = cost;
		}
		
		public int getMaxLevel() {
			return this.max_level;
		}
		
		public int getCost() {
			return this.cost;
		}
		
//		public int getCost(int level) {
//			if(level > max_level) return -1;
//			return (level * cost) +
//		}
		
	}
}
