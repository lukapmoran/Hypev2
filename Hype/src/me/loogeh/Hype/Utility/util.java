package me.loogeh.Hype.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class util {
	
	public static String search(String searchToken, List<String> possibilities) { //test
		if(searchToken == null || possibilities == null) return null;
		
		String found = null;
		int delta = Integer.MAX_VALUE;
		for(String possibility : possibilities) {
			if(possibility.toLowerCase().startsWith(searchToken.toLowerCase())) {
				int curDelta = possibility.length() - searchToken.length();
				if(curDelta < delta) {
					found = possibility;
					delta = curDelta;
				}
				if(curDelta == 0) break;
			}
		}
		return found;
	}
	
	public static String search(String searchToken, Set<String> possibilities) {
		if(searchToken == null || possibilities == null) return null;
		
		String found = null;
		int delta = Integer.MAX_VALUE;
		for(String possibility : possibilities) {
			if(possibility.toLowerCase().startsWith(searchToken.toLowerCase())) {
				int curDelta = possibility.length() - searchToken.length();
				if(curDelta < delta) {
					found = possibility;
					delta = curDelta;
				}
				if(curDelta == 0) break;
			}
		}
		return found;
	}
	
	public static boolean containsSpecialChar(String input) {
		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()) return true;
		else return false;
	}
	
	public static String join(int start, String[] array) {
		String string = "";
		for(int i = start; i < array.length; i++) {
			string = string + array[i] + " ";
		}
		return string.substring(0, string.length() - 1);
	}
	
	public static List<String> getListFromString(String list, String split_char) {
		ArrayList<String> arrayList = new ArrayList<String>();
		if(list == null) {
			arrayList.add("");
			return arrayList;
		}
		if(!list.contains(split_char)) {
			arrayList.add(list);
			return arrayList;
		}
		String[] split = list.split(split_char);
		for(String elem : split) {
			arrayList.add(elem);
		}
		return arrayList;
	}
	
	public static boolean arrayContains(String inputString, String[] items) {
		for(int i = 0; i < items.length; i++) {
			if(inputString.toLowerCase().equalsIgnoreCase(items[i].toLowerCase())) return true;
		}
		return false;
	}

	public static void subtractItem(Player player, int amount) {
		if(player.getItemInHand().getAmount() <= amount) player.setItemInHand(null);
		else player.getItemInHand().setAmount(player.getItemInHand().getAmount() - amount);
	}
	
	public static String getRomanNumeral(int number) {
		if(number < 1) return "";
		if(number == 1) return "I";
		else if(number == 2) return "II";
		else if(number == 3) return "III";
		else if(number == 4) return "IV";
		else if(number == 5) return "V";
		else if(number == 6) return "VI";
		else if(number == 7) return "VII";
		else if(number == 8) return "VIII";
		else if(number == 9) return "IX";
		else if(number == 10) return "X";
		return "";
	}
	
	public static Integer getInteger(String parse) {
		Integer integer = null;
		try {
			integer = Integer.parseInt(parse);
		} catch (NumberFormatException e) {
			return null;
		}
		return integer;
	}
	
	public static int getNextFreeSlot(Inventory inventory, int start_slot) { //start_slot starts at 1
		for(int i = start_slot; i < inventory.getSize() - 1; i++) {
			if(inventory.getItem(i) == null) return i;
		}
		return -1;
	}
	
	public static String[] getArrayFromList(List<String> list) {
		String[] array = new String[list.size()];
		if(list.size() == 0) return array;
		int size = list.size() - 1;
		if(size == 0) size = 1;
		for(int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
