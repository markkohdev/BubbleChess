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

import javax.swing.DefaultListModel;
import javax.swing.JLayeredPane;
import javax.swing.JList;

public class GamePlayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private static GameBoard board;
	private DefaultListModel p1CaptureListModel;
	private DefaultListModel p2CaptureListModel; 
	private GUIBridge bridge;
	
	
	
	// Flip Board if player is black pieces
	/**
	 * Creates gameplay panel and board using clients playerNum
	 * @param playerNum
	 */
	public GamePlayPanel() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainWin.getBridge();
		int color = this.getPlayerNum();
		BoardPiece[][] clientBoard = this.getClientBoard();
		board = new GameBoard(clientBoard, color);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		board.setLocation(23, 38);	
		add(board);
		p1CaptureListModel = new DefaultListModel();
		p2CaptureListModel = new DefaultListModel();
		JList p1CaptureList = new JList(p1CaptureListModel);
		p1CaptureList.setBounds(900, 38, 100, 250);
		add(p1CaptureList);
		
		JList p2CaptureList = new JList(p2CaptureListModel);
		p2CaptureList.setBounds(900, 328, 100, 250);
		add(p2CaptureList);
	}
	
	public int getPlayerNum(){	
		int playNum = bridge.GetPlayerNumber();
		return playNum;
	}
	
	public BoardPiece[][] getClientBoard() {
		BoardPiece[][] clientBoard = bridge.GetBoard();
		return clientBoard;
	}
	
	public boolean isPlayerTurn() {
		boolean turn = bridge.IsPlayersTurn();
		return turn;
	}
	
	public void updateCapturedList() {
		BoardPiece[] piecesCaptured = bridge.GetCaptured();
		int pNum = getPlayerNum();
		BoardPiece.Color pColor;
		pColor = BoardPiece.Color.WHITE;
		for(BoardPiece piece : piecesCaptured) {
			int pieceNum = piece.getPieceID();
			BoardPiece.Color pieceColor = piece.getColor();
			String[] pieces = {"King", "Queen", "Rook", "Bishop", "Knight", "Pawn" };
			if (pColor.equals(pieceColor)) {
				p1CaptureListModel.addElement(pieces[pieceNum]);
			}
			else {
				p2CaptureListModel.addElement(pieces[pieceNum]);
			}
		}
	}
}
