package com.bubblechess;
import java.io.IOException;

import com.bubblechess.client.*;

import org.json.simple.*;

public class BubbleChessDriver {
	
	protected static ServerHandler serverHandler;
	protected static User player;
	protected static User opponent;
	protected static Game game;

	public static void main(String[] args){
		//Run all the things here
		
		serverHandler = new ServerHandler("tux.cs.drexel.edu",8080);
		
		//TODO: Spawn the GUI with the login screen.
		
		
		
		//Wait here until GUI window closes, then run cleanup?
	}
	
	public static void login(String username, String password){
		/*
		 * 1. Log in and verify user
		 * 2. Assign user to player
		 * 3. Wait for createGame or joinGame
		 */
		
		try {
			int response = serverHandler.Login(username, password);
			
			if (response == -1){
				//TODO: Incorrect password, reprompt
			}
			else if (response == -2){
				//TODO: Invalid userID
			}
			else {
				player = new User(response,username);
				
				//TODO: Move forward to main menu
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void createGame(int playerNumber) {
		/* 1. Hit the server with a createGame request and the userID 
		 * 2. Wait for a response which contains the created game ID
		 * 3. Show user gameID
		 * 4. Wait for second player (response from server will be game object)
		 * 5. Now that we know both users and the gameID, create the game object
		 * 6. Start gameplay
		 */
		
		try {
			int gameID = serverHandler.CreateGame(player.getID(),playerNumber);
			
			if (gameID == -1){
				//TODO: ERROR!!
			}
			
			//Get the second playerID.  We may need to wait here.
			String[] user2 = serverHandler.GetOpponent(gameID, player.getID());
			
			if (user2 != null) {
				opponent = new User(Integer.parseInt(user2[0]),user2[1]);
			}
			else {
				//TODO: ERROR!!
			}
			
			//Set up the game.  User1 will be assumed white
			if (playerNumber == 1)
				//We are white
				game = new Game(gameID,player,opponent,new ChessBoard());
			else
				//Opponent is white
				game = new Game(gameID,opponent,player,new ChessBoard());
			
			startGame();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void joinGame(int gameID) {
		/*
		 * 1. Get gameID from user
		 * 2. Hit server with joinGame and ID
		 * 3. Wait for server to respond with the game object or failure
		 * 4. Create the game object on our side
		 * 5. Start gameplay
		 */
		
		try {
			boolean validGame = serverHandler.ValidGame(gameID);
			
			if (!validGame){
				//TODO: reprompt for correct gameID
			}
			else {
			
				//Get the second playerID.  We may need to wait here.
				String[] user2 = serverHandler.GetOpponent(gameID, player.getID());
				
				if (user2 != null) {
					opponent = new User(Integer.parseInt(user2[0]),user2[1]);
				}
				else {
					//TODO: ERROR!!
				}
				
				//Set up the game.  Check to see what opponent is.
				if (Integer.parseInt(user2[2]) == 1)
					//Opponent is white
					game = new Game(gameID,opponent,player,new ChessBoard());
				else 
					//Our player is white
					game = new Game(gameID,player,opponent,new ChessBoard());
				
				startGame();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startGame() {
		//TODO: This process needs revision
		
		/*
		 * 1. If player is white, wait for piece click
		 * 2. Get list of moves
		 * 3. Highlight moves on GUI
		 * 4. Wait for a final move or cancel
		 * 5. Send final move to server
		 * 6. Wait for next move from server (Black piece jump to here)
		 * 7. Repeat until endState or connection lost
		 */
	}
	
}
