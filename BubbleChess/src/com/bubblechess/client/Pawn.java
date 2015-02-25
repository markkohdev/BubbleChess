package com.bubblechess.client;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

	protected int[][] dirsWhite = {N,NE,NW};
	protected int[][] dirsBlack = {S,SE,SW};
	
	public Pawn(Color col) {
		color = col;
		hasMoved = false;
	}
	
	@Override
	public ArrayList<Move> getAllMoves(int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int from[] = {x,y};
		int to[] = new int[2];
		
		if (color == Color.WHITE) {
			for (int i=0;i<dirsWhite.length;i++) {
				to[0] = x + dirsWhite[i][0]; // next x
				to[1] = y + dirsWhite[i][1]; // next y
				
				// if coordinate is in the board
				if (to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
					moves.add(new Move(from, to));
				}
			}
			// can move forward 2 on the first move
			if (hasMoved == false) {
				to[0] = x;
				to[1] = y+2;
				moves.add(new Move(from, to));
			}
		}
		
		if (color == Color.BLACK) {
			for (int i=0;i<dirsBlack.length;i++) {
				to[0] = x + dirsBlack[i][0]; // next x
				to[1] = y + dirsBlack[i][1]; // next y
				
				// if coordinate is in the board
				if (to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
					moves.add(new Move(from, to));
				}
			}
			// can move forward 2 on the first move
			if (hasMoved == false) {
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
		if (this.getColor() == Color.WHITE) {
			return dirsWhite;
		}
		return dirsBlack;
	}

	@Override
	public BoardPiece clone() {
		ChessPiece piece = new Knight(this.getColor());
		piece.dirs = this.getDirs();
		piece.hasMoved = this.getHasMoved();
		return piece;
	}

}
