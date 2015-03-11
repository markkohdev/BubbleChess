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
	private MainApplicationWindow appWin;
	private JLabel lblErrorLabel;
	private GUIBridge bridge;
	/**
	 * Login states: 0 Waiting, 1 TryLogin, 2 CreateUser, 3 Continue as guest
	 */
	//private int loginState;

	

	/**
	 * Default Constructor
	 * Create the panel. Adds Listeners for all 3 Buttons and changes the login state when activated
	 */
	public LoginPanel() {
		appWin = MainApplicationWindow.getInstance();
		bridge = appWin.getBridge();
		//this.setLoginState(0);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		JLabel lblBubblepipeChess = new JLabel("BubblePipe Chess",JLabel.CENTER);
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(0, 150, 1024, 70);
		add(lblBubblepipeChess);
		
		
		textUsername = new JTextField();
		textUsername.setBounds(423, 242, 192, 20);
		add(textUsername);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(412, 217, 200, 14);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(412, 273, 200, 14);
		add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(423, 298, 192, 20);
		add(passwordField);
		
		
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
		btnLogin.setBounds(437, 350, 150, 20);
		add(btnLogin);

		JLabel txtrOr = new JLabel("OR",JLabel.CENTER);
		txtrOr.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtrOr.setBounds(0, 375, 1024, 70);
		add(txtrOr);
		
		JButton btnRegister = new JButton("Click Here To Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				goToRegister();
			}
		});
		btnRegister.setBounds(387, 450, 250, 20);
		add(btnRegister);

		lblErrorLabel = new JLabel();
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblErrorLabel.setBounds(423, 185, 70, 15);
	
	}
	
	/**
	 * function to try to login using the function on the GUIBridge
	 * if error occurs, it displays it on screen
	 */
	public void tryLogin() {
		String userName = getUserName();
		String password = getPassword();
		
		int result = bridge.Login(userName, password);
		
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
	
	
	/**
	 * sets next pane to register pane
	 */
	public void goToRegister() {
		appWin.setPaneResult(2);
	}
	

	/**
	 * Creates a label with red text and adds it to panel to display error
	 * @param msg
	 */
	public void setErrorLabel(String msg) {
		this.lblErrorLabel.setText(msg);
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
