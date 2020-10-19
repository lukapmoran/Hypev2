package me.loogeh.Hype.ChatChannels;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import me.loogeh.Hype.Main.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ChannelsFile {
	
	private String name;
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public ChannelsFile(String name) {
		this.name = name;
		customConfigFile = new File(Main.plugin.getDataFolder().getAbsolutePath() + "/" + name);
		if(!customConfigFile.exists()) {
			try {
				customConfigFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				Main.logger.log(Level.WARNING, "ChatChannels > Failed to create file " + name);
			}
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		InputStream defConfigStream = this.getClass().getResourceAsStream(name);
		if(defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		return this.customConfig;
	}
	
	public void save() {
		if(customConfig == null || customConfigFile == null) return;
		try {
			getConfig().save(customConfigFile);
		} catch(IOException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "ChatChannels > Failed to save file " + this.name);
		}
	}

	
	public void saveDefault() {
		if(customConfigFile == null) customConfigFile = new File(Main.plugin.getDataFolder().getAbsolutePath() + "/" + this.name);
		if(!customConfigFile.exists()) Main.plugin.saveResource(this.name, false);
	}
}
