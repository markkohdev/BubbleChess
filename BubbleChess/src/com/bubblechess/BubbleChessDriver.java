package com.bubblechess;
import com.bubblechess.client.*;
import org.json.simple.*;

public class BubbleChessDriver {

	public static void main(String[] args){
		//Run all the things here
		
		
		
	}
	
	public static void createGame() {
		/* 1. Hit the server with a createGame request and the userID 
		 * 2. Wait for a response which contains the created game ID
		 * 3. Show user gameID
		 * 4. Wait for second player (response from server will be game object)
		 * 5. Now that we know both users and the gameID, create the game object
		 * 6. Start gameplay
		 *  
		 */
	}

	public static void joinGame() {
		/*
		 * 1. Get userID from user
		 * 2. Hit server with joinGame and ID
		 * 3. Wait for server to respond with the game object or failure
		 * 4. Create the game object on our side
		 * 5. Start gameplay
		 */
	}
	
	public static void startGame() {
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
