package edu.wm.cs.cs301.guimemorygame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ContinueButtonActionListener implements ActionListener{
	
	private MemoryGame game;
	private JButton button;

	ContinueButtonActionListener(MemoryGame game) {
		this.game = game;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		game.continueButtonClicked();
//		game.isGameWon();
		this.button = game.getGUI().getContinueButton();
		button.setEnabled(false);
	}

}
