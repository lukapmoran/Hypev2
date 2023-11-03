package me.loogeh.Hype.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import me.loogeh.Hype.Main.Main;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Item {
	NULL("None", Material.AIR, "null", "none"),
	AIR("Air", Material.AIR, "air", "fists", "0"),
	STONE("Stone", Material.STONE, "stone", "1"),
	GRANITE("Granite", new ItemBuilder(Material.STONE).addDurability(1).toItemStack(), "granite", "stone:1", "1:1"),
	POLISHED_GRANITE("Polished Granite", new ItemBuilder(Material.STONE).addDurability(2).toItemStack(), "polished_granite", "polishedgranite", "stone:2", "1:2"),
	DIORITE("Diorite", new ItemBuilder(Material.STONE).addDurability(3).toItemStack(), "diorite", "stone:3", "1:3"),
	POLISHED_DIORITE("Polished Diorite", new ItemBuilder(Material.STONE).addDurability(4).toItemStack(), "polished_diorite", "polished_diorite", "stone:4", "1:4"),
	ANDESITE("Andesite", new ItemBuilder(Material.STONE).addDurability(5).toItemStack(), "andesite", "stone:5", "1:5"),
	POLISHED_ANDESITE("Polished Andesite", new ItemBuilder(Material.STONE).addDurability(6).toItemStack(), "polished_andesite", "polishedandesite", "stone:6", "1:6"),
	GRASS("Grass", Material.GRASS, "grass", "2"),
	DIRT("Dirt", Material.DIRT, "dirt", "3"),
	GRASSLESS_DIRT("Grassless Dirt", new ItemBuilder(Material.DIRT).addDurability(1).toItemStack(), "grassless_dirt", "grasslessdirt", "dirt:1", "3:1"),
	PODZOL("Podzol", new ItemBuilder(Material.DIRT).addDurability(2).toItemStack(), "podzol", "dirt:2", "3:2"),
	COBBLESTONE("Cobblestone", Material.COBBLESTONE, "cobblestone", "cobble_stone", "cobble", "4"),
	OAK_WOOD("Oak Wood", Material.WOOD, "oak_wood", "oakwood", "wood", "5"),
	SPRUCE_WOOD("Spruce Wood", new ItemBuilder(Material.WOOD).addDurability(1).toItemStack(), "spruce_wood", "sprucewood", "wood:1", "5:1"),
	BIRCH_WOOD("Birch Wood", new ItemBuilder(Material.WOOD).addDurability(2).toItemStack(), "birch_wood", "birchwood", "wood:2", "5:2"),
	JUNGLE_WOOD("Jungle Wood", new ItemBuilder(Material.WOOD).addDurability(3).toItemStack(), "jungle_wood", "junglewood", "wood:3", "5:3"),
	ACACIA_WOOD("Acacia Wood", new ItemBuilder(Material.WOOD).addDurability(4).toItemStack(), "acacia_wood", "acaciawood", "wood:4", "5:4"),
	DARK_OAK_WOOD("Dark Oak Wood", new ItemBuilder(Material.WOOD).addDurability(5).toItemStack(), "dark_oak_wood", "darkoakwood", "dark_oak", "darkoak", "wood:5", "5:5"),
	OAK_SAPLING("Oak Sapling", Material.SAPLING, "sapling", "oak_sapling", "oaksapling", "sapling_oak", "saplingoak", "6"),
	SPRUCE_SAPLING("Spruce Sapling", new ItemBuilder(Material.SAPLING).addDurability(1).toItemStack(), "spruce_sapling", "sprucesapling", "sapling_spruce", "saplingspruce", "6:1", "sapling:1"),
	BIRCH_SAPLING("Birch Sapling", new ItemBuilder(Material.SAPLING).addDurability(2).toItemStack(), "birch_sapling", "birchsapling", "sapling_birch", "saplingbirch", "sapling:2", "6:2"),
	JUNGLE_SAPLING("Jungle Sapling", new ItemBuilder(Material.SAPLING).addDurability(3).toItemStack(), "jungle_sapling", "junglesapling", "sapling_jungle", "saplingjungle", "6:3", "sapling:3"),
	ACACIA_SAPLING("Acacia Sapling", new ItemBuilder(Material.SAPLING).addDurability(4).toItemStack(), "acacia_sapling", "acaciasapling", "sapling:4", "6:4"),
	DARK_OAK_SAPLING("Dark Oak Sapling", new ItemBuilder(Material.SAPLING).addDurability(5).toItemStack(), "dark_oak_sapling", "darkoak_sapling", "darkoaksapling", "sapling_dark_oak", "sapling_darkoak", "saplingdarkoak", "sapling:5", "6:5"),
	BEDROCK("Bedrock", Material.BEDROCK, "bedrock", "7"),
	WATER("Water", Material.WATER, "water", "8"),
	STATIONARY_WATER("Stationary Water", Material.STATIONARY_WATER, "stationary_water", "water_stationary", "s_water", "swater", "9"),
	LAVA("Lava", Material.LAVA, "lava", "10"),
	STATIONARY_LAVA("Stationary Lava", Material.STATIONARY_LAVA, "stationary_lava", "lava_stationary", "s_lava", "slava", "11"),
	SAND("Sand", Material.SAND, "sand", "12"),
	RED_SAND("Red Sand", new ItemBuilder(Material.SAND).addDurability(1).toItemStack(), "red_sand", "redsand", "sand:1", "12:1"),
	GRAVEL("Gravel", Material.GRAVEL, "gravel", "13"),
	GOLD_ORE("Gold Ore", Material.GOLD_ORE, "gold_ore", "goldore", "ore_gold", "oregold", "14"),
	IRON_ORE("Iron Ore", Material.IRON_ORE, "iron_ore", "ironore", "ore_iron", "oreiron", "15"),
	COAL_ORE("Coal Ore", Material.COAL_ORE, "coal_ore", "coalore", "ore_coal", "orecoal", "16"),
	OAK_LOG("Oak Log", Material.LOG, "oak_log", "oaklog", "log_oak", "logoak", "log", "17"),
	SPRUCE_LOG("Spruce Log", new ItemBuilder(Material.LOG).addDurability(1).toItemStack(), "spruce_log", "sprucelog", "log_spruce", "logspruce", "log:1", "17:1"),
	BIRCH_LOG("Birch Log", new ItemBuilder(Material.LOG).addDurability(2).toItemStack(), "birch_log", "birchlog", "log_birch", "logbirch", "log:2","17:2"),
	JUNGLE_LOG("Jungle Log", new ItemBuilder(Material.LOG).addDurability(3).toItemStack(), "jungle_log", "junglelog", "log_jungle", "logjungle","log:3", "17:3"),
	OAK_LEAVES("Oak Leaves", Material.LEAVES, "leaves", "leaf", "oak_leaves", "oakleaves", "leaves_oak", "leavesoak", "18"),
	SPRUCE_LEAVES("Spruce Leaves", new ItemBuilder(Material.LEAVES).addDurability(1).toItemStack(), "spruce_leaves", "spruce_leaf", "spruceleaves", "spruceleaf", "leaves_spruce", "leavesspruce", "leaves:1", "leaf:1", "18:1"),
	BIRCH_LEAVES("Birch Leaves", new ItemBuilder(Material.LEAVES).addDurability(2).toItemStack(), "birch_leaves", "birchleaves", "birch_leaf", "birchleaf", "leaves_birch", "leavesbirch", "leaves:2", "leaf:2", "18:2"),
	JUNGLE_LEAVES("Jungle Leaves", new ItemBuilder(Material.LEAVES).addDurability(3).toItemStack(), "jungle_leaves", "jungleleaves", "jungle_leaf", "jungleleaf", "leaves_jungle", "leavesjungle", "leaves:3", "leaf:3", "18:3"),
	SPONGE("Sponge", Material.SPONGE, "sponge", "19"),
	GLASS("Glass", Material.GLASS, "glass", "20"),
	LAPIS_ORE("Lapis Ore", Material.LAPIS_ORE, "lapis_ore", "lapisore", "ore_lapis", "orelapis", "21"),
	LAPIS_BLOCK("Lapis Block", Material.LAPIS_BLOCK, "lapis_block", "lapisblock", "block_of_lapis", "blockoflapis", "22"),
	DISPENSER("Dispenser", Material.DISPENSER, "dispenser", "23"),
	SANDSTONE("Sandstone", Material.SANDSTONE, "sandstone", "sand_stone", "24"),
	CHISELED_SANDSTONE("Chiseled Sandstone", new ItemBuilder(Material.SANDSTONE).addDurability(1).toItemStack(), "chiseled_sandstone", "chiseledsandstone", "sandstone_chiseled", "sandstonechiseled", "sandstone:1", "24:1"),
	SMOOTH_SANDSTONE("Smooth Sandstone", new ItemBuilder(Material.SANDSTONE).addDurability(2).toItemStack(), "smooth_sandstone", "smoothsandstone", "sandstone_smooth", "sandstonesmooth", "sandstone:2", "24:2"),
	NOTE_BLOCK("Note Block", Material.NOTE_BLOCK, "note_block", "noteblock", "25"),
	BED_BLOCK("Bed Block", Material.BED_BLOCK, "bed_block", "bedblock", "26"),
	POWERED_RAIL("Powered Rail", Material.POWERED_RAIL, "powered_rail", "poweredrail", "27"),
	DETECTOR_RAIL("Detector Rail", Material.DETECTOR_RAIL, "detector_rail", "detectorrail", "28"),
	STICKY_PISTON("Sticky Piston", Material.PISTON_STICKY_BASE, "sticky_piston", "stickypiston", "piston_sticky", "pistonsticky", "29"),
	WEB("Web", Material.WEB, "web", "30"),
	DEAD_SHRUB("Dead Shrub", Material.LONG_GRASS, "tall_grass", "tallgrass", "long_grass", "longgrass", "31"),
	TALL_GRASS("Tall Grass", new ItemBuilder(Material.LONG_GRASS).addDurability(1).toItemStack(), "tall_grass", "tallgrass", "grass_tall", "grasstall", "dead_shrub:1", "deadshrub:1", "tall_grass:1", "tallgrass:1", "longgrass:1", "long_grass:1", "31:1"),
	FERN("Fern", new ItemBuilder(Material.LONG_GRASS).addDurability(2).toItemStack(), "fern", "dead_shrub:2", "deadshrub:2", "31:2"),
	DEAD_SHRUB_2("Dead Shrub", Material.DEAD_BUSH, "dead_shrub", "deadshrub", "32"),
	PISTON("Piston", Material.PISTON_BASE, "piston", "piston_base", "33"),
	PISTON_HEAD("Piston Head", Material.PISTON_EXTENSION, "piston_head", "pistonhead", "34"),
	WOOL("Wool", Material.WOOL, "wool", "white_wool", "wool_white", "woolwhite", "35"),
	ORANGE_WOOL("Orange Wool", new ItemBuilder(Material.WOOL).addDurability(1).toItemStack(), "orange_wool", "orangewool", "wool_orange", "woolorange", "wool:1", "35:1"),
	MAGENTA_WOOL("Magenta Wool", new ItemBuilder(Material.WOOL).addDurability(2).toItemStack(), "magenta_wool", "magentawool", "wool_magenta", "woolmagenta", "wool:2", "35:2"),
	LIGHT_BLUE_WOOL("Light Blue Wool", new ItemBuilder(Material.WOOL).addDurability(3).toItemStack(), "light_blue_wool", "lightbluewool", "wool_light_blue", "wool_lightblue", "woollightblue", "wool:3", "35:3"),
	YELLOW_WOOL("Yellow Wool", new ItemBuilder(Material.WOOL).addDurability(4).toItemStack(), "yellow_wool", "yellowwool", "wool_yellow", "woolyellow", "yellowool", "wool:4", "35:4"),
	LIME_WOOL("Lime Wool", new ItemBuilder(Material.WOOL).addDurability(5).toItemStack(), "lime_wool", "limewool", "wool_lime", "woollime", "woolime", "light_green_wool", "lightgreen_wool", "wool_light_green", "wool_lightgreen", "woollightgreen", "wool_bright_green", "wool_brightgreen", "woolbrightgreen", "bright_green_wool", "brightgreen_wool", "brightgreenwool", "wool:5", "35:5"),
	PINK_WOOL("Pink Wool", new ItemBuilder(Material.WOOL).addDurability(6).toItemStack(), "pink_wool", "pinkwool", "wool_pink", "woolpink", "wool:6", "35:6"),
	GRAY_WOOL("Gray Wool", new ItemBuilder(Material.WOOL).addDurability(7).toItemStack(), "gray_wool", "graywool", "wool_gray", "woolgray", "wool:7", "35:7"),
	LIGHT_GRAY_WOOL("Light Gray Wool", new ItemBuilder(Material.WOOL).addDurability(8).toItemStack(), "light_gray_wool", "lightgray_wool", "lightgraywool", "wool_lightgray", "woollightgray", "wool:8", "35:8"),
	CYAN_WOOL("Cyan Wool", new ItemBuilder(Material.WOOL).addDurability(9).toItemStack(), "cyan_wool", "cyanwool", "wool_cyan", "woolcyan", "bright_blue_wool", "brightblue_wool", "brightbluewool", "wool_bright_blue", "wool_brightblue", "woolbrightblue", "wool:9", "35:9"),
	PURPLE_WOOL("Purple Wool", new ItemBuilder(Material.WOOL).addDurability(10).toItemStack(), "purple_wool", "purplewool", "wool_purple", "woolpurple", "wool:10", "35:10"),
	BLUE_WOOL("Blue Wool", new ItemBuilder(Material.WOOL).addDurability(11).toItemStack(), "blue_wool", "bluewool", "wool_blue", "woolblue", "dark_blue_wool", "darkblue_wool", "darkbluewool", "wool_dark_blue", "wool_darkblue", "wooldarkblue", "wool:11", "35:11"),
	BROWN_WOOL("Brown Wool", new ItemBuilder(Material.WOOL).addDurability(12).toItemStack(), "brown_wool", "brownwool", "wool_brown", "woolbrown", "wool:12", "35:12"),
	GREEN_WOOL("Green Wool", new ItemBuilder(Material.WOOL).addDurability(13).toItemStack(), "green_wool", "greenwool", "wool_green", "woolgreen", "dark_green_wool", "darkgreen_wool", "darkgreenwool", "wool_dark_green", "wool_darkgreen", "wooldarkgreen", "wool:13", "35:13"),
	RED_WOOL("Red Wool", new ItemBuilder(Material.WOOL).addDurability(14).toItemStack(), "red_wool", "redwool", "wool_red", "woolred", "wool:14", "35:14"),
	BLACK_WOOL("Black Wool", new ItemBuilder(Material.WOOL).addDurability(15).toItemStack(), "black_wool", "blackwool", "wool_black", "woolblack", "wool:15", "35:15"),
	PISTON_MOVING_PIECE("Piston Moving Piece", Material.PISTON_MOVING_PIECE, "piston_moving_piece", "piston_movingpiece", "pistonmovingpiece", "moving_piece_piston", "movingpiece_piston", "movingpiecepiston", "36"),
	DANDELION("Dandelion", Material.YELLOW_FLOWER, "dandelion", "yellow_flower", "yellowflower", "flower_yellow", "floweryellow", "37"),
	POPPY("Poppy", Material.RED_ROSE, "red_flower", "redflower", "flower_red", "flowerred", "flowered", "rose", "red_rose", "redrose", "rose_red", "rosered", "38"),
	BLUE_ORCHID("Blue Orchid", new ItemBuilder(Material.RED_ROSE).addDurability(1).toItemStack(), "blue_orchid", "blueorchid", "orchid_blue", "orchidblue", "blue_flower", "blueflower", "flower_blue", "flowerblue", "flower:1", "rose:1", "red_flower:1", "redflower:1", "38:1"),
	ALLIUM("Allium", new ItemBuilder(Material.RED_ROSE).addDurability(2).toItemStack(), "allium", "flower:2", "red_flower:2", "rose:2", "redflower:2", "38:2"),
	AZURE_BLUET("Azure Bluet", new ItemBuilder(Material.RED_ROSE).addDurability(3).toItemStack(), "azure_bluet", "azurebluet", "flower:3", "red_flower:3", "redflower:3", "rose:3", "38:3"),
	RED_TULIP("Red Tulip", new ItemBuilder(Material.RED_ROSE).addDurability(4).toItemStack(), "red_tulip", "redtulip", "tulip_red", "tulipred", "flower:4", "red_flower:4", "redflower:4", "rose:4", "38:4"),
	ORANGE_TULIP("Orange Tulip", new ItemBuilder(Material.RED_ROSE).addDurability(5).toItemStack(), "orange_tulip", "orangetulip", "tulip_orange", "tuliporange", "flower:5", "red_flower:5", "redflower:5", "rose:5", "38:5"),
	WHITE_TULIP("White Tulip", new ItemBuilder(Material.RED_ROSE).addDurability(6).toItemStack(), "white_tulip", "whitetulip", "tulip_white", "tulipwhite", "flower:6", "red_flower:6", "redflower:6", "rose:6", "38:6"),
	PINK_TULIP("Pink Tulip", new ItemBuilder(Material.RED_ROSE).addDurability(7).toItemStack(), "pink_tulip", "pinktulip", "tulip_pink", "tulippink", "tulipink", "flower:7", "red_flower:7", "redflower:7", "rose:7", "38:7"),
	OXEYE_DAISY("Oxeye Daisy", new ItemBuilder(Material.RED_ROSE).addDurability(8).toItemStack(), "oxeye_daisy", "oxeyedaisy", "daisy_oxeye", "daisyoxeye", "daisy", "flower:8", "red_flower:8", "redflower:8", "rose:8", "38:8"),
	BROWN_MUSHROOM("Brown Mushroom", Material.BROWN_MUSHROOM, "brown_mushroom", "brownmushroom", "mushroom_brown", "mushroombrown", "39"),
	RED_MUSHROOM("Red Mushroom", Material.RED_MUSHROOM, "red_mushroom", "redmushroom", "mushroom_red", "mushroomred", "40"),
	GOLD_BLOCK("Gold Block", Material.GOLD_BLOCK, "gold_block", "goldblock", "block_of_gold", "blockofgold", "41"),
	IRON_BLOCK("Iron Block", Material.IRON_BLOCK, "iron_block", "ironblock", "block_of_iron", "blockofiron", "42"),
	DOUBLE_STONE_SLAB("Double Stone Slab", Material.DOUBLE_STEP, "double_stone_slab", "double_stoneslab", "doublestoneslab", "stone_slab_double", "stoneslabdouble", "double_slab", "doubleslab", "43"),
	DOUBLE_SANDSTONE_SLAB("Double Sandstone Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(1).toItemStack(), "double_sandstone_slab", "double_sandstoneslab", "doublesandstoneslab", "sandstone_slab_double", "sandstoneslab_double", "sandstoneslabdouble", "double_slab:1", "doubleslab:1", "43:1"),
	DOUBLE_WOODEN_SLAB("Double Wooden Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(2).toItemStack(), "double_wooden_slab", "double_woodenslab", "doublewoodenslab", "wooden_slab_double", "woodenslab_double", "woodenslabdouble", "double_wood_slab", "doublewood_slab", "doublewoodslab", "double_slab:2", "doubleslab:2", "43:2"),
	DOUBLE_COBBLESTONE_SLAB("Double Cobblestone Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(3).toItemStack(), "double_cobblestone_slab", "double_cobblestoneslab", "doublecobblestoneslab", "cobblestone_slab_double", "cobblestoneslab_double", "double_cobble_slab", "doublecobble_slab", "doublecobbleslab", "cobble_slab_double", "cobbleslab_double", "cobbleslabdouble", "double_slab:3", "doubleslab:3", "43:3"),
	DOUBLE_BRICK_SLAB("Double Brick Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(4).toItemStack(), "double_brick_slab", "double_brickslab", "doublebrickslab", "brick_slab_double", "brickslab_double", "brickslabdouble", "double_slab:4", "doubleslab:4", "43:4"),
	DOUBLE_STONEBRICK_SLAB("Double Stonebrick Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(5).toItemStack(), "double_stone_brick_slab", "double_stone_brickslab", "double_stonebrickslab", "doublestonebrickslab", "stone_brick_slab_double", "stonebrick_slab_double", "stonebrickslab_double", "stonebrickslabdouble", "double_slab:5", "doubleslab:5", "43:5"),
	DOUBLE_NETHERBRICK_SLAB("Double Netherbrick Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(6).toItemStack(), "double_nether_brick_slab", "double_nether_brickslab", "double_netherbrickslab", "doublenetherbrickslab", "nether_brick_slab_double", "netherbrick_slab_double", "netherbrickslab_double", "netherbrickslabdouble", "double_slab:6", "doubleslab:6", "43:6"),
	DOUBLE_QUARTZ_SLAB("Double Quartz Slab", new ItemBuilder(Material.DOUBLE_STEP).addDurability(7).toItemStack(), "double_quartz_slab", "double_quartzslab", "doublequartzslab", "quartz_slab_double", "quartzslab_double", "quartzslabdouble", "double_slab:7", "doubleslab:7", "43:7"),
	STONE_SLAB("Stone Slab", Material.STEP, "stone_slab", "stoneslab", "slab_stone", "slabstone", "slab", "44"),
	SANDSTONE_SLAB("Sandstone Slab", new ItemBuilder(Material.STEP).addDurability(1).toItemStack(), "sandstone_slab", "sand_stone_slab", "sandstoneslab", "slab_sandstone", "slab_sand_stone", "slabsandstone", "slab:1", "44:1"),
	WOODEN_SLAB("Wooden Slab", new ItemBuilder(Material.STEP).addDurability(2).toItemStack(), "wooden_slab", "woodenslab", "slab_wooden", "slabwooden", "slab:2", "44:2"),
	COBBLESTONE_SLAB("Cobblestone Slab", new ItemBuilder(Material.STEP).addDurability(3).toItemStack(), "cobblestone_slab", "cobblestoneslab", "slab_cobble_stone", "slab_cobblestone", "slabcobblestone", "cobble_slab", "cobbleslab", "slab_cobble", "slabcobble", "slab:3", "44:3"),
	BRICK_SLAB("Brick Slab", new ItemBuilder(Material.STEP).addDurability(4).toItemStack(), "brick_slab", "brickslab", "slab_brick", "slabbrick", "slab:4", "44:4"),
	STONEBRICK_SLAB("Stonebrick Slab", new ItemBuilder(Material.STEP).addDurability(5).toItemStack(), "stone_brick_slab", "stonebrick_slab", "stonebrickslab", "slab_stone_brick", "slab_stonebrick", "slabstonebrick", "slab:5", "44:5"),
	NETHER_BRICK_SLAB("Netherbrick Slab", new ItemBuilder(Material.STEP).addDurability(6).toItemStack(), "nether_brick_slab", "netherbrick_slab", "netherbrickslab", "slab_nether_brick", "slab_netherbrick", "slabnetherbrick", "slab:6", "44:6"),
	QUARTZ_SLAB("Quartz Slab", new ItemBuilder(Material.STEP).addDurability(7).toItemStack(), "quartz_slab", "quartzslab", "slab_qurtz", "slabquartz", "slab:7", "44:7"),
	BRICK("Brick", Material.BRICK, "brick", "brick_block", "brickblock", "block_of_bricks", "blockofbricks", "45"),
	TNT("TNT", Material.TNT, "tnt", "explosives", "46"),
	BOOKSHELF("Bookshelf", Material.BOOKSHELF, "bookshelf", "bookshelves", "47"),
	MOSSY_COBBLESTONE("Mossy Cobblestone", Material.MOSSY_COBBLESTONE, "mossy_cobble_stone", "mossy_cobblestone", "mossycobblestone", "cobble_stone_mossy", "cobblestone_mossy", "cobblestonemossy", "mossy_cobble", "mossycobble", "cobble_mossy", "cobblemossy", "48"),
	OBSIDIAN("Obsidian", Material.OBSIDIAN, "obisidian", "49"),
	TORCH("Torch", Material.TORCH, "torch", "50"),
	FIRE("Fire", Material.FIRE, "fire", "51"),
	MONSTER_SPAWNER("Monster Spawner", Material.MOB_SPAWNER, "monster_spawner", "monsterspawner", "mob_spawner", "mobspawner", "spawner", "52"),
	OAK_WOOD_STAIRS("Oak Stairs", Material.WOOD_STAIRS, "oak_wood_stairs", "oakwood_stairs", "oakwoodstairs", "wood_stairs", "woodstairs", "wooden_stairs", "woodenstairs", "stairs_oak_wood", "stairs_oakwood", "stairsoakwood", "stairs_wood", "stairswood", "stairs_wooden", "stairswooden", "53"),
	CHEST("Chest", Material.CHEST, "chest", "single_chest", "singlechest", "chest_single", "chestsingle", "54"),
	REDSTONE_WIRE("Redstone Wire", Material.REDSTONE_WIRE, "redstone_wire", "restonewire", "55"),
	DIAMOND_ORE("Diamond Ore", Material.DIAMOND_ORE, "diamond_ore", "diamondore", "ore_diamond", "orediamond", "56"),
	DIAMOND_BLOCK("Diamond Block", Material.DIAMOND_BLOCK, "diamond_block", "diamondblock", "block_of_diamond", "blockofdiamond", "57"),
	WORKBENCH("Workbench", Material.WORKBENCH, "workbench", "crafting_table", "craftingtable", "58"),
	WHEAT_CROPS("Wheat Crops", Material.CROPS, "wheat_crops", "wheatcrops", "wheat_crop", "wheatcrop", "crops", "crop", "59"),
	SOIL("Soil", Material.SOIL, "soil", "60"),
	FURNACE("Furnace", Material.FURNACE, "furnace", "61"),
	BURNING_FURNACE("Burning Furnace", Material.BURNING_FURNACE, "burning_furnace", "burningfurnace", "furnace_burning", "furnaceburning", "62"),
	SIGN_POST("Sign Post", Material.SIGN_POST, "sign_post", "signpost", "sing_post", "singpost", "63"),
	WOODEN_DOOR_BLOCK("Wooden Door Block", Material.WOODEN_DOOR, "wooden_door_block", "woodendoor_block", "woodendoorblock", "wood_door_block", "wooddoor_block", "wooddoorblock", "woodoorblock", "door_wooden", "doorwooden", "door_wood", "doorwood", "64"),
	LADDER("Ladder", Material.LADDER, "ladder", "65"),
	RAILS("Rails", Material.RAILS, "rails", "minecart_rails", "minecartrails", "minecart_track", "minecarttrack", "minecartrack", "track", "66"),
	COBBLESTONE_STAIRS("Cobblestone Stairs", Material.COBBLESTONE_STAIRS, "cobble_stone_stairs", "cobblestone_stairs", "cobblestonestairs", "cobble_stairs", "cobblestairs", "stairs_cobble_stone", "stairs_cobblestone", "stairscobblestone", "stairs_cobble", "stairscobble", "67"),
	WALL_SIGN("Wall Sign", Material.WALL_SIGN, "wall_sign", "wallsign", "wall_sing", "wallsing", "sign_wall", "signwall", "sing_wall", "singwall", "68"),
	LEVER("Lever", Material.LEVER, "lever", "69"),
	STONE_PRESSURE_PLATE("Stone Pressure Plate", Material.STONE_PLATE, "stone_pressure_plate", "stone_pressureplate", "stonepressureplate", "stone_plate", "stoneplate", "pressure_plate_stone", "pressureplate_stone", "pressureplatestone", "plate_stone", "platestone", "70"),
	IRON_DOOR_BLOCK("Iron Door Block", Material.IRON_DOOR_BLOCK, "iron_door_block", "irondoor_block", "irondoorblock", "71"),
	WOODEN_PRESSURE_PLATE("Wooden Pressure Plate", Material.WOOD_PLATE, "wooden_pressure_plate", "wooden_pressureplate", "woodenpressureplate", "wood_pressure_plate", "wood_pressureplate", "woodpressureplate", "pressure_plate_wooden", "pressure_plate_wood", "pressureplate_wooden", "pressureplatewooden", "pressureplate_wood", "pressureplatewooden", "72"),
	REDSTONE_ORE("Redstone Ore", Material.REDSTONE_ORE, "redstone_ore", "redstoneore", "ore_redstone", "oreredstone", "73"),
	GLOWING_REDSTONE_ORE("Glowing Redstone Ore", Material.GLOWING_REDSTONE_ORE, "glowing_redstone_ore", "glowing_redstoneore", "glowingredstoneore", "ore_redstone_glowing", "oreredstoneglowing", "74"),
	REDSTONE_TORCH("Redstone Torch", Material.REDSTONE_TORCH_OFF, "redstone_torch", "redstonetorch", "redstone_torch_off", "redstonetorch_off",  "torch_redstone", "torchredstone", "torch_redstone_off", "75"),
	REDSTONE_TORCH_ON("Redstone Torch On", Material.REDSTONE_TORCH_ON, "redstone_torch_on", "redstonetorch_on", "redstonetorchon", "torch_redstone_on", "torch_redstoneon", "torchredstoneon", "76"),
	STONE_BUTTON("Stone Button", Material.STONE_BUTTON, "stone_button", "stonebutton", "button_stone", "buttonstone", "77"),
	SNOW("Snow", Material.SNOW, "snow", "78"),
	ICE("Ice", Material.ICE, "ice", "79"),
	SNOW_BLOCK("Snow Block", Material.SNOW_BLOCK, "snow_block", "snowblock", "block_of_snow", "blockofsnow", "80"),
	CACTUS("Cactus", Material.CACTUS, "cactus", "81"),
	CLAY("Clay", Material.CLAY, "clay_block", "clayblock", "block_of_clay", "blockofclay", "82"),
	SUGAR_CANE_BLOCK("Sugar Cane Block", Material.SUGAR_CANE_BLOCK, "sugar_cane_block", "sugarcane_block", "sugarcaneblock", "block_of_sugar_cane", "blockofsugarcane", "block_of_sugarcane", "83"),
	JUKEBOX("Jukebox", Material.JUKEBOX, "jukebox", "record_player", "recordplayer", "84"),
	FENCE("Fence", Material.FENCE, "fence", "wooden_fence", "woodenfence", "wood_fence", "woodfence", "fence_wooden", "fencewooden", "fence_wood", "fencewood", "85"),
	PUMPKIN("Pumpkin", Material.PUMPKIN, "pumpkin", "pumpking", "86"),
	NETERRACK("Netherrack", Material.NETHERRACK, "netherrack", "netherack", "87"),
	SOUL_SAND("Soul Sand", Material.SOUL_SAND, "soul_sand", "soulsand", "88"),
	GLOWSTONE("Glowstone", Material.GLOWSTONE, "glowstone", "glow_stone", "89"),
	PORTAL("Portal", Material.PORTAL, "portal", "portal_block", "portalblock", "90"),
	JACK_O_LANTERN("Jack O Lantern", Material.JACK_O_LANTERN, "jack_o_lantern", "jackolantern", "lantern", "pumpkin_lit", "pumpkinlit", "lit_pumpkin", "litpumpkin", "91"),
	CAKE_BLOCK("Cake Block", Material.CAKE_BLOCK, "cake_block", "cakeblock", "block_of_cake", "blockofcake", "92"),
	REDSTONE_REPEATER("Redstone Repeater", Material.DIODE_BLOCK_OFF, "redstone_repeater", "redstonerepeater", "repeater", "redstone_repeater_off", "redstonerepeater_off", "redstonerepeateroff", "repeater_off", "repeateroff", "93"),
	REDSTONE_REPEATER_ON("Redstone Repeater On", Material.DIODE_BLOCK_ON, "redstone_repeater_on", "redstonerepeater_on", "redstonerepeateron", "repeater_on", "repeateron", "94"),
	STAINED_GLASS("Stained Glass", Material.STAINED_GLASS, "stained_glass", "stainedglass", "white_stained_glass", "white_stainedglass", "whitestainedglass", "glass_stained", "glassstained", "glass_stained_white", "glassstained_white", "glassstainedwhite", "95"),
	ORANGE_STAINED_GLASS("Orange Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(1).toItemStack(), "orange_stained_glass", "orange_stainedglass", "orangestainedglass", "stained_glass_orange", "stainedglass_orange", "stainedglassorange", "stained_glass:1", "stainedglass:1", "95:1"),
	MAGENTA_STAINED_GLASS("Magenta Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(2).toItemStack(), "magenta_stained_glass", "magenta_stainedglass", "magentastainedglass", "stained_glass_magenta", "stainedglass_magenta", "stainedglassmagenta", "stained_glass:2", "stainedglass:2", "95:2"),
	LIGHT_BLUE_STAINED_GLASS("Light Blue Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(3).toItemStack(), "light_blue_stained_glass", "lightblue_stained_glass", "light_blue_stainedglass", "lightblue_stainedglass", "lightbluestainedglass", "stained_glass_light_blue", "stainedglass_light_blue", "stainedglass_lightblue", "stainedglasslightblue", "stained_glass:3", "stainedglass:3", "95:3"),
	YELLOW_STAINED_GLASS("Yellow Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(4).toItemStack(), "yellow_stained_glass", "yellow_stainedglass", "yellowstainedglass", "stained_glass_yellow", "stainedglass_yellow", "stainedglassyellow", "stained_glass:4", "stainedglass:4", "95:4"),
	LIME_STAINED_GLASS("Lime Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(5).toItemStack(), "lime_stained_glass", "lime_stainedglass", "limestainedglass", "stained_glass_lime", "stainedglass_lime", "stainedglasslime", "stained_glass:5", "stainedglass:5", "95:5"),
	PINK_STAINED_GLASS("Pink Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(6).toItemStack(), "pink_stained_glass", "pink_stainedglass", "pinkstainedglass", "stained_glass_pink", "stainedglass_pink", "stainedglasspink", "stained_glass:6", "stainedglass:6", "95:6"),
	GRAY_STAINED_GLASS("Gray Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(7).toItemStack(), "gray_stained_glass", "gray_stainedglass", "graystainedglass", "stained_glass_gray", "stainedglass_gray", "stainedglassgray", "stained_glass:7", "stainedglass:7", "95:7"),
	LIGHT_GRAY_STAINED_GLASS("Light Gray Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(8).toItemStack(), "light_gray_stained_glass", "lightgray_stained_glass", "lightgray_stainedglass", "light_gray_stainedglass", "lightgraystainedglass", "stained_glass_light_gray", "stainedglass_light_gray", "stained_glass_lightgray", "stainedglass_lightgray", "stainedglasslightgray", "stained_glass:8", "stainedglass:8", "95:8"),
	CYAN_STAINED_GLASS("Cyan Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(9).toItemStack(), "cyan_stained_glass", "cyan_stainedglass", "cyanstainedglass", "stained_glass_cyan", "stainedglass_cyan", "stainedglasscyan", "stained_glass:9", "stainedglass:9", "95:9"),
	PURPLE_STAINED_GLASS("Purple Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(10).toItemStack(), "purple_stained_glass", "purple_stainedglass", "purplestainedglass", "stained_glass_purple", "stainedglass_purple", "stainedglasspurple", "stained_glass:10", "stainedglass:10", "95:10"),
	BLUE_STAINED_GLASS("Blue Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(11).toItemStack(), "blue_stained_glass", "blue_stainedglass", "bluestainedglass", "stained_glass_blue", "stainedglass_blue", "stainedglassblue", "stained_glass:11", "stainedglass:11", "95:11"),
	BROWN_STAINED_GLASS("Brown Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(12).toItemStack(), "brown_stained_glass", "brown_stainedglass", "brownstainedglass", "stained_glass_brown", "stainedglass_brown", "stainedglassbrown", "stained_glass:12", "stainedglass:12", "95:12"),
	GREEN_STAINED_GLASS("Green Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(13).toItemStack(), "green_stained_glass", "green_stainedglass", "greenstainedglass", "stained_glass_green", "stainedglass_green", "stainedglassgreen", "stained_glass:13", "stainedglass:13", "95:13"),
	RED_STAINED_GLASS("Red Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(14).toItemStack(), "red_stained_glass", "red_stainedglass", "redstainedglass", "stained_glass_red", "stainedglass_red", "stainedglassred", "stained_glass:14", "stainedglass:14", "95:14"),
	BLACK_STAINED_GLASS("Black Stained Glass", new ItemBuilder(Material.STAINED_GLASS).addDurability(15).toItemStack(), "black_stained_glass", "black_stainedglass", "blackstainedglass", "stained_glass_black", "stainedglass_black", "stainedglassblack", "stained_glass:15", "stainedglass:15", "95:15"),
	TRAPDOOR("Trapdoor", Material.TRAP_DOOR, "trapdoor", "trap_door", "wood_trap_door", "wood_trapdoor", "woodtrapdoor", "wooden_trap_door", "wooden_trapdoor", "woodentrapdoor", "96"),
	STONE_SILVERFISH("Stone (Silverfish)", Material.MONSTER_EGGS, "stone_silverfish", "stonesilverfish", "silverfish_stone", "silverfishstone", "97"),
	COBBLESTONE_SILVERFISH("Cobblestone (Silverfish)", new ItemBuilder(Material.MONSTER_EGGS).addDurability(1).toItemStack(), "cobblestone_silverfish", "cobblestonesilverfish", "silverfish_cobblestone", "silverfishcobblestone", "cobble_silverfish", "cobblesilverfish", "silverfish_cobble", "silverfishcobble", "silverfish_stone:1", "silverfishstone:1", "97:1"),
	STONEBRICK_SILVERFISH("Stonebrick (Silverfish)", new ItemBuilder(Material.MONSTER_EGGS).addDurability(2).toItemStack(), "stonebrick_silverfish", "stonebricksilverfish", "silverfish_stonebrick", "silverfishstonebrick", "silverfish_stone:2", "silverfishstone:2", "97:2"),
	MOSSY_STONEBRICK_SILVERFISH("Mossy Stonebrick (Silverfish)", new ItemBuilder(Material.MONSTER_EGGS).addDurability(3).toItemStack(), "mossy_stonebrick_silverfish", "mossystonebrick_silverfish", "mossystonebricksilverfish", "silverfish_mossy_stonebrick", "silverfish_mossystonebrick", "silverfishmossystonebrick", "silverfish_stone:3", "silverfishstone:3", "97:3"),
	CRACKED_STONEBRICK_SILVERFISH("Cracked Stonebrick (Silverfish)",  new ItemBuilder(Material.MONSTER_EGGS).addDurability(4).toItemStack(), "cracked_stonebrick_silverfish", "crackedstonebrick_silverfish", "crackedstonebricksilverfish", "silverfish_cracked_stonebrick", "silverfish_crackedstonebrick", "silverfishcrackedstonebrick", "silverfish_stone:4", "silverfishstone:4", "97:4"),
	CHISELED_STONEBRICK_SILVERFISH("Chiseled Stonebrick (Silverfish)", new ItemBuilder(Material.MONSTER_EGGS).addDurability(5).toItemStack(), "chiseled_stonebrick_silverfish", "chiseledstonebrick_silverfish", "chiseledstonebricksilverfish", "silverfish_chiseled_stonebrick", "silverfish_chiseledstonebrick", "silverfishchiseledstonebrick", "silverfish_stone:5", "silverfishstone:5", "97:5"),
	STONEBRICK("Stonebrick", Material.SMOOTH_BRICK, "stonebrick", "smoothbrick", "98"),
	MOSSY_STONEBRICK("Mossy Stonebrick", new ItemBuilder(Material.SMOOTH_BRICK).addDurability(1).toItemStack(), "mossy_stonebrick", "mossystonebrick", "mossy_smoothbrick", "mossysmoothbrick", "stonebrick_mossy", "stonebrickmossy", "smoothbrick_mossy", "smoothbrickmossy", "stonebrick:1", "smoothbrick:1", "98:1"),
	CRACKED_STONEBRICK("Cracked Stonebrick", new ItemBuilder(Material.SMOOTH_BRICK).addDurability(2).toItemStack(), "cracked_stonebrick", "crackedstonebrick", "cracked_smoothbrick", "crackedsmoothbrick", "stonebrick_cracked", "stonebrickcracked", "stonebrick:2", "smoothbrick:2", "98:2"),
	CHISELED_STONEBRICK("Chiseled Stonebrick", new ItemBuilder(Material.SMOOTH_BRICK).addDurability(3).toItemStack(), "chiseled_stonebrick", "chiseledstonebrick", "chiseled_smoothbrick", "chiseledsmoothbrick", "stonebrick:3", "smoothbrick:3", "98:3"),
	RED_MUSHROOM_BLOCK("Red Mushroom Block", Material.HUGE_MUSHROOM_1, "red_mushroom_block", "redmushroom_block", "redmushroomblock", "red_mushroom_cap", "redmushroom_cap", "redmushroomcap", "99"),
	BROWN_MUSHROOM_N("Brown Mushroom Block", Material.HUGE_MUSHROOM_2, "brown_mushroom_block", "brownmushroom_block", "brownmushroomblock", "brown_mushroom_cap", "brownmushroom_cap", "brownmushroomcap", "100"),
	IRON_BARS("Iron Bars", Material.IRON_FENCE, "iron_bars", "ironbars", "iron_fence", "ironfence", "101"),
	GLASS_PANE("Glass Pane", Material.THIN_GLASS, "glass_pane", "glasspane", "pane_of_glass", "paneofglass", "thin_glass", "thinglass", "102"),
	MELON_BLOCK("Melon Block", Material.MELON_BLOCK, "melon_block", "melonblock", "block_of_melon", "blockofmelon", "103"),
	PUMPKIN_STEM("Pumpkin Stem", Material.PUMPKIN_STEM, "pumpkin_stem", "pumpkinstem", "104"),
	MELON_STEM("Melon Stem", Material.MELON_STEM, "melon_stem", "melonstem", "105"),
	VINES("Vines", Material.VINE, "vines", "vine", "106"),
	FENCE_GATE("Fence Gate", Material.FENCE_GATE, "fence_gate", "fencegate", "wood_fence_gate", "woodfence_gate", "woodfencegate", "wooden_fence_gate", "woodenfence_gate", "woodenfencegate", "107"),
	BRICK_STAIRS("Brick Stairs", Material.BRICK_STAIRS, "brick_stairs", "brickstairs", "stairs_brick", "stairsbrick", "108"),
	STONEBRICK_STAIRS("Stonebrick Stairs", Material.SMOOTH_STAIRS, "stonebrick_stairs", "stonebrickstairs", "smoothbrick_stairs", "smoothbrickstairs", "smooth_stairs", "smoothstairs", "stairs_stonebrick", "stairsstonebrick", "stairs_smoothbrick", "stairssmoothbrick", "stairs_smooth", "stairssmooth", "109"),
	MYCELIUM("Mycelium", Material.MYCEL, "mycelium", "mycel", "110"),
	LILY_PAD("Lily Pad", Material.WATER_LILY, "lily_pad", "lilypad", "water_lily", "waterlily", "111"),
	NETHERBRICK("Netherbrick", Material.NETHER_BRICK, "nether_brick", "netherbrick", "112"),
	NETHERBRICK_FENCE("Netherbrick Fence", Material.NETHER_FENCE, "nether_brick_fence", "netherbrick_fence", "netherbrickfence", "113"),
	NETHERBRICK_STAIRS("Netherbrick Stairs", Material.NETHER_BRICK_STAIRS, "nether_brick_stairs", "netherbrick_stairs", "netherbrickstairs", "stairs_netherbrick", "stairsnetherbrick", "114"),
	NETHER_WART("Nether Wart", Material.NETHER_WARTS, "nether_warts", "netherwarts", "nether_wart", "netherwart", "115"),
	ENCHANTMENT_TABLE("Enchantment Table", Material.ENCHANTMENT_TABLE, "enchantment_table", "enchantmenttable", "enchantmentable", "table_of_enchantment", "tableofenchantment", "116"),
	BREWING_STAND("Brewing Stand", Material.BREWING_STAND, "brewing_stand", "brewingstand", "potionmaker", "117"),
	CAULDRON("Cauldron", Material.CAULDRON, "cauldron", "118"),
	END_PORTAL("End Portal", Material.ENDER_PORTAL, "end_portal", "endportal", "119"),
	END_PORTAL_FRAMCE("End Portal Frame", Material.ENDER_PORTAL_FRAME, "end_portal_frame", "endportal_frame", "endportalframe", "120"),
	END_STONE("End Stone", Material.ENDER_STONE, "end_stone", "endstone", "121"),
	DRAGON_EGG("Dragon Egg", Material.DRAGON_EGG, "dragon_egg", "dragonegg", "122"),
	REDSTONE_LAMP("Redstone Lamp", Material.REDSTONE_LAMP_OFF, "redstone_lamp", "redstonelamp", "redstone_lamp_off", "redstonelamp_off", "redstonelampoff", "123"),
	REDSTONE_LAMP_ON("Redstone Lamp (On)", Material.REDSTONE_LAMP_ON, "redstonelamp_on", "redstonelampon", "124"),
	DOUBLE_OAK_WOOD_SLAB("Double Oak Wood Slab", Material.WOOD_DOUBLE_STEP, "double_oak_wood_slab", "double_oakwood_slab", "double_oakwoodslab", "doubleoakwoodslab", "125"),
	DOUBLE_SPRUCE_WOOD_SLAB("Double Spruce Wood Slab", new ItemBuilder(Material.WOOD_DOUBLE_STEP).addDurability(1).toItemStack(), "double_spruce_wood_slab", "double_sprucewood_slab", "double_sprucewoodslab", "doublesprucewoodslab", "double_woodslab:1", "doublewoodslab:1", "125:1"),
	DOUBLE_BIRCH_WOOD_SLAB("Double Birch Wood Slab", new ItemBuilder(Material.WOOD_DOUBLE_STEP).addDurability(2).toItemStack(), "double_birch_wood_slab", "double_birchwood_slab", "double_birchwoodslab", "doublebirchwoodslab", "double_woodslab:2", "doublewoodslab:2", "125:2"),
	DOUBLE_JUNGLE_WOOD_SLAB("Double Jungle Wood Slab", new ItemBuilder(Material.WOOD_DOUBLE_STEP).addDurability(3).toItemStack(), "double_jungle_wood_slab", "double_junglewood_slab", "double_junglewoodslab", "doublejunglewoodslab", "double_woodslab:3", "doublewoodslab:3", "125:3"),
	DOUBLE_ACACIA_WOOD_SLAB("Double Acacia Wood Slab", new ItemBuilder(Material.WOOD_DOUBLE_STEP).addDurability(4).toItemStack(), "double_acacia_wood_slab", "double_acaciawood_slab", "double_acaciawoodslab", "doubleacaciawoodslab", "double_woodslab:4", "doublewoodslab:4", "125:4"),
	DOUBLE_DARK_OAK_WOOD_SLAB("Double Dark Oak Wood Slab", new ItemBuilder(Material.WOOD_DOUBLE_STEP).addDurability(5).toItemStack(), "double_dark_oak_wood_slab", "double_darkoak_wood_slab", "double_darkoakwood_slab", "double_darkoakwoodslab", "doubledarkoakwoodslab", "double_woodslab:5", "doublewoodslab:5", "125:5"),
	OAK_WOOD_SLAB("Oak Wood Slab", Material.WOOD_STEP, "oak_wood_slab", "oakwood_slab", "oakwoodslab", "wood_slab", "woodslab", "126"),
	SPRUCE_WOOD_SLAB("Spruce Wood Slab", new ItemBuilder(Material.WOOD_STEP).addDurability(1).toItemStack(), "spruce_wood_slab", "sprucewood_slab", "sprucewoodslab", "wood_slab:1", "woodslab:1", "126:1"),
	BIRCH_WOOD_SLAB("Birch Wood Slab", new ItemBuilder(Material.WOOD_STEP).addDurability(2).toItemStack(), "birch_wood_slab", "birchwood_slab", "birchwoodslab", "wood_slab:2", "woodslab:2", "126:2"),
	JUNGLE_WOOD_SLAB("Jungle Wood Slab", new ItemBuilder(Material.WOOD_STEP).addDurability(3).toItemStack(), "jungle_wood_slab", "junglewood_slab", "junglewoodslab", "wood_slab:3", "woodslab:3", "126:3"),
	ACACIA_WOOD_SLAB("Acacia Wood Slab", new ItemBuilder(Material.WOOD_STEP).addDurability(4).toItemStack(), "acacia_wood_slab", "acaciawood_slab", "acaciawoodslab", "wood_slab:4", "woodslab:4", "126:4"),
	DARK_OAK_WOOD_SLAB("Dark Oak Wood Slab", new ItemBuilder(Material.WOOD_STEP).addDurability(5).toItemStack(), "dark_oak_wood_slab", "darkoak_wood_slab", "darkoakwood_slab", "darkoakwoodslab", "wood_slab:5", "woodslab:5", "126:5"),
	COCOA_PLANT("Cocoa Plant", Material.COCOA, "cocoa", "cocoa_plant", "cocoaplant", "127"),
	SANDSTONE_STAIRS("Sandstone Stairs", Material.SANDSTONE_STAIRS, "sandstone_stairs", "sandstonestairs", "stairs_sandstone", "stairssandstone", "stairsandstone", "128"),
	EMERALD_ORE("Emerald Ore", Material.EMERALD_ORE, "emerald_ore", "emeraldore", "ore_emerald", "oreemerald", "oremerald", "129"),
	ENDER_CHEST("Ender Chest", Material.ENDER_CHEST, "ender_chest", "enderchest", "130"),
	TRIPWIRE_HOOK("Tripwire Hook", Material.TRIPWIRE_HOOK, "tripwire_hook", "tripwirehook", "131"),
	TRIPWIRE("Tripwire", Material.TRIPWIRE, "tripwire", "132"),
	EMERALD_BLOCK("Emerald Block", Material.EMERALD_BLOCK, "emerald_block", "emeraldblock", "block_of_emerald", "blockofemerald", "133"),
	SPRUCE_WOOD_STAIRS("Spruce Stairs", Material.SPRUCE_WOOD_STAIRS, "spruce_wood_stairs", "sprucewood_stairs", "sprucewoodstairs", "stairs_spruce_wood", "stairs_sprucewood", "stairssprucewood", "stairsprucewood", "stairs_spruce", "stairsspruce", "stairspruce", "134"),
	BIRCH_WOOD_STAIRS("Birch Stairs", Material.BIRCH_WOOD_STAIRS, "birch_wood_stairs", "birchwood_stairs", "birchwoodstairs", "stairs_birch_wood", "stairs_birchwood", "stairsbirchwood", "stairs_birch", "stairsbirch", "135"),
	JUNGLE_WOOD_STAIRS("Jungle Stairs", Material.JUNGLE_WOOD_STAIRS, "jungle_wood_stairs", "junglewood_stairs", "junglewoodstairs", "stairs_jungle_wood", "stairs_junglewood", "stairsjunglewood", "stairs_jungle", "stairsjungle", "136"),
	COMMAND_BLOCK("Command Block", Material.COMMAND, "command_block", "commandblock", "command", "137"),
	BEACON("Beacon", Material.BEACON, "beacon", "beacon_block", "beaconblock", "138"),
	COBBLESTONE_WALL("Cobblestone Wall", Material.COBBLE_WALL, "cobblestone_wall", "cobblestonewall", "cobble_wall", "cobblewall", "139"),
	MOSSY_COBBLESTONE_WALL("Mossy Cobblestone Wall", new ItemBuilder(Material.COBBLE_WALL).addDurability(1).toItemStack(), "mossy_cobblestone_wall", "mossycobblestone_wall", "mossycobblestonewall", "mossy_cobble_wall", "mossycobble_wall", "mossycobblewall", "cobble_wall:1", "cobblewall:1", "139:1"),
	FLOWER_POT("Flower Pot", Material.FLOWER_POT, "flower_pot", "flowerpot", "140"),
	CARROTS("Carrots", Material.CARROT, "carrots", "141"),
	POTATOES("Potatoes", Material.POTATO, "potatoes", "142"),
	WOOD_BUTTON("Wood Button", Material.WOOD_BUTTON, "wooden_button", "woodenbutton", "wood_button", "woodbutton", "143"),
	MOB_HEAD("Mob Head", Material.SKULL, "mob_head", "mobhead", "skull", "144"),
	ANVIL("Anvil", Material.ANVIL, "anvil", "145"),
	TRAPPED_CHEST("Trapped Chest", Material.TRAPPED_CHEST, "trapped_chest", "trappedchest", "146"),
	GOLD_PRESSURE_PLATE("Gold Pressure Plate", Material.GOLD_PLATE, "gold_pressure_plate", "gold_pressureplate", "goldpressureplate", "weighted_pressure_plate_light", "weighted_pressureplate_light", "weightedpressureplate_light", "weightedpressureplatelight", "light_pressure_plate", "light_pressureplate", "lightpressureplate", "light_plate", "lightplate", "gold_plate", "goldplate", "147"),
	IRON_PRESSURE_PLATE("Iron Pressure Plate", Material.IRON_PLATE, "iron_pressure_plate", "iron_pressureplate", "ironpressureplate", "weighted_pressure_plate_heavy", "weighted_pressureplate_heavy", "weightedpressureplate_heavy", "weightedpressureplateheavy", "heavy_pressure_plate", "heavy_pressureplate", "heavypressureplate", "heavy_plate", "heavyplate", "iron_plate", "ironplate", "148"),
	REDSTONE_COMPARATOR_OFF("Redstone Comparator", Material.REDSTONE_COMPARATOR_OFF, "redstone_comparator_off", "redstonecomparator_off", "redstonecomparatoroff", "149"),
	REDSTONE_COMPARATOR_ON("Redstone Comparator (On)", Material.REDSTONE_COMPARATOR_ON, "redstone_comparator_on", "redstonecomparator_on", "redstonecomparatoron", "150"),
	DAYLIGHT_SENSOR("Daylight Sensor", Material.DAYLIGHT_DETECTOR, "daylight_sensor", "daylightsensor", "daylight_detector", "daylightdetector", "151"),
	REDSTONE_BLOCK("Redstone Block", Material.REDSTONE_BLOCK, "redstone_block", "redstoneblock", "block_of_redstone", "blockofredstone", "152"),
	NETHER_QUARTZ_ORE("Nether Quartz Ore", Material.QUARTZ_ORE, "nether_quartz_ore", "netherquartz_ore", "netherquartzore", "nether_quarts_ore", "netherquarts_ore", "netherquartsore", "quartz_ore", "quartzore", "quarts_ore", "quartsore", "153"),
	HOPPER("Hopper", Material.HOPPER, "hopper", "154"),
	QUARTZ_BLOCK("Quartz Block", Material.QUARTZ_BLOCK, "nether_quartz_block", "netherquartz_block", "netherquartzblock", "nether_quarts_block", "netherquarts_block", "netherquartsblock", "quartz_block", "quarts_block", "quartzblock", "quartsblock", "block_of_quartz", "blockofquartz", "block_of_quarts", "blockofquarts", "block_of_nether_quarts", "block_of_netherquartz", "blockofnetherquartz", "block_of_nether_quarts", "block_of_netherquartz", "blockofnetherquartz", "155"),
	CHISELED_QUARTZ_BLOCK("Chiseled Quartz Block", new ItemBuilder(Material.QUARTZ_BLOCK).addDurability(1).toItemStack(), "chiseled_quartz", "chiseledquartz", "chiseled_quarts", "chiseledquarts", "nether_quartz_block:1", "netherquartzblock:1", "quartz:1", "quartz_block:1", "quartzblock:1", "155:1"),
	PILLAR_QUARTZ_BLOCK("Pillar Quartz Block", new ItemBuilder(Material.QUARTZ_BLOCK).addDurability(2).toItemStack(), "pillar_quartz", "pillarquartz", "pillar_quarts", "pillarquarts", "nether_quartz_block:2", "netherquartzblock:2", "quartz:2", "quartz_block:2", "quartzblock:2", "155:2"),
	QUARTZ_STAIRS("Quartz Stairs", Material.QUARTZ_STAIRS, "quartz_stairs", "quartzstairs", "quarts_stairs", "quartsstairs", "quartstairs", "stairs_quartz", "stairsquartz", "156"),
	ACTIVATOR_RAIL("Activator Rail", Material.ACTIVATOR_RAIL, "activator_rail", "activatorrail", "activatorail", "activator_track", "activatortrack", "157"),
	DROPPER("Dropper", Material.DROPPER, "dropper", "158"),
	STAINED_CLAY("White Clay", Material.STAINED_CLAY, "stained_clay", "stainedclay", "white_stained_clay", "white_stainedclay", "whitestainedclay", "stained_clay_white", "stainedclay_white", "stainedclaywhite", "159"),
	ORANGE_STAINED_CLAY("Orange Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(1).toItemStack(), "orange_stained_clay", "orange_stainedclay", "orangestainedclay", "stained_clay_orange", "stainedclay_orange", "stainedclayorange", "stained_clay:1", "stainedclay:1", "159:1"),
	MAGENTA_STAINED_CLAY("Magenta Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(2).toItemStack(), "magenta_stained_clay", "magenta_stainedclay", "magentastainedclay", "stained_clay_magenta", "stainedclay_magenta", "stainedclaymagenta", "stained_clay:2", "stainedclay:2", "159:2"),
	LIGHT_BLUE_STAINED_CLAY("Light Blue Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(3).toItemStack(), "light_blue_stained_clay", "lightblue_stained_clay", "lightblue_stainedclay", "lightbluestainedclay", "light_blue_stainedclay", "stained_clay_light_blue", "stained_clay_lightblue", "stainedclay_lightblue", "stainedclaylightblue", "stained_clay:3", "stainedclay:3", "159:3"),
	YELLOW_STAINED_CLAY("Yellow Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(4).toItemStack(), "yellow_stained_clay", "yellow_stainedclay", "yellowstainedclay", "stained_clay_yellow", "stainedclay_yellow", "stainedclayyellow", "stainedclayellow", "stained_clay:4", "stainedclay:4", "159:4"),
	LIME_STAINED_CLAY("Lime Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(5).toItemStack(), "lime_stained_clay", "lime_stainedclay", "limestainedclay", "stained_clay_lime", "stainedclay_lime", "stainedclaylime", "stained_clay:5", "stainedclay:5", "159:5"),
	PINK_STAINED_CLAY("Pink Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(6).toItemStack(), "pink_stained_clay", "pink_stainedclay", "pinkstainedclay", "stained_clay_pink", "stainedclay_pink", "stainedclaypink", "stained_clay:6", "stainedclay:6", "159:6"),
	GRAY_STAINED_CLAY("Gray Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(7).toItemStack(), "gray_stained_clay", "gray_stainedclay", "graystainedclay", "stained_clay_gray", "stainedclay_gray", "stainedclaygray", "stained_clay:7", "stainedclay:7", "159:7"),
	LIGHT_GRAY_STAINED_CLAY("Light Gray Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(8).toItemStack(), "light_gray_stained_clay", "lightgray_stained_clay", "lightgray_stainedclay", "lightgraystainedclay", "light_gray_stainedclay", "stained_clay_light_gray", "stainedclay_light_gray", "stainedclay_lightgray", "stainedclaylightgray", "stained_clay_lightgray", "stained_clay:8", "stainedclay:8", "159:8"),
	CYAN_STAINED_CLAY("Cyan Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(9).toItemStack(), "cyan_stained_clay", "cyan_stainedclay", "cyanstainedclay", "stained_clay_cyan", "stainedclay_cyan", "stainedclaycyan", "stained_clay:9", "stainedclay:9", "159:9"),
	PURPLE_STAINED_CLAY("Purple Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(10).toItemStack(), "purple_stained_clay", "purple_stainedclay", "purplestainedclay", "stained_clay_purple", "stainedclay_purple", "stainedclaypurple", "stained_clay:10", "stainedclay:10", "159:10"),
	BLUE_STAINED_CLAY("Blue Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(11).toItemStack(), "blue_stained_clay", "blue_stainedclay", "bluestainedclay", "stained_clay_blue", "stainedclay_blue", "stainedclayblue", "stained_clay:11", "stainedclay:11", "159:11"),
	BROWN_STAINED_CLAY("Brown Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(12).toItemStack(), "brown_stained_clay", "brown_stainedclay", "brownstainedclay", "stained_clay_brown", "stainedclay_brown", "stainedclaybrown", "stained_clay:12", "stainedclay:12", "159:12"),
	GREEN_STAINED_CLAY("Green Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(13).toItemStack(), "green_stained_clay", "green_stainedclay", "greenstainedclay", "stained_clay_green", "stainedclay_green", "stainedclaygreen", "stained_clay:13", "stainedclay:13", "159:13"),
	RED_STAINED_CLAY("Red Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(14).toItemStack(), "red_stained_clay", "red_stainedclay", "redstainedclay", "stained_clay_red", "stainedclay_red", "stainedclayred", "stained_clay:14", "stainedclay:14", "159:14"),
	BLACK_STAINED_CLAY("Black Clay", new ItemBuilder(Material.STAINED_CLAY).addDurability(15).toItemStack(), "black_stained_clay", "black_stainedclay", "blackstainedclay", "stained_clay_black", "stainedclay_black", "stainedclayblack", "stained_clay:15", "stainedclay:15", "159:15"),
	STAINED_PANE("White Glass Pane", Material.STAINED_GLASS_PANE, "stained_glass_pane", "stained_glasspane", "stainedglasspane", "white_stained_glass_pane", "white_stained_glasspane", "white_stainedglasspane", "whitestainedglasspane", "stained_glass_pane_white", "stained_glasspane_white", "stainedglasspanewhite", "160"),
	ORANGE_STAINED_PANE("Orange Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(1).toItemStack(), "orange_stained_glass_pane", "orange_stained_glasspane", "orange_stainedglasspane", "orangestainedglasspane", "stained_glass_pane_orange", "stained_glasspane_orange", "stainedglasspane_orange", "stainedglasspaneorange", "stained_glass_pane:1", "stainedglasspane:1", "160:1"),
	MAGENTA_STAINED_PANE("Magenta Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(2).toItemStack(), "magenta_stained_glass_pane", "magenta_stained_glasspane", "magenta_stainedglasspane", "magentastainedglasspane", "stained_glass_pane_magenta", "stained_glasspane_magenta", "stainedglasspane_magenta", "stainedglasspanemagenta", "stained_glass_pane:2", "stainedglasspane:2", "160:2"),
	LIGHT_BLUE_STAINED_PANE("Light Blue Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(3).toItemStack(), "light_blue_stained_glass_pane", "light_blue_stainedglasspane", "lightblue_stainedglasspane", "lightbluestainedglasspane", "stained_glass_pane_light_blue", "stainedglasspane_light_blue", "stainedglasspane_lightblue", "stainedglasspanelightblue", "stained_glass_pane:3", "stainedglasspane:3", "160:3"),
	YELLOW_STAINED_PANE("Yellow Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(4).toItemStack(), "yellow_stained_glass_pane", "yellow_stainedglasspane", "yellowstainedglasspane", "stained_glass_pane_yellow", "stained_glasspane_yellow", "stainedglasspane_yellow", "stainedglasspaneyellow", "stained_glass_pane:4", "stainedglasspane:4", "160:4"),
	LIME_STAINED_PANE("Lime Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(5).toItemStack(), "lime_stained_glass_pane", "lime_stainedglasspane", "limestainedglasspane", "stained_glass_pane_lime", "stained_glasspane_lime", "stainedglasspane_lime", "stainedglasspanelime", "stained_glass_pane:5", "stainedglasspane:5", "160:5"),
	PINK_STAINED_PANE("Pink Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(6).toItemStack(), "pink_stained_glass_pane", "pink_stainedglasspane", "pinkstainedglasspane", "stained_glass_pane_pink", "stained_glasspane_pink", "stainedglasspane_pink", "stainedglasspanepink", "stained_glass_pane:6", "stainedglasspane:6", "160:6"),
	GRAY_STAINED_PANE("Gray Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(7).toItemStack(), "gray_stained_glass_pane", "gray_stainedglasspane", "graystainedglasspane", "stained_glass_pane_gray", "stained_glasspane_gray", "stainedglasspane_gray", "stainedglasspanegray", "stained_glass_pane:7", "stainedglasspane:7", "160:7"),
	LIGHT_GRAY_STAINED_PANE("Light Gray Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(8).toItemStack(), "light_gray_stained_glass_pane", "light_gray_stainedglasspane", "lightgray_stainedglasspane", "lightgraystainedglasspane", "stained_glass_pane_light_gray", "stained_glasspane_light_gray", "stained_glasspane_lightgray", "stainedglasspane_light_gray", "stainedglasspane_lightgray", "stainedglasspanelightgray", "stained_glass_pane:8", "stainedglasspane:8", "160:8"),
	CYAN_STAINED_PANE("Cyan Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(9).toItemStack(), "cyan_stained_glass_pane", "cyan_stainedglasspane", "cyanstainedglasspane", "stained_glass_pane_cyan", "stained_glasspane_cyan", "stainedglasspane_cyan", "stainedglasspanecyan", "stained_glass_pane:9", "stainedglasspane:9", "160:9"),
	PURPLE_STAINED_PANE("Purple Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(10).toItemStack(), "purple_stained_glass_pane", "purple_stained_glasspane", "purple_stainedglasspane", "purplestainedglasspane", "stained_glass_pane_purple", "stained_glasspane_purple", "stainedglasspane_purple", "stainedglasspanepurple", "stained_glass_pane:10", "stainedglasspane:10", "160:10"),
	BLUE_STAINED_PANE("Blue Glass Pane",  new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(11).toItemStack(), "blue_stained_glass_pane", "blue_stained_glasspane", "blue_stainedglasspane", "bluestainedglasspane", "stained_glass_pane_blue", "stained_glasspane_blue", "stainedglasspane_blue", "stainedglasspaneblue", "stained_glass_pane:11", "stainedglasspane:11", "160:11"),
	BROWN_STAINED_PANE("Brown Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(12).toItemStack(), "brown_stained_glass_pane", "brown_stained_glasspane", "brown_stainedglasspane", "brownstainedglasspane", "stained_glass_pane_brown", "stained_glasspane_brown", "stainedglasspane_brown", "stainedglasspanebrown", "stained_glass_pane:12", "stainedglasspane:12", "160:12"),
	GREEN_STAINED_PANE("Green Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(13).toItemStack(), "green_stained_glass_pane", "green_stained_glasspane", "green_stainedglasspane", "greenstainedglasspane", "stained_glass_pane_green", "stained_glasspane_green", "stainedglasspane_green", "stainedglasspanegreen", "stained_glass_pane:13", "stainedglasspane:13", "160:13"),
	RED_STAINED_PANE("Red Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(14).toItemStack(), "red_stained_glass_pane", "red_stained_glasspane", "red_stainedglasspane", "redstainedglasspane", "stained_glass_pane_red", "stained_glasspane_red", "stainedglasspane_red", "stainedglasspanered", "stained_glass_pane:14", "stainedglasspane:14", "160:14"),
	BLACK_STAINED_PANE("Black Glass Pane", new ItemBuilder(Material.STAINED_GLASS_PANE).addDurability(15).toItemStack(), "black_stained_glass_pane", "black_stained_glasspane", "black_stainedglasspane", "blackstainedglasspane", "stained_glass_pane_black", "stained_glasspane_black", "stainedglasspane_black", "stainedglasspaneblack", "stained_glass_pane:15", "stainedglasspane:15", "160:15"),
	ACACIA_LEAVES("Acacia Leaves", Material.LEAVES_2, "acacia_leaves", "acacialeaves", "acacia_leaf", "acacialeaf", "leaves_2", "leaf_2", "leaves2", "leaf2", "161"),
	DARK_OAK_LEAVES("Dark Oak Leaves", new ItemBuilder(Material.LEAVES_2).addDurability(1).toItemStack(), "dark_oak_leaves", "darkoak_leaves", "darkoakleaves", "dark_oak_leaf", "darkoak_leaf", "darkoakleaf", "acacia_leaves:1", "acacialeaves:1", "acacia_leaf:1", "acacialeaf:1", "leaves_2:1", "leaves2:1", "leaf_2:1", "leaf2:1", "161:1"),
	ACACIA_LOG("Acacia Log", Material.LOG_2, "acacia_log", "acacialog", "log_acacia", "logacacia", "162"),
	DARK_OAK_LOG("Dark Oak Log", new ItemBuilder(Material.LOG_2).addDurability(1).toItemStack(), "dark_oak_log", "darkoak_log", "darkoaklog", "log_dark_oak", "log_darkoak", "logdarkoak", "acacia_log:1", "acacialog:1", "log_acacia:1", "logacacia:1", "162:1"),
	ACACIA_STAIRS("Acacia Stairs", Material.ACACIA_STAIRS, "acacia_stairs", "acaciastairs", "stairs_acacia", "stairsacacia", "163"),
	DARK_OAK_STAIRS("Dark Oak Stairs", Material.DARK_OAK_STAIRS, "dark_oak_stairs", "darkoak_stairs", "darkoakstairs", "stairs_dark_oak", "stairs_darkoak", "stairsdarkoak", "164"),
//	SLIME_BLOCK("Slime Block", Material.SLIME_BLOCK)
//	BARRIER("Barrier", Material.BARRIER)
//	IRON_TRAPDOOR("Iron Trapdoor", Material.IRON_TRAPDOOR)
	HAY_BALE("Hay Bale", Material.HAY_BLOCK, "hay_bale", "haybale", "hay_block", "hayblock", "block_of_hay", "blockofhay", "bale_of_hay", "baleofhay", "170"),
	WHITE_CARPET("White Carpet", Material.CARPET, "carpet", "white_carpet", "whitecarpet", "carpet_white", "carpetwhite", "171");
	
	
	public static HashMap<String, Item> itemMap = new HashMap<String, Item>();
	
	public static char DATA_SPLITTER = ':';
	public static char WHITESPACE_REPLACER = '_';
	
	private String minecraft_name;
	private String name;
	private ItemStack item;
	private List<String> aliases = new ArrayList<String>();
	private MetaBuilder meta;
	
	Item(String minecraft_name, String name, ItemStack item, String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = name;
		this.item = item;
		this.aliases = Arrays.asList(aliases);
	}
	
	Item(String minecraft_name, String name, Material item, String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = name;
		this.item = new ItemStack(item);
		this.aliases = Arrays.asList(aliases);
	}
	
	Item(String minecraft_name, String name, ItemStack item, MetaBuilder meta, String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = name;
		this.item = item;
		this.aliases = Arrays.asList(aliases);
		this.meta = meta;
	}
	
	Item(String minecraft_name, String name, Material item, MetaBuilder meta,  String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = name;
		this.item = new ItemStack(item);
		this.aliases = Arrays.asList(aliases);
		this.meta = meta;
	}
	
	Item(String minecraft_name, ItemStack item, String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = minecraft_name;
		this.item = item;
		this.aliases = Arrays.asList(aliases);
	}
	
	Item(String minecraft_name, Material item, String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = minecraft_name;
		this.item = new ItemStack(item);
		this.aliases = Arrays.asList(aliases);
	}
	
	Item(String minecraft_name, ItemStack item, MetaBuilder meta, String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = minecraft_name;
		this.item = item;
		this.aliases = Arrays.asList(aliases);
		this.meta = meta;
	}
	
	Item(String minecraft_name, Material item, MetaBuilder meta,  String...aliases) {
		this.minecraft_name = minecraft_name;
		this.name = minecraft_name;
		this.item = new ItemStack(item);
		this.aliases = Arrays.asList(aliases);
		this.meta = meta;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getMinecraftName() {
		return this.minecraft_name;
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	
	public List<String> getAliases() {
		return this.aliases;
	}
	
	public MetaBuilder getMeta() {
		return this.meta;
	}
	
	public static void fillMap() {
		for(Item item : values()) {
			for(String alias : item.getAliases()) {
				String formattedAlias = alias.toLowerCase();
				if(DATA_SPLITTER != ':') {
					if(formattedAlias.contains(":")) formattedAlias = formattedAlias.replaceAll(":", DATA_SPLITTER + "");
				}
				if(WHITESPACE_REPLACER != '_') {
					if(formattedAlias.contains("_")) formattedAlias = formattedAlias.replaceAll("_", WHITESPACE_REPLACER + "");
				}
				if(itemMap.containsKey(alias)) Main.logger.log(Level.WARNING, "Sector > 'item' duplicate of '" + formattedAlias + "' where " + item.getItem().getType());
				itemMap.put(formattedAlias, item);
			}
		}
		Main.logger.log(Level.WARNING, "Sector > 'item' size " + itemMap.size());
	}
	
	public static Item getItem(String alias) {
		if(!itemMap.containsKey(alias.toLowerCase())) return Item.NULL;
		return itemMap.get(alias.toLowerCase());
	}
	
	public static Item getItem(ItemStack item) {
		for(Item items : values()) {
			if(items.getItem().isSimilar(item)) return items;
		}
		return Item.NULL;
	}
}