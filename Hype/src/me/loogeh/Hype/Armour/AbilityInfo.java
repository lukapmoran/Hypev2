package me.loogeh.Hype.Armour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;

import me.loogeh.Hype.Miscellaneous.A;

public enum AbilityInfo {
	//axe = 7, sword = 9, bow = 6, passive = 8 |-| chain = 8, gold = 6, iron = 5, diamond = 5, leather = 9, global = 7
	BERSERKER(0, "Berserker", AbilityType.AXE, 8000L, 24, ClassType.CHAIN, 45000, ChatColor.GRAY + "Deal 30% extra damage for 8 seconds"), //chain
	PROXIMITY_HEAL(1, "Proximity Heal", AbilityType.SWORD, 12, ClassType.CHAIN, 35000, ChatColor.GRAY + "Heal any player near you of 3 hearts"), //chain
	TRIPLE_SHOT(2, "Triple Shot", AbilityType.BOW, 12, ClassType.CHAIN, 40000, ChatColor.GRAY + "Launch 2 extra arrows which are angled", ChatColor.GRAY + "by 25 degrees to the right and the", ChatColor.GRAY + "left"), //chain
	STOMP(3, "Stomp", AbilityType.SWORD, 16, ClassType.DIAMOND, 50000, ChatColor.GRAY + "Launch yourself into the air for", ChatColor.GRAY + "limited time before coming crashing", ChatColor.GRAY + "down giving players around you Slowness V", ChatColor.GRAY + "for 5 seconds"), //diamond
	SWITCH(4, "Switch", AbilityType.SWORD, 12, ClassType.DIAMOND, 40000, ChatColor.GRAY + "You switch positions with another", ChatColor.GRAY + "player upon receiving damage from them"), //diamond
	RUSH(5, "Rush", AbilityType.AXE, 16, ClassType.GOLD, 35000, ChatColor.GRAY + "Boost your sprint speed for 3", ChatColor.GRAY + "seconds"), //gold
	DODGE(6, "Dodge", AbilityType.SWORD, 12, ClassType.LEATHER, 40000, ChatColor.GRAY + "Swiftly glide across the ground to", ChatColor.GRAY + "avoid incoming attacks"), //gold
	SHOCKWAVE(7, "Shockwave", AbilityType.SWORD, 16, ClassType.IRON, 50000, ChatColor.GRAY + "Send a shockwave through the", ChatColor.GRAY + "ground launching nearby players", ChatColor.GRAY + "into the air"), //iron
	RESIST(8, "Resist", AbilityType.AXE, 20, ClassType.IRON, 55000, ChatColor.GRAY + "Become 60% more resistant to", ChatColor.GRAY + "incoming attacks for 3.5 seconds"), //iron
	LEAP(9, "Leap", AbilityType.AXE, 12, ClassType.LEATHER, 35000, ChatColor.GRAY + "Thrust yourself into the air for", ChatColor.GRAY + "faster transport or to avoid incoming", ChatColor.GRAY + "attacks"), //leather
	WILDFIRE(10, "Wildfire", AbilityType.SWORD, 14, ClassType.GOLD, 45000, ChatColor.GRAY + "Shoot fire out in a radial direction", ChatColor.GRAY + "setting the players who pick it", ChatColor.GRAY + "on fire for 2 seconds"), //fire shoots out in a radial direction //gold
	SMASH(11, "Smash", AbilityType.AXE, 20, ClassType.GOLD, 60000, ChatColor.GRAY + "Thurst yourself in an upward", ChatColor.GRAY + "direction and come smashing down", ChatColor.GRAY + "forcing nearby players skyward"), //gold //seismic slam
	BOREAS(12, "Boreas", AbilityType.SWORD, 3000L, 18, ClassType.CHAIN, 50000, ChatColor.GRAY + "Spawn snow which grows in height", ChatColor.GRAY + "and gives players in it Slowness II", ChatColor.GRAY + "repeatedly lasting 2 seconds"), //need new name - inceases height of snow by increment of 1 starting in the center  //chain
	BLIZZARD(13, "Blizzard", AbilityType.SWORD, 2400L, ClassType.DIAMOND, 55000, ChatColor.GRAY + "Shoot Snoballs frequently in the", ChatColor.GRAY + "direction that you are looking", ChatColor.GRAY + "giving players Slowness I for", ChatColor.GRAY + "1 second upon each hit"), //diamond
	HASTE(14, "Haste", AbilityType.PASSIVE, ClassType.CHAIN, 40000, ChatColor.GRAY + "Increase your mining speed by 40%", ChatColor.GRAY + "while wearing the required set"), //passive - fast mining //chain
	BREAK_FALL(15, "Break Fall", AbilityType.PASSIVE_III, 35000, ChatColor.GRAY + "Take no fall damage,", ChatColor.GRAY + "good for abilities which involve", ChatColor.GRAY + "falling e.g Leap"), //global
	SUSTENANACE(16, "Sustenance", AbilityType.PASSIVE_III, 40000, ChatColor.GRAY + "Permanently have full hunger, eliminating", ChatColor.GRAY + "the need to carry food around"), //global
	BREATHING(17, "Breathing", AbilityType.PASSIVE_III, 45000, ChatColor.GRAY + "Have the ability to breathe underwater.", ChatColor.GRAY + "Useful when it comes to evading", ChatColor.GRAY + "danger"), //global
	BOOST(18, "Boost", AbilityType.PASSIVE_III, 3, 45000, ChatColor.GRAY + "While in water double tap space", ChatColor.GRAY + "and boost yourself forwards"), //double jump in water //global
	ENDURANCE(19, "Endurance", AbilityType.PASSIVE, ClassType.GOLD, 40000, ChatColor.GRAY + "Become invulnerable to lava and fire"), //gold
	ARROW_PENETRATIVE(20, "Penetrative Arrow", AbilityType.BOW, 4000L, 16, ClassType.IRON, 35000, ChatColor.GRAY + "Shoot a Penetrative Arrow which", ChatColor.GRAY + "deals more damage than a regular", ChatColor.GRAY + "arrow"), //leather
	ARROW_CRIPPLING(21, "Crippling Arrow", AbilityType.BOW, 4000L, 16, ClassType.LEATHER, 35000, ChatColor.GRAY + "Shoot a Crippling Arrow which", ChatColor.GRAY + "applies Slowness III to the", ChatColor.GRAY + "player it hits"), //leather
	ARROW_INCENDIARY(22, "Incediary Arrow", AbilityType.BOW, 6000L, 20, ClassType.GOLD, 40000, ChatColor.GRAY + "Shoot an Incendiary Arrow to", ChatColor.GRAY + "ignite your nemesis"), //leather
	ARROW_POISON(23, "Poison Arrow", AbilityType.BOW, 6000L, 20, ClassType.LEATHER, 40000, ChatColor.GRAY + "Fire a Posion Arrow to", ChatColor.GRAY + "incapacitate an enemy"), //leather
	ARROW_NOXIOUS(24, "Noxious Arrow", AbilityType.BOW, 6000L, 20, ClassType.DIAMOND, 45000, ChatColor.GRAY + "Launch a Noxious Arrow in", ChatColor.GRAY + "order to confuse an enemy player"), //leather
	ARROW_GRAPPEL(25, "Grappel Arrow", AbilityType.BOW, 6000L, 20, ClassType.IRON, 50000, ChatColor.GRAY + "Fire an arrow which has", ChatColor.GRAY + "a grappel hook attached to", ChatColor.GRAY + "it in order to swiftly reach", ChatColor.GRAY + "tough spots"), //launch yourself towards where your arrow landed if it's higher than you //leather
	ARROW_REPULSE(26, "Repulsive Arrow", AbilityType.BOW, 16, ClassType.GOLD, 35000, ChatColor.GRAY + "Shoot a Repulsive Arrow to", ChatColor.GRAY + "knock a target off of a ledge", ChatColor.GRAY + "or slow them down"), //leather
	ARROW_POWER(27, "Power Arrow", AbilityType.BOW, ClassType.DIAMOND, 65000, ChatColor.GRAY + "Charge a Power Arrow to", ChatColor.GRAY + "deal selectable extra damage", ChatColor.GRAY + "to an opponent"), //overcharge
	ARROW_REGENERATIVE(28, "Regenerative Arrow", AbilityType.PASSIVE_II, 6000L, 20, ClassType.GOLD, 50000, ChatColor.GRAY + "Fire a Regenerative Arrow towards", ChatColor.GRAY + "teammates to assist them from", ChatColor.GRAY + "a distance"),
	RICOCHET(29, "Ricochet", AbilityType.AXE, 5000L, 24, ClassType.IRON, 55000, ChatColor.GRAY + "Deflect incoming damage towards", ChatColor.GRAY + "your attacker"), //damage taken is divided by 5 and given to the damager //iron
	STEALTH(30, "Stealth", AbilityType.PASSIVE_II, ClassType.CHAIN, 45000, ChatColor.GRAY + "Move through enemy territory", ChatColor.GRAY + "undetected"), //constant crouch //chain
	SWITFTNESS(31, "Swiftness", AbilityType.PASSIVE_II, ClassType.CHAIN, 35000, ChatColor.GRAY + "Evade a rival with increased ease"), //constant speed I //global
	BURST(32, "Burst", AbilityType.SWORD, 2400L, 14, ClassType.GOLD, 60000, ChatColor.GRAY + "Bombard opponents with fire to", ChatColor.GRAY + "distract them from their target"), //blizzard but with fire //gold
	FADE(33, "Fade", AbilityType.SWORD, 20, ClassType.IRON, 60000, ChatColor.GRAY + "Disappear into thin air for", ChatColor.GRAY + "limited time to confuse the enemy"), //go invisible for 3-4 seconds //iron
	BEAM(34, "Beam", AbilityType.AXE, 14, ClassType.LEATHER, 45000, ChatColor.GRAY + "Beam yourself to your target location", ChatColor.GRAY + "with a limit of 20 blocks"), //teleport to target location if distance  < 11 blocks //leather
	MAGNETIC_HAUL(35, "Magnetic Haul", AbilityType.SWORD, 6000L, 24, ClassType.CHAIN, 70000, ChatColor.GRAY + "Haul nearby players into your", ChatColor.GRAY + "vicinity through electromagnetism"), //chain
	FROST_AXE(36, "Frost Axe", AbilityType.AXE, 8000L, 22, ClassType.DIAMOND, 50000, ChatColor.GRAY + "Freeze nearby water and permit", ChatColor.GRAY + "quick travel over it"), //diamond
	HELLFIRE_AXE(37, "Hellfire Axe", AbilityType.AXE, 8000L, 22, ClassType.GOLD, 50000, ChatColor.GRAY + "Alters the molecular structure of", ChatColor.GRAY + "lava, temporarily making it solid"),
	WALL_HOP(38, "Wall Hop", AbilityType.PASSIVE, 12, ClassType.LEATHER, 35000, ChatColor.GRAY + "Hop off of a nearby wall", ChatColor.GRAY + "to get to difficult places"), //crouch while in mid air and facing a wall, launch your self back wards //leather
	REVENGE(39, "Revenge", AbilityType.PASSIVE_III, 45000, ChatColor.GRAY + "Have vengeance on your fallen", ChatColor.GRAY + "brothers by dealing 15% extra damage", ChatColor.GRAY + "to players who recently killed a", ChatColor.GRAY + "squad mate"), //deal 10% extra damage to people who recently killed your squad mates //global
	FOUNTAIN(40, "Fountain", AbilityType.AXE, 3000L, 24, ClassType.IRON, 50000, ChatColor.GRAY + "Become a fountain of web", ChatColor.GRAY + "which gives Slowness III for", ChatColor.GRAY + "1 second"), //web comes out of a player's head for 3 seconds //iron
	FUSILLADE(41, "Fusillade", AbilityType.BOW, ClassType.CHAIN, 65000, ChatColor.GRAY + "Blast arrows continuously in", ChatColor.GRAY + "your enemy's direction to", ChatColor.GRAY + "impair multiple targets"), //shoots multiple arrows out after a charge //chain
	SOOTHE(42, "Soothe", AbilityType.PASSIVE, ClassType.DIAMOND, 45000, ChatColor.GRAY + "All ability inflicted slowness is", ChatColor.GRAY + "reduced by 50%"), //diamond //Slowness received from abilities has duration reduced by 50%
	ADRENALINE(43, "Adrenaline", AbilityType.PASSIVE, ClassType.DIAMOND, 50000, ChatColor.GRAY + "Receive Regeneration 1 or 2", ChatColor.GRAY + "while approaching death"), //while <= 3 hearts the player gets regeneration 1, <= 1.5 hearts = regeneration 2 //diamond
	TOUGHNESS(44, "Toughness", AbilityType.PASSIVE_II, ClassType.DIAMOND, 50000, ChatColor.GRAY + "Deal regular amounts of damage in", ChatColor.GRAY + "Diamond Armour"), //remove the damage reduction in diamond //diamond
	RAPID_RECHARGE(45, "Rapid Recharge", AbilityType.PASSIVE_III, 60000, ChatColor.GRAY + "Have a 15% chance of a cooldown", ChatColor.GRAY + "being decreased by 25%"), //15% chance that cooldown is reduced by 25%
	TEAM_PLAYER(46, "Team Player", AbilityType.PASSIVE, 12000L, ClassType.IRON, 60000, ChatColor.GRAY + "Deal 20% extra damage for 12 seconds", ChatColor.GRAY + "upon a squad mate's death occuring", ChatColor.GRAY + "nearby"), //deal 20% extra damage for 12 seconds when a squad mate dies within 5 blocks of you
	DESPERATION(47, "Desperation", AbilityType.PASSIVE_II, ClassType.IRON, 50000, ChatColor.GRAY + "Deal extra daemage while becoming", ChatColor.GRAY + "near to death"), //deal 20% extra damage when health <= 3.5 hearts
	INCREMENTAL_UPSURGE(48, "Incremental Upsurge", AbilityType.PASSIVE_II, ClassType.LEATHER, 65000, ChatColor.GRAY + "Gain a new level of Speed for", ChatColor.GRAY + "every 20 seconds of constant", ChatColor.GRAY + "sprinting"), //slowly gain levels of speed every 20 seconds up to speed 3
	BARRIER(49, "Barrier", AbilityType.SWORD, 6000L, 24, ClassType.LEATHER, 65000, ChatColor.GRAY + "Form a invisible barrier around", ChatColor.GRAY + "you to disallow players from", ChatColor.GRAY + "approaching you"), //opposite of magnetic haul, doesn't let players within a certain distance of the user
//	HEATWAVE(50, "Heatwave", AbilityType., cooldown, Set., cost, description),
	TREMOR(51, "Tremor", AbilityType.AXE, 3500L, 24, ClassType.CHAIN, 65000, ChatColor.GRAY + "Cause a series of minor", ChatColor.GRAY + "seismic tremors which shake", ChatColor.GRAY + "players around you"),
	ARCTIC_BLAST(52, "Arctic Blast", AbilityType.AXE, 18, ClassType.GOLD, 55000, ChatColor.GRAY + "Shoot a block of ice which explodes,", ChatColor.GRAY + "creating a layer of snow covering", ChatColor.GRAY + "sufficient area to incapacitate", ChatColor.GRAY + "nearby players"),
	TRAIL(53, "Trail", AbilityType.SWORD, 8000L, 24, ClassType.GOLD, 65000, ChatColor.GRAY + "Leave a trail of snow behind you", ChatColor.GRAY + "which gives players walking over it Slowness II", ChatColor.GRAY + "for 2 seconds"),
	CRIPPLING_AXE(54, "Crippling Axe", AbilityType.PASSIVE, ClassType.LEATHER, 50000, ChatColor.GRAY + "Partially immobilize a player upon", ChatColor.GRAY + "damaging them with a Gold, Iron or", ChatColor.GRAY + "Diamond Axe"),
	INFERNO_AXE(55, "Inferno Axe", AbilityType.PASSIVE, ClassType.GOLD, 50000, ChatColor.GRAY + "Ignite a player for limited time", ChatColor.GRAY + "by hitting them with a Gold, Iron or", ChatColor.GRAY + "Diamond Axe"),
	LIGHTNING_SCEPTRE(56, "Lightning Sceptre", AbilityType.PASSIVE_II, 16, ClassType.DIAMOND, 50000, ChatColor.GRAY + "Call upon the power of gods", ChatColor.GRAY + "and cast a lightning strike down", ChatColor.GRAY + "at your desired location"),
	FARMER(57, "Farmer", AbilityType.PASSIVE_II, ClassType.GOLD, 50000, ChatColor.GRAY + "Efficiently reap Melon and", ChatColor.GRAY + "Pumpkin blocks for fast sale"),
	REAPER(58, "Reaper", AbilityType.PASSIVE_II, ClassType.LEATHER, 50000, ChatColor.GRAY + "Swiftly harvest Wheat, Carrots, Potatoes", ChatColor.GRAY + "and Sugar Cane"),
	LUMBERJACK(59, "Lumberjack", AbilityType.PASSIVE, ClassType.CHAIN, 50000, ChatColor.GRAY + "Hastily cut down trees for sale"),
	PLANTER(60, "Planter", AbilityType.PASSIVE_II, ClassType.LEATHER, 50000, ChatColor.GRAY + "Quickly plant seeds to make for", ChatColor.GRAY + "more frequent harvests"),
	CULTIVATER(61, "Cultivater", AbilityType.PASSIVE_II, ClassType.GOLD, 50000, ChatColor.GRAY + "Fertalise a wider spread of", ChatColor.GRAY + "land"),
	APTITUDE(62, "Aptitude", AbilityType.PASSIVE_III, 60000, ChatColor.GRAY + "Be unaffected by snow"),
	NEWTONIAN_THWART(63, "Newtonian Thwart", AbilityType.PASSIVE_II, ClassType.IRON, 65000, ChatColor.GRAY + "Defy Newton's third law of motion"), //no knockback on people you hit
	ESCAPE(64, "Escape", AbilityType.PASSIVE, ClassType.IRON, 60000, ChatColor.GRAY + "Have increased lower body strength", ChatColor.GRAY + "which can be useful for escaping", ChatColor.GRAY + "enemy traps"),
	BLACKSMITH(65, "Blacksmith", AbilityType.PASSIVE, ClassType.GOLD, 65000, ChatColor.GRAY + "Gradually repair your protective gear", ChatColor.GRAY + "by sitting in lava.", ChatColor.GRAY + "You will take damage if you", ChatColor.GRAY + "do not have Endurance selected"),
	NONE(-1, "", AbilityType.NONE, 0);

