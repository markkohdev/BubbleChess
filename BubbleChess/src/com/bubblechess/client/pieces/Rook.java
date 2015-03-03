package com.bubblechess.client.pieces;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.ChessPiece;
import com.bubblechess.client.Move;
import com.bubblechess.client.BoardPiece.Color;

public class Rook extends ChessPiece {
	
	protected int[][] dirs = {N,E,S,W};
	
	public Rook(Color col) {
		color = col;
		hasMoved = false;
	}

	@Override
	public ArrayList<Move> getMoves(int x, int y) {
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
		ChessPiece piece = new Rook(this.getColor());
		return piece;
	}
}
