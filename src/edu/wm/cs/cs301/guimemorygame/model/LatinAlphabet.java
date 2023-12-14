package edu.wm.cs.cs301.guimemorygame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LatinAlphabet implements Alphabet {
	private String difficulty;

	public LatinAlphabet(String difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public char[] toCharArray() {
		char[] characters = "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTTUUVVWWXXYYZZ@@&&".toCharArray();
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

//		Collections.shuffle(charList);
		char[] shuffledCharacters = new char[charList.size()];
		
		for (int i = 0; i < charList.size(); i++) {
			shuffledCharacters[i] = charList.get(i);
			
		}
		return shuffledCharacters;
	}
}
