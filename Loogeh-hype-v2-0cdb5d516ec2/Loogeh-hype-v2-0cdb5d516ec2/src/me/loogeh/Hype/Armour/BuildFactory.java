package me.loogeh.Hype.Armour;

import java.util.HashMap;

public class BuildFactory {
	
	private int build_id;
	private ClassType armourSet;
	private HashMap<AbilityType, Integer> buildAbilities = new HashMap<AbilityType, Integer>();
	
	public BuildFactory(int build_id, ClassType armourSet) {
		if(build_id > 5 || build_id < 1) {
			try {
				throw new Exception("Sector > 'build factory' build cannot be greater than 5 or less than 1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(armourSet.equals(ClassType.EMPTY) || armourSet.equals(ClassType.NONE)) {
			try {
				throw new Exception("Sector > 'build factory' armour set cannot be EMPTY or NONE");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.build_id = build_id;
		this.armourSet = armourSet;
	}
	
	public BuildFactory(int build_id, ClassType armourSet, boolean defaults) {
		if(build_id > 5 || build_id < 1) {
			try {
				throw new Exception("Sector > 'build factory' build cannot be greater than 5 or less than 1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(armourSet.equals(ClassType.EMPTY) || armourSet.equals(ClassType.NONE)) {
			try {
				throw new Exception("Sector > 'build factory' armour set cannot be EMPTY or NONE");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(defaults) {
			this.build_id = build_id;
			this.armourSet = armourSet;
			HashMap<AbilityType, Integer> buildAbilities = new HashMap<AbilityType, Integer>();
			for(AbilityInfo ability : AbilityInfo.getDefaults(armourSet)) {
				buildAbilities.put(ability.getType(), ability.getID());
			}
			this.buildAbilities = buildAbilities;
		} else {
			this.build_id = build_id;
			this.armourSet = armourSet;
		}
	}
	
	public int getBuildId() {
		return this.build_id;
	}
	
	public ClassType getArmourSet() {
		return this.armourSet;
	}
	
	public HashMap<AbilityType, Integer> getBuildAbilities() {
		return this.buildAbilities;
	}
	
	public boolean hasAbility(AbilityInfo ability) {
		return buildAbilities.containsValue(ability.getID());
	}
	
	public void withAbility(AbilityInfo ability) {
		if(ability.getSet().equals(getArmourSet())) buildAbilities.put(ability.getType(), ability.getID());
	}
	
	public void withAbilities(BuildFactory buildFactory) {
		for(AbilityType types : AbilityType.values()) {
			AbilityInfo ability = AbilityInfo.getAbility(buildFactory.getBuildAbilities().get(types));
			withAbility(ability);
		}
	}
	
	public void withAbilities(AbilityInfo[] abilities) {
		for(AbilityInfo ability : abilities) {
			withAbility(ability);
		}
	}
	
	
	public enum Build {
		ONE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		ALL(-1);
		
		private int id;
		
		Build(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
	}

}
