/* File: ReadabilityIndices.java
 * Name: [TODO: Your name here!]
 * Section Leader: [TODO: Your SL's name here!]
 * 
 * [TODO: Edit this header file to include a description of this program!]
 */

import acm.program.*;
import java.util.*;
import java.io.*;

public class ReadabilityIndices extends ConsoleProgram {
	public void run() {
		ArrayList<String> indvidualLines= new ArrayList<String>();
		while (true) {
			String fileName = readLine("Enter file name or website url: ");
			if (fileName.isEmpty()) break;
			if (isUrl(fileName)==true) {
				indvidualLines = Scraper.pageContents(fileName);
			}else{
				indvidualLines = fileContents(fileName);
			}
			println("The Flesch Kincaid Grade Level is:"+fleschKincaidGradeLevelOf(indvidualLines));
			println("The Dale Chall Readability Score is:"+daleChallReadabilityScoreOf(indvidualLines));
		}
	}
	
	/**
	 * Checks if a text is a URL
	 * 
	 * @param text
	 * @return boolean
	 */
	private boolean isUrl(String fileName){
		if (fileName.length()<8) return false;
		if ((fileName.substring(0,8)=="http://") || (fileName.substring(0,9)=="https://")) return true;
		return false;
	}
	
	/**
	 * Creates Array List of String where each String is a line of a file
	 * 
	 * @param file name
	 * @return Array List of String
	 */

	private ArrayList<String> fileContents(String fileName) {
		ArrayList<String> fileInput= new ArrayList<String>();
		try {
			BufferedReader br = 
				new BufferedReader(new FileReader(fileName));
			int numLines=0;
			while (fileInput.get(numLines)!=null) {
				fileInput.add(br.readLine());
				numLines++;
			}
			println("Number of lines:"+numLines);
			br.close();
		} catch (IOException e) {
			return null;
		}
		return fileInput;
	}
	/**
	 * Provides the Flesch Kincaid Grade Level of an array list of string
	 * 
	 * @param Array list of strings
	 * @return Flesch Kincaid Grade Level
	 */
	private double fleschKincaidGradeLevelOf(ArrayList<String> lines){
		double result=0;
		double numWords=0;
		double numSentences=0;
		double numSyllables=0;
		String currentLine="";
		ArrayList<String> currentLineToken= new ArrayList<String>();
		for (int i=0;i<lines.size();i++){
			currentLine=lines.get(i);
			currentLineToken=tokenize(currentLine);
			numWords=numWords+wordsInLine(currentLineToken);
			numSentences=numSentences+sentencesInLine(currentLineToken);
			numSyllables=numSyllables+syllablesInLine(currentLineToken);
		}
		if (numWords==0) numWords=1;
		if (numSentences==0) numWords=1;
		result=-15.59+0.39*(numWords/numSentences)+11.8*(numSyllables/numWords);
		return result;
	}
	/**
	 * Provides the Dale Chall Readability Score of a text
	 * 
	 * @param Array list of strings where each string represents a line of a file
	 * @return Dale Chall Readability Score
	 */
	private double daleChallReadabilityScoreOf(ArrayList<String> lines) {
		double result=0;
		double numWords=0;
		double numDifficultWords=0;
		double numSentences=0;
		double numSyllables=0;
		double bonus=0;
		String currentLine="";
		ArrayList<String> currentLineToken= new ArrayList<String>();
		for (int i=0;i<lines.size();i++){
			currentLine=lines.get(i);
			currentLineToken=tokenize(currentLine);
			numWords=numWords+wordsInLine(currentLineToken);
			numDifficultWords=numDifficultWords+diffWordsInLine(currentLineToken);
			numSentences=numSentences+sentencesInLine(currentLineToken);
			numSyllables=numSyllables+syllablesInLine(currentLineToken);
		}
		if (numWords==0) numWords=1;
		if (numSentences==0) numWords=1;
		if (numDifficultWords/numWords>=0.05) bonus=1;
		result=0.1579*100*(numDifficultWords/numWords)+0.0496*(numWords/numSentences) +bonus;
		return result;
	}

	/**
	 * Counts the number of Syllables in an Array list
	 * 
	 * @param Array list of strings
	 * @return Number of syllables in the array list
	 */

	private int syllablesInLine(ArrayList<String> tokens) {
		int result=0;
		for (int i=0; i<tokens.size(); i++) {
			if (isWord(tokens.get(i))==true){
				result=result+syllablesInWord(tokens.get(i));
			}
		}
		return result;
	}

	private int diffWordsInLine(ArrayList<String> tokens) {
		int result=0;
		for (int i=0; i<tokens.size(); i++) {
			if (isWord(tokens.get(i))==true){
				if (syllablesInWord((tokens.get(i)))>=3) result++;
			}
		}
		return result;
	}
	/**
	 * Counts the number of words in an Array list
	 * 
	 * @param Array list of strings
	 * @return boolean
	 */

	private int wordsInLine(ArrayList<String> tokens){
		int result=0;
		char currentChar='{';
		for (int i=0; i<tokens.size(); i++) {
			currentChar=tokens.get(i).charAt(0);
			if (Character.isLetter(currentChar)==true) result++;
		}
		return result;
	}
	/**
	 * Checks if a String is a word
	 * 
	 * @param The string 
	 * @return boolean
	 */
	private boolean isWord(String line) {
		for (int i=0; i<line.length();i++){
			if (Character.isLetter(line.charAt(i))==false) return false;
		}
		return true;
	}

	/**
	 * Counts the number of sentences in a string
	 * 
	 * @param The string 
	 * @return The number of . ? and ! in that string
	 */
	private int sentencesInLine(ArrayList<String> tokens) {
		int result=0;
		char currentChar='[';
		for (int i=0; i<tokens.size(); i++) {
			currentChar=tokens.get(i).charAt(0);
			if (currentChar=='.'||currentChar=='?'||currentChar=='!') result++;
		}
		return result;
	}
	/**
	 * Receives a string and turns it into tokens. Each token is either consecutive letters or just one of something else.
	 * 
	 * @param The string 
	 * @return An array list of Strings where each String is consecutive letters or just one of something else
	 */
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
	/**
	 * Given a String and a starting point, returns an the number of letters in the word.
	 * 
	 * @param The string and where to start in the string
	 * @return the number of consecutive letters from that starting point.
	 */
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