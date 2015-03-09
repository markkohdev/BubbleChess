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
	protected PrintWriter toServer;
	
	public RequestHandler(Socket clientSocket, ServerInstance server) {
		// TODO Auto-generated constructor stub
		_clientSocket = clientSocket;
		_server = server;
		
		try {
			toServer = new PrintWriter(_clientSocket.getOutputStream(), true);
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
            	case "createGame":
            		int newId = _server.getGames().size() + 1;
            		
            		long userId = (long) obj.get("userID");
            		
            		int userNumber = (int)((long) obj.get("userNumber"));
            		
        			GameThread gt = new GameThread(newId, userNumber, _clientSocket, (int) userId);
            		
            		_server.addGameThread(newId, gt);
            		_server.addJoinableGame(newId);
            		
            		//Return gameid
            		JSONObject json = new JSONObject();
            		
            		json.put("result","success");
            		json.put("gameID",newId);
            		toServer.println(json.toJSONString());  
            		
            	break;
            	case "joinGame":
            		userId = (long) obj.get("userID");
            		
            		long gameId = (long) obj.get("gameID");
            		
            		GameThread gt = _server.getGame((int) gameId);
            		if(gt.joinGame(_clientSocket, (int) userId)) {
            			_server.removeJoinableGame((int) gameId);
            			
            			JSONObject json = new JSONObject();
            			json.put("result","success");
            			toServer.println(json.toJSONString());  
            		}
            		else {
            			JSONObject json = new JSONObject();
            			json.put("result","failure");
            			toServer.println(json.toJSONString());  
            		}
            	break;
            	case "getJoinableGames":
            		ArrayList<Integer> joinableGames = _server.getJoinableGames();
            		
            		JSONObject json = new JSONObject();
            		json.put("result","success");
            		
            		JSONArray games = new JSONArray();
            		
            		for(int i = 0; i < joinableGames.size(); i ++) {
            			games.add(joinableGames.get(i));
            		}
            		json.put("games", games);
            		
            		//TODO: Add failure method
            	break;
            	case "insertMove":
            		//Need to cast from long to int
            		userId = (long) obj.get("userID");
            		gameId = (long) obj.get("gameID");
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
            		GameThread insertThread = _server.getGame((int) gameId);
            		insertThread.insertMove((int) userId, (int) colFrom, (int) rowFrom, (int) colTo, (int) rowTo);
            	break;
            	case "getAllMoves":
            		gameId = (long) obj.get("gameID");
           
            		GameThread allMovesThread = _server.getGame((int) gameId);
            		allMovesThread.getAllMoves(_clientSocket);
            	break;
            	case "getUser":
            		String username = (String) obj.get("username");
            		System.out.println(username);
            		
            		//Method to return userid
            		ChessDB cdb = new ChessDB();
            		int getUserId = cdb.getUser(username);
            		
            		//This will come through as a JSON String
            		json = new JSONObject();
            		json.put("result","success");
            		json.put("userID", getUserId);
            		toServer.println(json.toJSONString());
            		
            		//TODO: Add failure
            	break;
            	case "checkLogin":
            		String userName = (String) obj.get("username");
            		String password = (String) obj.get("password");
            		
            		cdb = new ChessDB();
            		
            		//get userid for login
            		int loginUserId = cdb.getUser(userName);
            		
            		//Method to check if user password is right
            		boolean loginStatus = cdb.checkLogin(loginUserId, password); 		
            		
            		if(loginStatus == true) {
            			json = new JSONObject();
                		json.put("result","success");
                		json.put("userID", loginUserId);
                		toServer.println(json.toJSONString());
            		}
            		else {
            			json = new JSONObject();
                		json.put("result","failure");
                		toServer.println(json.toJSONString());
            		}
            	break;
            	case "createUser":
            		username = (String) obj.get("username");
            		password = (String) obj.get("password");
            		
            		//Method to create a user
            		cdb = new ChessDB();
            		cdb.insertUser(username, password);
            		int userid = cdb.getUser(username);
            		
            		json = new JSONObject();
            		json.put("result", "success");
            		json.put("userID", userid);
            		toServer.println(json.toJSONString());
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
