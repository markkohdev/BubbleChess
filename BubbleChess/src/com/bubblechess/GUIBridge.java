package com.bubblechess;
import java.io.IOException;
import java.util.ArrayList;

import com.bubblechess.client.*;

public class GUIBridge {
	protected Game game;
	protected User player;
	protected User opponent;
	protected ServerHandler server;
	
	/**
	 * Constructor
	 * @param serverHandler
	 */
	public GUIBridge(ServerHandler serverHandler) {
		this.server = serverHandler;
		game = null;
		player = null;
		opponent = null;
		
	}
	
	public GUIBridge() {
		game = null;
		player = null;
		opponent = null;
		
	}
	
	
	/*************************************************************************
	 * Pre-Game setup methods
	 ************************************************************************/
	
	/**
	 * Hit the server with the login info and return a username or failure code.
	 * @param username
	 * @param password
	 * @return 0 if success, -1 if incorrect password, -2 if user not found
	 */
	public int Login(String username, String password) {
		//Make sure username and password both have values
		assert (!username.equals(""));
		assert (!password.equals(""));
		
		int result = server.Login(username, password);
		
		if (result >= 0) {
			//Create user
			player = new User(result,username);
			
			return 0; //success
		}
		else {
			return result;
		}
	}
	
	/**
	 * Call the server to create a temporary user and give back the userID
	 * @return 0 if success, -1 if something went wrong (it shouldn't).
	 */
	public int ContinueAsGuest() {
		int result = server.ContinueAsGuest();
		
		if (result >= 0) {
			//Create user
			player = new User(result,"Guest");
			
			return 0; //success
		}
		else {
			return -1;
		}
		
	}
	
	/**
	 * To register a user.  Pass in the desired username/password. 
	 * @param username
	 * @param password
	 * @return 0 if success, -1 if username exists, -2 otherwise
	 */
	public int Register(String username, String password){
		//Make sure username and password both have values
		assert (!username.equals(""));
		assert (!password.equals(""));
		
		int result = server.Register(username, password);
		
		if (result >= 0) {
			//Create user
			player = new User(result,username);
			
			return 0; //success
		}
		else {
			return result;
		}
		
	}
	
	/**
	 * Create a game.  Will wait until opponent has joined to return true. 
	 * @param color 1 if white selected, 2 if black selected
	 * @return True if createGame successful, False if failed.  
	 */
	public boolean CreateGame(int color) {
		//We haven't logged in yet
		assert(player != null);
		
		int result = server.CreateGame(player.getID(), color);
		
		if (result >= 0) {
			//Create user
			int gameID = result;
			
			//Get the second playerID.  The server will spin here?
			String[] user2 = server.GetOpponent(gameID, player.getID(), color);
			if (user2 != null) {
				opponent = new User(Integer.parseInt(user2[0]),user2[1]);
			}
			else {
				return false;
			}
			
			//Set up the game.  User1 will be assumed white
			if (color == 1)
				//We are white
				game = new Game(gameID,player,opponent,new ChessBoard());
			else
				//Opponent is white
				game = new Game(gameID,opponent,player,new ChessBoard());
			
			return true; //success
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Have the player join the specified game
	 * @param gameID
	 * @return True if successful, False if not (Game full or invalid)
	 */
	public boolean JoinGame(int gameID) {
		//Make sure we're logged in
		assert (player != null);
		
		String[] result = server.JoinGame(gameID, player.getID());
		
		//We weren't able to join
		if(result == null)
			return false;
		
		//Set up the opponent
		opponent = new User(Integer.parseInt(result[0]),result[1]);
		
		//Set up the game.  PlayerNumber 1 will be assumed white
		if (Integer.parseInt(result[2]) == 1)
			//Opponent is white
			game = new Game(gameID,opponent,player,new ChessBoard());
		else
			//We are white
			game = new Game(gameID,player,opponent,new ChessBoard());
			
		
		return true; //success
		
		
	}
	
	/**
	 * Get a list of joinable games
	 * @return A list of joinable gameID's
	 */
	public ArrayList<Integer> GetJoinableGames() {
		return server.GetJoinableGames();
	}
	
	
	/*************************************************************************
	 * In-Game methods
	 ************************************************************************/
	
	/**
	 * Simple check to assert that it's the players turn.  Should be checked
	 * before attempting to play a move or getPossibleMoves.
	 * @return True if it's the current players turn, false if not.
	 */
	public boolean IsPlayersTurn() {
		// Make sure we're in-game
		assert(game != null);
		assert(player != null);
		assert(opponent != null);
		
		if (game.getTurn().getID() == player.getID()) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Return an array of possible moves in the current game for a given piece
	 * @param col
	 * @param row
	 * @return ArrayList of Moves to be displayed to the user
	 */
	public ArrayList<Move> getMoves(int col, int row){
		return game.getMoves(col, row);
	}
	
	public boolean PlayMove(Move m) {
		boolean valid = game.playMove(m);
		
		//If our move wasn't valid
		if(!valid)
			return false;
		
		return server.SendMove(m, player.getID(), game.getID());
	}
	
	//TODO: Implement waitfornextmove
	
	
	/**
	 * Dummy method to test move validation functionality
	 */
	public boolean TestGame() {
		User user1 = new User(1, "Eric");
		User user2 = new User(2, "Mark");
		
		Board testboard = new ChessBoard();
		int gameId = 123;
		
		Game testgame = new Game(gameId, user1, user2, testboard);
		
		//System.out.println("success");
		//System.out.println(testboard.getBoard());
		
		System.out.println(testgame.getMoves(0,0));
		System.out.println(testgame.getMoves(1,0));
		System.out.println(testgame.getMoves(2,0));
		System.out.println(testgame.getMoves(3,0));
		System.out.println(testgame.getMoves(4,0));
		System.out.println(testgame.getMoves(5,0));
		System.out.println(testgame.getMoves(6,0));
		System.out.println(testgame.getMoves(7,0));
		
		//System.out.println(testgame.getMoves(0,1));
		
		
		return false;
	}
	

}
