package com.bubblechess.client;

import java.util.ArrayList;

public abstract class ChessPiece implements BoardPiece {

	public Color color;
	
	int[] N = {0,1};
	int[] E = {1,0};
	int[] S = {0,-1};
	int[] W = {-1,0};
	int[] NE = {1,1};
	int[] SE = {1,-1};
	int[] SW = {-1,-1};
	int[] NW = {-1,1};
	
	public abstract BoardPiece clone();
	public abstract ArrayList<Move> getAllMoves(int x, int y);

	public String getColor() {
		switch(color) {
		case WHITE: return "WHITE"; 
		case BLACK: return "BLACK";
		default: return "ERROR";
		}
	}
}
