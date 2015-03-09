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
	
	/*private MouseAdapter pieceListener = new MouseAdapter() { 
		@Override
		public void mouseClicked(MouseEvent arg0) {
			BoardCell cell = (BoardCell) arg0.getComponent();
			if (cell.isSelected() == 1) {
				cell.selectCell(false);
			}
			else {				
				cell.selectCell(true);
			}
		}
	};*/
	
	private MouseAdapter moveListener = new MouseAdapter() { 
		@Override
		public void mouseClicked(MouseEvent arg0) {
			/*BoardCell cell = (BoardCell) arg0.getComponent();
			if (cell.getBorder() == null) {
				cell.selectCell(true);
			}
			else {				
				cell.selectCell(false);
			}
			cell.changeListenerState(true); */
		}
	};
	
	/**
	 * Create the panel.
	 */
	public BoardCell() {
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		this.selected = 0;
		this.haveListener = false;

	}
	
	public void setBackColor(Color c) {
		setBackground(c);
	}
	
	public void setColumn(int i) {
		this.column = i;
	}
	
	public void setRow(int i) { 
		this.row = i;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
		
	public void changeListenerState(boolean b, MouseAdapter listener) {	
		if (b) {
			this.haveListener = true;
			this.addMouseListener(listener);
		}
		else {
			this.haveListener = false;
			this.removeMouseListener(listener);
		}
	}
	
	public void tempDisableListener(boolean b, MouseAdapter listener) { 
		if (b) {
			this.removeMouseListener(listener);
		}
		else { 
			this.addMouseListener(listener);
		}
	}
	
	public boolean hasListener() { 
		return this.haveListener;
	}
	
	public int isSelected() {
		return this.selected;
	}
	
	
	public void selectCell(boolean b) {
		if (b) {
			this.setBorder(BorderFactory.createBevelBorder(1,Color.GREEN, Color.GREEN));
			this.selected = 1;
		}
		else {
			this.setBorder(BorderFactory.createEmptyBorder());
			this.selected = 0;
		}
		
	}
	
	public void addChessPiece(String uni, Color c) {
		Font pieceFont = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
		JLabel piece = new JLabel(uni);
		piece.setFont(pieceFont);
		piece.setForeground(c);
		piece.setOpaque(false);
		piece.setHorizontalAlignment(JLabel.CENTER);
		add(piece);
		
	}
	
	public void removeChessPiece() { 
		this.remove(0);
	}
	
	

}
