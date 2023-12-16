package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.plaf.FontUIResource;

public class InstructionsPanel {

	private JFrame instructions;

	public InstructionsPanel() {
		instructions = initializeInstructionsFrame();
	}

	private JFrame initializeInstructionsFrame() {
		JFrame frame = new JFrame("Instructions");

		JTextArea instructions = new JTextArea();
		instructions.setText("                    Memory Game Instructions \n\n "
				+ "1. Objective: \n - The goal of the game is to find and uncover all\n matching pairs "
				+ "of characters on the board.\n\n 2. Rules:\n - Click on two tiles to flip them over\n"
				+ " - If the two tiles are a match they will stay visible\n - Otherwise they will "
				+ "return to being hidden\n - Choose varying difficulties and character sets\n from the "
				+ "menu bar\n\n 3. How to play:\n - Select two tiles, then click "
				+ "the continue button or\n wait for the two second timer to finish\n - Try to remember "
				+ "where symbols were located on\n the board\n - The game ends when all tiles are visible");
		instructions.setFont(new FontUIResource("Arial", Font.BOLD, 20));
		instructions.setEditable(false);
		
		frame.add(instructions);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		return frame;
	}
}
