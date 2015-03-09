package com.bubblechess.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

public class GamePlayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private GameBoard board;
	
	
	
	
	// Flip Board if player is black pieces
	/**
	 * Creates gameplay panel and board using clients playerNum
	 * @param playerNum
	 */
	public GamePlayPanel(int playerNum) {
		board = new GameBoard(playerNum);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		board.setLocation(10, 11);
		board.addPiecesToBoard(playerNum);		
		add(board);
	}
	
}
