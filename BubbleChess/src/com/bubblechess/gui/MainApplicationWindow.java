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

	/**
	 * sets the JFrame of the window as visible
	 */
	public void setFrameVisible() { 
		this.frame.setVisible(true);
	}
	
	/**
	 * Removes panel received from the frame
	 * @param panel
	 */
	public void removePanel(JPanel panel) {
		this.frame.remove(panel);
	}
	/**
	 * adds panel received as param to the frame
	 * @param panel
	 */
	public void addPanel(JPanel panel) {
		this.frame.add(panel);		
	}
	/**
	 * Sets panel received as paramter to visible or not
	 * @param panel
	 * @param b
	 */
	public void setVisiblePanel(JPanel panel, boolean b) {
		panel.setVisible(b);
	}
	
	/** 
	 * Starts the login Panel. Property Listens is watching for when the loginState
	 * of the LoginPanel is changed the the change event is fired off.
	 * @param p
	 */
	public void startLogin(LoginPanel p) {
		p.addPropertyChangeListener("loginState", new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) { 
				int newStateValue = (int) evt.getNewValue();
				// if value is 1 it will try to login and start main menu if sucess
				if(newStateValue == 1) {
					LoginPanel lP = (LoginPanel) evt.getSource();
					int result = loginFunction(lP);
					if(result >= 0) { 
						startMainMenu();
						
					}
					
					
				}
				// fires off create new user screen
				else if(newStateValue == 2) {
					
				}
				// fires off continue as guest and if sucess starts main menu
				else if(newStateValue == 3) {
					int result = continueAsGuest();
					
					if(result >= 0) {
						startMainMenu();
					}
					
				}
			}
		});
	}
	
	/**
	 * Tries to login to server using login function from GUIBridge.
	 * @param lP
	 * @returns 0 Sucess, -1 Incorrect Pass, -2 User Not Found
	 */
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
	
	/**
	 * calls Continue as guest from bridge
	 * @return 0 success, -1 failure
	 */
	public int continueAsGuest() { 
		return bridge.ContinueAsGuest();
	}
	
	public int registerUser() { 
		return 0;
	}
	
	
	public void startMainMenu() {
		
		
	}
	



}
