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
	
	public int getID(){
		return gameID;
	}
	
	public User getUser1(){
		return user1;
	}
	
	public User getUser2(){
		return user2;
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
	 * Returns the number of moves played in the game so far
	 * @return The number of moves
	 */
	public int getNumMoves(){
		return moves.size();
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
		
		if(turn == user1)
			turn = user2;
		else
			turn = user1;
		
		return true;
	}
	
	public User getTurn(){
		return turn;
	}
	
	public boolean endState(){
		return board.endState();
	}
	
	public ArrayList<Move> getMoves(int col, int row){
		return board.getMoves(col, row);
	}
	
	public int InCheck(){
		if (board.inCheck(BoardPiece.Color.WHITE))
			return 1;
		else if (board.inCheck(BoardPiece.Color.BLACK))
			return 2;
		else
			return 0;
	}


}
