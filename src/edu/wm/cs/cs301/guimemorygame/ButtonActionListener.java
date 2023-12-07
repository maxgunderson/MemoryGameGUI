package edu.wm.cs.cs301.guimemorygame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
// i think all this class should do is redirect the call to main
	private int x, y;
	
	private GameBoard board;
	private MemoryGame game;
	
	private Buttons buttons;
	
	private GamePiece tile1 = null;
	private GamePiece tile2 = null;
	
	ButtonActionListener(MemoryGame game, GameBoard board, Buttons buttons, int x, int y) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.game = game;
		this.buttons = buttons;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		game.buttonClicked(x, y);
	}
}
