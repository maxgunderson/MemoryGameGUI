package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.FontUIResource;

public class LeaderBoardPanel {
	private JFrame leaderBoard;
	private String data;
	
	public LeaderBoardPanel(String data) {
		this.data = data;
		createFrame();
	}
	
	private void createFrame() {
		leaderBoard = new JFrame("LEADERBOARD");
		leaderBoard.setSize(500, 200);
		leaderBoard.add(createTextArea());
		leaderBoard.setLocationRelativeTo(null);
		leaderBoard.setVisible(true);
		
	}
	
	private JPanel createTextArea() {
		JPanel panel = new JPanel(new BorderLayout());
		JTextArea text = new JTextArea();
		JScrollPane scroll = new JScrollPane(text);

		text.setText(data);
		text.setFont(new FontUIResource("Arial", Font.BOLD, 20));
	    text.setMargin(new Insets(20, 20, 20, 20)); 
	    text.setCaretPosition(0);
		text.setEditable(false);
		
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}

}
