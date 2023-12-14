package edu.wm.cs.cs301.guimemorygame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wm.cs.cs301.guimemorygame.model.MemoryGame;

public class ButtonActionListener implements ActionListener {
	private int x, y;
	
	private MemoryGame game;
	
	public ButtonActionListener(MemoryGame game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		game.buttonClicked(x, y);
	}
}
