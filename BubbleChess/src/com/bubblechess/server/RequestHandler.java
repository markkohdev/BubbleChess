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
		// TODO Auto-generated constructor stub
		_clientSocket = clientSocket;
		_server = server;
		_cdb = new ChessDB(false);
		
		try {	
				_toClient = new PrintStream(_clientSocket.getOutputStream());
				_in = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
				_request = _in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor used for testing purposes
	 * @param clientSocket
	 * @param server
	 * @param isTest
	 */
	public RequestHandler(Socket clientSocket, ServerInstance server, String request, PrintStream stream) {
		// TODO Auto-generated constructor stub
		_clientSocket = clientSocket;
		_server = server;
		_cdb = new ChessDB(true);
		
		_toClient = stream;
		_request = request;
		_isTest = true;
	}
	
	public void run (){
		try {
            //Input
            /* Basic Move JSON
            { 
            	"request": "insertMove"
                "userID": 1234,
                "gameID": 1,
                "colFrom": 2,
                "rowFrom": 2,
                "colTo": 3,
                "rowTo": 3
            }
            */
            //String request="{ \"request\": \"insertMove\", \"userID\": 1234,\"gameID\": 1,\"colFrom\": 2,\"rowFrom\": 2,\"colTo\": 3,\"rowTo\": 3}";
           
            /* getAllMoves JSON
            { 
            	"request": "getAllMoves",
                "gameID": 1
            }
            */
            //String request = "{ \"request\": \"getAllMoves\",\"gameID\": 1}";
            
            
            /* createUser JSON
            { 
            	"request": "getUser",
                "username": "Test1",
                "password": "pass1"
            }
            */
            //String request = "{ \"request\": \"createUser\",\"username\": \"Test1\",\"password\": \"pass1\"}";
            
            /* getUser JSON
            { 
            	"request": "getUser",
                "username": "Test1"
            }
            */
            //String request = "{ \"request\": \"getUser\",\"username\": \"Test1\"}";
            
            /* checkUser JSON
            { 
            	"request": "checkUser",
                "username": "Test1",
                "password": "pass1"
            }
            */
            //String request = "{ \"request\": \"checkUser\",\"username\": \"Test1\", \"password\": \"pass1\"}";
            
            /* createGame JSON
            { 
            	"request": "createGame",
                "userID": "1234",
                "userNumber": "user1"
            }
            */
            //String request = "{ \"request\": \"createGame\",\"userID\": \"1234\",\"userNumber\": \"user1\"}";
            
            /* joinGame JSON
            { 
            	"request": "joinGame",
                "userID": "1234",
                "gameID": "1234"
            }
            */
            //String request = "{ \"request\": \"joinGame\",\"userID\": \"1234\",\"gameID\": \"1234\"}";
			
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
                		json.put("playNumber", oppPlayerNumber);
                		sendToClient(json.toJSONString());
            		}
            		else {
            			json = new JSONObject();
                		json.put("result","waiting");
                		System.out.println(json.toJSONString());
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
            			
            			System.out.println("Joined("+otherPlayer+", "+userId+")");
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
            	case "getAllMoves":
            		gameId = (int)((long) obj.get("gameID"));
           
            		Game allMovesThread = _server.getGame(gameId);
            		//allMovesThread.getAllMoves(_clientSocket);
            	break;
            	
            }
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		finally {
			try {
				if(!_isTest) {
					_clientSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends a response to the client and the System.out
	 * @param results
	 */
	public void sendToClient(String results) {
		_toClient.println(results);
		_results = results;
	}
	
	public String getResults() {
		return _results;
	}
}
