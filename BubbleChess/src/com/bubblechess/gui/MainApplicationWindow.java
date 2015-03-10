package com.bubblechess.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bubblechess.GUIBridge;
import com.bubblechess.client.ServerHandler;

public class MainApplicationWindow extends JFrame {
	
	private static MainApplicationWindow instance = null;	
	// private JFrame frame;
	private GUIBridge bridge;
	private int paneResult;
	private JPanel previousPanel;
	private int gamePlaying;

	/**
	 * Create the application.
	 */
	public MainApplicationWindow() {	
		this.paneResult = -5;
		this.gamePlaying = 0;
		//frame = new JFrame();
		setBounds(0, 0, 1024, 768);
		//frame.setBounds(100, 100, 450, 300);
		//frame.setPreferredSize(new Dimension(1024,768));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// LoginPanel lP = new LoginPanel();
		// frame.setContentPane(lP);
		// frame.setVisible(true);
	}
	
	public static MainApplicationWindow getInstance() {
		if (instance == null) {
			instance = new MainApplicationWindow();
		}
		return instance;
	}
	
	public void setBridge(GUIBridge b) {
		this.bridge = b;
	}
	
	public GUIBridge getBridge(){ 
		return this.bridge;
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
	public void setFrameVisible(boolean f) { 
		this.setVisible(f);
	}
	
	/**
	 * Removes panel received from the frame
	 * @param panel
	 */
	public void removePanel(JPanel panel) {
		this.remove(panel);
	}
	/**
	 * adds panel received as param to the frame
	 * @param panel
	 */
	public void addPanel(JPanel panel) {
		this.setContentPane(panel);
		this.previousPanel = panel;
		this.setVisible(true);
		this.resetPaneResult();
	}
	
	public void removePreviousPanel(){
		this.remove(this.previousPanel);
	}
	/**
	 * Sets panel received as paramter to visible or not
	 * @param panel
	 * @param b
	 */
	public void setVisiblePanel(JPanel panel, boolean b) {
		panel.setVisible(b);
	}
	
	public int isGamePlaying() { 
		return this.gamePlaying;
	}
	
	public void setGamePlaying(int i) { 
		this.gamePlaying = i;
	}
	

	
	/** 
	 * Starts the login Panel. Property Listens is watching for when the loginState
	 * of the LoginPanel is changed the the change event is fired off.
	 * @param p
	 */
	/*public void startLogin(LoginPanel p) {
		p.addPropertyChangeListener("loginState", new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) { 
				int newStateValue = (int) evt.getNewValue();
				LoginPanel lP = (LoginPanel) evt.getSource();
				// if value is 1 it will try to login and start main menu if sucess
				if(newStateValue == 1) {
					int result = loginFunction(lP);
					/*if(result >= 0) { 
						startMainMenu();
						
					}
					if(result >= 0) {
						setPaneResult(result);
					}
					
					
				}
				// fires off create new user screen
				else if(newStateValue == 2) {
					setPaneResult(newStateValue);
					
				}
				// fires off continue as guest and if success starts main menu
				else if(newStateValue == 3) {
					int result = continueAsGuest();
					
					if(result >= 0) {
						setPaneResult(result);
					}
					
				}
			}
		});
	}*/
	
	/**
	 * Tries to login to server using login function from GUIBridge.
	 * @param lP
	 * @returns 0 Sucess, -1 Incorrect Pass, -2 User Not Found
	 */
	/*public int loginFunction(LoginPanel lP) {
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
	/*public int continueAsGuest() { 
		return bridge.ContinueAsGuest();
	}
	
	public int registerUser() { 
		return 0;
	} */
	
	
	public void setPaneResult(int i) {
		paneResult = i;
	}
	public void resetPaneResult() { 
		paneResult = 0;
	}
	
	public int getPaneResult() {
		return this.paneResult;
	}
	public void startMainMenu() {
		
	}
	



}
