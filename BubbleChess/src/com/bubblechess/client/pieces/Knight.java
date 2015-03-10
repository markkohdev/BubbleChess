package com.bubblechess.client.pieces;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.ChessPiece;
import com.bubblechess.client.Move;

public class Knight extends ChessPiece {
	
	
	public Knight(Color col) {
		color = col;
		
		// Modify directional vectors for knight movement
		N = new int[]{1,2};
		NE = new int[]{2,1};
		E = new int[]{2,-1};
		SE = new int[]{1,-2};
		S = new int[]{-1,-2};
		SW = new int[]{-2,-1};
		W = new int[]{-2,1};
		NW = new int[]{-1,2};
		
		dirs = new int[][]{N,NE,E,SE,S,SW,W,NW};
		id=4;
	}

	@Override
	public ArrayList<Move> getMoves(int x, int y) {
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
		return piece;
	}

	@Override
	public String getType() {
		return "Knight";
	}
}
