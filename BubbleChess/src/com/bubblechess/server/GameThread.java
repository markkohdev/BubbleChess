package com.bubblechess.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import org.json.simple.JSONObject;

import com.sun.javafx.collections.MappingChange.Map;

public class GameThread extends Thread {
	
	//User1 is white user2 is black
	private Socket _user1;
	private int _user1Id;
	
	private Socket _user2;
	private int _user2Id;
	
	private int _gameId;
	
	/**
	 * Constructor
	 * @param gameId
	 * @param id
	 * @param user
	 * @param userId
	 */
	public GameThread(int gameId, int id, Socket user, int userId) {
		setUser(id, user, userId);
		_gameId = gameId;
	}
	
	//Getters
	/**
	 * Method to get user socket
	 * @param id
	 * @return
	 */
	public Socket getUser(int id) {
		if(id == 1) {
			return _user1;
		}
		else if(id == 2) {
			return _user2;
		}
		else {
			return null;
		}
	}
	
	//Setters
	/**
	 * Sets a user to a socket and id in the thread
	 * @param id
	 * @param user
	 * @param userId
	 */
	public void setUser(int id, Socket user, int userId) {
		if(id == 1) {
			_user1 = user;
			_user1Id = userId;
		}
		else if(id ==2) {
			_user2 = user;
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
	public boolean joinGame(Socket user, int userId) {
		if(_user1 == null) {
			setUser(1, user, userId);
			return true;
		}
		else if(_user2 == null) {
			setUser(2, user, userId);
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
	public void insertMove(int userId, int colFrom, int rowFrom, int colTo, int rowTo) throws IOException {
		ChessDB cdb = new ChessDB();
		cdb.insertMove(userId, _gameId, colFrom, rowFrom, colTo, rowTo);
		
		JSONObject move = new JSONObject();
		move.put("userID", userId);
		move.put("colFrom", colFrom);
		move.put("rowFrom", rowFrom);
		move.put("colTo", colTo);
		move.put("rowTo", rowTo);
		
		//Respond to the right user
		if(userId == _user1Id) {
			DataOutputStream out = new DataOutputStream(_user1.getOutputStream());
			
			JSONObject json = new JSONObject();
    		json.put("result","success");
    		out.writeUTF(json.toJSONString());  
    		
    		//send move to opposite user
    		JSONObject oppJson = new JSONObject();
    		oppJson.put("message","newMove");
    		oppJson.put("move", move);
    		out = new DataOutputStream(_user2.getOutputStream());
    		out.writeUTF(oppJson.toJSONString());
		}
		else if(userId == _user2Id) {
			DataOutputStream out = new DataOutputStream(_user2.getOutputStream());
			
			JSONObject json = new JSONObject();
    		json.put("result","success");
    		out.writeUTF(json.toJSONString());  
    		
    		//send move to opposite user
    		JSONObject oppJson = new JSONObject();
    		oppJson.put("message","newMove");
    		oppJson.put("move", move);
    		out = new DataOutputStream(_user1.getOutputStream());
    		out.writeUTF(oppJson.toJSONString());
		}
	}
	
	/**
	 * Method to return all moves from a game
	 * @param userSocket
	 * @throws IOException
	 */
	public void getAllMoves(Socket userSocket) throws IOException {
		ChessDB cdb = new ChessDB();
		String moves = cdb.getAllMoves(_gameId);
		
		DataOutputStream out = new DataOutputStream(userSocket.getOutputStream());
	
		//This will come through as a JSON String
		JSONObject json = new JSONObject();
		json.put("result","success");
		json.put("moves", moves);
		out.writeUTF(json.toJSONString());
		
		//TODO: Add failure
	}
}
