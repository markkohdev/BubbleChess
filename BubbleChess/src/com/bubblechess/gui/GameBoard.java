package com.bubblechess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;





import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard extends JPanel {
	
	private BoardCell squares[][] = new BoardCell[8][8];
	private int selectedCol, selectedRow;	
	private Color c1, c2;
	
	
	
	
	private MouseAdapter pieceListener = new MouseAdapter() { 
		@Override
		public void mouseClicked(MouseEvent arg0) {
			BoardCell cell = (BoardCell) arg0.getComponent();
			if (cell.isSelected() == 1) {
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						squares[i][j].selectCell(false);
					}
				}
			}
			else {
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						squares[i][j].selectCell(false);
					}
				}
				cell.selectCell(true);
				getMoves(cell.getColumn(), cell.getRow());
				
			}			
		}
	};
	
	public GameBoard() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new GridLayout(8, 8));
		setBounds(0,0,500,500);



		
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
				// ImageIcon pawn = new ImageIcon(GameBoard.class.getResource("/com/bubblechess/gui/WhitePieces/pawn.png"));
				// label.setForeground(new Color(192,192,192));
				// label.setForeground(new Color(218,165,32));
				
					
				squares[j][i] = cell;	
				
			}
			
		}



	}
	
	// Dark Square RGB: 92, 129, 152
	// Light Square RGB: 140, 150, 155
	
	
	public GameBoard(int color) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new GridLayout(8, 8));
		setBounds(0,0,500,500);



		
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
				// ImageIcon pawn = new ImageIcon(GameBoard.class.getResource("/com/bubblechess/gui/WhitePieces/pawn.png"));
				// label.setForeground(new Color(192,192,192));
				// label.setForeground(new Color(218,165,32));
				
					
				squares[j][i] = cell;	
				
			}
			
		}
		
		
		this.addPiecesToBoard(color);
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				
				this.add(squares[j][i]);
				
			}
			
		}



	}
	

	
	
	public void addPiecesToBoard(int color) {
		
		//String[] unicode = { "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659" };
        String[] unicode = { "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F" };
        
		if(color == 1) { 
			c1 = new Color(139,69,19);
			c2 = new Color(192,192,192);
		}
		else {
			c1 = new Color(192,192,192);
			c2 = new Color(139,69,19);
		}
		
		for(int i = 0; i < 8; i++)
		{
			BoardCell currentPawnPanel = squares[i][1];
			currentPawnPanel.addChessPiece(unicode[5], c1);			
			squares[i][1] = currentPawnPanel;
			
			
			
			String pieceUni;
			switch (i) {
				case 0: pieceUni = unicode[2];
						break;
				case 1: pieceUni = unicode[4];
						break;
				case 2: pieceUni = unicode[3];
						break;
				case 3: pieceUni = unicode[1];
						break;
				case 4: pieceUni = unicode[0];
						break;
				case 5: pieceUni = unicode[3];
						break;
				case 6: pieceUni = unicode[4];
						break;
				case 7: pieceUni = unicode[2];
						break;
				default: pieceUni = unicode[2];
						break;				
					
			}
			
			BoardCell currentPiecePanel = squares[i][0];
			currentPiecePanel.addChessPiece(pieceUni, c1);
			squares[i][0] = currentPiecePanel;
		}
		
		
		
		for(int i = 0; i < 8; i++)
		{
			BoardCell currentPawnPanel = squares[i][6];
			currentPawnPanel.addChessPiece(unicode[5], c2);
			currentPawnPanel.changeListenerState(true, pieceListener);
			squares[i][6] = currentPawnPanel;
			
			String pieceUni;
			switch (i) {
				case 0: pieceUni = unicode[2];
						break;
				case 1: pieceUni = unicode[4];
						break;
				case 2: pieceUni = unicode[3];
						break;
				case 3: pieceUni = unicode[1];
						break;
				case 4: pieceUni = unicode[0];
						break;
				case 5: pieceUni = unicode[3];
						break;
				case 6: pieceUni = unicode[4];
						break;
				case 7: pieceUni = unicode[2];
						break;
				default: pieceUni = unicode[2];
						break;				
					
			}
			
			BoardCell currentPiecePanel = squares[i][7];
			currentPiecePanel.addChessPiece(pieceUni, c2);
			currentPiecePanel.changeListenerState(true, pieceListener);
			squares[i][7] = currentPiecePanel;
			
			
		}
		
		
		
	}
	
	
	public void setSelCol(int c) {
		this.selectedCol = c;
	}
	
	public void setSelRow(int r) {
		this.selectedRow = r;
	}
	
	public int getSelRow() { 
		return this.selectedRow;
	}
	
	public int getSelCol() { 
		return this.selectedCol;
	}
	
	
	
	public void getMoves(int column, int row) {
		
		
	}
	
	

	

}
