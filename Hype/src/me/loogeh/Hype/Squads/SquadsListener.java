package me.loogeh.Hype.Squads;

import me.loogeh.Hype.Event.PlayerJumpEvent;
import me.loogeh.Hype.Event.PlayerMoveBlockEvent;
import me.loogeh.Hype.Event.SquadEnterTerritoryEvent;
import me.loogeh.Hype.Event.SquadRelationChangeEvent;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Squads.Squad.ClaimType;
import me.loogeh.Hype.Utility.utilWorld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

@SuppressWarnings("deprecation")
public class SquadsListener implements Listener {
	public Main plugin;

	public SquadsListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onSquadPlayerMoveBlock(PlayerMoveBlockEvent event) {
		Player player = event.getPlayer();
		Squad owner = SquadManager.getOwner(event.getTo().getChunk()); 
		Squad fromOwner = SquadManager.getOwner(event.getFrom().getChunk());
		Squad pSquad = SquadManager.getSquad(player);
		Location from = event.getFrom();
		Location to = event.getTo();
		int fx = from.getBlockX() >> 4;
		int fz = from.getBlockZ() >> 4;
		int tx = to.getBlockX() >> 4;
		int tz = to.getBlockZ() >> 4;
		if(Squad.homeMove.contains(player.getName())) {
			Squad.homeMove.remove(player.getName());
			M.message(player, "Squads", ChatColor.WHITE + "Home teleportation cancelled");
		}
		if((fx != tx) || (fz != tz)) {
			if(SquadManager.hasSquad(player.getName())) {
				if(owner == fromOwner) return;
				if(fromOwner != null && owner == null) {
					M.message(player, "Territory", ChatColor.YELLOW + "Wilderness");
					Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
					return;
				}
				if(fromOwner != null && owner != null) {
					if(fromOwner.getName().equalsIgnoreCase(owner.getName())) return;
					ClaimType type = SquadManager.getClaimType(event.getTo().getChunk());
					if(type == ClaimType.SAFE) {
						M.message(player, "Territory", pSquad.getRelationColour(owner.getName()) + owner.getName() + ChatColor.GRAY + " (" + ChatColor.GREEN + "Safe" + ChatColor.GRAY + ")");
						Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
						return;
					}
					M.message(player, "Territory", pSquad.getRelationColour(owner.getName()) + owner.getName());
					Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
					return;
				}
				if(fromOwner == null && owner != null) {
					ClaimType type = SquadManager.getClaimType(event.getTo().getChunk());
					if(type == ClaimType.SAFE) {
						M.message(player, "Territory", pSquad.getRelationColour(owner.getName()) + owner.getName() + ChatColor.GRAY + " (" + ChatColor.GREEN + "Safe" + ChatColor.GRAY + ")");
						Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
						return;
					}
					M.message(player, "Territory", pSquad.getRelationColour(owner.getName()) + owner.getName());
					Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
					return;
				}
			}
			if(owner == fromOwner) return;
			if(fromOwner != null && owner == null) {
				M.message(player, "Territory", ChatColor.YELLOW + "Wilderness");
				Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
				return;
			}
			if(fromOwner != null && owner != null) {
				if(fromOwner.getName().equalsIgnoreCase(owner.getName())) return;
				ClaimType type = SquadManager.getClaimType(event.getTo().getChunk());
				if(type == ClaimType.SAFE) {
					M.message(player, "Territory", ChatColor.WHITE + owner.getName() + ChatColor.GRAY + " (" + ChatColor.GREEN + "Safe" + ChatColor.GRAY + ")");
					Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
					return;
				}
				M.message(player, "Territory", ChatColor.WHITE + owner.getName());
				Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
				return;
			}
			if(fromOwner == null && owner != null) {
				ClaimType type = SquadManager.getClaimType(event.getTo().getChunk());
				if(type == ClaimType.SAFE) {
					M.message(player, "Territory", ChatColor.WHITE + owner.getName() + ChatColor.GRAY + " (" + ChatColor.GREEN + "Safe" + ChatColor.GRAY + ")");
					Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
					return;
				}
				M.message(player, "Territory", ChatColor.WHITE + owner.getName());
				Main.plugin.getServer().getPluginManager().callEvent(new SquadEnterTerritoryEvent(player, fromOwner, owner, from, to));
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(event.getFrom().getY() < event.getTo().getY()) {
			Location to = event.getTo();
			if(player.getWorld().getBlockAt((int) to.getX(), (int) to.getY(), (int) to.getZ()).getType() == Material.AIR) {
				Main.plugin.getServer().getPluginManager().callEvent(new PlayerJumpEvent(player));
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadDamageByEntity(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(!(event.getDamager() instanceof Player && !(event.getDamager() instanceof Projectile))) return;
		Player player = (Player) event.getEntity();
		Entity damager = event.getDamager();
		if(player == null) return;
		if(SquadManager.isClaimed(player.getLocation().getChunk())) {
			ClaimType type = SquadManager.getClaimType(player.getLocation().getChunk());
			if(type == ClaimType.SAFE) {
				event.setCancelled(true);
				if(damager instanceof Player) {
					Player attacker = (Player) damager;
					M.message(attacker, "Squads", ChatColor.WHITE + "You cannot damage people in a " + ChatColor.YELLOW + "safe zone");
					return;
				}
				if(damager instanceof Projectile) {
					Projectile projectile = (Projectile) damager;
					if(projectile.getShooter() == null) return;
					if(!(projectile.getShooter() instanceof Player)) return;
					Player shooter = (Player) projectile.getShooter();
					if(shooter != null) M.message(shooter, "Squads", ChatColor.WHITE + "You cannot damage people in a " + ChatColor.YELLOW + "safe zone");
					return;
				}
			}
			if(type == ClaimType.DEFAULT) {
				Squad squad = SquadManager.getSquad(player);
				if(squad == null) return;
				if(damager instanceof Player) {
					Player attacker = (Player) damager;
					Squad dmgr_squad = SquadManager.getSquad(attacker);
					if(dmgr_squad == null) return;
					if(squad.getName().equalsIgnoreCase(dmgr_squad.getName())) {
						event.setCancelled(true);
						M.message(attacker, "Squads", ChatColor.WHITE + "You cannot damage " + dmgr_squad.getRelationColour(squad.getName()) + player.getName());
						return;
					}
					if(squad.areAlly(dmgr_squad.getName())) {
						event.setCancelled(true);
						M.message(attacker, "Squads", ChatColor.WHITE + "You cannot damage " + dmgr_squad.getRelationColour(squad.getName()) + player.getName());
						return;
					}

				}
			}
		}
		if(!SquadManager.isClaimed(player.getLocation().getChunk())) {
			if(damager instanceof Player) {
				Player dmgr_p = (Player) damager;
				Squad dmgr_squad = SquadManager.getSquad(dmgr_p);
				Squad squad = SquadManager.getSquad(player);
				if(dmgr_squad == null || squad == null) return;
				if(squad.getName().equalsIgnoreCase(dmgr_squad.getName())) {
					event.setCancelled(true);
					M.message(dmgr_p, "Squads", ChatColor.WHITE + "You cannot damage " + dmgr_squad.getRelationColour(squad.getName()) + player.getName());
				}
			}
			if(damager instanceof Projectile) {
				Projectile proj = (Projectile) damager;
				if(!(proj.getShooter() instanceof Player)) return;
				Player shooter = (Player) proj.getShooter();
				Squad dmgr_squad = SquadManager.getSquad(shooter);
				Squad squad = SquadManager.getSquad(player);
				if(dmgr_squad == null || squad == null) return;
				if(dmgr_squad.getName().equalsIgnoreCase(squad.getName()) || squad.areAlly(dmgr_squad.getName())) {
					event.setCancelled(true);
					if(player != null) M.message(player, "Squads", ChatColor.WHITE + "You cannot damage " + dmgr_squad.getRelationColour(squad.getName()) + player.getName());
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(Squad.isAdminMode(player)) {
			event.setCancelled(false);
			return;
		}
		int id = event.getBlock().getTypeId();
		Chunk chunk = event.getBlock().getChunk();
		if(!SquadManager.isClaimed(chunk)) return;
		Squad owner = SquadManager.getOwner(chunk);
		Squad p_squad = SquadManager.getSquad(player);
		if(owner == null) return;
		if(p_squad == null && !Squad.allowPlace(id)) {
			event.setCancelled(true);
			M.message(player, "Squads", ChatColor.WHITE + "You cannot place in " + ChatColor.YELLOW + owner.getName());
			return;
		}
		if(p_squad.getName().equalsIgnoreCase(owner.getName())) return;
		if(!Squad.allowPlace(id)) {
			event.setCancelled(true);
			M.message(player, "Squads", ChatColor.WHITE + "You cannot place in " + ChatColor.YELLOW + owner.getName());
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(player.isOp() && player.getItemInHand() != null) {
			if(player.getItemInHand().getType().equals(Material.WOOD_AXE)) {
				event.setCancelled(true);
				return;
			}
		}
		if(WandSession.sessions.containsKey(player.getName())) {
			event.setCancelled(true);
			return;
		}
		Block block = event.getBlock();
		int id = block.getTypeId();
		Chunk chunk = block.getLocation().getChunk();
		if(!SquadManager.isClaimed(chunk)) return;
		if(SquadManager.isClaimed(chunk)) {
			if(Squad.adminMode.contains(player.getName())) {
				event.setCancelled(false);
				return;
			}
			Squad owner = SquadManager.getOwner(chunk);
			Squad p_squad = SquadManager.getSquad(player);
			if(owner == null) return;
			if(p_squad == null && !Squad.canBreak(id)) {
				event.setCancelled(true);
				M.message(player, "Squads", ChatColor.WHITE + "You cannot break in " + ChatColor.YELLOW + owner.getName());
				return;
			}
			if(p_squad.getName().equalsIgnoreCase(owner.getName())) return;
			if(!Squad.canBreak(id)) {
				event.setCancelled(true);
				M.message(player, "Squads", ChatColor.WHITE + "You cannot break in " + ChatColor.YELLOW + owner.getName());
				return;
			}
		}
	}


	@EventHandler(priority = EventPriority.LOW)
	public void onSquadPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(Squad.adminMode.contains(player.getName())) {
			event.setCancelled(false);
			ItemStack held = event.getItem();
			if(held != null) {
				int id = held.getTypeId();
				if(id == Squad.getWandID()) {
					if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
						if(WandSession.sessions.containsKey(player.getName())) {
							if(WandSession.sessions.get(player.getName()).getSelection1() == null) {
								WandSession.sessions.get(player.getName()).setSelection1(event.getClickedBlock().getLocation());
								M.message(player, "Squads", ChatColor.WHITE + "Selection 1 " + ChatColor.YELLOW + utilWorld.chunkToStr(WandSession.sessions.get(player.getName()).getSelectionChunk1()));
							}
						} else {
							WandSession.sessions.put(player.getName(), new WandSession(event.getClickedBlock().getLocation()));
							M.message(player, "Squads", ChatColor.WHITE + "Selection 1 " + ChatColor.YELLOW + utilWorld.chunkToStr(WandSession.sessions.get(player.getName()).getSelectionChunk1()));
						}
						event.setCancelled(true);
					}
					else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if(WandSession.sessions.containsKey(player.getName())) WandSession.sessions.get(player.getName()).setSelection2(event.getClickedBlock().getLocation());
						else WandSession.sessions.put(player.getName(), new WandSession(event.getClickedBlock().getLocation()));
						M.message(player, "Squads", ChatColor.WHITE + "Selection 2 " + ChatColor.YELLOW + utilWorld.chunkToStr(event.getClickedBlock().getChunk()));
						event.setCancelled(true);
					}
				}
			}
		}
		if(Squad.adminMode.contains(player.getName())) {
			event.setCancelled(false);
			return;
		}
		if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			Squad owner = SquadManager.getOwner(block.getChunk());
			Squad p_squad = SquadManager.getSquad(player);
			if(p_squad != null && owner != null) {
				if(owner.getName().equalsIgnoreCase(p_squad.getName())) {
					event.setCancelled(false);
					return;
				}
				if(p_squad.getTrust(owner.getName())) {
					if(Squad.trustInteractableBlocks.contains(block.getTypeId())) {
						event.setCancelled(false);
						return;
					} else if(!Squad.trustInteractableBlocks.contains(block.getTypeId()) && Squad.interactableBlocks.contains(block.getTypeId())){
						M.message(player, "Squads", ChatColor.WHITE + "You cannot interact in " + ChatColor.YELLOW + owner.getName());
						event.setCancelled(true);
						return;
					}
				}
				if(Squad.interactableBlocks.contains(block.getTypeId())) {
					M.message(player, "Squads", ChatColor.WHITE + "You cannot interact in " + ChatColor.YELLOW + owner.getName());
					event.setCancelled(true);
					return;
				}
			}
			if(Squad.interactableBlocks.contains(block.getTypeId())) {
				M.message(player, "Squads", ChatColor.WHITE + "You cannot interact in " + ChatColor.YELLOW + owner.getName());
				event.setCancelled(true);
			}
		}
		if(Squad.getBlockUsageOfPressurePlates()) {
			if(event.getAction() == Action.PHYSICAL) {
				Location loc = player.getLocation();
				if(!SquadManager.isClaimed(loc.getChunk())) return;
				Squad owner = SquadManager.getOwner(loc.getChunk());
				Squad p_squad = SquadManager.getSquad(player);
				if(p_squad != null) {
					if(owner.getName().equalsIgnoreCase(p_squad.getName())) {
						event.setCancelled(false);
						return;
					}
					if(p_squad.getTrust(owner.getName())) {
						event.setCancelled(false);
						return;
					}
					event.setCancelled(true);
					return;
				}
				event.setCancelled(true);
				return;
			}
		}
	}


	@EventHandler(priority = EventPriority.LOW)
	public void onSquadPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Squad squad = SquadManager.getSquad(player);
		if(squad == null) return;
		if((squad.getPower() > Squad.getMinimumPower()) && (squad.getPower() <= squad.getMaxPower())) {
			squad.alterPower(-1);
			long next = SquadManager.getNextAvailableMillis(System.currentTimeMillis());
			SquadManager.powerRegen.put(next, squad.getName());
		}
		if(player.getKiller() != null) {
			Squad k_squad = SquadManager.getSquad(player.getKiller());
			if(k_squad == null) return;
			if(squad.areEnemy(k_squad)) k_squad.addDominance(squad);
		} else {
			Squad owner = SquadManager.getOwner(player.getLocation().getChunk());
			if(owner == null) return;
			if(squad.areEnemy(owner)) owner.addDominance(squad);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadEntityBlockChange(EntityChangeBlockEvent event) {
		Block block = event.getBlock();
		Chunk chunk = block.getChunk();
		if(SquadManager.isClaimed(chunk)) event.setCancelled(true);
		else return;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadEntityDoorBreak(EntityBreakDoorEvent event) {
		Block block = event.getBlock();
		Chunk chunk = block.getChunk();
		if(SquadManager.isClaimed(chunk)) event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		event.setCancelled(true);
		Squad.sendChat(player, message);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(Squad.getChangeNameColour()) {
			Scoreboard board = TeamManager.getNewScoreboard(player);
			TeamManager.boards.put(player.getName(), board);
			player.setScoreboard(board);
			TeamManager.join(player);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(C.boldDarkGray + "Quit > " + ChatColor.GRAY + player.getName());
		if(TeamManager.boards.containsKey(player.getName())) TeamManager.boards.remove(player.getName());
		if(Squad.adminMode.contains(player.getName())) Squad.adminMode.remove(player.getName());
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadCreatureSpawn(CreatureSpawnEvent event) {
		if(SquadManager.isClaimed(event.getLocation().getChunk())) {
			if(event.getSpawnReason().equals(SpawnReason.CUSTOM)) return;
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadEntityExplode(EntityExplodeEvent event) {
		Chunk chunk = event.getLocation().getChunk();
		if(SquadManager.isClaimed(chunk)) {
			ClaimType type = SquadManager.getClaimType(chunk);
			if(type == ClaimType.SAFE) {
				event.blockList().clear();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadBlockSpread(BlockSpreadEvent event) {
		if(Squad.getDisableFireSpread()) {
			if(event.getBlock().getType() == Material.FIRE) event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		Chunk chunk = entity.getLocation().getChunk();
		if(entity instanceof Player) {
			if(event.getCause() == DamageCause.FALL) {
				if(!SquadManager.isClaimed(chunk)) return;
				ClaimType type = SquadManager.getClaimType(chunk);
				if(type == ClaimType.SAFE) {
					if(!Squad.getTakeFallDamageInSafeZone()) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSquadRelationChange(SquadRelationChangeEvent event) {
		Squad changer = event.getChanger();
		Squad squad = event.getSquad();
		for(String change : changer.memberMap.keySet()) {
			Player p_change = Bukkit.getPlayer(change);
			if(p_change != null) {
				Scoreboard newBoard = TeamManager.getNewScoreboard(p_change);
				p_change.setScoreboard(newBoard);
			}
		}

		for(String squad_member : squad.memberMap.keySet()) {
			Player p_member = Bukkit.getPlayer(squad_member);
			if(p_member != null) {
				Scoreboard newBoard = TeamManager.getNewScoreboard(p_member);
				p_member.setScoreboard(newBoard);
			}
		}
	}
	
	@EventHandler
	public void onSquadEnterTerritory(SquadEnterTerritoryEvent event) {
		
	}
}
