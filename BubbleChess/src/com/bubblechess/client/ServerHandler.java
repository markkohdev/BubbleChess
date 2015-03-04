package com.bubblechess.client;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
	
	/**
	 * Send a login message to the server, and return the associated UserID
	 * upon authentication.
	 * @param username
	 * @param password
	 * @return The userID of the authenticated user if authenticated, -1 if
	 * invalid password, -2 if username not found, -3 unexpected
	 */
	public int Login(String username, String password){
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
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			return -3;
		}
		
		if(response.get("result").equals("success")) {
			return Integer.parseInt((String)response.get("userID"));
		}
		else if(response.get("result").equals("incorrect password")) {
			//Incorrect password
			return -1;
		}
		else if (response.get("result").equals("user not found")) {
			// User not found
			return -2;
		}
		else {
			return -3;
		}
	}
	
	/**
	 * Hits the server to create a temporary user ID and returns it to the caller
	 * @return The temporary userID
	 */
	public int ContinueAsGuest(){
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
			return -1;
		}
		
		if(response.get("result").equals("success")) {
			return Integer.parseInt((String)response.get("userID"));
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Register for a new user account
	 * @param username
	 * @param password
	 * @return userID if success, -1 if username exists, -2 unexpected
	 */
	public int Register(String username, String password) {
		JSONObject json = new JSONObject();
		
		json.put("request","createUser");
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			return -2;
		}
		
		if(response.get("result").equals("success")) {
			return Integer.parseInt((String)response.get("userID"));
		}
		else if (response.get("result").equals("username already exists")) {
			return -1;
		}
		else {
			return -2;
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @param playerNumber
	 * @return
	 */
	public int CreateGame(int userID, int playerNumber) {
		JSONObject json = new JSONObject();
		
		json.put("request","createGame");
		json.put("userID", userID);
		
		//User1 is white player.  It's important to keep track of this.
		if(playerNumber == 1)
			json.put("userNumber", "user1");
		else
			json.put("userNumber", "user2");
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			return -1;
		}
		
		if(response.get("result").equals("success")) {
			return Integer.parseInt((String)response.get("gameID"));
		}
		else {
			return -1;
		}
	}
	
	/**
	 * 
	 * @param gameID The gameID of the newly created game
	 * @param userID The userID of the requester
	 * @param playerNumber The playerNumber of the reuqestor (1 for white, 2 for black)
	 * @return The userID and username of the oppoenent as [userID, username]
	 */
	public String[] GetOpponent(int gameID, int userID, int playerNumber){
		JSONObject json = new JSONObject();
		
		json.put("request","getOpponent");
		json.put("gameID", gameID);
		json.put("userID", userID);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response;
		try {
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			return null;
		}
		
		if(response.get("result").equals("success")) {
			// Return [userID, username]
			String[] opponent = {
					(String)response.get("userID"), //Opponent userID
					(String)response.get("username"), //Opponent username
					};
			return opponent;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Requests to join game, returns opponent info if successful.
	 * @param gameID The ID of the game desired to be joined
	 * @param userID The ID of the user requesting to join
	 * @return The opponent data as [userID,username,userNumber], or null on
	 * failure to join game
	 */
	public String[] JoinGame(int gameID, int userID){
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
			return null;
		}
		
		if(response.get("result").equals("success")) {
			// Return [userID, username]
			String[] opponent = {
					(String)response.get("userID"), //Opponent userID
					(String)response.get("username"), //Opponent username
					(String)response.get("userNumber")
					};
			return opponent;
		}
		else {
			return null;
		}
	}
	
	
	public ArrayList<Integer> GetJoinableGames() {
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
			return null;
		}
		
		if(response.get("result").equals("success")) {
			JSONArray games = (JSONArray) response.get("games");
			ArrayList<Integer> gameIDs = new ArrayList<Integer>();
			Iterator<String> game = games.iterator();
			while (game.hasNext()) {
				int gameID = Integer.parseInt(game.next());
				gameIDs.add(gameID);
			}
			return gameIDs;
		}
		else {
			return null;
		}
	}
	
	
	
	public boolean SendMove(Move m, int userID, int gameID){
		JSONObject json = new JSONObject();
		
		json.put("gameID", gameID);
		json.put("user", userID);
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
			return false;
		}
		
		if (response.get("result").equals("success")){
			return true;
		}
		else
			return false;
	}
	
	public Move GetNextMove(){
		JSONObject response;
		try {
			//We're gonna spin here I guess?
			response = (JSONObject)JSONValue.parse(fromServer.readLine());
		} catch (IOException e) {
			System.err.println("Error recieving data from server.");
			e.printStackTrace();
			return null;
		}
		
		if (response.get("result").equals("success")){
			int[] from = {
			              Integer.parseInt((String)response.get("colFrom")),
			              Integer.parseInt((String)response.get("rowFrom"))
			};
			int[] to = {
		              Integer.parseInt((String)response.get("colTo")),
		              Integer.parseInt((String)response.get("rowTo"))
			};
			
			Move m = new Move(from,to);
			return m;
		}
		else
			return null;
	}
	
	
}
