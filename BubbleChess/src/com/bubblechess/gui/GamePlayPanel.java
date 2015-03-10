package com.bubblechess.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.bubblechess.GUIBridge;
import com.bubblechess.client.BoardPiece;

public class GamePlayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private static GameBoard board;
	
	
	
	
	// Flip Board if player is black pieces
	/**
	 * Creates gameplay panel and board using clients playerNum
	 * @param playerNum
	 */
	public GamePlayPanel() {
		int color = this.getPlayerNum();
		board = new GameBoard(color);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		board.setLocation(23, 38);	
		add(board);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				refreshBoard();
			}
		});
		btnNewButton.setBounds(743, 396, 117, 25);
		add(btnNewButton);
			
		
	}
	
	public void refreshBoard() {
		this.remove(board);
		board.refreshBoard();
		this.add(board);
		//this.invalidate();
		//this.validate();
		this.repaint();
		
	}
	
	public int getPlayerNum(){
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainWin.getBridge();		
		int playNum = bridge.GetPlayerNumber();
		return playNum;
	}
	
	public BoardPiece[][] getClientBoard() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainWin.getBridge();
		BoardPiece[][] clientBoard = bridge.GetBoard();
		return clientBoard;
	}
}