	public static HashMap<Integer, AbilityInfo> abilityIds = new HashMap<Integer, AbilityInfo>();
	
	private int id;
	private String name = "";
	private AbilityType type = AbilityType.NONE;
	private long duration = 0L;
	private int cooldown = 0;
	private ClassType set = ClassType.NONE;
	private int cost = 0;
	private int required_level = 1;
	private List<String> desc = new ArrayList<String>();

	AbilityInfo(int id, String name, AbilityType type, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, long duration, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.duration = duration;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.GRAY + "Duration: " + duration / 1000);
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, int cooldown, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.cooldown = cooldown;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.GRAY + "Cooldown: " + cooldown);
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, long duration, int cooldown, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.duration = duration;
		this.cooldown = cooldown;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.GRAY + "Duration: " + duration / 1000);
		list.add(ChatColor.GRAY + "Cooldown: " + cooldown);
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, ClassType set, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.set = set;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, long duration, ClassType set, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.duration = duration;
		this.set = set;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.GRAY + "Duration: " + duration / 1000);
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, int cooldown, ClassType set, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.cooldown = cooldown;
		this.set = set;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.GRAY + "Cooldown: " + cooldown);
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	AbilityInfo(int id, String name, AbilityType type, long duration, int cooldown, ClassType set, int cost, String... desc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.duration = duration;
		this.cooldown = cooldown;
		this.set = set;
		this.cost = cost;
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(desc));
		list.add("");
		list.add(ChatColor.GRAY + "Duration: " + duration / 1000);
		list.add(ChatColor.GRAY + "Cooldown: " + cooldown);
		list.add("");
		list.add(ChatColor.YELLOW + "Level " + required_level);
		list.add(ChatColor.YELLOW + "$" + cost);
		this.desc = list;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public AbilityType getType() {
		return this.type;
	}

	public long getDuration() {
		return this.duration;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public ClassType getSet() {
		return this.set;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public int getRequiredLevel() {
		return this.required_level;
	}
	
	public List<String> getDescription() {
		return this.desc;
	}
	
	public List<String> getLore() {
		ArrayList<String> lore = new ArrayList<String>();
		if(getAction() != null) {
			lore.add(ChatColor.YELLOW + getAction().getName());
			lore.add("");
		}
		lore.addAll(getDescription());
		lore.add(ChatColor.YELLOW + "$" + getCost());
		return lore;
	}
	
	public A getAction() {
		return getAction(this);
	}
	
	public static List<AbilityInfo> getAbilities(ClassType set, AbilityType type) {
		List<AbilityInfo> abilities = new ArrayList<AbilityInfo>();
		for(AbilityInfo ability : values()) {
			if(ability.getSet() != null) {
				if(ability.getSet().equals(set) && ability.getType().equals(type)) {
					abilities.add(ability);
				}
			}

		}
		return abilities;
	}

	public static List<AbilityInfo> getGlobalPassives() {
		List<AbilityInfo> abilities = new ArrayList<AbilityInfo>();
		for(AbilityInfo ability : values()) {
			if(ability.getType().equals(AbilityType.PASSIVE_III)) {
				abilities.add(ability);
			}
		}
		return abilities;
	}
	
	public static AbilityInfo getAbility(String name) {
		for(AbilityInfo ability : values()) {
			if(ability.getName().equalsIgnoreCase(name)) return ability;
			else if(ability.getName().toLowerCase().startsWith(name)) return ability;
		}
		return null;
	}
	
	public static AbilityInfo getAbilityExact(String name) {
		for(AbilityInfo ability : values()) {
			if(ability.getName().equalsIgnoreCase(name)) return ability;
		}
		return null;
	}
	
	public static AbilityInfo getAbility(int id) {
		if(!abilityIds.containsKey(id)) return AbilityInfo.NONE;
		else return abilityIds.get(id);
	}
	
	public static void fillIds() {
		for(AbilityInfo ability : values()) {
			abilityIds.put(ability.getID(), ability);
		}
	}
	
	public static A getAction(AbilityInfo ability) {
		if(ability.equals(AbilityInfo.BERSERKER)) return A.R;
		else if(ability.equals(AbilityInfo.PROXIMITY_HEAL)) return A.R;
		else if(ability.equals(AbilityInfo.TRIPLE_SHOT)) return A.L;
		else if(ability.equals(AbilityInfo.STOMP)) return A.R_BLOCK;
		else if(ability.equals(AbilityInfo.RUSH)) return A.R;
		else if(ability.equals(AbilityInfo.DODGE)) return A.R_BLOCK;
		else if(ability.equals(AbilityInfo.SHOCKWAVE)) return A.R_BLOCK;
		else if(ability.equals(AbilityInfo.RESIST)) return A.R;
		else if(ability.equals(AbilityInfo.LEAP)) return A.R;
		else if(ability.equals(AbilityInfo.WILDFIRE)) return A.R_BOTH;
		else if(ability.equals(AbilityInfo.SMASH)) return A.R_BLOCK;
		else if(ability.equals(AbilityInfo.RICOCHET)) return A.R_BOTH;
		else if(ability.equals(AbilityInfo.FADE)) return A.R_BOTH;
		else if(ability.equals(AbilityInfo.BEAM)) return A.R_BOTH;
		else if(ability.equals(AbilityInfo.FROST_AXE)) return A.R;
		else if(ability.equals(AbilityInfo.FOUNTAIN)) return A.R;
		else if(ability.equals(AbilityInfo.MAGNETIC_HAUL)) return A.R_BOTH;
		else if(ability.equals(AbilityInfo.BARRIER)) return A.R_BOTH;
		else if(ability.equals(AbilityInfo.HELLFIRE_AXE)) return A.R;
		else if(ability.equals(AbilityInfo.TRAIL)) return A.R_BLOCK;
		else if(ability.equals(AbilityInfo.BURST)) return A.R;
		else if(ability.equals(AbilityInfo.ARCTIC_BLAST)) return A.R;
		else if(ability.equals(AbilityInfo.BLIZZARD)) return A.R_BOTH;
		return null;
	}
	
	public static AbilityInfo[] getDefaults(ClassType set) {
		AbilityInfo[] defaults = new AbilityInfo[6];
		if(set.equals(ClassType.LEATHER)) {
			defaults[0] = AbilityInfo.DODGE;
			defaults[1] = AbilityInfo.LEAP;
			defaults[2] = AbilityInfo.ARROW_CRIPPLING;
			defaults[3] = AbilityInfo.WALL_HOP;
			defaults[4] = AbilityInfo.REAPER;
			defaults[5] = AbilityInfo.BREAK_FALL;
		}
		if(set.equals(ClassType.GOLD)) {
			defaults[0] = AbilityInfo.WILDFIRE;
			defaults[1] = AbilityInfo.RUSH;
			defaults[2] = AbilityInfo.ARROW_INCENDIARY;
			defaults[3] = AbilityInfo.ENDURANCE;
			defaults[4] = AbilityInfo.ARROW_REGENERATIVE;
			defaults[5] = AbilityInfo.APTITUDE;
		}
		if(set.equals(ClassType.CHAIN)) {
			defaults[0] = AbilityInfo.PROXIMITY_HEAL;
			defaults[1] = AbilityInfo.BERSERKER;
			defaults[2] = AbilityInfo.TRIPLE_SHOT;
			defaults[3] = AbilityInfo.HASTE;
			defaults[4] = AbilityInfo.SWITFTNESS;
			defaults[5] = AbilityInfo.SUSTENANACE;
		}
		if(set.equals(ClassType.IRON)) {
			defaults[0] = AbilityInfo.SHOCKWAVE;
			defaults[1] = AbilityInfo.RESIST;
			defaults[2] = AbilityInfo.ARROW_PENETRATIVE;
			defaults[3] = AbilityInfo.ESCAPE;
			defaults[4] = AbilityInfo.DESPERATION;
			defaults[5] = AbilityInfo.BREAK_FALL;
		}
		if(set.equals(ClassType.DIAMOND)) {
			defaults[0] = AbilityInfo.STOMP;
			defaults[1] = AbilityInfo.FROST_AXE;
			defaults[2] = AbilityInfo.ARROW_NOXIOUS;
			defaults[3] = AbilityInfo.SOOTHE;
			defaults[4] = AbilityInfo.TOUGHNESS;
			defaults[5] = AbilityInfo.BREATHING;
		}
		if(set.equals(ClassType.NONE) || set.equals(ClassType.EMPTY)) {
			defaults[0] = AbilityInfo.NONE;
			defaults[1] = AbilityInfo.NONE;
			defaults[2] = AbilityInfo.NONE;
			defaults[3] = AbilityInfo.NONE;
			defaults[4] = AbilityInfo.NONE;
			defaults[5] = AbilityInfo.NONE;
		}
		return defaults;
	}
	
	public static AbilityInfo getDefault(ClassType set, AbilityType type) {
		if(set.equals(ClassType.LEATHER)) {
			if(type.equals(AbilityType.SWORD)) return AbilityInfo.DODGE;
			else if(type.equals(AbilityType.AXE)) return AbilityInfo.LEAP;
			else if(type.equals(AbilityType.BOW)) return AbilityInfo.ARROW_CRIPPLING;
			else if(type.equals(AbilityType.PASSIVE)) return AbilityInfo.WALL_HOP;
			else if(type.equals(AbilityType.PASSIVE_II)) return AbilityInfo.REAPER;
			else if(type.equals(AbilityType.PASSIVE_III)) return AbilityInfo.BREAK_FALL;
		}
		if(set.equals(ClassType.GOLD)) {
			if(type.equals(AbilityType.SWORD)) return AbilityInfo.WILDFIRE;
			else if(type.equals(AbilityType.AXE)) return AbilityInfo.RUSH;
			else if(type.equals(AbilityType.BOW)) return AbilityInfo.ARROW_INCENDIARY;
			else if(type.equals(AbilityType.PASSIVE)) return AbilityInfo.ENDURANCE;
			else if(type.equals(AbilityType.PASSIVE_II)) return AbilityInfo.ARROW_REGENERATIVE;
			else if(type.equals(AbilityType.PASSIVE_III)) return AbilityInfo.APTITUDE;
		}
		if(set.equals(ClassType.CHAIN)) {
			if(type.equals(AbilityType.SWORD)) return AbilityInfo.PROXIMITY_HEAL;
			else if(type.equals(AbilityType.AXE)) return AbilityInfo.BERSERKER;
			else if(type.equals(AbilityType.BOW)) return AbilityInfo.TRIPLE_SHOT;
			else if(type.equals(AbilityType.PASSIVE)) return AbilityInfo.HASTE;
			else if(type.equals(AbilityType.PASSIVE_II)) return AbilityInfo.SWITFTNESS;
			else if(type.equals(AbilityType.PASSIVE_III)) return AbilityInfo.SUSTENANACE;
		}
		if(set.equals(ClassType.IRON)) {
			if(type.equals(AbilityType.SWORD)) return AbilityInfo.SHOCKWAVE;
			else if(type.equals(AbilityType.AXE)) return AbilityInfo.RESIST;
			else if(type.equals(AbilityType.BOW)) return AbilityInfo.ARROW_PENETRATIVE;
			else if(type.equals(AbilityType.PASSIVE)) return AbilityInfo.ESCAPE;
			else if(type.equals(AbilityType.PASSIVE_II)) return AbilityInfo.DESPERATION;
			else if(type.equals(AbilityType.PASSIVE_III)) return AbilityInfo.BREAK_FALL;
		}
		if(set.equals(ClassType.DIAMOND)) {
			if(type.equals(AbilityType.SWORD)) return AbilityInfo.STOMP;
			else if(type.equals(AbilityType.AXE)) return AbilityInfo.FROST_AXE;
			else if(type.equals(AbilityType.BOW)) return AbilityInfo.ARROW_NOXIOUS;
			else if(type.equals(AbilityType.PASSIVE)) return AbilityInfo.SOOTHE;
			else if(type.equals(AbilityType.PASSIVE_II)) return AbilityInfo.TOUGHNESS;
			else if(type.equals(AbilityType.PASSIVE_III)) return AbilityInfo.BREATHING;
		}
		return AbilityInfo.NONE;
		
	}
	
	public static String getAction(String name) {
		AbilityInfo ability = getAbilityExact(name);
		if(ability == null) return "None";
		else return ability.getAction().getName();
	}
}
