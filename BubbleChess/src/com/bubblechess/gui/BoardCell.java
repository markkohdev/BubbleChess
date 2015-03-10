package com.bubblechess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardCell extends JPanel {

	private int column;
	private int row;
	private boolean haveListener;
	private int selected;

	/*
	 * Below Comment Block not used. Will be Taken Out later on.
	 * 
	 * private MouseAdapter pieceListener = new MouseAdapter() {
	 * 
	 * @Override public void mouseClicked(MouseEvent arg0) { BoardCell cell =
	 * (BoardCell) arg0.getComponent(); if (cell.isSelected() == 1) {
	 * cell.selectCell(false); } else { cell.selectCell(true); } } };
	 *

	private MouseAdapter moveListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			 BoardCell cell = (BoardCell) arg0.getComponent(); if
			 (cell.getBorder() == null) { cell.selectCell(true); } else {
			 cell.selectCell(false); } cell.changeListenerState(true);
			 
		}
	}; */

	/**
	 * Constructor
	 * Create the panel.
	 */
	public BoardCell() {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		this.selected = 0;
		this.haveListener = false;

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
		this.column = i;
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
		return this.column;
	}
	

	/**
	 * Changes haveListen to true/false and adds/removes MouseAdapter param as mouse listener for cell
	 * @param b
	 * @param listener
	 */
	public void changeListenerState(MouseAdapter listener) {
			this.addMouseListener(listener);
	}

	/* Function not used. changeListenState should be used instead.
	 * Keeping function in case needed later on.
	 * 
	public void tempDisableListener(boolean b, MouseAdapter listener) {
		if (b) {
			this.removeMouseListener(listener);
		} else {
			this.addMouseListener(listener);
		}
	} */

	/**
	 * Checks if cell has listener
	 * @return true Cell has listener added, false Call does not have listener added
	 */
	public boolean hasListener() {
		return this.haveListener;
	}

	/**
	 * Checks if cell is selected
	 * @return 0 Not Select, 1 Selected
	 */
	public int isSelected() {
		return this.selected;
	}

	/**
	 * Adds selection border to screen
	 * @param b
	 */
	public void selectCell(boolean b) {
		if (b) {
			this.setBorder(BorderFactory.createBevelBorder(1, Color.GREEN,
					Color.GREEN));
			this.selected = 1;
		} else {
			this.setBorder(BorderFactory.createEmptyBorder());
			this.selected = 0;
		}

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

	/**
	 * Removes chess piece(JLabel) from cell.
	 * @param piece
	 */
	public void removeChessPiece(JLabel piece) {
		this.remove(piece);
	}

}
