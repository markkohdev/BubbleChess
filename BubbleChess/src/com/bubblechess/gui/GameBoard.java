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

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard extends JPanel {
	
	private BoardCell squares[][];
	private int selectedCol, selectedRow;	
	private Color c1, c2;
	protected int width;
	protected int height;
	protected GamePlayPanel game;
	
	/**
	 * Constructor of GameBoard, adding pieces to board with players color on proper side
	 * @param color
	 */
	public GameBoard(GamePlayPanel game, int color){
		this.game = game;
		
		this.width = 8;
		this.height = 8;
		squares = new BoardCell[width][height];
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new GridLayout(8, 8));
		setBounds(0,0,500,500);
	
		//White
		if (color == 1) {
			for(int row = height-1; row >= 0; row--)
			{
				for(int col = 0; col < width; col++)
				{
					//Create empty cells with the appropriate cell color
					BoardCell cell = new BoardCell(this);
					cell.setColumn(col);
					cell.setRow(row);
					if((col+row)%2 != 0) {
						cell.setBackColor(Color.white);	
					}
					else {
						cell.setBackColor(Color.black);
					}
					
					squares[col][row] = cell;
					this.add(cell);
				}
				
			}
		}
		else {
			for(int row = 0; row < height; row++)
			{
				for(int col = width -1; col >=0; col--)
				{
					//Create empty cells with the appropriate cell color
					BoardCell cell = new BoardCell(this);
					cell.setColumn(col);
					cell.setRow(row);
					if((col+row)%2 != 0) {
						cell.setBackColor(Color.white);	
					}
					else {
						cell.setBackColor(Color.black);
					}
					
					squares[col][row] = cell;
					this.add(cell);
				}
				
			}
		}
		
	}
	
	public void RefreshBoard(BoardPiece[][] board){
		//Clear the board
		//this.removeAll();
		
		for (int col=0; col <board.length; col ++){
			for (int row = 0; row < board[col].length; row++){
				
				squares[col][row].ClearPiece();
				squares[col][row].SetPiece(board[col][row]);
				
			}
		}
	}
	
	public void CellClicked(int col, int row) {
		System.out.println("Square clicked: ("+col +"," + row +")");
		game.SquareClicked(col, row);
	}
	
	public void HighlightSquares(int[][] highlight) {
		for(int i = 0; i < highlight.length; i++){
			int col = highlight[i][0];
			int row = highlight[i][1];
			squares[col][row].highlightCell(true);
		}
	}
	
	public boolean SquareIsHighlighted(int col, int row){
		return squares[col][row].isHighlighted();
	}

	public void clearHighlights() {
		for (int col=0; col <width; col ++){
			for (int row = 0; row < height; row++){
				squares[col][row].highlightCell(false);
			}
		}
	}




}
