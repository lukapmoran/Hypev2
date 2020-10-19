package me.loogeh.Hype.Utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class utilMath {
	public static Random rand = new Random();

	public static int getRandom(int min, int max) {
		return rand.nextInt(max - min) + min;
	}
	
	public static int r(int max) {
		return rand.nextInt(max);
	}
	
	public static double r(double max, double interval) {
		double[] array = new double[(int) ((int) max / interval)];
		int count = 0;
		for(double i = 0.0; i < array.length - 1; i += interval) {
			array[count] = i;
			count++;
		}
		return array[getRandom(1, array.length - 1)];
	}
	
	public static double rD(double max, double min) {
		   double r = Math.random();
		   if (r < 0.5) {
		      return ((1 - Math.random()) * (max - min) + min);
		   }
		   return (Math.random() * (max - min) + min);
		}

	public static boolean getChance(int percent) {
		return rand.nextInt(100) <= percent;
	}

	public static double trim(double unrounded, int precision) {
		String format = "#.#";

		for(int i = 1; i < precision; i++) {
			format = format + "#";
		}
		DecimalFormat twoDec = new DecimalFormat(format);
		return Double.valueOf(twoDec.format(unrounded)).doubleValue();
	}

	public static List<Location> getSphere(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
		List<Location> circleblocks = new ArrayList<Location>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
						Location l = new Location(loc.getWorld(), x, y + plus_y, z);
						circleblocks.add(l);
					}
				}
			}
		}
		return circleblocks;
	}
	
	public static double getPercentage(double part, double whole, int trim) {
		return trim((part / whole) * 100, trim);
	}
	
	public static int round(int target, PlaceValue targetPlaceValue) {
		return Math.round(target / targetPlaceValue.getValue()) * targetPlaceValue.getValue();
	}
	
	public enum PlaceValue {
		UNITS(1),
		TENS(10),
		HUNDREDS(100),
		THOUSANDS(1000);
		
		private int value;
		
		PlaceValue(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
	}
	
}
