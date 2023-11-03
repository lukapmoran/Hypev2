package me.loogeh.Hype.Sector;

import me.loogeh.Hype.Formatting.M;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Settings extends Sector {

	private static Settings instance;
	
	public Settings() {
		super("Settings");
	}
	
	public static Settings get() {
		if(instance == null) instance = new Settings();
		return instance;
		
	}

	public void load() {
		
	}

	public void help(Player player) {
		message(player, ChatColor.WHITE + "Commands " + ChatColor.GRAY + "(optional) <required> [command options]");
		M.sendHelpMessage(player, "/settings set <setting> <value>", "Set the value of a setting");
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Settings - " + ChatColor.WHITE + "Console cannot use settings commands");
			return true;
		}
		Player player = (Player) sender;
		Member member = Member.get(player);
		if(commandLabel.equalsIgnoreCase("settings")) {
			if(member == null) {
				message(player, ChatColor.WHITE + "Faield to find your player data");
				return true;
			}
			if(args.length == 0) {
				
			}
		}
		return false;
	}

}
