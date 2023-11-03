package me.loogeh.Hype.World;

import me.loogeh.Hype.World.BlockLetter.Axis;
import me.loogeh.Hype.World.BlockLetter.LetterStructure;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class SentenceBuilder {

	private String sentence;
	private int spacing = 1;

	public SentenceBuilder(String sentence, int spacing) {
		this.sentence = sentence;
		this.spacing = spacing;
	}

	public SentenceBuilder(String sentence) {
		this.sentence = sentence;
	}

	public String getSentence() {
		return this.sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public int getSpacing() {
		return this.spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public void construct(Location location, ItemStack[] blockOptions, Axis baseAxis, Axis sideAxis, int direction) {
		char[] charArray = getSentence().toUpperCase().toCharArray();
		for(char character : charArray) {
			BlockLetter bLetter = LetterStructure.getBlockLetter(character);
			if(bLetter != null) {
				bLetter.spawn(location, blockOptions, baseAxis, sideAxis, direction);
				if(direction == 1) {
					if(baseAxis.equals(Axis.Z)) location.setZ(location.getBlockZ() + (bLetter.columns + getSpacing()));
					else if(baseAxis.equals(Axis.Y)) location.setY(location.getBlockY() + (bLetter.columns + getSpacing()));
					else if(baseAxis.equals(Axis.X)) location.setX(location.getBlockX() + (bLetter.columns + getSpacing()));
				} else if(direction == -1) {
					if(baseAxis.equals(Axis.Z)) location.setZ(location.getBlockZ() - (bLetter.columns + getSpacing()));
					else if(baseAxis.equals(Axis.Y)) location.setY(location.getBlockY() - (bLetter.columns + getSpacing()));
					else if(baseAxis.equals(Axis.X)) location.setX(location.getBlockX() - (bLetter.columns + getSpacing()));
				}
			}
		}
	}
}
