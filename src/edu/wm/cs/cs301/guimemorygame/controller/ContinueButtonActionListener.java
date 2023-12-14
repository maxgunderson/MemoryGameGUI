package edu.wm.cs.cs301.guimemorygame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wm.cs.cs301.guimemorygame.model.MemoryGame;

public class ContinueButtonActionListener implements ActionListener{
	
	private MemoryGame game;
	private JButton button;

	public ContinueButtonActionListener(MemoryGame game) {
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		game.continueButtonClicked();
		this.button = game.getGUI().getContinueButton();
		button.setEnabled(false);
	}

}
