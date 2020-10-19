package me.loogeh.Hype.Miscellaneous;

import me.loogeh.Hype.Armour.ArmourListener;
import me.loogeh.Hype.ChatChannels.ChannelListener;
import me.loogeh.Hype.Entity.EntityListener;
import me.loogeh.Hype.Games.GameListener;
import me.loogeh.Hype.Games.PublicArena;
import me.loogeh.Hype.Inventory.InventoryListener;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Player.PlayerListener;
import me.loogeh.Hype.Region.RegionListener;
import me.loogeh.Hype.Server.ServerListener;
import me.loogeh.Hype.Shops.ShopsListener;
import me.loogeh.Hype.Squads.SquadsListener;

import org.bukkit.plugin.PluginManager;

public class Listeners {

	public static void register() {
		Main plugin = Main.get();
		PluginManager pm = Main.get().getServer().getPluginManager();
		pm.registerEvents(new SquadsListener(plugin), plugin);
		pm.registerEvents(new ArmourListener(plugin), plugin);
		pm.registerEvents(new ChannelListener(plugin), plugin);
		pm.registerEvents(new PlayerListener(plugin), plugin);
		pm.registerEvents(new ServerListener(plugin), plugin);
		pm.registerEvents(new EntityListener(plugin), plugin);
		pm.registerEvents(new InventoryListener(plugin), plugin);
		pm.registerEvents(new ShopsListener(plugin), plugin);
		pm.registerEvents(new PublicArena(plugin), plugin);
		pm.registerEvents(new GameListener(plugin), plugin);
		pm.registerEvents(new RegionListener(plugin), plugin);
	}
	
}
