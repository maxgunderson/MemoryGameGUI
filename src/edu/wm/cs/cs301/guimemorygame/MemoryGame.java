package edu.wm.cs.cs301.guimemorygame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MemoryGame {
	private GameBoard board;
	private String difficulty;
	private String charSet;
	private Scanner scanner;
	private int row, col;
	private Alphabet Alpha;
	private List<List<String>> records;

	public MemoryGame() {
		scanner = new Scanner(System.in);
		records = new ArrayList<>();

		System.out.println("TILE BASED MEMORY GAME\n");
		readLeaderBoard();
		displayLeaderBoard();
		System.out.println(" ");
		welcomeMessage();
		runGameFunctions();
	}

	private void runGameFunctions() {
		difficultyInput();
		inputCharSetType();
		chooseAlphabet();
		chooseArraySize(difficulty);
		board = new GameBoard(row, col, Alpha, difficulty);
		runGame();
	}

	// adjusted for 1 based indexing
	private void flipTile(GameBoard board, int rows, int cols) {
		rows--;
		cols--;
		GamePiece[][] array = board.getPiece();
		array[rows][cols].setVisible(true);
	}

	// tile match confirmation
	public boolean isMatch(GamePiece one, GamePiece two) {
		if (one.equals(two)) {
			System.out.println("Match!");
			one.setVisible(true);
			two.setVisible(true);
			return true;
		} else {
			System.out.println("No Match. This turn has ended.");
			one.setVisible(false);
			two.setVisible(false);
			return false;
		}
	}

	private void difficultyInput() {
		boolean validInput = false;

		while (!validInput) {
			System.out.println("ENTER DIFFICULTY: 'Easy', 'Medium', or 'Hard':");
			difficulty = scanner.nextLine().toLowerCase().replaceAll("\\s", "");
			if (difficulty.equals("easy") || difficulty.equals("medium") || difficulty.equals("hard")) {
				validInput = true;
			} else {
				System.out.println("Error: Input must be of proper form");
			}
		}
	}

	private void inputCharSetType() {
		boolean validInput = false;
		System.out.println("The possible character sets for the game are the English alphabet and the Hebrew alphabet");
		System.out.println("Enter '1' for English and '2' for the Hebrew alphabet: ");

		while (!validInput) {
			charSet = scanner.nextLine().toLowerCase().replaceAll("\\s", "");
			if (charSet.equals("1") || charSet.equals("2")) {

				validInput = true;
			} else {
				System.out.println("Error: Input must be of proper form.");
				System.out.println("Enter '1' for english and '2' for the other alphabet: ");
			}
		}
	}

	private void chooseAlphabet() {
		if (charSet.equals("1")) {
			Alpha = new LatinAlphabet(difficulty);
		} else {
			Alpha = new HebrewAlphabet(difficulty);
		}
	}

	private void chooseArraySize(String mode) {
		if (mode.equals("easy")) {
			row = 3;
			col = 4;
		} else if (mode.equals("medium")) {
			row = 4;
			col = 7;
		} else if (mode.equals("hard")) {
			row = 7;
			col = 8;
		}
	}

	// run every iteration of while loop within runGame()
	private boolean checkTileStatus() {
		for (int x = 1; x < row + 1; x++) {
			for (int y = 1; y < col + 1; y++) {
				if (board.getPieceObject(x, y).isVisible() == false) {
					return false;
				}
			}
		}
		return true;
	}

	// gets input from the user: [row col], and returns it
	private String[] inputTileScanner() {
		String rowRegEx = "[1-" + String.valueOf(row) + "]";
		String colRegEx = "[1-" + String.valueOf(col) + "]";

		System.out.println("Choose a tile [R C] or type 'quit' to exit: ");
		String[] input = scanner.nextLine().split(" ");
		if (input[0].equals("quit")) {
			return input;
		} else if (input.length < 2){
			System.out.println("Error: Input must be of proper form:");
			input = inputTileScanner();
		} else if (!(input[0].matches(rowRegEx)) || !(input[1].matches(colRegEx)) || input.length > 2) {
			System.out.println("Error: Input must be of proper form:");
			input = inputTileScanner();
		} else if (board.getPieceObject(Integer.parseInt(input[0]), Integer.parseInt(input[1])).isVisible()) {
			System.out.println("This tile has already been flipped");
			input = inputTileScanner();
		}
		return input;
	}

	private void runGame() {
		int turnCount = 1;
		boolean gameStatus = true;
		String askNewGame;
		String[] tile1;
		String[] tile2;

		while (gameStatus) { // while game is in play
			System.out.println("Turn: " + Integer.toString(turnCount)); // turnCount
			board.displayBoard(board.getPiece());

			tile1 = inputTileScanner(); // input
			if (tile1[0].replace(" ", "").equals("quit")) { // quit check
				System.out.println("<Game Exited>");
				break;
			}

			int tile1Row = Integer.valueOf(tile1[0]);
			int tile1Col = Integer.valueOf(tile1[1]);

			System.out.println("Turn: " + Integer.toString(turnCount));
			flipTile(board, tile1Row, tile1Col);
			board.displayBoard(board.getPiece());

			tile2 = inputTileScanner(); // input
			if (tile2[0].replace(" ", "").equals("quit")) { // quit check
				System.out.println("<Game Exited>");
				break;
			}

			int tile2Row = Integer.valueOf(tile2[0]);
			int tile2Col = Integer.valueOf(tile2[1]);
			System.out.println("Turn: " + Integer.toString(turnCount));
			flipTile(board, tile2Row, tile2Col);
			board.displayBoard(board.getPiece());

			if (!isMatch(board.getPieceObject(tile1Row, tile1Col), board.getPieceObject(tile2Row, tile2Col))) {
				turnCount++;
			}
			System.out.println("Press <enter> to contine: ");
			scanner.nextLine();

			// loop breaking condition using checkTileStatus()
			if (checkTileStatus() == true) {
				gameStatus = false;
				System.out.println("Congratulations, you won the game in " + turnCount + " moves!");
				System.out.println("Here is the complete board:");
				board.displayBoard(board.getPiece());
				
				determineScoreForLeaderboard(difficulty, String.valueOf(turnCount));
				displayLeaderBoard();

				System.out.println("If you would like to play again enter 'yes', otherwise enter 'quit':");
				askNewGame = scanner.nextLine().toLowerCase().replaceAll("\\s", "");

				if (askNewGame.equals("yes")) {
					runGameFunctions();
				} else {
					System.out.println("Thank you for playing the game.");
				}
			}
		}
		scanner.close();
	}

	// reads in Leaderboard.csv, is run only once at the beginning of game
	private void readLeaderBoard() {
		BufferedReader reader;
		String lineReader;
		try { 
			reader = new BufferedReader(new FileReader("./resources/Leaderboard.csv"));

			while ((lineReader = reader.readLine()) != null) {
				String[] valueBuffer = lineReader.split(",");
				records.add(Arrays.asList(valueBuffer));
			}

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	// displays the current Leaderboard using the "records" list
	private void displayLeaderBoard() {
		String value = "";

		System.out.println("LEADERBOARD:");
		for (int i = 0; i < records.size(); i++) {
			List<String> record = records.get(i);
			for (int x = 0; x < record.size(); x++) {
				value += " " + record.get(x);
			}
			if (i != records.size()) { // account for extra array block
				System.out.println(value + " turns");
			}
			value = "";
		}
	}

	// write to actual csv file if score is appropriate
	private void writeToLeaderBoard(String difficulty, String userName, String turnCount) {
		File leaderBoard = new File("./resources/Leaderboard.csv");

		if (difficulty.equals("easy")) {
			records.get(0).set(1, userName);
			records.get(0).set(2, turnCount);
		} else if (difficulty.equals("medium")) {
			records.get(1).set(1, userName);
			records.get(1).set(2, turnCount);
		} else {
			records.get(2).set(1, userName);
			records.get(2).set(2, turnCount);
		}

		try {
			FileWriter writer = new FileWriter(leaderBoard);
			for (int i = 0; i < records.size(); i++) {
				for (int x = 0; x < records.get(i).size(); x++) {
					writer.write(records.get(i).get(x) + ",");
				}
				writer.write(System.lineSeparator());
			}
			writer.close();

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	// determines if the user's score after a win should be written
	private void determineScoreForLeaderboard(String difficulty, String turnCount) {
		String userName;
		int intTurnCount = Integer.valueOf(turnCount);

		if ((difficulty.equals("easy")) && (intTurnCount <= Integer.valueOf(records.get(0).get(2)))) {
			printHelperForLeaderBoard(difficulty);
			userName = scanner.nextLine().replaceAll("\\s", "");
			writeToLeaderBoard(difficulty, userName, turnCount);
		} else if ((difficulty.equals("medium")) && (intTurnCount <= Integer.valueOf(records.get(1).get(2)))) {
			printHelperForLeaderBoard(difficulty);
			userName = scanner.nextLine().replaceAll("\\s", "");
			writeToLeaderBoard(difficulty, userName, turnCount);
		} else if ((difficulty.equals("hard")) && (intTurnCount <= Integer.valueOf(records.get(2).get(2)))) {
			printHelperForLeaderBoard(difficulty);
			userName = scanner.nextLine().replaceAll("\\s", "");
			writeToLeaderBoard(difficulty, userName, turnCount);
		}
	}
	
	private void printHelperForLeaderBoard(String difficulty) {
		System.out.println("New Highscore for difficulty level " + difficulty);
		System.out.println("Enter your name to be displayed on the leaderboard:");
	}

	private void welcomeMessage() {
		System.out.println("RULES: ");
		System.out.println(" You will pick two tiles to compare from the game board.");
		System.out.println(" If the two tiles match, your turn will continue and they will remain visible.");
		System.out.println(" Otherwise, your turn will increment and the tiles will go back to being turned over.");
		System.out.println(" Try to memorize the tile placement in order the have the best chance of getting");
		System.out.println(" future matches. You win by turning over every set of matched tiles.");
		System.out.println(" The goal is to win the game in as few turns as possible.");
		System.out.println(" ");
	}
}
