package edu.wm.cs.cs301.guimemorygame;

public class GameBoard {
	private final GamePiece[][] board;
	private final int row, col; 
	private String difficulty; 
	
	public GameBoard(int rows, int cols, Alphabet a, String diff) {
		row = rows;
		col = cols;
		difficulty = diff;
		
		this.board = new GamePiece[rows][cols];
		initializeBoard(board, rows, cols, a); 	
	}

	// 2D board creation and object insertion
	private void initializeBoard(GamePiece[][] Board, int rows, int cols, Alphabet a) { 
		char[] alphabet = a.toCharArray();
		int counter = 0;
		
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				board[x][y] = new CharacterGamePiece(alphabet[counter]); 
				counter ++;
			}
		}
	}
	
	public GamePiece[][] getPiece() {
		return this.board;
	}
	
	public void displayBoard(GamePiece[][] board) {
		if (difficulty.equals("easy")) {
			displayEasy(board);
		} 
		else if (difficulty.equals("medium")) {
			displayMedium(board);
		} 
		else {
			displayHard(board);
		}
	}
	
	public void displayEasy(GamePiece[][] board) {
		System.out.println("     1   2   3   4");
		System.out.println("   ================= ");
		for (int x = 0; x < row; x++) {
			System.out.print((x + 1) + " || ");
			for (int y = 0; y < col; y++) {
				if (board[x][y].isVisible()) {
					if (y == 3) {
						System.out.print(board[x][y].getSymbol() + " |");
					} else {
						System.out.print(board[x][y].getSymbol() + " | ");
					}
				} else { 
					if (y == 3) {
						System.out.print("? |");
					} else {
						System.out.print("? | ");
					}
				}
			}
			System.out.println("|");
			if (x != 2) {
				System.out.println("   ------------------");
			}
		}
		System.out.println("   ================= ");
	}
	
	public void displayMedium(GamePiece[][] board) {
		System.out.println("     1   2   3   4   5   6   7");
		System.out.println("   ============================= ");
		for (int x = 0; x < row; x++) {
			System.out.print((x + 1) + " || ");
			for (int y = 0; y < col; y++) {
				if (board[x][y].isVisible()) {
					if (y == 6) {
						System.out.print(board[x][y].getSymbol() + " |");
					} else {
						System.out.print(board[x][y].getSymbol() + " | ");
					}
				} else { 
					if (y == 6) {
						System.out.print("? |");
					} else {
						System.out.print("? | ");
					}
				}
			}
			System.out.println("|");
			if (x != 3) {
				System.out.println("   -----------------------------");
			}
		}
		System.out.println("   ============================= ");
	}
	
	public void displayHard(GamePiece[][] board) {
		System.out.println("     1   2   3   4   5   6   7   8");
		System.out.println("   ================================= ");
		for (int x = 0; x < row; x++) {
			System.out.print((x + 1) + " || ");
			for (int y = 0; y < col; y++) {
				if (board[x][y].isVisible()) {
					if (y == 7) {
						System.out.print(board[x][y].getSymbol() + " |");
					} else {
						System.out.print(board[x][y].getSymbol() + " | ");
					}
				} else { 
					if (y == 7) {
						System.out.print("? |");
					} else {
						System.out.print("? | ");
					}
				}
			}
			System.out.println("|");
			if (x != 6) {
				System.out.println("   ---------------------------------");
			}
		}
		System.out.println("   ================================= ");
	}
	
	// adjusted for 1 based indexing 
	public GamePiece getPieceObject(int rows, int cols) { 
		rows --; 
		cols --;
		return board[rows][cols];
	}
}
