package me.loogeh.Hype.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.loogeh.Hype.Formatting.C;
import me.loogeh.Hype.Main.Main;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;

public class Boss {
	
	public static HashMap<Integer, Boss> bosses = new HashMap<Integer, Boss>(); //UUID and Boss obj
	
	private UUID id;
	private String name;
	private Entity entity;
	private BossType type;
	
	
	public Boss(String name, BossType type, Location location) {
		this.name = name;
		this.entity = location.getWorld().spawnEntity(location, type.getEntityType());
		this.id = this.entity.getUniqueId();
		this.type = type;
	}
	
	public UUID getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public BossType getType() {
		return this.type;
	}
	
	public enum BossType {
		SLIME("Slime Of Destruction", EntityType.SLIME) {

			public void spawn(Location location) {
				Slime slime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
				slime.setMetadata("boss", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
				//abilities - Launch (launches towards players based on number of players in area and uses vectors), Invulnerable to fire, Jumps up and comes back down launching blocks and players into the air, teleports near players
				slime.setCustomName(C.boldRed + "Slime of Destruction");
				slime.setCustomNameVisible(true);
				slime.setMaxHealth(1000.0D);
				slime.setHealth(slime.getMaxHealth());
				slime.setSize(30);
			}
			
			public void die() {
				
			}
			
		},
		
		SNOWMAN("Snowman of the Arctic", EntityType.SNOWMAN) {

			public void spawn(Location location) {
				//abilities - Blizzard but with more devastating effects
			}

			public void die() {
				
			}
			
		};
		
		public abstract void spawn(Location location);
		public abstract void die();
		
		private String name;
		private EntityType entityType;
		private List<BossAbility> useableAbilities = new ArrayList<BossAbility>();
		
		BossType(String name, EntityType entityType, BossAbility... useableAbilities) {
			this.name = name;
			this.entityType = entityType;
		}
		
		public String getName() {
			return this.name;
		}
		
		public EntityType getEntityType() {
			return this.entityType;
		}
		
		public List<BossAbility> getUseableAbilities() {
			return this.useableAbilities;
		}
	}
	
	public enum BossAbility {
		LAUNCH("Launch") {
			//launches themself towards players
			public void use(Entity entity) {
				
			}
		},
		BLIZZARD("Blizzard") {
			//shoots snowballs in all directions
			public void use(Entity entity) {
				
			}
		},
		SEISMIC_SLAM("Seismic Slam") {
			//launches blocks and players in the air
			public void use(Entity entity) {
				
			}
		},
		
		METEORITE("Meteorite") {
			//shoots fireball into ground launching fire in all directions
			@Override
			public void use(Entity entity) {
				
			}
			
		};
		
		public abstract void use(Entity entity);
		
		private String name;
		
		BossAbility(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}

}
