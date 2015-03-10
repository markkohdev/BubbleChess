package com.bubblechess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class RegisterPanel extends JPanel {

	private JTextField textNewUsername;
	private JTextField newPasswordField;
	/**
	 * Create the panel.
	 */
	public RegisterPanel() {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		textNewUsername = new JTextField();
		textNewUsername.setBounds(423, 242, 192, 20);
		add(textNewUsername);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (getUserName().equals("")) {
					setErrorLabel("No Username Was Entered");		
				}
				else if(getPassword().equals("")) {
					setErrorLabel("No Password Was Entered");
				}
				else {
					tryRegister();
				}
				
			}
		});
		btnRegister.setBounds(625, 297, 110, 23);
		add(btnRegister);    

		newPasswordField = new JTextField();
		newPasswordField.setBounds(423, 298, 192, 20);
		add(newPasswordField);
		
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(423, 217, 192, 14);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(423, 273, 192, 14);
		add(lblPassword);
		
		JLabel lblBubblepipeChess = new JLabel("Register User");
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(396, 71, 318, 70);
		add(lblBubblepipeChess);

	}
	
	/**
	 * gets username entered in textfield
	 * @return username
	 */
	public String getUserName() { 
		return this.textNewUsername.getText();
	}
	
	/**
	 * returns password as string from password field
	 * @return
	 */
	public String getPassword() {
		return this.newPasswordField.getText();
	}
	
	public void tryRegister() { 
		MainApplicationWindow mainWin = (MainApplicationWindow) this.getParent();
		String password = this.getPassword();
		String userName = this.getUserName();
		
		int result = mainWin.bridge.Register(userName, password);
		if (result >= 0) {
			mainWin.setPaneResult(3);
		}
		else {
			this.setErrorLabel("Something went wrong creating user");
		}
	}
	
	/**
	 * Creates a label with red text and adds it to panel to display error
	 * @param msg
	 */
	public void setErrorLabel(String msg) {
		JLabel lblErrorLabel = new JLabel(msg);
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblErrorLabel.setBounds(423, 185, 70, 15);
		add(lblErrorLabel);
	}
	
	

}
