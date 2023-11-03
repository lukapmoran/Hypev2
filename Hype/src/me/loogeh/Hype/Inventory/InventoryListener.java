package me.loogeh.Hype.Inventory;

import me.loogeh.Hype.Main.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
	Main plugin;

	public InventoryListener(Main instance) {
		plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		if(inv.getMaxStackSize() == 13) {
			event.setCancelled(true);
			player.closeInventory();
			return;
		}
		HInventory h_inv = HInventory.getInventory(inv);
		if(h_inv != null) {
			if(h_inv.getFlags().contains(IFlag.DISABLE_CLICK)) {
				if(event.getRawSlot() < event.getView().getBottomInventory().getSize() - 1) {
					event.setCancelled(true); //fix
					player.updateInventory();
				}
			}
			if(h_inv.getFlags().contains(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM)) {
				event.setCancelled(true);
				player.updateInventory();
			}
			if(h_inv.getFlags().contains(IFlag.DISABLE_LEFT_CLICK)) {
				if(event.isLeftClick()) {
					event.setCancelled(true);
					player.updateInventory();
				}
			}
			if(h_inv.getFlags().contains(IFlag.DISABLE_RIGHT_CLICK)) {
				if(event.isRightClick()) {
					event.setCancelled(true);
					player.updateInventory();
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		HInventory h_inv = HInventory.getInventory(inv);
		if(h_inv != null) {
			if(h_inv.getDestroyOnClose()) h_inv.remove();
		}
	}
}

