package com.bubblechess;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

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
	 * Create a game and return the gameID
	 * @param playerNumber 1 if white selected, 2 if black selected
	 * @return The gameID if successful, -1 if error
	 */
	public int CreateGame(int playerNumber) {
		//We haven't logged in yet
		assert(player != null);
		
		assert(playerNumber == 1 || playerNumber == 2);
		
		int result = server.CreateGame(player.getID(), playerNumber);
		
		if (result >= 0) {
			
			//Create user
			int gameID = result;
			
			//Set up the game.  User1 will be assumed white
			if (playerNumber == 1)
				//We are white
				game = new Game(gameID,player,null,new ChessBoard());
			else
				//Opponent is white
				game = new Game(gameID,null,player,new ChessBoard());
			
			return gameID; //success
		}
		else {
			return result;
		}
		
	}
	
	/**
	 * Spin while waiting for opponent to the current game
	 * @return True if an opponent joined, False if there was an issue
	 */
	public boolean WaitForOpponent(){
		//Get the second playerID.  The server will spin here?
		String[] user2 = server.GetOpponent(game.getID(), player.getID(), GetPlayerNumber());
		if (user2 != null) {
			opponent = new User(Integer.parseInt(user2[0]),user2[1]);
			
			//If we're player 1, our opponent is player2
			if(GetPlayerNumber()==1){
				game.setUser2(opponent);
			}
			else
				game.setUser1(opponent);
			return true;
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
	
	/**
	 * Get the player number.
	 * @return 1 if player 1, 2 if player 2, 0 if neither
	 */
	public int GetPlayerNumber() {
		if (game.getUser1() != null && game.getUser1() == player)
			return 1;
		else if (game.getUser2() != null && game.getUser2() == player)
			return 2;
		else
			return 0;
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
	
	/**
	 * Check if a move is valid, and if it is then send it to the server.
	 * @param m
	 * @return True if move is valid and submitted, false otherwise
	 */
	public boolean PlayMove(Move m) {
		boolean valid = game.playMove(m);
		
		//If our move wasn't valid
		if(!valid)
			return false;
		
		//If we've reached the end of the game by playing a move, we won or drew
		if(game.endState())
			System.out.println("Game over.");
		
		return server.SendMove(m, player.getID(), game.getID());
		
		
	}
	
	public Move WaitForNextMove() {
		if (IsPlayersTurn())
			return null;
		
		//We may wait here
		Move next = server.CheckForMove(game.getID(),GetPlayerNumber());
		
		game.playMove(next);
		
		//If we've reached the end of the game by playing a move, we won or drew
		if(game.endState())
			System.out.println("Game over.");
		
		return next;
	}
	
	/**
	 * Whether or not the game is in an End game state.
	 * @return True if EndState, False otherwise.
	 */
	public boolean EndState() {
		return game.endState();
	}
	
	/**
	 * Get a matrix of board pieces representing the board state
	 * @return All pieces currently on the board
	 */
	public BoardPiece[][] GetBoard() {
		return game.getBoard();
	}
	
	/**
	 * Get an array of captured BoardPieces
	 * @return Captured pieces
	 */
	public BoardPiece[] GetCaptured() {
		return game.getCaptured();
	}
	
	
	/**
	 * Check if either player is in check
	 * @return 0 if neither player in check, 1 if white in check, 2 if black in
	 * check
	 */
	public int InCheck() {
		return game.InCheck();
	}
	
	/**
	 * For testing a small game between two players
	 */
	public void TestServer(String username,String password,boolean creator){
		
		System.out.println("Register code: " + this.Register(username, password));
		
		System.out.println("Login code: " + this.Login(username, password));
		
		if (creator) {
			System.out.println("Creating game...");
			System.out.println("GameID: " + this.CreateGame(1));
			WaitForOpponent();
			System.out.println("Opponent: " + this.opponent.getUsername());
			this.PlayMove(new Move(new int[]{4,1}, new int[]{4,3}));
			System.out.println("Move sent.");
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			this.PlayMove(new Move(new int[]{3,0}, new int[]{7,4}));
			System.out.println("Move sent.");
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			this.PlayMove(new Move(new int[]{5,0}, new int[]{2,3}));
			System.out.println("Move sent.");
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			this.PlayMove(new Move(new int[]{7,4}, new int[]{5,6}));
			System.out.println("Move sent.");
			
		}
		else {
		
			ArrayList<Integer> games = this.GetJoinableGames();
			for(int gameid : games){
				System.out.println("Joinable: " + gameid);
			}
			
			System.out.println("Joining Game: " + games.get(0));
			System.out.println("Game Join: " + this.JoinGame(games.get(0)));
			System.out.println("Opponent: " + this.opponent.getUsername());
			
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			this.PlayMove(new Move(new int[]{4,6}, new int[]{4,4}));
			System.out.println("Move sent.");
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			this.PlayMove(new Move(new int[]{1,7}, new int[]{2,5}));
			System.out.println("Move sent.");
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			this.PlayMove(new Move(new int[]{5,7}, new int[]{2,4}));
			System.out.println("Move sent.");
			System.out.println("Waiting for move...");
			System.out.println("Received move: " + this.WaitForNextMove().toString());
			
		}
		
	}

}
