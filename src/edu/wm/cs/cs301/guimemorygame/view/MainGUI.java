package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

import edu.wm.cs.cs301.guimemorygame.controller.ButtonActionListener;
import edu.wm.cs.cs301.guimemorygame.controller.ContinueButtonActionListener;
import edu.wm.cs.cs301.guimemorygame.model.MemoryGame;

public class MainGUI {

	private JFrame frame; 
	private JFrame gameWonFrame;
	
	private MemoryGame game;
	private JButton[][] buttonGrid;
	private JButton continueButton;
	private Buttons buttons;
	private String difficulty;
	private JLabel turnLabel;
		
	public MainGUI(MemoryGame game) {
		this.difficulty = "medium";
		this.game = game;
		
		frame = createMainGUI();
		
		buttons = new Buttons(game, game.getBoard(), buttonGrid, continueButton);
			
	}
	
	private JFrame createMainGUI() {
		JFrame gameFrame = new JFrame("Memory Game");
		
		gameFrame.setSize(600, 800);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameFrame.setJMenuBar(createMenuBar());
		gameFrame.add(createButtonLayout(4, 7), BorderLayout.CENTER);
		gameFrame.add(createTurnCount(), BorderLayout.NORTH); 
		gameFrame.add(createContineButton(), BorderLayout.SOUTH);
				
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
				
		return gameFrame;
	}
	
	// creates the menu bar at the top with difficulties 
	private JMenuBar createMenuBar() { // need to add fonts down the line
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Difficulty");
		menuBar.add(menu);
		
		JMenuItem easyDifficulty = new JMenuItem("Easy");
		easyDifficulty.addActionListener(e -> {game.updateBoard("easy");});
		menu.add(easyDifficulty);
				
		JMenuItem mediumDifficulty = new JMenuItem("Medium");
		mediumDifficulty.addActionListener(e -> {game.updateBoard("medium");});
		menu.add(mediumDifficulty);
		
		JMenuItem hardDifficulty = new JMenuItem("Hard");
		hardDifficulty.addActionListener(e -> {game.updateBoard("hard");});
		menu.add(hardDifficulty);
		
		JMenu characterSet = new JMenu("Character Set");
		menuBar.add(characterSet);
		
		JMenuItem english = new JMenuItem("English Alphabet");
		english.addActionListener(e -> {game.setCharSet("1"); game.updateBoard(difficulty);});
		characterSet.add(english);
		
		JMenuItem hebrew = new JMenuItem("Hebrew Alphabet");
		hebrew.addActionListener(e -> {game.setCharSet("0"); game.updateBoard(difficulty);});
		characterSet.add(hebrew);
		
		JMenu exitComponent = new JMenu("Exit");
		menuBar.add(exitComponent);
		
		JMenuItem exitGame = new JMenuItem("Exit Game"); 
		exitComponent.add(exitGame);
		exitGame.addActionListener(e -> {System.exit(0);}); // exit the program 

		
		return menuBar;
	}
	
	private JPanel createTurnCount() {
		JPanel turnCount = new JPanel(new BorderLayout());
		turnLabel = new JLabel("Turn Count: 1");
		turnCount.add(turnLabel);
		turnLabel.setHorizontalAlignment(JLabel.CENTER);
		turnLabel.setFont(new FontUIResource("Arial", Font.BOLD, 25));
		return turnCount;
		
	}
	
	// creates the grid of buttons
	private JPanel createButtonLayout(int row, int col) {
		buttonGrid = new JButton[row][col];
		JPanel buttonLayout = new JPanel(new BorderLayout());
		buttonLayout.setLayout(new GridLayout(row, col));

		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				ButtonActionListener listener = new ButtonActionListener(game, x, y);
				JButton button = new JButton("?");
				buttonGrid[x][y] = button;
				button.addActionListener(listener);
				button.setBackground(Color.red);
				button.setFont(new FontUIResource("Arial", Font.BOLD, 40));
				

				buttonLayout.add(buttonGrid[x][y]);
			}
		}

		return buttonLayout;
	}
	
	public void updateDifficulty(int x, int y, String difficulty) {
		this.difficulty = difficulty;
		
		frame.getContentPane().removeAll();

		frame.setJMenuBar(createMenuBar());
		frame.add(createButtonLayout(x, y), BorderLayout.CENTER);
		frame.add(createTurnCount(), BorderLayout.NORTH); 
		frame.add(createContineButton(), BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);

		frame.revalidate();
		frame.setSize(600, 800);

		buttons = new Buttons(game, game.getBoard(), buttonGrid, continueButton);
		
	}
	
	private JButton createContineButton() {
		ContinueButtonActionListener listener = new ContinueButtonActionListener(game);
		continueButton = new JButton("Continue");
		continueButton.addActionListener(listener);
		continueButton.setEnabled(false);
		return continueButton;
	}
	
	public void updateTurnLabel() {
		turnLabel.setText("Turn Count: " + game.getTurncount());
	}
	
	public JFrame createGameWonFrame() {
		JFrame frame = new JFrame("Game Won");
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(createTurnCountGameWon(), BorderLayout.NORTH);
		frame.add(createGameWonButtons(), BorderLayout.CENTER);
		
		gameWonFrame = frame;
		return frame;
	}
	
	private JPanel createTurnCountGameWon() {
		JPanel count = new JPanel(new BorderLayout());
		JLabel label = new JLabel("You won the game in " + game.getTurncount() + " moves.");
		count.add(label);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new FontUIResource("Arial", Font.BOLD, 25));
		count.setBackground(Color.LIGHT_GRAY);
		return count;
	}
	
	private JPanel createGameWonButtons() {
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.setBackground(Color.LIGHT_GRAY);
		JButton playAgain = new JButton("Play Again");
		JButton exit = new JButton("Exit Game");
		playAgain.addActionListener(e -> {game.updateBoard(difficulty);  gameWonFrame.dispose();});
		exit.addActionListener(e -> {System.exit(0);});
		
		playAgain.setFont(new FontUIResource("Arial", Font.BOLD, 40));
		exit.setFont(new FontUIResource("Arial", Font.BOLD, 40));
		
		buttons.add(playAgain);
		buttons.add(exit);

		return buttons;
	}
	
	public JButton getContinueButton() {
		return continueButton;
	}

	public Buttons getButton() {
		return buttons;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
}
