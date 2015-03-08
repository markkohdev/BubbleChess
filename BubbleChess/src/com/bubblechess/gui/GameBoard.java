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
	
	private JPanel squares[][] = new JPanel[8][8];
	
	private Font pieceFont = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
	
	private MouseAdapter squareListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JPanel panel = (JPanel) arg0.getComponent();
			panel.setBorder(BorderFactory.createBevelBorder(1,Color.green, Color.GREEN));
			
			
			
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
				
				JPanel p = new JPanel();
				p.setLayout(new BorderLayout());
				if((i+j)%2 == 0) {
					p.setBackground(Color.white);
					
				}
				else {
					p.setBackground(Color.black);
				}
				// ImageIcon pawn = new ImageIcon(GameBoard.class.getResource("/com/bubblechess/gui/WhitePieces/pawn.png"));
				// label.setForeground(new Color(192,192,192));
				// label.setForeground(new Color(218,165,32));
				
					
				squares[i][j] = p;	
				
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
				
				JPanel p = new JPanel();
				p.setLayout(new BorderLayout());
				if((i+j)%2 == 0) {
					p.setBackground(Color.white);
					
				}
				else {
					p.setBackground(Color.black);
				}
				// ImageIcon pawn = new ImageIcon(GameBoard.class.getResource("/com/bubblechess/gui/WhitePieces/pawn.png"));
				// label.setForeground(new Color(192,192,192));
				// label.setForeground(new Color(218,165,32));
				
					
				squares[i][j] = p;	
				
			}
			
		}
		
		
		this.addPiecesToBoard(color);
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				
				this.add(squares[i][j]);
				
			}
			
		}



	}
	

	
	
	public void addPiecesToBoard(int color) {
		
		Color c1, c2;
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
			JPanel currentPawnPanel = squares[1][i];
			JLabel pawnlabel = new JLabel(unicode[5]);
			pawnlabel.setForeground(c1);
			pawnlabel.setFont(pieceFont);
			pawnlabel.setOpaque(false);
			pawnlabel.setHorizontalAlignment(JLabel.CENTER);
			//pawnlabel.addMouseListener(pieceListener);
			currentPawnPanel.add(pawnlabel);
			currentPawnPanel.addMouseListener(squareListener);
			
			squares[1][i] = currentPawnPanel;
			
			
			
			String pieceUni;
			switch (i) {
				case 0: pieceUni = unicode[2];
						break;
				case 1: pieceUni = unicode[4];
						break;
				case 2: pieceUni = unicode[3];
						break;
				case 3: pieceUni = unicode[0];
						break;
				case 4: pieceUni = unicode[1];
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
			
			JPanel currentPiecePanel = squares[0][i];
			JLabel label = new JLabel(pieceUni);
			label.setForeground(c1);
			label.setFont(pieceFont);
			label.setOpaque(false);
			label.setHorizontalAlignment(JLabel.CENTER);
			//label.addMouseListener(pieceListener);
			currentPiecePanel.add(label);
			currentPiecePanel.addMouseListener(squareListener);
			squares[0][i] = currentPiecePanel;
		}
		
		for(int i = 0; i < 8; i++)
		{
			JPanel currentPawnPanel = squares[6][i];
			JLabel pawnlabel = new JLabel(unicode[5]);
			pawnlabel.setForeground(c2);
			pawnlabel.setFont(pieceFont);
			pawnlabel.setOpaque(false);
			pawnlabel.setHorizontalAlignment(JLabel.CENTER);
			// pawnlabel.addMouseListener(pieceListener);
			currentPawnPanel.add(pawnlabel);
			squares[6][i] = currentPawnPanel;
			
			String pieceUni;
			switch (i) {
				case 0: pieceUni = unicode[2];
						break;
				case 1: pieceUni = unicode[4];
						break;
				case 2: pieceUni = unicode[3];
						break;
				case 3: pieceUni = unicode[0];
						break;
				case 4: pieceUni = unicode[1];
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
			
			JPanel currentPiecePanel = squares[7][i];
			JLabel label = new JLabel(pieceUni);
			label.setForeground(c2);
			label.setFont(pieceFont);
			label.setOpaque(false);
			label.setHorizontalAlignment(JLabel.CENTER);
			
			//label.addMouseListener(pieceListener);
			currentPiecePanel.add(label);
			currentPiecePanel.addMouseListener(squareListener);
			squares[7][i] = currentPiecePanel;
			
			
		}
		
		
		
	}
	
	
	
	public boolean movePiece() { 
		return true;
	}
	
	

	

}
