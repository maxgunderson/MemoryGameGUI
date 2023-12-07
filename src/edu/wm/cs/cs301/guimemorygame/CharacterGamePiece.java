package edu.wm.cs.cs301.guimemorygame;

public class CharacterGamePiece implements GamePiece {
	private final Character symbol;
	private boolean visibility;
	private int row, col; 

	public CharacterGamePiece(char s, int row, int col) {
		this.symbol = s;
		this.visibility = false;
		this.row = row;
		this.col = col;
	}

	public Character getSymbol() {
		return this.symbol;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}

	public void setVisible(boolean v) {
		this.visibility = v;
	}

	public boolean isVisible() {
		return this.visibility;
	}

	public boolean equals(GamePiece other) {
		if (this.symbol.equals(other.getSymbol())) {
			return true;
		}
		return false;
	}
}
