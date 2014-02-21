/* File: ReadabilityIndices.java
 * Name: [TODO: Your name here!]
 * Section Leader: [TODO: Your SL's name here!]
 * 
 * [TODO: Edit this header file to include a description of this program!]
 */

import acm.program.*;

public class ReadabilityIndices extends ConsoleProgram {
	public void run() {
		/* Continuously prompt the user for a word, then print out how many
		 * syllables it's estimated to contain.
		 */
		while (true) {
			/* Read a word. If the user enters the empty string, stop. */
			String word = readLine("Enter a word: ");
			if (word.isEmpty()) break;
			
			/* Output the estimated number of syllables. */
			println("  Estimated number of syllables: " + syllablesInWord(word));
		}
	}
	
	/**
	 * Given a word, returns an estimate of the number of syllables in that word.
	 * 
	 * @param word The word in question.
	 * @return An estimate of the number of syllables in the word.
	 */
	private int syllablesInWord(String word) {
		/* TODO: Fill this in as Step One of the assignment. */
		return 0;
	}
}