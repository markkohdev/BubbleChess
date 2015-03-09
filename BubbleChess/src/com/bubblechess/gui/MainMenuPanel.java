package com.bubblechess.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.omg.CORBA.portable.InputStream;

import java.awt.Font;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel {

	/**
	 * Create the panel.
	 * 
	 */
	public MainMenuPanel() {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		JButton btnCreateGame = new JButton("Create Game");
		btnCreateGame.setBounds(412, 246, 152, 58);
		add(btnCreateGame);
		
		JButton btnJoinGame = new JButton("Join Game");
		btnJoinGame.setBounds(412, 341, 152, 58);
		add(btnJoinGame);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(412, 428, 152, 58);
		add(btnLogout);
		
		JLabel lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblMainMenu.setBounds(409, 90, 206, 70);
		lblMainMenu.setEnabled(false);
		add(lblMainMenu);
		


		

	}
}
