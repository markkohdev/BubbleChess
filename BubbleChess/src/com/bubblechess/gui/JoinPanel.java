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

	/**joinableGames
	 * Create the panel.
	 */
	public JoinPanel(ArrayList<Integer> games) {
		
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
	
	
	public void joinGame() {
		MainApplicationWindow mainWin = MainApplicationWindow.getInstance();
		int gameID = this.getSelectedID();
		GUIBridge b = mainWin.getBridge();
		boolean result = b.JoinGame(gameID);
		if(!result) {
			setErrorLabel("Error Joining Game");			
		}
		else {
			mainWin.setPaneResult(6);
		}
		
	}
	
	public void setErrorLabel(String msg) {
		this.lblErrorLabel.setText(msg);
	}
	
	public int getSelectedID() {
		int gameID = (Integer)this.joinDropDown.getSelectedItem();		
		return gameID;
		
	}

}
