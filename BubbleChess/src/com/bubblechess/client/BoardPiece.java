package com.bubblechess.client;

import java.util.ArrayList;

public interface BoardPiece {

	public enum PieceColor {WHITE, BLACK};
		
	/**
	 * Create a deep copy of the BoardPiece
	 * @return A deep copy of the BoardPiece
	 */
	public BoardPiece clone();
	
	/**
	 * Get a list of possible moves for a piece in the passed location
	 * @param x
	 * @param y
	 * @return A list of possible moves
	 */
	public ArrayList<Move> getMoves(int x, int y);
	
	/**
	 * Get a list of possible special moves for a piece in the passed location
	 * This includes moves such as castling and en passant
	 * @param x
	 * @param y
	 * @return A list of possible special moves
	 */
	public ArrayList<Move> getSpecialMoves(int x, int y);
	
	/**
	 * Get the piece's color (enum)
	 * @return The piece's color
	 */
	public PieceColor getColor();
	
	
	/**
	 * Get the piece's ID number
	 * @return The piece's ID
	 */
	public int getPieceID();
}
