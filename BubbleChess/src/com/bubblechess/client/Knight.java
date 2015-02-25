package com.bubblechess.client;

import java.util.ArrayList;

public class Knight extends ChessPiece {
	
	int[] N = {1,2};
	int[] E = {2,1};
	int[] S = {2,-1};
	int[] W = {1,-2};
	int[] NE = {-1,-2};
	int[] SE = {-2,-1};
	int[] SW = {-2,1};
	int[] NW = {-1,2};
	
	public int[][] dirs = {N,NE,E,SE,S,SW,W,NW};
	
	public Knight(Color col) {
		color = col;
	}

	/**
	 * Returns a list of possible moves (not guaranteed to be legal)
	 * @param x x-coordinate of chessboard
	 * @param y y-coordinate of chessboard
	 * @return A list of moves
	 */
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

	@Override
	public BoardPiece clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
