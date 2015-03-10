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
	private String _lastMove = "";
	
	private int _currentUser = 1;
	private ChessDB _cdb;
	
	/**
	 * Constructor
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
	 * Method to get opponent of a player by id
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
	 * Method used to get the other user id for joining
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
	 * Returns the current player number
	 * @return
	 */
	public int getCurrentPlayer() {
		return _currentUser;
	}
	
	/**
	 * Method to get player number based off ID
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
	 * Returns last move object as a String
	 * @return
	 */
	public String getLastMove() {
		return _lastMove;
	}
	
	//Setters
	/**
	 * Sets a user to a socket and id in the thread
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
	 * Method to join a game thread
	 * @param user
	 * @param userId
	 * @return
	 */
	public boolean joinGame(int userId, Socket client) {
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
	 * Method to insert move into a game
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

		_lastMove = move.toJSONString();

		if(moveCheck) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**/
	/**
	 * Method to return all moves from a game
	 * @param userSocket
	 * @throws IOException
	 */
	public void getAllMoves(Socket userSocket) throws IOException {
		String moves = _cdb.getAllMoves(_gameId);
		
		DataOutputStream out = new DataOutputStream(userSocket.getOutputStream());
	
		//This will come through as a JSON String
		JSONObject json = new JSONObject();
		json.put("result","success");
		json.put("moves", moves);
		out.writeUTF(json.toJSONString());
		
		//TODO: Add failure
	}
}
