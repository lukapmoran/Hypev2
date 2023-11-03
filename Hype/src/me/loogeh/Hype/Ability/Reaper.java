package me.loogeh.Hype.Ability;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Event.AbilityUseEvent;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.Squad.Relation;
import me.loogeh.Hype.Squads.SquadManager;

public class Reaper extends Ability {

	public Reaper() {
		super("Reaper", AbilityInfo.REAPER);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Squad squad = SquadManager.getSquad(player);
		Member member = Member.get(player);
		if(!member.getClasses().isUseable(getInfo())) return;
		if(!getInfo().getSet().equals(Armour.getKit(player))) return;
		Block block = event.getBlock(); 
		Material type = block.getType();
		if(SquadManager.isClaimed(block.getChunk())) {
			Squad owner = SquadManager.getOwner(block.getChunk());
			if(squad != null && owner != null) {
				if(squad.getRelation(owner).equals(Relation.SELF)) {
					if(type.equals(Material.WHEAT)) {

						AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
						Main.get().getServer().getPluginManager().callEvent(useEvent);
						if(useEvent.isCancelled()) return;
						
						for(int x = 0; x < 3; x++) {
							for(int z = 0; z < 3; z++) {
								Block rel = block.getRelative(x, 0, z);
								if(rel.getType().equals(Material.WHEAT)) rel.breakNaturally();
							}
						}
					}
				}
			}
		}
	}

}
