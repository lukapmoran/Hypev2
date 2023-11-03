package me.loogeh.Hype.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.loogeh.Hype.Formatting.C;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MetaBuilder {

	private String display_name;
	private List<String> lore = new ArrayList<String>();
	private int maxLineLength = -1;
	private int maxDislayNameLength = -1;
	private boolean lookForSpace = false;
	private String color = "";

	public MetaBuilder() {}

	public MetaBuilder(int maxDisplayNameLength, int maxLineLength, boolean lookForSpace) {
		this.maxDislayNameLength = maxDisplayNameLength;
		this.maxLineLength = maxLineLength;
		this.lookForSpace = lookForSpace;
	}

	public MetaBuilder withName(String name) {
		if(maxDislayNameLength > -1) if(name.length() > maxDislayNameLength) name.substring(0, maxDislayNameLength);
		this.display_name = name;
		return this;
	}

	public MetaBuilder withLore(String lore) { //fix
		if(maxLineLength > -1)
			if(lore.length() > maxLineLength) {
				int timesToLong = (int) Math.ceil(lore.length() / maxLineLength);
				if(timesToLong == 1) {
					//if(lookForSpace) getFirstSpace(String text, int afterCharIndex)
					String secondLine = lore.substring(maxLineLength, lore.length());
					lore.substring(0, maxLineLength);
					this.lore.add(getColor() + lore);
					this.lore.add(getColor() + secondLine);
					return this;
				} else if(timesToLong > 1) {
					String[] lines = new String[timesToLong];
					for(int i = 0; i < timesToLong; i++) {
						if(getLookForSpace()) {
							if(lines[0] == null) {
								int index; 
								if(i < 1) index = lore.indexOf(" ", maxLineLength);
								else index = lore.indexOf(" ", i * maxLineLength);
								if(lore.length() < index || index < 0) lines[0] = getColor() + lore;
								else lines[0] = getColor() + lore.substring(0, index);
							} else {
								String cropee = "";
								if(i > 1) cropee = lore.substring((i - 1) * maxLineLength, lore.indexOf(" ", i * maxLineLength)); //cropee = crop ee > String getting cropped
								else cropee = lore.substring(maxLineLength, maxLineLength * 2);
								int index;
								if(i < 1) index = cropee.indexOf(" ", maxLineLength);
								else index = cropee.indexOf(" ", i * maxLineLength);
								if(cropee.length() < index || index < 0) lines[i] = getColor() + cropee;
								else lines[i] = getColor() + cropee.substring((i - 1) * maxLineLength, i * maxLineLength);
							}
						} else {
							int splitIndex = (i + 1) * timesToLong;
							if(lore.length() < splitIndex) lines[i] = getColor() + lore.substring(i * timesToLong, lore.length());
							else lines[i] = getColor() + lore.substring(i * timesToLong, splitIndex);
						}
					}
					if(lines.length > 0) this.lore.addAll(Arrays.asList(lines));
					return this;
				}

			}
		this.lore.add(getColor() + lore);
		return this;
	}

	public MetaBuilder withLore(String...lore) {
		this.lore.addAll(Arrays.asList(lore));
		return this;
	}

	public MetaBuilder withColor(String color) {
		this.color = color;
		return this;
	}

	public MetaBuilder withColor(ChatColor color) {
		this.color = C.COLOR_CHAR + color.getChar() + "";
		return this;
	}

	public MetaBuilder blank() {
		this.lore.add("");
		return this;
	}

	public MetaBuilder breaker(BreakerType type, boolean centered) {
		String breaker = "";
		int charCount = (int) (maxLineLength / type.getLength());
		for(int i = 0; i < charCount; i++) {
			breaker = breaker + type.getSequence();
		}
		String spaces = "";
		if(centered) {
			int startSpaces = ((int) (charCount / 2) / 2);
			for(int i = 0; i < startSpaces; i++) {
				spaces = spaces + " ";
			}
		}
		this.lore.add(spaces + getColor() + breaker + spaces);
		return this;
	}

	public MetaBuilder breaker(BreakerType type, int offset) {
		if(type.equals(BreakerType.HYPHEN)) {
			String breaker = "";
			for(int i = 0; i < maxLineLength + offset; i++) {
				breaker = breaker + "-";
			}
			this.lore.add(getColor() + breaker);
		}
		return this;
	}

	public List<String> getLore() {
		return this.lore;
	}

	public String getDisplayName() {
		return this.display_name;
	}

	public boolean getLookForSpace() {
		return this.lookForSpace;
	}

	private String getColor() {
		return this.color;
	}

	public ItemMeta toItemMeta(ItemMeta cur) {
		ItemMeta meta = cur;
		if(meta == null) {
			ItemStack blank = new ItemStack(Material.AIR);
			meta = blank.getItemMeta();
		}
		if(getDisplayName() != null) meta.setDisplayName(getDisplayName());
		if(!getLore().isEmpty()) meta.setLore(getLore());
		return meta;
	}

	public enum BreakerType { //the higher the length, the less the characters
		HYPHEN("-", 1.3F),
		STRIKETHROUGH(ChatColor.STRIKETHROUGH + "-", 1.0F),
		UNDERLINE(ChatColor.UNDERLINE + " ", 0.75F),
		BOLD_STRIKETHROUGH(C.boldStrikethrough + "-", 1.32F),
		SQUARE("▅", 1.8F),
		SQUARE_STRIKETHROUGH(ChatColor.STRIKETHROUGH + "▅", 1.8F),
		SQUARE_UNDERLINE(ChatColor.UNDERLINE + "▅", 1.8F);

		private String sequence;
		private float length;

		BreakerType(String sequence, float length) {
			this.sequence = sequence;
			this.length = length;
		}

		public String getSequence() {
			return this.sequence;
		}

		public float getLength() {
			return this.length;
		}
	}

	public enum LoreLength {
		TINY(10),
		SMALL(30),
		MEDIUM(50),
		LARGE(70),
		HUGE(100);

		private int length;

		LoreLength(int length) {
			this.length = length;
		}

		public int getLength() {
			return this.length;
		}
	}

}
