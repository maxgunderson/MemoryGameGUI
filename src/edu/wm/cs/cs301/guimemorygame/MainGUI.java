package edu.wm.cs.cs301.guimemorygame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

public class MainGUI {
	
	private JFrame frame; 
	private MemoryGame game;
	private JButton[][] buttonGrid;
	private Buttons buttons;
	private JButton continueButton;
	
	public MainGUI(MemoryGame game) {
		this.game = game;
		
		frame = createMainGUI();
		buttons = new Buttons(game, game.getBoard(), buttonGrid, continueButton);

	}
	
	// creates main gui with all components
	private JFrame createMainGUI() {
		JFrame gameFrame = new JFrame("Memory Game");
		
		gameFrame.setSize(600, 800);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setJMenuBar(createMenuBar());
		gameFrame.add(createButtonLayout(), BorderLayout.CENTER);
		gameFrame.add(createContineButton(), BorderLayout.SOUTH);
		
		gameFrame.setVisible(true);
		
		return gameFrame;
	}
	
	// creates the menu bar at the top with difficulties 
	private JMenuBar createMenuBar() { // need to add fonts down the line
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Difficulty");
		menuBar.add(menu);
				
		JMenuItem mediumDifficulty = new JMenuItem("Medium");
		menu.add(mediumDifficulty);
		
		JMenu exitComponent = new JMenu("Exit");
		menuBar.add(exitComponent);
		
		JMenuItem exitGame = new JMenuItem("Exit Game"); 
		exitComponent.add(exitGame);
		exitGame.addActionListener(e -> {System.exit(0);}); // exit the program 

		
		return menuBar;
	}
	
	// creates the grid of buttons
	private JPanel createButtonLayout() {
		buttonGrid = new JButton[4][7];
		JPanel buttonLayout = new JPanel(new BorderLayout());
		buttonLayout.setLayout(new GridLayout(4, 7));

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 7; y++) {
				ButtonActionListener listener = new ButtonActionListener(game, game.getBoard(), buttons, x, y);
				JButton button = new JButton("?");
				buttonGrid[x][y] = button;
				button.addActionListener(listener);
				// sick idea could be to set the foreground color to green once the continue button is clicked
//				buttonGrid[x][y].setForeground(Color.red);
				button.setBackground(Color.red);
				button.setBorderPainted(false);
				button.setFont(new FontUIResource("Arial", Font.BOLD, 40));
				

				buttonLayout.add(buttonGrid[x][y]);
			}
		}

		return buttonLayout;
	}
	
	private JButton createContineButton() {
		ContinueButtonActionListener listener = new ContinueButtonActionListener(game);
		continueButton = new JButton("Continue");
		continueButton.addActionListener(listener);
		continueButton.setEnabled(false);
		return continueButton;
	}
	
	public JButton getContinueButton() {
		return continueButton;
	}

	public Buttons getButton() {
		return buttons;
	}
	
}
