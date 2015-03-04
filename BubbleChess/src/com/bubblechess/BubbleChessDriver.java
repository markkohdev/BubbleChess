package com.bubblechess;
import java.io.IOException;

import com.bubblechess.client.*;

import org.json.simple.*;

public class BubbleChessDriver {
	

	public static void main(String[] args){
		//Run all the things here
		
		ServerHandler server = new ServerHandler("tux.cs.drexel.edu",8080);
		
		//Spawn GUI here.  Pass it into GUIBridge.  Let it roll from there?
	}
	
	
}
