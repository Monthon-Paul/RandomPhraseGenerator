package Comprehensive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * This class represents Generating a random phrase from a given sentence with
 * non-terminals and there production rule.
 * 
 * @author Monthon Paul & Hong Chen
 * @version December 8, 2021
 */
public class RandomPhraseGenerator {

	// Initialize variables, Data Structures and construction
	private static List<String> list, set, items, storage;
	private static HashMap<String, List<String>> map;
	private static StringBuilder sb;
	private static int number;

	public static void main(String[] args) throws Exception {
		fileReader(args[0]); // read the file

		// Depending on how many phrase print out the randomly generated phrase
		for (int i = 0; i < Integer.parseInt(args[1]); i++) {
			generatePhrase(map);
			//System.out.println(generatePhrase(map));
		}
	}

	/**
	 * This method generates a random phrase and construct a given grammar rule in
	 * order from the given production from <start>. Returns the given constructed
	 * String of a specific grammar rule from its non-terminal and production
	 * 
	 * @param map - given data structure that stores the non-terminal and its
	 *            production
	 * @return a grammar rule following the non-terminal & its production
	 */
	private static String generatePhrase(HashMap<String, List<String>> map) {
		// finds start
		items = map.get("<start>");
		sb = new StringBuilder();
		// Since it's like any other non-terminal, pick a random production
		number = (int) (Math.random() * items.size()); // random number generator
		// Separate every String character into non-terminal & terminal
		list = loopthroughlist(items.get(number), 0);

		// Goes through the the separation list to build the String and return the
		// following
		for (String E : list) {
			if (map.containsKey(E)) {
				set = map.get(E);
				number = (int) (Math.random() * set.size());
				// must check if non-terminal is in production
				recursiveSearch(set.get(number));
			} else {
				sb.append(E);
			}
		}
		return sb.toString();
	}

	/**
	 * Recursive helper for searching the String to find non-terminals and construct
	 * a String
	 * 
	 * @param word - randomly picked word (might contain non-terminal)
	 */
	private static void recursiveSearch(String word) {
		// Separate every String character into non-terminal & terminal
		storage = loopthroughlist(word, 0);

		// recursive case
		for (String E : storage) {
			if (map.containsKey(E)) {
				set = map.get(E);
				number = (int) (Math.random() * set.size());
				recursiveSearch(set.get(number));
			} else {
				sb.append(E);
			}
		}
	}

	/**
	 * This method separates the given String into non-terminals and terminal
	 * Strings
	 * 
	 * @param E     - given String
	 * @param start - first index of String
	 * @return a List containing from the given Stirn of non-terminal & terminal
	 *         Strings
	 */
	private static List<String> loopthroughlist(String E, int start) {
		items = new LinkedList<String>();
		// loop through every character to look to < > to determine non-terminals
		for (int i = 0; i < E.length(); i++) {
			if (String.valueOf(E.charAt(i)).equals("<")) {
				items.add(E.substring(start, i));
				start = i;
			}
			if (String.valueOf(E.charAt(i)).equals(">")) {
				items.add(E.substring(start, i + 1));
				start = i + 1;
			}
		}
		// if there is no non-terminal, just add the given String
		if (items.isEmpty()) {
			items.add(E);
			return items;
		}
		// add the remaining String
		items.add(E.substring(start));
		return items;
	}

	/**
	 * read & get the data from the given text file.
	 * 
	 * @param filename - the file of .g
	 * @throws Exception - file does not exist
	 */
	private static void fileReader(String filename) throws Exception {
		BufferedReader input;
		input = new BufferedReader(new FileReader(filename));

		// Read each line
		String line = input.readLine();
		
		// make a HashMap to contain the non-terminal & production rules
		map = new HashMap<String, List<String>>();

		// loop through the file of each line to grab every String 
		// add the non-terminal and its production rules in the Map
		while (line != null) {
			if (line.equals("{")) {
				list = new ArrayList<String>();
				line = input.readLine();
				map.put(line, list);
				line = input.readLine();
				while (!line.equals("}")) {
					list.add(line);
					line = input.readLine();
				}
			}
			line = input.readLine();
		}
		input.close();
	}
}
