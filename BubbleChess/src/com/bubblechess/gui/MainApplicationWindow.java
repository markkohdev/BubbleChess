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
