package me.loogeh.Hype.World;

import me.loogeh.Hype.Utility.utilMath;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class BlockLetter {

	public int columns = 4;
	public int rows = 5;
	public char character;
	public char[] structure = new char[20]; //change array length to make for more accurate structures?

	public BlockLetter(int columns, int rows, char character, char[] structure) {
		this.columns = columns;
		this.rows = rows;
		this.character = character;
		this.structure = structure;
	}
	
	public BlockLetter(int columns, char character, char[] structure) {
		this.columns = columns;
		this.character = character;
		this.structure = structure;
	}

	@SuppressWarnings("deprecation")
	public void spawn(Location location, ItemStack[] blockOptions, Axis baseAxis, Axis sideAxis, int direction) {
		if(baseAxis.equals(sideAxis)) return;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				boolean setBlock = structure[(i * columns) + j] == '1' ? true : false;
				Location blockLoc = location.clone();
				if(direction == 1) {
					if(baseAxis.equals(Axis.Z)) blockLoc.setZ(location.getBlockZ() + j);
					else if(baseAxis.equals(Axis.Y)) blockLoc.setY(location.getBlockY() + j);
					else if(baseAxis.equals(Axis.X)) blockLoc.setX(location.getBlockX() + j);
				} else if(direction == -1) {
					if(baseAxis.equals(Axis.Z)) blockLoc.setZ(location.getBlockZ() - j);
					else if(baseAxis.equals(Axis.Y)) blockLoc.setY(location.getBlockY() - j);
					else if(baseAxis.equals(Axis.X)) blockLoc.setX(location.getBlockX() - j);
				}
				
				
				if(sideAxis.equals(Axis.Y)) blockLoc.setY(location.getBlockY() - i);
				else if(sideAxis.equals(Axis.X)) blockLoc.setX(location.getBlockX() - i);
				else if(sideAxis.equals(Axis.Z)) blockLoc.setZ(location.getBlockZ() - i);
				
				if(setBlock) {
					ItemStack block;
					block = blockOptions.length > 1 ? blockOptions[utilMath.r(blockOptions.length - 1)] : blockOptions[0];
					blockLoc.getBlock().setType(block.getType());
					blockLoc.getBlock().setData((byte) block.getDurability());
				}
			}
		}
	}
	
	public enum Axis {
		X, Y, Z;
	}
	
	public enum LetterStructure {
		A(new BlockLetter(4, 'A',
				new char[] {
				'0','1','1','0',
				'1','0','0','1',
				'1','1','1','1',
				'1','0','0','1',
				'1','0','0','1',
				'1','0','0','1'})),
		B(new BlockLetter(4, 'B',
				new char[] {
				'1','1','1','0',
				'1','0','0','1',
				'1','1','1','1',
				'1','0','0','1',
				'1','1','1','0'})), 
		C(new BlockLetter(4, 'C',
				new char[] {
				'1','1','1','1',
				'1','0','0','0',
				'1','0','0','0',
				'1','0','0','0',
				'1','1','1','1'})),
		D(new BlockLetter(4, 'D',
				new char[] {
				'1','1','1','0',
				'1','0','0','1',
				'1','0','0','1',
				'1','0','0','1',
				'1','1','1','0'})),
		E(new BlockLetter(3, 'E',
				new char[] {
				'1','1','1',
				'1','0','0',
				'1','1','1',
				'1','0','0',
				'1','1','1'})),
		F(new BlockLetter(3, 'F',
				new char[] {
				'1','1','1',
				'1','0','0',
				'1','1','1',
				'1','0','0',
				'1','0','0'})),
		G(new BlockLetter(4, 'G',
				new char[] {
				'1','1','1','1',
				'1','0','0','0',
				'1','0','1','1',
				'1','0','0','1',
				'1','1','1','1'})),
		H(new BlockLetter(4, 'H',
				new char[] {
				'1','0','0','1',
				'1','0','0','1',
				'1','1','1','1',
				'1','0','0','1',
				'1','0','0','1'})),
		I(new BlockLetter(3, 'I', new char[] {
				'1','1','1',
				'0','1','0',
				'0','1','0',
				'0','1','0',
				'1','1','1'})),
		J(new BlockLetter(4, 'J',
				new char[] {
				'0','0','0','1',
				'0','0','0','1',
				'0','0','0','1',
				'1','0','0','1',
				'1','1','1','1'})),
		K(new BlockLetter(3, 'K',
				new char[] {
				'1','0','1',
				'1','0','1',
				'1','1','0',
				'1','0','1',
				'1','0','1'})),
		L(new BlockLetter(4, 'L',
				new char[] {
				'1','0','0','0',
				'1','0','0','0',
				'1','0','0','0',
				'1','0','0','0',
				'1','1','1','1'})),
		M(new BlockLetter(5, 'M',
				new char[] {
				'1','0','0','0','1',
				'1','1','0','1','1',
				'1','0','1','0','1',
				'1','0','0','0','1',
				'1','0','0','0','1',})),
		N(new BlockLetter(5, 'N',
				new char[] {
				'1','0','0','0','1',
				'1','1','0','0','1',
				'1','0','1','0','1',
				'1','0','0','1','1',
				'1','0','0','0','1'})),
		O(new BlockLetter(5, 'O',
				new char[] {
				'0','1','1','1','0',
				'1','0','0','0','1',
				'1','0','0','0','1',
				'1','0','0','0','1',
				'0','1','1','1','0'})),
		P(new BlockLetter(3, 'P',
				new char[] {
				'1','1','1',
				'1','0','1',
				'1','1','1',
				'1','0','0',
				'1','0','0'})),
		Q(new BlockLetter(5, 'Q',
				new char[] {
				'1','1','1','1','0',
				'1','0','0','1','0',
				'1','0','0','1','0',
				'1','0','1','1','1',
				'1','1','1','1','0'})),
		R(new BlockLetter(3, 'R',
				new char[] {
				'1','1','1',
				'1','0','1',
				'1','1','0',
				'1','0','1',
				'1','0','1'})),
		S(new BlockLetter(3, 'S', 
				new char[] {
				'1','1','1',
				'1','0','0',
				'1','1','1',
				'0','0','1',
				'1','1','1'})),
		T(new BlockLetter(3, 'T', 
				new char[] {
				'1','1','1',
				'0','1','0',
				'0','1','0',
				'0','1','0',
				'0','1','0'})),
		U(new BlockLetter(4, 'U',
				new char[] {
				'1','0','0','1',
				'1','0','0','1',
				'1','0','0','1',
				'1','0','0','1',
				'1','1','1','1'})),
		V(new BlockLetter(3, 'V', 
				new char[] {
				'1','0','1',
				'1','0','1',
				'1','0','1',
				'1','0','1',
				'0','1','0'
		})),
		W(new BlockLetter(5, 'W',
				new char[] {
				'1','0','0','0','1',
				'1','0','0','0','1',
				'1','0','0','0','1',
				'1','0','1','0','1',
				'1','1','1','1','1',
				
		})),
		X(new BlockLetter(3, 'X',
				new char[] {
				'1','0','1',
				'1','0','1',
				'0','1','0',
				'1','0','1',
				'1','0','1'})),
		Y(new BlockLetter(3, 'Y',
				new char[] {
				'1','0','1',
				'1','0','1',
				'1','1','1',
				'0','1','0',
				'0','1','0'})),
		Z(new BlockLetter(3, 'Z',
				new char[] {
				'1','1','1',
				'0','0','1',
				'0','1','0',
				'1','0','0',
				'1','1','1'})),
		SPACE(new BlockLetter(2, ' ', 
				new char[] {
				'0','0',
				'0','0',
				'0','0',
				'0','0',
				'0','0'})),
		PERIOD(new BlockLetter(1, '.', new char[] {
				'0',
				'0',
				'0',
				'0',
				'1',}));

		private BlockLetter blockLetter;

		LetterStructure(BlockLetter blockLetter) {
			this.blockLetter = blockLetter;
		}
		
		
		public BlockLetter getBlockLetter() {
			return this.blockLetter;
		}
		
		public static BlockLetter getBlockLetter(char character) {
			for(LetterStructure structure : values()) {
				if(structure.getBlockLetter().character == character) return structure.getBlockLetter();
			}
			return null;
		}

	}

}
