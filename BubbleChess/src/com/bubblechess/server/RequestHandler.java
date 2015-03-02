package com.bubblechess.server;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.net.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestHandler extends Thread {
	private Socket _clientSocket = null;
	private ServerDriver _driver = null;
	
	public RequestHandler(Socket clientSocket, ServerDriver driver) {
		// TODO Auto-generated constructor stub
		_clientSocket = clientSocket;
		_driver = driver;
	}
	
	public void run (){
		try {
			ObjectInputStream in = new ObjectInputStream(_clientSocket.getInputStream());
            
            //Input
            System.out.println(in.readUTF());
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
            
            String request = "";
			request = in.readUTF();
           
            //JSON parsing
            JSONParser parser = new JSONParser();
    		JSONObject obj = new JSONObject();
			obj = (JSONObject)parser.parse(request);
			
             
            String requestString = (String) obj.get("request");
            //System.out.println(requestString);
            
            DataOutputStream out = new DataOutputStream(_clientSocket.getOutputStream());
            
            switch(requestString) {
            	case "createGame":
            		int newId = _driver.getGames().size() + 1;
            		
            		long userId = (long) obj.get("userID");
            		
            		String userNumber = (String) obj.get("userNumber");
            		
            		int userNum;
            		if(userNumber.equalsIgnoreCase("user1")) {
            			userNum = 1;
            			GameThread gt = new GameThread(newId, userNum, _clientSocket, (int) userId);
                		
                		_driver.addGameThread(newId, gt);
                		_driver.addJoinableGame(newId);
                		
                		//Return gameid
                		JSONObject json = new JSONObject();
                		
                		json.put("result","success");
                		json.put("gameID",newId);
                		out.writeUTF(json.toJSONString());  
              		}
            		else if(userNumber.equalsIgnoreCase("user2")) {
            			userNum = 2;
            			GameThread gt = new GameThread(newId, userNum, _clientSocket, (int) userId);
                		
            			_driver.addGameThread(newId, gt);
                		_driver.addJoinableGame(newId);
                		
                		//Return gameid
                		JSONObject json = new JSONObject();
                		
                		json.put("result","success");
                		json.put("gameID",newId);
                		out.writeUTF(json.toJSONString());  
                	}
            		else {
            			JSONObject json = new JSONObject();
                		
                		json.put("result","failure");
                		out.writeUTF(json.toJSONString());  
            		}
            		
            	break;
            	case "joinGame":
            		userId = (long) obj.get("userID");
            		
            		long gameId = (long) obj.get("gameID");
            		
            		GameThread gt = _driver.getGame((int) gameId);
            		if(gt.joinGame(_clientSocket, (int) userId)) {
            			_driver.removeJoinableGame((int) gameId);
            			
            			JSONObject json = new JSONObject();
            			json.put("result","success");
                		out.writeUTF(json.toJSONString());  
            		}
            		else {
            			JSONObject json = new JSONObject();
            			json.put("result","failure");
                		out.writeUTF(json.toJSONString());  
            		}
            	break;
            	case "getJoinableGames":
            		ArrayList<Integer> joinableGames = _driver.getJoinableGames();
            		
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
            		GameThread insertThread = _driver.getGame((int) gameId);
            		insertThread.insertMove((int) userId, (int) colFrom, (int) rowFrom, (int) colTo, (int) rowTo);
            	break;
            	case "getAllMoves":
            		gameId = (long) obj.get("gameID");
           
            		GameThread allMovesThread = _driver.getGame((int) gameId);
            		allMovesThread.getAllMoves(_clientSocket);
            	break;
            	case "getUser":
            		String username = (String) obj.get("username");
            		System.out.println(username);
            		
            		//Method to return userid
            		ChessDB cdb = new ChessDB();
            		int getUserId = cdb.getUser(username);
            		
            		out = new DataOutputStream(_clientSocket.getOutputStream());
            		
            		//This will come through as a JSON String
            		json = new JSONObject();
            		json.put("result","success");
            		json.put("userID", getUserId);
            		out.writeUTF(json.toJSONString());
            		
            		//TODO: Add failure
            	break;
            	case "checkLogin":
            		String userName = (String) obj.get("username");
            		String password = (String) obj.get("password");
            		
            		System.out.println(userName);
            		System.out.println(password);
            		
            		cdb = new ChessDB();
            		out = new DataOutputStream(_clientSocket.getOutputStream());
            		
            		//get userid for login
            		int loginUserId = cdb.getUser(userName);
            		
            		//Method to check if user password is right
            		boolean loginStatus = cdb.checkLogin(loginUserId, password); 		
            		
            		if(loginStatus == true) {
            			json = new JSONObject();
                		json.put("result","success");
                		out.writeUTF(json.toJSONString());
            		}
            		else {
            			json = new JSONObject();
                		json.put("result","failure");
                		out.writeUTF(json.toJSONString());
            		}
            	break;
            	case "createUser":
            		username = (String) obj.get("username");
            		password = (String) obj.get("password");
            		
            		System.out.println(username);
            		System.out.println(password);
            		
            		//Method to create a user
            		cdb = new ChessDB();
            		cdb.insertUser(username, password);
            		
            		//TODO: Add failure method
            	break;
            }
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
