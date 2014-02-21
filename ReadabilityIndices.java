/* File: ReadabilityIndices.java
 * Name: [TODO: Your name here!]
 * Section Leader: [TODO: Your SL's name here!]
 * 
 * [TODO: Edit this header file to include a description of this program!]
 */

import acm.program.*;
import java.util.*;

public class ReadabilityIndices extends ConsoleProgram {
	public void run() {
		while (true) {
			String line = readLine("Enter text to tokenize: ");
			if (line.isEmpty()) break;
			ArrayList<String> tokens = tokenize(line);
			println(" Number of tokens: " + tokens.size());
			for (int i = 0; i < tokens.size(); i++) {
				println(" Token #" + (i + 1) + ": [" + tokens.get(i) + "]");
			}
		}
	}

	private ArrayList<String> tokenize(String line) {
		int i=0;
		int wordSize=0;
		ArrayList<String> tokens= new ArrayList<String>();
		while (i<line.length()) {
			wordSize= sizeOfWord(line,i);
			tokens.add(line.substring(i, i+wordSize));
			i=i+wordSize;
		}
		return tokens;
	}

	private int sizeOfWord(String line, int start){
		char currentChar=line.charAt(start);
		int wordSize=0;
		int i=start;
		while (Character.isLetter(currentChar)==true && i<line.length()) {
			wordSize+=1;
			i+=1;
			currentChar=line.charAt(i);
		}
		if (wordSize==0) return 1;
		return wordSize;
	}


	/**
	 * Given a word, returns an estimate of the number of syllables in that word.
	 * 
	 * @param word The word in question.
	 * @return An estimate of the number of syllables in the word.
	 */
	private int syllablesInWord(String word) {
		/* TODO: Fill this in as Step One of the assignment. */
		int syllableCount=0;
		int lastVowel=-2;
		word=word.toLowerCase();
		for (int i=0; i<word.length(); i++){
			char currentChar=word.charAt(i);
			if (currentChar=='e' || currentChar=='a' || currentChar=='y' || currentChar=='u' || currentChar=='i' || currentChar=='o'){
				if (lastVowel!=(i-1)) {
					if ((currentChar=='e') && (i==word.length()-1)){
						break;
					}
					syllableCount+=1;
				}
				lastVowel=i;
			}	
		}		
		if (syllableCount<=0) return 1;
		return syllableCount;
	}


}