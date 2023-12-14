package edu.wm.cs.cs301.guimemorygame.model;

public class GameBoard {
	private final GamePiece[][] board;

	public GameBoard(int rows, int cols, Alphabet a, String diff) {

		this.board = new GamePiece[rows][cols];
		initializeBoard(board, rows, cols, a);

	}

	// 2D board creation and object insertion
	private void initializeBoard(GamePiece[][] Board, int rows, int cols, Alphabet a) {
		char[] alphabet = a.toCharArray();
		int counter = 0;

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				board[x][y] = new CharacterGamePiece(alphabet[counter], x, y);
				counter++;
			}
		}
	}

	public GamePiece[][] getPiece() {
		return this.board;
	}

	public GamePiece getPieceObject(int rows, int cols) {
		return board[rows][cols];
	}
}
