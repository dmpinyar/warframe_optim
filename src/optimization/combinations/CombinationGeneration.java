package optimization.combinations;

import java.util.ArrayList;

/**
 * Provides static methods to generate all possible combinations
 */
public class CombinationGeneration {
	
	/**
	 * Generates all of the combinations and assigns them to an arrayList
	 */
	public static ArrayList<Combination> generateCombinations(ArrayList<Mod> mods, int occupiedMods) {
		//parse lists
		ArrayList<Mod> standardMods = new ArrayList<Mod>();
		ArrayList<Mod> exilusMods = new ArrayList<Mod>();
		ArrayList<Mod> auraMods = new ArrayList<Mod>();
		
		for (int i = 0; i < mods.size(); i++) {
			if (mods.get(i).type == Mod.TYPE.Exilus) exilusMods.add(mods.get(i));
			else if (mods.get(i).type == Mod.TYPE.Aura) auraMods.add(mods.get(i));
			else standardMods.add(mods.get(i));
		}
		
		ArrayList<ArrayList<Mod>> rawCombinations = generateCombinationsRaw(standardMods, 8 - occupiedMods);
		
		//add exilus and aura mods
		for (int i = 0; i < rawCombinations.size(); i++) {
			ArrayList<Mod> temp = rawCombinations.remove(i);
			for (int j = 0; j < exilusMods.size(); j++) {
				ArrayList<Mod> export = new ArrayList<Mod>();
				for (int k = 0; k < temp.size(); k++) export.add(temp.get(k));
				export.add(exilusMods.get(j));
				rawCombinations.add(i, export);
				
			}
			
			i++;
		}
		
		for (int i = 0; i < rawCombinations.size(); i++) {
			ArrayList<Mod> temp = rawCombinations.remove(i);
			for (int j = 0; j < auraMods.size(); j++) {
				ArrayList<Mod> export = new ArrayList<Mod>();
				for (int k = 0; k < temp.size(); k++) export.add(temp.get(k));
				export.add(auraMods.get(j));
				rawCombinations.add(i, export);
				
			}
			
			i++;
		}
		
		//translate list
		ArrayList<Combination> combinations = new ArrayList<Combination>();
		for (int i = 0; i < rawCombinations.size(); i++) {
			combinations.add(new Combination(rawCombinations.get(i)));
		}
		
		return combinations;
	}
	
	/**
	 * just does math to see how big it should be
	 */
	public static long combinationSize() {
		
		
		return 0;
	}
	
	/**
	 * Generates the combinations raw
	 * @param arr the arrayList of Mods
	 * @param k the size of the outputted combinations
	 * @return an arrayList of arrayLists of Mods
	 */
	private static ArrayList<ArrayList<Mod>> generateCombinationsRaw(ArrayList<Mod> arr, int k) {
		ArrayList<ArrayList<Mod>> combinations = new ArrayList<>();
        generateCombinationsRecursive(arr, k, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    private static void generateCombinationsRecursive(ArrayList<Mod> arr, int k, int start, ArrayList<Mod> currentCombination, ArrayList<ArrayList<Mod>> combinations) {
        if (currentCombination.size() == k) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i < arr.size(); i++) {
            currentCombination.add(arr.get(i));
            generateCombinationsRecursive(arr, k, i + 1, currentCombination, combinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
	
}
