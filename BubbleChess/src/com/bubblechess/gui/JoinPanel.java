package com.bubblechess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bubblechess.GUIBridge;

public class JoinPanel extends JPanel {
	
	private JLabel lblErrorLabel;
	private JComboBox joinDropDown;
	private MainApplicationWindow mainWin;
	private GUIBridge bridge;


	/**
	 * Constructor for Join Panel
	 * Gets list of joinable games from the GUIbridge
	 */
	public JoinPanel() {
		mainWin = MainApplicationWindow.getInstance();
		bridge = mainWin.getBridge();
		ArrayList<Integer> games = bridge.GetJoinableGames();
		int numGames = games.size();
		String[] joinableGames = new String[numGames];
		for (int i = 0; i < numGames; i++) {
			joinableGames[i] = Integer.toString(games.get(i));			
		}
		
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1024,768));
		setLayout(null);
		
		
		
		JButton btnJoinGame = new JButton("Join");
		btnJoinGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				joinGame();		
			}
		});
		btnJoinGame.setBounds(515, 298, 110, 23);
		add(btnJoinGame); 
		
		
		joinDropDown = new JComboBox(joinableGames);
		joinDropDown.setBounds(423, 242, 202, 20);
		joinDropDown.setSelectedIndex(0);
		add(joinDropDown);
		
		lblErrorLabel = new JLabel();
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblErrorLabel.setBounds(423, 185, 70, 15);
		
		
		JLabel lblBubblepipeChess = new JLabel("Join Game");
		lblBubblepipeChess.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBubblepipeChess.setBounds(396, 71, 318, 70);
		add(lblBubblepipeChess);

	}
	
	/**
	 * join game function, tries to join game using the bridge function join game
	 * if success sets next panel to gameplay if not, sets error label text
	 */
	public void joinGame() {
		int gameID = this.getSelectedID();
		boolean result = bridge.JoinGame(gameID);
		if(!result) {
			setErrorLabel("Error Joining Game");			
		}
		else {
			mainWin.setPaneResult(6);
		}
		
	}
	/**
	 * function to set the error label to message inputted
	 * @param msg
	 */
	public void setErrorLabel(String msg) {
		this.lblErrorLabel.setText(msg);
	}
	
	
	/**
	 * Used to get the ID selected from the dropdown
	 * @return ID from selected Game
	 */
	public int getSelectedID() {
		int gameID = (Integer)this.joinDropDown.getSelectedItem();		
		return gameID;
		
	}

}
