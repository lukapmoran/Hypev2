package me.loogeh.Hype.Miscellaneous;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class FuzzySearch {
	public static HashMap<String,String[]> searchers = new HashMap<String,String[]>();


	public static void addSearcher(String id, String[] words){
		searchers.put(id,words);
	}

	public static void removeSearcher(String id){
		searchers.remove(id);
	}	
	
	public static String nextID() {
		return (searchers.size() + 1) + "";
	}
	
	public static void addWords(String id, String... wordsToAdd){
		String[] currentWords = searchers.get(id);
		//create new larger array and copy over old data
		String[] words = new String[currentWords.length+wordsToAdd.length];
		for(int i=0;i<currentWords.length;i++){
			words[i]=currentWords[i];
		}
		//add new words
		for(int i=0;i<wordsToAdd.length;i++){
			words[i+currentWords.length]=wordsToAdd[i];
		}
		searchers.put(id, currentWords);
	}

	public static String[] getWords(String id){
		return searchers.get(id);
	}

	public static String getClosestWord(String id, String search){
		String closest_so_far = null;
		int closest_lev_so_far = 5;
		for(String word : searchers.get(id)){
			int distance = StringUtils.getLevenshteinDistance(word.toLowerCase(), search.toLowerCase());
			if(distance < closest_lev_so_far && distance < 5){
				closest_lev_so_far = distance;
				closest_so_far = word;
			}
		}

		return closest_so_far;
	}

	public static String[] getClosestWords(String id, final String search){

		String[] words = searchers.get(id);

		Arrays.sort(words,new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				int d1 = StringUtils.getLevenshteinDistance(search, o1);
				int d2 = StringUtils.getLevenshteinDistance(search, o2);
				return Integer.signum(d1 - d2);
			}
		});

		return words;
	}
	
	public static String getClosestWord2(String id, String token) {
		String[] words = searchers.get(id);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(String word : words) {
			int lev = StringUtils.getLevenshteinDistance(word.toLowerCase(), token.toLowerCase());
			map.put(word, lev);
		}
		String closest = null;
		double closest_lev = 0.0D;
		for(Entry<String, Integer> entry : map.entrySet()) {
			double percent = 1 - ((entry.getValue() / getLongestStringLength(id)) * 100);
			if(percent == 0) return entry.getKey();
			if(percent > closest_lev) {
				closest_lev = percent;
				closest = entry.getKey();
			}
		}
		return closest;
	}
	
	public static int getLongestStringLength(String id) {
		if(!searchers.containsKey(id)) return (Integer) null;
		int longest = 0;
		for(String elem : searchers.get(id)) {
			if(elem.length() > longest) longest = elem.length();
		}
		return longest;
	}

	public static boolean isArg(String id, String search){
		return Arrays.asList(searchers.get(id)).contains(search);
	}

	public static String[] getClosestWords(String id, final String search, int amount){
		String[] closestWords = getClosestWords(id,search);

		String[] trimmed = new String[amount];
		for(int i=0;i<closestWords.length;i++){
			if(i<trimmed.length){
				trimmed[i] = closestWords[i];
			}
		}
		return trimmed;
	}
}