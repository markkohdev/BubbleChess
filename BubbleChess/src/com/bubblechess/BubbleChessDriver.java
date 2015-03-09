package com.bubblechess;
import java.io.IOException;

import com.bubblechess.client.*;
import com.bubblechess.gui.*;


import org.json.simple.*;

public class BubbleChessDriver {
	
	
	public static void main(String[] args){
		//Run all the things here		
		
		//ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		ServerHandler server = new ServerHandler("144.118.48.18",8080);		
		GUIBridge bridge = new GUIBridge(server);
		LoginPanel login = new LoginPanel();
		MainApplicationWindow mainAppWindow = new MainApplicationWindow(bridge);
		mainAppWindow.addPanel(login);
		mainAppWindow.setFrameVisible();
		mainAppWindow.startLogin(login);
		while(login.getLoginState() == 0) {
			// wait x time then recheck login state
		}
		
		
		int playerNum = bridge.GetPlayerNumber();
		GamePlayPanel gameScreen = new GamePlayPanel(playerNum);
		//Spawn GUI here.  Pass it into GUIBridge.  Let it roll from there?

		//MainApplicationWindow mainAppWindow = new MainApplicationWindow(gameScreen);
		// LoginPanel loginScreen = new LoginPanel(GUIBridge);
		
		while(!bridge.EndState()) {
			
			
			
			
			
		}
		
		
	}
	
	
}
