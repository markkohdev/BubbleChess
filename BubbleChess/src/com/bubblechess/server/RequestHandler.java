package com.bubblechess.server;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.net.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestHandler extends Thread {
	private Socket _clientSocket = null;
	private ServerInstance _server = null;
	protected PrintStream _toClient;
	protected BufferedReader _in;
	protected String _request;
	protected boolean _isTest = false;
	protected String _response;
	private String _results;
	
	protected ChessDB _cdb;
	
	/** 
	 * Constructor for main execution
	 * @param clientSocket
	 * @param server
	 */
	public RequestHandler(Socket clientSocket, ServerInstance server) {
		_clientSocket = clientSocket;
		_server = server;
		_cdb = new ChessDB(false);
		
		try {	
				_toClient = new PrintStream(_clientSocket.getOutputStream());
				_in = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
				_request = _in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor used for testing purposes
	 * @param clientSocket
	 * @param server
	 * @param request
	 * @param stream
	 */
	public RequestHandler(Socket clientSocket, ServerInstance server, String request, PrintStream stream) {
		_clientSocket = clientSocket;
		_server = server;
		_cdb = new ChessDB(true);
		
		_toClient = stream;
		_request = request;
		_isTest = true;
	}
	
	/**
	 * Main Thread method 
	 */
	public void run (){
		try {
			if (_request == null)
				return;
			
            //JSON parsing
			System.out.println(_request);

    		JSONObject obj = (JSONObject) JSONValue.parse(_request);
            String requestString = (String) obj.get("request");
            
            switch(requestString) {
		        case "createUser":
		    		String username = (String) obj.get("username");
		    		String password = (String) obj.get("password");
		    		
		    		//Method to create a user
		    		JSONObject json;
		    		
		    		//Check if user exists
		    		if(_cdb.getUser(username) != -1) {
		    			json = new JSONObject();
			    		json.put("result", "username already exists");
		    		}
		    		else {
			    		_cdb.insertUser(username, password);
			    		int userid = _cdb.getUser(username);
			    		
			    		json = new JSONObject();
			    		json.put("result", "success");
			    		json.put("userID", userid);
		    		}
		    		
		    		sendToClient(json.toJSONString());
		    	break;
		        case "checkLogin":
            		String userName = (String) obj.get("username");
            		password = (String) obj.get("password");

            		//get userid for login
            		int loginUserId = _cdb.getUser(userName);
            		
            		if(loginUserId == -1) {
            			json = new JSONObject();
                		json.put("result","user not found");
            		}
            		else {
            			//Method to check if user password is right
                		boolean loginStatus = _cdb.checkLogin(loginUserId, password); 		
                		
                		if(loginStatus == true) {
                			json = new JSONObject();
                    		json.put("result","success");
                    		json.put("userID", loginUserId);
                		}
                		else {
                			json = new JSONObject();
                    		json.put("result","incorrect password");
                		}
            		}
            		sendToClient(json.toJSONString());
            	break;
            	case "createGame":

            		long userId = (long) obj.get("userID");
            		int playerNumber = (int)((long) obj.get("playerNumber"));
            		
            		int newId = _cdb.insertGame((int) userId, playerNumber);
            		
        			Game game = new Game(newId, playerNumber, (int) userId, _cdb);

            		_server.addGameThread(newId, game);
            		_server.addJoinableGame(newId);
            		
            		//Return gameid
            		json = new JSONObject();
            		json.put("result", "success");
            		json.put("gameID", newId);
            		sendToClient(json.toJSONString()); 
            		
            		//TODO: Add failure method
            	break;
            	case "getOpponent":
            		int gameId = (int)((long) obj.get("gameID"));
            		userId = (long) obj.get("userID");
            		playerNumber = (int)((long) obj.get("playerNumber"));
            		
            		game = _server.getGame(gameId);
            		int oppId = game.getOpponentId(playerNumber);
            		
            		if(oppId != -1) {
            			String oppUsername = _cdb.getUsername(oppId);
                		int oppPlayerNumber = game.getPlayerNumber(oppId);
                		
                		//This will come through as a JSON String
                		json = new JSONObject();
                		json.put("result","success");
                		json.put("userID", oppId);
                		json.put("username", oppUsername);
                		json.put("playerNumber", oppPlayerNumber);
                		sendToClient(json.toJSONString());
            		}
            		else {
            			json = new JSONObject();
                		json.put("result","waiting");
                		sendToClient(json.toJSONString());
            		}
            	break;
            	case "getJoinableGames":
            		ArrayList<Integer> joinableGames = _server.getJoinableGames();
            		
            		json = new JSONObject();
            		json.put("result","success");
            		
            		JSONArray games = new JSONArray();
            		
            		for(int i = 0; i < joinableGames.size(); i ++) {
            			games.add(joinableGames.get(i));
            		}
            		json.put("games", games);
            		
            		sendToClient(json.toJSONString());
            		//TODO: Add failure method
            	break;
            	case "joinGame":
            		userId = (long) obj.get("userID");
            		gameId = (int)((long) obj.get("gameID"));
            		
            		game = _server.getGame((int) gameId);
            		
            		int otherPlayer = game.getOtherUser();
            		String otherUsername = _cdb.getUsername(otherPlayer);
            		
            		if(game.joinGame((int) userId)) {
            			int otherPlayerNumber = game.getPlayerNumber(otherPlayer);
            			playerNumber = game.getPlayerNumber((int) userId);
            			
            			_cdb.addOpponent((int) userId, playerNumber, gameId);
            			
            			_server.removeJoinableGame(gameId);
            			
            			json = new JSONObject();
            			json.put("result", "success");
            			json.put("userID", otherPlayer);
            			json.put("username", otherUsername);
            			json.put("playerNumber", otherPlayerNumber);
            			sendToClient(json.toJSONString());  
            		}
            		else {
            			json = new JSONObject();
            			json.put("result","game does not exist");
            			sendToClient(json.toJSONString());  
            		}
            	break;
            	case "insertMove":
            		//Need to cast from long to int
            		userId = (long) obj.get("userID");
            		gameId = (int)((long) obj.get("gameID"));
            		long colFrom = (long) obj.get("colFrom");
            		long rowFrom = (long) obj.get("rowFrom");
            		long colTo = (long) obj.get("colTo");
            		long rowTo = (long) obj.get("rowTo");
            		
            		game = _server.getGame(gameId);
            		boolean moveStatus = game.insertMove((int) userId, (int) colFrom, (int) rowFrom, (int) colTo, (int) rowTo);
            		
            		if(moveStatus){
            			json = new JSONObject();
            			json.put("result", "success");
            		}
            		else {
            			json = new JSONObject();
            			json.put("result", "failure");
            		}
            		sendToClient(json.toJSONString()); 
            	break;
            	case "checkForMove":
            		gameId = (int)((long) obj.get("gameID"));
            		playerNumber = (int)((long) obj.get("playerNumber"));
            		
            		json = new JSONObject();
            		game = _server.getGame(gameId);
            		
     
            		if(game.getCurrentPlayer() == playerNumber) {
            			JSONObject moveObj = game.getLastMove();
                		int moveColFrom = (int) moveObj.get("colFrom");
                		int moveRowFrom = (int) moveObj.get("rowFrom");
                		int moveColTo = (int) moveObj.get("colTo");
                		int moveRowTo = (int) moveObj.get("rowTo");
                		
                		json.put("result","success");
                		json.put("colFrom", moveColFrom);
                		json.put("rowFrom", moveRowFrom);
                		json.put("colTo", moveColTo);
                		json.put("rowTo", moveRowTo);
            		}
            		else {
            			json.put("result","waiting");
            		}
            		sendToClient(json.toJSONString());
            	break;
            	
            }
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		finally {
			try {
				if(!_isTest) {
					_clientSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends a response to the client and set the result string
	 * @param results
	 */
	public void sendToClient(String results) {
		_toClient.println(results);
		_results = results;
	}
	
	/**
	 * Gets the result string that was sent to the client
	 * @return
	 */
	public String getResults() {
		return _results;
	}
}
