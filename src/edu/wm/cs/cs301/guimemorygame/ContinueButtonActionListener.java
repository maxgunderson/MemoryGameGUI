package edu.wm.cs.cs301.guimemorygame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContinueButtonActionListener implements ActionListener{
	
	private MemoryGame game;

	ContinueButtonActionListener(MemoryGame game) {
		this.game = game;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		game.continueButtonClicked();
		game.isGameWon();
	}

}
