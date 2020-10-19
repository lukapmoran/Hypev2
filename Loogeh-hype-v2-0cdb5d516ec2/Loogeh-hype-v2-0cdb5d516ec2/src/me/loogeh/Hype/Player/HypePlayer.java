package me.loogeh.Hype.Player;

import me.loogeh.Hype.Utility.utilItem;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class HypePlayer {
	
	public static void setDeathMessage(PlayerDeathEvent event, int damagerCount) {
		Player player = event.getEntity();
		String msg = event.getDeathMessage();
		Player killer = player.getKiller();
		if(killer != null) {
			String item = "Fists";
			if(killer.getItemInHand() != null) {
				int levels = utilItem.getEnchantmentLevel(killer.getItemInHand());
				item = WordUtils.capitalize(killer.getItemInHand().getType().toString().toLowerCase().replaceAll("_", " "));
				if(killer.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
					if(player.getLastDamageCause().getCause() == DamageCause.BLOCK_EXPLOSION || player.getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION) item = "TNT";
				}
				if(levels > 0) item = "L" + levels + " " + item;
			}
			if(item.equalsIgnoreCase("air")) item = "Fists";
			if(player.getName().equalsIgnoreCase(killer.getName())) {
				event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + killer.getName() + ChatColor.WHITE + " killed themself with " + ChatColor.YELLOW + item);
				return;
			}
			if(damagerCount > 1) {
				event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + killer.getName() + "+ " + damagerCount + ChatColor.WHITE + " killed " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " with " + ChatColor.YELLOW + item);
				return;
			}
			event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + killer.getName() + ChatColor.WHITE + " killed " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " with " + ChatColor.YELLOW + item);
			return;
		}
		String[] split = msg.split(" ");
		if(msg.contains("died")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + " died");
		else if(msg.contains("hit the ground too hard") || msg.contains("fell from a high place") || msg.contains("fell off a ladder") || msg.contains("fell off some vines") || msg.contains("fell out of the water") || msg.contains("fell into a patch of fire") || msg.contains("fell into a patch of cacti")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " fell to their death");
		else if(msg.contains("was squashed by a falling anvil")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was squashed by an " + ChatColor.YELLOW + "Anvil");
		else if(msg.contains("was pricked to death") || msg.contains("walked into a cactus whilst trying to escape")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by a " + ChatColor.YELLOW + "Cactus");
		else if(msg.contains("was shot by arrow")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was shot by an " + ChatColor.YELLOW + "Arrow");
		else if(msg.contains("drowned")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " drowned");
		else if(msg.contains("blew up")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was " + ChatColor.YELLOW + "Exploded");
		else if(msg.contains("was blown up by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was blown up by " + ChatColor.YELLOW + split[5]);
		else if(msg.contains("was doomed to fall by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was pushed to their death by " + ChatColor.YELLOW + split[6]);
		else if(msg.contains("was shot off some vines by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was shot off some vines by " + ChatColor.YELLOW + split[7]);
		else if(msg.contains("was shot off a ladder by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was shot off a ladder by " + ChatColor.YELLOW + split[7]);
		else if(msg.contains("was blown from a high place by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was blown off a ledge by " + ChatColor.YELLOW  + split[8]);
		else if(msg.contains("went up in flames")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE +  " burned to death");
		else if(msg.contains("burned to death")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE +  " burned to death");
		else if(msg.contains("was burnt to a crisp whilst fighting")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was set alight by " + ChatColor.YELLOW + split[8]);
		else if(msg.contains("walked into fire whilst fighting")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " walked into fire with " + ChatColor.YELLOW + split[6]);
		else if(msg.contains("was slain by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by a " + ChatColor.YELLOW + split[4]);
		else if(msg.contains("was shot by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was shot by a " + ChatColor.YELLOW + split[4]);
		else if(msg.contains("was fireballed by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was fireballed by a " + ChatColor.YELLOW + split[4]);
		else if(msg.contains("was killed by magic")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by" + ChatColor.YELLOW + " magic");
		else if(msg.contains("was killed by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + split[4]);
		else if(msg.contains("got finished off by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + split[4] + ChatColor.WHITE + " with " + ChatColor.YELLOW + split[6]);
		else if(msg.contains("tried to swim in lava")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + "Lava");
		else if(msg.contains("was squashed by a falling block")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by a " + ChatColor.YELLOW + "Falling Block");
		else if(msg.contains("starved to death")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + "Starvation");
		else if(msg.contains("suffocated in a wall")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + "Suffocation");
		else if(msg.contains("was killed while trying to hurt")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + split[7] + ChatColor.WHITE + " with " + ChatColor.YELLOW + "Thorns");
		else if(msg.contains("fell out of the world") || msg.contains("fell from a high place and fell out of the world")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + "Void");
		else if(msg.contains("was knocked into the void by")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was knocked into the " + ChatColor.YELLOW + "Void " + ChatColor.WHITE + " by " + ChatColor.YELLOW + split[7]);
		else if(msg.contains("withered away")) event.setDeathMessage(ChatColor.GRAY + "Death - " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " was killed by " + ChatColor.YELLOW + "Wither Potion");
	}
	
//	public static void setDeathMessage(PlayerDeathEvent event) {
//		if(event == null) return;
//		Player player = event.getEntity();
//		HPlayer h_player = HPlayer.getHPlayer(player);
//		if(h_player == null) return;
//		String recentCauses = h_player.getLastDamageCauses();
//		String prefix = C.boldDarkGray + "Death > ";
//		if(player.getKiller() == null) {
//			if(recentCauses.equals("")) {
//				event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " died");
//				return;
//			}
//			event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + recentCauses.substring(0, recentCauses.length() - 2));
//			return;
//		}
//		Player killer = player.getKiller();
//		if(killer != null) {
//			ItemStack item = killer.getItemInHand();
//			int damagerCount = h_player.getRecentDamagersCount();
//			if(recentCauses.equals("")) {
//				if(damagerCount < 1) {
//					if(item == null) event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " with " + ChatColor.GREEN + "Fists");
//					else event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " with " + ChatColor.GREEN + utilItem.toCommon(item.getType().toString()));
//					return;
//				} else {
//					if(item == null) event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName() + " + " + damagerCount + ChatColor.GRAY + " with " + ChatColor.GREEN + "Fists");
//					else event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName() + " + " + damagerCount  + ChatColor.GRAY + " with " + ChatColor.GREEN + utilItem.toCommon(item.getType().toString()));
//				}
//			} else {
//				String cleanCause = recentCauses.substring(0, recentCauses.length() - 2);
//				if(damagerCount < 1) {
//					 event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " with " + ChatColor.GREEN + cleanCause);
//				} else {
//					event.setDeathMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName() + " + " + damagerCount + ChatColor.GRAY + " with " + ChatColor.GREEN + cleanCause);
//				}
//			}
//		}
//	}
	
	public static void setSpecialist(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
	}




	public static void setSamuri(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
	}




	public static void setAgility(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
	}




	public static void setArcher(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
	}



	public static void setJuggernaut(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
	}
}
