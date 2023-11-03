package me.loogeh.Hype.Games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Formatting.M;
import me.loogeh.Hype.Games.Game.GameType;
import me.loogeh.Hype.Inventory.HInventory;
import me.loogeh.Hype.Inventory.IFlag;
import me.loogeh.Hype.Utility.util;
import me.loogeh.Hype.Utility.utilItem.HeadType;

public class GameInventory extends HInventory {

	private GameType type;
	private GIType itype;
	
	public GameInventory(GIType itype, GameType type, int rows) {
		super(C.boldGreen + itype.getTitle(), rows);
		this.type = type;
		this.itype = itype;
		this.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
	}
	
	public GameType getGameType() {
		return this.type;
	}
	
	public GIType getGIType() {
		return this.itype;
	}
	
	static class GameCreator extends GameInventory {
		
		private Map selectedMap;
		private int mapIndex;
		private final List<Map> possibleMaps;
		
		public GameCreator(GameType type) {
			super(GIType.MATCH_CREATOR, type, 6);
			possibleMaps = MapManager.getMaps(type);
			if(possibleMaps.size() > 0) {
				this.selectedMap = possibleMaps.get(0);
				this.mapIndex = 0;
			}
			else {
				for(HumanEntity h_ent : this.getInventory().getViewers()) {
					Player player = (Player) h_ent;
					if(player != null) {
						player.closeInventory();
						M.message(player, "Game", ChatColor.WHITE + "Found 0 possible maps for " + ChatColor.GREEN + type.getName());
					}
				}
				return;
			}
			this.addFlag(IFlag.DISABLE_CLICK_TOP_AND_BOTTOM);
			update();
		}
		
		public Map getSelectedMap() {
			return this.selectedMap;
		}
		
		public List<Map> getPossibleMaps() {
			return this.possibleMaps;
		}
		
		public void setSelectedMap() {
			
		}
		
		public void update() {
			if(getGameType().equals(GameType.CTF)) {
				if(selectedMap == null) {
					if(possibleMaps.size() < 1) {
						this.remove();
						return;
					} else selectedMap = possibleMaps.get(0);
				}
				for(int i = 1; i <8; i++) this.setItem(Material.GOLD_BLOCK, C.boldAqua + this.getGameType().getName(), i);
				this.setItem(Material.EMPTY_MAP, C.boldGreen + selectedMap.getName(), 13, C.boldYellow + "Min Players " + ChatColor.WHITE + selectedMap.getMinimumPlayers(), C.boldYellow + "Max Players " + ChatColor.WHITE + selectedMap.getPlayerLimit());
				List<String> map_change_lore = getMapChangerLore();
				this.setItem(Material.EMERALD_BLOCK, C.boldGreen + "Next Map", 17, util.getArrayFromList(map_change_lore));
				this.setItem(Material.REDSTONE_BLOCK, C.boldGreen + "Previous Map", 9, util.getArrayFromList(map_change_lore));
				
				this.setItem(new ItemStack(Material.SKULL_ITEM, selectedMap.getPlayerLimit(), HeadType.PLAYER.getData()), C.boldGreen + "Max Players", 22);
				this.setItem(Material.EMERALD_BLOCK, C.boldGreen + "Increase", 26);
				this.setItem(Material.REDSTONE_BLOCK, C.boldGreen + "Decrease", 18);
				
				this.setItem(Material.EMERALD, C.boldGreen + "Use PowerUps", 31);
				this.setItem(Material.EMERALD_BLOCK, C.boldGreen + "True", 35);
				this.setItem(Material.REDSTONE_BLOCK, C.boldGreen + "False", 27);

				this.setItem(new ItemStack(Material.GOLD_INGOT, getGameType().getMinWinScore()), C.boldGreen + "Win Score", 40);
				this.setItem(Material.EMERALD_BLOCK, C.boldGreen + "Increase Score", 44);
				this.setItem(Material.REDSTONE_BLOCK, C.boldGreen + "Decrease Score", 36);				
				
				this.setItem(Material.ENCHANTMENT_TABLE, C.boldAqua + "Create Match", 8); //change back to 53 Failed to handle packet error is fixed
				this.setItem(Material.TNT, C.boldRed + "Abort", 0); //change back to 45 once Failed to handle packet error is fixed
				
				//add cost adjuster?
			}
		}
		
		public List<String> getMapChangerLore() {
			List<String> lore = new ArrayList<String>();
			for(Map maps : possibleMaps) {
				if(maps == selectedMap) lore.add(ChatColor.GREEN + maps.getName());
				else lore.add(ChatColor.GRAY + maps.getName());
			}
			return lore;
		}
		
		public Map nextMap() { //test
			if(possibleMaps.size() > 0) {
				if(possibleMaps.size() >= this.mapIndex + 1) {
					Map newMap = possibleMaps.get(mapIndex + 1);
					this.selectedMap = newMap;
					this.mapIndex++;
				} else {
					Map newMap = possibleMaps.get(0);
					if(newMap == this.selectedMap) return selectedMap;
					this.selectedMap = newMap;
					this.mapIndex = 0;
				}
				update();
			}
			return selectedMap;
		}
		
		public Map prevMap() { //test > finish
			if(possibleMaps.size() > 0) {
				if(possibleMaps.size() >= this.mapIndex - 1) {
					Map newMap = possibleMaps.get(mapIndex + 1);
					this.selectedMap = newMap;
					this.mapIndex++;
				} else {
					Map newMap = possibleMaps.get(0);
					if(newMap == this.selectedMap) return selectedMap;
					this.selectedMap = newMap;
					this.mapIndex = 0;
				}
				update();
			}
			return selectedMap;
		}
	}
	
	public enum GIType { 
		MATCH_SELECTOR("Select Match"),
		GAME_SELECTOR("Select Game"),
		MATCH_CREATOR("Create Match"),
		KIT_SELECTOR("Select Kit"),
		BUILD_SELECTOR("Select Build");
		
		private String title = "";
		
		GIType(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return this.title;
		}
	}
}
