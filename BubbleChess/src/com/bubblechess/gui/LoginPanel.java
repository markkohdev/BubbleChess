package com.bubblechess.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import com.bubblechess.GUIBridge;

public class LoginPanel extends JPanel {
	
	
	private JTextField textUsername;
	private JPasswordField passwordField;
	private JLabel lblErrorLabel;
	/**
	 * Login states: 0 Waiting, 1 TryLogin, 2 CreateUser, 3 Continue as guest
	 */
	private int loginState;

	

	/**
	 * Default Constructor
	 * Create the panel. Adds Listeners for all 3 Buttons and changes the login state when activated
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
				if (getUserName().equals("")) {
					setErrorLabel("No Username Was Entered");		
				}
				else if(getPassword().equals("")) {
					setErrorLabel("No Password Was Entered");
				}
				else {
					tryLogin();
				}
				
			}
		});
		btnLogin.setBounds(625, 297, 89, 23);
		add(btnLogin);
		
		JTextPane txtpnDontHaveAn = new JTextPane();
		txtpnDontHaveAn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				continueAsGuest();
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
				goToRegister();
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
		
		lblErrorLabel = new JLabel();
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblErrorLabel.setBounds(423, 185, 70, 15);
		


	}
	
	
	public void tryLogin() {
		MainApplicationWindow appWin = (MainApplicationWindow)this.getParent();
		String userName = getUserName();
		String password = getPassword();
		
		int result = appWin.bridge.Login(userName, password);
		
		if(result >= 0) {
			appWin.setPaneResult(3);
		}
		else if(result == -1) {
			setErrorLabel("Incorrect Password");
		}
		else if(result == -2) {
			setErrorLabel("User not found");
		}
	}
	
	public void continueAsGuest() { 
		MainApplicationWindow appWin = (MainApplicationWindow)this.getParent();
		int result = appWin.bridge.ContinueAsGuest();
		
		if(result >= 0) { 
			appWin.setPaneResult(3);
		}
		else {
			setErrorLabel("Error creating guest account");
		}
		 
	}
	
	public void goToRegister() {
		MainApplicationWindow appWin = (MainApplicationWindow)this.getParent();
		appWin.setPaneResult(2);
	}
	
	/**
	 * Chenges the login state and fires off a property Change
	 * @param state
	 */
	public void setLoginState(int state) {
		int oldValue = this.loginState;
		this.loginState = state;
		firePropertyChange("loginState", oldValue, this.loginState);
	}
	

	/**
	 * Creates a label with red text and adds it to panel to display error
	 * @param msg
	 */
	public void setErrorLabel(String msg) {
		this.lblErrorLabel.setText(msg);
	}
	
	/**
	 * gets login state
	 * @return loginState
	 */
	public int getLoginState() {
		return this.loginState;
	}
	/**
	 * gets username entered in textfield
	 * @return username
	 */
	public String getUserName() { 
		return this.textUsername.getText();
	}
	
	/**
	 * returns password as string from password field
	 * @return
	 */
	public String getPassword() {
		String pass = new String(this.passwordField.getPassword());
		return pass;
	}

}
