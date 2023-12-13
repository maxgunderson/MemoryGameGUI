package edu.wm.cs.cs301.guimemorygame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;

public class MemoryGame {
	private GameBoard board;
	private String difficulty;
	private String charSet;
	private Scanner scanner;
	
	private int turnCount;
	private int row, col;
	private Alphabet Alpha;
	private List<List<String>> records;
	
	private GamePiece tile1;
	private GamePiece tile2;
	
	private Buttons buttons;
	private Boolean buttonClickedTurn;
	private Boolean ableToClickButton;
	
	private MainGUI main;
	private Timer timer;
	

	public MemoryGame() {
		scanner = new Scanner(System.in);
		records = new ArrayList<>();

//		readLeaderBoard();
//		displayLeaderBoard();
		runGameFunctions("medium");

//		chooseArraySize("medium");
		
		main = new MainGUI(this);
		buttons = main.getButton();
		main.setDifficulty(difficulty);
//		runGameFunctions("medium");

	}

	private void runGameFunctions(String difficulty) {
		turnCount = 1;
		buttonClickedTurn = false;
		ableToClickButton = true;
		this.difficulty = difficulty;
//		inputCharSetType();
		charSet = "1";
		chooseAlphabet();
		chooseArraySize(difficulty);
		board = new GameBoard(row, col, Alpha, difficulty);
		
	}
	
	// determines what to do when a button is clicked 
	// rotates back and forth between matches/no matches
	public void buttonClicked(int x, int y) {		
		if (!ableToClickButton || board.getPieceObject(x, y).isVisible()) {
			return;
		}
		
		if (buttonClickedTurn) {
			flipTile(board, x, y);
			tile2 = board.getPieceObject(x, y);
			
			buttonClickedTurn = false;
			ableToClickButton = false;
			buttons.enableContinueButton();
			createTimer();

		} else {
			flipTile(board, x, y);
			tile1 = board.getPieceObject(x, y);
			
			buttonClickedTurn = true;
			ableToClickButton = true;
			buttons.disableContinueButton();
		}
	}
	
	// this creates a 2 second timer that perfroms the continueBUttonClicking 
	// right now sometimes it goes too fast when the player clicks buttons to fast
	// i think it has to do with overlapping timers stil going 
	// small bug/implementation issue to fix 
	public void createTimer() { 
		if (timer!= null && timer.isRunning()) {
			timer.stop();
		}
		 timer = new Timer(3000, (ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) { 
            	if (main.getContinueButton().isEnabled() == false) {
            		return;
            	} else {
                   continueButtonClicked();
                   main.getContinueButton().setEnabled(false);
            	}
            }
        });
		timer.setRepeats(false);
		timer.start();
	}
	
	public void continueButtonClicked() {
		if (isMatch(tile1, tile2)) {
			System.out.println("match");
			buttons.setGreen(tile1.getRow(), tile1.getCol());
			buttons.setGreen(tile2.getRow(), tile2.getCol());
		} else {
			buttons.flipButtonOver(tile1.getRow(), tile1.getCol());
			buttons.flipButtonOver(tile2.getRow(), tile2.getCol());
			turnCount++;
			main.updateTurnLabel();
		}
		
		ableToClickButton = true;
		
		if (isGameWon()) {
			main.createGameWonFrame();
		}
	}

	public void flipTile(GameBoard board, int rows, int cols) {
		GamePiece[][] array = board.getPiece();
		array[rows][cols].setVisible(true);
		buttons.flipButton(rows, cols);
		board.displayBoard(array);
	}
	
	// used in continueButton to determine if the game is won,
	// logic in the if statements is not correct right now
	// needs to be fixed at a later time
	public boolean isGameWon() {
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				if (!board.getPieceObject(x, y).isVisible()) {
					System.out.println("GAME NOT OVER");
					return false;
				} 
			}
		}
		System.out.println("GAME OVER");
		return true;
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
	
	public void updateBoard(String difficulty) {
		this.difficulty = difficulty;
		
		runGameFunctions(difficulty);
		main.updateDifficulty(row, col);
		main.setDifficulty(difficulty);
		buttons = main.getButton();
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

	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getTurncount() {
		return turnCount;
	}
	
	public GameBoard getBoard() {
		return this.board;
	}
	
	public MainGUI getGUI() {
		return this.main;
	}
}
