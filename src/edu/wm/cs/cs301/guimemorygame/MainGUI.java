package edu.wm.cs.cs301.guimemorygame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

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
		gameFrame.setJMenuBar(createMenuBar());
		gameFrame.add(createButtonLayout(), BorderLayout.CENTER);
		gameFrame.add(createContineButton(), BorderLayout.SOUTH);
		
		gameFrame.setVisible(true);
		
		return gameFrame;
	}
	
	// creates the menu bar at the top with difficulties 
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Difficulty");
		menuBar.add(menu);
		
		JMenuItem mediumDifficulty = new JMenuItem("Medium");
		menu.add(mediumDifficulty);
		
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
				buttonGrid[x][y] = new JButton("?");
//				buttonGrid[x][y] = new JButton(game.getBoard().getPieceObject(x, y).);

				buttonGrid[x][y].addActionListener(listener);
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

	public Buttons getButton() {
		return buttons;
	}
}
