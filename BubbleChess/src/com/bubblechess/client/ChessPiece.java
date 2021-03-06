package com.bubblechess.client;

import java.util.ArrayList;

public abstract class ChessPiece implements BoardPiece {

	protected PieceColor color;
	protected int[][] dirs;
	protected boolean hasMoved;
	protected int id = -1;
	
	
	// Directional vectors as (x,y)
	protected int[] N = {0,1};
	protected int[] E = {1,0};
	protected int[] S = {0,-1};
	protected int[] W = {-1,0};
	protected int[] NE = {1,1};
	protected int[] SE = {1,-1};
	protected int[] SW = {-1,-1};
	protected int[] NW = {-1,1};
	
	/**
	 * A deep copy implemented by every ChessPiece subclass
	 * @return An identical BoardPiece
	 */
	public abstract BoardPiece clone();
	
	/**
	 * Returns a list of possible moves (not guaranteed to be legal)
	 * @param x
	 * @param y
	 * @return A list of moves
	 */
	public abstract ArrayList<Move> getMoves(int x, int y);
	
	/**
	 * Returns a list of possible special moves (not guaranteed to be legal)
	 * @param x
	 * @param y
	 * @return A list of special moves
	 */
	public ArrayList<Move> getSpecialMoves(int x, int y){
		return new ArrayList<Move>();
	}

	/**
	 * Returns the color of this piece
	 * @return Color.WHITE or Color.BLACK
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * Get the piece's type, e.g. Rook or Pawn
	 * @return A string representing the type of the piece
	 */
	public abstract String getType();
	
	/**
	 * Returns the directional movements of this piece
	 * @return An array of directional vectors
	 */
	public int[][] getDirs() {
		return dirs;
	}
	
	/**
	 * Returns true if the piece has moved, false otherwise
	 * Necessary information to determine if castling is legal
	 * @return True if moved, False otherwise
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * Get the piece ID number
	 * @return the piece ID
	 */
	public int getPieceID(){
		return id;
	}
}
