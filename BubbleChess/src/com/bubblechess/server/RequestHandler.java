package com.bubblechess.server;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.net.*;
import java.io.*;

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
            		userId = (int) userId;
            		
            		String userNumber = (String) obj.get("userNumber");
            		
            		int userNum;
            		if(userNumber.equalsIgnoreCase("user1")) {
            			userNum = 1;
            			GameThread gt = new GameThread(userNum, _clientSocket);
                		
                		_driver.addGameThread(newId, gt);
                		
                		//Return gameid
                		JSONObject json = new JSONObject();
                		
                		json.put("result","success");
                		json.put("gameID",newId);
                		out.writeUTF(json.toJSONString());  
              		}
            		else if(userNumber.equalsIgnoreCase("user2")) {
            			userNum = 2;
            			GameThread gt = new GameThread(userNum, _clientSocket);
                		_driver.addGameThread(newId, gt);
                		
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
            	case "insertMove":
            		//Need to cast from long to int
            		userId = (long) obj.get("userid");
            		userId = (int) userId;
            		
            		long gameId = (long) obj.get("gameid");
            		gameId = (int) gameId;
            		
            		long colFrom = (long) obj.get("colfrom");
            		colFrom = (int) colFrom;
            		
            		long rowFrom = (long) obj.get("rowfrom");
            		rowFrom = (int) rowFrom;
            		
            		long colTo = (long) obj.get("colto");
            		colTo = (int) colTo;
            		
            		long rowTo = (long) obj.get("rowto");
            		rowTo = (int) rowTo;
            		
            		System.out.println(userId);
            		System.out.println(gameId);
            		System.out.println(colFrom);
            		System.out.println(rowFrom);
            		System.out.println(colTo);
            		System.out.println(rowTo);
            		
            		//Method to insert moves
            		//ChessDB cdb = new ChessDB();
            		//cdb.insertMove(c, userId, gameId, colFrom, rowFrom, colTo, rowTo);
            	break;
            	case "getAllMoves":
            		gameId = (long) obj.get("gameid");
            		gameId = (int) gameId;
            		
            		//Method to return userid
            		//ChessDB cdb = new ChessDB();
            		//cdb.getAllMoves(gameId);
            	break;
            	case "getUser":
            		String username = (String) obj.get("username");
            		System.out.println(username);
            		
            		//Method to return userid
            		//ChessDB cdb = new ChessDB();
            		//cdb.getUser();
            	break;
            	case "checkLogin":
            		String userName = (String) obj.get("username");
            		String password = (String) obj.get("password");
            		
            		System.out.println(userName);
            		System.out.println(password);
            		
            		//Method to check if user password is right
            		//checkLogin();
            	break;
            	case "createUser":
            		username = (String) obj.get("username");
            		password = (String) obj.get("password");
            		
            		System.out.println(username);
            		System.out.println(password);
            		
            		//Method to create a user
            		//ChessDB cdb = new ChessDB();
            		//cdb.insertUser(c, userName, password);
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
