package me.loogeh.Hype.Sector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Member.mPerms.Rank;
import me.loogeh.Hype.Utility.util;

import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class Animal extends Sector implements CommandExecutor {

	private static Animal instance;

	public Animal() {
		super("Animal");
		List<String> commandList = new ArrayList<String>();
		commandList.add("animal");
		for(String command : commandList) {
			getPlugin().getCommand(command).setExecutor(this);
		}
		instance = this;
	}

	public static Animal get() {
		if(instance == null) instance = new Animal();
		return instance;
	}

	@SuppressWarnings("deprecation")
	public void spawnAnimal(Player player, Spawnable type, int amount) {
		if(player == null) return;
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return;
		}
		if(!member.getPermissions().is(Rank.MODERATOR)) {
			M.sendLackPermsMessage(player);
			return;
		}
		if(amount > type.getSpawnLimit()) {
			message(player, ChatColor.WHITE + "You may only spawn " + ChatColor.YELLOW + type.getSpawnLimit() + " " + type.getName() + ChatColor.WHITE + " at a time");
			return;
		}
		Location location = player.getTargetBlock(null, 50).getLocation();
		for(int i = 0; i < amount; i++) {
			player.getWorld().spawnEntity(location, type.getType());
		}
		message(player, ChatColor.WHITE + "You spawned " + ChatColor.YELLOW + amount + " " + type.getName());
	}

	@SuppressWarnings("deprecation")
	public void spawnVillager(Player player, Profession profession, boolean baby, int amount) {
		if(player == null) return;
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return;
		}
		if(!member.getPermissions().is(Rank.MODERATOR)) {
			M.sendLackPermsMessage(player);
			return;
		}
		if(amount > Spawnable.VILLAGER.getSpawnLimit()) {
			message(player, ChatColor.WHITE + "You may only spawn " + ChatColor.YELLOW + Spawnable.VILLAGER.getSpawnLimit() + " Villagers " + ChatColor.WHITE + "at a time");
			return;
		}
		Location location = player.getTargetBlock(null, 50).getLocation();
		for(int i = 0; i < amount; i++) {
			Villager villager = (Villager) player.getWorld().spawnEntity(location, EntityType.VILLAGER);
			villager.setProfession(profession);
		}
		message(player, ChatColor.WHITE + "You spawned " + ChatColor.YELLOW + amount + " Villagers(s)");
	}

	public void spawnBabyAnimal(Player player, Spawnable type, int amount) {
		if(player == null) return;
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return;
		}
		if(!member.getPermissions().is(Rank.MODERATOR)) {
			M.sendLackPermsMessage(player);
			return;
		}
		if(!type.getCanBaby()) {
			message(player, ChatColor.YELLOW + type.getName() + ChatColor.WHITE + " cannot be set as " + ChatColor.YELLOW + "Baby");
			return;
		}
		if(amount > type.getSpawnLimit()) {
			message(player, ChatColor.WHITE + "You may only spawn " + ChatColor.YELLOW + type.getSpawnLimit() + " " + type.getName() + ChatColor.WHITE + " at a time");
			return;
		}
		for(int i = 0; i < amount; i++) {
			@SuppressWarnings("deprecation")
			Entity entity = player.getWorld().spawnEntity(player.getTargetBlock(null, 50).getLocation(), type.getType());
			if(type == Spawnable.CHICKEN) {
				Chicken chicken = (Chicken) entity;
				chicken.setBaby();
			}
			else if(type == Spawnable.COW) {
				Cow cow = (Cow) entity;
				cow.setBaby();
			} else if(type == Spawnable.HORSE) {
				Horse horse = (Horse) entity;
				horse.setBaby();
			} else if(type == Spawnable.MOOSHROOM) {
				MushroomCow m_cow = (MushroomCow) entity;
				m_cow.setBaby();
			} else if(type == Spawnable.OCELOT) {
				Ocelot ocelot = (Ocelot) entity;
				ocelot.setBaby();
			} else if(type == Spawnable.PIG) {
				Pig pig = (Pig) entity;
				pig.setBaby();
			} else if(type == Spawnable.PIG_ZOMBIE) {
				PigZombie p_zombie = (PigZombie) entity;
				p_zombie.setBaby(true);
			} else if(type == Spawnable.SHEEP) {
				Sheep sheep = (Sheep) entity;
				sheep.setBaby();
			} else if(type == Spawnable.VILLAGER) {
				Villager villager = (Villager) entity;
				villager.setBaby();
			} else if(type == Spawnable.WOLF) {
				Wolf wolf = (Wolf) entity;
				wolf.setBaby();
			} else if(type == Spawnable.ZOMBIE) {
				Zombie zombie = (Zombie) entity;
				zombie.setBaby(true);
			}
		}
		message(player, ChatColor.WHITE + "You spawned " + ChatColor.YELLOW + amount + " Baby" + type.getName() + "(s)");
	}

	public void kill(Player player, EntityType type, int radius) {
		if(player == null) return;
		if(radius == 0) {
			message(player, "Killed " + ChatColor.YELLOW + "0 entities");
			return;
		}
		int count = 0;
		Iterator<Entity> it = player.getWorld().getEntities().iterator();
		while(it.hasNext()) {
			Entity entity = it.next();
			if(type == null || entity.getType() == type) {
				if(radius == -1) {
					entity.remove();
					count++;
				} else {
					if(player.getLocation().distanceSquared(entity.getLocation()) < radius * radius) {
						entity.playEffect(EntityEffect.WOLF_HEARTS);
						entity.remove();
						count++;
					}
				}
			}
		}
		message(player, "Killed " + ChatColor.YELLOW + count + " Entities");
	}

	public void load() {

	}

	public static boolean getSpawnable(EntityType type) {
		for(Spawnable spawnable : Spawnable.values()) {
			if(spawnable.getType().equals(type)) return true;
		}
		return false;
	}

	public static int getSpawnLimit(EntityType type) {
		if(!getSpawnable(type)) return 0;
		return 0;
	}


	public enum Spawnable {
		BAT("Bat", EntityType.BAT, 500, Bat.class),
		BLAZE("Blaze", EntityType.BLAZE, 200, Blaze.class),
		CAVE_SPIDER("Cave Spider", EntityType.CAVE_SPIDER, 200, CaveSpider.class),
		CHICKEN("Chicken", EntityType.CHICKEN, 400, Chicken.class, true),
		COW("Cow", EntityType.COW, 200, Cow.class, true),
		CREEPER("Creeper", EntityType.CREEPER, 200, Creeper.class),
		ENDER_DRAGON("Ender Dragon", EntityType.ENDER_DRAGON, 5, EnderDragon.class),
		ENDERMAN("Enderman", EntityType.ENDERMAN, 200, Enderman.class),
		GHAST("Ghast", EntityType.GHAST, 40, Ghast.class),
		GIANT("Giant", EntityType.GIANT, 3, Giant.class),
		HORSE("Horse", EntityType.HORSE, 100, Horse.class, true),
		IRON_GOLEM("Iron Golem", EntityType.IRON_GOLEM, 50, IronGolem.class),
		MAGMA_CUBE("Magma Cube", EntityType.MAGMA_CUBE, 100, MagmaCube.class),
		MOOSHROOM("Mooshroom", EntityType.MUSHROOM_COW, 200, MushroomCow.class, true),
		OCELOT("Ocelot", EntityType.OCELOT, 400, Ocelot.class, true),
		PIG("Pig", EntityType.PIG, 300, Pig.class, true),
		PIG_ZOMBIE("Zombie Pigman", EntityType.PIG_ZOMBIE, 100, PigZombie.class, true),
		SHEEP("Sheep", EntityType.SHEEP, 200, Sheep.class, true),
		SILVERFISH("Silverfish", EntityType.SILVERFISH, 400, Silverfish.class),
		SKELETON("Skeleton", EntityType.SKELETON, 200, Skeleton.class),
		SLIME("Slime", EntityType.SLIME, 200, Slime.class),
		SNOWMAN("Snowman", EntityType.SNOWMAN, 200, Snowman.class),
		SPIDER("Spider", EntityType.SPIDER, 200, Spider.class),
		SQUID("Squid", EntityType.SQUID, 200, Squid.class),
		VILLAGER("Villager", EntityType.VILLAGER, 150, Villager.class, true),
		WITCH("Witch", EntityType.WITCH, 100, Witch.class),
		WITHER("Wither", EntityType.WITHER, 50, Wither.class),
		WOLF("Wolf", EntityType.WOLF, 150, Wolf.class, true),
		ZOMBIE("Zombie", EntityType.ZOMBIE, 200, Zombie.class, true);

		public static HashMap<String, Spawnable> spawnableMap = new HashMap<String, Spawnable>();

		private String name;
		private EntityType type;
		private int spawn_limit;
		private Class<?> clazz;
		private boolean baby = false;

		static {
			for(Spawnable spawnable : values()) {
				spawnableMap.put(spawnable.getName().toLowerCase(), spawnable);
			}
		}

		Spawnable(String name, EntityType type, int spawn_limit, Class<?> clazz) {
			this.name = name;
			this.type = type;
			this.spawn_limit = spawn_limit;
			this.clazz = clazz;
		}

		Spawnable(String name, EntityType type, int spawn_limit, Class<?> clazz, boolean baby) {
			this.name = name;
			this.type = type;
			this.spawn_limit = spawn_limit;
			this.clazz = clazz;
			this.baby = baby;
		}

		public String getName() {
			return this.name;
		}

		public EntityType getType() {
			return this.type;
		}

		public int getSpawnLimit() {
			return this.spawn_limit;
		}

		public Class<?> getBukkitClass() {
			return this.clazz;
		}

		public boolean getCanBaby() {
			return this.baby;
		}

		public static Spawnable search(String name) {
			String result = util.search(name, spawnableMap.keySet());
			if(result == null) return null;
			Spawnable type = spawnableMap.get(result.toLowerCase());
			return type;
		}
		
		public static EntityType searchBukkitEntity(String name) {
			if(name == null) return null;
			
			EntityType found = null;
			int delta = Integer.MAX_VALUE;
			for(EntityType type : EntityType.values()) {
				if(type.name().toLowerCase().startsWith(name.toLowerCase())) {
					int curDelta = type.name().length() - name.length();
					if(curDelta < delta) {
						found = type;
						delta = curDelta;
					}
					if(curDelta == 0) break;
				}
			}
			return found;
		}

	}

	public void help(Player player) {
		message(player, ChatColor.WHITE + "Commands " + ChatColor.GRAY + "(optional) <required>");
		M.sendHelpMessage(player, "/animal spawn <animal> (? animal = villager <prof>) (quantity)", "Spawn animals");
		M.sendHelpMessage(player, "/animal kill <animal> (radius)", "Kill animals");
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Animal - " + ChatColor.WHITE + "Console cannot use animal commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			message(player, ChatColor.WHITE + "Failed to find your player data");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("animal")) {
			if(!member.getPermissions().is(Rank.MODERATOR)) {
				M.sendLackPermsMessage(player);
				return true;
			}
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(args.length == 1) {
				help(player);
				return true;
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("spawn")) {
					Spawnable type = Spawnable.search(args[1]);
					if(type == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Animal");
						return true;
					}
					spawnAnimal(player, type, 1);
					return true;
				}
				if(args[0].equalsIgnoreCase("kill")) {
					if(args[1].equalsIgnoreCase("all")) {
						kill(player, null, 40);
						return true;
					}
					EntityType type = Spawnable.searchBukkitEntity(args[1]);
					if(type == null) {
						message(player, "You must enter a valid " + ChatColor.YELLOW + "Animal");
						return true;
					}
					kill(player, type, 40);
					return true;
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("spawn")) {
					if(args[1].equalsIgnoreCase("baby")) {
						Spawnable type = Spawnable.search(args[1]);
						if(type == null) {
							message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Animal");
							return true;
						}
						spawnBabyAnimal(player, type, 1);
						return true;
					}
					Spawnable type = Spawnable.search(args[1]);
					if(type == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Animal");
						return true;
					}
					if(!type.equals(Spawnable.VILLAGER)) {
						Integer amount = util.getInteger(args[2]);
						if(amount == null) {
							message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Quantity");
							return true;
						}
						spawnAnimal(player, type, amount);
						return true;
					}
					Integer amount = util.getInteger(args[2]);
					if(amount == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Quantity");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("kill")) {
					if(args[1].equalsIgnoreCase("all")) {
						Integer radius = util.getInteger(args[2]);
						if(radius == null) {
							message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Radius");
							return true;
						}
						kill(player, null, radius);
						return true;
					}
					EntityType type = Spawnable.searchBukkitEntity(args[1]);
					if(type == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Animal");
						return true;
					}
					Integer radius = util.getInteger(args[2]);
					if(radius == null) {
						message(player, ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "Radius");
						return true;
					}
					kill(player, type, radius);
					return true;
				}
			}
		}
		return false;
	}

}
