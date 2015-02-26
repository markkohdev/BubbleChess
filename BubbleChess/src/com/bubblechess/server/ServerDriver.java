package com.bubblechess.server;

import java.net.*;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerDriver extends Thread
{
   private ServerSocket serverSocket;
   
   public ServerDriver() throws IOException
   {
	  int port = 4444;
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
           
            System.out.println("Just connected to "+ server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            //Input
            System.out.println(in.readUTF());
            /* Basic Move JSON
            { 
            	"request": "insertMove"
                "userid": 1234,
                "gameid": 1,
                "colfrom": 2,
                "rowfrom": 2,
                "colto": 3,
                "rowto": 3
            }
            */
            //String request="{ \"request\": \"insertMove\", \"userid\": 1234,\"gameid\": 1,\"colfrom\": 2,\"rowfrom\": 2,\"colto\": 3,\"rowto\": 3}";
           
            /* getAllMoves JSON
            { 
            	"request": "getAllMoves",
                "gameid": 1
            }
            */
            //String request = "{ \"request\": \"getAllMoves\",\"gameid\": 1}";
            
            
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
            	"request": "getUser",
                "username": "Test1",
                "password": "pass1"
            }
            */
            //String request = "{ \"request\": \"checkUser\",\"username\": \"Test1\", \"password\": \"pass1\"}";
            
            
            String request = in.readUTF();
           
            //JSON parsing
            JSONParser parser = new JSONParser();
    		JSONObject obj = new JSONObject();
    		obj = (JSONObject)parser.parse(request);
             
            String requestString = (String) obj.get("request");
            //System.out.println(requestString);
            switch(requestString) {
            	case "insertMove":
            		//Need to cast from long to int
            		long userId = (long) obj.get("userid");
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
            
            //DataOutputStream out = new DataOutputStream(server.getOutputStream());
            //out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
   }
   public static void main(String [] args)
   {
	  //removed this to keep it static
      //int port = Integer.parseInt(args[0]);
      try
      {
         Thread t = new ServerDriver();
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Check login method for users
    * @param userId
    * @param password
    */
   public void checkLogin(int userId, String password) {
		
   }
}