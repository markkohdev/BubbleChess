package com.bubblechess.client.pieces;

import java.util.ArrayList;

import com.bubblechess.client.BoardPiece;
import com.bubblechess.client.ChessPiece;
import com.bubblechess.client.Move;
import com.bubblechess.client.BoardPiece.Color;

public class King extends ChessPiece {
	
	protected int[][] dirs = {N,NE,E,SE,S,SW,W,NW};
	
	public King(Color col) {
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
			
			// if coordinate is in the board
			if (to[0]>=0 && to[0]<=7 && to[1]>=0 && to[1]<=7) {
				moves.add(new Move(from, to));
			}
		}
		
		// castling may be possible if the white king is on e1 or the black king on e8
		if (color==Color.WHITE && x == 4 && y == 0 && hasMoved == false) {
			to[0] = 6;
			to[1] = 0;
			moves.add(new Move(from, to));
			to[0] = 2;
			to[1] = 0;
			moves.add(new Move(from, to));
		}
		if (color==Color.BLACK && x == 4 && y == 7 && hasMoved == false) {
			to[0] = 6;
			to[1] = 7;
			moves.add(new Move(from, to));
			to[0] = 2;
			to[1] = 7;
			moves.add(new Move(from, to));
		}

		return moves;
	}

	@Override
	public BoardPiece clone() {
		ChessPiece piece = new King(this.getColor());
		return piece;
	}
}
