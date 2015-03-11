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
		setBounds(0, 0, 1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * start an instance of the MainApplicationWindow class
	 * @return instance of MainApplicationWindow
	 */
	public static MainApplicationWindow getInstance() {
		if (instance == null) {
			instance = new MainApplicationWindow();
		}
		return instance;
	}
	
	/**
	 * Set the bridge to the input guibridge
	 * @param b
	 */
	public void setBridge(GUIBridge b) {
		this.bridge = b;
	}
	
	/**
	 * Get the guibridge
	 * @return returns the gui bridge
	 */
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
	
	/**
	 * Remove previous main from JFrame
	 */
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
	
	
	/**
	 * Set the paneResult (next pane number) to i
	 * @param i
	 */
	public void setPaneResult(int i) {
		paneResult = i;
	}
	
	
	/**
	 * Reset paneResult to 0
	 */
	public void resetPaneResult() { 
		paneResult = 0;
	}
	
	
	/**
	 * Get the pane result
	 * @return paneResult
	 */
	public int getPaneResult() {
		return this.paneResult;
	}

}
