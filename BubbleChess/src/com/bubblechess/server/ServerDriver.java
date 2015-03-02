package com.bubblechess.server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerDriver {
	
	private Map _gameMap;
	
	public ServerDriver() {
		_gameMap = new HashMap();
	}
	
	//getters
	public GameThread getGame(int gameId) {
		return (GameThread) _gameMap.get(gameId);
	}
	public Map getGames() {
		return _gameMap;
	}
	
	//setters
	public void addGameThread(int gameId, GameThread game) {
		if (_gameMap.get(gameId) == null) {
			_gameMap.put(gameId, game);
		}
	}
	
	public void main (String args[]) throws IOException{
		ServerSocket server = new ServerSocket(420);
		
		while (true){
			Socket client = server.accept();
		      
			RequestHandler rh = new RequestHandler(client, this);
			//System.out.println("Recieved a client");
			rh.start();
		}
	}
	
}

