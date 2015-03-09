package com.bubblechess.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerInstance {
	
	private Map _games;
	private ArrayList<Integer> _joinableGames;

	/**
	 * Constructor
	 */
	public ServerInstance() {
		_games = new HashMap();
		_joinableGames = new ArrayList<Integer>();
	}
	
	//getters
	/**
	 * Method to get correct game thread
	 * @param gameId
	 * @return
	 */
	public Game getGame(int gameId) {
		return (Game) _games.get(gameId);
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
	public void addGameThread(int gameId, Game game) {
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
}
