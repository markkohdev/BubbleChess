package com.bubblechess.client;

import java.util.ArrayList;

public class Game {
	
	/**
	 * Attributes
	 */
	protected int gameID;
	protected User user1; //White
	protected User user2; //Black
	protected Board board;
	protected ArrayList<Move> moves;
	protected User turn;
	

	/**
	 * Constructor
	 * @param gameID Game ID
	 * @param u1 First user
	 * @param u2 Second user
	 * @param b The initialize gameboard
	 */
	public Game(int gameID, User u1, User u2,Board b){
		this.gameID = gameID;
		user1 = u1;
		user2 = u2;
		board = b;
		moves = new ArrayList<Move>();
		turn = user1;
	}
	
	/**
	 * Gets the ID of the game
	 * @return Game ID
	 */
	public int getID(){
		return gameID;
	}
	
	/**
	 * Gets the first user object
	 * @return User 1
	 */
	public User getUser1(){
		return user1;
	}
	
	/**
	 * Gets the second user object
	 * @return User 2
	 */
	public User getUser2(){
		return user2;
	}
	
	/**
	 * Sets the first user object
	 * @param u1
	 */
	public void setUser1(User u1){
		user1 = u1;
	}
	
	/**
	 * Sets the second user object
	 * @param u2
	 */
	public void setUser2(User u2){
		user2 = u2;
	}

	/**
	 * The move at the specified index
	 * @param index+
	 * @return The requested move, or null if index out of range
	 */
	public Move getMove(int index){
		if(index < 0 || index > getNumMoves()) {
			return null;
		}
		return moves.get(index);
	}

	/**
	 * Gets the number of moves that have been played so far
	 * @return The number of moves
	 */
	public int getNumMoves(){
		return moves.size();
	}
	
	/**
	 * Gets the board as a 2 dimensional array
	 * @return The board
	 */
	public BoardPiece[][] getBoard(){
		return board.getBoard();
	}
	
	/**
	 * Gets a list of captured pieces
	 * @return An array of captured BoardPieces
	 */
	public BoardPiece[] getCaptured() {
		return board.getCaptured();
	}
	
	/**
	 * Add the passed move to the moves list, updates the turn
	 * @param m The move
	 * @return True if the move was successfully added, False if the game is in
	 * endState
	 */
	public boolean playMove(Move m) {
		if(board.endState()) {
			return false;
		}
		
		boolean valid = board.applyMove(m);
		
		//Make sure the move was valid
		if (!valid)
			return false;
		
		//Add it to the local history
		moves.add(m);
		
		//Update the board's state
		board.updateState();
		
		//Switch the turns
		if(turn.getID() == user1.getID())
			turn = user2;
		else
			turn = user1;
		
		return true;
	}
	
	/**
	 * Gets the user object who's turn it currently is
	 * @return User
	 */
	public User getTurn(){
		return turn;
	}
	
	/**
	 * Checks if the board is currently an end State
	 * @return True if the board is in an end state, False otherwise
	 */
	public boolean endState(){
		return board.endState();
	}
	
	/**
	 * Gets the current board state as a String
	 * @return The board's state
	 */
	public String getBoardState() {
		return board.getState();
	}
	
	/**
	 * Gets all possible moves from a given coordinate
	 * @param col
	 * @param row
	 * @return A list of possible moves
	 */
	public ArrayList<Move> getMoves(int col, int row){
		return board.getMoves(col, row);
	}
	
	/**
	 * Checks if the board is currently in state for a player
	 * @return 1 if white is in check, 2 if black is in check, 0 otherwise
	 */
	public int InCheck(){
		if (board.inCheck(BoardPiece.PieceColor.WHITE))
			return 1;
		else if (board.inCheck(BoardPiece.PieceColor.BLACK))
			return 2;
		else
			return 0;
	}


}
