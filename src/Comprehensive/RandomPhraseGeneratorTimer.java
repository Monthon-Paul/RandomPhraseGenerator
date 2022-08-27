package Comprehensive;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class RandomPhraseGeneratorTimer {

	private static Random rand;

	public static void main(String[] args) throws Exception {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.
		long startTime = System.nanoTime();
		while (System.nanoTime() - startTime < 1000000000) { // empty block
		}

		// Time adding then removing N values to different kinds of Stacks
		for (int N = 1000; N <= 20000; N += 1000) {
			writeG("Ntimes.g", N);
			timePhraseskN(N);
		}

	}

	private static void timePhraseskN(int N) throws Exception {
		long startTime, midpointTime, stopTime;
		int timesToLoop = 100;

		// Generate random input before starting the timer
		//String[] data = { "src/comprehensive/poetic_sentence.g", String.valueOf(N) };
		String[] data = { "/Users/monthonpaul/eclipse-workspace/CS2420 Assignments/Ntimes.g", String.valueOf(10) };

		startTime = System.nanoTime();

		for (int i = 0; i < timesToLoop; i++) {
			RandomPhraseGenerator.main(data);
		}

		midpointTime = System.nanoTime();

		// Run an loop to capture the cost of the overhead
		for (long i = 0; i < timesToLoop; i++) {
		}

		stopTime = System.nanoTime();

		// Subtract the cost of running the loop
		// from the cost of running the loop plus the real work.
		// Average it over the number of runs.
		double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

		System.out.println(N + "\t" + averageTime);
	}

	/**
	 * Writes the tree to a .g file
	 * 
	 * @param filename - the file to write to
	 */
	public static void writeG(String filename, int N) {
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));
			StringBuilder sb = new StringBuilder();
			StringBuilder builder;
			
			// Set up the dot graph and properties
			output.println("Ntimes.g file");
			output.println("");
			output.println("{");
			output.println("<start>");
			for(int i = 1; i <= N; i++) {
				sb.append("<" + i + ">" + " ");
			}
			output.println(sb.toString());
			output.println("}");
			output.println("");
			
			for(int j = 1; j < N; j++) {
				builder = new StringBuilder();
				builder.append("{\n" + "<" + j + ">" + "\n" + randomInteger().toString() +"\n}\n");
				output.println(builder);
			}
			
			// Close the graph
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Integer randomInteger() {
		return rand.nextInt();
	}

}
