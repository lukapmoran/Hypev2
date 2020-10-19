package me.loogeh.Hype.Inventory;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Inventory - " + ChatColor.WHITE + "Console cannot use inventory commands");
			return true;
		}
//		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("inventory") || commandLabel.equalsIgnoreCase("inv")) {
			if(args.length == 0) {
				
			}
			if(args.length == 1) {
				
			}
			if(args.length == 2) {
				
			}
			if(args.length == 3) {
				
			}
		}
		return false;
	}
	

}
