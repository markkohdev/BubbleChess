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
	/*
	 * Login states: 0 Waiting, 1 TryLogin, 2 CreateUser, 3 Continue as guest
	 */
	private int loginState;

	

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		this.setLoginState(0);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		textUsername = new JTextField();
		textUsername.setBounds(423, 242, 192, 20);
		add(textUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*if (getUserName().equals("")) {
					setErrorLabel("No Username Was Entered");		
				}
				else if(getPassword().equals("")) {
					setErrorLabel("No Password Was Entered");
				}
				else {
					setLoginState(1);
				}*/
				
			}
		});
		btnLogin.setBounds(625, 297, 89, 23);
		add(btnLogin);
		
		JTextPane txtpnDontHaveAn = new JTextPane();
		txtpnDontHaveAn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setLoginState(2);
			}
		});
		txtpnDontHaveAn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnDontHaveAn.setBackground(Color.LIGHT_GRAY);
		txtpnDontHaveAn.setText("Don't Have An Account Register Here");
		txtpnDontHaveAn.setBounds(423, 331, 291, 20);
		add(txtpnDontHaveAn);
		
		JTextArea txtrOr = new JTextArea();
		txtrOr.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtrOr.setBackground(Color.LIGHT_GRAY);
		txtrOr.setText("_______________________________________________ OR ___________________________________________________________");
		txtrOr.setBounds(10, 401, 1004, 51);
		add(txtrOr);
		
		JButton btnContinueAsGuest = new JButton("Continue As Guest");
		btnContinueAsGuest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setLoginState(3);
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
	
	public void setLoginState(int state) {
		int oldValue = this.loginState;
		this.loginState = state;
		firePropertyChange("loginState", oldValue, this.loginState);
	}
	

	public void setErrorLabel(String msg) {
		JLabel lblErrorLabel = new JLabel(msg);
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblErrorLabel.setBounds(423, 185, 70, 15);
		this.add(lblErrorLabel);
	}
	
	public int getLoginState() {
		return this.loginState;
	}
	
	public String getUserName() { 
		return this.textUsername.getText();
	}
	
	public String getPassword() {
		return Arrays.toString(this.passwordField.getPassword());
	}

}
