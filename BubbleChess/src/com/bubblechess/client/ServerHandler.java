package com.bubblechess.client;
import java.net.Socket;
import java.net.UnknownHostException;
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
	 * invalid password, -2 if username not found
	 * @throws IOException 
	 */
	public int Login(String username, String password) throws IOException{
		JSONObject json = new JSONObject();
		
		json.put("request","loginAuth");
		json.put("username", username);
		json.put("password",password);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response = (JSONObject)JSONValue.parse(fromServer.readLine());
		
		if(response.get("result").equals("success")) {
			return Integer.parseInt((String)response.get("userID"));
		}
		else if(response.get("result").equals("incorrect password")) {
			//Incorrect password
			return -1;
		}
		else {
			// User not found
			return -2;
		}
	}
	
	public int CreateGame(int userID, int playerNumber) throws IOException{
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
		JSONObject response = (JSONObject)JSONValue.parse(fromServer.readLine());
		
		if(response.get("result").equals("success")) {
			return Integer.parseInt((String)response.get("gameID"));
		}
		else {
			return -1;
		}
	}
	
	public String[] GetOpponent(int gameID, int userID) throws IOException{
		JSONObject json = new JSONObject();
		
		json.put("request","getOpponent");
		json.put("gameID", gameID);
		json.put("userID", userID);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response = (JSONObject)JSONValue.parse(fromServer.readLine());
		
		if(response.get("result").equals("success")) {
			// Return [userID, username]
			String[] opponent = {
					(String)response.get("userID"), //Opponent userID
					(String)response.get("username"), //Opponent username
					(String)response.get("playernumber") //Opponent playernumber (1 or 2)
					};
			return opponent;
		}
		else {
			return null;
		}
	}
	
	public boolean ValidGame(int gameID) throws IOException{
		JSONObject json = new JSONObject();
		
		json.put("request","getOpponent");
		json.put("gameID", gameID);
		
		toServer.println(json.toJSONString());
		
		//Wait for server response
		JSONObject response = (JSONObject)JSONValue.parse(fromServer.readLine());
		
		if(response.get("result").equals("success")) {
			// Return [userID, username]
			return true;
		}
		else {
			return false;
		}
	}
	
	public void SendMove(Move m, User u, Game g){
		JSONObject json = new JSONObject();
		
		json.put("move",m.toJSON());
		json.put("gameID", g.getID());
		json.put("user", u.getID());
		
		toServer.println(json.toJSONString());
		
		//TODO: Finish this jawn
	}
	
	
	
}
