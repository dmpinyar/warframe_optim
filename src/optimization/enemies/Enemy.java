package optimization.enemies;

/**
 * Holds the data for a certain Enemy
 */
public class Enemy {
	public enum FACTION{Grineer, Corpus, Infested}
	
	public FACTION faction;
	public String enemyName;
	public double baseHealth;
	public double baseArmor;
	public double baseShields;
	private int baseLevel;
	
	//I'm ignoring health and armor types as far as damage is concerned
	//It's so much more work for something that generally doesn't matter
	
	/**
	 * import constructor
	 */
	public Enemy(String enemyName, FACTION faction, double health, double armor, double shields, int baseLevel) {
		this.enemyName = enemyName;
		this.faction = faction;
		this.baseHealth = health;
		this.baseArmor = armor;
		this.baseShields = shields;
		this.baseLevel = baseLevel;
	}
	
	/**
	 * Used for evaluating an enemies base level and creating a corresponding object for it 
	 * Creates an enemy object based on inputs
	 * Inputted when enemy is fully armor stripped 
	 * Slightly wrong because it ignores health/shield types
	 */
	public Enemy(String enemyName, FACTION faction, double health, double armor, double shields, double damageToKillLevel40, double damageToKillLevel60) {
		this.enemyName = enemyName;
		this.faction = faction;
		this.baseHealth = health;
		this.baseArmor = armor;
		this.baseShields = shields;
		
		//finds baseLevel from damage to kill at various levels
		
		
	}
	
	public double getEffectiveHealth(int level, double armorStripPercentage) {
		return getHealth(level) * (.9 * Math.sqrt(((1 - armorStripPercentage) * getArmor(level)) / 2700)) + getShields(level);
	}
	
	private double getHealth(int level) {
		int deltaL = level - baseLevel;
		double functionOneOut;
		double functionTwoOut;
		
		if (faction == FACTION.Grineer) {
			functionOneOut = 1 + .015 * Math.pow(deltaL, 2.12);
			functionTwoOut = 1 + (24 * .2 * Math.sqrt(5)) * Math.pow(deltaL, .72);
			
		} else if (faction == FACTION.Corpus) {
			functionOneOut = 1 + .015 * Math.pow(deltaL, 2.12);
			functionTwoOut = 1 + (30 * .2 * Math.sqrt(5)) * Math.pow(deltaL, .55);
			
		} else {
			//infested
			functionOneOut = 1 + .0225 * Math.pow(deltaL, 2.12);
			functionTwoOut = 1 + (36 * .2 * Math.sqrt(5)) * Math.pow(deltaL, .72);
			
		}
		
		double sFunction = 0;
		if (deltaL > 80) sFunction = 1;
		else if (deltaL >= 70) sFunction = 3 * Math.pow((.01 * (deltaL - 70)), 2) - 2 * Math.pow(.01 * (deltaL - 70), 3);
		
		double healthMultiplier;
		if (level <= 15) {
			healthMultiplier = functionOneOut;
		} else if (level <= 25) {
			healthMultiplier = (1 + .025 * (level - 15)) * functionOneOut;
		} else if (level <= 35) {
			healthMultiplier = (1.25 + .125 * (level - 25)) * functionOneOut;
		} else if (level <= 50) {
			healthMultiplier = (2.5 + (2 / 15) * (level - 35)) * functionOneOut;
		} else if (level <= 100) {
			healthMultiplier = (4.5 + .03 * (level - 50)) * (functionOneOut * (1 - sFunction) + functionTwoOut * (sFunction));
		} else {
			healthMultiplier = 6 * functionTwoOut;
		}
		
		return healthMultiplier * baseHealth;
	}
	
	private double getShields(int level) {
		int deltaL = level - baseLevel;
		double functionOneOut;
		double functionTwoOut;
		
		if (faction == FACTION.Corpus) {
			functionOneOut = 1 + .02 * Math.pow(deltaL, 1.76);
			functionTwoOut = 1 + 2 * Math.pow(deltaL, .72);
			
		} else {
			//grinner
			//if your infested has shields you're cooked from the beginning
			functionOneOut = 1 + .02 * Math.pow(deltaL, 1.75);
			functionTwoOut = 1 + 1.6 * Math.pow(deltaL, .75);
			
		}
		
		double sFunction = 0;
		if (deltaL > 80) sFunction = 1;
		else if (deltaL >= 70) sFunction = 3 * Math.pow((.01 * (deltaL - 70)), 2) - 2 * Math.pow(.01 * (deltaL - 70), 3);
		
		double shieldMultiplier;
		if (level <= 15) {
			shieldMultiplier = functionOneOut;
		} else if (level <= 25) {
			shieldMultiplier = (1 + .025 * (level - 15)) * functionOneOut;
		} else if (level <= 35) {
			shieldMultiplier = (1.25 + .125 * (level - 25)) * functionOneOut;
		} else if (level <= 50) {
			shieldMultiplier = (2.5 + (2 / 15) * (level - 35)) * functionOneOut;
		} else if (level <= 100) {
			shieldMultiplier = (4.5 + .03 * (level - 50)) * (functionOneOut * (1 - sFunction) + functionTwoOut * (sFunction));
		} else {
			shieldMultiplier = 6 * functionTwoOut;
		}
		
		return shieldMultiplier * baseShields;
	}
	
	private double getArmor(int level) {
		int deltaL = level - baseLevel;
		double functionOneOut;
		double functionTwoOut;
		
		functionOneOut = 1 + .005 * Math.pow(deltaL, 1.75);
		functionTwoOut = 1 + .4 * Math.pow(deltaL, .75);
		
		double sFunction = 0;
		if (deltaL > 80) sFunction = 1;
		else if (deltaL >= 70) sFunction = 3 * Math.pow((.01 * (deltaL - 70)), 2) - 2 * Math.pow(.01 * (deltaL - 70), 3);
		
		double armor = baseArmor * functionOneOut * (1 - sFunction) + functionTwoOut * (sFunction);
		if (armor > 2700) armor = 2700;
		
		return armor;
	}
	
	

	/**
	 * For use when exporting to a file
	 */
	@Override
	public String toString() {
		return "";
	}
	
	
	
}
