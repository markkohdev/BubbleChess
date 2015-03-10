package com.bubblechess;
import java.io.IOException;

import com.bubblechess.client.*;
import com.bubblechess.gui.*;


import org.json.simple.*;

public class BubbleChessDriver {
	
	
	public static void main(String[] args){

		//Run all the things here
		int close = 0;
		
		//ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		ServerHandler server = new ServerHandler("144.118.48.18",8080);		
		GUIBridge bridge = new GUIBridge(server);
		LoginPanel login = new LoginPanel();
		MainApplicationWindow mainAppWindow = new MainApplicationWindow(bridge);
		mainAppWindow.addPanel(login);
		mainAppWindow.setFrameVisible(true);
		
		close = appMenus(mainAppWindow, bridge);
		
		
		if (close != 1) {
			// play game
			
		}

		
		
		
		//int playerNum = bridge.GetPlayerNumber();
		//GamePlayPanel gameScreen = new GamePlayPanel(playerNum);
		//Spawn GUI here.  Pass it into GUIBridge.  Let it roll from there?

		//MainApplicationWindow mainAppWindow = new MainApplicationWindow(gameScreen);
		// LoginPanel loginScreen = new LoginPanel(GUIBridge);
		
		/*while(!bridge.EndState()) {
		}*/
		
	}
		
		
		
	private static int appMenus(MainApplicationWindow mainAppWindow, GUIBridge bridge) {
		int close = 0;
		int gameID = 0;
		int userColor = 0;
		// 1 white, 2 black
		int gamePlaying = mainAppWindow.isGamePlaying();
		while(gamePlaying == 0) {
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
				
				break;
			case 6:
				// gameplay
				userColor = bridge.GetPlayerNumber();
				if(userColor != 0) {
					GamePlayPanel gamePanel = new GamePlayPanel(userColor);
					mainAppWindow.addPanel(gamePanel);
					mainAppWindow.setGamePlaying(1);
				}
				break;
			case 7:
				close = -1;
				break;
			default:
				break;
				}
			if (close == -1) {
				break;
			}
		}
		
		return close;

	}
	



	
	/*int gamePlaying = mainAppWindow.isGamePlaying();
	int gameID = 0;
	// 1 white, 2 black
	int userColor;
	int gamePlaying = mainAppWindow.isGamePlaying();
	while(gamePlaying == 0) {

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
			
			break;
		case 6:
			// gameplay
			userColor = bridge.GetPlayerNumber();
			if(userColor != 0) {
				GamePlayPanel gamePanel = new GamePlayPanel(userColor);
				mainAppWindow.addPanel(gamePanel);
				mainAppWindow.setGamePlaying(1);
			}
			break;
		case 7:
			close = 1;
			break;
		default:
			break;		
	}*/
	
}
