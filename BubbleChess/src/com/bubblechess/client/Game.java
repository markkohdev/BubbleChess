package com.bubblechess.client;

import java.util.ArrayList;

public class Game {
	
	/**
	 * Attributes
	 */
	protected int gameID;
	protected User user1;
	protected User user2;
	protected Board board;
	protected ArrayList<Move> moves;
	protected User turn;
	

	/**
	 * Constructor
	 * @param u1 First user
	 * @param u2 Second user
	 * @param b The initialize gameboard
	 */
	public Game(User u1, User u2,Board b){
		user1 = u1;
		user2 = u2;
		board = b;
		moves = new ArrayList<Move>();
		turn = user1;
	}

	/**
	 * The move at the specified index
	 * @param index
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
		moves.add(m);
		
		if(turn == user1)
			turn = user2;
		else
			turn = user1;
		return true;
	}
	
	public User getTurn(){
		return turn;
	}


}
