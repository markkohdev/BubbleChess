package com.bubblechess.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

public class GamePlayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private GameBoard board = new GameBoard(1);
	
	
	public GamePlayPanel() {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		board.setLocation(10, 11);
		board.addPiecesToBoard(1);

		
		add(board);
		

		

	}
	
}
