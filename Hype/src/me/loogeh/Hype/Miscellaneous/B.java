package me.loogeh.Hype.Miscellaneous;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class B {

	@SuppressWarnings("deprecation")
	public static boolean isBlock(Block block, Material material, byte data) {
		return block.getType() == material && block.getData() == (byte) data;
	}
	
}
