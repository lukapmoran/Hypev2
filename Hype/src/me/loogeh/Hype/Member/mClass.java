package me.loogeh.Hype.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

import me.loogeh.Hype.Armour.AbilityInfo;
import me.loogeh.Hype.Armour.AbilityType;
import me.loogeh.Hype.Armour.Armour;
import me.loogeh.Hype.Armour.BuildFactory;
import me.loogeh.Hype.Armour.BuildManager;
import me.loogeh.Hype.Armour.ClassType;
import me.loogeh.Hype.Main.Main;
import me.loogeh.Hype.Sector.Member;
import me.loogeh.Hype.Sector.Sector;

public class mClass extends Sector {

	private String uuid;
	
	private ClassType lastClass = ClassType.NONE;
	
	private HashMap<AbilityInfo, Long> activeAbilities = new HashMap<AbilityInfo, Long>();
	
	private boolean playerBlocking = false;
	private int swordBlockCount = 0;
	private long lastSwordBlock = 0L;
	private boolean bowCharging = false;
	private int bowCharge = 0;
	private long lastBowCharge = 0L;
	private boolean bowChargeReady = false;
	private long lastSprint = 0L;
	private boolean screenShaking = false;
	private long lastScreenShake = 0L;
	private long lastCrouch = 0L;
	private AbilityInfo arrowDrawn = AbilityInfo.NONE;
	
	private BuildManager buildManager;
	
	public mClass(Member member) {
		super("Class");
		this.uuid = member.getUUID();
	}
	
	public String getUUID() {
		return this.uuid;
	}

	public ClassType getLastClass() {
		return lastClass;
	}

	public void setLastClass(ClassType lastClass) {
		this.lastClass = lastClass;
	}

	public HashMap<AbilityInfo, Long> getActiveAbilities() {
		return activeAbilities;
	}

	public void setActiveAbilities(HashMap<AbilityInfo, Long> activeAbilities) {
		this.activeAbilities = activeAbilities;
	}
	
	public void addActiveAbility(AbilityInfo info) {
		activeAbilities.put(info, System.currentTimeMillis());
	}
	
	public long getElapsed(AbilityInfo info) {
		if(!activeAbilities.containsKey(info)) return -1L;
		return activeAbilities.get(info);
	}
	
	public void removeActiveAbility(AbilityInfo info) {
		activeAbilities.remove(info);
	}
	
	public boolean hasAbilityElapsed(AbilityInfo info) {
		if(!getActiveAbilities().containsKey(info)) return true;
		boolean elapsed = (System.currentTimeMillis() - getActiveAbilities().get(info)) > info.getDuration() ? true : false;
		if(elapsed) removeActiveAbility(info);
		return elapsed;
	}

	public boolean isPlayerBlocking() {
		return playerBlocking;
	}

	public void setPlayerBlocking(boolean playerBlocking) {
		this.playerBlocking = playerBlocking;
	}

	public int getSwordBlockCount() {
		return swordBlockCount;
	}

	public void setSwordBlockCount(int swordBlockCount) {
		this.swordBlockCount = swordBlockCount;
	}

	public long getLastSwordBlock() {
		return lastSwordBlock;
	}

	public void setLastSwordBlock(long lastSwordBlock) {
		this.lastSwordBlock = lastSwordBlock;
	}

	public boolean isBowCharging() {
		return bowCharging;
	}

	public void setBowCharging(boolean bowCharging) {
		this.bowCharging = bowCharging;
	}

	public int getBowCharge() {
		return bowCharge;
	}

	public void setBowCharge(int bowCharge) {
		this.bowCharge = bowCharge;
	}

	public long getLastBowCharge() {
		return lastBowCharge;
	}

	public void setLastBowCharge(long lastBowCharge) {
		this.lastBowCharge = lastBowCharge;
	}

	public boolean isBowChargeReady() {
		return bowChargeReady;
	}

