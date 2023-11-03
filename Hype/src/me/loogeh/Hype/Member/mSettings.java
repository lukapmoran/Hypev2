package me.loogeh.Hype.Member;

import me.loogeh.Hype.Formatting.MessageFormatter;
import me.loogeh.Hype.Sector.Sector;

public class mSettings extends Sector {
	
	private boolean chatBleep = false;
	
	private boolean deathRelationColours = false;
	
	private String messageFormat = "&5&l%sender% &d&l> &5&l%target% &d&l%message%";
	private String[] messageReplacers = new String[]{"%sender%", "%target%", "%message%"};
	
	private String chatFormat = "&darkrel%squad% &rel%sender% &f%message%";
	private String[] chatReplacers = new String[]{"%squad%", "%sender%", "%message%"};

	private String ccFormat = "&2%channelname% &a%sender% &2> &a%message%";
	private String[] ccReplacers = new String[]{"%channelname%", "%sender%", "%message%"};
	
	private String serverJoin = "&8&lJoin > &7%player%";
	private String serverLeave = "&8&lQuit > &7%player%";
	private String serverTaggedLeave = "&8&lTagged Quit > &7%player%";
	private String[] serverReplacers = new String[]{"%player%"};
	
	private String abilityMessage = "&1Ability - &fYou used &e%ability%";
	private String[] abilityReplacers = new String[]{"%ability%"};
	
	private String cooldownComplete = "&1Cooldown - &fYou can now use &e%cooldown%";
	private String[] cooldownReplacers = new String[]{"%cooldown%"};
	
	public mSettings() {
		super("Settings");
	}
	
	public boolean isChatBleep() {
		return chatBleep;
	}
	
	public void setChatBleep(boolean chatBleep) {
		this.chatBleep = chatBleep;
	}
	
	public boolean isDeathRelationColours() {
		return deathRelationColours;
	}
	
	public void setDeathRelationColours(boolean deathRelationColours) {
		this.deathRelationColours = deathRelationColours;
	}
	
	public String getPersonalMessageFormat() {
		return this.messageFormat;
	}
	
	public void setPersonalMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}
	
	public String getChatFormat() {
		return chatFormat;
	}

	public void setChatFormat(String chatFormat) {
		this.chatFormat = chatFormat;
	}
	
	public String[] getChatReplacers() {
		return chatReplacers;
	}

	public void setChatReplacers(String[] chatReplacers) {
		this.chatReplacers = chatReplacers;
	}

	public String getCCFormat() {
		return ccFormat;
	}

	public void setCCFormat(String ccFormat) {
		this.ccFormat = ccFormat;
	}

	public String[] getCCReplacers() {
		return ccReplacers;
	}

	public void setCCReplacers(String[] ccReplacers) {
		this.ccReplacers = ccReplacers;
	}

	public String getServerJoin() {
		return serverJoin;
	}

	public void setServerJoin(String serverJoin) {
		this.serverJoin = serverJoin;
	}

	public String getServerLeave() {
		return serverLeave;
	}

	public void setServerLeave(String serverLeave) {
		this.serverLeave = serverLeave;
	}

	public String getServerTaggedLeave() {
		return serverTaggedLeave;
	}

	public void setServerTaggedLeave(String serverTaggedLeave) {
		this.serverTaggedLeave = serverTaggedLeave;
	}

	public String[] getServerReplacers() {
		return serverReplacers;
	}

	public void setServerReplacers(String[] serverReplacers) {
		this.serverReplacers = serverReplacers;
	}
	
	public String getAbilityMessage() {
		return abilityMessage;
	}

	public void setAbilityMessage(String abilityMessage) {
		this.abilityMessage = abilityMessage;
	}

	public String[] getAbilityReplacers() {
		return abilityReplacers;
	}

	public void setAbilityReplacers(String[] abilityReplacers) {
		this.abilityReplacers = abilityReplacers;
	}

	public String getCooldownComplete() {
		return cooldownComplete;
	}

	public void setCooldownComplete(String cooldownComplete) {
		this.cooldownComplete = cooldownComplete;
	}

	public String[] getCooldownReplacers() {
		return cooldownReplacers;
	}

	public void setCooldownReplacers(String[] cooldownReplacers) {
		this.cooldownReplacers = cooldownReplacers;
	}

	public String getPersonalMessage(String sender, String target, String message) {
		MessageFormatter formatter = new MessageFormatter(messageFormat, messageReplacers, new String[]{sender, target, message});
		System.out.println("message");
		return formatter.format();
	}
	
	public String getCCMessage(String chatChannel, String sender, String message) {
		MessageFormatter formatter = new MessageFormatter(ccFormat, ccReplacers, new String[]{chatChannel, sender, message});
		return formatter.format();
	}
	
	public String getJoinMessage(String player) {
		MessageFormatter formatter = new MessageFormatter(serverJoin, serverReplacers, new String[]{player});
		return formatter.format();
	}
	
	public String getLeaveMessage(String player) {
		MessageFormatter formatter = new MessageFormatter(serverLeave, serverReplacers, new String[]{player});
		return formatter.format();
	}
	
	public String getAbilityMessage(String ability) {
		MessageFormatter formatter = new MessageFormatter(abilityMessage, abilityReplacers, new String[]{ability});
		return formatter.format();
	}
	
	public String getCooldownMessage(String cooldown) {
		MessageFormatter formatter = new MessageFormatter(cooldownComplete, cooldownReplacers, new String[]{cooldown});
		return formatter.format();
	}
	
	public void reset() {
		setPersonalMessageFormat("&5&l%sender% &d&l> &5&l%target% &d&l%message%");
		setChatFormat("&darkrel%squad% &rel%sender% &f%message%");
		setCCFormat("&2%channelname% &a%sender% &2> &a%message%");
		setServerJoin("&8&lJoin > &7%player%");
		setServerLeave("&8&lQuit > &7%player%");
		setServerTaggedLeave("&8&lTagged Quit > &7%player%");
		setAbilityMessage("&1Ability - &fYou used &e%ability%");
		setCooldownComplete("&1Cooldown - &fYou can now use &e%cooldown%");
	}
	
	public void save() {
		
	}

	public void load() {
		//create table if not exists
	}
}
