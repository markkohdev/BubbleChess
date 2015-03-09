package com.bubblechess.gui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPasswordField;

import com.bubblechess.GUIBridge;

public class LoginPanel extends JPanel {
	private JTextField textUsername;
	private JPasswordField passwordField;

	

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("deprecation")
	public LoginPanel(final GUIBridge bridge) {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		textUsername = new JTextField();
		textUsername.setBounds(423, 242, 192, 20);
		add(textUsername);
		textUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					
				
			}
		});
		btnLogin.setBounds(625, 297, 89, 23);
		add(btnLogin);
		
		JTextPane txtpnDontHaveAn = new JTextPane();
		txtpnDontHaveAn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		txtpnDontHaveAn.setForeground(Color.BLUE);
		txtpnDontHaveAn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnDontHaveAn.setBackground(Color.LIGHT_GRAY);
		txtpnDontHaveAn.setText("Don't Have An Account Register Here");
		txtpnDontHaveAn.setBounds(423, 331, 291, 20);
		add(txtpnDontHaveAn);
		
		JTextArea txtrOr = new JTextArea();
		txtrOr.setFont(new Font("Monospaced", Font.BOLD, 18));
		txtrOr.setBackground(Color.LIGHT_GRAY);
		txtrOr.setText("_______________________________________________ OR ___________________________________________________________");
		txtrOr.setBounds(10, 401, 1004, 51);
		add(txtrOr);
		
		JButton btnContinueAsGuest = new JButton("Continue As Guest");
		btnContinueAsGuest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnContinueAsGuest.setBounds(423, 508, 291, 23);
		add(btnContinueAsGuest);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(423, 217, 192, 14);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(423, 273, 192, 14);
		add(lblPassword);
		
		JLabel lblBubblepipeChess = new JLabel("BubblePipe Chess");
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(396, 71, 318, 70);
		add(lblBubblepipeChess);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(423, 298, 192, 20);
		add(passwordField);


	}
	
	
	/*
	@SuppressWarnings("deprecation")
	public int loginFunction() {
		
		int loginResult = null;
		
		String userName = textUsername.getText();
		String password = Arrays.toString(passwordField.getPassword());
		if(userName == null || password == null) {
			// change warning label to say nothing entered
		}
		else {
			loginResult = bridge.Login(userName, password);		
			
		}

		if(loginResult == 0) {
			return 0;
			
		}
		else if(loginResult == (-2)) {
			// change error label to user not found
		}
		else if(loginResult == (-1)) {
			// change error label to user not found
			
		}
		else if(loginResult > 0) {
			// change error label unknown result code
		}
		
	}*/
}
