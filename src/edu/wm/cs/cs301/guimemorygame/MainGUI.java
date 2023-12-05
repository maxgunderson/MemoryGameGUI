package edu.wm.cs.cs301.guimemorygame;

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
		
	public MainGUI(MemoryGame game) {
		this.game = game;
		this.frame = createMainGUI();
	}
	
	// creates main gui with all components
	private JFrame createMainGUI() {
		JFrame gameFrame = new JFrame("Memory Game");
		gameFrame.setSize(600, 800);
		gameFrame.setJMenuBar(createMenuBar());
		gameFrame.add(createButtonLayout());
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
	// later will need to involve lots of code to update each button with values
	private JPanel createButtonLayout() {
		JButton[][] grid = new JButton[4][7];
		JPanel buttonLayout = new JPanel();
		buttonLayout.setLayout(new GridLayout(4, 7));

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 7; y++) {
				grid[x][y] = new JButton(game.getBoard().getPieceObject(x+1, y+1).getSymbol().toString());
				buttonLayout.add(grid[x][y]);
			}
		}
	
		return buttonLayout;
		
	}
}
