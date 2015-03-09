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
		
		GamePlayPanel gameScreen = new GamePlayPanel();
		//Spawn GUI here.  Pass it into GUIBridge.  Let it roll from there?

		MainApplicationWindow mainAppWindow = new MainApplicationWindow(gameScreen);
		// LoginPanel loginScreen = new LoginPanel(GUIBridge);
		
		
	}
	
	
}
