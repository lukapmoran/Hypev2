package me.loogeh.Hype.Squads;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Squads.Squad.ClaimType;
import me.loogeh.Hype.Squads.Squad.Source;
import me.loogeh.Hype.Utility.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SquadsCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {

		}
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Squad squad = SquadManager.getSquad(player);
			if(commandLabel.equalsIgnoreCase("s") || commandLabel.equalsIgnoreCase("squad") || commandLabel.equalsIgnoreCase("squads")) {
				if(args.length == 0) {
					if(squad == null) {
						M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
						return true;
					}
					SquadManager.getSquad(player).showDetails(player);
				}
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("claim")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						if(squad.getRank(player.getName()).getID() < 2) {
							M.message(player, "Squads", ChatColor.WHITE + "You must be alteast an " + ChatColor.YELLOW + "Admin " + ChatColor.WHITE + "to claim");
							return true;
						}
						Chunk chunk = player.getLocation().getChunk();
						if(!Squad.getCanClaimNextTo()) {
							Chunk plusX = chunk.getWorld().getChunkAt(chunk.getX() + 1, chunk.getZ());
							Chunk subX = chunk.getWorld().getChunkAt(chunk.getX() - 1, chunk.getZ());
							Chunk plusZ = chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() + 1);
							Chunk subZ = chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() - 1);
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
								return true;
							}
						}
						if(SquadManager.isClaimed(chunk)) {
							Squad owner = SquadManager.getOwner(chunk);

							if(owner.getPower() < 1) {
								squad.stealClaim(player, chunk, ClaimType.DEFAULT);
								return true;
							}
							if(owner.getLand() > owner.getMaxLand()) {
								squad.stealClaim(player, chunk, ClaimType.DEFAULT);
								return true;
							}
							M.message(player, "Squads", ChatColor.WHITE + "This land is already owned by " + ChatColor.YELLOW + SquadManager.getOwner(chunk).getName());
							return true;
						}
						if(squad.getLand() >= squad.getMaxLand()) {
							M.message(player, "Squads", ChatColor.WHITE + "You have insufficient available land");
							return true;
						}
						squad.claim(chunk, ClaimType.DEFAULT);
						M.message(player, "Squads", ChatColor.WHITE + "You claimed a chunk");
						return true;
					}
					if(args[0].equalsIgnoreCase("unclaim") || args[0].equalsIgnoreCase("uc")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.unclaim(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("unclaim-all") || args[0].equalsIgnoreCase("uca")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.unclaimAll(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("home")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.sendHome(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("leave")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.leave(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("disband")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.disband(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("about")) {
						Squad.sendAbout(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("help")) {
						Squad.sendHelpMenu(player, 1);
						return true;
					}
					if(args[0].equalsIgnoreCase("chat") || args[0].equalsIgnoreCase("c")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						if(!squad.squadChat.containsKey(player.getName())) {
							squad.squadChat.put(player.getName(), true);
							M.message(player, "Squads", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "> Squad");
							return true;
						}
						if(squad.squadChat.containsKey(player.getName())) {
							boolean chatting = squad.squadChat.get(player.getName());
							if(chatting) {
								squad.squadChat.put(player.getName(), false);
								M.message(player, "Squads", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "> Server");
								return true;
							}
							squad.squadChat.put(player.getName(), true);
							M.message(player, "Squads", ChatColor.YELLOW + "Chat " + ChatColor.WHITE + "> Squad");
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("view")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.showDetails(player);
						return true;
					}
					if(util.arrayContains(args[0], Squad.two_arg)) {
						player.sendMessage(Squad.getUsage(args[0]));
						return true;
					}
					if(!util.arrayContains(args[0], Squad.commands)) {
						Squad searchSquad = SquadManager.getSquad(args[0]);
						if(searchSquad != null) {
							searchSquad.showDetails(player);
							return true;
						}
						Player target = Bukkit.getPlayer(args[0]);
						if(target != null) {
							Squad searchPlayer = SquadManager.getSquad(args[0]);
							if(searchPlayer != null) {
								searchPlayer.showDetails(player);
								return true;
							}
						} else {
							Squad searchPlayer = SquadManager.getSquadFromPlayer(args[0]);
							if(searchPlayer != null) {
								searchPlayer.showDetails(player);
								return true;
							}
						}
						if(searchSquad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
							return true;
						}
					}
				}
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("search")) {
						Squad searchSquad = SquadManager.getSquad(args[0]);
						if(searchSquad != null) {
							searchSquad.showDetails(player);
							return true;
						}
						Player target = Bukkit.getPlayer(args[0]);
						if(target != null) {
							Squad searchPlayer = SquadManager.getSquad(args[0]);
							if(searchPlayer != null) {
								searchPlayer.showDetails(player);
								return true;
							}
						} else {
							Squad searchPlayer = SquadManager.getSquadFromPlayer(args[0]);
							if(searchPlayer != null) {
								searchPlayer.showDetails(player);
								return true;
							}
						}
						if(searchSquad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[0]);
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("create")) {
						String name = args[1];
						if(SquadManager.hasSquad(player.getName())) {
							M.message(player, "Squads", ChatColor.WHITE + "You are already in a Squad");
							return true;
						}
						ResultSet rs = Main.mysql.doQuery("SELECT squad FROM `squad` WHERE squad='" + name.toLowerCase() + "'");
						try {
							if(rs.next()) {
								M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " already exists");
								return true;
							}
						} catch (SQLException e) {
							M.message(player, "Squads", ChatColor.WHITE + "Failed to query database - Try again");
						}
						if(name.length() > Main.config.getInt("squads.max_name_length")) {
							M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " exceeds the maximum name length");
							return true;
						}
						if(name.length() < Main.config.getInt("squads.min_name_length")) {
							M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " has too few characters");
							return true;
						}
						if(util.containsSpecialChar(name) && Main.config.getBoolean("special_chars_allowed") == false) {
							M.message(player, "Squads", ChatColor.YELLOW + "Special characters " + ChatColor.WHITE + "are not allowed");
							return true;
						}
						if(util.arrayContains(name.toLowerCase(), Squad.commands) || Squad.getDisallowedNames().contains(name.toLowerCase())) {
							M.message(player, "Squads", ChatColor.WHITE + "The name you entered isn't allowed");
							return true;
						}
						boolean success = Squad.create(name, player.getName(), false);
						if(!success) return true;
						M.broadcast("Squads", ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " formed " + ChatColor.YELLOW + name);
						return true;
					}
					if(args[0].equalsIgnoreCase("createadmin") || args[0].equalsIgnoreCase("createa")) {
						if(!player.hasPermission("squads.create_admin")) {
							M.message(player, "Squads", ChatColor.WHITE + "You lack permission to do this");
							return true;
						}
						String name = args[1];
						if(SquadManager.hasSquad(player.getName())) {
							M.message(player, "Squads", ChatColor.WHITE + "You are already in a Squad");
							return true;
						}
						if(SquadManager.squads.containsKey(name)) {
							M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " already exists");
							return true;
						}
						if(name.length() > Main.config.getInt("squads.max_name_length")) {
							M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " exceeds the maximum name length");
							return true;
						}
						if(name.length() < Main.config.getInt("squads.min_name_length")) {
							M.message(player, "Squads", ChatColor.YELLOW + name + ChatColor.WHITE + " has too few characters");
							return true;
						}
						if(util.containsSpecialChar(name) && Main.config.getBoolean("special_chars_allowed") == false) {
							M.message(player, "Squads", ChatColor.YELLOW + "Special characters " + ChatColor.WHITE + "are not allowed");
							return true;
						}
						if(util.arrayContains(name.toLowerCase(), Squad.commands)) {
							M.message(player, "Squads", ChatColor.WHITE + "The name you entered isn't allowed");
							return true;
						}
						Squad.create(name, player.getName(), true);
						M.message(player, "Squads", ChatColor.WHITE + "You created the admin squad " + ChatColor.YELLOW + name);
						return true;
					}
					if(args[0].equalsIgnoreCase("ally")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.requestAlliance(player, target.getName());
						return true;
					}
					if(args[0].equalsIgnoreCase("enemy")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.addEnemy(player, target.getName());
						return true;
					}
					if(args[0].equalsIgnoreCase("neutral")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.setNeutral(player, target.getName());
						return true;
					}
					if(args[0].equalsIgnoreCase("help")) {
						int page = 1;
						try {
							page = Integer.parseInt(args[1]);
						} catch(NumberFormatException e) {
							M.message(player, "Squads", ChatColor.WHITE + "You must enter a numberical page number");
							return true;
						}
						Squad.sendHelpMenu(player, page);
						return true;
					}
					if(args[0].equalsIgnoreCase("set")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						if(args[1].equalsIgnoreCase("home")) {
							squad.setHome(player);
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("i")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.invite(player, args[1]);
						return true;
					}
					if(args[0].equalsIgnoreCase("uninvite") || args[0].equalsIgnoreCase("deinvite")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.uninvite(player, args[1]);
						return true;
					}
					if(args[0].equalsIgnoreCase("show") || args[0].equalsIgnoreCase("get")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						if(args[1].equalsIgnoreCase("invites")) {
							squad.showInvitations(player);
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("promote")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						String searchPlayer = SquadManager.searchPlayer(squad, args[1]);
						if(searchPlayer == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.promote(player, searchPlayer);
						return true;
					}
					if(args[0].equalsIgnoreCase("demote")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						String searchPlayer = SquadManager.searchPlayer(squad, args[1]);
						if(searchPlayer == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.demote(player, searchPlayer);
						return true;

					}
					if(args[0].equalsIgnoreCase("leader")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.passLeadership(player, args[1]);
						return true;
					}
					if(args[0].equalsIgnoreCase("kick")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						squad.kick(player, args[1]);
						return true;
					}
					if(args[0].equalsIgnoreCase("trust")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						Squad searchedSquad = SquadManager.getSquad(args[1]);
						if(searchedSquad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.giveTrust(player, searchedSquad.getName());
						return true;
					}
					if(args[0].equalsIgnoreCase("untrust") || args[0].equalsIgnoreCase("revoketrust")) {
						if(squad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
							return true;
						}
						Squad searchedSquad = SquadManager.getSquad(args[1]);
						if(searchedSquad == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						squad.revokeTrust(player, searchedSquad.getName());
						return true;
					}
					if(args[0].equalsIgnoreCase("join")) {
						Squad search = SquadManager.getSquad(args[1]);
						if(search != null) {
							search.join(player);
							return true;
						}
						Squad searchPlayer = SquadManager.searchPlayer(args[1]);
						if(searchPlayer != null) {
							searchPlayer.join(player);
							return true;
						}
						if(search == null && searchPlayer == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
						}
						return true;
					}
					if(args[0].equalsIgnoreCase("claim")) {
						if(args[1].equalsIgnoreCase("list")) {
							if(squad == null) {
								M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
								return true;
							}
							squad.sendClaims(player);
							return true;
						}
						if(args[1].equalsIgnoreCase("selected")) {
							if(!player.hasPermission("squads.admin.claim_selection")) {
								M.sendLackPermsMessage(player);
								return true;
							}
							WandSession session = WandSession.sessions.get(player.getName());
							if(session == null) {
								M.message(player, "Squads", ChatColor.WHITE + "You must first select two blocks");
								return true;
							}
							if(session.getSelection1() == null) {
								M.message(player, "Squads", ChatColor.YELLOW + "Left click " + ChatColor.WHITE + "a block to set selection 1");
								return true;
							}
							if(session.getSelection2() == null) {
								M.message(player, "Squads", ChatColor.YELLOW + "Right click " + ChatColor.WHITE + "a block to set selection 2");
								return true;
							}
							squad.claim(player, session.getSelectionChunk1(), session.getSelectionChunk2(), ClaimType.DEFAULT);
							if(WandSession.sessions.containsKey(player.getName())) WandSession.sessions.remove(player.getName());
							return true;
						} else {
							ClaimType type = ClaimType.getType(args[1]);
							if(squad == null) {
								M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
								return true;
							}
							if(!squad.admin) {
								M.message(player, "Squads", ChatColor.WHITE + "Only " + ChatColor.YELLOW + "Admin Squads " + ChatColor.WHITE + "can claim " + type.getFormattedName() + " chunks");
								return true;
							}
							if(squad.getRank(player.getName()).getID() < 2) {
								M.message(player, "Squads", ChatColor.WHITE + "You must be atleast an " + ChatColor.YELLOW + "admin " + ChatColor.WHITE + "to claim");
								return true;
							}
							if(type == null) {
								M.message(player, "Squads", ChatColor.WHITE + "Please enter a valid claim type");
								return true;
							}
							if(squad.getLand() >= squad.getMaxLand()) {
								M.message(player, "Squads", ChatColor.WHITE + "You have insufficient available land");
								return true;
							}
							Chunk chunk = player.getLocation().getChunk();
							if(SquadManager.isClaimed(chunk)) {
								Squad owner = SquadManager.getOwner(chunk);
								if(owner.getPower() < 1) {
									squad.stealClaim(player, chunk, ClaimType.DEFAULT);
									return true;
								}
								if(owner.getLand() > owner.getMaxLand()) {
									squad.stealClaim(player, chunk, ClaimType.DEFAULT);
									return true;
								}
								M.message(player, "Squads", ChatColor.WHITE + "This land is already owned by " + ChatColor.YELLOW + SquadManager.getOwner(chunk).getName());
								return true;
							}
							squad.claim(chunk, type);
							return true;
						}
					}
				}
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("claim")) {
						if(args[1].equalsIgnoreCase("selected") || args[1].equalsIgnoreCase("selection")) {
							if(!player.hasPermission("squads.admin.claim_selection")) {
								M.sendLackPermsMessage(player);
								return true;
							}
							WandSession session = WandSession.sessions.get(player.getName());
							if(session == null) {
								M.message(player, "Squads", ChatColor.WHITE + "You must first select two blocks");
								return true;
							}
							if(session.getSelection1() == null) {
								M.message(player, "Squads", ChatColor.YELLOW + "Left click " + ChatColor.WHITE + "a block to set selection 1");
								return true;
							}
							if(session.getSelection2() == null) {
								M.message(player, "Squads", ChatColor.YELLOW + "Right click " + ChatColor.WHITE + "a block to set selection 2");
								return true;
							}
							ClaimType type = ClaimType.getType(args[2]);
							if(type == null) {
								M.message(player, "Squads", ChatColor.WHITE + "Please enter a valid claim type");
								return true;
							}
							squad.claim(player, session.getSelectionChunk1(), session.getSelectionChunk2(), type);
							if(WandSession.sessions.containsKey(player.getName())) WandSession.sessions.remove(player.getName());
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("bank")) {
						if(args[1].equalsIgnoreCase("deposit") || args[1].equalsIgnoreCase("add")) {
							if(squad == null) {
								M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
								return true;
							}
							int amount;
							try {
								amount = Integer.parseInt(args[2]);
							} catch(NumberFormatException e) {
								M.message(player, "Squads", ChatColor.WHITE + "You must enter a valid deposit amount");
								return true;
							}
							squad.depositMoneyIntoBank(player, amount);
							return true;
						}
						if(args[1].equalsIgnoreCase("withdraw")) {
							if(squad == null) {
								M.message(player, "Squads", ChatColor.WHITE + "You are not in a Squad");
								return true;
							}
							int amount;
							try {
								amount = Integer.parseInt(args[2]);
							} catch(NumberFormatException e) {
								M.message(player, "Squads", ChatColor.WHITE + "You must enter a valid withdrawal amount");
								return true;
							}
							squad.depositMoneyIntoBank(player, amount);
							return true;
						}
					}
				}
			}
			if(commandLabel.equalsIgnoreCase("sa")) {
				if(!player.hasPermission("squads.admin")) {
					M.sendLackPermsMessage(player);
					return true;
				}
				if(args.length == 0) {
					Squad.sendHelpMenu(player, 4);
					return true;
				}
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("toggle")) {
						if(!player.hasPermission("squads.admin.toggle")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad.toggleAdminMode(player);
						return true;
					}
					if(args[0].equalsIgnoreCase("unclaim")) {
						if(!player.hasPermission("squads.admin.force_unclaim")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Chunk chunk = player.getLocation().getChunk();
						if(!SquadManager.isClaimed(chunk)) {
							M.message(player, "Squads", ChatColor.WHITE + "You are not in claimed land");
							return true;
						}
						Squad.unclaim(player, chunk, Source.SERVER_ADMIN);
					}
					if(args[0].equalsIgnoreCase("clear")) {
						if(!player.isOp()) {
							M.sendLackPermsMessage(player);
							return true;
						}
						if(!player.hasPermission("squads.admim.clear_all")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad.clearAll(player);
						return true;
					}
				}
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("disband")) {
						if(!player.hasPermission("squads.admin.force_disband")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						target.forceDisband();
						M.message(player, "Squads", ChatColor.WHITE + "You disbanded " + ChatColor.YELLOW + target.getName());
						return true;
					}
					if(args[0].equalsIgnoreCase("claim")) {
						if(!player.hasPermission("squads.admin.force_claim")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						target.forceClaim(player.getLocation().getChunk(), ClaimType.DEFAULT);
						M.message(player, "Squads", "You claimed a chunk for " + ChatColor.YELLOW + target.getName());
					}
					if(args[0].equalsIgnoreCase("uca")) {
						if(!player.hasPermission("squads.admin.force_unclaim_all")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						target.unclaimAll();
						M.message(player, "Squads", ChatColor.WHITE + "You unclaimed all of " + ChatColor.YELLOW + target.getName() + "'s land");
						return true;
					}
					if(args[0].equalsIgnoreCase("tp")) {
						if(!player.hasPermission("squads.admin.teleport_home")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						if(target.getHome() == null) {
							M.message(player, "Squads", ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " does not have a home");
							return true;
						}
						player.teleport(target.getHome());
						M.message(player, "Squads", ChatColor.WHITE + "You teleported to " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.WHITE + "home");
					}
				}
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("claim")) {
						if(!player.hasPermission("squads.admin.force_claim")) {
							M.sendLackPermsMessage(player);
							return true;
						}
						Squad target = SquadManager.getSquad(args[1]);
						ClaimType type = ClaimType.getType(args[2]);
						if(target == null) {
							M.message(player, "Squads", ChatColor.WHITE + "Found 0 matches for " + ChatColor.YELLOW + args[1]);
							return true;
						}
						if(type == null) {
							M.message(player, "Squads", ChatColor.WHITE + "You must enter a valid " + ChatColor.YELLOW + "claim type");
							return true;
						}
						target.forceClaim(player.getLocation().getChunk(), type);
						M.message(player, "Squads", ChatColor.WHITE + "You claimed a chunk for " + ChatColor.YELLOW + target.getName());
						return true;
					}
				}
			}
		}
		return false;
	}
}
