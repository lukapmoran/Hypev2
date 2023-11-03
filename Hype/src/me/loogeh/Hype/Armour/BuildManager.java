package me.loogeh.Hype.Armour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.loogeh.Hype.Armour.BuildFactory.Build;

public class BuildManager {
	
	public HashMap<ClassType, List<Integer>> unlockedAbilities = new HashMap<ClassType, List<Integer>>();
	public HashMap<ClassType, Integer> selectedBuilds = new HashMap<ClassType, Integer>();
	public HashMap<ClassType, HashMap<Integer, BuildFactory>> storedBuilds = new HashMap<ClassType, HashMap<Integer, BuildFactory>>();
	
	public BuildManager(boolean defaults) {
		if(defaults) {
			for(ClassType sets : ClassType.values()) {
				BuildFactory buildFactory = new BuildFactory(1, sets);
				for(AbilityInfo def : AbilityInfo.getDefaults(sets)) {
					buildFactory.withAbility(def);
				}
			}
		} else {
			HashMap<ClassType, List<Integer>> unlockedAbilities = new HashMap<ClassType, List<Integer>>();
			unlockedAbilities.put(ClassType.LEATHER, new ArrayList<Integer>());
			unlockedAbilities.put(ClassType.GOLD, new ArrayList<Integer>());
			unlockedAbilities.put(ClassType.CHAIN, new ArrayList<Integer>());
			unlockedAbilities.put(ClassType.IRON, new ArrayList<Integer>());
			unlockedAbilities.put(ClassType.DIAMOND, new ArrayList<Integer>());
			this.unlockedAbilities = unlockedAbilities;
			HashMap<ClassType, Integer> selectedBuilds = new HashMap<ClassType, Integer>();
			selectedBuilds.put(ClassType.LEATHER, 1);
			selectedBuilds.put(ClassType.GOLD, 1);
			selectedBuilds.put(ClassType.CHAIN, 1);
			selectedBuilds.put(ClassType.IRON, 1);
			selectedBuilds.put(ClassType.DIAMOND, 1);
			this.selectedBuilds = selectedBuilds;
			HashMap<ClassType, HashMap<Integer, BuildFactory>> storedBuilds = new HashMap<ClassType, HashMap<Integer, BuildFactory>>();
			storedBuilds.put(ClassType.LEATHER, new HashMap<Integer, BuildFactory>());
			storedBuilds.put(ClassType.GOLD, new HashMap<Integer, BuildFactory>());
			storedBuilds.put(ClassType.CHAIN, new HashMap<Integer, BuildFactory>());
			storedBuilds.put(ClassType.IRON, new HashMap<Integer, BuildFactory>());
			storedBuilds.put(ClassType.DIAMOND, new HashMap<Integer, BuildFactory>());
			this.storedBuilds = storedBuilds;
		}
	}
	
	public int getSelectedBuild(ClassType set) {
		if(set.equals(ClassType.NONE) || set.equals(ClassType.EMPTY)) return -1;
		return selectedBuilds.get(set);
	}
	
	public boolean isSelected(ClassType set, Build build) {
		if(build.equals(Build.ALL)) return false;
		return selectedBuilds.get(set) == build.getId();
	}
	
	public boolean isSelected(ClassType set, int build) {
		if(build < 1 || build > 5) return false;
		return selectedBuilds.get(set) == build;
	}
	
	public boolean isSelected(ClassType set, AbilityInfo ability) {
		if(set.equals(ClassType.NONE) || set.equals(ClassType.EMPTY)) return false;
		return getBuild(set, getSelectedBuild(set)).hasAbility(ability);
	}
	
	public boolean isSelected(ClassType set, AbilityInfo ability, int build) {
		if(set.equals(ClassType.NONE) || set.equals(ClassType.EMPTY) || build > 5 || build < 1) return false;
		return getBuild(set, build).hasAbility(ability);
	}
	
	public BuildFactory getBuild(ClassType set, Build build) {
		if(build.equals(Build.ALL)) return null;
		return storedBuilds.get(set).get(build.getId());
	}
	
	public BuildFactory getBuild(ClassType set, int build) {
		if(build < 1 || build > 5) return null;
		return storedBuilds.get(set).get(build);
	}
	
	public void setBuild(ClassType set, Build buildId, BuildFactory build) {
		if(buildId.equals(Build.ALL)) return;
		if(storedBuilds.get(set) == null) {
			return;
		}
		storedBuilds.get(set).put(buildId.getId(), build);
	}
	
	public void setBuild(ClassType set, int buildId, BuildFactory build) {
		if(buildId < 1 || buildId > 5) return;
		storedBuilds.get(set).put(buildId, build);
	}
	
	public boolean isUnlocked(ClassType set, AbilityInfo ability) {
		return unlockedAbilities.get(set).contains(ability.getID());
	}
	
	public boolean isUnlocked(ClassType set, int ability) {
		return unlockedAbilities.get(set).contains(ability);
	}
	
	public void addUnlock(ClassType set, AbilityInfo ability) {
		if(!unlockedAbilities.get(set).contains(ability.getID())) unlockedAbilities.get(set).add(ability.getID());
	}
	
	public void addUnlock(ClassType set, int abilityId) {
		if(!unlockedAbilities.get(set).contains(abilityId)) unlockedAbilities.get(set).add(abilityId);
	}
	
	public void setSelectedBuild(ClassType set, int buildId) {
		selectedBuilds.put(set, buildId);
	}
	
	public void setSelectedBuild(ClassType set, Build build) {
		if(build.equals(Build.ALL)) return;
		selectedBuilds.put(set, build.getId());
	}
}
