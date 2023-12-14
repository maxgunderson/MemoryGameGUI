package edu.wm.cs.cs301.guimemorygame.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HebrewAlphabet implements Alphabet{
	private String difficulty; 
	
	public HebrewAlphabet(String difficulty) {
		this.difficulty = difficulty; 
	}
	
	@Override
	public char[] toCharArray() {
		String unicodeValues = "\u16A0\u16A0\u16B0\u16B0\u16C0\u16C0\u16D0\u16D0\u16F0\u16F0\u16A1\u16A1"
				+ "\u16B1\u16B1\u16C1\u16C1\u16D1\u16D1\u16E1\u16E1\u16DC\u16DC\u16A2\u16A2\u16B2\u16B2"
				+ "\u16C2\u16C2\u16D2\u16D2\u16E2\u16E2\u16CC\u16CC\u16A3\u16A3\u16B3\u16B3\u16C3\u16C3"
				+ "\u16D3\u16D3\u16E3\u16E3\u16ED\u16ED\u16A4\u16A4\u16B4\u16B4\u16C4\u16C4\u16D4\u16D4"
				+ "\u16E4\u16E4";
				
		char[] characters = unicodeValues.toCharArray();
		
        List<Character> charList = new ArrayList<>();
        
        if (difficulty.equals("easy")) {
        	for (int i = 0; i < 12; i++) {
        		charList.add(characters[i]);
        	}
        } else if (difficulty.equals("medium")) {
        	for (int i = 0; i < 28; i++) {
        		charList.add(characters[i]);
        	}
        } else {
        	for (char c : characters) {
        		charList.add(c);
        	}
        }
        Collections.shuffle(charList);
        char[] shuffledCharacters = new char[charList.size()];
        
        for (int i = 0; i < charList.size(); i++) { 
        	shuffledCharacters[i] = charList.get(i);
        }
		return shuffledCharacters; 
	}
}
