package optimization.io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import optimization.combinations.Combination;
import optimization.combinations.Mod;

/**
 * holds the static methods used to import and export mods from files
 */
public class StatIO {

	private final static String modInput = "input-files/WarframeMods.txt";
	private final static String modOutput = "output-files/WarframeModsOut.txt";
	
	/**
	 * Reads in the mods from a predetermined file
	 * Could add more in the future to read in from other files, but who really cares
	 */
	public static ArrayList<Mod> readMods() throws FileNotFoundException {
		Scanner read = new Scanner(new File(modInput));
		ArrayList<Mod> inputMods = new ArrayList<Mod>();
		
		while (read.hasNextLine()) {
			Mod temp = parseModLine(read.nextLine(), inputMods);
			if (temp != null) inputMods.add(temp);
		}
		
		read.close();
		return inputMods;
	}
	
	/**
	 * helper method to parse every line from the input
	 */
	private static Mod parseModLine(String modLine, ArrayList<Mod> inputMods) {
		Scanner lineRead = new Scanner(modLine);
		lineRead.useDelimiter(",");
		String statString = lineRead.next();
		String modName = lineRead.next();
		double buff = lineRead.nextDouble();
		int capacity = lineRead.nextInt();
		
		Mod.TYPE type = Mod.TYPE.Standard;
		String typeString = null;
		
		if (lineRead.hasNext()) typeString = lineRead.next();
		lineRead.close();
		
		Mod.STATS stat = null;
		if ("Duration".equals(statString)) stat = Mod.STATS.Duration;
		else if ("Efficiency".equals(statString)) stat = Mod.STATS.Efficiency;
		else if ("Range".equals(statString)) stat = Mod.STATS.Range;
		else if ("Strength".equals(statString)) stat = Mod.STATS.Strength;
		
		if (typeString != null) {
			if (typeString.equals("Exilus")) type = Mod.TYPE.Exilus;
			else if (typeString.equals("Aura")) type = Mod.TYPE.Aura;
		}
		
		for (int i = 0; i < inputMods.size(); i++) {
			if (inputMods.get(i).modName.equals(modName)) {
				inputMods.get(i).addAdditionalInfo(stat, buff);
				return null;
			}
		}
		return new Mod(modName, stat, buff, capacity, type);
	}
	
	/**
	 * Writes mods out to a predetermined file
	 * Externally handles sorting
	 * Externally handles generation of combinations (obviously)
	 */
	public static void writeMods(ArrayList<Combination> combinations) throws FileNotFoundException {
		PrintWriter write = new PrintWriter(new File(modOutput));
		for (int i = 0; i < combinations.size(); i++) {
			write.println(combinations.get(i).toString());
		}
		
		write.close();
	}
	
	
}
