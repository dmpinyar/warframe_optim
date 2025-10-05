package optimization.evaluation;

import optimization.primary.WarframeOptimization;

public class XakuDamage {
	
	private static final int BASE_WEAPONS = 6;
	private static final int BASE_DAMAGE = 50;
	private static final double ROAR_MULTIPLIER = 1.3;

	/**
	 * evaluates the damage per second without considering armor or ttk
	 */
	public static double evaluateDamageRaw(double abilityStrength, double abilityRange, double abilityEfficiency,
			double abilityDuration, int enemyLevel, boolean roar) {
		
		if (abilityEfficiency < WarframeOptimization.MINIMUM_EFFICIENCY || abilityDuration < WarframeOptimization.MINIMUM_DURATION) return 0;
		
		if (roar) {
			return ROAR_MULTIPLIER * enemyLevel * abilityStrength * abilityStrength * BASE_DAMAGE * Math.floor(BASE_WEAPONS * abilityRange);
		} else {
			return enemyLevel * abilityStrength * BASE_DAMAGE * Math.floor(BASE_WEAPONS * abilityRange);
		}
		
	}
	
	
	
	
	
}
