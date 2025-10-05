package optimization.combinations;

import java.util.ArrayList;

import optimization.evaluation.XakuDamage;

/**
 * Holds the data for each combination
 * Makes it easier to print data to output file
 * Implements comparable for sorting on the basis of the evaluatedData field
 */
public class Combination implements Comparable<Combination> {

	public ArrayList<Mod> mods;
	public double evaluatedData;
	private double duration;
	private double efficiency;
	private double range;
	private double strength;
	
	/**
	 * List of mods to be developed externally 
	 */
	public Combination(ArrayList<Mod> mods) {
		this.mods = mods;
		populateStats();
	}
	
	private void populateStats() {
		duration = 1;
		efficiency = 1;
		range = 1;
		strength = 1;
		
		for (int i = 0; i < mods.size(); i++) {
			if (Mod.STATS.Duration == mods.get(i).positiveStat) duration += mods.get(i).positiveBuff; 
			else if (Mod.STATS.Efficiency == mods.get(i).positiveStat) efficiency += mods.get(i).positiveBuff; 
			else if (Mod.STATS.Range == mods.get(i).positiveStat) range += mods.get(i).positiveBuff; 
			else if (Mod.STATS.Strength == mods.get(i).positiveStat) strength += mods.get(i).positiveBuff; 
			
			if (mods.get(i).negativeStat != null) {
				if (Mod.STATS.Duration == mods.get(i).negativeStat) duration += mods.get(i).negativeBuff; 
				else if (Mod.STATS.Efficiency == mods.get(i).negativeStat) efficiency += mods.get(i).negativeBuff; 
				else if (Mod.STATS.Range == mods.get(i).negativeStat) range += mods.get(i).negativeBuff; 
				else if (Mod.STATS.Strength == mods.get(i).negativeStat) strength += mods.get(i).negativeBuff; 
			}
			
			
		}
	}
	
	/**
	 * Populates evaluatedData from an external static method
	 */
	public void evaluateXaku(int enemyLevel, boolean roar) {
		evaluatedData = XakuDamage.evaluateDamageRaw(strength, range, efficiency, duration, enemyLevel, roar);
	}
	
	/**
	 * Compares to another on the basis of evaluated Data
	 */
	@Override
	public int compareTo(Combination o) {
		return (int)(evaluatedData - o.evaluatedData);
	}

	/**
	 * For use in exporting
	 * Prints the evaluated data, then the stat modifiers, then all the mods in the combination
	 */
	@Override
	public String toString() {
		String string = "" + evaluatedData + ",";
		for (int i = 0; i < mods.size() - 1; i++) {
			string += mods.get(i).modName + ",";
		}
				
		string += mods.get(mods.size() - 1).modName + ",duration: " + duration + ",efficiency: " + efficiency + ",range: " + range + ",strength: " + strength;
		
		return string;
	}

}
