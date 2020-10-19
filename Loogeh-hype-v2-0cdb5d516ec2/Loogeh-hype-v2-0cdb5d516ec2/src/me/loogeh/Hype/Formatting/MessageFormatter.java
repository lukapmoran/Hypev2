package me.loogeh.Hype.Formatting;

import org.bukkit.ChatColor;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class MessageFormatter {
	
	private String rawFormat = null;
	private String[] replacersList = null;
	private String[] replacementsList = null;
	
	public MessageFormatter(String rawFormat, String[] replacersList, String[] replacementsList) {
		this.rawFormat = rawFormat;
		this.replacersList = replacersList;
		this.replacementsList = replacementsList;
		if(replacersList.length != replacementsList.length) {
			try {
				throw new IllegalArgumentException("The amount of replacers is not the same as the amount of replacements");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getRawFormat() {
		return this.rawFormat;
	}
	
	public String[] getReplacers() {
		return this.replacersList;
	}
	
	public String[] getReplacements() {
		return this.replacementsList;
	}
	
	public void setRawFormat(String rawFormat) {
		this.rawFormat = rawFormat;
	}
	
	public void setReplacers(String[] replacersList) {
		this.replacersList = replacersList;
	}
	
	public void setReplacements(String[] replacementsList) {
		this.replacementsList = replacementsList;
	}
	
	public void addReplacers(String... replacers) {
		replacersList = ArrayUtils.addAll(getReplacers(), replacers);
	}
	
	public void addRepalcements(String... replacements) {
		replacementsList = ArrayUtils.addAll(getReplacements(), replacements);
	}
	
	public String format() {
		String formattedMessage = getRawFormat();
		formattedMessage = ChatColor.translateAlternateColorCodes('&', formattedMessage);
		for(int i = 0; i < replacersList.length; i++) {
			formattedMessage = StringUtils.replace(formattedMessage, replacersList[i], replacementsList[i]);
		}
		return formattedMessage;
	}

}
