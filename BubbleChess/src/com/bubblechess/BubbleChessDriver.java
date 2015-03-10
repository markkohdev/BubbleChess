package com.bubblechess;
import java.io.IOException;
import java.util.ArrayList;

import com.bubblechess.client.*;
import com.bubblechess.gui.*;

import org.json.simple.*;

public class BubbleChessDriver {
	
	
	public static void main(String[] args){

		//Run all the things here
		int close = 0;
		
		//ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		ServerHandler server = new ServerHandler("144.118.117.17",8080);		
		GUIBridge bridge = new GUIBridge(server);
		LoginPanel login = new LoginPanel();
		MainApplicationWindow mainAppWindow = MainApplicationWindow.getInstance();
		mainAppWindow.setBridge(bridge);
		mainAppWindow.addPanel(login);
		mainAppWindow.setFrameVisible(true);
		close = 0;
		
		while (close != 1) {
			int gameID = 0;
			int pane = mainAppWindow.getPaneResult();
			switch(pane) {
			case 1:
				// login
				LoginPanel newLogPanel = new LoginPanel();
				mainAppWindow.addPanel(newLogPanel);
				break;
			case 2:
				// create
				RegisterPanel newRegister = new RegisterPanel();
				mainAppWindow.addPanel(newRegister);
				break;
			case 3:
				// mainmenu
				MainMenuPanel newMenuPanel = new MainMenuPanel();
				mainAppWindow.addPanel(newMenuPanel);
				break;
			case 4:
				// create
				CreateGamePanel createPanel = new CreateGamePanel();
				mainAppWindow.addPanel(createPanel);
				gameID = createPanel.getGameID();
				break;
			case 5:
				// JoinGame
				ArrayList<Integer> games = bridge.GetJoinableGames();
				JoinPanel joinP = new JoinPanel();
				mainAppWindow.addPanel(joinP);
				break;
			case 6:
				// gameplay
				WaitingForOppPanel waitingPanel = new WaitingForOppPanel(gameID);
				mainAppWindow.addPanel(waitingPanel);
				boolean haveOpponent = bridge.WaitForOpponent();
				if(haveOpponent) {
					GamePlayPanel gamePanel = new GamePlayPanel();
					mainAppWindow.addPanel(gamePanel);
				}
				break;
			case 7:
				close = 1;
				break;
			case 8:
				EndScreenPanel endPanel = new EndScreenPanel();
				mainAppWindow.addPanel(endPanel);
				break;
			default:
				close = 1;
				break;
			}
		}
	}
	
}
