package com.bubblechess.server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerDriver {
	
	private Map _games;
	private ArrayList<Integer> _joinableGames;
	
	/**
	 * Constructor
	 */
	public ServerDriver() {
		_games = new HashMap();
	}
	
	//getters
	/**
	 * Method to get correct game thread
	 * @param gameId
	 * @return
	 */
	public GameThread getGame(int gameId) {
		return (GameThread) _games.get(gameId);
	}
	
	/**
	 * Method to return games
	 * @return
	 */
	public Map getGames() {
		return _games;
	}
	
	/**
	 * Method to return a list of joinable games
	 * @return
	 */
	public ArrayList<Integer> getJoinableGames() {
		return _joinableGames;
	}
	
	//setters
	/** 
	 * Method to add a game thread to the games list
	 * @param gameId
	 * @param game
	 */
	public void addGameThread(int gameId, GameThread game) {
		if (_games.get(gameId) == null) {
			_games.put(gameId, game);
		}
	}
	
	/**
	 * Method to add a gameid to a joinable games list
	 * @param gameId
	 */
	public void addJoinableGame(int gameId) {
		_joinableGames.add(gameId);
	}
	
	//methods
	/**
	 * Method to remove a game from the joinable games list
	 * @param gameId
	 */
	public void removeJoinableGame(int gameId) {
		int index = _joinableGames.indexOf(gameId);
		_joinableGames.remove(index);
	}
	
	/**
	 * Main method to run the server on port 430
	 * @param args
	 * @throws IOException
	 */
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

