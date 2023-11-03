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
import me.loogeh.Hype.Squads.SquadManager;
import me.loogeh.Hype.Squads.Squad.Relation;

public class Lumberjack extends Ability {

	public Lumberjack() {
		super("Lumberjack", AbilityInfo.LUMBERJACK);
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
					if(type.equals(Material.LOG) || type.equals(Material.LOG_2)) {

						AbilityUseEvent useEvent = new AbilityUseEvent(player, this);
						Main.get().getServer().getPluginManager().callEvent(useEvent);
						if(useEvent.isCancelled()) return;
						
						for(int x = 0; x < 2; x++) {
							for(int y = 0; y < 10; y++) {
								for(int z = 0; z < 2; z++) {
									Block rel = block.getRelative(x, y, z);
									Block negRel = block.getRelative(-x, y, -z);
									Block neutRel = block.getRelative(-x, y, z);
									Block neutRelInvert = block.getRelative(x, y, -z);
									Block cur = block.getRelative(0, y, 0);
									if(rel.getType().equals(type)) rel.breakNaturally();
									if(negRel.getType().equals(type)) negRel.breakNaturally();
									if(cur.getType().equals(type)) cur.breakNaturally();
									if(neutRel.getType().equals(type)) neutRel.breakNaturally();
									if(neutRelInvert.getType().equals(type)) neutRelInvert.breakNaturally();
								}
							}
						}
					}
				}
			}
		}
	}

}
