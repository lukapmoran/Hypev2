package me.loogeh.Hype.Armour;

import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Event.BowUnchargeEvent;
import me.loogeh.Hype.Event.BowUnchargeEvent.UnchargeReason;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Inventory.HInventory;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Utility.util;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmourListener implements Listener {
	public Main plugin;

	public ArmourListener(Main instance) {
		plugin = instance;
	}
	

	@EventHandler
	public void onArmourAbilityUse(AbilityUseEvent event) {
		Member member = Member.get(event.getPlayer());
		member.getStats().addAbility();
	}
	

	@EventHandler
	public void onArmourPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if(player.getItemInHand() == null) return;
		if(player.getItemInHand().getType().equals(Material.BOW)) Main.plugin.getServer().getPluginManager().callEvent(new BowUnchargeEvent(player, UnchargeReason.TELEPORT));
	}

	@EventHandler
	public void onArmourPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(player.getItemInHand() == null) return;
		if(player.getItemInHand().getType().equals(Material.BOW)) Main.plugin.getServer().getPluginManager().callEvent(new BowUnchargeEvent(player, UnchargeReason.PLAYER_LEAVE));
	}

	@EventHandler
	public void onArmourItemChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		if(player.getItemInHand() == null) return;
		if(player.getItemInHand().getType().equals(Material.BOW)) Main.plugin.getServer().getPluginManager().callEvent(new BowUnchargeEvent(player, UnchargeReason.ITEM_CHANGE));
	}

	@EventHandler
	public void onArmourItemBreak(PlayerItemBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getBrokenItem();
		if(item == null) return;
		if(item.getType().equals(Material.BOW)) Main.plugin.getServer().getPluginManager().callEvent(new BowUnchargeEvent(player, UnchargeReason.ITEM_CHANGE));
	}

	@EventHandler
	public void onArmourPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(player.getItemInHand() == null) return;
		if(player.getItemInHand().getType().equals(Material.BOW)) Main.plugin.getServer().getPluginManager().callEvent(new BowUnchargeEvent(player, UnchargeReason.ITEM_CHANGE));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArmourPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Squad squad = SquadManager.getSquad(player);
		Member member = Member.get(player);
		Block block = event.getBlock();
		Material type = block.getType();
		Location location = block.getLocation();
		if(SquadManager.isClaimed(location.getChunk())) {
			Squad owner = SquadManager.getOwner(location.getChunk());
			if(squad != null && owner != null) {
				if(type.equals(Material.SOIL)) {
					if(member.getClasses().isUseable(AbilityInfo.PLANTER)) {
						if(player.getItemInHand() == null) return;
						for(int x = 0; x < 2; x++) {
							for(int z = 0; z < 2; z++) {
								Block rel = block.getRelative(x, 0, z);
								Block negRel = block.getRelative(-x, 0, -z);
								Block neutRel = block.getRelative(x, 0, -z);
								Block neutRelInvert = block.getRelative(-x, 0, z);
								Block relY = rel.getRelative(x, 1, z);
								Block negRelY = negRel.getRelative(-x, 1, -z);
								Block neutRelY = neutRel.getRelative(x, 1, -z);
								Block neutRelInvertY = neutRelInvert.getRelative(-x, 1, z);
								if(player.getItemInHand().getType().equals(Material.SEEDS)) {
									if(relY.getType().equals(Material.AIR)) {
										relY.setType(Material.SEEDS);
										relY.setData((byte) 1);
									}
									if(negRelY.getType().equals(Material.AIR)) {
										negRelY.setType(Material.SEEDS);
										negRelY.setData((byte) 1);
									}
									if(neutRelY.getType().equals(Material.AIR)) {
										neutRelY.setType(Material.SEEDS);
										neutRelY.setData((byte) 1);
									}
									if(neutRelInvertY.getType().equals(Material.AIR)) {
										neutRelInvertY.setType(Material.SEEDS);
										neutRelInvertY.setData((byte) 1);
									}
								}
								if(player.getItemInHand().getType().equals(Material.CARROT) || player.getItemInHand().getType().equals(Material.CARROT_ITEM)) {
									if(relY.getType().equals(Material.AIR)) {
										relY.setType(Material.CARROT);
										relY.setData((byte) 1);
									}
									if(negRelY.getType().equals(Material.AIR)) {
										negRelY.setType(Material.CARROT);
										negRelY.setData((byte) 1);
									}
									if(neutRelY.getType().equals(Material.AIR)) {
										neutRelY.setType(Material.CARROT);
										neutRelY.setData((byte) 1);
									}
									if(neutRelInvertY.getType().equals(Material.AIR)) {
										neutRelInvertY.setType(Material.CARROT);
										neutRelInvertY.setData((byte) 1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onArmourInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = event.getClickedBlock();
			if(block.getType().equals(Material.ENCHANTMENT_TABLE)) {
				KitSelector kitSelector = new KitSelector();
				kitSelector.open(player);
			}
		}
	}
	
	
	@EventHandler
	public void onArmourInventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		Member member = Member.get(player);
		if(member == null) return;
		HInventory h_inv = HInventory.getInventory(inv);
		ItemStack item = event.getCurrentItem();
		if(item == null) return;
		Material type = item.getType();
		String item_name = "";
		if(!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		if(meta.hasDisplayName()) item_name = ChatColor.stripColor(meta.getDisplayName());
		if(h_inv != null) {
			if(h_inv instanceof ArmourInventory) {
				ArmourInventory a_inv = (ArmourInventory) h_inv;
				if(a_inv instanceof KitSelector) {
					BuildManager manager = member.getClasses().getBuildManager();
					BuildSelector b_selector = new BuildSelector(manager, ClassType.LEATHER);
					if(item.getType().equals(Material.LEATHER_HELMET) || item.getType().equals(Material.LEATHER_CHESTPLATE) || item.getType().equals(Material.LEATHER_LEGGINGS) ||  item.getType().equals(Material.LEATHER_BOOTS)) b_selector = new BuildSelector(manager, ClassType.LEATHER);
					else if(item.getType().equals(Material.GOLD_HELMET) || item.getType().equals(Material.GOLD_CHESTPLATE) || item.getType().equals(Material.GOLD_LEGGINGS) ||  item.getType().equals(Material.GOLD_BOOTS)) b_selector = new BuildSelector(manager, ClassType.GOLD);
					else if(item.getType().equals(Material.CHAINMAIL_HELMET) || item.getType().equals(Material.CHAINMAIL_CHESTPLATE) || item.getType().equals(Material.CHAINMAIL_LEGGINGS) ||  item.getType().equals(Material.CHAINMAIL_BOOTS)) b_selector = new BuildSelector(manager, ClassType.CHAIN);
					else if(item.getType().equals(Material.IRON_HELMET) || item.getType().equals(Material.IRON_CHESTPLATE) || item.getType().equals(Material.IRON_LEGGINGS) ||  item.getType().equals(Material.IRON_BOOTS)) b_selector = new BuildSelector(manager, ClassType.IRON);
					else if(item.getType().equals(Material.DIAMOND_HELMET) || item.getType().equals(Material.DIAMOND_CHESTPLATE) || item.getType().equals(Material.DIAMOND_LEGGINGS) ||  item.getType().equals(Material.DIAMOND_BOOTS)) b_selector = new BuildSelector(manager, ClassType.DIAMOND);
					else return;
					b_selector.open(player);
				}
				if(a_inv instanceof BuildSelector) {
					BuildSelector b_selector = (BuildSelector) a_inv;
					ClassType set = b_selector.getArmourSet();
					Integer build = util.getInteger(item_name.split(" ")[2]);
					if(build == null) return;
					if(item.getType().equals(Material.BOOK_AND_QUILL)) {
						BuildEditor b_editor = new BuildEditor(member, b_selector.getBuildManager().getBuild(set, build));
						b_editor.open(player);
					}
				}
				if(a_inv instanceof BuildEditor) {
					//find ability which was selected
					String name = StringUtils.replace(item_name, " (Locked)", "");
					AbilityInfo ability = AbilityInfo.getAbilityExact(name);
					if(ability == null || ability.equals(AbilityInfo.NONE)) return;
						
					BuildEditor editor = (BuildEditor) a_inv;
					System.out.println(name);
					ClassType set = editor.getBuildFactory().getArmourSet();
					System.out.println(item.getType().name());
					if(type.equals(Material.BOOK_AND_QUILL)) {
						AbilityPurchaser purchase = new AbilityPurchaser(ability, set, editor.getBuildFactory().getBuildId());
						purchase.open(player);
					}
					if(item.getType().equals(Material.BOOK)) {
//						member.getClasses().selectedAbility(set, ability, build);
						editor.update();
					}
				}
				if(a_inv instanceof AbilityPurchaser) {
					AbilityPurchaser purchaser = (AbilityPurchaser) a_inv;
					if(item.getType().equals(Material.REDSTONE_BLOCK)) {
						purchaser.remove();
						M.message(player, "Purchase", ChatColor.WHITE + "Purchase of " + ChatColor.YELLOW + purchaser.getAbility().getName() + ChatColor.WHITE + " cancelled");
					}
					if(item.getType().equals(Material.EMERALD_BLOCK)) {
//						boolean success = member.getClasses().purchaseAbility(purchaser.getArmourSet(), purchaser.getAbility());
						boolean success = true;
						if(success) {
							BuildEditor editor = new BuildEditor(member, member.getClasses().getBuildManager().getBuild(purchaser.getArmourSet(), purchaser.getBuild()));
							editor.open(player);
							purchaser.remove();
						}
					}
				}
			}
		}
	}
}
