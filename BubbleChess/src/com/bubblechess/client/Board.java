package com.bubblechess.client;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece.Color;

public interface Board {

	public enum Player {PLAYER1, PLAYER2};
	
	public BoardPiece[][] getBoard();
	
	/**
	 * Get gameboard width
	 * @return Width
	 */
	public int getWidth();
	
	/**
	 * Get gameboard height
	 * @return Height
	 */
	public int getHeight();
	
	public boolean applyMove(Move m);
	
	/**
	 * Apply the passed move to the game board
	 * @param m The Move to be applied
	 * @param validate Validate the move
	 * @return True if move was successful, False if move invalid
	 */
	public boolean applyMove(Move m, boolean validate);
	
	public Board applyMoveCloning(Move m);
	
	/**
	 * Apply the passed move to a clone of the game board
	 * @param m
	 * @param validate Validate the move
	 * @return
	 */
	public Board applyMoveCloning(Move m, boolean validate);
	
	/**
	 * Get a deep copy of the current board
	 * @return A deep copy of the current board
	 */
	public Board clone();
	
	/**
	 * Get all moves for a given piece in a board
	 * @param x
	 * @param y
	 * @return A list of moves
	 */
	public ArrayList<Move> getMoves(int col, int row);
	
	/**
	 * Get all moves for a given piece in a board
	 * @param x
	 * @param y
	 * @return A list of moves
	 */
	public ArrayList<Move> getMoves(char col, char row);
	

	/**
	 * Get the gameboard state string
	 * @return The game board state
	 */
	public String getState();
	
	/**
	 * Get whether or not the game is in over
	 * @return True if the game is over, False otherwise
	 */
	public boolean endState();
	
	/**
	 * Get a list of the captured pieces on the board
	 * @return
	 */
	public BoardPiece[] getCaptured();

	/**
	 * Update the board's state
	 */
	public void updateState();
	
	public boolean inCheck(Color color);
	
}
