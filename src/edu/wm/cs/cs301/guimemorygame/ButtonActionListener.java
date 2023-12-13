package edu.wm.cs.cs301.guimemorygame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
	private int x, y;
	
	private MemoryGame game;
	
	ButtonActionListener(MemoryGame game, GameBoard board, Buttons buttons, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		game.buttonClicked(x, y);
	}
}
