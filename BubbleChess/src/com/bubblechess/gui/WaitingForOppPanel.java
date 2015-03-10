package com.bubblechess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingForOppPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public WaitingForOppPanel(int gameID) {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		JLabel lblBubblepipeChess = new JLabel("Waiting For Opponent");
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(380, 288, 566, 70);
		add(lblBubblepipeChess);
	
		String id = Integer.toString(gameID);
		String text = "Your Game ID:  " + id;
		
		JLabel lblgameID = new JLabel(text);
		lblgameID.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblgameID.setBounds(492, 433, 191, 70);
		add(lblgameID);


	}

}
