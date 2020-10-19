package me.loogeh.Hype.Sector;

import java.util.HashSet;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Item.Item;
import me.loogeh.Hype.Member.mPerms.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Debug extends Sector implements CommandExecutor {

	private static Debug instance;
	
	private static HashSet<String> debugSet = new HashSet<String>();
	
	public Debug() {
		super("Debug");
		getPlugin().getCommand("debug").setExecutor(this);
		register();
		instance = this;
	}
	
	public static Debug get() {
		if(instance == null) instance = new Debug();
		return instance;
	}

	public void load() {
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack itemstack = new ItemStack(block.getType(), 1, block.getData());
		if(debugSet.contains(player.getUniqueId().toString())) {
			if(player.getItemInHand() == null) return;
			if(player.getItemInHand().getType().equals(Material.BONE)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.DARK_GRAY + "Block Data");
				player.sendMessage(ChatColor.DARK_GRAY + "Bukkit Name > " + ChatColor.GREEN + block.getType().name());
				player.sendMessage(ChatColor.DARK_GRAY + "Hype Name > " + ChatColor.GREEN + Item.getItem(itemstack).getName());
				player.sendMessage(ChatColor.DARK_GRAY + "Data > " + ChatColor.GREEN + block.getData());
				player.sendMessage(ChatColor.DARK_GRAY + "Power > " + ChatColor.GREEN + block.getBlockPower());
				player.sendMessage(ChatColor.DARK_GRAY + "Temp > " + ChatColor.GREEN + block.getTemperature());
				player.sendMessage(ChatColor.DARK_GRAY + "State > " + ChatColor.GREEN + block.getState());
				player.sendMessage(ChatColor.DARK_GRAY + "Light > " + ChatColor.GREEN + block.getLightLevel());
				player.sendMessage(ChatColor.DARK_GRAY + "Light Dispersion > (Sky " + ChatColor.GREEN + block.getLightFromSky() + ChatColor.DARK_GRAY + ", Block " + ChatColor.GREEN + block.getLightFromBlocks() + ChatColor.DARK_GRAY + ")");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		if(!debugSet.contains(player.getUniqueId().toString())) return;
		Block block = event.getClickedBlock();
		if(player.getItemInHand() == null) return;
		ItemStack itemstack = new ItemStack(block.getType(), 1, block.getData());
		if(player.getItemInHand().getType().equals(Material.BONE)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_GRAY + "Block Data");
			player.sendMessage(ChatColor.DARK_GRAY + "Bukkit Name > " + ChatColor.GREEN + block.getType().name());
			player.sendMessage(ChatColor.DARK_GRAY + "Hype Name > " + ChatColor.GREEN + Item.getItem(itemstack).getName());
			player.sendMessage(ChatColor.DARK_GRAY + "Data > " + ChatColor.GREEN + block.getData());
			player.sendMessage(ChatColor.DARK_GRAY + "Power > " + ChatColor.GREEN + block.getBlockPower());
			player.sendMessage(ChatColor.DARK_GRAY + "Temp > " + ChatColor.GREEN + block.getTemperature());
			player.sendMessage(ChatColor.DARK_GRAY + "State > " + ChatColor.GREEN + block.getState());
			player.sendMessage(ChatColor.DARK_GRAY + "Light > " + ChatColor.GREEN + block.getLightLevel());
			player.sendMessage(ChatColor.DARK_GRAY + "Light Dispersion > (Sky " + ChatColor.GREEN + block.getLightFromSky() + ChatColor.DARK_GRAY + ", Block " + ChatColor.GREEN + block.getLightFromBlocks() + ChatColor.DARK_GRAY + ")");
		}
	}

	public void help(Player player) {
		M.sendHelpMessage(player, "/debug toggle", "Toggles debug mode");
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Debug - " + ChatColor.WHITE + "Console cannot use debug commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(member == null) {
			message(player, "Failed to find your player data");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("debug")) {
			if(!member.getPermissions().is(Rank.ADMIN)) {
				M.sendLackPermsMessage(player);
				return true;
			}
			if(args.length == 0) {
				help(player);
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("help")) {
					help(player);
					return true;
				}
				if(args[0].equalsIgnoreCase("toggle")) {
					boolean toggled = debugSet.contains(player.getUniqueId().toString()) ? true : false;
					if(toggled) {
						debugSet.remove(player.getUniqueId().toString());
						message(player, "You disabled " + ChatColor.YELLOW + "debug mode");
						return true;
					}
					debugSet.add(player.getUniqueId().toString());
					message(player, "You enabled " + ChatColor.YELLOW + "debug mode");
					return true;
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("vec")) {
					if(args[1].equalsIgnoreCase("add")) {
						Location location = new Location(Bukkit.getWorld("game_world"), 707.5D, 39.2D, -284.5D);
						Location location2 = new Location(Bukkit.getWorld("game_world"), 696.5D, 39.2D, -312.5D);
						Vector vector = location.toVector().subtract(location2.toVector());
						player.setVelocity(vector);
						player.sendMessage(vector.getX() + "x, " + vector.getY() + ", " + vector.getZ() + "z");
					}
					if(args[1].equalsIgnoreCase("sub")) {
						Location location = new Location(Bukkit.getWorld("game_world"), 707.5D, 39.2D, -284.5D);
						Location location2 = new Location(Bukkit.getWorld("game_world"), 696.5D, 39.2D, -312.5D);
						Vector vector = location.toVector().add(location2.toVector());
						player.setVelocity(vector);
						player.sendMessage(vector.getX() + "x, " + vector.getY() + ", " + vector.getZ() + "z");
					}
				}
			}
		}
		return false;
	}

}
