package com.bubblechess.client;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.*;

import org.json.simple.*;

public class ServerHandler {
	
	protected String hostname;
	protected int port;
	protected Socket socket;
	protected PrintWriter toServer;
	protected BufferedReader fromServer;
	

	public ServerHandler(String hostname, int port){
		this.hostname = hostname;
		this.port = port;
		
		//SetupConnection();
	}
	
	/**
	 * Create the connection to the server
	 */
	protected void SetupConnection() {
		if (socket == null) {
			try {
				socket = new Socket(hostname,port);
				toServer = new PrintWriter(socket.getOutputStream(),true);
				fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			catch(UnknownHostException e){
				System.out.println("Error: Not connected to server.  Unknown host.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Close the connection to the server
	 */
	protected void CloseConnection() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Unable to close connection.");
			}
			socket = null;
			toServer = null;
			fromServer = null;
		}
	}
	
	/**
	 * Send a login message to the server, and return the associated UserID
	 * upon authentication.
	 * @param username
	 * @param password
	 * @return The userID of the authenticated user if authenticated, -1 if
	 * invalid password, -2 if username not found, -3 unexpected
	 */
	public int Login(String username, String password){
		SetupConnection();
		int retVal = 0;
		
		JSONObject json = new JSONObject();
		
		json.put("request","checkLogin");
		json.put("username", username);
		json.put("password",password);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.out.println("Error recieving data from server.");
			e.printStackTrace();
			
			CloseConnection();
			return -3;
		}
		
		if(response.get("result").equals("success")) {
		
			retVal = (int)((long)response.get("userID"));
		}
		else if(response.get("result").equals("incorrect password")) {
			//Incorrect password
			retVal = -1;
		}
		else if (response.get("result").equals("user not found")) {
			// User not found
			retVal = -2;
		}
		else {
			retVal = -3;
		}
		
		CloseConnection();
		return retVal;
	}
	
	/**
	 * Hits the server to create a temporary user ID and returns it to the caller
	 * @return The temporary userID
	 */
	public int ContinueAsGuest(){
		SetupConnection();
		int retVal = 0;
		
		JSONObject json = new JSONObject();
		
		json.put("request","continueAsGuest");
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			
			CloseConnection();
			return -1;
		}
		
		if(response.get("result").equals("success")) {
			retVal = (int)((long)response.get("userID"));
		}
		else {
			retVal = -1;
		}
		
		CloseConnection();
		return retVal;
	}
	
	/**
	 * Register for a new user account
	 * @param username
	 * @param password
	 * @return userID if success, -1 if username exists, -2 unexpected
	 */
	public int Register(String username, String password) {
		SetupConnection();
		int retVal = 0;
		
		JSONObject json = new JSONObject();
		
		json.put("request","createUser");
		json.put("username",username);
		json.put("password",password);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			
			CloseConnection();
			return -2;
		}
		
		if(response.get("result").equals("success")) {
			retVal = (int)((long)response.get("userID"));
		}
		else if (response.get("result").equals("username already exists")) {
			retVal = -1;
		}
		else {
			retVal = -2;
		}
		
