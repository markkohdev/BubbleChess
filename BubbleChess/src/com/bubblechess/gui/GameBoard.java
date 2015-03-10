package com.bubblechess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;








import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bubblechess.GUIBridge;
import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.Move;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameBoard extends JPanel {
	
	private BoardCell boardCells[][];
	private int selectedCol, selectedRow;	
	
	
	
	/**
	 * Create MouseAdapter to trigger event for when pieces are clicked
	 */
	private MouseAdapter selectListener = new MouseAdapter() { 
		@Override
		public void mouseClicked(MouseEvent arg0) {
			boolean pTurn = isPlayerTurn();
			if (pTurn) {
				BoardCell cell = (BoardCell) arg0.getComponent();
				addBorderToCell(true, Color.GREEN);
				ArrayList<Move> potentialMoves = getMoves(cell);
				for(Move m : potentialMoves) {
					int mCol = m.colTo();
					int mRow = m.rowTo();
					boardCells[mCol][mRow].selectCell(true, Color.CYAN);
					boardCells[mCol][mRow].addListener(moveListner);
				}		
			}
		}			
	};
	
	private MouseAdapter moveListener = new MouseAdapter() { 
		@Override
		public void mouseClicked(MouseEvent arg0) {
			boolean pTurn isPlayerTurn();
			if (pTurn) {
				
				
			}
		}
	}
	
	// Dark Square RGB: 92, 129, 152
	// Light Square RGB: 140, 150, 155
	
	/**
	 * Constructor of GameBoard, adding pieces to board with players color on proper side
	 * @param color
	 */
	public GameBoard(BoardPiece[][] clientBoard, int color){
		boardCells = new BoardCell[8][8];
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new GridLayout(8, 8));
		setBounds(0,0,500,500);		
		this.setCellColors();
		this.addPiecesToCells(clientBoard, color);
		this.addCellsToBoard(color);
	}
	
	public void addCellsToBoard(int color) {
		if(color == 1) {
			for(int i = 0; i < 8; i++)
			{
				for(int j = 0; j < 8; j++)
				{
					
					this.add(boardCells[i][j]);
					
				}
				
			}
			
		}
		else {
			for(int i = 7; i >= 0; i--)
			{
				for(int j = 7; j >= 0; j--)
				{
					
					this.add(boardCells[i][j]);
					
				}
				
			}
			
		}		
	}
	
	public void setCellColors() {
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				
				BoardCell cell = new BoardCell();
				cell.setColumn(j);
				cell.setRow(i);
				if((i+j)%2 == 0) {
					cell.setBackColor(Color.white);	
				}
				else {
					cell.setBackColor(Color.black);
				}					
				this.boardCells[i][j] = cell;	
			}
			
		}
	}
	/**
	 * Add Pieces to board using param to determine placement of pieces
	 * @param color
	 */
	public void addPiecesToCells(BoardPiece[][] clientBoard, int color) {		
		//currentPawnPanel.changeListenerState(pieceListener);
		Color c;
		BoardPiece.Color whiteColor = BoardPiece.Color.WHITE;
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; i++) {
				BoardPiece.Color pieceColor = clientBoard[i][j].getColor();
				int pieceNumber = clientBoard[i][j].getPieceID();
				if (pieceColor.compareTo(whiteColor) == 0) {
					c = new Color(192,192,192);
				}
				else{
					c = new Color(139,69,19);
				}
				String pieceUni = this.getPieceUnicode(pieceNumber);
				boardCells[i][j].addChessPiece(pieceUni, c);
				
			}
		}
	}
	
	public String getPieceUnicode(int pieceNum) {
		String[] unicode = {"\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F" };
		String pieceUni;
		switch(pieceNum) {
			case 0: pieceUni = unicode[0];
					break;
			case 1: pieceUni = unicode[1];
					break;
			case 2: pieceUni = unicode[2];
					break;
			case 3: pieceUni = unicode[3];
					break;
			case 4: pieceUni = unicode[4];
					break;
			default: pieceUni = "";
					break;
		}
		
		return pieceUni;
		
	}
	
	public void addBorderToCell(BoardCell cell, Color c) {
		if (cell.isSelected() == 1) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					boardCells[i][j].selectCell(false, c);
				}
			}
		}
		else {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					boardCells[i][j].selectCell(true, c);
				}
			}
		}
	}
	
	public boolean isPlayerTurn() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainWin.getBridge();
		
		boolean turn = bridge.IsPlayersTurn();
		return turn;
	}
	
	public ArrayList<Move> getMoves(BoardCell cell) {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainWin.getBridge();
		int col = cell.getColumn();
		int row = cell.getRow();
		ArrayList<Move> moves = bridge.getMoves(col, row);
		
		return moves;
	}
	
	
	/**
	 * Function to start listeners for clients pieces are start of game
	 */
	public void startListeners() {
		
	}
	
	/**
	 * Set selected piece col to param
	 * @param c
	 */
	public void setSelCol(int c) {
		this.selectedCol = c;
	}
	
	
	/**
	 * Set selected piece row to param
	 * @param r
	 */
	public void setSelRow(int r) {
		this.selectedRow = r;
	}
	
	/**
	 * Get selected piece row
	 * @return selectedRow
	 */
	public int getSelRow() { 
		return this.selectedRow;
	}
	
	/**
	 * Get selected piece column
	 * @return selectedCol
	 */
	public int getSelCol() { 
		return this.selectedCol;
	}


}
