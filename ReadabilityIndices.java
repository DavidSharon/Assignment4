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
			println(" Syllables: " + syllablesInLine(tokens));
			println(" Words: " + wordsInLine(tokens));
			println(" Sentences: " + sentencesInLine(tokens));
		}
	}

	private int syllablesInLine(ArrayList<String> tokens) {
		int result=0;
		for (int i=0; i<tokens.size(); i++) {
			if (isWord(tokens.get(i))==true){
				result=result+syllablesInWord(tokens.get(i));
			}
		}
		return result;
	}

	private int wordsInLine(ArrayList<String> tokens){
		int result=0;
		char currentChar='{';
		for (int i=0; i<tokens.size(); i++) {
			currentChar=tokens.get(i).charAt(0);
			if (Character.isLetter(currentChar)==true) result++;
		}
		return result;
	}
	
	private boolean isWord(String line) {
		for (int i=0; i<line.length();i++){
			if (Character.isLetter(line.charAt(i))==false) return false;
		}
		return true;
	}
	
	private int sentencesInLine(ArrayList<String> tokens) {
		int result=0;
		char currentChar='[';
		for (int i=0; i<tokens.size(); i++) {
			currentChar=tokens.get(i).charAt(0);
			if (currentChar=='.'||currentChar=='?'||currentChar=='!') result++;
		}
		return result;
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
				if (i<line.length()) currentChar=line.charAt(i);
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