		CloseConnection();
		return retVal;
	}
	
	/**
	 * 
	 * @param userID
	 * @param playerNumber
	 * @return
	 */
	public int CreateGame(int userID, int playerNumber) {
		SetupConnection();
		int retVal = 0;
		
		JSONObject json = new JSONObject();
		
		json.put("request","createGame");
		json.put("userID", userID);
		json.put("playerNumber", playerNumber);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			CloseConnection();
			return -1;
		}
		
		if(response.get("result").equals("success")) {
			retVal =  (int)((long)response.get("gameID"));
		}
		else {
			retVal = -1;
		}
		
		//We want to persist the connection here
		CloseConnection();
		return retVal;
	}
	
	/**
	 * 
	 * @param gameID The gameID of the newly created game
	 * @param userID The userID of the requester
	 * @param playerNumber The playerNumber of the reuqestor (1 for white, 2 for black)
	 * @return The userID and username of the oppoenent as [userID, username]
	 */
	public String[] GetOpponent(int gameID, int userID, int playerNumber){
		String[] retVal = null;
		
		int attempts = 0;
		int MAX_ATTEMPTS = 1000;
		while (attempts < MAX_ATTEMPTS && retVal == null) {
			SetupConnection();
			JSONObject json = new JSONObject();
			
			json.put("request","getOpponent");
			json.put("gameID", gameID);
			json.put("userID", userID);
			json.put("playerNumber", playerNumber);
			
			toServer.println(json.toJSONString());
			
			//Wait for server response
			JSONObject response;
			try {
				response = (JSONObject)JSONValue.parse(fromServer.readLine());
			} catch (IOException e) {
				System.err.println("Error recieving data from server.");
				e.printStackTrace();
				CloseConnection();
				return null;
			}
			
			if(response.get("result").equals("success")) {
				// Return [userID, username]
				String[] opponent = {
						(String)response.get("userID").toString(), //Opponent userID
						(String)response.get("username"), //Opponent username
						};
				retVal = opponent;
			}
			else if (response.get("result").equals("waiting")){
				attempts++;
				System.out.print(".");
				CloseConnection();
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		CloseConnection();
		return retVal;
	}
	
	/**
	 * Requests to join game, returns opponent info if successful.
	 * @param gameID The ID of the game desired to be joined
	 * @param userID The ID of the user requesting to join
	 * @return The opponent data as [userID,username,userNumber], or null on
	 * failure to join game
	 */
	public String[] JoinGame(int gameID, int userID){
		SetupConnection();
		JSONObject json = new JSONObject();
		
		json.put("request","joinGame");
		json.put("gameID", gameID);
		json.put("userID",userID);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			CloseConnection();
			return null;
		}
		
		if(response.get("result").equals("success")) {
			// Return [userID, username]
			String[] opponent = {
					(String)response.get("userID").toString(), //Opponent userID
					(String)response.get("username"), //Opponent username
					(String)response.get("playerNumber").toString()
					};
			CloseConnection();
			return opponent;
		}
		else if (response.get("result").equals("game does not exist")) {
			CloseConnection();
			return null;
		}
		else {
			CloseConnection();
			return null;
		}
	}
	
	
	public ArrayList<Integer> GetJoinableGames() {
		SetupConnection();
		JSONObject json = new JSONObject();
		
		json.put("request","getJoinableGames");
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			CloseConnection();
			return null;
		}
		
		if(response.get("result").equals("success")) {
			JSONArray games = (JSONArray) response.get("games");
			ArrayList<Integer> gameIDs = new ArrayList<Integer>();
			Iterator<Long> game = games.iterator();
			while (game.hasNext()) {
				int gameID = (int)((long)game.next());
				gameIDs.add(gameID);
			}
			CloseConnection();
			return gameIDs;
		}
		else {
			CloseConnection();
			return null;
		}
	}
	
	
	
	public boolean SendMove(Move m, int userID, int gameID){
		SetupConnection();
		JSONObject json = new JSONObject();
		
		json.put("request", "insertMove");
		json.put("gameID", gameID);
		json.put("userID", userID);
		json.put("colFrom", m.colFrom());
		json.put("rowFrom", m.rowFrom());
		json.put("colTo", m.colTo());
		json.put("rowTo", m.rowTo());
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			CloseConnection();
			return false;
		}
		
		if (response.get("result").equals("success")){
			CloseConnection();
			return true;
		}
		else
			CloseConnection();
			return false;
	}
	
	public Move CheckForMove(int gameID, int playerNumber){
		Move retVal = null;
		
		int attempts = 0;
		int MAX_ATTEMPTS = 1000;
		while (attempts < MAX_ATTEMPTS && retVal == null) {
			SetupConnection();
			
			JSONObject json = new JSONObject();
			
			json.put("request", "checkForMove");
			json.put("gameID", gameID);
			json.put("playerNumber", playerNumber);
			
			toServer.println(json.toJSONString());
		
			JSONObject response;
			try {
				response = (JSONObject)JSONValue.parse(fromServer.readLine());
			} catch (IOException e) {
				System.err.println("Error recieving data from server.");
				e.printStackTrace();
				CloseConnection();
				return null;
			}
			
			if (response.get("result").equals("success")){
				int[] from = {
				              (int)((long)response.get("colFrom")),
				              (int)((long)response.get("rowFrom"))
				};
				int[] to = {
						(int)((long)response.get("colTo")),
						(int)((long)response.get("rowTo"))
				};
				
				Move m = new Move(from,to);
				retVal = m;
			}
			else if (response.get("result").equals("waiting")){
				attempts++;
				System.out.print(".");
				CloseConnection();
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		CloseConnection();
		return retVal;
	}
	
	public boolean EndGame() {
		CloseConnection();
		return false;
	}
	
	
}
