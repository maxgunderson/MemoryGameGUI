package edu.wm.cs.cs301.guimemorygame.model;

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

import edu.wm.cs.cs301.guimemorygame.view.Buttons;
import edu.wm.cs.cs301.guimemorygame.view.LeaderBoardPanel;
import edu.wm.cs.cs301.guimemorygame.view.MainGUI;

public class MemoryGame {
	private GameBoard board;
	private String difficulty;
	private String charSet;
	private String userName;
	private Scanner scanner;
	
	private String leaderBoardData;

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

		readLeaderBoard();
		setLeaderBoardText();
		
		charSet = "1";
		difficulty = "medium";
		
		runGameFunctions(difficulty);
		
		main = new MainGUI(this);
		buttons = main.getButton();
		main.setDifficulty(difficulty);
		
		new LeaderBoardPanel(leaderBoardData);
	}

	private void runGameFunctions(String difficulty) {
		turnCount = 1;
		buttonClickedTurn = false;
		ableToClickButton = true;
		this.difficulty = difficulty;
		chooseAlphabet();
		chooseArraySize(difficulty);
		board = new GameBoard(row, col, Alpha, difficulty);
	}
	
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
	}
	
	public boolean isGameWon() {
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				if (!board.getPieceObject(x, y).isVisible()) {
					return false;
				} 
			}
		}
		return true;
	}

	public boolean isMatch(GamePiece one, GamePiece two) {
		if (one.equals(two)) {
			one.setVisible(true);
			two.setVisible(true);
			return true;
		} else {
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
		main.updateDifficulty(row, col, difficulty);
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
	private void setLeaderBoardText() {
//		main.displayLeaderBoard();
//		String value = "";
		leaderBoardData = "";
//		System.out.println("LEADERBOARD:");
		for (int i = 0; i < records.size(); i++) {
			List<String> record = records.get(i);
			for (int x = 0; x < record.size(); x++) {
				leaderBoardData += " " + record.get(x);
			}
			if (i != records.size()) { // account for extra array block
				leaderBoardData += " turns\n\n";
//				System.out.println(value + " turns\n");
			}
//			value = "";
		}
		System.out.println(leaderBoardData); 

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
	private void determineScoreForLeaderboard(String turnCount) {
		int intTurnCount = Integer.valueOf(turnCount);
		
		if ((difficulty.equals("easy")) && (intTurnCount <= Integer.valueOf(records.get(0).get(2)))) {
			writeToLeaderBoard(difficulty, userName, turnCount);
		} else if ((difficulty.equals("medium")) && (intTurnCount <= Integer.valueOf(records.get(1).get(2)))) {
			writeToLeaderBoard(difficulty, userName, turnCount);
		} else if ((difficulty.equals("hard")) && (intTurnCount <= Integer.valueOf(records.get(2).get(2)))) {
			writeToLeaderBoard(difficulty, userName, turnCount);
		}
	}
	
	public void setName(String name) {
		userName = name;
		determineScoreForLeaderboard(String.valueOf(turnCount));
		setLeaderBoardText();
	}
	
	public String getLeaderBoardData() {
		return leaderBoardData;
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
	
	public void setCharSet(String set) {
		this.charSet = set;
	}
}
