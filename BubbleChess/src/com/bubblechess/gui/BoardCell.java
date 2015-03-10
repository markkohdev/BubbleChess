package com.bubblechess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.BoardPiece.PieceColor;;

public class BoardCell extends JPanel {

	private int col;
	private int row;
	private boolean haveListener;
	private boolean highlighted;
	private GameBoard board;

	/**
	 * Constructor
	 * Create the panel.
	 */
	public BoardCell(GameBoard board) {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		this.highlighted = false;
		this.haveListener = false;
		this.board = board;
		
		
		addMouseListener(new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                background = getBackground();
                setBackground(Color.CYAN);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	setBackground(background);
            	AlertClicked();
            	
            }
        });

	}
	
	/**
	 * Tell the board that the current cell was clicked
	 */
	public void AlertClicked(){
		this.board.CellClicked(col, row);
	}

	/**
	 * Sets Background color or Cell to param
	 * @param c
	 */
	public void setBackColor(Color c) {
		setBackground(c);
	}

	/**
	 * Sets column number of cell to i
	 * @param i
	 */
	public void setColumn(int i) {
		this.col = i;
	}

	/**
	 * sets row numer of cell to i
	 * @param i
	 */
	public void setRow(int i) {
		this.row = i;
	}

	/**
	 * Get cell Row
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Get cell column
	 * @return column
	 */
	public int getColumn() {
		return this.col;
	}
	
	
	public void highlightCell(boolean highlight) {
		if (highlight) {
			this.setBorder(BorderFactory.createBevelBorder(1, Color.GREEN,
					Color.GREEN));
			this.highlighted = true;
		} else {
			this.setBorder(BorderFactory.createEmptyBorder());
			this.highlighted = false;
		}
	}
	
	public boolean isHighlighted(){
		return highlighted;
	}

	/**
	 * Adds chess piece unicode as JLabel to cell
	 * @param uni
	 * @param c
	 */
	public void addChessPiece(String uni, Color c) {
		Font pieceFont = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
		JLabel piece = new JLabel(uni);
		piece.setFont(pieceFont);
		piece.setForeground(c);
		piece.setOpaque(false);
		piece.setHorizontalAlignment(JLabel.CENTER);
		piece.setVisible(true);
		// piece.setVisible(true);
		add(piece);
		//this.revalidate();
		//this.repaint();
		//this.validate();
	}
	
	
	public void ClearPiece() {
		this.removeAll();
		this.highlightCell(false);
	}

	public void SetPiece(BoardPiece boardPiece) {
		
		if (boardPiece != null) {
			// unicode	 =		{"king",   "queen",  "rook",   "bishop", "knight", "pawn" }  
	        String[] pieceCodes = {"\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F" };
			
			int PieceType = boardPiece.getPieceID();
			
			//If we have a piece, set it up
			if (PieceType != -1) {
				Color c;
				
				//Set up the color
				if (boardPiece.getColor() == PieceColor.WHITE) {
					c = new Color(192,192,192);
				}
				else {
					c = new Color(139,69,19);
				}
				
				Font pieceFont = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
				JLabel piece = new JLabel(pieceCodes[PieceType]);
				piece.setFont(pieceFont);
				piece.setForeground(c);
				piece.setOpaque(false);
				piece.setHorizontalAlignment(JLabel.CENTER);
				piece.setVisible(true);
				add(piece);
			}
		}
	}

}
