package com.bubblechess;
import java.io.IOException;
import java.util.ArrayList;

import com.bubblechess.client.*;
import com.bubblechess.gui.*;

import org.json.simple.*;

public class BubbleChessDriver {
	
	
	public static void main(String[] args){

		if (args.length < 2) {
			System.out.println("Please run as follows <server IP> <server port>");
			return;
		}
		//ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		ServerHandler server;
		try {
			server = new ServerHandler(args[0],Integer.parseInt(args[1]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return;
		}
		
		
		int close = 0;	
		GUIBridge bridge = new GUIBridge(server);
		MainApplicationWindow mainAppWindow = MainApplicationWindow.getInstance();
		mainAppWindow.setBridge(bridge);
		
		//LoginPanel login = new LoginPanel();
		
		
		//mainAppWindow.addPanel(login);
		mainAppWindow.setPaneResult(1);
		mainAppWindow.setFrameVisible(true);

		
		while (close != 1) {
			
			close = appWindowSwitcher();
		}
	}
	
	/**
	 * Function to switch windows
	 * @param mainAppWindow
	 */
	public static int appWindowSwitcher() {
		MainApplicationWindow mainAppWindow = MainApplicationWindow.getInstance();
		GUIBridge bridge = mainAppWindow.getBridge();
		int gameID = bridge.GetGameID();
		int close = 0;
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
			JoinPanel joinP = new JoinPanel();
			mainAppWindow.addPanel(joinP);
			break;
		case 6:
			// Created gameplay
			WaitingForOppPanel waitingPanel = new WaitingForOppPanel(gameID);
			mainAppWindow.addPanel(waitingPanel);
			boolean haveOpponent = bridge.WaitForOpponent();
			if(haveOpponent) {
				GamePlayPanel gamePanel = new GamePlayPanel();
				mainAppWindow.addPanel(gamePanel);
				if (bridge.GetPlayerNumber() == 2)
					gamePanel.OpponentTurn();
			}
			break;
		case 7:
			close = 1;
			break;
		case 8:
			EndScreenPanel endPanel = new EndScreenPanel();
			mainAppWindow.addPanel(endPanel);
			break;
		}
		return close;
	}
	
}
