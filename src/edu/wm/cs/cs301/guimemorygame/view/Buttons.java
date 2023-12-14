package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.Color;

import javax.swing.JButton;

import edu.wm.cs.cs301.guimemorygame.model.GameBoard;
import edu.wm.cs.cs301.guimemorygame.model.GamePiece;
import edu.wm.cs.cs301.guimemorygame.model.MemoryGame;

public class Buttons {
	
	private GameBoard board;
	private MemoryGame game;
	
	private GamePiece tile1 = null;
	private GamePiece tile2 = null;
	
	private JButton[][] buttonArray;
	private JButton continueButton;

	private boolean secondTileClick = false; // first or second tile flip
	

	public Buttons(MemoryGame game, GameBoard board, JButton[][] grid, JButton continueButton) {
		this.board = board;
		this.game = game;
		this.buttonArray = grid;
		this.continueButton = continueButton;
	}
	
	public void flipButton(int x, int y) {
		String s = board.getPieceObject(x, y).getSymbol().toString();
		buttonArray[x][y].setText(s);
	}
	
	public void flipButtonOver(int x, int y) {
		buttonArray[x][y].setText("?");
	}
	
	public void enableContinueButton() {
		continueButton.setEnabled(true);
	}
	
	public void disableContinueButton() {
		continueButton.setEnabled(false);
	}
	
	public void setGreen(int x, int y) {
		buttonArray[x][y].setForeground(new Color(31, 129, 16));
	}
	
	public void matchingTest(int x, int y) {
		game.flipTile(board, x, y);
		if (secondTileClick) {
			tile2 = board.getPieceObject(x, y);
			game.isMatch(tile1, tile2);
			secondTileClick = false;
		} else {
			tile1 = board.getPieceObject(x, y);
			secondTileClick = true;
		}
	}
}
