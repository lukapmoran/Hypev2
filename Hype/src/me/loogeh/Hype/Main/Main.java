package me.loogeh.Hype.Main;

import me.loogeh.Hype.Armour.AbilityFactory;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.ChatChannels.ChannelManager;
import me.loogeh.Hype.ChatChannels.ChannelsFile;
import me.loogeh.Hype.CombatTag.CombatTag;
import me.loogeh.Hype.Entity.Entity;
import me.loogeh.Hype.Entity.SeizureSheep;
import me.loogeh.Hype.Games.Game;
import me.loogeh.Hype.Games.MapManager;
import me.loogeh.Hype.Item.Item;
import me.loogeh.Hype.Item.ThrowItem;
import me.loogeh.Hype.Miscellaneous.Commands;
import me.loogeh.Hype.Miscellaneous.Cooldown;
import me.loogeh.Hype.Miscellaneous.Listeners;
import me.loogeh.Hype.Miscellaneous.UnknownCommand;
import me.loogeh.Hype.SQL.MySQL;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Sector.MemberManager;
import me.loogeh.Hype.Sector.Sector;
import me.loogeh.Hype.Server.LogFile;
import me.loogeh.Hype.Server.ServerManager;
import me.loogeh.Hype.Server.Ticker;
import me.loogeh.Hype.Shops.Shop;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Squads.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static final Logger logger = Logger.getLogger("Minecraft");
	public static FileConfiguration config = null;
	public static Main plugin = null;
	public static ChannelsFile channelsFile = null;
	public static FileConfiguration channels = null;
	public static HashMap<String, LogFile> server_logs = new HashMap<String, LogFile>();
	public static UnknownCommand uc;
	public static MySQL mysql;
	public static AbilityFactory abilityFactory;
	public static MemberManager memberManager;
	
	public void onEnable() {
		try {
			long start = System.currentTimeMillis();
			plugin = this;
			config = getConfig();
			config.options().copyDefaults(true);
			saveConfig();
			channelsFile = new ChannelsFile("channels.yml");
			channels = channelsFile.getConfig();
			channels.options().copyDefaults(true);
			channelsFile.save();
			mysql = new MySQL();
			boolean connection = mysql.connect();
			if(connection == false) {
				logger.log(Level.WARNING, "  ");
				logger.log(Level.WARNING, "  ");
				logger.log(Level.WARNING, "  ");
				logger.log(Level.WARNING, "------------------------------------------------");
				logger.log(Level.WARNING, "Sector > Failed to connect to MySQL database");
				logger.log(Level.WARNING, "Sector > Shutting down server");
				logger.log(Level.WARNING, "------------------------------------------------");
				logger.log(Level.WARNING, "  ");
				logger.log(Level.WARNING, "  ");
				logger.log(Level.WARNING, "  ");
				Bukkit.getServer().shutdown();
				return;
			}
			memberManager = new MemberManager();
			
			Squad.databaseCheck();
			Entity.databaseCheck();
			Game.databaseCheck();
			Armour.databaseCheck();
			Shop.databaseCheck();
			SquadManager.load();
			ChannelManager.load();
			SeizureSheep.load();
			Commands.register();
			Listeners.register();
			TeamManager.checkOnlinePlayers();
			Shop.load();
			Game.loadWorlds();
			Game.fillSelector();
			abilityFactory = AbilityFactory.get();
			MapManager.load();
			Cooldown.startCooldownScheduler();
			SquadManager.startPowerScheduler();
			CombatTag.startCombatTagScheduler();
			ThrowItem.startScheduler();
			ServerManager.recordBuild();
			Item.fillMap();
			AbilityInfo.fillIds();
			Sector.loadAll();
			Member.reload();
			plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Ticker(), 1L, 1L);
			
			uc = new UnknownCommand();
			logger.log(Level.INFO, "Nucleus > enabled in " + (System.currentTimeMillis() - start) + "ms");
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Nucleus > Failed to enable, shutting down server");
			getServer().shutdown();
		}
	}
	
	public void onDisable() {
		long start = System.currentTimeMillis();
		Shop.killShops();
		SeizureSheep.stop();
		plugin = null;
		logger.log(Level.INFO, "Nucleus > disabled in " + (System.currentTimeMillis() - start) + "ms");
	}
	
	public static LogFile getServerLog() {
		return server_logs.get("server_log");
	}
	
	public static Main get() {
		return plugin;
	}

}