	public void setBowChargeReady(boolean bowChargeReady) {
		this.bowChargeReady = bowChargeReady;
	}

	public long getLastSprint() {
		return lastSprint;
	}

	public void setLastSprint(long lastSprint) {
		this.lastSprint = lastSprint;
	}

	public boolean isScreenShaking() {
		return screenShaking;
	}

	public void setScreenShaking(boolean screenShaking) {
		this.screenShaking = screenShaking;
	}

	public long getLastScreenShake() {
		return lastScreenShake;
	}

	public void setLastScreenShake(long lastScreenShake) {
		this.lastScreenShake = lastScreenShake;
	}

	public long getLastCrouch() {
		return lastCrouch;
	}

	public void setLastCrouch(long lastCrouch) {
		this.lastCrouch = lastCrouch;
	}

	public AbilityInfo getArrowDrawn() {
		return arrowDrawn;
	}

	public void setArrowDrawn(AbilityInfo arrowDrawn) {
		this.arrowDrawn = arrowDrawn;
	}

	public BuildManager getBuildManager() {
		return buildManager;
	}

	public void setBuildManager(BuildManager buildManager) {
		this.buildManager = buildManager;
	}
	
	public boolean isUseable(AbilityInfo info) {
		return getBuildManager().getBuild(info.getSet(), getBuildManager().getSelectedBuild(info.getSet())).getBuildAbilities().containsValue(info.getID());
	}

	public void load() {
		Armour.tryDefaults(uuid);
		BuildManager manager = new BuildManager(false);
		
		ResultSet unlocks = Main.mysql.doQuery("SELECT * FROM ability_unlocks WHERE uuid='" + getUUID() + "'");
		try {
			while(unlocks.next()) {
				ClassType set = ClassType.getSet(unlocks.getString(2));
				manager.addUnlock(set, unlocks.getInt(3));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet sel_builds = Main.mysql.doQuery("SELECT * FROM selected_builds WHERE uuid='" + getUUID() + "'");
		try {
			while(sel_builds.next()) {
				manager.setSelectedBuild(ClassType.getSet(sel_builds.getString(2)), sel_builds.getInt(3));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = Main.mysql.doQuery("SELECT * FROM armour_builds WHERE uuid='" + getUUID() + "'");
		try {
			while(rs.next()) {
				BuildFactory factory = new BuildFactory(rs.getInt(3), ClassType.getSet(rs.getString(2)));
				
				AbilityInfo sword = AbilityInfo.getAbility(rs.getInt(4));
				AbilityInfo axe = AbilityInfo.getAbility(rs.getInt(5));
				AbilityInfo bow = AbilityInfo.getAbility(rs.getInt(6));
				AbilityInfo passive = AbilityInfo.getAbility(rs.getInt(7));
				AbilityInfo passive_II = AbilityInfo.getAbility(rs.getInt(8));
				AbilityInfo passive_III = AbilityInfo.getAbility(rs.getInt(9));
				
				if(sword.equals(AbilityInfo.NONE)) sword = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.SWORD);
				if(axe.equals(AbilityInfo.NONE)) axe = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.AXE);
				if(bow.equals(AbilityInfo.NONE)) bow = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.BOW);
				if(passive.equals(AbilityInfo.NONE)) passive = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.PASSIVE);
				if(passive_II.equals(AbilityInfo.NONE)) passive_II = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.PASSIVE_II);
				if(passive_III.equals(AbilityInfo.NONE)) passive_III = AbilityInfo.getDefault(factory.getArmourSet(), AbilityType.PASSIVE_III);
				
				factory.withAbilities(new AbilityInfo[] {sword, axe, bow, passive, passive_II, passive_III});
				manager.setBuild(factory.getArmourSet(), factory.getBuildId(), factory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.log(Level.WARNING, "Sector > Failed to load 'armour_builds' for " + getUUID());
		}
		setBuildManager(manager);
	}

}
