package com.bubblechess.client;

import java.util.ArrayList;

public class Bishop extends ChessPiece {

	public int[][] dirs = {NE,SE,SW,NW};
	
	public Bishop(Color col) {
		color = col;
	}
	
	/**
	 * Returns a list of possible moves (not guaranteed to be legal)
	 * @param x x-coordinate of chessboard
	 * @param y y-coordinate of chessboard
	 * @return A list of moves
	 * @throws Exception 
	 */
	@Override
	public ArrayList<Move> getAllMoves(int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int from[] = {x,y};
		int to[] = new int[2];
		
		for (int i=0;i<dirs.length;i++) {
			to[0] = x + dirs[i][0]; // next x
			to[1] = y + dirs[i][1]; // next y
			
			// while coordinate is in the board
			while(to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
				moves.add(new Move(from, to));
				to[0] += dirs[i][0];
				to[1] += dirs[i][1];
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
