package com.bubblechess.client;

import java.util.ArrayList;

public class Knight extends ChessPiece {
	
	// Modify directional vectors for knight movement
	protected int[] N = {1,2};
	protected int[] E = {2,1};
	protected int[] S = {2,-1};
	protected int[] W = {1,-2};
	protected int[] NE = {-1,-2};
	protected int[] SE = {-2,-1};
	protected int[] SW = {-2,1};
	protected int[] NW = {-1,2};
	
	protected int[][] dirs = {N,NE,E,SE,S,SW,W,NW};
	
	public Knight(Color col) {
		color = col;
	}

	@Override
	public ArrayList<Move> getAllMoves(int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int from[] = {x,y};
		int to[] = new int[2];
		
		for (int i=0;i<dirs.length;i++) {
			to[0] = x + dirs[i][0]; // next x
			to[1] = y + dirs[i][1]; // next y
			
			// if coordinate is in the board
			if (to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
				moves.add(new Move(from, to));
			}
		}
		return moves;
	}

	/**
	 * Returns the directional movements of the knight
	 * @return An array of directional vectors
	 */
	@Override
	public int[][] getDirs() {
		return dirs;
	}
	
	@Override
	public BoardPiece clone() {
		ChessPiece piece = new Knight(this.getColor());
		piece.dirs = this.getDirs();
		return piece;
	}
}
