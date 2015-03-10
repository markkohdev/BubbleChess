package com.bubblechess.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import org.json.simple.JSONObject;

public class Game {
	
	//User1 is white user2 is black
	private int _user1Id = -1;
	private int _user2Id = -1;
	
	private int _gameId;
	private JSONObject _lastMove;
	
	private int _currentUser = 1;
	private ChessDB _cdb;
	
	/**
	 * Constructor for the Game Object
	 * @param gameId
	 * @param id
	 * @param user
	 * @param userId
	 */
	public Game(int gameId, int playerNumber, int userId, ChessDB cdb) {
		setUser(userId, playerNumber);
		_gameId = gameId;
		_cdb = cdb;
		
	}
	
	//Getters
	/**
	 * Gets an opponents userID from a playerNumber
	 * @param playerNumber
	 * @return
	 */
	public int getOpponentId(int playerNumber) {
		if(playerNumber == 1) {
			return _user2Id;
		}
		else if(playerNumber == 2) {
			return _user1Id;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the id of the user that is in the game waiting for an opponent
	 * @return
	 */
	public int getOtherUser() {
		if(_user1Id == -1) {
			return _user2Id;
		}
		else if (_user2Id == -1) {
			return _user1Id;
		}
		else {
			return -1;
		}
	}
	/**
	 * Gets the current player number for the turn
	 * @return
	 */
	public int getCurrentPlayer() {
		return _currentUser;
	}
	
	/**
	 * Gets the player number from the userID
	 * @param userId
	 * @return
	 */
	public int getPlayerNumber(int userId) {
		if(userId == _user1Id) {
			return 1;
		}
		else if(userId == _user2Id) {
			return 2;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Gets the last move that was executed in the game as JSON
	 * @return
	 */
	public JSONObject getLastMove() {
		return _lastMove;
	}
	
	//Setters
	/**
	 * Sets a user id to a player
	 * @param id
	 * @param user
	 * @param userId
	 */
	public void setUser(int userId, int playerNumber) {
		if(playerNumber == 1) {
			_user1Id = userId;
		}
		else if(playerNumber == 2) {
			_user2Id = userId;
		}
	}
	
	//Methods
	/**
	 * Allows a user to join an existing game and become a player
	 * @param user
	 * @param userId
	 * @return
	 */
	public boolean joinGame(int userId) {
		if(_user1Id == -1) {
			setUser(userId, 1);
			return true;
		}
		else if(_user2Id == -1) {
			setUser(userId, 2);
			return true;
		}
		else {
			//fail
			return false;
		}
	}
	
	//Game Methods
	/**
	 * Inserts a move into a game and sets it as the last move. After this it will add it to the database
	 * @param userId
	 * @param colFrom
	 * @param rowFrom
	 * @param colTo
	 * @param rowTo
	 * @throws IOException
	 */
	public boolean insertMove(int userId, int colFrom, int rowFrom, int colTo, int rowTo) throws IOException {
		boolean moveCheck = _cdb.insertMove(userId, _gameId, colFrom, rowFrom, colTo, rowTo);
		
		if(_currentUser == 1) {
			_currentUser = 2;
		}
		else {
			_currentUser = 1;
		}
		
		JSONObject move = new JSONObject();
		move.put("userID", userId);
		move.put("colFrom", colFrom);
		move.put("rowFrom", rowFrom);
		move.put("colTo", colTo);
		move.put("rowTo", rowTo);

		_lastMove = move;

		if(moveCheck) {
			return true;
		}
		else {
			return false;
		}
	}
}
