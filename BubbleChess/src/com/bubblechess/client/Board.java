package com.bubblechess.client;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece.Color;

public interface Board {

	public enum Player {PLAYER1, PLAYER2};
	
	/**
	 * Get game board
	 * @return A 2D array of BoardPieces
	 */
	public BoardPiece[][] getBoard();
	
	/**
	 * Get game board width
	 * @return Width
	 */
	public int getWidth();
	
	/**
	 * Get game board height
	 * @return Height
	 */
	public int getHeight();
	
	/**
	 * Apply the passed move to the game board
	 * @param m
	 * @return True if move was successful, False if move is invalid
	 */
	public boolean applyMove(Move m);
	
	/**
	 * Apply the passed move to the game board and check that move is legal
	 * if the validate flag is set to True
	 * @param m
	 * @param validate
	 * @return True if move was successful, False if move is invalid
	 */
	public boolean applyMove(Move m, boolean validate);
	
	/**
	 * Apply the passed move to a clone of the game board
	 * @param m
	 * @return A new game board
	 */
	public Board applyMoveCloning(Move m);
	
	/**
	 * Apply the passed move to a clone of the game board
	 * @param m
	 * @param validate
	 * @return A new game board with the applied move
	 */
	public Board applyMoveCloning(Move m, boolean validate);
	
	/**
	 * Get a deep copy of the current board
	 * @return A deep copy of the current board
	 */
	public Board clone();
	
	/**
	 * Get all moves for a given piece in a board
	 * @param col
	 * @param row
	 * @return A list of moves
	 */
	public ArrayList<Move> getMoves(int col, int row);
	
	/**
	 * Get all moves for a given piece in a board
	 * @param col
	 * @param row
	 * @return A list of moves
	 */
	public ArrayList<Move> getMoves(char col, char row);
	

	/**
	 * Get the game board state string
	 * @return The game board state
	 */
	public String getState();
	
	/**
	 * Check if the game is over
	 * @return True if the game is over, False otherwise
	 */
	public boolean endState();
	
	/**
	 * Get a list of captured pieces
	 * @return An array of captured pieces
	 */
	public BoardPiece[] getCaptured();

	/**
	 * Update the board's state
	 */
	public void updateState();
	
	/**
	 * Check to see if the passed player color is in check
	 * @param color
	 * @return True if the player color is in check, False otherwise
	 */
	public boolean inCheck(Color color);
	
}
