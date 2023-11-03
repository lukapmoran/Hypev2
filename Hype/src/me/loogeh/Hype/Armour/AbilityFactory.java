package me.loogeh.Hype.Armour;

import java.util.HashMap;

import me.loogeh.Hype.Ability.Adrenaline;
import me.loogeh.Hype.Ability.Aptitude;
import me.loogeh.Hype.Ability.ArcticBlast;
import me.loogeh.Hype.Ability.ArrowPenetrative;
import me.loogeh.Hype.Ability.ArrowNoxious;
import me.loogeh.Hype.Ability.ArrowIncendiary;
import me.loogeh.Hype.Ability.ArrowGrappling;
import me.loogeh.Hype.Ability.ArrowCrippling;
import me.loogeh.Hype.Ability.ArrowPoison;
import me.loogeh.Hype.Ability.Beam;
import me.loogeh.Hype.Ability.Barrier;
import me.loogeh.Hype.Ability.ArrowRepulse;
import me.loogeh.Hype.Ability.ArrowRegenerative;
import me.loogeh.Hype.Ability.ArrowPower;
import me.loogeh.Hype.Ability.Berserker;
import me.loogeh.Hype.Ability.Blacksmith;
import me.loogeh.Hype.Ability.Blizzard;
import me.loogeh.Hype.Ability.Boost;
import me.loogeh.Hype.Ability.Boreas;
import me.loogeh.Hype.Ability.BreakFall;
import me.loogeh.Hype.Ability.Breathing;
import me.loogeh.Hype.Ability.Burst;
import me.loogeh.Hype.Ability.Cultivater;
import me.loogeh.Hype.Ability.CripplingAxe;
import me.loogeh.Hype.Ability.Desperation;
import me.loogeh.Hype.Ability.Dodge;
import me.loogeh.Hype.Ability.Endurance;
import me.loogeh.Hype.Ability.Fountain;
import me.loogeh.Hype.Ability.Farmer;
import me.loogeh.Hype.Ability.Fade;
import me.loogeh.Hype.Ability.Escape;
import me.loogeh.Hype.Ability.FrostAxe;
import me.loogeh.Hype.Ability.Fusillade;
import me.loogeh.Hype.Ability.Haste;
import me.loogeh.Hype.Ability.InfernoAxe;
import me.loogeh.Hype.Ability.IncrementalUpsurge;
import me.loogeh.Hype.Ability.HellfireAxe;
import me.loogeh.Hype.Ability.ItemWeb;
import me.loogeh.Hype.Ability.Leap;
import me.loogeh.Hype.Ability.LightningSceptre;
import me.loogeh.Hype.Ability.Lumberjack;
import me.loogeh.Hype.Ability.MagneticHaul;
import me.loogeh.Hype.Ability.NewtonianThwart;
import me.loogeh.Hype.Ability.Planter;
import me.loogeh.Hype.Ability.ProximityHeal;
import me.loogeh.Hype.Ability.RapidRecharge;
import me.loogeh.Hype.Ability.Resist;
import me.loogeh.Hype.Ability.Revenge;
import me.loogeh.Hype.Ability.Ricochet;
import me.loogeh.Hype.Ability.Rush;
import me.loogeh.Hype.Ability.Shockwave;
import me.loogeh.Hype.Ability.Smash;
import me.loogeh.Hype.Ability.Stealth;
import me.loogeh.Hype.Ability.Stomp;
import me.loogeh.Hype.Ability.Sustenance;
import me.loogeh.Hype.Ability.Swiftness;
import me.loogeh.Hype.Ability.Switch;
import me.loogeh.Hype.Ability.TeamPlayer;
import me.loogeh.Hype.Ability.Toughness;
import me.loogeh.Hype.Ability.Trail;
import me.loogeh.Hype.Ability.Tremor;
import me.loogeh.Hype.Ability.TripleShot;
import me.loogeh.Hype.Ability.WallHop;
import me.loogeh.Hype.Ability.Wildfire;
import me.loogeh.Hype.Main.Main;

public abstract class AbilityFactory {
	
	public HashMap<String, Ability> abilities = new HashMap<String, Ability>();

	public abstract void fill();

	public Ability getAbility(String name) {
		return abilities.get(name);
	}

	public HashMap<String, Ability> getAbilities() {
		return this.abilities;
	}

	public void addAbility(Ability ability) {
		abilities.put(ability.getName(), ability);
	}

	public void addAbilities(Ability[] abilities) {
		for(Ability ability : abilities) {
			this.abilities.put(ability.getName(), ability);
		}
	}

	public void removeAbililty(Ability ability) {
		if(abilities.containsKey(ability.getName())) abilities.remove(ability.getName());
		return;
	}

	public void removeAbility(String ability) {
		if(abilities.containsKey(ability)) abilities.remove(ability);
		return;
	}

	public static AbilityFactory get() {
		AbilityFactory factory = new AbilityFactory() {

			public void fill() {
				addAbilities(new Ability[] {
						new Adrenaline(),
						new Aptitude(),
						new ArcticBlast(),
						new ArrowCrippling(),
						new ArrowGrappling(),
						new ArrowIncendiary(),
						new ArrowNoxious(),
						new ArrowPenetrative(),
						new ArrowPoison(),
						new ArrowPower(),
						new ArrowRegenerative(),
						new ArrowRepulse(),
						new Barrier(),
						new Beam(),
						new Berserker(),
						new Blacksmith(),
						new Blizzard(),
						new Boost(),
						new Boreas(),
						new BreakFall(),
						new Breathing(),
						new Burst(),
						new CripplingAxe(),
						new Cultivater(),
						new Desperation(),
						new Dodge(),
						new Endurance(),
						new Escape(),
						new Fade(),
						new Farmer(),
						new Fountain(),
						new FrostAxe(),
						new Fusillade(),
						new Haste(),
						new HellfireAxe(),
						new IncrementalUpsurge(),
						new InfernoAxe(),
						new Leap(),
						new LightningSceptre(),
						new Lumberjack(),
						new MagneticHaul(),
						new NewtonianThwart(),
						new Planter(),
						new ProximityHeal(),
						new RapidRecharge(),
						new Resist(),
						new Revenge(),
						new Ricochet(),
						new Rush(),
						new Shockwave(),
						new Smash(),
						new Stealth(),
						new Stomp(),
						new Sustenance(),
						new Swiftness(),
						new Switch(),
						new TeamPlayer(),
						new Toughness(),
						new Trail(),
						new Tremor(),
						new TripleShot(),
						new WallHop(),
						new Wildfire(),
						
						new ItemWeb()});
				for(Ability ability : this.getAbilities().values()) {
					Main.get().getServer().getPluginManager().registerEvents(ability, Main.get());
				}
			}
		};
		factory.fill();
		return factory;
	}
}
