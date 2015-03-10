package com.bubblechess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.bubblechess.GUIBridge;

public class CreateGamePanel extends JPanel {
	
	private JComboBox colorDropDown;
	private int createResult;
	private JLabel lblErrorLabel;
	
	
	/**
	 * Default constructor for create Game Panel
	 * Create the panel.
	 */
	public CreateGamePanel() {
		
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		JLabel lblBubblepipeChess = new JLabel("Create Game");
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(396, 71, 318, 70);
		add(lblBubblepipeChess);
		
		JLabel lblColor = new JLabel("Pick your Color");
		lblColor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblColor.setBounds(423, 217, 192, 14);
		add(lblColor);
		
		lblErrorLabel = new JLabel();
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblErrorLabel.setBounds(423, 185, 70, 15);
		
		/*textNewUserName = new JTextField();
		textNewUserName.setBounds(423, 242, 192, 20);
		add(textNewUserName);*/
		
		JButton btnCreateGame = new JButton("Create");
		btnCreateGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tryCreateGame();		
			}
		});
		btnCreateGame.setBounds(515, 298, 110, 23);
		add(btnCreateGame); 
		
		String[] colors = {"White", "Black"};
		colorDropDown = new JComboBox(colors);
		colorDropDown.setBounds(423, 242, 202, 20);
		colorDropDown.setSelectedIndex(0);
		add(colorDropDown);

	}
	/**
	 * Gets the color selected from the drop down
	 * @return 1 is white, 2 is black
	 */
	public int getSelectedColor() {
		int color = this.colorDropDown.getSelectedIndex();
		
		// White is 1, Black is 2. Indexes are 0 and 1 respectively, add 1 to get correct color
		color = color + 1;
		
		return color;
		
	}
	
	/**
	 * sets the createResult to the gameID
	 * @param i
	 */
	public void setGameID(int i) { 
		this.createResult = i;
	}
	
	/**
	 * Gets the gameID from the createResult
	 * @return gets the GameID from the create Result
	 */
	public int getGameID() { 
		return this.createResult;
	}
	
	/**
	 * Tries to create the game using bridge function with options chosen from panel
	 */
	public void tryCreateGame() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		int color = this.getSelectedColor();
		GUIBridge b = mainWin.getBridge();
		int result = b.CreateGame(color);
		if(result < 0) {
			setErrorLabel("Error Creating Game");			
		}
		else {
			this.setGameID(result);
			mainWin.setPaneResult(6);
		}
	}
	

	public void setErrorLabel(String msg) {
		this.lblErrorLabel.setText(msg);
	}
}
