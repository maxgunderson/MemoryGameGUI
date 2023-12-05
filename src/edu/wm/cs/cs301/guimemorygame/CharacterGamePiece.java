package edu.wm.cs.cs301.guimemorygame;

public class CharacterGamePiece implements GamePiece {
	private final Character symbol;
	private boolean visibility;

	public CharacterGamePiece(char s) {
		this.symbol = s;
		this.visibility = false;
	}

	public Character getSymbol() {
		return this.symbol;
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
