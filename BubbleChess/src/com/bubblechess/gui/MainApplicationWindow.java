package com.bubblechess.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplicationWindow extends JFrame {

	// private JFrame frame;

	/**
	 * Create the application.
	 */
	public MainApplicationWindow() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public MainApplicationWindow(JPanel panel) {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
	}





}
