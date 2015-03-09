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
	protected PrintWriter toClient;
	
	public RequestHandler(Socket clientSocket, ServerInstance server) {
		// TODO Auto-generated constructor stub
		_clientSocket = clientSocket;
		_server = server;
		
		try {
			toClient = new PrintWriter(_clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run (){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
            
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
            
            String request = in.readLine();
           
            //JSON parsing
			System.out.println(request);
    		JSONObject obj = (JSONObject) JSONValue.parse(request);
             
            String requestString = (String) obj.get("request");
            
            switch(requestString) {
		        case "createUser":
		    		String username = (String) obj.get("username");
		    		String password = (String) obj.get("password");
		    		
		    		// TODO: Confirm no user
		    		//Method to create a user
		    		ChessDB cdb = new ChessDB();
		    		JSONObject json;
		    		
		    		//Check if user exists
		    		if(cdb.getUser(username) != -1) {
		    			json = new JSONObject();
			    		json.put("result", "username already exists");
		    		}
		    		else {
			    		cdb.insertUser(username, password);
			    		int userid = cdb.getUser(username);
			    		
			    		json = new JSONObject();
			    		json.put("result", "success");
			    		json.put("userID", userid);
		    		}

		    		toClient.println(json.toJSONString());
		    	break;
		        case "checkLogin":
            		String userName = (String) obj.get("username");
            		password = (String) obj.get("password");

            		//get userid for login
            		cdb = new ChessDB();
            		int loginUserId = cdb.getUser(userName);
            		
            		if(loginUserId == -1) {
            			json = new JSONObject();
                		json.put("result","user not found");
            		}
            		else {
            			//Method to check if user password is right
                		boolean loginStatus = cdb.checkLogin(loginUserId, password); 		
                		
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
            		toClient.println(json.toJSONString());
            	break;
            	case "createGame":

            		long userId = (long) obj.get("userID");
            		int playerNumber = (int)((long) obj.get("playerNumber"));
            		
            		cdb = new ChessDB();
            		int newId = cdb.insertGame((int) userId, playerNumber);
            		
        			Game game = new Game(newId, playerNumber, (int) userId);

            		_server.addGameThread(newId, game);
            		_server.addJoinableGame(newId);
            		//Return gameid
            		json = new JSONObject();
            		json.put("result", "success");
            		json.put("gameID", newId);
            		toClient.println(json.toJSONString());  
            	break;
            	case "getOpponent":
            		int gameId = (int)((long) obj.get("gameID"));
            		userId = (long) obj.get("userID");
            		playerNumber = (int)((long) obj.get("playerNumber"));
            		
            		game = _server.getGame(gameId);
            		int oppId = game.getOpponentId(playerNumber);
            		
            		if(oppId != -1) {
            			//Method to return userid
                		cdb = new ChessDB();
                		username = cdb.getUsername((int) userId);
                		
                		//This will come through as a JSON String
                		json = new JSONObject();
                		json.put("result","success");
                		json.put("userID", oppId);
                		json.put("username", username);
                		toClient.println(json.toJSONString());
            		}
            		else {
            			json = new JSONObject();
                		json.put("result","waiting");
                		System.out.println(json.toJSONString());
                		toClient.println(json.toJSONString());
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
            		
            		toClient.println(json.toJSONString());
            		//TODO: Add failure method
            	break;
            	case "joinGame":
            		userId = (long) obj.get("userID");
            		gameId = (int)((long) obj.get("gameID"));
            		
            		game = _server.getGame((int) gameId);
            		
            		cdb = new ChessDB();
            		
            		int otherPlayer = game.getOtherUser();
            		String otherUsername = cdb.getUsername(otherPlayer);
            		
            		if(game.joinGame((int) userId, _clientSocket)) {
            			playerNumber = game.getPlayerNumber((int) userId);
            			
            			_server.removeJoinableGame(gameId);
            			
            			json = new JSONObject();
            			json.put("result", "success");
            			json.put("userID", otherPlayer);
            			json.put("username", otherUsername);
            			json.put("playerNumber", playerNumber);
            			toClient.println(json.toJSONString());  
            		}
            		else {
            			json = new JSONObject();
            			json.put("result","game does not exist");
            			toClient.println(json.toJSONString());  
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
            		
            		/*System.out.println(userId);
            		System.out.println(gameId);
            		System.out.println(colFrom);
            		System.out.println(rowFrom);
            		System.out.println(colTo);
            		System.out.println(rowTo);*/
            		
            		//Talk to gameThread
            		game = _server.getGame(gameId);
            		game.insertMove((int) userId, (int) colFrom, (int) rowFrom, (int) colTo, (int) rowTo, _clientSocket);
            	break;
            	case "checkForMove":
            		gameId = (int)((long) obj.get("gameID"));
            		playerNumber = (int)((long) obj.get("playerNumber"));
            		
            		json = new JSONObject();
            		game = _server.getGame(gameId);
            		
            		if(game.getCurrentPlayer() == playerNumber) {
                		json.put("result","success");
                		// TODO: Get move info as json
                		//json.put("move", getUserId);
            		}
            		else {
            			json.put("result","waiting");
            		}
            		toClient.println(json.toJSONString());
            	break;
            	case "getAllMoves":
            		gameId = (int)((long) obj.get("gameID"));
           
            		Game allMovesThread = _server.getGame(gameId);
            		allMovesThread.getAllMoves(_clientSocket);
            	break;
            	case "getUser":
            		username = (String) obj.get("username");
            		System.out.println(username);
            		
            		//Method to return userid
            		cdb = new ChessDB();
            		int getUserId = cdb.getUser(username);
            		
            		//This will come through as a JSON String
            		json = new JSONObject();
            		json.put("result","success");
            		json.put("userID", getUserId);
            		toClient.println(json.toJSONString());
            		
            		//TODO: Add failure
            	break;
            	
            	
            }
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
            
        //DataOutputStream out = new DataOutputStream(server.getOutputStream());
        //out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
		
		finally {
			try {
				_clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
