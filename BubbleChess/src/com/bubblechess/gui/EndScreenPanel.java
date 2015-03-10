package com.bubblechess.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndScreenPanel extends JPanel {

	/**
	 * Creates the end screen panel and button to go to main menu
	 * Create the panel.
	 */
	public EndScreenPanel() {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		 
		JLabel lblBubblepipeChess = new JLabel("Game Over");
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(394, 79, 318, 70);
		add(lblBubblepipeChess);
		
		
		
		JButton btnGoToMainMenu = new JButton("Go To Main Menu");
		btnGoToMainMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainApplicationWindow appWin = MainApplicationWindow.getInstance();
				appWin.setPaneResult(3);
			}
		});
		btnGoToMainMenu.setBounds(412, 341, 152, 58);
		add(btnGoToMainMenu);

	}
	
}


