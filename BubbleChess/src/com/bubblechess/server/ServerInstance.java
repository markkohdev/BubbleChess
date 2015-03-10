package com.bubblechess.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerInstance {
	
	private Map _games;
	private ArrayList<Integer> _joinableGames;

	/**
	 * Constructor for Server instance
	 */
	public ServerInstance() {
		_games = new HashMap();
		_joinableGames = new ArrayList<Integer>();
	}
	
	//getters
	/**
	 * Gets the game object of a specific game ID
	 * @param gameId
	 * @return
	 */
	public Game getGame(int gameId) {
		return (Game) _games.get(gameId);
	}
	
	/**
	 * Returns a list of games
	 * @return
	 */
	public Map getGames() {
		return _games;
	}
	
	/**
	 * Returns a list of joinable games
	 * @return
	 */
	public ArrayList<Integer> getJoinableGames() {
		return _joinableGames;
	}
	
	//setters
	/** 
	 * Adds a game object to the games list
	 * @param gameId
	 * @param game
	 */
	public void addGameThread(int gameId, Game game) {
		if (_games.get(gameId) == null) {
			_games.put(gameId, game);
		}
	}
	
	/**
	 * Adds a game object to the joinable games list
	 * @param gameId
	 */
	public void addJoinableGame(int gameId) {
		_joinableGames.add(gameId);
	}
	
	//methods
	/**
	 * Removes a game from the joinable games list
	 * @param gameId
	 */
	public void removeJoinableGame(int gameId) {
		int index = _joinableGames.indexOf(gameId);
		_joinableGames.remove(index);
	}
}
