package me.loogeh.Hype.Shops;

import java.util.Arrays;

import me.loogeh.Hype.Event.ShopLeaveEvent;
import me.loogeh.Hype.Event.ShopOpenEvent;
import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Shops.Shop.ShopType;
import me.loogeh.Hype.Utility.utilItem;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopsListener implements Listener {
	Main plugin;

	public ShopsListener(Main instance) {
		plugin = instance;
	}

	@EventHandler
	public void onShopEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if(Shop.isShop(entity.getUniqueId())) event.setCancelled(true);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onShopInventoryClickEvent(InventoryClickEvent event) {
		Inventory inv = event.getView().getTopInventory();
		String nameRaw = ChatColor.stripColor(inv.getName());
		Player player = (Player) event.getWhoClicked();
		if(player == null) return;
		if(nameRaw.startsWith("Shop")) {
			event.setCancelled(true);
			player.updateInventory();
			int slot = event.getSlot();
			int raw_slot = event.getRawSlot();
			ItemStack item = event.getCurrentItem();
			String name = ChatColor.stripColor(inv.getName().replaceAll("Shop ", ""));
			if(item == null) return;
			ShopType type = ShopType.getType(name);
			if(raw_slot < 54) {//top inventory
				if(type != null && !type.equals(ShopType.ENCHANTER)) {
				Shop shop = Shop.getShop(type);
					if(shop == null) return;
					if(event.getClick().isShiftClick()) {
						shop.buy(player, item, item.getType().getMaxStackSize());
						return;
					}
					if(event.getClick().isLeftClick() || event.getClick().isRightClick()) {
						shop.buy(player, item, 1);
						return;
					}
				} else if(type != null && type.equals(ShopType.ENCHANTER)) {
					Shop shop = Shop.getShop(ShopType.ENCHANTER);
					if(shop == null) return;
					Enchantment enchantment = null;
					for(Enchantment ench : item.getEnchantments().keySet()) {
						enchantment = ench;
						break;
					}
					if(enchantment == null) return;
					if(event.getClick().isLeftClick() || event.getClick().isRightClick()) {
						shop.buyEnchantment(player, enchantment);
						 return;
					}
				}
			}
			if(raw_slot > 53 && raw_slot < 90) {//bottom inventory
				if(type != null && !type.equals(ShopType.ENCHANTER)) {
					Shop shop = Shop.getShop(type);
					if(event.getClick().isShiftClick()) {
						shop.sell(player, item, item.getAmount(), slot);
						return;
					}
					if(event.getClick().isLeftClick() || event.getClick().isRightClick()) {
						shop.sell(player, item, 1, slot);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onShopsInventoryClose(InventoryCloseEvent event) {
		Inventory inv = event.getView().getTopInventory();
		String nameRaw = ChatColor.stripColor(inv.getName());
		Player player = (Player) event.getPlayer();
		if(nameRaw.startsWith("Shop")) {
			Main.plugin.getServer().getPluginManager().callEvent(new ShopLeaveEvent(player, Shop.getShop(nameRaw.split(" ")[1].toLowerCase())));
		}
	}

	@EventHandler
	public void onShopLeaveEvent(ShopLeaveEvent event) {
		Player player = event.getPlayer();
		Shop shop = event.getShop();
		if(shop == null || player == null) return;
		if(shop.getType() != ShopType.ENCHANTER) shop.sendLog(player);
		else shop.sendELog(player);
		if(Shop.shopLog.containsKey(player.getName())) Shop.shopLog.remove(player.getName());
	}

	@EventHandler
	public void onShopOpenEvent(ShopOpenEvent event) {
		Player player = event.getPlayer();
		Shop shop = event.getShop();
		if(shop == null || player == null) return;
		Shop.shopLog.put(player.getName(), new ShopLog(player.getName()));
		for(ItemStack items : player.getInventory().getContents()) {
			if(items != null) {
				ItemMeta item_meta = items.getItemMeta();
				int sell_price = Shop.getSellPriceShop(items);
				ShopType seller = Shop.getWhereItemSold(items);
				if(seller == null) return;
				item_meta.setDisplayName(C.boldAqua + utilItem.toCommon(items.getType().toString().toLowerCase()));
				if(sell_price != -1) {
					item_meta.setLore(Arrays.asList(C.boldAqua + "Sell Shop " + ChatColor.AQUA + seller.getName(), C.boldAqua + "Sell Price " + ChatColor.AQUA + "$" + sell_price));
				}
			}
		}
	}

	@EventHandler
	public void onShopEntityInteractEvent(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Shop shop = Shop.getShop(event.getRightClicked().getUniqueId());
		event.setCancelled(true);
		if(shop == null) return;
		else {
			shop.open(player);
			Main.plugin.getServer().getPluginManager().callEvent(new ShopOpenEvent(player, shop));
		}
	}

}
