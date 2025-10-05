package optimization.combinations;

/**
 * Holds all of the data for the warfame mod
 * Holds enums for mod type and ability stat modifier type
 * No real functionality, just useful for holding data
 */
public class Mod {
	public enum STATS{Duration, Efficiency, Range, Strength}
	public enum TYPE{Standard, Exilus, Aura}
	
	public String modName;
	public STATS positiveStat;
	public STATS negativeStat;
	public double positiveBuff;
	public double negativeBuff;
	public int capacity;
	public TYPE type;
	
	/**
	 * Constructs new one
	 */
	public Mod(String modName, STATS stat, double buff, int capacity, TYPE type) {
		this.modName = modName;
		this.capacity = capacity;
		this.type = type;
		
		if (buff < 0) {
			negativeStat = stat;
			negativeBuff = buff;
		} else {
			positiveStat = stat;
			positiveBuff = buff;
		}
	}
	
	/**
	 * Used when reading in because I chose to seperate the positive and 
	 * negative buffs across lines for some reason
	 */
	public void addAdditionalInfo(STATS stat, double buff) {
		if (buff < 0) {
			negativeStat = stat;
			negativeBuff = buff;
		} else {
			positiveStat = stat;
			positiveBuff = buff;
		}
	}
	
}
