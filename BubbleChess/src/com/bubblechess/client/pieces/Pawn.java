package com.bubblechess.client.pieces;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.ChessPiece;
import com.bubblechess.client.Move;

public class Pawn extends ChessPiece {

	protected int[][] dirsWhite = {N,NE,NW};
	protected int[][] dirsBlack = {S,SE,SW};

	/**
	 * Constructor for the Pawn Piece
	 * @param col
	 */
	public Pawn(PieceColor col) {
		color = col;
		hasMoved = false;
		id=5;
	}
	
	/**
	 * Gets possible moves a piece can make
	 * @return
	 */
	@Override
	public ArrayList<Move> getMoves(int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int from[] = {x,y};
		int to[] = new int[2];
		
		if (color == PieceColor.WHITE) {
			for (int i=0;i<dirsWhite.length;i++) {
				to[0] = x + dirsWhite[i][0]; // next x
				to[1] = y + dirsWhite[i][1]; // next y
				
				// if coordinate is in the board
				if (to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
					moves.add(new Move(from, to));
				}
			}
		}
		
		if (color == PieceColor.BLACK) {
			for (int i=0;i<dirsBlack.length;i++) {
				to[0] = x + dirsBlack[i][0]; // next x
				to[1] = y + dirsBlack[i][1]; // next y
				
				// if coordinate is in the board
				if (to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
					moves.add(new Move(from, to));
				}
			}
		}
		
		return moves;
	}
	
	/**
	 * Returns the pawns move forward 2 moves
	 * @return
	 */
	@Override
	public ArrayList<Move> getSpecialMoves(int x, int y){
		ArrayList<Move> moves = new ArrayList<Move>();
		int[] from = {x, y};
		int[] to = new int[2];
		
		//Move forward two spaces on first move
		if (hasMoved==false){
			if (color==PieceColor.WHITE){
				to[0] = x;
				to[1] = y+2;
				moves.add(new Move(from, to));
			}
			if (color==PieceColor.BLACK){
				to[0] = x;
				to[1] = y-2;
				moves.add(new Move(from, to));
			}
		}
		
		return moves;
	}
	
	/**
	 * Returns the directional movements of a pawn depending on color
	 * @return An array of directional vectors
	 */
	@Override
	public int[][] getDirs() {
		if (this.getColor() == PieceColor.WHITE) {
			return dirsWhite;
		}
		return dirsBlack;
	}

	/**
	 * Clones the piece object
	 * @return
	 */
	@Override
	public BoardPiece clone() {
		ChessPiece piece = new Pawn(this.getColor());
		return piece;
	}

	/**
	 * Returns the type of the piece
	 * @return
	 */
	@Override
	public String getType() {
		return "Pawn";
	}
}
