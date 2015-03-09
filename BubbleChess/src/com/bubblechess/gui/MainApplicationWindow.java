package com.bubblechess.gui;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bubblechess.GUIBridge;

public class MainApplicationWindow {

	private JFrame frame;
	private GUIBridge bridge;

	/**
	 * Create the application.
	 */
	public MainApplicationWindow(GUIBridge b) {
		this.bridge = b;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Function to launch the application. Created from old main function.
	 */
	/*public static void launchApplicationWindow(final GUIBridge b) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplicationWindow mainWindow = new MainApplicationWindow(b);
					mainWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	public void setFrameVisible() { 
		this.frame.setVisible(true);
	}
	
	public void removePanel(JPanel panel) {
		this.frame.remove(panel);
	}
	
	public void addPanel(JPanel panel) {
		this.frame.add(panel);		
	}
	
	public void setVisiblePanel(JPanel panel, boolean b) {
		panel.setVisible(b);
	}
	
	public void startLogin(LoginPanel p) {
		p.addPropertyChangeListener("loginState", new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) { 
				int newStateValue = (int) evt.getNewValue();
				if(newStateValue == 1) {
					LoginPanel lP = (LoginPanel) evt.getSource();
					int result = loginFunction(lP);
					if(result >= 0) { 
						startMainMenu();
						
					}
					
					
				}
				else if(newStateValue == 2) {
					
				}
				else if(newStateValue == 3) {
					int result = continueAsGuest();
					
					if(result >= 0) {
						startMainMenu();
					}
					
				}
			}
		});
	}
	
	
	public int loginFunction(LoginPanel lP) {
		String user = lP.getUserName();
		String pass = lP.getPassword();
		
		int result = this.bridge.Login(user, pass);
		if(result == -1) {
			lP.setErrorLabel("Incorrect Password");
		}
		else if (result == -2) {
			lP.setErrorLabel("User Not Found");
		}
		
		
		return result;
	}
	
	public int continueAsGuest() { 
		return bridge.ContinueAsGuest();
	}
	
	public int registerUser() { 
		return 0;
	}
	
	
	public void startMainMenu() {
		
		
	}
	



}
