package me.loogeh.Hype.Miscellaneous;

import me.loogeh.Hype.Armour.ArmourCommands;
import me.loogeh.Hype.ChatChannels.ChannelCommands;
import me.loogeh.Hype.Entity.EntityCommands;
import me.loogeh.Hype.Games.GameCommands;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Player.PlayerCommands;
import me.loogeh.Hype.Server.ServerCommands;
import me.loogeh.Hype.Shops.ShopsCommands;
import me.loogeh.Hype.Squads.SquadsCommands;

public class Commands {
	
	public static void register() {
		ChannelCommands ccmds = new ChannelCommands();
		SquadsCommands scmds = new SquadsCommands();
		EntityCommands encmds = new EntityCommands();
		PlayerCommands pcmds = new PlayerCommands();
		ServerCommands sercmds = new ServerCommands();
		ShopsCommands shopcmds = new ShopsCommands();
		ArmourCommands arcmds = new ArmourCommands();
		GameCommands gcmds = new GameCommands();
		Main.plugin.getCommand("chatchannel").setExecutor(ccmds);
		Main.plugin.getCommand("chatchannels").setExecutor(ccmds);
		Main.plugin.getCommand("cc").setExecutor(ccmds);
		Main.plugin.getCommand("s").setExecutor(scmds);
		Main.plugin.getCommand("squad").setExecutor(scmds);
		Main.plugin.getCommand("squads").setExecutor(scmds);
		Main.plugin.getCommand("sa").setExecutor(scmds);
		Main.plugin.getCommand("server").setExecutor(sercmds);
		Main.plugin.getCommand("give").setExecutor(sercmds);
		Main.plugin.getCommand("g").setExecutor(sercmds);
		Main.plugin.getCommand("entity").setExecutor(encmds);
		Main.plugin.getCommand("p").setExecutor(pcmds);
		Main.plugin.getCommand("player").setExecutor(pcmds);
		Main.plugin.getCommand("shops").setExecutor(shopcmds);
		Main.plugin.getCommand("armour").setExecutor(arcmds);
		Main.plugin.getCommand("game").setExecutor(gcmds);
	}

}
