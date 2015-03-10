package com.bubblechess;
import java.io.IOException;

import com.bubblechess.client.*;
import com.bubblechess.gui.*;


import org.json.simple.*;

public class BubbleChessDriver {
	
	
	public static void main(String[] args){
		
		//Run all the things here		
		
		//ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		ServerHandler server = new ServerHandler("144.118.117.17",8080);		
		GUIBridge bridge = new GUIBridge(server);
				
		bridge.TestServer();
				
		//GamePlayPanel gameScreen = new GamePlayPanel();
		//Spawn GUI here.  Pass it into GUIBridge.  Let it roll from there?

		//MainApplicationWindow mainAppWindow = new MainApplicationWindow(gameScreen);
		// LoginPanel loginScreen = new LoginPanel(GUIBridge);
				
		/*
		//Run all the things here		
		
		//ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		ServerHandler server = new ServerHandler("144.118.48.18",8080);		
		GUIBridge bridge = new GUIBridge(server);
		LoginPanel login = new LoginPanel();
		MainApplicationWindow mainAppWindow = new MainApplicationWindow(bridge);
		mainAppWindow.addPanel(login);
		mainAppWindow.setFrameVisible(true);
		
		int gameStart = 0;
		
		do {
			
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
				
				break;
			case 5:
				// JoinGame
				
				break;
			case 6:
				// gameplay
				
				break;
			case 7:
				// end game
				
				break;
			default:
				break;
			} 
			
			
			
		} while(mainAppWindow.getPaneResult() == 0); 

		
		
		//int playerNum = bridge.GetPlayerNumber();
		//GamePlayPanel gameScreen = new GamePlayPanel(playerNum);
		//Spawn GUI here.  Pass it into GUIBridge.  Let it roll from there?

		//MainApplicationWindow mainAppWindow = new MainApplicationWindow(gameScreen);
		// LoginPanel loginScreen = new LoginPanel(GUIBridge);
		
		while(!bridge.EndState()) {
			
			
			
			
			
		}
		*/
		
	}
	
	
}
