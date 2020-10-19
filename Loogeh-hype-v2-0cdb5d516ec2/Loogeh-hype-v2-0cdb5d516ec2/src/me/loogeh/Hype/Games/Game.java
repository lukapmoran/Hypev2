package me.loogeh.Hype.Games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Games.GameInventory.GIType;
import me.loogeh.Hype.Inventory.HInventory;
import me.loogeh.Hype.Inventory.IFlag;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Miscellaneous.Countdown;
import me.loogeh.Hype.Utility.utilMath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Game implements Listener {
	
	private static HashMap<Integer, Game> games = new HashMap<Integer, Game>();
	
	public static HInventory game_selector = new HInventory(C.boldGreen + "Select Game", 5);
	
	private int id;
	private Map map;
	private HashMap<UUID, Team> players = new HashMap<UUID, Team>();
	private HashMap<String, ClassType> selectedKits = new HashMap<String, ClassType>();
	private GameType type;
	private Location lobby_loc;
	private boolean usePowerups = true;
	private HashMap<PowerUp, Location> powerUps = new HashMap<PowerUp, Location>();
	private HashMap<PowerUp, Long> lastPowerUpUsage = new HashMap<PowerUp, Long>();
	private HashSet<String> spectators = new HashSet<String>();
	private GameStatus status = GameStatus.LOBBY;
	private int max_players;
	private int win_score;
	private int join_cost = 1000;
	private HashMap<Team, Integer> team_scores = new HashMap<Team, Integer>();
	private HashMap<String, StatSession> stats = new HashMap<String, StatSession>();
//	private HashMap<String, GameBet> bets = new HashMap<String, GameBet>(); //GameBet stores amount of money and team being betted on
	public Countdown countdown;
	
//	private String chat_format = ChatColor.BLUE + "$$team$$ - " + ChatColor.WHITE + "$$message$$";
	
	public Game(Map map, GameType type, Location lobby_loc) {
		this.id = getNextID();
		if(this.id == -1) {
			try {
				throw new Exception("Failed to find valid ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!map.getGameTypes().contains(type)) {
			try {
				throw new GameException(type.getName() + " cannot be played on " + map.getName());
			} catch (GameException e) {
				e.printStackTrace();
			}
		}
		if(!lobby_loc.getWorld().getName().equalsIgnoreCase("game_world"))
			try {
				throw new GameException("Lobby location can only be in world 'game_world'");
			} catch (GameException e) {
				e.printStackTrace();
		}
		if(type == null) {
			try {
				throw new NullPointerException("GameType cannot be null");
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		if(games.containsKey(this.id)) {
			try {
				throw new GameException("ID already in use");
			} catch(GameException e) {
				e.printStackTrace();
			}
		}
		this.map = map;
		this.type = type;
		this.lobby_loc = lobby_loc;
		this.max_players = map.getPlayerLimit();
		this.win_score = type.getMinWinScore();
		games.put(this.id, this);
		Main.get().getServer().getPluginManager().registerEvents(this, Main.get());
	}
	
	public int getID() {
		return this.id;
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public GameType getType() {
		return this.type;
	}
	
	public Location getLobbyLocation() {
		return this.lobby_loc;
	}
	
	public HashMap<UUID, Team> getPlayers() {
		return this.players;
	}
	
	public HashMap<PowerUp, Location> getPowerUps() {
		return this.powerUps;
	}
	
	public boolean getUsePowerUps() {
		return this.usePowerups;
	}
	
	public void setUsePowerUps(boolean use) {
		this.usePowerups = use;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void setLobbyLocation(Location location) {
		this.lobby_loc = location;
	}
	
	public HashMap<PowerUp, Long> getLastPowerUpUsage() {
		return lastPowerUpUsage;
	}
	
	public HashMap<String, ClassType> getSelectedKits() {
		return this.selectedKits;
	}
	
	public static List<Game> getGames() {
		return (List<Game>) games.values();
	}
	
	public HashMap<String, StatSession> getStatSessions() {
		return this.stats;
	}
	
	public StatSession getStats(String uuid) {
		if(getStatSessions().containsKey(uuid)) return getStatSessions().get(uuid);
		return null;
	}
	
	public int getMaxPlayers() {
		return this.max_players;
	}
	
	public int getWinScore() {
		return this.win_score;
	}
	
	public int getJoinCost() {
		return this.join_cost;
	}
	
	public HashMap<Team, Integer> getTeamScores() {
		return this.team_scores;
	}
	
	public Integer getScore(Team team) {
		if(!this.team_scores.containsKey(team)) return null;
		return this.team_scores.get(team);
	}
	
	public void addScore(Team team, int score) {
		if(!this.team_scores.containsKey(team)) return;
		int current = this.team_scores.get(team);
		int added = current + score;
		if(added > this.win_score) {
			added = this.win_score;
			this.team_scores.put(team, added);
//			win(); //make win method or hard code it here
		} else this.team_scores.put(team, current + score);
		
	}
	
	public HashSet<String> getSpectators() {
		return this.spectators;
	}
	
	public GameStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	
	public void setMaxPlayers(int max) {
		if(max > map.getPlayerLimit()) max = map.getPlayerLimit();
		this.max_players = max;
	}

	public void setWinScore(int win_score) {
		if(win_score > type.getMaxWinScore()) win_score = type.getMaxWinScore();
		this.win_score = win_score;
	}
	
	public void setJoinCost(int cost) {
		this.join_cost = cost;
	}
	
	public static int getNextID() {
		for(int i = 0; i < 1000; i++) {
			if(!games.containsKey(i)) return i;
		}
		return -1;
	}
	
	public Team getSmallestTeam() {
		return Team.RED;
	}
	
	public Team getTeam(String player) {
		if(players.containsKey(player)) return players.get(player);
		return null;
	}
	
	public Team getTeam(Player player) {
		if(player == null) return null;
		if(players.containsKey(player.getName())) return players.get(player.getName());
		return null;
	}
	
	public static <T extends Game> Game getGame(Player player) {
		if(player == null) return null;
		for(int i : games.keySet()) {
			Game game = games.get(i);
			if(game.getPlayers().containsKey(player.getName())) return game;
		}
		return null;
	}
	
	public static <T extends Game> Game getGame(int id) {
		if(!games.containsKey(id)) return null;
		return games.get(id);
	}
	
	public static int getGameCount(GameType type) {
		int count = 0;
		for(int id : games.keySet()) {
			if(games.get(id).getType().equals(type)) count++;
		}
		return count;
	}
	
	public void remove() {
		for(UUID key : this.players.keySet()) {
			Player player = Bukkit.getPlayer(key);
			if(player == null) continue;
			player.teleport(player.getWorld().getSpawnLocation());
		}
		if(games.containsKey(this.id)) games.remove(this.id);
	}
	
	public Game getInstance() {
		return this;
	}
	
	public static void fillSelector() {
		if(!game_selector.getFlags().contains(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM)) game_selector.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
		game_selector.setDestroyOnClose(false);
		game_selector.setItem(GameType.ARCTIC_BRAWL.getMetaItem(), 0, ChatColor.GRAY + "Eliminate other players by knocking", ChatColor.GRAY + "them into the freezing water surrounding", ChatColor.GRAY + "you", ChatColor.GRAY + "(Taken from AusMC)");
		game_selector.setItem(GameType.BATTLE_OF_HORSES.getMetaItem(), 1);
		game_selector.setItem(GameType.BOAT_WARS.getMetaItem(), 2);
		game_selector.setItem(GameType.CASTLE_DEFENSE.getMetaItem(), 3, ChatColor.GRAY + "One team is the attackers and the other are defenders.", ChatColor.GRAY + "The attackers have mutliple lives and limited items", ChatColor.GRAY + "and the defenders have an array of items", ChatColor.GRAY + "however, they only have 1 life", ChatColor.GRAY + "each team need to reduce the enemy's" + ChatColor.GRAY + "life count to 0 to win");
		game_selector.setItem(GameType.CTF.getMetaItem(), 4, ChatColor.GRAY + "Capture the enemy's flag until", ChatColor.GRAY + "your team reaches the win score");
		game_selector.setItem(GameType.FFA.getMetaItem(), 5, ChatColor.GRAY + "Battle all other players in the", ChatColor.GRAY + "game. Be the last man standing to", ChatColor.GRAY + "win");
		game_selector.setItem(GameType.FOUR_CORNERS.getMetaItem(), 6, ChatColor.GRAY + "There are four teams. Each team", ChatColor.GRAY + "spawns on one corner of the map.", ChatColor.GRAY + "Reach the win score to win");
		game_selector.setItem(GameType.HOT_POTATO.getMetaItem(), 7, ChatColor.GRAY + "A block of TNT is passed around,", ChatColor.GRAY + "get rid of it as soon as possible", ChatColor.GRAY + "if it is given to you");
		game_selector.setItem(GameType.KIT_ARENA.getMetaItem(), 8, ChatColor.GRAY + "Select a kit and attack the", ChatColor.GRAY + "other team until the win", ChatColor.GRAY + "score is obtained by a team");
		game_selector.setItem(GameType.KOTH.getMetaItem(), 18, ChatColor.GRAY + "Maintain your place at the top", ChatColor.GRAY + "of the hill until you reach", ChatColor.GRAY + "the win score");
		game_selector.setItem(GameType.MOB_ARENA.getMetaItem(), 19, ChatColor.GRAY + "Survive for as many rounds as possible.", ChatColor.GRAY + "Each round becomes increasingly harder");
		game_selector.setItem(GameType.PAINTBALL.getMetaItem(), 20);
		game_selector.setItem(GameType.PIRATE_BATTLE.getMetaItem(), 21, ChatColor.GRAY + "Force all the enemy players into", ChatColor.GRAY + "the water to win");
		game_selector.setItem(GameType.SABOTAGE.getMetaItem(), 22, ChatColor.GRAY + "Pick up the bomb from the middle of", ChatColor.GRAY + "and plant it at the enemy's bomb", ChatColor.GRAY + "site");
		game_selector.setItem(GameType.SEARCH_AND_DESTROY.getMetaItem(), 23, ChatColor.GRAY + "One team spawns as defenders and", ChatColor.GRAY + "the other spawns as attackers.", ChatColor.GRAY + "The attackers' bomb holder must plant", ChatColor.GRAY + "the bomb at one of the two bomb", ChatColor.GRAY + "sites and let it detonate to win", ChatColor.GRAY + "the round");
		game_selector.setItem(GameType.SHIP_ASSAULT.getMetaItem(), 24, ChatColor.GRAY + "One team attacks a boat and the other must defend it.", ChatColor.GRAY + "The attackers have mutliple lives and", ChatColor.GRAY + "the defenders have only 1. A teams life", ChatColor.GRAY + "count must be reduced to 0 to win");
		game_selector.setItem(GameType.SPLEEF.getMetaItem(), 25);
		game_selector.setItem(GameType.SPLEGG.getMetaItem(), 26);
		game_selector.setItem(GameType.TRESPASSERS.getMetaItem(), 40, ChatColor.GRAY + "The map is split into two areas with a wall", ChatColor.GRAY + "separating them. Each team must break the wall", ChatColor.GRAY + "and slaughter the other team to win");
	}
	
	public static HInventory getMatchSelector(GameType type) {
		HInventory h_inv = new HInventory(C.boldGreen + "Select Match", 6);
		if(getGameCount(type) < 36) h_inv.setItem(Material.BOOK_AND_QUILL, C.boldAqua + "Create Match", 8);
		else h_inv.setItem(Material.ENCHANTED_BOOK, C.boldRed + ChatColor.STRIKETHROUGH + "Create Match", 8);
		h_inv.setItem(Material.REDSTONE_BLOCK, C.boldRed + "Back", 0);
		h_inv.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
		List<Game> list = new ArrayList<Game>();
		for(Entry<Integer, Game> entry : games.entrySet()) {
			Game game = entry.getValue();
			if(game == null) continue;
			if(game.getType().equals(type)) list.add(game);
		}
		for(Game match : list) {
			if(match.getStatus().equals(GameStatus.LOBBY)) {
				int amount = 1;
				if(match.getPlayers().size() > 1) amount = match.getPlayers().size();
				ItemStack item = new ItemStack(Material.DIAMOND_BLOCK, amount);
				for(int i = 18; i < h_inv.getInventory().getSize(); i++) {
					if(h_inv.getInventory().getItem(i) == null) {
						h_inv.setItem(item, C.boldAqua + "Game " + match.getID(), i, C.boldYellow + "Starting in " + ChatColor.WHITE + match.countdown.getSeconds());
						break;
					}
				}
			}
		}
		return h_inv;
	}
	
	public static GameInventory getGMatchSelector(GameType type) {
		GameInventory inv = new GameInventory(GIType.MATCH_SELECTOR, type, 6);
		if(getGameCount(type) < 36) inv.setItem(Material.BOOK_AND_QUILL, C.boldAqua + "Create Match", 8);
		else inv.setItem(Material.ENCHANTED_BOOK, C.boldRed + ChatColor.STRIKETHROUGH + "Create Match", 8);
		inv.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
		List<Game> list = new ArrayList<Game>();
		for(Entry<Integer, Game> entry : games.entrySet()) {
			Game game = entry.getValue();
			if(game == null) continue;
			if(game.getType().equals(type)) list.add(game);
		}
		for(Game match : list) {
			if(match.getStatus().equals(GameStatus.LOBBY)) {
				int amount = 1;
				if(match.getPlayers().size() > 1) amount = match.getPlayers().size();
				ItemStack item = new ItemStack(Material.DIAMOND_BLOCK, amount);
				if(item.getAmount() == 1) item.setType(Material.IRON_BLOCK);
				for(int i = 18; i < inv.getInventory().getSize(); i++) {
					if(inv.getInventory().getItem(i) == null) {
						inv.setItem(item, C.boldAqua + "Game " + match.getID(), i);
						break;
					}
				}
			}
		}
		return inv;
	}
	
	public static GameInventory getMatchCreator(GameType type) {
		GameInventory inv = new GameInventory(GIType.MATCH_CREATOR, type, 6);
		List<String> maps = new ArrayList<String>();
		maps.add("");
		for(Entry<String, Map> entry : MapManager.loadedMaps.entrySet()) {
			if(entry.getValue().getGameTypes().contains(type)) maps.add(entry.getKey());
		}
		if(maps.size() == 1) {
			
		}
		for(int i = 1; i < maps.size() - 1; i++) {//i = 1 because index 0 = "" #map selector
			if(i == 1) maps.set(i, ChatColor.GREEN + maps.get(i));
			else maps.set(i, ChatColor.WHITE + maps.get(i));
		}
		return inv;
	}
	
	public abstract void start();
	
	public abstract void join(Player player);
	
	public abstract void leave(Player player);
	
	public abstract void lose(Player player); //when a player dies or loses the game in any other way apart from game ending
	
	public abstract void end();
	
	public void message(String message, boolean format) {
		for(UUID players : getPlayers().keySet()) {
			Player player = Bukkit.getPlayer(players);
			if(player != null) {
				if(format) {
					player.sendMessage(ChatColor.BLUE + getType().getName() + " - " + ChatColor.WHITE + message);
				}
				else player.sendMessage(message);
			}
		}
	}
	
	public void message(Team team, String message, boolean format) {
		for(UUID players : getPlayers().keySet()) {
			Player player = Bukkit.getPlayer(players);
			if(player != null) {
				if(getTeam(player).equals(team)) {
					if(format) player.sendMessage(ChatColor.BLUE + getType().getName() + " - " + ChatColor.WHITE + message);
					else player.sendMessage(message);
				}
			}
		}
	}
	
	public void messageAlive(String message) {
		
	}
	
	public static void loadWorlds() {
		WorldCreator creator = new WorldCreator("game_world");
		creator.seed(7032892981549696907L);
		creator.generateStructures(false);
		creator.type(WorldType.FLAT);
		creator.environment(Environment.NORMAL);
		creator.createWorld();
	}
	
	public static void databaseCheck() {
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS kit_arena_stats (uuid TEXT, " +
				"play_time BIGINT(20), " +
				"play_time_diamond BIGINT(20), " +
				"play_time_iron BIGINT(20), " +
				"play_time_chain BIGINT(20), " +
				"play_time_gold BIGINT(20), " +
				"play_time_leather BIGINT(20), " +
				"kills MEDIUMINT(8), " +
				"deaths MEDIUMINT(8), " +
				"kills_diamond MEDIUMINT(8), " +
				"kills_iron MEDIUMINT(8), " +
				"kills_chain MEDIUMINT(8), " +
				"kills_gold MEDIUMINT(8), " +
				"kills_leather MEDIUMINT(8)," +
				"deaths_diamond MEDIUMINT(8), " +
				"deaths_iron MEDIUMINT(8), " +
				"deaths_chain MEDIUMINT(8), " +
				"deaths_gold MEDIUMINT(8), " +
				"deaths_leather MEDIUMINT(8)," +
				"wins MEDIUMINT(8)," +
				"losses MEDIUMINT(8)," +
				"score INT(11)," +
				"bow_shots MEDIUMINT(8)," +
				"bow_hits MEDIUMINT(8)," +
				"profit INT(11)," +
				"best_win_streak SMALLINT(3)," +
				"current_win_streak SMALLINT(3))");
		Main.mysql.doUpdate("CREATE TABLE IF NOT EXISTS kit_arena_stats_global (play_time BIGINT(20), kills INT(11), deaths INT(11), games INT(11), score BIGINT(20))");
	}

	public enum GameStatus {
		LOBBY,
		PLAYING,
		STOPPED,
		ENDED;
	}

	public enum GameType {
		KIT_ARENA("Kit Arena", 0, new ItemStack(Material.IRON_SWORD, 1), 10, 100, ChatColor.GRAY + "Select a kit and attack the", ChatColor.GRAY + "other team until the win", ChatColor.GRAY + "score is obtained by a team"),
		CTF("Capture The Flag", 1, new ItemStack(Material.WOOL, 1, (short) 14), 1, 10),
		FFA("Free For All", 2, new ItemStack(Material.BOW, 1), 10, 30),
		TRESPASSERS("Trespassers", 3, new ItemStack(Material.BEACON, 1), 10, 50),
		PAINTBALL("Paintball", 4, new ItemStack(Material.SNOW_BALL, 1), 10, 100),
		SPLEEF("Spleef", 5, new ItemStack(Material.IRON_SPADE, 1), 0, 0), //0 max win score and min win score because it's an elmination game type
		SPLEGG("Splegg", 6, new ItemStack(Material.EGG, 1), 0, 0), //look at spleef's comment
		ARCTIC_BRAWL("Arctic Brawl", 7, new ItemStack(Material.ICE, 1), 0, 0), //look at it again ^
		FOUR_CORNERS("Four Corners", 8, new ItemStack(Material.WOOL, 1, (short) 9), 20, 150),
		MOB_ARENA("Mob Arena", 9, new ItemStack(Material.FIRE, 1), 0, 0), //elimination game type
		HOT_POTATO("Hot Potato", 10, new ItemStack(Material.POTATO_ITEM, 1), 0, 0), //elmination game type
		BOAT_WARS("Boat Wars", 11, new ItemStack(Material.BOAT, 1), 0, 0), //elimination game type
		CASTLE_DEFENSE("Castle Defense", 12, new ItemStack(Material.SMOOTH_BRICK, 1), 0, 0), //elmination game type
		BATTLE_OF_HORSES("Battle of Horses", 13, new ItemStack(Material.DIAMOND_BARDING, 1), 10, 30),
		SEARCH_AND_DESTROY("Search and Destroy", 14, new ItemStack(Material.ANVIL), 2, 10), //1 score per round
		SABOTAGE("Sabotage", 15, new ItemStack(Material.TNT), 1, 10), //1 score per round
		KOTH("King of the Hill", 16, new ItemStack(Material.GRASS, 1), 10, 180), //1 score per second on the hill with no one else on it
		SHIP_ASSAULT("Ship Assault", 17, new ItemStack(Material.WOOD), 20, 150), //like castle defense except with a boat
		PIRATE_BATTLE("Pirate Battle", 18, new ItemStack(Material.DISPENSER), 0, 0), //two pirate ships, each with cannons shoot at each other. If a player enters water they die. Path between ships //0, 0 scores because it's elimination
		HEADQUARTERS("Headquarters", 19, new ItemStack(Material.GLASS), 100, 500);
		//PLAYER_TAG("Player Tag" //1 player starts as a tagger and has to tag other players until there are only 3 left (being the winners)
		
		private String name;
		private int id;
		private ItemStack item;
		private List<String> desc = new ArrayList<String>();
		private int max_win_score;
		private int min_win_score;
		
		GameType(String name, int id, ItemStack item, int min_win_score, int max_win_score, String...desc) {
			this.name = name;
			this.id = id;
			this.item = item;
			this.min_win_score = min_win_score;
			this.max_win_score = max_win_score;
			ArrayList<String> description = new ArrayList<String>();
			description.addAll(Arrays.asList(desc));
			this.desc = description;
		}

		GameType(String name, int id, ItemStack item, int min_win_score, int max_win_score) {
			this.name = name;
			this.id = id;
			this.min_win_score = min_win_score;
			this.max_win_score = max_win_score;
			this.item = item;
			
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getID() {
			return this.id;
		}
		
		public ItemStack getItem() {
			return this.item;
		}
		
		public ItemStack getMetaItem() {
			ItemStack metaItem = this.item;
			ItemMeta meta = this.item.getItemMeta();
			meta.setDisplayName(C.boldGreen + getName());
			meta.setLore(getDescription());
			metaItem.setItemMeta(meta);
			return metaItem;
		}
		
		public List<String> getDescription() {
			return this.desc;
		}
		
		public int getMaxWinScore() {
			return this.max_win_score;
		}
		
		public int getMinWinScore() {
			return this.min_win_score;
		}
		
		public static GameType getType(String name) {
			for(GameType types : values()) {
				if(types.getName().equalsIgnoreCase(name)) return types;
			}
			return null;
		}
		
		public static GameType getType(int id) {
			for(GameType types : values()) {
				if(types.getID() == id) return types;
			}
			return null;
		}
	}

	public enum Team {
		RED("Red", "Alpha", DyeColor.RED, ChatColor.RED),
		BLUE("Blue", "Bravo", DyeColor.BLUE, ChatColor.BLUE),
		YELLOW("Yellow", "Charlie", DyeColor.YELLOW, ChatColor.YELLOW),
		GREEN("Green", "Delta", DyeColor.GREEN, ChatColor.GREEN),
		FFA("FFA", ChatColor.YELLOW);

		private String name;
		private String phonetic_name;
		private DyeColor color;
		private ChatColor c_color;

		Team(String name, String phonetic_name, DyeColor color, ChatColor c_color) {
			this.name = name;
			this.phonetic_name = phonetic_name;
			this.color = color;
			this.c_color = c_color;
		}
		
		Team(String name, ChatColor c_color) {
			this.name = name;
			this.c_color = c_color;
		}

		public String getName() {
			return this.name;
		}

		public String getPhoneticName() {
			return this.phonetic_name;
		}

		public DyeColor getColor() {
			return this.color;
		}
		
		public ChatColor getChatColor() {
			return this.c_color;
		}
		
		public DyeColor getRandomColor() { //for ffa
			DyeColor[] list = DyeColor.values();
			return list[utilMath.getRandom(0, list.length - 1)];
		}
	}
	
	public enum PowerUp {
		HEALTH_REGEN("Health Pack"),
		SUPPLY_REFILL("Supplies");
		
		private String name;
		private long respawnDelay = 60000L;
		
		PowerUp(String name, long respawnDelay) {
			this.name = name;
		}
		
		PowerUp(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public long getRespawnDelay() {
			return this.respawnDelay;
		}
	}
	
	public enum TeamUpgrade {
		EXTRA_ARROWS;
	}
}
