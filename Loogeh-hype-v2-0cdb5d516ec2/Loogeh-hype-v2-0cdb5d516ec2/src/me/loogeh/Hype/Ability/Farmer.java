package me.loogeh.Hype.Ability;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import me.loogeh.Hype.Armour.Ability;
import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Squads.Squad;
import me.loogeh.Hype.Squads.Squad.Relation;
import me.loogeh.Hype.Squads.SquadManager;

public class Farmer extends Ability {

	public Farmer() {
		super("Farmer", AbilityInfo.FARMER);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Squad squad = SquadManager.getSquad(player);
		Member member = Member.get(player);
		Block block = event.getBlock();
		Material type = block.getType();
		if(!member.getClasses().isUseable(getInfo())) return;
		if(SquadManager.isClaimed(block.getChunk())) {
			Squad owner = SquadManager.getOwner(block.getChunk());
			if(owner == null || squad == null) return;
			if(squad.getRelation(owner).equals(Relation.SELF)) {
				if(block.getType().equals(Material.PUMPKIN) || block.getType().equals(Material.MELON_BLOCK)) {
					for(int x = 0; x < 3; x++) {
						Block rel = block.getRelative(x, 0, 0);
						Block negRel = block.getRelative(-x, 0, 0);
						if(rel.getType().equals(type)) rel.breakNaturally();
						if(negRel.getType().equals(type)) negRel.breakNaturally();
					}
					for(int z = 0; z < 3; z++) {
						Block rel = block.getRelative(0, 0, z);
						Block negRel = block.getRelative(0, 0, -z);
						if(rel.getType().equals(type)) rel.breakNaturally();
						if(negRel.getType().equals(type)) negRel.breakNaturally();
					}
				}
			}
		}
	}

}
